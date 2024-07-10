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
import java.util.Arrays;

public class Keyboard extends InstanceFactory {
  /**
   * Unique identifier of the tool, used as reference in project files.
   * Do NOT change as it will prevent project files from loading.
   *
   * Identifier value must MUST be unique string among all tools.
   */
  public static final String _ID = "Keyboard";

  public static class Poker extends InstancePoker {
    private short[] getScanCode(int keycode) {
      return switch (keycode) {
        case KeyEvent.VK_F9 -> new short[] {0x01};
        case KeyEvent.VK_F5 -> new short[] {0x03};
        case KeyEvent.VK_F3 -> new short[] {0x04};
        case KeyEvent.VK_F1 -> new short[] {0x05};
        case KeyEvent.VK_F2 -> new short[] {0x06};
        case KeyEvent.VK_F12 -> new short[] {0x07};
        case KeyEvent.VK_F10 -> new short[] {0x09};
        case KeyEvent.VK_F8 -> new short[] {0x0A};
        case KeyEvent.VK_F6 -> new short[] {0x0B};
        case KeyEvent.VK_F4 -> new short[] {0x0C};
        case KeyEvent.VK_TAB -> new short[] {0x0D};
        case KeyEvent.VK_BACK_QUOTE -> new short[] {0x0E};
        case KeyEvent.VK_ALT -> new short[] {0x11};
        case KeyEvent.VK_SHIFT -> new short[] {0x12};
        case KeyEvent.VK_CONTROL -> new short[] {0x14};
        case KeyEvent.VK_Q -> new short[] {0x15};
        case KeyEvent.VK_1 -> new short[] {0x16};
        case KeyEvent.VK_Z -> new short[] {0x1A};
        case KeyEvent.VK_S -> new short[] {0x1B};
        case KeyEvent.VK_A -> new short[] {0x1C};
        case KeyEvent.VK_W -> new short[] {0x1D};
        case KeyEvent.VK_2 -> new short[] {0x1E};
        case KeyEvent.VK_C -> new short[] {0x21};
        case KeyEvent.VK_X -> new short[] {0x22};
        case KeyEvent.VK_D -> new short[] {0x23};
        case KeyEvent.VK_E -> new short[] {0x24};
        case KeyEvent.VK_4 -> new short[] {0x25};
        case KeyEvent.VK_3 -> new short[] {0x26};
        case KeyEvent.VK_SPACE -> new short[] {0x29};
        case KeyEvent.VK_V -> new short[] {0x2A};
        case KeyEvent.VK_F -> new short[] {0x2B};
        case KeyEvent.VK_T -> new short[] {0x2C};
        case KeyEvent.VK_R -> new short[] {0x2D};
        case KeyEvent.VK_5 -> new short[] {0x2E};
        case KeyEvent.VK_N -> new short[] {0x31};
        case KeyEvent.VK_B -> new short[] {0x32};
        case KeyEvent.VK_H -> new short[] {0x33};
        case KeyEvent.VK_G -> new short[] {0x34};
        case KeyEvent.VK_Y -> new short[] {0x35};
        case KeyEvent.VK_6 -> new short[] {0x36};
        case KeyEvent.VK_M -> new short[] {0x3A};
        case KeyEvent.VK_J -> new short[] {0x3B};
        case KeyEvent.VK_U -> new short[] {0x3C};
        case KeyEvent.VK_7 -> new short[] {0x3D};
        case KeyEvent.VK_8 -> new short[] {0x3E};
        case KeyEvent.VK_COMMA -> new short[] {0x41};
        case KeyEvent.VK_K -> new short[] {0x42};
        case KeyEvent.VK_I -> new short[] {0x43};
        case KeyEvent.VK_O -> new short[] {0x44};
        case KeyEvent.VK_0 -> new short[] {0x45};
        case KeyEvent.VK_9 -> new short[] {0x46};
        case KeyEvent.VK_PERIOD -> new short[] {0x49};
        case KeyEvent.VK_SLASH -> new short[] {0x4A};
        case KeyEvent.VK_L -> new short[] {0x4B};
        case KeyEvent.VK_SEMICOLON -> new short[] {0x4C};
        case KeyEvent.VK_P -> new short[] {0x4D};
        case KeyEvent.VK_MINUS -> new short[] {0x4E};
        case KeyEvent.VK_QUOTE -> new short[] {0x52};
        case KeyEvent.VK_OPEN_BRACKET -> new short[] {0x54};
        case KeyEvent.VK_EQUALS -> new short[] {0x55};
        case KeyEvent.VK_CAPS_LOCK -> new short[] {0x58};
//        case KeyEvent.VK_SHIFT -> new short[] {0x59};
        case KeyEvent.VK_ENTER -> new short[] {0x5A};
        case KeyEvent.VK_CLOSE_BRACKET -> new short[] {0x5B};
        case KeyEvent.VK_BACK_SLASH -> new short[] {0x5D};
        case KeyEvent.VK_BACK_SPACE -> new short[] {0x66};
        case KeyEvent.VK_NUMPAD1 -> new short[] {0x69};
        case KeyEvent.VK_NUMPAD4 -> new short[] {0x6B};
        case KeyEvent.VK_NUMPAD7 -> new short[] {0x6C};
        case KeyEvent.VK_NUMPAD0 -> new short[] {0x70};
//        case KeyEvent.VK_NUMPAD_PERIOD -> new short[] {0x71};
        case KeyEvent.VK_NUMPAD2 -> new short[] {0x72};
        case KeyEvent.VK_NUMPAD5 -> new short[] {0x73};
        case KeyEvent.VK_NUMPAD6 -> new short[] {0x74};
        case KeyEvent.VK_NUMPAD8 -> new short[] {0x75};
        case KeyEvent.VK_ESCAPE -> new short[] {0x76};
        case KeyEvent.VK_NUM_LOCK -> new short[] {0x77};
        case KeyEvent.VK_F11 -> new short[] {0x78};
//        case KeyEvent.VK_NUMPAD_PLUS -> new short[] {0x79};
        case KeyEvent.VK_NUMPAD3 -> new short[] {0x7A};
//        case KeyEvent.VK_NUMPAD_MINUS -> new short[] {0x7B};
//        case KeyEvent.VK_NUMPAD_MULTIPLY -> new short[] {0x7C};
        case KeyEvent.VK_NUMPAD9 -> new short[] {0x7D};
        case KeyEvent.VK_SCROLL_LOCK -> new short[] {0x7E};
        case KeyEvent.VK_F7 -> new short[] {0x83};
        case KeyEvent.VK_END -> new short[] {0xE0, 0x69};
        case KeyEvent.VK_LEFT -> new short[] {0xE0, 0x6B};
        case KeyEvent.VK_HOME -> new short[] {0xE0, 0x6C};
        case KeyEvent.VK_INSERT -> new short[] {0xE0, 0x70};
        case KeyEvent.VK_DELETE -> new short[] {0xE0, 0x71};
        case KeyEvent.VK_DOWN -> new short[] {0xE0, 0x72};
        case KeyEvent.VK_RIGHT -> new short[] {0xE0, 0x74};
        case KeyEvent.VK_UP -> new short[] {0xE0, 0x75};
        case KeyEvent.VK_PAGE_DOWN -> new short[] {0xE0, 0x7A};
        case KeyEvent.VK_PAGE_UP -> new short[] {0xE0, 0x7D};
        case KeyEvent.VK_PRINTSCREEN -> new short[] {0xE0, 0x12, 0xE0, 0x7C};
        default -> new short[] {-1};
      };
    }

