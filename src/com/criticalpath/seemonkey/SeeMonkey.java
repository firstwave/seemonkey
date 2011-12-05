package com.criticalpath.seemonkey;

//import java.awt.image.BufferedImage;

import java.awt.Rectangle;
import java.io.IOException;

import org.sikuli.script.*;

import com.android.chimpchat.*;
//import com.android.chimpchat.adb.AdbChimpDevice;
import com.android.chimpchat.core.*;
//import com.android.monkeyrunner.MonkeyDevice;

public class SeeMonkey extends Region implements IScreen{

	protected IChimpDevice _device;
	protected IRobot _robot;
	public int autoDelay = 500;
	public int longPressDelay = 1000;
	private final int _monkeyPortDelay = 5000; // milliseconds to wait until 'monkey --port 12345' is called a second time
	static {
		Settings.MoveMouseDelay = (float) 0.01;
	}
	public SeeMonkey() {
		waitForConnection();
	}
	
	public SeeMonkey(long timeout, String deviceId) {
		waitForConnection(timeout, deviceId);
	}
	
	private void waitForConnection(){
		   waitForConnection(-1, null);
	}
	
	private void waitForConnection(long timeoutMs, String deviceId){
		if (deviceId == null)
			Debug.info("Waiting for connection to default device");
		else
			Debug.info("Waiting for connection to device '" + deviceId + "'");		
		Debug.info("It is not unusual to see a com.android.ddmlib.ShellCommandUnresponsiveException");
	
		ChimpChat chimpchat = ChimpChat.getInstance();
		try{
			if(timeoutMs<0 || deviceId == null) {
				_device = chimpchat.waitForConnection();
			} else {
				_device = chimpchat.waitForConnection(timeoutMs, deviceId);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		/*
		 * For some reason, `monkey --port 12345` (AdbMonkeyDevice::AdbMonkeyDevice()) hangs 
		 * on some Android builds (CM, I'm looking at you...)
		 * For some other reason, issuing this command, letting it hang, and issuing it again opens port 12345.
		 * It would be nice to be able to detect when this is absolutely necessary, cuz I don't like leaving my commands 
		 * hanging like that. Looking at the source for AdbMonkeyDevice, it looks like the android devs don't really know
		 * how to handle this either.
		 */
		_device.shell("monkey --port 12345");
		sleep(_monkeyPortDelay); // very IMPORTANT!
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
    
    public String getArgs(String name) {
    	// the run-script command adds the "seemonkey." namespace to make this a bit safer
    	return System.getProperty("seemonkey." + name);
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
    	if (type == "UP") press(keycode, 0);
    	else if (type == "DOWN") press(keycode, 1);
    	else press(keycode, 2); 
    }
    
    public void longPress(String keycode) {
    	// Long Press in this instance refers to long pressing a key. For long pressing screen elements, see longClick()
    	_device.press("KEYCODE_" + keycode, TouchPressType.DOWN);
    	sleep(longPressDelay);
    	_device.press("KEYCODE_" + keycode, TouchPressType.UP);
    	sleep();
    }
   
    public int type(String text) {
    	// MonkeyDevice.type() still has no support for spaces, so here's a workaround wrapper function
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
    
    public <PSMRL> int click(PSMRL target) throws FindFailed {
    	return click(target, 0);
    }
    public <PSMRL> int click(PSMRL target, int modifiers) throws FindFailed {
    	int rv = super.click(target, modifiers);
    	sleep();
    	return rv;
    }
    public <PSRML> int rightClick(PSRML target) throws FindFailed {
    	// It seems like a long-press is analogoous to a right-click in Android land.
    	return longClick(target, 0);
    }
    
    public <PSRML> int rightClisk(PSRML target, int modifiers) throws FindFailed{
    	return longClick(target, modifiers);
    }
    
    public <PSRML> int longClick(PSRML target) throws FindFailed {
    	// the longClick action is what is referred to in Android-land as a Long Press. The reason I've called it longClick is
    	// that I'm trying to maintain some consistency with the built-in sikuli API, which calls
    	// a keypress a 'press' and a screen tap a 'click'
    	return longClick(target, 0);
    }
    
    public <PSRML> int longClick(PSRML target, int modifiers) throws FindFailed {
    	Location loc = getLocationFromPSRML(target);
    	if ( loc != null) {
	    	_robot.mouseMove(loc.x, loc.y);
	    	_robot.mousePress(modifiers);
	    	sleep(longPressDelay);
	    	_robot.mouseRelease(modifiers);
	    	sleep();
	    	return 1;
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
    }
}

