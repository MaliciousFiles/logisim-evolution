/*
 * Logisim-evolution - digital logic design tool and simulator
 * Copyright by the Logisim-evolution developers
 *
 * https://github.com/logisim-evolution/
 *
 * This is free software released under GNU GPLv3 license
 */

/*
 * This file was originally written by Kevin Walsh <kwalsh@cs.cornell.edu> for
 * Cornell's CS 314 computer organization course. It was subsequently modified
 * Martin Dybdal <dybber@dybber.dk> and Anders Boesen Lindbo Larsen
 * <abll@diku.dk> for use in the computer architecture class at the Department
 * of Computer Science, University of Copenhagen.
 */
package com.cburch.logisim.std.io;

import static com.cburch.logisim.std.Strings.S;

import com.cburch.logisim.circuit.CircuitState;
import com.cburch.logisim.comp.*;
import com.cburch.logisim.data.Attribute;
import com.cburch.logisim.data.AttributeEvent;
import com.cburch.logisim.data.AttributeListener;
import com.cburch.logisim.data.AttributeSet;
import com.cburch.logisim.data.AttributeSets;
import com.cburch.logisim.data.Attributes;
import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Direction;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;
import com.cburch.logisim.prefs.AppPreferences;
import com.cburch.logisim.tools.ToolTipMaker;
import com.cburch.logisim.util.FontUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.util.Arrays;
import java.util.BitSet;

public class Video extends InstanceFactory {
  private static final boolean[][] FONT = new boolean[256][200];

  private static boolean[] fillBoolArray(boolean[] arr) {
    boolean[] ret = new boolean[200];

    for (int i = 0; i < 200; i++) {
      ret[i] = i < arr.length && arr[i];
    }

    return ret;
  }

