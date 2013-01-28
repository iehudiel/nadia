/*
 * SimpleConversation.java
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

package com.redarctic.nadia.controls.menu;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.redarctic.nadia.ext.simplesignalslot.ISignalProvider;
import com.redarctic.nadia.ext.simplesignalslot.ISlotProvider;
import com.redarctic.nadia.ext.simplesignalslot.SignalSlotMap;
import com.redarctic.nadia.ext.simplesignalslot.SlotProviderMethodPair;
import com.redarctic.nadia.baseengine.DrawableObject;
import com.redarctic.nadia.controls.DirectionalPad;
import com.redarctic.nadia.controls.menu.CharacterDialog.DockPlace;

import android.graphics.Canvas;
import android.graphics.Point;

public class SimpleConversation<E extends CharacterDialog> 
extends ConcurrentLinkedQueue<E>
implements ISignalProvider, ISlotProvider, DrawableObject {
	private static final long serialVersionUID = -6476080508195188243L;
	private static final int DEFAULT_NUM_ROWS = 5;
	
	public static final String SIGNAL_CONVERSATION_ENDED = "CONVERSATION_ENDED";
	
	private DirectionalPad gamepad;
	private Point screenSize;
	
	public SimpleConversation(DirectionalPad gamepad, Point screenSize) {
		super();
		setGamepad(gamepad);
		this.screenSize = screenSize;
	}
	
	public SimpleConversation(DirectionalPad gamepad, Point screenSize, ArrayList<E> dialogs) {
		super();
		setGamepad(gamepad);
		this.screenSize = screenSize;
		addAll(dialogs);
	}
	
	public synchronized boolean add(String message) {
		String characterName = "*";
		boolean added = false;
		
		CharacterDialog dialog = new CharacterDialog(characterName, message);
		dialog.setDockPlace(DockPlace.UP);
		dialog.setWidth(this.screenSize.x);
		dialog.setNumRows(DEFAULT_NUM_ROWS);
		dialog.initialize();
		added = this.add((E) dialog);
		
		
		return added;
	}
	
	public synchronized boolean add(String characterName, String message) {
		boolean added = false;
		CharacterDialog dialog = new CharacterDialog(characterName, message);
		dialog.setDockPlace(DockPlace.UP);
		dialog.setWidth(this.screenSize.x);
		dialog.setNumRows(DEFAULT_NUM_ROWS);
		dialog.initialize();
		added = this.add((E)dialog);
		
		return added;
	}
	
	public synchronized void addAll(ArrayList<E> dialogs) {
		for (E dialog : dialogs) {
			if (!add(dialog)) {
				continue;
			}
		}
	}
	
	public DirectionalPad getGamepad() {
		return gamepad;
	}

	public synchronized void setGamepad(DirectionalPad gamepad) {
		if (this.gamepad != null && gamepad != this.gamepad) {
			disconnectOldGamepad();
		}
				
		if (gamepad != null) 
			this.gamepad = gamepad;
	}
	
	private synchronized void disconnectOldGamepad() {
		try {
			SignalSlotMap.fastDisconnect((ISignalProvider)this.gamepad);
			SignalSlotMap.fastDisconnect((ISignalProvider)this.gamepad.getButtonAction());
			SignalSlotMap.fastDisconnect((ISignalProvider)this.gamepad.getButtonArrowDown());
			SignalSlotMap.fastDisconnect((ISignalProvider)this.gamepad.getButtonArrowLeft());
			SignalSlotMap.fastDisconnect((ISignalProvider)this.gamepad.getButtonArrowRight());
			SignalSlotMap.fastDisconnect((ISignalProvider)this.gamepad.getButtonArrowUp());
			SignalSlotMap.fastDisconnect((ISignalProvider)this.gamepad.getButtonCancel());
			SignalSlotMap.fastDisconnect((ISignalProvider)this.gamepad.getButtonMenu());
		}
		catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	private void disconnectHead() {
		try {
			SignalSlotMap.fastDisconnect((ISignalProvider)this.peek());
		}
		catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize() {	
	}
	
	public synchronized void initConnections() {
		if (!this.isEmpty()) {
			SignalSlotMap.fastConnect(this.getGamepad(), DirectionalPad.SIGNAL_BUTTON_ACTION_RELEASED, 
					SlotProviderMethodPair.create(this, "nextDialog"));
		}
	}

	@Override
	public synchronized void update(Canvas canvas) {
		if (!this.isEmpty())
			this.peek().update(canvas);
	}

	@Override
	public synchronized void drawMe(Canvas canvas) {
		if (!this.isEmpty())
			this.peek().drawMe(canvas);
	}
	
	public void nextDialog() {
		if (!isEmpty()) {
			disconnectHead();
			
			poll();
			SignalSlotMap.fastConnect(this.getGamepad(), DirectionalPad.SIGNAL_BUTTON_ACTION_RELEASED, 
					SlotProviderMethodPair.create(this, "nextDialog"));
		}
		else {
			SignalSlotMap.fastEmit(this, SIGNAL_CONVERSATION_ENDED);
		}
	}
}
