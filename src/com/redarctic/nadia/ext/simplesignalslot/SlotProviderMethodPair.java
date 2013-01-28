/*
 * SlotProviderMethodPair.java
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

import java.lang.reflect.Method;

public class SlotProviderMethodPair {
	public static SlotProviderMethodPair create(ISlotProvider slotProvider,
			String slotMethodName, Class slotParameterTypes[]) {
		Method m = getMethodWithParam(slotProvider, slotMethodName,
				slotParameterTypes);

		return initPair(slotProvider, m);

	}

	public static SlotProviderMethodPair create(ISlotProvider slotProvider,
			String slotMethodName, Class slotParameterType) {
		return create(slotProvider, slotMethodName,
				new Class[] { slotParameterType });

	}

	public static SlotProviderMethodPair create(Class cls,
			String slotMethodName, Class slotParameterTypes[]) {
		Method m = getMethodWithParam(cls, slotMethodName, slotParameterTypes);

		return initPair(null, m);

	}

	public static SlotProviderMethodPair create(Class cls,
			String slotMethodName, Class slotParameterType) {
		return create(cls, slotMethodName, new Class[] { slotParameterType });

	}

	/**
	 * 
	 * @param slotProvider
	 *            can be null if the method is a static method (since going to
	 *            act on a class)
	 * @param m
	 * @return
	 * @throws RuntimeException
	 *             slot method not found;
	 */
	private static SlotProviderMethodPair initPair(ISlotProvider slotProvider,
			Method m) {
		if (m != null) {
			SlotProviderMethodPair pair = new SlotProviderMethodPair();
			pair.slotProvider = slotProvider;
			pair.slotMethod = m;
			pair.slotMethod.setAccessible(true);
			return pair;
		}

		throw new RuntimeException("slot method not found");
	}

	public static SlotProviderMethodPair create(ISlotProvider slotProvider,
			String slotMethodName) {
		Method m = getMethodNoParam(slotProvider, slotMethodName);

		return initPair(slotProvider, m);
	}

	public static SlotProviderMethodPair create(Class cls, String slotMethodName) {
		Method m = getMethodNoParam(cls, slotMethodName);

		return initPair(null, m);
	}

	/**
	 * 
	 * @param o
	 * @param methodName
	 * @param parameterTypes
	 * @return null if cannot find a match
	 */
	private static Method getMethodWithParam(Object o, String methodName,
			Class parameterTypes[]) {
		return getMethodWithParam(o.getClass(), methodName, parameterTypes);
	}

	private static Method getMethodWithParam(Class cls, String methodName,
			Class parameterTypes[]) {
		Method m[] = cls.getMethods();
		Method result = null; // tentatively

		for (int i = 0; i < m.length; ++i) {
			if (m[i].getName().equals(methodName)
					&& haveExactNames(m[i].getParameterTypes(), parameterTypes)) {
				result = m[i]; // found a match
				break;
			}
		}

		return result;
	}

	private static Method getMethodNoParam(Object o, String methodName) {
		return getMethodNoParam(o.getClass(), methodName);
	}

	private static Method getMethodNoParam(Class cls, String methodName) {
		Method m[] = cls.getMethods();
		Method result = null; // tentatively

		for (int i = 0; i < m.length; ++i) {
			if (m[i].getName().equals(methodName)
					&& (m[i].getParameterTypes().length == 0)) {
				result = m[i]; // found a match
				break;
			}
		}

		return result;
	}

	/**
	 * Only permits exact class names; ignores whether a pair of types might be
	 * of assignable form.
	 * 
	 * @param pt1
	 * @param pt2
	 * @return null if any pair of names is different
	 */
	private static boolean haveExactNames(Class pt1[], Class pt2[]) {
		if (pt1.length != pt2.length) {
			return false;
		}

		for (int i = 0; i < pt1.length; ++i) {
			// any pair of different names stops the comparison
			if (!(pt1[i].getName().equals(pt2[i].getName()))) {
				return false;
			}
		}

		return true;
	}

	public ISlotProvider getSlotProvider() {
		return slotProvider;
	}

	public Method getSlotMethod() {
		return slotMethod;
	}

	private ISlotProvider slotProvider;
	private Method slotMethod;
}
