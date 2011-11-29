import unittest
import xmlrunner
import sys


from com.criticalpath.seemonkey import SeeMonkey
from com.android.monkeyrunner import MonkeyDevice
from org.sikuli.script import Settings

Settings.MoveMouseDelay = 0.01
Settings.AutoWaitTimeout = 10

print "set up android connection"
scr = SeeMonkey() # compatiable with Sikuli Screen/Region

assert scr != None
dev = scr.getMonkeyDevice() # Android Monkey device
scr.autoDelay = 100
class SeeMonkeyTest(unittest.TestCase):
    def testSanity(self):
        print("This is an example of output on STDOUT")
        sys.stderr.write("This is an example of output on STDERR")
        scr.wake()
        scr.press('HOME')
     
if __name__ == '__main__':
    unittest.main(testRunner=xmlrunner.XMLTestRunner(output='test-reports'))
