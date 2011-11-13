#!/bin/sh
jar cvfm bin/seemonkey.jar manifest.txt -C bin .
monkeyrunner -plugin bin/seemonkey.jar seemonkey_test.py
