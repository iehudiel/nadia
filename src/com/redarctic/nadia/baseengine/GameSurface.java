/*
 * GameSurface.java
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

import com.redarctic.nadia.controls.DirectionalPad;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.Window;

public abstract class GameSurface 
extends Activity
implements OnTouchListener {
	Point screenSize;
	
	protected DirectionalPad gamePad = null;
	protected GameSurfaceView view;
	
	static GameSurface ref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.screenSize = new Point();
		this.loadGamePad();
		if (null == this.gamePad)
			this.view = new GameSurfaceView(this);
		else
			this.view = new GameSurfaceView(this, this.gamePad);
		this.loadScreenSize();
		this.loadResources();		
		
		this.view.setOnTouchListener(this);
		
		setContentView(this.view);
		GameSurface.ref = this;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if (this.gamePad != null)
			this.gamePad.onTouch(v, event);
		
		return true;
	}
			
	@Override
	protected void onStop() {
		if (this.gamePad != null) {
			this.gamePad.destroy();
			this.gamePad = null;
		}
		
		super.onStop();
		System.gc();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		this.view.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.view.resume();
	}
	
	public static GameSurface getRef() {
		return ref;
	}
	
	public static String getStringResource(int resourceId) {
		String strResourceString = "";
		
		strResourceString = getRef().getString(resourceId);
		
		return strResourceString;
	}

	public GameSurfaceView getView() {
		return view;
	}
	
	public void setView(GameSurfaceView view) {
		this.view = view;
	}

	public Point getScreenSize() {
		return screenSize;
	}

	public DirectionalPad getGamePad() {
		return gamePad;
	}
	
	public GameState getCurrentState() {
		return this.view.getStateManager().getCurrentState();
	}
	
	protected void loadScreenSize() {
		DisplayMetrics metrics = getBaseContext().getResources().getDisplayMetrics();
		
		this.screenSize.x = metrics.widthPixels;
		this.screenSize.y = metrics.heightPixels;
	}
	
	protected abstract void loadResources();
	protected abstract void loadGamePad();
}
