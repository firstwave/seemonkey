#!/bin/sh
jar cvfm ../bin/seemonkey.jar ../manifest.txt -C ../bin com/
cp ../bin/seemonkey.jar .

#adb shell "monkey --port 12345" &
#echo "WARNING SHOT FIRED"

./run-sikuli.sh $*
