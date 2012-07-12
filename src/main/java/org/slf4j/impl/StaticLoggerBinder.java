/*
 * StaticLoggerBinder.java
 * UserMetrix-jclient
 *
 * Copyright (c) 2012 UserMetrix Pty Ltd. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.slf4j.impl;

import com.usermetrix.jclient.UserMetrix;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

/**
 * The binding of {@link LoggerFactory} class with an actual instance of
 * {@link ILoggerFactory} is performed using information returned by this class.
 */
public class StaticLoggerBinder implements LoggerFactoryBinder {

    /** This is a SINGLETON, the unique instance of this class. */
    private static final StaticLoggerBinder SINGLETON = new StaticLoggerBinder();

    /**
     * The version tag -- To avoid constant folding by the compiler, this field
     * must *not* be final
     */
    public static String REQUESTED_API_VERSION = "1.6";

    /** The logger factory used for the UserMetrix SLF4J binding. */
    private final ILoggerFactory loggerFactory;

    /** The class name of the logger factory used for the UserMetrix SLF4J binding. */
    private static final String loggerFactoryClassStr = UMLoggerFactory.class.getName();

    /**
     * Private constructor - this is a singleton.
     */
    private StaticLoggerBinder() {
        loggerFactory = new UMLoggerFactory();

        // Initialise UserMetrix so that we are all good to start collecting data
        // when developers fetch loggers from the Factory.
        try {
            UserMetrix.initalise();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * @return The single instance of the LoggerBinder.
     */
    public static StaticLoggerBinder getSingleton() {
        return SINGLETON;
    }

    /**
     * @return The logger factory for the UserMetrix SLF4J binding.
     */
    public ILoggerFactory getLoggerFactory() {
        return loggerFactory;
    }

    /**
     * @return The name of the logger factory class used with the UserMetrix
     * SLF4J binding.
     */
    public String getLoggerFactoryClassStr() {
        return loggerFactoryClassStr;
    }    
}
