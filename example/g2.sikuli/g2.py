from com.criticalpath.seemonkey import SeeMonkey
from com.android.monkeyrunner import MonkeyDevice
from org.sikuli.script import Settings

Settings.MoveMouseDelay = 0.01
#Settings.AutoWaitTimeout = 100

print "set up android connection"
scr = SeeMonkey() # compatiable with Sikuli Screen/Region

assert scr != None
dev = scr.getMonkeyDevice() # Android Monkey device
scr.autoDelay = 1000


keypad = {0:"1321122943551.png",
    1:"1321122812838.png",
    2:"ABC.png",
    3:"DEF.png",
    4:"GHI.png",
    5:"5jKL.png",
    6:"MN0.png",
    7:"7PQRS.png",
    8:"TUV.png",
    9:"WXYZ.png"}

#scr.wake()
#if scr.exists("1321123632123.png"): 
#    scr.dragDrop("1321123632123.png","7.png")
#if scr.exists("1321123737364.png"):
#    scr.click("1321123737364.png")
#    print("This was matched, apparently")
#scr.wait("1321123758493.png")
#scr.click("1321123758493.png")
#scr.wait("DEFABC5jKLMN.png")
print("pre-sleep")
scr.sleep(100000)
print("post-sleep")
print("clicking...")
scr.click(keypad[5])
scr.click(keypad[0])
scr.sleep(1000)
scr.click(keypad[3])
#scr.click(keypad[3])
#scr.click(keypad[7])
#scr.click(keypad[6])
#scr.click(keypad[4])
#scr.click(keypad[5])
#scr.click(keypad[6])
#scr.click(keypad[7])
#scr.click(keypad[9])

#scr.click("1321123015142.png")
#scr.wait("1321123362465.png")
exit(0)