/*
 * SpriteFont.java
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

package com.redarctic.nadia.baseengine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;

public class SpriteFont {
	public static final int DEFAULT_TEXT_ALPHA = 200;
	public static final float DEFAULT_TEXT_SIZE = 16.0f;
	
	public static void drawText(Canvas canvas, String text, PointF position, Paint paint) {
		if ( canvas != null && position != null ) {
			if (paint == null) {
				paint = getDefaultWhiteFont();
			}
			
			canvas.drawText(text, position.x, position.y, paint);
		}
	}
	
	public static void drawText(Canvas canvas, String text, Point position, Paint paint) {
		if ( canvas != null && position != null ) {
			if (paint == null) {
				paint = getDefaultWhiteFont();
			}
			
			canvas.drawText(text, (float)position.x, (float)position.y, paint);
		}
	}
	
	public static Paint getDefaultWhiteFont() {
		Paint paint = new Paint();
		int white = Color.LTGRAY;
		paint.setColor(white);
		paint.setAlpha(DEFAULT_TEXT_ALPHA);
		paint.setAntiAlias(true);
		Typeface typeFace = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
		paint.setTypeface(typeFace);
		paint.setTextSize(DEFAULT_TEXT_SIZE);
		
		return paint;
	}
	
	public static Paint getDefaultBlackFont() {
		Paint paint = new Paint();
		int color = Color.BLACK;
		paint.setColor(color);
		paint.setAlpha(DEFAULT_TEXT_ALPHA);
		paint.setAntiAlias(true);
		Typeface typeFace = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
		paint.setTypeface(typeFace);
		paint.setTextSize(DEFAULT_TEXT_SIZE);
		
		return paint;
	}
	
	public static Paint getDefaultFont(int color) {
		Paint paint = new Paint();
		paint.setColor(color);
		paint.setAlpha(DEFAULT_TEXT_ALPHA);
		paint.setAntiAlias(true);
		Typeface typeFace = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
		paint.setTypeface(typeFace);
		paint.setTextSize(DEFAULT_TEXT_SIZE);
		
		return paint;
	}
	
	public static Paint getDefaultFont(long argbMask) {
		Paint paint = new Paint();
		int alpha = (int)(argbMask & 0xff000000) >> 24;
		int red = (int)(argbMask & 0x00ff0000) >> 16;
		int green = (int)(argbMask & 0x0000ff00) >> 8;
		int blue = (int)(argbMask & 0x000000ff);
		paint.setARGB(alpha, red, green, blue);
		paint.setAlpha(DEFAULT_TEXT_ALPHA);
		paint.setAntiAlias(true);
		Typeface typeFace = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
		paint.setTypeface(typeFace);
		paint.setTextSize(DEFAULT_TEXT_SIZE);
		
		return paint;
	}
	
	public static Point getMeasureString(String text) {
		Point ptSize = new Point();
		
		Rect size = new Rect();
		getDefaultWhiteFont().getTextBounds(text, 0, text.length(), size);
		
		ptSize.x = size.width();
		ptSize.y = size.height();
		
		return ptSize;
	}
}
