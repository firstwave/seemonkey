package com.criticalpath.seemonkey;

//import java.awt.image.BufferedImage;

import java.awt.Rectangle;
import java.io.IOException;

import org.sikuli.script.*;

import com.android.chimpchat.*;
import com.android.chimpchat.adb.AdbChimpDevice;
import com.android.chimpchat.core.*;

public class SeeMonkey extends Region implements IScreen{

	protected IChimpDevice _device;
	protected IRobot _robot;
	public int autoDelay = 500;
	public int longPressDelay = 1000;
	
	public SeeMonkey() {
		setAutoWaitTimeout(1000);
		waitForConnection();
	}
	
	public SeeMonkey(long timeout, String deviceId) {
		setAutoWaitTimeout(1000);
		waitForConnection(timeout, deviceId);
	}
	
	private void waitForConnection(){
		   waitForConnection(-1, null);
	}
	
	private void waitForConnection(long timeoutMs, String deviceId){
		//Map<String, String> options = new TreeMap<String, String>();
		//options.put("backend", "adb");
		ChimpChat chimpchat = ChimpChat.getInstance();
		
		try{
			if(timeoutMs<0 || deviceId == null) {
				_device = chimpchat.waitForConnection();
			} else {
				_device = chimpchat.waitForConnection(timeoutMs, deviceId);
			}

			Debug.info("Properly started!!");
		}
		
		catch(Exception e){
			Debug.error("no android connection");
			e.printStackTrace();
		}

		_robot = new DoMonkey(_device);
		
		Rectangle b = getBounds();
		init(b.x, b.y, b.width, b.height, this);
	}

	// IScreen Methods
	public ScreenImage capture() {
	   return capture(getBounds());
	}
	public ScreenImage capture(int x, int y, int w, int h) {
	   return capture(new Rectangle(x,y,w,h));
	}
	public ScreenImage capture(Rectangle rect) {
		return _robot.captureScreen(rect);
	}
	public ScreenImage capture(Region reg) {
	   return capture(reg.getROI());
	}

	public Rectangle getBounds(){
		String width = _device.getProperty("display.width");
		
		if (width == null)
			return new Rectangle(0,0,0,0);
		
		String height = _device.getProperty("display.height");
		return new Rectangle(0, 0, 
				Integer.parseInt(width), 
				Integer.parseInt(height));
	}
	
	public IRobot getRobot(){
		return _robot;
	}

	public Region newRegion(Rectangle rect){
		return Region.create(rect, this);
	}

	public void showMove(Location loc){}
	public void showClick(Location loc){}
	public void showTarget(Location loc){}
	public void showDropTarget(Location loc){}

	//end IScreen Methods
	
    public Object getMonkeyDevice() {
    	return _robot.getDevice();
    }
    
    public void press(String keycode, int type) {
    	keycode = "KEYCODE_" + keycode.toUpperCase();
    	switch (type) {
    	case 0: _device.press(keycode, TouchPressType.UP); break;
    	case 1: _device.press(keycode, TouchPressType.DOWN); break;
    	default: _device.press(keycode, TouchPressType.DOWN_AND_UP);
    	}
    	sleep();
    }
    
    public void press(String keycode) {
    	press(keycode, 2);
    }
    
    public void press(String keycode, String type) {
    	keycode = keycode.toUpperCase();
    	if (keycode == "UP") press(keycode, 0);
    	else if (keycode == "DOWN") press(keycode, 1);
    	else press(keycode, 2); 
    }
    
    public void longPress(String keycode) {
    	_device.press("KEYCODE_" + keycode, TouchPressType.DOWN);
    	sleep(longPressDelay);
    	_device.press("KEYCODE_" + keycode, TouchPressType.UP);
    	sleep();
    }
    
//    public <PSRML> int click(PSRML target, int modifiers) throws  FindFailed{
//    	// the click wrappers are needed to add in autoDelay, otherwise adb chokes on rapid-fire clicks
//    	int rv = super.click(target, modifiers);
//    	System.out.println("SeeMonkey.click(t,m)");
//    	sleep();
//    	return rv;
//    }
//    public <PSRML> int click(PSRML target) throws  FindFailed{
//    	// the click wrappers are needed to add in autoDelay, otherwise adb chokes on rapid-fire clicks
//    	System.out.println("SeeMonkey.click(t)");
//    	return click(target, 0);
//    }
    
    public int type(String text) {
		String word = "";
    	for (int i = 0; i < text.length(); i++){
    	    char c = text.charAt(i);        
    	    if (c == ' ') {
    	    	if (word != "") {
    	    		_device.type(word);
    	    		word = "";
    	    	} 
    	    	_device.press("KEYCODE_SPACE", TouchPressType.DOWN_AND_UP);
    	    }
    	    else {
    	    	word += Character.toString(c);
    	    }
    	}
    	if (word != "") _device.type(word);
    	
    	sleep();
    	return 0;
    }
    
    public int sequence(String sequence) {
    	// cloned the old EvilMonkey.sequence() concept here, although a bit limited in functionality
		Boolean isLongPress;
    	String keycode = new String();
    	for (int i = 0; i < sequence.length(); i++) {
    		char c = sequence.charAt(i);
    		// if the code s capitalized, then it indicates a long press
    		if (Character.isUpperCase(c)) {
    			isLongPress = true;
    			c = Character.toLowerCase(c);
    		} else {
    			isLongPress = false;
    		}
    		
    		switch (c) {
    		
    		case 'b': keycode = "BACK"; break;
    		case 'h': keycode = "HOME"; break;
    		case 'm': keycode = "MENU"; break;
    		case 'p': keycode = "POWER"; break;
    		
    		case 'u': keycode = "DPAD_UP"; break;
    		case 'd': keycode = "DPAD_DOWN"; break;
    		case 'l': keycode = "DPAD_LEFT"; break;
    		case 'r': keycode = "DPAD_RIGHT"; break;
    		case 'c': keycode = "DPAD_CENTER"; break;
    		
    		case '.': sleep();
    		}
    		if (keycode != "") {
    			if (isLongPress) {
    				_device.press("KEYCODE_" + keycode, TouchPressType.DOWN);
    				sleep(longPressDelay);
    				_device.press("KEYCODE_" + keycode, TouchPressType.UP);
    			} else {
    				_device.press("KEYCODE_" + keycode, TouchPressType.DOWN_AND_UP);
    			}
    			keycode = "";
    			sleep();
    		}
    	}
    	return 0;
    }
   
    
    // SEARCH METHODS
    public Boolean exists(String resource) {
    	if (find(resource) == null)
    		return false;
    	else
    		return true;
    }
    
    public Finder find(String resource) {
    	try {
    		// TODO: Make this use a buffered image (ScreenImage) instead of writing to file
    		IChimpImage screenCapture = _device.takeSnapshot();
    		String screenCaptureFilename = "/tmp/seemonkey.png";
    		screenCapture.writeToFile(screenCaptureFilename, "png");
    		// get a new finder object, and see if anything matched.
    		Finder mFinder = new Finder(screenCaptureFilename);    	    		
    		
    		mFinder.find(resource);
    		if (mFinder.hasNext()) {
    			return mFinder;
    		} else {
    			return null;
    		}
    	} catch (IOException e) {

    		return null;
    	}
    }
    
    public void wake() {
    	_device.wake();
    	sleep();
    }
    public void sleep() {
    	sleep(autoDelay);
    }
    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
        System.out.println("Zzz...");
    }
}
