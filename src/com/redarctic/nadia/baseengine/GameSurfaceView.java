/*
 * GameSurfaceView.java
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

import com.redarctic.nadia.controls.DirectionalPad;
import com.redarctic.nadia.ext.simplesignalslot.ISlotProvider;
import com.redarctic.nadia.ext.simplesignalslot.SignalSlotMap;
import com.redarctic.nadia.ext.simplesignalslot.SlotProviderMethodPair;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView 
extends SurfaceView 
implements Runnable, DrawableObject, ISlotProvider {
	Thread t;
	SurfaceHolder shHolder;
	boolean isItOK = false;
	
	protected ArrayList<GameComponent> components = new ArrayList<GameComponent>();
	GameStateManager stateManager;
	GameState currentState;
	
	DirectionalPad gamePad;
	
	public GameSurfaceView(Context context) {
		super(context);
		this.initialize();
	}
	
	public GameSurfaceView(Context context, DirectionalPad gamePad) {
		super(context);
		if (null != gamePad)
			this.gamePad = gamePad;
		else
			throw new NullPointerException();
		this.initialize();
	}

	@Override
	public void initialize() {
		this.shHolder = getHolder();
		
		this.initializeStates();
	}

	@Override
	public void update(Canvas canvas) {
		if (null != this.gamePad) {
			this.gamePad.update(canvas);
		}
		
		for (GameComponent component : this.getComponents()) {
			component.update(canvas);
		}
		this.currentState.update(canvas);
	}

	@Override
	public void drawMe(Canvas canvas) {
		canvas.drawARGB(255, 255, 255, 255);
		
		this.currentState.drawMe(canvas);
		
		if (null != this.gamePad)
			this.gamePad.drawMe(canvas);
	}

	@Override
	public void run() {
		while (true == this.isItOK) {
			if (!this.shHolder.getSurface().isValid()) {
				continue;
			}
			
			/*
			 * BEGIN TO DRAW 
			 */
			Canvas c = this.shHolder.lockCanvas();
			
			this.update(c);
			this.drawMe(c);
			
			/*
			 * END DRAW 
			 */
			this.shHolder.unlockCanvasAndPost(c);
		}
	}
	
	public void resume() {
		this.isItOK = true;
	}
	
	public void pause() {
		this.isItOK = false;
	}
	
	public void setCurrentState(GameState state) {
		this.currentState = state;
	}

	public ArrayList<GameComponent> getComponents() {
		return this.components;
	}
	
	public DirectionalPad getGamePad() {
		return gamePad;
	}

	public void setGamePad(DirectionalPad gamePad) {
		this.gamePad = gamePad;
	}

	private void initializeStates() {
		if (null == this.stateManager) {
			this.stateManager = new GameStateManager();
			
			if (null == this.getComponents()) {
				this.components = new ArrayList<GameComponent>();
			}
			this.components.add(this.stateManager);
			
			for (GameComponent component : this.getComponents()) {
				component.initialize();
			}
			
			SignalSlotMap.fastConnect(this.stateManager, GameStateManager.SIGNAL_STATE_CHANGE, 
					SlotProviderMethodPair.create(this, "setCurrentState", GameState.class));
		}
	}
}
