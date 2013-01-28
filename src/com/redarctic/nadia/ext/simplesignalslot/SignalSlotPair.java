/*
 * SignalSlotPair.java
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

package com.redarctic.nadia.ext.simplesignalslot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Emmanuel Arana Corzo <emmanuel.arana@gmail.com>
 */
public class SignalSlotPair {
	public static final String UNMATCHED_SIGNAL_DATA_TYPE = "Unmatched signalData type";

	public boolean matches(ISignalProvider signalProvider,
			SlotProviderMethodPair pair) {
		return (this.signalProvider_ == signalProvider) // identical reference
				&& (this.slotProvider_ == pair.getSlotProvider())
				&& (this.slotMethod_.equals(pair.getSlotMethod()));

	}

	public boolean matches(ISignalProvider signalProvider,
			ISlotProvider slotProvider) {
		return (this.signalProvider_ == signalProvider) // identical reference
				&& (this.slotProvider_ == slotProvider);
	}

	public boolean matches(ISlotProvider slotProvider) {
		return (this.slotProvider_ == slotProvider); // identical reference
	}

	public boolean matches(ISignalProvider signalProvider) {
		return (this.signalProvider_ == signalProvider); // identical reference
	}

	public boolean matches(ISignalProvider signalProvider, String signalName) {
		return (this.signalProvider_ == signalProvider) // identical reference
				&& this.signalName_.equals(signalName);
	}

	public boolean matches(ISignalProvider signalProvider, String signalName,
			SlotProviderMethodPair pair) {
		return (this.signalProvider_ == signalProvider)
				&& this.signalName_.equals(signalName)
				&& (this.slotProvider_ == pair.getSlotProvider())
				&& (this.slotMethod_.equals(pair.getSlotMethod()));
	}

	/**
	 * Invoke method of my slot provider.
	 * 
	 * @param signalData
	 */
	public void invoke(Object signalData[]) {		
		try {
			// invoke the method
			this.slotMethod_.invoke(this.slotProvider_, signalData);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public SignalSlotPair(ISignalProvider signalProvider, String signalName,
			SlotProviderMethodPair slotProviderMethodPair) {
		this.signalProvider_ = signalProvider;
		this.signalName_ = signalName;
		this.slotProvider_ = slotProviderMethodPair.getSlotProvider();
		this.slotMethod_ = slotProviderMethodPair.getSlotMethod();
	}

	public String toString() {
		return "[" + this.getClass().getName() + ":" + "signalProvider_:"
				+ signalProvider_.toString() + ";" + "signalName_:"
				+ signalName_ + ";" + "slotProvider_:"
				+ slotProvider_.toString() + ";" + "slotMethod_:" + slotMethod_
				+ ";" + "]";
	}

	private ISignalProvider signalProvider_;
	private String signalName_;
	private ISlotProvider slotProvider_;
	private Method slotMethod_;
}