  static {
    FONT[0]  = fillBoolArray(new boolean[] { false });
    FONT[65] = fillBoolArray(new boolean[] { false, false, false, false, true, true, false, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, false, true, true, true, true, true, true, false, false, false, false, true, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[66] = fillBoolArray(new boolean[] { false, true, true, true, true, true, true, false, false, false, false, true, false, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, true, false, false, false, true, true, true, true, true, true, false, false, false, false, true, false, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, true, false, false, false, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[67] = fillBoolArray(new boolean[] { false, false, false, true, true, true, true, true, true, false, false, false, true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[68] = fillBoolArray(new boolean[] { false, true, true, true, true, true, true, false, false, false, false, true, false, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, true, false, false, false, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[69] = fillBoolArray(new boolean[] { false, false, true, true, true, true, true, true, true, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[70] = fillBoolArray(new boolean[] { false, false, true, true, true, true, true, true, true, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[71] = fillBoolArray(new boolean[] { false, false, false, true, true, true, true, true, true, false, false, false, true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, true, true, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, false, true, false, false, false, false, false, true, false, false, false, false, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[72] = fillBoolArray(new boolean[] { false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, true, true, true, true, true, true, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[73] = fillBoolArray(new boolean[] { false, true, true, true, true, true, true, true, true, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[74] = fillBoolArray(new boolean[] { false, false, false, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, true, false, false, false, false, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[75] = fillBoolArray(new boolean[] { false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, true, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, true, true, false, false, false, false, false, true, true, true, false, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, true, false, false, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[76] = fillBoolArray(new boolean[] { false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[77] = fillBoolArray(new boolean[] { false, true, true, false, false, false, false, true, true, false, false, true, false, true, false, false, true, false, true, false, false, true, false, true, false, false, true, false, true, false, false, true, false, false, true, true, false, false, true, false, false, true, false, false, true, true, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[78] = fillBoolArray(new boolean[] { false, true, true, false, false, false, false, false, true, false, false, true, false, true, false, false, false, false, true, false, false, true, false, true, false, false, false, false, true, false, false, true, false, true, false, false, false, false, true, false, false, true, false, false, true, false, false, false, true, false, false, true, false, false, true, false, false, false, true, false, false, true, false, false, true, false, false, false, true, false, false, true, false, false, false, true, false, false, true, false, false, true, false, false, false, true, false, false, true, false, false, true, false, false, false, true, false, false, true, false, false, true, false, false, false, true, false, false, true, false, false, true, false, false, false, false, true, false, true, false, false, true, false, false, false, false, true, false, true, false, false, true, false, false, false, false, true, false, true, false, false, true, false, false, false, false, true, false, true, false, false, true, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[79] = fillBoolArray(new boolean[] { false, false, true, true, true, true, true, true, false, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, false, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[80] = fillBoolArray(new boolean[] { false, true, true, true, true, true, true, false, false, false, false, true, false, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, true, false, false, false, true, true, true, true, true, true, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[81] = fillBoolArray(new boolean[] { false, false, false, true, true, true, true, false, false, false, false, false, true, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, true, false, false, false, true, false, false, true, false, false, false, true, false, false, true, false, false, true, false, false, false, false, true, false, true, false, false, false, true, false, false, false, false, true, false, false, false, false, false, true, true, true, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[82] = fillBoolArray(new boolean[] { false, true, true, true, true, true, true, false, false, false, false, true, false, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, true, false, false, false, true, true, true, true, true, true, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, false, true, true, false, false, false, false, false, false, true, false, false, false, true, false, false, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, false, false, true, false, false, false, true, false, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[83] = fillBoolArray(new boolean[] { false, false, false, true, true, true, true, false, false, false, false, false, true, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, true, true, true, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, false, true, false, false, false, false, true, false, false, false, false, false, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[84] = fillBoolArray(new boolean[] { false, true, true, true, true, true, true, true, true, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[85] = fillBoolArray(new boolean[] { false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, false, true, false, false, false, false, true, false, false, false, false, false, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[86] = fillBoolArray(new boolean[] { false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[87] = fillBoolArray(new boolean[] { false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, true, true, false, false, true, false, false, true, false, false, true, true, false, false, true, false, false, true, false, true, false, false, true, false, true, false, false, true, false, true, false, false, true, false, true, false, false, true, false, true, false, false, true, false, true, false, false, true, true, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[88] = fillBoolArray(new boolean[] { false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[89] = fillBoolArray(new boolean[] { false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
    FONT[90] = fillBoolArray(new boolean[] { false, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false });
  }

  /**
   * Unique identifier of the tool, used as reference in project files. Do NOT change as it will
   * prevent project files from loading.
   *
   * <p>Identifier value must MUST be unique string among all tools.
   */
  public static final String _ID = "RGB Video";

  @Override
  public void paintIcon(InstancePainter painter) {
    final var g = painter.getGraphics().create();
    g.setColor(Color.WHITE);
    g.fillRoundRect(scale(2), scale(2), scale(16 - 1), scale(16 - 1), scale(3), scale(3));
    g.setColor(Color.BLACK);
    g.drawRoundRect(scale(2), scale(2), scale(16 - 1), scale(16 - 1), scale(3), scale(3));
    int five = scale(5);
    int ten = scale(10);
    g.setColor(Color.RED);
    g.fillRect(five, five, five, five);
    g.setColor(Color.BLUE);
    g.fillRect(ten, five, five, five);
    g.setColor(Color.GREEN);
    g.fillRect(five, ten, five, five);
    g.setColor(Color.MAGENTA);
    g.fillRect(ten, ten, five, five);
    g.dispose();

  }

  static final int CLK = 0;
  static final int WE = 1;
  static final int X = 2;
  static final int Y = 3;
  static final int DATA = 4;

  private static final int WIDTH = 640;
  private static final int HEIGHT = 480;

  public Video() {
    super(_ID, S.getter("videoComponent"), new VideoHdlGeneratorFactory());

    setOffsetBounds(Bounds.create(-30, -(HEIGHT + 14), WIDTH + 14, HEIGHT + 14));

    final var ps = new Port[5];
    ps[CLK] = new Port(0, 0, Port.INPUT, 1);
    ps[WE] = new Port(10, 0, Port.INPUT, 1);
    ps[X] = new Port(40, 0, Port.INPUT, 6);
    ps[Y] = new Port(50, 0, Port.INPUT, 5);
    ps[DATA] = new Port(60, 0, Port.INPUT, 8);
    ps[CLK].setToolTip(S.getter("videoClockTip"));
    ps[WE].setToolTip(S.getter("videoWriteEnableTip"));
    ps[X].setToolTip(S.getter("videoXTip"));
    ps[Y].setToolTip(S.getter("videoYTip"));
    ps[DATA].setToolTip(S.getter("videoDataTip"));
    setPorts(ps);
  }

  @Override
  public void propagate(InstanceState circuitState) {
    final var state = getVideoState(circuitState);

    final var clk = circuitState.getPortValue(CLK);
    final var we = circuitState.getPortValue(WE);
    final var x = circuitState.getPortValue(X);
    final var y = circuitState.getPortValue(Y);
    final var data = circuitState.getPortValue(DATA);

    synchronized (state) {
      if (state.lastClock(clk) == Value.FALSE && clk == Value.TRUE) {
        if (we.toLongValue() == 1) state.write((int) x.toLongValue(), (int) y.toLongValue(), (char) data.toLongValue());
      }
    }
  }

  @Override
  public void paintInstance(InstancePainter painter) {
    final var g = painter.getGraphics();
    final var state = getVideoState(painter);

    var bds = painter.getInstance().getComponent().getBounds();
    int x = bds.getX();
    int y = bds.getY();

    g.setColor(new Color(AppPreferences.COMPONENT_COLOR.get()));

    painter.drawClock(CLK, Direction.NORTH);
    painter.drawBounds();
    painter.drawPort(WE);
    painter.drawPort(X);
    painter.drawPort(Y);
    painter.drawPort(DATA);

    g.drawRect(x + 6, y + 6, WIDTH + 2, HEIGHT + 2);

    char[] buffer = state.getBuffer();

    for (int v = 0; v < HEIGHT; v++) {
      int charY = v / VideoData.CHAR_HEIGHT;

      for (int u = 0; u < WIDTH; u++) {
        int charX = u / VideoData.CHAR_WIDTH;
        boolean[] ch = FONT[buffer[charX + charY * VideoData.WIDTH]];

        if (ch[(v % VideoData.CHAR_HEIGHT) * VideoData.CHAR_WIDTH + u % VideoData.CHAR_WIDTH]) {
          g.setColor(Color.BLACK);
        } else {
          g.setColor(Color.WHITE);
        }

        g.fillRect(x + 7 + u, y + 7 + v, 1, 1);
      }
    }
  }

  private static VideoData getVideoState(InstanceState state) {
    var ret = (VideoData) state.getData();

    if (ret == null) {
      ret = new VideoData();
      state.setData(ret);
    }

    return ret;
  }

  private static int scale(int v) {
    return AppPreferences.getScaled(v);
  }
}
