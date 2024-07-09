/*
 * Logisim-evolution - digital logic design tool and simulator
 * Copyright by the Logisim-evolution developers
 *
 * https://github.com/logisim-evolution/
 *
 * This is free software released under GNU GPLv3 license
 */

package com.cburch.logisim.std.io;

import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceData;
import java.awt.FontMetrics;

class KeyboardData implements InstanceData, Cloneable {
  private short index;
  private short[] buffer;
  private Value lastValue;
  private Value lastClock;

  public KeyboardData() {
    lastValue = Value.createUnknown(BitWidth.create(16));
    reset();
  }

  public void reset() {
    buffer = new short[65536];
    index = Short.MIN_VALUE;
  }

  public void insert(short c) {
    buffer[(index++)-Short.MIN_VALUE] = c;
  }

  public Value propagate(Value clock, short address) {
    Value ret = lastValue;

    if (clock == Value.TRUE && lastClock == Value.FALSE) {
      lastValue = Value.createKnown(16, buffer[address]);
    }
    lastClock = clock;

    return ret;
  }

  @Override
  public Object clone() {
    try {
      final var ret = (KeyboardData) super.clone();
      ret.index = this.index;
      ret.buffer = this.buffer.clone();
      ret.lastValue = lastValue;
      return ret;
    } catch (CloneNotSupportedException e) {
      return null;
    }
  }
}
