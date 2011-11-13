#!/bin/sh
jar cvfm ../bin/seemonkey.jar ../manifest.txt -C ../bin com/
#jar cvfm ../bin/seemonkey.jar ../manifest.txt -C ../bin com/ -C .. libs/

#monkeyrunner -plugin ../bin/seemonkey.jar monkeyrunner.py
