
import commands
import sys
from com.android.monkeyrunner import *
from com.criticalpath.seemonkey import SeeMonkey

devices = commands.getoutput('adb devices').strip().split('\n')[1:]
if len(devices) == 0:
  MonkeyRunner.alert("No devices found. Start an emulator or connect a device.", "No devices found", "Exit")
  sys.exit(1) 
elif len(devices) == 1:
  choice = 0
else:
  choice = MonkeyRunner.choice("More than one device found. Please select target device.", devices, "Select target device")

device_id = devices[choice].split('\t')[0]

device = MonkeyRunner.waitForConnection(5, device_id)

SeeMonkey.sleep(7)

SeeMonkey.press(device, 'menu')
SeeMonkey.sleep(10)
SeeMonkey.press(device, 'back')
