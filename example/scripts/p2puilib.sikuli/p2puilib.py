from org.sikuli.script import *
loginUi = {"PayPalLogo":"Paymq.png",
        "PasswordTabActive": "Password.png",
        "PasswordTabInactive":"Password-1.png",
        "PINTabActive":"lll.png",
        "PINTabInactive":"PIN.png",
        "SecureLogin":"SecureLogin.png",
        "EmailFieldFocused":Pattern("Enteremail.png").similar(0.80),
        "EmailFieldUnfocused":"Enteremail-1.png",
        "PasswordFieldFocused":"passwordEnte.png",
        "PasswordFieldUnfocused":"passwordEnte-1.png",
        "LoginButtonDisabled":"LogIn.png",
        
         "LoginButtonEnabled":"LogIn-1.png",
        "CreatePinCheckboxInactive":Pattern("CreateaPINfo.png").targetOffset(-182,1),
        "CreatePinCheckboxActive":"CreateaPINfo-1.png"}

        
        