#!/usr/bin/env python
'''
Author: Oliver Bartley
Date: 11/28/2011
Version: 1.0

Configure the Java environment and Sikuli interpereter to run .sikuli scripts that make use of SeeMonkey objects.
For usage, run with the -h option
'''

import sys, os
import argparse
import subprocess

def main():
    # parse command line arguments
    parser = argparse.ArgumentParser(description = "Configure the Java environment and Sikuli interpereter to run .sikuli scripts that make use of SeeMonkey objects.")
    
    parser.add_argument("--android-sdk", dest="androidSdk", default=None,
                        help="Sets path to local Android SDK. Default is to use the value of the environment variable $ANDROID_SDK", metavar = "DIR")
    parser.add_argument("--seemonkey-root", dest="seemonkeyRoot", default=os.getcwd(),
                        help="Sets path to the root of the SeeMonkey package. Due to the way the Sikuli interpereter locates native libraries, they must always be available in 'DIR/libs'.\
                             Use this option if you need to run a script from within a directory outside of the package root.\
                             Default is the current working directory.", metavar="DIR")
    
    parser.add_argument("--set-property", action='append', dest='props',
                        help="Sets a Java system property 'seemonkey.NAME'='VALUE', e.g. '--set-property xmlrunner.output=foo/bar' will set the Java system property 'seemonkey.xmlrunner.output' equal to 'foo/bar'. This is useful to pass additional data to the sikuli environment from the command line.", metavar="NAME=VALUE")
    
    parser.add_argument("path_to_script", help="path to sikuli script", nargs='+')
    
    args = parser.parse_args()
    
    os.chdir(args.seemonkeyRoot)
    
    if args.androidSdk is None:
        try:
            androidSdk = stripSlash(os.environ['ANDROID_SDK'])
        except:
            # throws a KeyError if the variable is not set
            sys.stderr.write("Cannot locate Android SDK\n")
            sys.stderr.write("Set $ANDROID_SDK or use the --android-sdk option to specify a path.\n")
            exit(1)
    else:
        androidSdk = stripSlash(args.androidSdk)
        
    try:
        javaClasspath = os.environ['CLASSPATH']
    except:
        # throws a KeyError if the variable is not set
        javaClasspath = ''
        
    
    # add custom seeMonkey stuff to the classpath
    for j in ["libs/sikuli-script.jar",
              "libs/seeMonkey.jar",
              "libs/python"]: # additional python packages/modules to be imported within sikuli scripts
        javaClasspath = "%s:%s" %(javaClasspath, j)
    
    # add android sdk libs to the classpath
    for j in ['guavalib.jar',
              'monkeyrunner.jar',
              'sdklib.jar',
              'chimpchat.jar',
              'ddmlib.jar']:
        javaClasspath = "%s:%s/tools/lib/%s" % (javaClasspath, androidSdk, j)
        
    
    # construct the process call in a list object
    procCall = ["java", "-cp", javaClasspath]
    
    # add set-property arguments, if available
    if args.props is not None:
        for p in args.props:
            procCall.append("-D%s" % p)
    
    procCall.append("org.sikuli.script.SikuliScript")
    
    # now we just push a script path, issue the process call for each script path given
    for s in args.path_to_script:
        print("Running script '%s'..." % s)
        procCall.append(s)
        subprocess.call(procCall)
        procCall.pop() # remove the name of the script we just ran,
                       # so that we're ready for the next item in args.scripts
                       
def stripSlash(path):
    '''
    Strip the trailing slash, if present, in a given path
    '''
    if path[-1:] == "/":
        path = path[0:-1]
    return path

if (__name__ == "__main__"):
    main()
