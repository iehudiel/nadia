/*
 * BaseGameState.java
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

import com.redarctic.nadia.controls.DirectionalPad;
import com.redarctic.nadia.controls.menu.ControlManager;

import android.graphics.Canvas;
import android.graphics.Color;


public abstract class BaseGameState extends GameState {

	protected ControlManager controlManager;
	
	
	public BaseGameState(GameStateManager manager) {
		super(manager);
	}	

	@Override
	public void initialize() {		
		super.initialize();
		
		
	}

	@Override
	public void update(Canvas canvas) {
		super.update(canvas);
	}

	@Override
	public void drawMe(Canvas canvas) {
		super.drawMe(canvas);
	}

	@Override
	protected void loadContent() {
		if (controlManager == null)
			this.setControlManager(new ControlManager(SpriteFont.getDefaultFont(Color.LTGRAY)));
	}

	public ControlManager getControlManager() {
		return controlManager;
	}

	public void setControlManager(ControlManager controlManager) {
		this.controlManager = controlManager;
	}
	
	public void setGamepad(DirectionalPad gamepad) {
		this.controlManager.setGamepad(gamepad);
	}
	
	public DirectionalPad getGamepad() {
		return this.controlManager.getGamepad();
	}
}
