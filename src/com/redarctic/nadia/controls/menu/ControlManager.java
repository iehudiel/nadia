/*
 * ControlManager.java
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

import com.redarctic.nadia.ext.simplesignalslot.ISignalProvider;
import com.redarctic.nadia.ext.simplesignalslot.ISlotProvider;
import com.redarctic.nadia.ext.simplesignalslot.SignalSlotMap;
import com.redarctic.nadia.ext.simplesignalslot.SlotProviderMethodPair;
import com.redarctic.nadia.baseengine.DrawableObject;
import com.redarctic.nadia.baseengine.SpriteFont;
import com.redarctic.nadia.controls.DirectionalPad;

import android.graphics.Canvas;
import android.graphics.Paint;

public class ControlManager 
extends ArrayList<Control> 
implements DrawableObject, ISignalProvider, ISlotProvider {
	private static final long serialVersionUID = 396736089807979267L;
	
	int selectedControl = 0;
	boolean acceptInput = true;
	static Paint spriteFont;
	
	public static final String SIGNAL_FOCUS_CHANGED = "FOCUS_CHANGED";	
	
	private DirectionalPad gamepad;
	
	public ControlManager() {
		super();
		ControlManager.spriteFont = SpriteFont.getDefaultBlackFont();
	}
	
	public ControlManager(Paint spriteFont) {
		super();
		ControlManager.spriteFont = spriteFont;
	}
	
	public ControlManager(Paint spriteFont, int capacity) {
		super(capacity);
		ControlManager.spriteFont = spriteFont;
	}
	
	public DirectionalPad getGamepad() {
		return gamepad;
	}

	public void setGamepad(DirectionalPad gamepad) {
		if (this.gamepad != null && gamepad != this.gamepad) {
			disconnectAll();
		}
		
		this.gamepad = gamepad;
		SignalSlotMap.fastConnect(this.gamepad, DirectionalPad.SIGNAL_BUTTON_DOWN_RELEASED, 
				SlotProviderMethodPair.create(this, "nextControl"));
		SignalSlotMap.fastConnect(this.gamepad, DirectionalPad.SIGNAL_BUTTON_UP_RELEASED, 
				SlotProviderMethodPair.create(this, "previousControl"));
	}

	private void disconnectAll() {
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

	public static Paint getSpriteFont() {
		return spriteFont;
	}

	public boolean isAcceptInput() {
		return acceptInput;
	}

	public void setAcceptInput(boolean acceptInput) {
		this.acceptInput = acceptInput;
	}
		
	public int getSelectedControl() {
		return selectedControl;
	}

	

	@Override
	public void update(Canvas canvas) {
		if (!this.isEmpty()) {
			for (Control c : this) {
				if (c.isEnabled()) 
					c.update(canvas);
				
				if (c.isHasFocus())
					c.handleInput();
			}
			
			if (!acceptInput)
				return;
		}
	}
	
	public void update(Canvas canvas, DirectionalPad controls) {
		if (this.isEmpty()) {
			return;
		}
		
		for (Control c : this) {
			if (c.isEnabled()) {
				c.update(canvas);
			}
			
			if (c.isHasFocus()) {
				c.handleInput();
			}
		}
		
		if (!acceptInput) {
			return;
		}
		
		if (controls.isDPadUpPressed()) {
			previousControl();
		}
		
		if (controls.isDPadDownPressed()) {
			nextControl();
		}
	}

	@Override
	public void drawMe(Canvas canvas) {
		for (Control c : this) {
			if (c.isVisible()) {
				c.drawMe(canvas);
			}
		}
	}
	
	public void nextControl() {
		if (isEmpty()) {
			return;
		}
		
		int currentControl = this.selectedControl;
		Control tmp = this.get(selectedControl);
		tmp.setHasFocus(false);
		this.set(selectedControl, tmp);
				
		do {
			this.selectedControl++;
			
			if (this.selectedControl == this.size())
				this.selectedControl = 0;
			
			if ( this.get(this.selectedControl).isTabStop() &&
					this.get(this.selectedControl).isEnabled() ) {
				Integer sC = this.selectedControl;
				doEmit(SIGNAL_FOCUS_CHANGED, sC);
				break;
			}
		}
		while ( currentControl != this.selectedControl );
		
		Control tmp2 = this.get(selectedControl);
		tmp2.setHasFocus(true);
		this.set(selectedControl, tmp2);
	}
	
	public void previousControl() {
		if (isEmpty()) {
			return;
		}
		
		int currentControl = this.selectedControl;
		Control tmp = this.get(selectedControl);
		tmp.setHasFocus(false);
		this.set(this.selectedControl, tmp);
		
		do {
			this.selectedControl--;
			
			if ( this.selectedControl < 0 ) {
				this.selectedControl = size() - 1;
			}
			
			if ( this.get(this.selectedControl).isTabStop() &&
					this.get(this.selectedControl).isEnabled()) {
				Integer sC = this.selectedControl;
				doEmit(SIGNAL_FOCUS_CHANGED, sC);
				break;
			}
		}
		while (currentControl != this.selectedControl);
		
		Control tmp2 = this.get(this.selectedControl);
		tmp2.setHasFocus(true);
		this.set(this.selectedControl, tmp2);
	}
	
	public void doEmit(String signalName, Object data) {
		SignalSlotMap.fastEmit(this, signalName, data);
	}
	

	@Override
	public void initialize() {
		
	}
}
