package com.cburch.logisim.std.io;

import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceData;

public class VideoData implements InstanceData, Cloneable {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 24;

    public static final int CHAR_WIDTH = 10;
    public static final int CHAR_HEIGHT = 20;

    private char[] buffer = new char[WIDTH*HEIGHT];
    private Value lastClock;

    public char[] getBuffer() {
        return buffer.clone();
    }

    public void write(int x, int y, char value) {
        buffer[x + y * 64] = value;
    }

    public Value lastClock(Value clock) {
        Value ret = lastClock;

        lastClock = clock;

        return ret;
    }

    @Override
    public Object clone() {
        try {
            final var ret = (VideoData) super.clone();
            ret.buffer = this.buffer.clone();
            return ret;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
