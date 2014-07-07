package com.atmexplorer.utils;

import java.util.List;

/**
 * Class provides various checking of input parameters.
 * @author Aleksandr Stetsko (alexandr.stetsko@outlook.com)
 */
public final class Should {

    private Should() {}

    public static void runInThread(long threadId, String message) {
        if(Thread.currentThread().getId() != threadId) {
            throw new IllegalThreadStateException(message);
        }
    }

    public static void beNotEmpty(List<?> list, String message) {
        if(list.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void beNotEmpty(Object [] array, String message) {
        if(array.length == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notContainNull(List<?> list, String message) {
        int i = 0;
        for(Object o : list) {
            Should.beNotNull(o, message + " [" + i++ + "]");
        }
    }

    public static void notContainNull(Object [] array, String message) {
        for(int i = 0; i < array.length; i++) {
            Should.beNotNull(array[i], message + " [" + i + "]");
        }
    }

    public static void beNotNull(Object o, String message) {
        if(o == null) {
            throw new NullPointerException(message);
        }
    }

    public static void beTrue(boolean value, String message) {
        if(!value) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void beFalse(boolean value, String message) {
        if(value) {
            throw new IllegalArgumentException(message);
        }
    }

}
