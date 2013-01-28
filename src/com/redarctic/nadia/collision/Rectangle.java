/*
 * Rectangle.java
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

package com.redarctic.nadia.collision;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

public class Rectangle extends RectF {
	public Rectangle() {
		super();
	}
	
	public Rectangle(float left, float top, float right, float bottom) {
		super(left, top, right, bottom);
	}
	
	public Rectangle(RectF src) {
		super(src);
	}
	
	public Rectangle(PointF pt1, PointF pt2) {
		super(pt1.x, pt1.y, pt2.x, pt2.y);
	}
	
	public Rectangle(PointF position, float width, float height) {
		super(
				position.x, 
				position.y,
				position.x + width,
				position.y + height);
	}
	
	public Rectangle(Point position, int width, int height) {
		super(
				position.x, 
				position.y,
				position.x + width,
				position.y + height);
	}
}
