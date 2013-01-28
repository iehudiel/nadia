/*
 * GameStateManager.java
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

import java.util.Stack;

import com.redarctic.nadia.ext.simplesignalslot.ISignalProvider;
import com.redarctic.nadia.ext.simplesignalslot.ISlotProvider;
import com.redarctic.nadia.ext.simplesignalslot.SignalSlotMap;
import com.redarctic.nadia.ext.simplesignalslot.SlotProviderMethodPair;

import android.graphics.Canvas;

public class GameStateManager extends GameComponent implements ISignalProvider, ISlotProvider {
	
	public static final String SIGNAL_STATE_CHANGE = "STATE_CHANGE";
	
	final int START_DRAW_ORDER = 5000;
	final int DRAW_ORDER_INC = 100;
	
	int drawOrder;
	
	private Stack<GameState> gameStates = new Stack<GameState>();
	
	public GameState getCurrentState() {
		return gameStates.peek();
	}
	
	public GameStateManager() {
		super();
		this.drawOrder = START_DRAW_ORDER;
	}
	
	@Override
	public void initialize() {
	}

	@Override
	public void update(Canvas canvas) {		
	}
	
	public void popState() {
		if (gameStates.size() > 0) {
			removeState();
			drawOrder -= DRAW_ORDER_INC;
			
			SignalSlotMap.fastEmit(this, SIGNAL_STATE_CHANGE, (GameState)null);
		}
	}
	
	private void removeState() {
		GameState state = gameStates.peek();
		SignalSlotMap.fastDisconnect(this, 
				SlotProviderMethodPair.create(state, "stateChange", GameState.class));
		// TODO: make something with the component states in the game (Remove)
		gameStates.pop();
	}
	
	private void addState(GameState newState) {
		gameStates.push(newState);
		
		// TODO: Add the components to the game itself
		SignalSlotMap.fastConnect(this, SIGNAL_STATE_CHANGE, 
				SlotProviderMethodPair.create(newState, "stateChange", GameState.class));
	}
	
	public void pushState(GameState newState) {
		drawOrder += DRAW_ORDER_INC;
		newState.setDrawOrder(drawOrder);
		
		addState(newState);
		
		SignalSlotMap.fastEmit(this, SIGNAL_STATE_CHANGE, newState);
	}
	
	public void changeState(GameState newState) {
		while (!gameStates.isEmpty())
			removeState();
		
		newState.setDrawOrder(START_DRAW_ORDER);
		drawOrder = START_DRAW_ORDER;
		
		addState(newState);
		
		SignalSlotMap.fastEmit(this, SIGNAL_STATE_CHANGE, newState);
	}

	@Override
	public void onEnabledChange(Boolean value) {
	}

	@Override
	public void onUpdateOrderChange(Integer value) {		
	}

}
