/*
 * DirectionalPad.java
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

package com.redarctic.nadia.controls;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.redarctic.nadia.ext.simplesignalslot.ISignalProvider;
import com.redarctic.nadia.ext.simplesignalslot.ISlotProvider;
import com.redarctic.nadia.ext.simplesignalslot.SignalSlotMap;
import com.redarctic.nadia.ext.simplesignalslot.SlotProviderMethodPair;
import com.redarctic.nadia.baseengine.DrawableObject;

public class DirectionalPad 
implements DrawableObject, OnTouchListener, ISignalProvider, ISlotProvider {
	final static long INPUT_TIME_WAIT_MSECS = 1000 / 20;
	
	/**
	 * SIGNALS
	 */
	public final static String SIGNAL_BUTTON_UP_PRESSED     =     "BUTTON_UP_PRESSED";
	public final static String SIGNAL_BUTTON_DOWN_PRESSED   =   "BUTTON_DOWN_PRESSED";
	public final static String SIGNAL_BUTTON_LEFT_PRESSED   =   "BUTTON_LEFT_PRESSED";
	public final static String SIGNAL_BUTTON_RIGHT_PRESSED  =  "BUTTON_RIGHT_PRESSED";
	public final static String SIGNAL_BUTTON_ACTION_PRESSED = "BUTTON_ACTION_PRESSED";
	public final static String SIGNAL_BUTTON_CANCEL_PRESSED = "BUTTON_CANCEL_PRESSED";
	public final static String SIGNAL_BUTTON_MENU_PRESSED   =   "BUTTON_MENU_PRESSED";
	
	public final static String SIGNAL_BUTTON_UP_RELEASED     =     "BUTTON_UP_RELEASED";
	public final static String SIGNAL_BUTTON_DOWN_RELEASED   =   "BUTTON_DOWN_RELEASED";
	public final static String SIGNAL_BUTTON_LEFT_RELEASED   =   "BUTTON_LEFT_RELEASED";
	public final static String SIGNAL_BUTTON_RIGHT_RELEASED  =  "BUTTON_RIGHT_RELEASED";
	public final static String SIGNAL_BUTTON_ACTION_RELEASED = "BUTTON_ACTION_RELEASED";
	public final static String SIGNAL_BUTTON_CANCEL_RELEASED = "BUTTON_CANCEL_RELEASED";
	public final static String SIGNAL_BUTTON_MENU_RELEASED   =   "BUTTON_MENU_RELEASED";
	/**
	 * End of SIGNALS
	 */
	
	
	private ButtonPad buttonArrowUp;
	private ButtonPad buttonArrowDown;
	private ButtonPad buttonArrowLeft;
	private ButtonPad buttonArrowRight;
	private ButtonPad buttonAction;
	private ButtonPad buttonCancel;
	private ButtonPad buttonMenu;
	
	final static int DPAD_BUTTON_SIZE = 32;
		
	public DirectionalPad(
			ButtonPad up, 
			ButtonPad down,
			ButtonPad left,
			ButtonPad right,
			ButtonPad action,
			ButtonPad cancel,
			ButtonPad menu) {
		this.setButtonArrowUp(up);
		this.setButtonArrowDown(down);
		this.setButtonArrowLeft(left);
		this.setButtonArrowRight(right);
		this.setButtonAction(action);
		this.setButtonCancel(cancel);
		this.setButtonMenu(menu);
	}
	
	public void destroy() {
		this.buttonArrowUp.destroy();
		this.buttonArrowDown.destroy();
		this.buttonArrowLeft.destroy();
		this.buttonArrowRight.destroy();
		this.buttonAction.destroy();
		this.buttonCancel.destroy();
		this.buttonMenu.destroy();
		
		this.buttonArrowUp = null;
		this.buttonArrowDown = null;
		this.buttonArrowLeft = null;
		this.buttonArrowRight = null;
		this.buttonAction = null;
		this.buttonCancel = null;
		this.buttonMenu = null;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		try {
			Thread.sleep(INPUT_TIME_WAIT_MSECS);
		} 
		catch (InterruptedException e) {			
			e.printStackTrace();
		}
		// TODO Improve this algorithm (many calculations)
		this.buttonArrowUp.onTouch(v, event);		
		this.buttonArrowDown.onTouch(v, event);		
		this.buttonArrowLeft.onTouch(v, event);		
		this.buttonArrowRight.onTouch(v, event);		
		this.buttonAction.onTouch(v, event);		
		this.buttonCancel.onTouch(v, event);		
		this.buttonMenu.onTouch(v, event);
		
		//doEmitAll();
		
		return true;
	}
	
	/*private void doEmitAll() {
		if (this.buttonArrowUp.isPressed() == true) {
			doEmit(SIGNAL_BUTTON_UP_PRESSED);
		}
		else if (this.buttonArrowUp.isPressed() == false) {
			doEmit(SIGNAL_BUTTON_UP_RELEASED);
		}
		if (this.buttonArrowDown.isPressed() == true) {
			doEmit(SIGNAL_BUTTON_DOWN_PRESSED);
		}
		else if (this.buttonArrowDown.isPressed() == false) {
			doEmit(SIGNAL_BUTTON_DOWN_RELEASED);
		}
		if (this.buttonArrowLeft.isPressed() == true) {
			doEmit(SIGNAL_BUTTON_LEFT_PRESSED);
		}
		else if (this.buttonArrowLeft.isPressed() == false) {
			doEmit(SIGNAL_BUTTON_LEFT_RELEASED);
		}
		if (this.buttonArrowRight.isPressed() == true) {
			doEmit(SIGNAL_BUTTON_RIGHT_PRESSED);
		}
		else if (this.buttonArrowRight.isPressed() == false) {
			doEmit(SIGNAL_BUTTON_RIGHT_RELEASED);
		}
		if (this.buttonAction.isPressed() == true) {
			doEmit(SIGNAL_BUTTON_ACTION_PRESSED);
		}
		else if (this.buttonAction.isPressed() == false) {
			doEmit(SIGNAL_BUTTON_ACTION_RELEASED);
		}
		if (this.buttonCancel.isPressed() == true) {
			doEmit(SIGNAL_BUTTON_CANCEL_PRESSED);
		}
		else if (this.buttonCancel.isPressed() == false) {
			doEmit(SIGNAL_BUTTON_CANCEL_RELEASED);
		}
		if (this.buttonMenu.isPressed() == true) {
			doEmit(SIGNAL_BUTTON_MENU_PRESSED);
		}
		else if (this.buttonMenu.isPressed() == false) {
			doEmit(SIGNAL_BUTTON_MENU_RELEASED);
		}
	}*/

	@Override
	public synchronized void update(Canvas canvas) {
		int bottom = canvas.getHeight() - 64;
		int right = canvas.getWidth() - 64;
		
		final int INIT_PAD_LEFT = 80;
		
		Point ptCenterArrowUp = new Point(INIT_PAD_LEFT, bottom - (DPAD_BUTTON_SIZE * 2 ) );
		Point ptCenterArrowDown = new Point(INIT_PAD_LEFT, 
				bottom );
		
		Point ptCenterArrowLeft = new Point(INIT_PAD_LEFT - DPAD_BUTTON_SIZE, 
				bottom - DPAD_BUTTON_SIZE);
		Point ptCenterArrowRight = new Point(INIT_PAD_LEFT + DPAD_BUTTON_SIZE, 
				bottom - DPAD_BUTTON_SIZE);
		Point ptCenterAction = new Point(right, bottom);
		Point ptCenterCancel = new Point(right - 64 -10, bottom);
		Point ptCenterMenu = new Point(right - 128 - 20,  bottom);
		
		this.buttonArrowUp.setCenter(ptCenterArrowUp);
		this.buttonArrowDown.setCenter(ptCenterArrowDown);
		this.buttonArrowLeft.setCenter(ptCenterArrowLeft);
		this.buttonArrowRight.setCenter(ptCenterArrowRight);
		this.buttonAction.setCenter(ptCenterAction);
		this.buttonCancel.setCenter(ptCenterCancel);
		this.buttonMenu.setCenter(ptCenterMenu);
		
		this.buttonArrowUp.update(canvas);
		this.buttonArrowDown.update(canvas);
		this.buttonArrowLeft.update(canvas);
		this.buttonArrowRight.update(canvas);
		this.buttonAction.update(canvas);
		this.buttonCancel.update(canvas);
		this.buttonMenu.update(canvas);
	}

	@Override
	public synchronized void drawMe(Canvas canvas) {
		this.buttonArrowUp.drawMe(canvas);
		this.buttonArrowDown.drawMe(canvas);
		this.buttonArrowLeft.drawMe(canvas);
		this.buttonArrowRight.drawMe(canvas);
		this.buttonAction.drawMe(canvas);
		this.buttonCancel.drawMe(canvas);
		this.buttonMenu.drawMe(canvas);
	}
	
	public ButtonPad getButtonArrowUp() {
		return buttonArrowUp;
	}

	public void setButtonArrowUp(ButtonPad buttonArrowUp) {
		if (this.buttonArrowUp != null)
			SignalSlotMap.fastDisconnect((ISignalProvider)this.buttonArrowUp);
		this.buttonArrowUp = buttonArrowUp;
		SignalSlotMap.fastConnect(this.buttonArrowUp, ButtonPad.SIGNAL_BUTTON_PRESSED, 
				SlotProviderMethodPair.create(this, "doEmitArrowUpPressed", Boolean.class));
		SignalSlotMap.fastConnect(this.buttonArrowUp, ButtonPad.SIGNAL_BUTTON_RELEASED, 
				SlotProviderMethodPair.create(this, "doEmitArrowUpReleased", Boolean.class));
	}
	
	public void doEmitArrowUpPressed(Boolean value) {
		doEmit(SIGNAL_BUTTON_UP_PRESSED);	
	}
	
	public void doEmitArrowUpReleased(Boolean value) {
		doEmit(SIGNAL_BUTTON_UP_RELEASED);
	}
	

	public ButtonPad getButtonArrowDown() {
		return buttonArrowDown;
	}

	public void setButtonArrowDown(ButtonPad buttonArrowDown) {
		if (this.buttonArrowDown != null)
			SignalSlotMap.fastDisconnect((ISignalProvider)this.buttonArrowDown);
		this.buttonArrowDown = buttonArrowDown;
		SignalSlotMap.fastConnect(this.buttonArrowDown, ButtonPad.SIGNAL_BUTTON_PRESSED, 
				SlotProviderMethodPair.create(this, "doEmitArrowDownPressed", Boolean.class));
		SignalSlotMap.fastConnect(this.buttonArrowDown, ButtonPad.SIGNAL_BUTTON_RELEASED, 
				SlotProviderMethodPair.create(this, "doEmitArrowDownReleased", Boolean.class));
	}
	
	public void doEmitArrowDownPressed(Boolean value) {
		doEmit(SIGNAL_BUTTON_DOWN_PRESSED);	
	}
	
	public void doEmitArrowDownReleased(Boolean value) {
		doEmit(SIGNAL_BUTTON_DOWN_RELEASED);
	}


	public ButtonPad getButtonArrowLeft() {
		return buttonArrowLeft;
	}

	public void setButtonArrowLeft(ButtonPad buttonArrowLeft) {
		if (this.buttonArrowLeft != null)
			SignalSlotMap.fastDisconnect((ISignalProvider)this.buttonArrowLeft);
		this.buttonArrowLeft = buttonArrowLeft;
		SignalSlotMap.fastConnect(this.buttonArrowLeft, ButtonPad.SIGNAL_BUTTON_PRESSED, 
				SlotProviderMethodPair.create(this, "doEmitArrowLeftPressed", Boolean.class));
		SignalSlotMap.fastConnect(this.buttonArrowLeft, ButtonPad.SIGNAL_BUTTON_RELEASED, 
				SlotProviderMethodPair.create(this, "doEmitArrowLeftReleased", Boolean.class));
	}
	
	public void doEmitArrowLeftPressed(Boolean value) {
		doEmit(SIGNAL_BUTTON_LEFT_PRESSED);	
	}
	
	public void doEmitArrowLeftReleased(Boolean value) {
		doEmit(SIGNAL_BUTTON_LEFT_RELEASED);
	}


	public ButtonPad getButtonArrowRight() {
		return buttonArrowRight;
	}


	public void setButtonArrowRight(ButtonPad buttonArrowRight) {
		if (this.buttonArrowRight != null)
			SignalSlotMap.fastDisconnect((ISignalProvider)this.buttonArrowRight);
		this.buttonArrowRight = buttonArrowRight;
		SignalSlotMap.fastConnect(this.buttonArrowRight, ButtonPad.SIGNAL_BUTTON_PRESSED, 
				SlotProviderMethodPair.create(this, "doEmitArrowRightPressed", Boolean.class));
		SignalSlotMap.fastConnect(this.buttonArrowRight, ButtonPad.SIGNAL_BUTTON_RELEASED, 
				SlotProviderMethodPair.create(this, "doEmitArrowRightReleased", Boolean.class));
	}
	
	public void doEmitArrowRightPressed(Boolean value) {
		doEmit(SIGNAL_BUTTON_RIGHT_PRESSED);	
	}
	
	public void doEmitArrowRightReleased(Boolean value) {
		doEmit(SIGNAL_BUTTON_RIGHT_RELEASED);
	}


	public ButtonPad getButtonAction() {
		return buttonAction;
	}

	public void setButtonAction(ButtonPad buttonAction) {
		if (this.buttonAction != null)
			SignalSlotMap.fastDisconnect((ISignalProvider)this.buttonAction);
		this.buttonAction = buttonAction;
		SignalSlotMap.fastConnect(this.buttonAction, ButtonPad.SIGNAL_BUTTON_PRESSED, 
				SlotProviderMethodPair.create(this, "doEmitActionPressed", Boolean.class));
		SignalSlotMap.fastConnect(this.buttonAction, ButtonPad.SIGNAL_BUTTON_RELEASED, 
				SlotProviderMethodPair.create(this, "doEmitActionReleased", Boolean.class));
	}
	
	public void doEmitActionPressed(Boolean value) {
		doEmit(SIGNAL_BUTTON_ACTION_PRESSED);	
	}
	
	public void doEmitActionReleased(Boolean value) {
		doEmit(SIGNAL_BUTTON_ACTION_RELEASED);
	}
	
	
	public ButtonPad getButtonCancel() {
		return buttonCancel;
	}

	public void setButtonCancel(ButtonPad buttonCancel) {
		if (this.buttonCancel != null)
			SignalSlotMap.fastDisconnect((ISignalProvider)this.buttonCancel);
		this.buttonCancel = buttonCancel;
		SignalSlotMap.fastConnect(this.buttonCancel, ButtonPad.SIGNAL_BUTTON_PRESSED, 
				SlotProviderMethodPair.create(this, "doEmitCancelPressed", Boolean.class));
		SignalSlotMap.fastConnect(this.buttonCancel, ButtonPad.SIGNAL_BUTTON_RELEASED, 
				SlotProviderMethodPair.create(this, "doEmitCancelReleased", Boolean.class));
	}
	
	public void doEmitCancelPressed(Boolean value) {
		doEmit(SIGNAL_BUTTON_CANCEL_PRESSED);	
	}
	
	public void doEmitCancelReleased(Boolean value) {
		doEmit(SIGNAL_BUTTON_CANCEL_RELEASED);
	}
	
	
	public void setButtonMenu(ButtonPad buttonMenu) {
		if (this.buttonMenu != null)
			SignalSlotMap.fastDisconnect((ISignalProvider)this.buttonMenu);
		this.buttonMenu = buttonMenu;
		SignalSlotMap.fastConnect(this.buttonMenu, ButtonPad.SIGNAL_BUTTON_PRESSED, 
				SlotProviderMethodPair.create(this, "doEmitMenuPressed", Boolean.class));
		SignalSlotMap.fastConnect(this.buttonMenu, ButtonPad.SIGNAL_BUTTON_RELEASED, 
				SlotProviderMethodPair.create(this, "doEmitMenuReleased", Boolean.class));
	}

	public ButtonPad getButtonMenu() {
		return buttonMenu;
	}
	
	public void doEmitMenuPressed(Boolean value) {
		doEmit(SIGNAL_BUTTON_MENU_PRESSED);	
	}
	
	public void doEmitMenuReleased(Boolean value) {
		doEmit(SIGNAL_BUTTON_MENU_RELEASED);
	}
	

	public boolean isDPadUpPressed() {
		return this.buttonArrowUp.isPressed();
	}
	
	public boolean isDPadDownPressed() {
		return this.buttonArrowDown.isPressed();
	}
	
	public boolean isDPadLeftPressed() {
		return this.buttonArrowLeft.isPressed();
	}
	
	public boolean isDPadRightPressed() {
		return this.buttonArrowRight.isPressed();
	}
	
	public boolean isActionPressed() {
		return this.buttonAction.isPressed();
	}
	
	public boolean isCancelPressed() {
		return this.buttonCancel.isPressed();
	}
	
	public boolean isMenuPressed() {
		return this.buttonMenu.isPressed();
	}
	
	private void doEmit(String signalName) {
		//SignalSlotMap.emit(this, signalName);
		SignalSlotMap.fastEmit(this, signalName);
	}

	@Override
	public void initialize() {		
	}
}
