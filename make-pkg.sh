#!/bin/sh

DISTRIBUTION_DIR="$HOME/Projects/SeeMonkeyDist"
jar cvfm bin/seemonkey.jar manifest.txt -C bin com/
echo "copying .jar to distribution package"
cp bin/seemonkey.jar "$DISTRIBUTION_DIR/libs/"
