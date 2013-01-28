/*
 * Tileset.java
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

import com.redarctic.nadia.collision.Rectangle;

import android.graphics.Bitmap;

public class Tileset {
	private Bitmap image;
	private int tileWidthInPixels;
	private int tileHeightInPixels;
    private int tilesWide;
    private int tilesHigh;
    private Rectangle[] sourceRectangles;
    
    public Bitmap getTexture() {
    	return this.image;
    }
    
    private void setTexture(Bitmap value) {
    	this.image = value;
    }
    
    public int getTileWidth() {
    	return this.tileWidthInPixels;
    }
    
    private void setTileWidth(int value) {
    	this.tileWidthInPixels = value;
    }
    
    public int getTileHeight() {
    	return this.tileHeightInPixels;
    }
    
    private void setTileHeight(int value) {
    	this.tileHeightInPixels = value;
    }

	public int getTilesWide() {
		return tilesWide;
	}

	private void setTilesWide(int tilesWide) {
		this.tilesWide = tilesWide;
	}

	public int getTilesHigh() {
		return tilesHigh;
	}

	private void setTilesHigh(int tilesHigh) {
		this.tilesHigh = tilesHigh;
	}

	public Rectangle[] getSourceRectangles() {
		return sourceRectangles.clone();
	}   
    
	public Tileset(Bitmap image, int tilesWide, int tilesHigh, int tileWidth, int tileHeight) {
		this.setTexture(image);
		this.setTileWidth(tileWidth);
		this.setTileHeight(tileHeight);
		this.setTilesWide(tilesWide);
		this.setTilesHigh(tilesHigh);
		
		int tiles = tilesWide * tilesHigh;
		
		sourceRectangles = new Rectangle[tiles];
		
		int tile = 0;
		
		for (int y = 0; y < tilesHigh; y++) {
			for (int x = 0; x < tilesWide; x++) {
				sourceRectangles[tile] = new Rectangle(
						x * tileWidth, 
						y * tileHeight, 
						(x * tileWidth) + tileWidth, 
						(y * tileHeight) + tileHeight);
				tile++;
			}
		}
		
	}
}