    @Override
    public void keyPressed(InstanceState state, KeyEvent e) {
      final var data = getKeyboardState(state);
      var used = true;

      synchronized (data) {
        short[] scanCode = getScanCode(e.getKeyCode());

          //noinspection AssignmentUsedAsCondition
        if (used = (scanCode[0] != -1)) {
          for (short c : scanCode) data.insert(c);
        }
      }

      if (used) {
        e.consume();
        state.getInstance().fireInvalidated();
      }
    }

    @Override
    public void keyReleased(InstanceState state, KeyEvent e) {
      final var data = getKeyboardState(state);
      var used = true;

      synchronized (data) {
        short[] scanCode = getScanCode(e.getKeyCode());

        //noinspection AssignmentUsedAsCondition
        if (used = (scanCode[0] != -1)) {
          if (scanCode[0] != 0xE0) data.insert((short) 0xF0);
          for (short c : scanCode) {
            data.insert(c);
            if (c == 0xE0) data.insert((short) 0xF0);
          }
        }
      }

      if (used) {
        e.consume();
        state.getInstance().fireInvalidated();
      }
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

  static final int WIDTH = 150;

  static final int HEIGHT = 30;


  public Keyboard() {
    super(_ID, S.getter("keyboardComponent"), new KeyboardHdlGeneratorFactory());
//    setAttributes(new Attribute[] {}, new Object[] {});
    setOffsetBounds(Bounds.create(0, -20, WIDTH, HEIGHT));
    setIcon(new KeyboardIcon());
    setInstancePoker(Poker.class);

    final var ps = new Port[7];
    ps[RST] = new Port(10, -20, Port.INPUT, 1);
    ps[PCLK] = new Port(10, 10, Port.INOUT, 1);
    ps[PDAT] = new Port(20, 10, Port.INOUT, 1);
    ps[ADR] = new Port(140, -20, Port.INPUT, 16);
    ps[DATA] = new Port(140, 10, Port.OUTPUT, 8);
    ps[WCLK] = new Port(0, -10, Port.INPUT, 1);
    ps[RCLK] = new Port(0, 0, Port.INPUT, 1);
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
