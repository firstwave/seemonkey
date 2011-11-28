import unittest
import xmlrunner
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
scr.autoDelay = 100
class TestAndroidBasic(unittest.TestCase):
    def testA_Success(self):
        scr.wake()
        scr.press('HOME')
    
    def testB_Failure(self):
        assert False

if __name__ == '__main__':
    unittest.main(testRunner=xmlrunner.XMLTestRunner(output='test-reports'))
