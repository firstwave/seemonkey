#!/bin/sh

jar cvfm bin/seemonkey.jar manifest.txt -C bin com/
echo "copying .jar to example distribution package"
cp bin/seemonkey.jar example/libs/
