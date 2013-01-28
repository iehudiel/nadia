/*
 * Menu.java
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

import com.redarctic.nadia.ext.simplesignalslot.SignalSlotMap;
import com.redarctic.nadia.ext.simplesignalslot.SlotProviderMethodPair;
import com.redarctic.nadia.baseengine.ColorPallete;
import com.redarctic.nadia.baseengine.SpriteFont;
import com.redarctic.nadia.controls.DirectionalPad;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Menu extends ControlManager {
	private static final long serialVersionUID = -5944428321944028960L;
	
	private ControlBorder controlBorder;
	private Point initPosition;
	private PictureBox arrowImage;
	
	private int maxItemWidth = 0;
	
	public Menu(DirectionalPad gamepad) {
		super();
		setGamepad(gamepad);
	}
	
	public Menu(DirectionalPad gamepad, Point initPosition) {
		super();
		setGamepad(gamepad);
		setInitPosition(initPosition);
	}
	
	public Menu(Paint spriteFont, DirectionalPad gamepad, Point initPosition) {
		super(spriteFont);
		setGamepad(gamepad);
		setInitPosition(initPosition);
	}
	
	///
	/// GETTERS AND SETTERS
	///	
	public ControlBorder getControlBorder() {
		return controlBorder;
	}

	public void setControlBorder(ControlBorder controlBorder) {
		this.controlBorder = controlBorder;
	}

	public Point getInitPosition() {
		return initPosition;
	}

	public void setInitPosition(Point initPosition) {
		this.initPosition = initPosition;
	}

	public void setArrowImage(Bitmap arrowImage) {
		if (arrowImage != null) {
			if (this.arrowImage != null) {
				this.arrowImage = null;
			}
			this.arrowImage = new PictureBox(arrowImage, 
					new Rect(0, 0, arrowImage.getWidth(), arrowImage.getHeight()));
		}
	}
	///
	/// End of GETTERS AND SETTERS
	///
	

	public void calculate() {
		if (this.initPosition != null && this.arrowImage != null) {			
			Point posit = new Point(initPosition);
			Point p1 = new Point(posit);
			p1.x -= 10;
			p1.y -= 20;
			
			for (Control c : this) {
				if (c instanceof LinkLabel) {
					if (c.getSize().x > maxItemWidth) {
						maxItemWidth = c.getSize().x;
					}
					
					Point pos = new Point(posit);
					c.setPosition(pos);
					posit.y += (c.getSize().y + 5);
					pos = null;
				}
			}
			
			Point p2 = new Point();
			p2.x = (maxItemWidth + this.arrowImage.getImage().getWidth() + 20);
			p2.y = posit.y - p1.y + 10;
			
			this.controlBorder = new ControlBorder(p1, p2);
		}
	}
	
	public void calculate(Point initPosition, Bitmap arrowImage) {
		if (initPosition != null && arrowImage != null) {
			this.setInitPosition(initPosition);
			this.setArrowImage(arrowImage);
			
			calculate();
		}
		else {
			throw new NullPointerException();
		}
	}

	@Override
	public void update(Canvas canvas) {
		super.update(canvas);
	}

	@Override
	public void drawMe(Canvas canvas) {		
		if (this.controlBorder != null) {
			this.controlBorder.drawMe(canvas);
		}
		if (this.arrowImage != null) {
			this.arrowImage.drawMe(canvas);
		}
		super.drawMe(canvas);
	}
	
	public void initConnections() {
		SignalSlotMap.fastConnect(this, ControlManager.SIGNAL_FOCUS_CHANGED, 
				SlotProviderMethodPair.create(this, "focusChanged", Integer.class));
	}
	
	public void focusChanged(Integer selectedControl) {
		if (this.arrowImage != null) {
			if (this.isEmpty())
				return;
			
			if (selectedControl >= this.size())
				selectedControl = 0;
			
			Control control = get(selectedControl);
			if (control instanceof LinkLabel) {
				control.setHasFocus(true);
				this.set(selectedControl, control);
				Point position = new Point(control.getPosition().x
						+ this.maxItemWidth + 10, control.getPosition().y
						- (this.arrowImage.getSourceRect().height() >> 1));
				this.arrowImage.setPosition(position);
			}
		}
	}
	
	public void disconnectAll() {
		for (Control c : this) {
			if (c instanceof LinkLabel)
				SignalSlotMap.fastDisconnect((LinkLabel)c);
		}
	}
	
	public void add(String member) {
		LinkLabel element = new LinkLabel();
		element.setText(member);
		element.setSize(SpriteFont.getMeasureString(member));
		element.setColor(ColorPallete.COLOR_MENU_TEXT_LIGHT);
		element.setGamepad(getGamepad());
		this.add(element);
	}
}
