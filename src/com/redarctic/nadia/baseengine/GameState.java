/*
 * GameState.java
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

import java.util.ArrayList;

import com.redarctic.nadia.ext.simplesignalslot.ISlotProvider;

import android.graphics.Canvas;

public abstract class GameState extends DrawableGameComponent implements ISlotProvider {
	ArrayList<GameComponent> childComponents;

	public ArrayList<GameComponent> getChildComponents() {
		return childComponents;
	}
	
	GameState tag;
	
	public GameState getTag() {
		return this.tag;
	}
	
	protected GameStateManager stateManager;
	
	public GameState(GameStateManager manager) {
		super();
		this.stateManager = manager;
		this.childComponents = new ArrayList<GameComponent>();
		tag = this;
	}
	
	@Override
	public void initialize() {
		super.initialize();
	}

	@Override
	public void update(Canvas canvas) {
		for (GameComponent component : childComponents) {
			if (component.isEnabled()) {
				component.update(canvas);
			}
		}
	}

	@Override
	public void drawMe(Canvas canvas) {
		DrawableGameComponent drawComponent;
		
		for ( GameComponent component : childComponents ) {
			if (component instanceof DrawableGameComponent) {
				drawComponent = (DrawableGameComponent)component;
				
				if (drawComponent.isVisible()) {
					drawComponent.drawMe(canvas);
				}
			}
		}
	}
	
	protected void show() {
		this.setVisible(true);
		this.setEnabled(true);
		
		for (GameComponent component : childComponents ) {
			component.setEnabled(true);
			
			if (component instanceof DrawableGameComponent) {
				((DrawableGameComponent) component).setVisible(true);
			}
		}
	}
	
	protected void hide() {
		this.setVisible(false);
		this.setEnabled(false);
		
		for (GameComponent component : childComponents ) {
			component.setEnabled(false);
			
			if (component instanceof DrawableGameComponent) {
				((DrawableGameComponent) component).setVisible(false);
			}
		}
	}
	
	public void stateChange(GameState origin) {
		if (origin == this.tag) {
			show();
		}
		else {
			hide();
		}
	}
}
