#!/bin/sh
jar cvfm ../bin/seemonkey.jar ../manifest.txt -C ../bin com/
cp ../bin/seemonkey.jar .
./run-sikuli.sh $*
