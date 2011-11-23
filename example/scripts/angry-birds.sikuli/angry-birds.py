import unittest
import sys

from com.criticalpath.seemonkey import SeeMonkey
from com.android.monkeyrunner import MonkeyDevice
from org.sikuli.script import Settings

Settings.MoveMouseDelay = 0.01
Settings.AutoWaitTimeout = 10

Settings.ActionLogs = False
Settings.DebugLogs = False
print "set up android connection"
scr = SeeMonkey() # compatiable with Sikuli Screen/Region

assert scr != None
dev = scr.getMonkeyDevice() # Android Monkey device
scr.autoDelay = 250

class angryBirds(unittest.TestCase):
    def test_0(self):
        while not scr.exists("app-icon.png"):
            scr.press('HOME')        
        scr.click("1321928060768.png")
        scr.wait("play.png", 30)
        scr.click("1321928134902.png")
        scr.click("NVilPomnzv52.png")
        scr.click(Pattern("1321928202919.png").similar(0.85))

    def test_1_1(self):
        print("Level 1-1")
        scr.wait("red-bird.png", 30)
        scr.dragDrop("Y1.png", Pattern("Y1.png").targetOffset(-238,100))# first shot
        scr.wait("level-list.png", 30)
        scr.click("level-list.png")        
if __name__ == '__main__':
    unittest.main()

    exit(0)