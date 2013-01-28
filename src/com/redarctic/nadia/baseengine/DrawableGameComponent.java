/*
 * DrawableGameComponent.java
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

import com.redarctic.nadia.ext.simplesignalslot.ISignalProvider;
import com.redarctic.nadia.ext.simplesignalslot.ISlotProvider;
import com.redarctic.nadia.ext.simplesignalslot.SignalSlotMap;
import com.redarctic.nadia.ext.simplesignalslot.SlotProviderMethodPair;

public abstract class DrawableGameComponent 
extends GameComponent implements DrawableObject, ISignalProvider, ISlotProvider {
	private int drawOrder;
	private boolean visible;	

	public static final String SIGNAL_DRAW_ORDER_CHANGED = "DRAW_ORDER_CHANGED";
	public static final String SIGNAL_VISIBLE_CHANGED = "VISIBLE_CHANGED";
	
	public DrawableGameComponent() {
		super();
		this.drawOrder = 0;
		this.visible = true;
		
		this.initialize();		
		//this.loadContent();
		
		SignalSlotMap.fastConnect(this, SIGNAL_DRAW_ORDER_CHANGED, 
				SlotProviderMethodPair.create(this, "onDrawOrderChanged", Integer.class));
		SignalSlotMap.fastConnect(this, SIGNAL_VISIBLE_CHANGED, 
				SlotProviderMethodPair.create(this, "onVisibleChanged", Boolean.class));
	}
	
	public void initialize() {		
	}
	
	public int getDrawOrder() {
		return drawOrder;
	}

	public void setDrawOrder(int drawOrder) {
		this.drawOrder = drawOrder;
		Integer value = this.drawOrder;
		SignalSlotMap.fastEmit(this, SIGNAL_DRAW_ORDER_CHANGED, value);
		value = null;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		Boolean value = this.visible;
		SignalSlotMap.fastEmit(this, SIGNAL_VISIBLE_CHANGED, value);
		value = null;
	}
	
	public abstract void onDrawOrderChanged(Integer value);
	public abstract void onVisibleChanged(Boolean value);
	
	protected void loadContent() {		
	}
	
	protected abstract void unloadContent();
}
