package com.atmexplorer;

import android.test.ActivityInstrumentationTestCase;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.atmexplorer.MainActivityTest \
 * com.atmexplorer.tests/android.test.InstrumentationTestRunner
 */
public class MainActivityTest extends ActivityInstrumentationTestCase<MainActivity> {

    public MainActivityTest() {
        super("com.atmexplorer", MainActivity.class);
    }

}
