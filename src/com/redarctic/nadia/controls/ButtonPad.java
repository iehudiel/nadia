/*
 * ButtonPad.java
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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.redarctic.nadia.ext.simplesignalslot.ISignalProvider;
import com.redarctic.nadia.ext.simplesignalslot.ISlotProvider;
import com.redarctic.nadia.ext.simplesignalslot.SignalSlotMap;
import com.redarctic.nadia.baseengine.DrawableObject;
import com.redarctic.nadia.collision.Circle;

public class ButtonPad 
extends Circle implements DrawableObject, OnTouchListener, ISignalProvider, ISlotProvider {
	
	public enum PadType {
		DPAD_UP, DPAD_DOWN, DPAD_LEFT, DPAD_RIGHT,
		A, B, X, Y
	}
	
	public static final String SIGNAL_BUTTON_PRESSED = "BUTTON_PRESSED";
	public static final String SIGNAL_BUTTON_RELEASED = "BUTTON_RELEASED";
	
	private Bitmap buttonImage;
	private Bitmap buttonHighlightImage;
	private boolean fPreviousPressed;
	private boolean fIsPressed;
	private PadType padType;
	
	public ButtonPad(Point center, int radius) throws NullPointerException {
		super(center, radius);
		buttonImage = null;
		initialize();
		setPadType(null);
	}
	
	public ButtonPad(int x, int y, int radius) {
		super(x, y, radius);
		buttonImage = null;
		initialize();
		setPadType(null);
	}
	
	public ButtonPad(Bitmap buttonImage, Point center, int radius) 
	throws NullPointerException {
		super(center, radius);
		initialize();
		if ( buttonImage != null ) 
			this.buttonImage = buttonImage;
		else
			throw new NullPointerException("There must be passed an " +
					"image as parameter");
	}
	
	public ButtonPad(Bitmap buttonImage, int x, int y, int radius) 
	throws NullPointerException {
		super(x, y, radius);
		initialize();
		
		if( buttonImage != null ) 
			this.buttonImage = buttonImage;
		else 
			throw new NullPointerException("There must be passed an " +
					"image as parameter");
	}
	
	public ButtonPad(Bitmap buttonImage, Point center, int radius, PadType padType) 
	throws NullPointerException {
		super(center, radius);
		initialize();
		
		this.setPadType(padType);
		
		if ( buttonImage != null ) 
			this.buttonImage = buttonImage;
		else
			throw new NullPointerException("There must be passed an " +
					"image as parameter");
	}
	
	
	
	public ButtonPad(Bitmap buttonImage, int x, int y, int radius, PadType padType) 
	throws NullPointerException {
		super(x, y, radius);
		initialize();
		
		this.setPadType(padType);
				
		if( buttonImage != null ) 
			this.buttonImage = buttonImage;
		else 
			throw new NullPointerException("There must be passed an " +
					"image as parameter");
	}
	
	public void destroy() {
		this.buttonImage = null;
		this.buttonHighlightImage = null;		
	}
	
	public Bitmap getButtonImage() {
		return buttonImage;
	}

	public void setButtonImage(Bitmap buttonImage) 
	throws NullPointerException {
		if (buttonImage != null) 
			this.buttonImage = buttonImage;
		else
			throw new NullPointerException("There must be passed an " +
					"image as parameter");
	}
	
	public Bitmap getButtonHighlightImage() {
		return buttonHighlightImage;
	}

	public void setButtonHighlightImage(Bitmap buttonHighlightImage)
	throws NullPointerException {
		if ( buttonHighlightImage != null ) {
			this.buttonHighlightImage = buttonHighlightImage;
		}
		else {
			Log.e("ButtonPad.setButtonHighlightImage", "There must be passed an " +
					"image as parameter");
			throw new NullPointerException("There must be passed an " +
					"image as parameter");
		}
	}
	
	@Override
	public synchronized void update(Canvas canvas) {
		if ( !this.fIsPressed ) { 
			if ( this.fPreviousPressed ) {
				//Log.v(SIGNAL_BUTTON_RELEASED, "The button " + this.padType + " has been released");
				this.doEmit(ButtonPad.SIGNAL_BUTTON_RELEASED, Boolean.FALSE);
			}
		}
		else {
			this.doEmit(ButtonPad.SIGNAL_BUTTON_PRESSED, Boolean.TRUE);
		}
		this.fPreviousPressed = this.fIsPressed;
	}
	

	@Override
	public void drawMe(Canvas canvas) {
		float left, top;
		
		left = this.getX() - this.getRadius();
		top = this.getY() - this.getRadius();
		
		if ( this.buttonImage != null ) {
			if ( !this.fIsPressed ) {
				canvas.drawBitmap(this.buttonImage, left, top, null);				
			}
			else if ( this.fIsPressed && this.buttonHighlightImage == null ) {
				canvas.drawBitmap(this.buttonHighlightImage, left, top, null);				
			}
			else if ( this.fIsPressed && this.buttonHighlightImage != null ) {
				canvas.drawBitmap(this.buttonHighlightImage, left, top, null);
			}
		}
		
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch( event.getAction() ) {
		case MotionEvent.ACTION_DOWN: {
			Point ptCurrent = new Point((int)event.getX(), (int)event.getY());
			if(this.intersects(ptCurrent)) {
				this.fIsPressed = true;
			}
			else {
				this.fIsPressed = false;
			}
			break;
		} // END OF MotionEvent.ACTION_DOWN
		case MotionEvent.ACTION_OUTSIDE:
		{
			Point ptCurrent = new Point((int)event.getX(), (int)event.getY());
			if (this.intersects(ptCurrent)) {
				this.fIsPressed = true;
			}
			else {
				this.fIsPressed = false;
			}
			break;
		}
		case MotionEvent.ACTION_UP:
		{
			this.fIsPressed = false;
			break;
		}
		default:
		{
			this.fIsPressed = false;
			break;
		}
		}
		return true;
	}
	
	public boolean isPressed() {
		return fIsPressed;
	}
	
	public boolean isPreviousPressed() {
		return this.fPreviousPressed;
	}
	
	public void setPadType(PadType padType) {
		this.padType = padType;
	}

	public PadType getPadType() {
		return this.padType;
	}
	
	public void doEmit(String signalName, Object data) {
		SignalSlotMap.fastEmit(this, signalName, data);
	}

	@Override
	public void initialize() {
		this.buttonHighlightImage = null;
		this.fPreviousPressed = false;
		this.fIsPressed = false;
	}
}
