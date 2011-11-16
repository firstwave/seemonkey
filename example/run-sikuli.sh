#!/bin/sh
SIKULI_SCRIPT_JAR=sikuli-script.jar
SIKULI_PLUGIN_JAR=seemonkey.jar

CLASSPATH=$SIKULI_SCRIPT_JAR:$SIKULI_PLUGIN_JAR
CLASSPATH=${CLASSPATH}:$ANDROID_SDK/tools/lib/guavalib.jar
CLASSPATH=${CLASSPATH}:$ANDROID_SDK/tools/lib/monkeyrunner.jar
CLASSPATH=${CLASSPATH}:$ANDROID_SDK/tools/lib/sdklib.jar
CLASSPATH=${CLASSPATH}:$ANDROID_SDK/tools/lib/chimpchat.jar
CLASSPATH=${CLASSPATH}:$ANDROID_SDK/tools/lib/ddmlib.jar

java -cp $CLASSPATH -Dseemonkey.variable="FOOD HOLE" org.sikuli.script.SikuliScript $*
