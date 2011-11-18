import unittest
import sys

from com.criticalpath.seemonkey import SeeMonkey
from com.android.monkeyrunner import MonkeyDevice
from org.sikuli.script import Settings

Settings.MoveMouseDelay = 0.01
Settings.AutoWaitTimeout = 10
Settings.ActionLogs = True

print "set up android connection"
scr = SeeMonkey() # compatiable with Sikuli Screen/Region

assert scr != None
dev = scr.getMonkeyDevice() # Android Monkey device
scr.autoDelay = 1000

class TestAndroidBasic(unittest.TestCase):

#    def testA_Sanity(self):
#        img = scr.capture()
#        assert img != None

#    def testC_Unlock(self):
#        global scr, dev
        
#        if not scr.exists("1321044614384.png"):
#            scr.press("BACK")
#            scr.press("POWER")
#            scr.wake()
#        scr.dragDrop("1321044614384.png", "1321044637846.png")
#    def testD_Locales(self):
#        global scr, dev
#        scr.click("1321045502125.png")
#        scr.click("1321066951502.png")
#        scr.wait("New")
#        scr.click("New")
#        scr.wait("EX")
#        scr.type("foo bar")
#        assert scr.exists("foo bar")
#        scr.sequence("bbb. H. b..")


    def testE_P2PLogin(self):
        global scr
        #scr.click("1321121622020.png")
        #scr.dragDrop("1321083903909.png","1321083914785.png")
        scr.click("L2.png")
        wait("Secure Login")
        scr.dragDrop("Paymq.png","Paymq.png")
        
        
        try: 
            scr.click("Enteremail.png")
            scr.type("op-us-p12@paypal.com")
        except:
            scr.click("Enterpasswor.png")
        scr.type("11111111")
        scr.press("BACK")
        scr.click("LogIn.png")

        scr.wait("SendMoney.png")
        scr.click("SendMoney.png")
        scr.wait("Send")
        scr.type("503-225-5050")
        scr.press("DPAD_DOWN")
        scr.type("1")
        scr.press("BACK")
        assert scr.exists("1321087052128.png")
        
                
if __name__ == '__main__':
    unittest.main()
