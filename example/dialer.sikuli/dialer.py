from com.criticalpath.seemonkey import SeeMonkey
from com.android.monkeyrunner import MonkeyDevice
from org.sikuli.script import Settings

Settings.MoveMouseDelay = 0.01
Settings.AutoWaitTimeout = 100
Settings.DebugLogs = True

print "set up android connection"
#dev = MonkeyRunner.waitForConnection(10000, "HT0A4R200920")
scr = SeeMonkey() # compatiable with Sikuli Screen/Region
sleep(10)
assert scr != None
dev = scr.getMonkeyDevice() # Android Monkey device
scr.autoDelay = 250

keypad = {0:"1321122943551.png",
    1:"1321122812838.png",
    2:"ABC.png",
    3:"DEF.png",
    4:"GHI.png",
    5:"5jKL.png",
    6:"MN0.png",
    7:"7PQRS.png",
    8:"TUV.png",
    9:"WXYZ.png",
    100:"10D.png"}

def clickKeypad(digits):
    global scr
    for d in digits:
        print(d)
        scr.click(keypad[d])
clickKeypad([5,0,3,8,100,0,9,9,0,6])
scr.click("1321123015142.png")
exit(0)
