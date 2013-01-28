/*
 * Engine.java
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

package com.redarctic.nadia.baseengine.tileengine;

import android.graphics.Point;

public class Engine {
	static int tileWidth;
	static int tileHeight;
	
	public static int getTileWidth() {
		return tileWidth;
	}
	public static int getTileHeight() {
		return tileHeight;
	}
	
	public Engine(int tileWidth, int tileHeight) {
		Engine.tileWidth = tileWidth;
		Engine.tileHeight = tileHeight;
	}
	
	public static Point vectorToCell(Point position) {
		return new Point((int)position.x / tileWidth, (int)position.y / tileHeight);
	}
}
