/*
 * UserMetrixAppender.java
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
package com.usermetrix.jclient;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.AppenderBase;

/**
 * A LogBack appender for UserMetrix.
 */
public class UserMetrixAppender extends AppenderBase<ILoggingEvent> {

    @Override
    public void start() {
        // Initialise UserMetrix so that we are all good to start collecting
        // data when developers fetch loggers from the Factory.
        try {
            UserMetrix.initalise();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        super.start();
    }

    @Override
    protected void append(ILoggingEvent loggingEvent) {
        Class<?> sourceClass;
        try {
            sourceClass = Class.forName(loggingEvent.getLoggerName());
        } catch (ClassNotFoundException e) {
            // Unable to find the class that we are logging - so just use the
            // base object as the source name.
            sourceClass = Object.class;
        }

        switch (loggingEvent.getLevel().toInt()) {
            case Level.ERROR_INT:
            case Level.WARN_INT:

                // If logging event contains details of something that has been
                // thrown - pump that into the UserMetrix logging event.
                if (loggingEvent.getThrowableProxy() != null &&
                    loggingEvent.getThrowableProxy().getClass().equals(ThrowableProxy.class)) {
                    ThrowableProxy tp = (ThrowableProxy) loggingEvent.getThrowableProxy();

                    UserMetrix.getInstance().error(loggingEvent.getMessage(),
                                                   tp.getThrowable(),
                                                   sourceClass);

                // Nothing has been thrown - just do a vanilla log event.
                } else {

                    UserMetrix.getInstance().error(loggingEvent.getMessage(),
                                                   sourceClass);
                }

                break;

            case Level.TRACE_INT:
            case Level.INFO_INT:
            case Level.DEBUG_INT:
            default:
                UserMetrix.getInstance().event(loggingEvent.getMessage(),
                                               sourceClass);
                break;
        }
    }

    @Override
    public void stop() {
        // Shutdown usermetrix and transmit the log to the UserMetrix server.
        UserMetrix.shutdown();

        super.stop();
    }
}
