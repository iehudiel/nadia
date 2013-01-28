/*
 * Animation.java
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

package com.redarctic.nadia.baseengine.sprite;

import android.graphics.Canvas;
import android.os.SystemClock;

import com.ashokgelal.samaya.TimeSpan;
import com.redarctic.nadia.ext.MathHelper;
import com.redarctic.nadia.collision.Rectangle;

public class Animation implements Cloneable {
	public static final int DEFAULT_FRAMES_PER_SECOND = 5;
	Rectangle[] frames;
	int framesPerSecond;
	TimeSpan frameZeroTimer;
	TimeSpan frameLength;
	TimeSpan frameTimer;
	int currentFrame;
	int frameWidth;
	int frameHeight;
	
	public int getFramesPerSecond() {
		return this.framesPerSecond;
	}
	
	public void setFramesPerSecond(int value) {
		if (value < 1)
			this.framesPerSecond = 1;
		else if (value > 60)
			this.framesPerSecond = 60;
		else
			this.framesPerSecond = value;
		
		this.frameLength = TimeSpan.FromSeconds( 1 / (double)this.framesPerSecond );
	}
	
	public Rectangle getCurrentFrameRect() {
		if (frames.length > this.currentFrame)
			return this.frames[this.currentFrame];
		else 
			return null;
	}
	
	public int getCurrentFrame() {
		return this.currentFrame;
	}
	
	public void setCurrentFrame(int value) {
		this.currentFrame = (int)MathHelper.clamp(value, 0, this.frames.length - 1);
	}
	
	public int getFrameWidth() {
		return this.frameWidth;
	}
	
	public int getFrameHeight() {
		return this.frameHeight;
	}
	
	public Animation(int frameCount, int frameWidth, int frameHeight, int xOffset, int yOffset) 
	throws AnimationException {
		if (frameCount > 0) {
			frames = new Rectangle[frameCount];
			this.frameWidth = frameWidth;
			this.frameHeight = frameHeight;
			
			for (int i = 0; i < frameCount; i++) {
				this.frames[i] = new Rectangle(
						xOffset + (frameWidth * i),
						yOffset,
						xOffset + (frameWidth * i) + frameWidth,
						yOffset + frameHeight);
			}
			this.setFramesPerSecond(DEFAULT_FRAMES_PER_SECOND);
			this.reset();
		} else {
			throw new AnimationException("The frameCount parameter is less than 1");
		}
	}
	
	private Animation(Animation animation) {
		this.frames = animation.frames;
		this.setFramesPerSecond(DEFAULT_FRAMES_PER_SECOND);
	}
	
	public void update(Canvas canvas) {
		TimeSpan tsTmp = TimeSpan.Subtract(
				TimeSpan.FromMilliseconds( SystemClock.elapsedRealtime() ), 
				this.frameZeroTimer
				);
		
		this.frameTimer = TimeSpan.Add(
				this.frameTimer,
				tsTmp
				);
		
		if ( this.frameTimer.GreaterThanOrEqual(frameLength) ) {
			frameTimer = TimeSpan.Zero;
			currentFrame = (currentFrame + 1) % frames.length;
		}
	}
	
	public void reset() {
		this.currentFrame = 0;
		frameTimer = TimeSpan.Zero;
		this.frameZeroTimer = TimeSpan.FromMilliseconds(SystemClock.elapsedRealtime());
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Animation animationClone = new Animation(this);
		
		animationClone.frameWidth = this.frameWidth;
		animationClone.frameHeight = this.frameHeight;
		animationClone.reset();
		
		return animationClone;
	}	
}
