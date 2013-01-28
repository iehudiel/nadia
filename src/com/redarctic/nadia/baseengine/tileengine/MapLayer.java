/*
 * MapLayer.java
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

public class MapLayer {
	Tile[][] map;
	
	public int getWidth() {
		return map[0].length;
	}
	
	public int getHeight() {
		if (map.length > 0) 
			return map.length;		
		else 
			return 0;
	}
	
	public MapLayer(Tile[][] map) {
		this.map = (Tile[][])map.clone();
	}
	
	public MapLayer(int width, int height) {
		map = new Tile[height][width];
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				map[y][x] = new Tile(0, 0);
			}
		}
	}
	
	public Tile getTile(int x, int y) {
		if (y < getHeight() && x < getWidth()) {
			return map[y][x];
		}
		else {
			return null;
		}
	}
	
	public void setTile(int x, int y, Tile tile) {
		if (y < getHeight() && x < getWidth()) {
			map[y][x] = tile;
		}
	}
	
	public void setTile(int x, int y, int tileIndex, int tileset) {
		if (y < getHeight() && x < getWidth())
			map[y][x] = new Tile(tileIndex, tileset);
	}
}
