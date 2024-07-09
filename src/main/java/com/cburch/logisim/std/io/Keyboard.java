/*
 * Logisim-evolution - digital logic design tool and simulator
 * Copyright by the Logisim-evolution developers
 *
 * https://github.com/logisim-evolution/
 *
 * This is free software released under GNU GPLv3 license
 */

package com.cburch.logisim.std.io;

import static com.cburch.logisim.std.Strings.S;

import com.cburch.logisim.data.Attribute;
import com.cburch.logisim.data.Attributes;
import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Direction;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.gui.icons.KeyboardIcon;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstancePoker;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;
import com.cburch.logisim.instance.StdAttr;
import com.cburch.logisim.prefs.AppPreferences;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Keyboard extends InstanceFactory {
  /**
   * Unique identifier of the tool, used as reference in project files.
   * Do NOT change as it will prevent project files from loading.
   *
   * Identifier value must MUST be unique string among all tools.
   */
  public static final String _ID = "Keyboard";

  public static class Poker extends InstancePoker {
    @Override
    public void keyPressed(InstanceState state, KeyEvent e) {
      final var data = getKeyboardState(state);
      var used = true;

      synchronized (data) {
        int keycode = e.getKeyCode();

          //noinspection AssignmentUsedAsCondition
        if (used = ((keycode & 0xFF) == keycode)) data.insert((short) keycode);
      }

      if (used) {
        e.consume();
        state.getInstance().fireInvalidated();
      }
    }

    @Override
    public void keyTyped(InstanceState state, KeyEvent e) {
      final var data = getKeyboardState(state);
      final var ch = e.getKeyChar();

      synchronized (data) {
        data.insert((short) ch);
      }

      state.getInstance().fireInvalidated();
      e.consume();
    }
  }

  private static KeyboardData getKeyboardState(InstanceState state) {
    var ret = (KeyboardData) state.getData();

    if (ret == null) {
      ret = new KeyboardData();
      state.setData(ret);
    }

    return ret;
  }

  protected static final int RST = 0;

  protected static final int PCLK = 1;
  protected static final int PDAT = 2;

  protected static final int ADR = 3;
  protected static final int DATA = 4;

  protected static final int WCLK = 5;
  protected static final int RCLK = 6;

  private static final int DELAY0 = 9;

  static final int WIDTH = 145;

  static final int HEIGHT = 25;

  private static final Font DEFAULT_FONT = new Font("monospaced", Font.PLAIN, 12);

  @SuppressWarnings("checkstyle:IllegalTokenText")
  private static final char FORM_FEED = 12; // control-L (LINE FEED)

  private static final Attribute<Integer> ATTR_BUFFER =
      Attributes.forIntegerRange("buflen", S.getter("keybBufferLengthAttr"), 1, 256);

  public Keyboard() {
    super(_ID, S.getter("keyboardComponent"), new KeyboardHdlGeneratorFactory());
    setAttributes(
        new Attribute[] {ATTR_BUFFER, StdAttr.EDGE_TRIGGER},
        new Object[] {32, StdAttr.TRIG_RISING});
    setOffsetBounds(Bounds.create(0, -15, WIDTH, HEIGHT));
    setIcon(new KeyboardIcon());
    setInstancePoker(Poker.class);

    final var ps = new Port[7];
    ps[RST] = new Port(10, -20, Port.INPUT, 1);
    ps[PCLK] = new Port(10, 10, Port.INOUT, 1);
    ps[PDAT] = new Port(20, 10, Port.INOUT, 1);
    ps[ADR] = new Port(140, -20, Port.INPUT, 16);
    ps[DATA] = new Port(140, 10, Port.OUTPUT, 8);
    ps[WCLK] = new Port(0, -10, Port.CLOCK, 1);
    ps[RCLK] = new Port(0, 0, Port.CLOCK, 1);
    ps[RST].setToolTip(S.getter("keybResetTip"));
    ps[PCLK].setToolTip(S.getter("keybPS2ClockTip"));
    ps[PDAT].setToolTip(S.getter("keybPS2DataTip"));
    ps[ADR].setToolTip(S.getter("keybAddressTip"));
    ps[DATA].setToolTip(S.getter("keybDataTip"));
    ps[WCLK].setToolTip(S.getter("keybWrClkTip"));
    ps[RCLK].setToolTip(S.getter("keybRdClkTip"));
    setPorts(ps);
  }

  @Override
  public void paintInstance(InstancePainter painter) {
    final var g = painter.getGraphics();
    g.setColor(new Color(AppPreferences.COMPONENT_COLOR.get()));
    painter.drawClock(WCLK, Direction.EAST);
    painter.drawClock(RCLK, Direction.EAST);
    painter.drawBounds();
    painter.drawPort(RST);
    painter.drawPort(PCLK);
    painter.drawPort(PDAT);
    painter.drawPort(ADR);
    painter.drawPort(DATA);
  }

  @Override
  public void propagate(InstanceState circState) {
    final var state = getKeyboardState(circState);

    final var reset = circState.getPortValue(RST);
    final var rdClock = circState.getPortValue(RCLK);
    final var address = circState.getPortValue(ADR);

    Value val;
    synchronized (state) {
      val = state.propagate(rdClock, (short) address.toLongValue());

      if (reset == Value.TRUE) {
        state.reset();
      }
    }

    circState.setPort(DATA, val, DELAY0);
  }
}
