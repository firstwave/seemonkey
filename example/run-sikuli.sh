#!/bin/sh
SIKULI_SCRIPT_JAR=libs/sikuli-script.jar
SIKULI_PLUGIN_JAR=libs/seemonkey.jar
PYTHON_LIBRARY=libs/python

# set classpath for local support libraries
CLASSPATH=$SIKULI_SCRIPT_JAR:$SIKULI_PLUGIN_JAR:$PYTHON_LIBRARY

# add android sdk jars to the classpath
CLASSPATH=${CLASSPATH}:$ANDROID_SDK/tools/lib/guavalib.jar
CLASSPATH=${CLASSPATH}:$ANDROID_SDK/tools/lib/monkeyrunner.jar
CLASSPATH=${CLASSPATH}:$ANDROID_SDK/tools/lib/sdklib.jar
CLASSPATH=${CLASSPATH}:$ANDROID_SDK/tools/lib/chimpchat.jar
CLASSPATH=${CLASSPATH}:$ANDROID_SDK/tools/lib/ddmlib.jar

java -cp $CLASSPATH -Dseemonkey.variable="sample variable" org.sikuli.script.SikuliScript $*
