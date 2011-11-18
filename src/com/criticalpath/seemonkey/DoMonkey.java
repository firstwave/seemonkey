package com.criticalpath.seemonkey;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.sikuli.script.Debug;
import org.sikuli.script.Location;
import org.sikuli.script.ScreenImage;
import org.sikuli.script.IRobot;
//import org.sikuli.script.IRobot.KeyMode;

//import com.android.chimpchat.*;
//import com.android.chimpchat.adb.*;
//import com.android.chimpchat.adb.AdbChimpDevice;
import com.android.chimpchat.core.*;
import com.android.monkeyrunner.MonkeyDevice;
public class DoMonkey implements IRobot {
	   protected IChimpDevice _device;
	   protected Location _mouse;
	   protected int _autoDelay = 0;

	   public DoMonkey(IChimpDevice device){
	      _device = device;
	      _mouse = new Location(0,0);
	   }
	   
	   // I've moved all the keystroke handling to SeeMonkey, but these stubs are still
	   // required by the IRobot interface. It kind of violates the Screen vs Robot metaphor
	   // but the end result is a cleaner jython api
	   protected void key(boolean down, int keycode){
		   Debug.log(5, "DoMonkey.key");
		   // Keystroke methods are implemented in SeeMonkey
	   }

	   public void typeChar(char character, KeyMode mode){
		   Debug.log(5, "DoMonkey.typeChar");
		   // Keystroke methods are implemented in SeeMonkey
	   }

	   public void pressModifiers(int modifiers){
		   Debug.log(5, "DoMonkey.pressModifiers");
		   // Keystroke methods are implemented in SeeMonkey
	   
	   }

	   public void releaseModifiers(int modifiers){
		   Debug.log(5, "DoMonkey.releaseModifiers");
		   // Keystroke methods are implemented in SeeMonkey
	   
	   }

	   public void keyPress(int keycode){
		   Debug.log(5, "DoMonkey.keyPress");
		   // Keystroke methods are implemented in SeeMonkey
	   }

	   public void keyRelease(int keycode){
	      Debug.log(5, "DoMonkey.keyRelease");
	      // Keystroke methods are implemented in SeeMonkey
	   }  

	   public void mouseMove(int x, int y){
	      _mouse.x = x;
	      _mouse.y = y;
	   }
	   public void mousePress(int buttons){
	      _device.touch(_mouse.x, _mouse.y, TouchPressType.DOWN);
	      //delay(_autoDelay);
	   }
	   public void mouseRelease(int buttons){
	      _device.touch(_mouse.x, _mouse.y, TouchPressType.UP);
	      //delay(_autoDelay);
	   }

	   public void smoothMove(Location dest){
		   // The sikuli interpereter calls this to update the 'mouse' position before sending mouse down
		   // So, instead of simply calling a tap(x,y) method (which makes more sense on a touch screen)
		   // we have to set the x,y before mousePress is called.
		   mouseMove(dest.x, dest.y);
	   }
	   public void smoothMove(Location src, Location dest, long ms){
		   mouseMove(dest.x, dest.y);
	   }

	   public void dragDrop(Location start, Location end, int steps, long ms, int buttons){
	      _device.drag(start.x, start.y, end.x, end.y, (int) ms, steps);
	      //delay(_autoDelay);
	   }
	             

	   public void mouseWheel(int wheelAmt){
	      // no implementation on android
	   }

	   public ScreenImage captureScreen(Rectangle screenRect, String filename){
		  // override the default captureScreen, write to a file instead of doing in-memory conversion
	      IChimpImage img = _device.takeSnapshot();
	      try{
	         img.writeToFile(filename, "png");
	         BufferedImage bimg = ImageIO.read(new File(filename));
	         BufferedImage sub_bimg = bimg.getSubimage(screenRect.x, screenRect.y, screenRect.width, screenRect.height);
	         ScreenImage simg = new ScreenImage(screenRect, sub_bimg);
	         return simg;
	      }
	      catch(IOException e){
	         e.printStackTrace();
	         
	         return null;
	      }
	   }
	   public ScreenImage captureScreen(Rectangle screenRect) {
		   // this is the method that find operations use to get a screenshot, needs to be as quick as possible!
		   IChimpImage img = _device.takeSnapshot();
		   try{
			   BufferedImage bimg = img.getBufferedImage();
			   return new ScreenImage(screenRect, bimg);
		   } catch(Exception e) {
			   Debug.error("BufferedImage failure!");
			   e.printStackTrace();
			   return null;
		   }
	   }

	   public void waitForIdle(){
	      // no implementation on android
	   }

	   public void delay(int ms){
		  // this method is required by IRobot interface, I don't like using it; I prefer to use SeeMonkey.sleep() instead.
	      try{
	         Thread.sleep((long) ms);
	      }
	      catch(InterruptedException e){
	      }
	   }

	   public void setAutoDelay(int ms){
	      _autoDelay = ms;
	   }

	   public Object getDevice(){
	      return new MonkeyDevice(_device);
	   }
}
