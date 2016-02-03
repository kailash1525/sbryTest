package com.sbry.testsrc;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Created by Kailash on 03/02/16.
 *
 * TestRunner class (which runs the relevent test class) which can be called via console
 */
public class TestRunner {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(WebScrapperTest.class);

        if(result.getFailures().size() > 0) {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        } else {
            System.out.println("ALL TEST CASES PASSED.");
        }
    }

}