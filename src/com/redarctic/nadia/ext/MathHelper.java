/*
 * MathHelper.java
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

package com.redarctic.nadia.ext;

import android.graphics.Point;
import android.graphics.PointF;

public class MathHelper {
	public static int clamp(int value, int min, int max) {
		int retValue = 0;
		
		if (value > max) {
			retValue = max;
		}
		else if (value < min) {
			retValue = min;
		}
		else if (min <= value && value <= max) {
			retValue = value;
		}
		
		return retValue;
	}
		
	public static float clamp(float value, float min, float max) {
		float retValue = 0.0f;
		
		if (value > max) {
			retValue = max;
		}
		else if (value < min) {
			retValue = min;
		}
		else if (min <= value && value <= max) {
			retValue = value;
		}
		
		return retValue;
	}
	
	public static Point normalize(Point p) {
		Point pRet = new Point(p);
		if (p != null) {
			double x = 0.0, y = 0.0;
			
			x = p.x / length2d(p);
			y = p.y / length2d(p);
			pRet.x = (int)Math.round(x);
			pRet.y = (int)Math.round(y);
		}
		return pRet;
	}
	
	public static PointF normalize(PointF p) {
		PointF pRet = new PointF(p.x, p.y);
		if (p != null) {
			float x = 0.0f, y = 0.0f;
			
			x = p.x / (float)length2d(p.x, p.y);
			y = p.y / (float)length2d(p.x, p.y);
			pRet.x = x;
			pRet.y = y;
		}
		return pRet;
	}
	
	public static double length2d(Point p) {
		return length2d(p.x, p.y);
	}
	
	public static double length2d(double x, double y) {
		return sqrt((x * x) + (y * y));
	}
	
	public static double sqrt(final double a) {
		final long x = Double.doubleToLongBits(a) >> 32;
		double y = Double.longBitsToDouble((x + 1072632448) << 31);
		
		y = (y + a / y) * 0.5;
		return y;
	}
	
	public static double pow(final double a, final double b) {
		final int tmp = (int) (Double.doubleToLongBits(a) >> 32);
	    final int tmp2 = (int) (b * (tmp - 1072632447) + 1072632447);
	    return Double.longBitsToDouble(((long) tmp2) << 32);
	}
	
	public static double exp(double val) {
		final long tmp = (long) (1512775 * val + 1072632447);
		return Double.longBitsToDouble(tmp << 32);
	}
	
	public static PointF pointMulScalar(PointF point, float scalar) {
		PointF pRet = new PointF(point.x, point.y);
		
		pRet.x *= scalar;
		pRet.y *= scalar;
		
		return pRet;
	}
	
	public static Point pointMulScalar(Point p, int scalar) {
		Point pRet = new Point(p);
		
		pRet.x *= scalar;
		pRet.y *= scalar;
		
		return pRet;
	}
}
