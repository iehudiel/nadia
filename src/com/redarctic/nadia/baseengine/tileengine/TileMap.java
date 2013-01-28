/*
 * TileMap.java
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

import java.util.ArrayList;

import com.redarctic.nadia.ext.MathHelper;
import com.redarctic.nadia.collision.Rectangle;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;


public class TileMap {
	ArrayList<Tileset> tilesets;
	ArrayList<MapLayer> mapLayers;
	
	static int mapWidth;
	static int mapHeight;
	
	public static int getWidthInPixels() {
		return mapWidth * Engine.getTileWidth();
	}
	
	public static int getHeightInPixels() {
		return mapHeight * Engine.getTileHeight();
	}
	
	public TileMap(ArrayList<Tileset> tilesets, ArrayList<MapLayer> layers) 
	throws TileException {
		this.tilesets = tilesets;
		this.mapLayers = layers;
		
		mapWidth = mapLayers.get(0).getWidth();
		mapHeight = mapLayers.get(0).getHeight();
		
		for (int i = 1; i < layers.size(); i++) {
			if (mapWidth != mapLayers.get(i).getWidth() || mapHeight != mapLayers.get(i).getHeight())
				throw new TileException("Map layer size exception");
		}
	}
	
	public TileMap(Tileset tileset, MapLayer layer) {
		this.tilesets = new ArrayList<Tileset>();
		this.tilesets.add(tileset);
		
		this.mapLayers = new ArrayList<MapLayer>();
		this.mapLayers.add(layer);
		
		mapWidth = mapLayers.get(0).getWidth();
		mapHeight = mapLayers.get(0).getHeight();
	}
	
	public void drawMe(Canvas canvas, Camera camera) {
		Point cameraPoint = Engine.vectorToCell(
				MathHelper.pointMulScalar(camera.getPosition(), 
						Math.round(1 / camera.getZoom()))
				);
		Point viewPoint = Engine.vectorToCell(
				new Point(
						(int)Math.round((camera.getPosition().x + camera.getViewportRectangle().width())
								* (1 / camera.getZoom())), 
						(int)Math.round((camera.getPosition().y + camera.getViewportRectangle().height()) 
								* (1 / camera.getZoom()))
						)
		);
		
		Point min = new Point();
		Point max = new Point();
		
		min.x = Math.max(0, cameraPoint.x - 1);
		min.y = Math.max(0, cameraPoint.y - 1);
		max.x = Math.min(viewPoint.x + 1, mapWidth);
		max.y = Math.min(viewPoint.y + 1, mapHeight);
		
		Rectangle destination = new Rectangle(0, 0, Engine.getTileWidth(), Engine.getTileHeight());
		Tile tile;
		
		for (MapLayer layer : this.mapLayers) {
			for (int y = min.y; y < max.y; y++) {
				destination.top = y * Engine.getTileHeight();
				destination.bottom = (y * Engine.getTileHeight()) + Engine.getTileHeight();
				
				for (int x = min.x; x < max.x; x++) {
					tile = layer.getTile(x, y);
					
					if (tile.getTileIndex() == -1 || tile.getTileset() == -1)
						continue;
					
					destination.left = x * Engine.getTileWidth();
					destination.right = (x * Engine.getTileWidth()) + Engine.getTileWidth();
					
					Rectangle rSrc = tilesets.get(tile.getTileset()).getSourceRectangles()[tile.getTileIndex()];
					Rect rSrc1 = new Rect((int)rSrc.left, (int)rSrc.top, (int)rSrc.right, (int)rSrc.bottom);					
					canvas.drawBitmap(
							tilesets.get(tile.getTileset()).getTexture(), 
							rSrc1,
							destination, 
							new Paint());
				}				
			}
		}
	}
	
	public void addLayer(MapLayer layer) throws TileException {
		if (layer != null) {
			if (layer.getWidth() != mapWidth && layer.getHeight() != mapHeight)
				throw new TileException("Map layer size exception");
			
			mapLayers.add(layer);
		}
	}
}
