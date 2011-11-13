package com.criticalpath.seemonkey;

import org.python.util.PythonInterpreter;
import com.google.common.base.Predicate;

public class Plugin implements Predicate<PythonInterpreter> {
	
    @Override
    public boolean apply(PythonInterpreter python) {
    	System.out.println("SeeMonkey initialized.");
    	python.exec("from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice");
    	//python.exec("from com.criticalpath.seemonkey import SeeMonkey");
    	return true;
    }
}
