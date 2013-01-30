/*
 * World.java
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

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Point;

import com.redarctic.nadia.baseengine.DrawableGameComponent;
import com.redarctic.nadia.baseengine.item.ItemManager;
import com.redarctic.nadia.baseengine.tileengine.Camera;
import com.redarctic.nadia.collision.Rectangle;

public class World extends DrawableGameComponent {
	Rectangle screenRect;
	
	public Rectangle getScreenRect() {
		return screenRect;
	}
	
	ItemManager itemManager = new ItemManager();
	
	private ArrayList<Level> levels = new ArrayList<Level>();
	int currentLevel = -1;
	
	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		if (currentLevel < 0 || currentLevel >= levels.size())
			throw new IndexOutOfBoundsException();
		if (levels.get(currentLevel) == null)
			throw new NullPointerException();
		
		this.currentLevel = currentLevel;
	}

	public ArrayList<Level> getLevels() {
		return levels;
	}
	
	public World(Rectangle screenRectangle) {
		super();
		this.screenRect = screenRectangle;
	}
	
	public World(Point screenSize) {
		super();
		this.screenRect = new Rectangle(0, 0, screenSize.x, screenSize.y);
	}

	@Override
	public void drawMe(Canvas canvas) {
	}
	
	public void drawLevel(Canvas canvas, Camera camera) {
		if (levels.get(currentLevel) != null)
			levels.get(currentLevel).drawMe(canvas, camera);
	}

	@Override
	public void onDrawOrderChanged(Integer value) {}

	@Override
	public void onVisibleChanged(Boolean value) {}

	@Override
	protected void unloadContent() {}

	@Override
	public void update(Canvas canvas) {
	}

	@Override
	public void onEnabledChange(Boolean value) {}

	@Override
	public void onUpdateOrderChange(Integer value) {}
}
