/*
 * PrimitiveWrapper.java
 * 
 * Copyright (c) 2013, Emmanuel Arana Corzo. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */

package com.redarctic.nadia.ext.simplesignalslot;

/**
 * Author: Thia Yeo Ching, tycordinal@yahoo.co.uk Date: Mar 27, 2003 Time:
 * 12:46:55 AM
 */
public class PrimitiveWrapper {
	public static Boolean wrap(boolean v) {
		return Boolean.valueOf(v);
	}

	public static Character wrap(char v) {
		return Character.valueOf(v);
	}

	public static Byte wrap(byte v) {
		return new Byte(v);
	}

	public static Short wrap(short v) {
		return new Short(v);
	}

	public static Integer wrap(int v) {
		return Integer.valueOf(v);
	}

	public static Long wrap(long v) {
		return Long.valueOf(v);
	}

	public static Float wrap(float v) {
		return Float.valueOf(v);
	}

	public static Double wrap(double v) {
		return Double.valueOf(v);
	}
}
