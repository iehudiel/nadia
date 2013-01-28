/*
 * GameComponent.java
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

import android.graphics.Canvas;

public abstract class GameComponent implements ISignalProvider, ISlotProvider {
	private boolean enabled;
	private int updateOrder;
	
	public static final String SIGNAL_ENABLED_CHANGE = "ENABLED_CHANGE";
	public static final String SIGNAL_UPDATE_ORDER_CHANGE = "UPDATE_ORDER_CHANGE";
	
	public GameComponent() {
		this.enabled = true;
		this.updateOrder = 0;
		
		SignalSlotMap.fastConnect(this, SIGNAL_ENABLED_CHANGE, 
				SlotProviderMethodPair.create(this, "onEnabledChange", Boolean.class));
		SignalSlotMap.fastConnect(this, SIGNAL_UPDATE_ORDER_CHANGE, 
				SlotProviderMethodPair.create(this, "onUpdateOrderChange", Integer.class));
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		Boolean value = this.enabled;
		SignalSlotMap.fastEmit(this, SIGNAL_ENABLED_CHANGE, value);
	}
	public int getUpdateOrder() {
		return updateOrder;
	}
	public void setUpdateOrder(int updateOrder) {
		this.updateOrder = updateOrder;
		Integer value = this.updateOrder;
		SignalSlotMap.fastEmit(this, SIGNAL_UPDATE_ORDER_CHANGE, value);
	}
	
	public abstract void initialize();
	
	public abstract void update(Canvas canvas);
	
	public abstract void onEnabledChange(Boolean value);
	
	public abstract void onUpdateOrderChange(Integer value);
}
