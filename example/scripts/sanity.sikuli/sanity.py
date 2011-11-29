import unittest
import xmlrunner
import sys


from com.criticalpath.seemonkey import SeeMonkey

scr = SeeMonkey()

assert scr != None

scr.autoDelay = 100
class SeeMonkeyTest(unittest.TestCase):
    def testSanity(self):
        scr.wake()
        scr.press('HOME')
     
if __name__ == '__main__':
    unittest.main(testRunner=xmlrunner.XMLTestRunner(output='test-reports'))
