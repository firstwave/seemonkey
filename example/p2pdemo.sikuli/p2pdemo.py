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
scr.autoDelay = 100
class TestAndroidBasic(unittest.TestCase):
    def testA_setup(self):
        scr.wake()
        if scr.exists("1321469716016.png"):
            if scr.exists("1321472659750.png"):
                scr.dragDrop(Pattern("1321469716016.png").similar(0.73),"1321472659750.png")
            else:    
                scr.dragDrop(Pattern("1321469716016.png").similar(0.73),"1321469736786.png")
        while not scr.exists("1321469893355.png"):
            scr.press('HOME')

        # Clear paypal data
        scr.press('MENU')
        scr.click("1321471500918.png")
        scr.click("QApplication.png")
        scr.click("Manageapplic.png")
        while not scr.exists("lEPayPal.png"):
            scr.press('DPAD_DOWN')
            scr.press('DPAD_DOWN')
            scr.press('DPAD_DOWN')
            
        scr.click("lEPayPal.png")
        try:
            scr.click(Pattern("Cleardata.png").similar(0.92))
            scr.click(Pattern("OK.png").similar(0.83))
            print("Data cleared.")
        except:
            # because the data may already be cleared
            pass
        
        # go back to homescreen and launch the paypal app
        
        while not scr.exists("1321469893355.png"):
            scr.press('HOME')
        scr.click("1321469893355.png")
        while not scr.exists(Pattern("1321469932497.png").similar(0.88)):
            scr.press('DPAD_DOWN')
            scr.press('DPAD_DOWN')
            scr.press('DPAD_DOWN')
            
        scr.click("1321469932497.png")
        scr.wait(Pattern("Accept.png").similar(0.89), 10)

    def testB_UserAgreement(self):
        assert scr.exists(Pattern("1321474789331.png").similar(0.87))
        assert scr.exists(Pattern("Accept.png").similar(0.89))

        # verify links in user agreement
        # these are visible by default on WVGA screens
        scr.click("PrlvacvPollc.png")
        scr.wait("PB.png", 30) # indicates that the website has loaded
        assert scr.exists("PrivacyPolic.png") # has the correct page loaded?

        scr.press('BACK')
        scr.click(Pattern("Accept.png").similar(0.89))
        scr.wait("SecureLogin.png")

    def testC_Login(self):
        assert scr.exists("Password.png")
        assert scr.exists(Pattern("Enteremail.png").similar(0.77)) # should be focused and empty
        scr.longClick("Paymq-1.png")
        scr.wait("Menu.png", 10)
        try:
            scr.click(Pattern("stage2mobie0.png").similar(0.99).targetOffset(-87,3))
            scr.click(Pattern("EUseMEPDIaut.png").targetOffset(-182,9))
        except:
            # must already be selected
            pass
        scr.press('BACK')
        scr.wait("Password.png")
        scr.type('op-us-p12@paypal.com')
        scr.press('ENTER')
        scr.type('11111111')
        if scr.exists("1321476124694.png"):
            scr.press('BACK')
        scr.click("LogIn-1.png")
        scr.wait("HPaymq.png", 30)

        
    def testD_SendMoney(self):
        scr.click("SendMoney.png")
        scr.wait("SendMoney-1.png")
        scr.type('op-us-p8@paypal.com')
        sleep(1)
        scr.press('DPAD_DOWN')
        scr.type('.05')
        if scr.exists("1321476399095.png"):
            scr.press('BACK')
        scr.click(Pattern("Impayingforg.png").targetOffset(-192,-2))
        scr.click("1321476474973.png")
        scr.wait("Paymentdetai.png", 10)
        scr.click("EiltI3mSS3g.png")
        scr.type('SeeMonkey 12345')
        if scr.exists("1321476124694.png"):
            scr.press('BACK')
        assert scr.exists("Send.png")
if __name__ == '__main__':
    unittest.main()                                              