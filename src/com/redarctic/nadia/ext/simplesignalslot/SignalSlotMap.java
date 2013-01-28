/*
 * SignalSlotMap.java
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

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Simple connection between a pair of signal and slot. Note that signal and
 * slot names never uniquely identify any connection.
 * 
 * This is simple and easy to use, and more resemble to Qt's signal and slot
 * model. Enjoy! Take a look at how to make use of the concept by those creative
 * guys: http://doc.trolltech.com/3.1/signalsandslots.html
 * 
 * @author Emmanuel Arana Corzo <emmanuel.arana@gmail.com>
 */
public class SignalSlotMap {
	private static ConcurrentHashMap<ISignalProvider, ArrayList<SignalSlotPair>> connList = new ConcurrentHashMap<ISignalProvider, ArrayList<SignalSlotPair>>();

	
	/**
	 * fastConnect
	 * connect Establish a connection. No checking for duplication. Use this if
	 * you want typesafe checking. This internally is faster than connect.
	 * An special consideration is that you cannot mix emit with fastConnect,
	 * is strongly bound with just the fast methods 
	 * @param signalProvider
	 * @param signalName
	 *            usually a constant of signalProvider class
	 * @param slotProviderMethodPair
	 *            create using SlotProviderMethodPair.create
	 * @author Emmanuel Arana Corzo <emmanuel.arana@gmail.com>
	 */
	public static synchronized void fastConnect(ISignalProvider signalProvider,
			String signalName, SlotProviderMethodPair slotProviderMethodPair)
			throws NullPointerException {
		if (slotProviderMethodPair != null && signalProvider != null
				&& signalName.length() > 0) {
			// We need to first look for the existence of the key
			if (connList.containsKey(signalProvider)) {
				ArrayList<SignalSlotPair> sspList = connList
						.get(signalProvider);

				// TODO: put a search engine to look if that was already
				// inserted
				SignalSlotPair ssp = new SignalSlotPair(signalProvider,
						signalName, slotProviderMethodPair);
				sspList.add(ssp);
				connList.put(signalProvider, sspList);
			} else {
				ArrayList<SignalSlotPair> sspList = new ArrayList<SignalSlotPair>();

				SignalSlotPair ssp = new SignalSlotPair(signalProvider,
						signalName, slotProviderMethodPair);
				sspList.add(ssp);
				connList.put(signalProvider, sspList);
			}
		} else {
			throw new NullPointerException();
		}
	}

	public static synchronized void fastEmit(ISignalProvider signalProvider,
			String signalName, Object signalData[]) {
		if (connList.containsKey(signalProvider)) {
			ArrayList<SignalSlotPair> sspList = connList.get(signalProvider);

			for (SignalSlotPair ssp : sspList) {
				if (ssp.matches(signalProvider, signalName)) {
					ssp.invoke(signalData);
				}
			}
		}
	}

	
	public static synchronized void fastEmit(ISignalProvider signalProvider,
			String signalName, Object signalData) {
		fastEmit(signalProvider, signalName, new Object[] { signalData });
	}

	
	public static synchronized void fastEmit(ISignalProvider signalProvider,
			String signalName) {
		fastEmit(signalProvider, signalName, null);
	}

	
	public static synchronized void fastDisconnect(
			ISignalProvider signalProvider, String signalName,
			SlotProviderMethodPair slotProviderMethodPair) {
		if (connList.containsKey(signalProvider)) {
			ArrayList<SignalSlotPair> toBeRemovedList = new ArrayList<SignalSlotPair>();

			for (SignalSlotPair ssp : connList.get(signalProvider)) {
				if (ssp.matches(signalProvider, signalName,
						slotProviderMethodPair)) {
					toBeRemovedList.add(ssp);
				}
			}

			fastRemoveConnections(signalProvider, toBeRemovedList);
		}
	}

	
	public static synchronized void fastDisconnect(
			ISignalProvider signalProvider, String signalName) {
		ArrayList<SignalSlotPair> toBeRemovedList = new ArrayList<SignalSlotPair>();

		for (SignalSlotPair ssp : connList.get(signalProvider)) {
			if (ssp.matches(signalProvider, signalName)) {
				toBeRemovedList.add(ssp);
			}
		}

		fastRemoveConnections(signalProvider, toBeRemovedList);
	}

	
	public static synchronized void fastDisconnect(
			ISignalProvider signalProvider, ISlotProvider slotProvider) {
		if (connList.containsKey(signalProvider)) {
			ArrayList<SignalSlotPair> toBeRemovedList = new ArrayList<SignalSlotPair>();

			for (SignalSlotPair ssp : connList.get(signalProvider)) {
				if (ssp.matches(signalProvider, slotProvider)) {
					toBeRemovedList.add(ssp);
				}
			}

			fastRemoveConnections(signalProvider, toBeRemovedList);
		}
	}

	
	public static synchronized void fastDisconnect(
			ISignalProvider signalProvider,
			SlotProviderMethodPair slotProviderMethodPair) {
		if (connList.containsKey(signalProvider)) {
			ArrayList<SignalSlotPair> toBeRemovedList = new ArrayList<SignalSlotPair>();

			for (SignalSlotPair ssp : connList.get(signalProvider)) {
				if (ssp.matches(signalProvider, slotProviderMethodPair)) {
					toBeRemovedList.add(ssp);
				}
			}

			fastRemoveConnections(signalProvider, toBeRemovedList);
		}
	}
	

	public static synchronized void fastDisconnect(
			ISignalProvider signalProvider) {
		fastRemoveConnections(signalProvider);
	}
	

	public static synchronized void fastDisconnect(ISlotProvider slotProvider) {
		for (ISignalProvider signalProvider : connList.keySet()) {
			ArrayList<SignalSlotPair> toBeRemovedList = new ArrayList<SignalSlotPair>();

			for (SignalSlotPair ssp : connList.get(signalProvider)) {
				if (ssp.matches(slotProvider)) {
					toBeRemovedList.add(ssp);
				}
			}

			fastRemoveConnections(signalProvider, toBeRemovedList);
		}
	}

	private static void fastRemoveConnections(ISignalProvider signalProvider) {
		if (connList.containsKey(signalProvider)) {
			connList.get(signalProvider).clear();
			connList.remove(signalProvider);
			System.gc();
		}
	}

	private static void fastRemoveConnections(ISignalProvider signalProvider,
			ArrayList<SignalSlotPair> toBeRemovedList) {
		if (connList.containsKey(signalProvider)) {
			ArrayList<SignalSlotPair> sspList = connList.get(signalProvider);
			for (SignalSlotPair ssp : toBeRemovedList) {
				sspList.remove(ssp);
			}
			toBeRemovedList.clear();
			toBeRemovedList = null;
			if (!sspList.isEmpty())
				connList.put(signalProvider, sspList);
			else {
				connList.remove(signalProvider);
			}
			System.gc();
		}
	}
}
