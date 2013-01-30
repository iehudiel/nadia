/*
 * Level.java
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

package com.redarctic.nadia.baseengine.world;

import android.graphics.Canvas;

import com.redarctic.nadia.baseengine.tileengine.Camera;
import com.redarctic.nadia.baseengine.tileengine.TileMap;

public class Level {
	private TileMap map;

	public TileMap getMap() {
		return map;
	}
	
	public Level(TileMap tileMap) {
		if (tileMap != null)
			this.map = tileMap;
	}

	public void update(Canvas canvas) {		
	}
	
	public void drawMe(Canvas canvas, Camera camera) {
		this.map.drawMe(canvas, camera);
	}
	
	
}
