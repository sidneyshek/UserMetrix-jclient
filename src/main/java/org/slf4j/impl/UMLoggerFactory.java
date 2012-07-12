/*
 * UMLoggerFactory.java
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
import java.util.HashMap;
import java.util.Map;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/**
 * The UserMetrix logger factory for generating UserMetrix loggers for different
 * classes.
 */
public class UMLoggerFactory implements ILoggerFactory {

    /** Mapping between class names and corresponding loggers (adapters). */
    private Map<String, Logger> loggerMap;

    public UMLoggerFactory() {
        loggerMap = new HashMap<String, Logger>();
    }

    /**
     * Gets a SLF4J compatible UserMetrix logger.
     *
     * @param name The name of the class that you wish to fetch the
     * corresponding for.
     *
     * @return A SLF4J logger that wraps around UserMetrix logging calls.
     */
    public Logger getLogger(String name) {
        Logger result = null;

        synchronized (this) {
            try {
                result = loggerMap.get(name);

                if (result == null) {                
                    com.usermetrix.jclient.Logger umLogger = UserMetrix.getLogger(Class.forName(name));
                    result = new UMLoggerAdapter(umLogger);
                    loggerMap.put(name, result);                    
                }
            } catch (ClassNotFoundException e) {
                System.err.println("Unable to get logger for class: " + name);
            }
        }

        return result;
    }
}
