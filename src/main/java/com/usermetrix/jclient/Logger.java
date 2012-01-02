/*
 * Logger.java
 * UserMetrix-jclient
 *
 * Copyright (c) 2011 UserMetrix Pty Ltd. All rights reserved.
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

/**
 * Class for logging messages to the central UserMetrix server.
 */
public final class Logger {
    /** The source class for messages from this logger. */
    private Class<?> logSource;

    /** The UserMetrix manager responsible for dispatching messages. */
    private UserMetrix manager;

    /**
     * Constructor - Don't create this directly, rather obtain one from the
     * UserMetrix class (UserMetrix.getLogger(yourClass.class);.
     *
     * @param source The source of messages originating from this logger.
     * @param logManager The manager Responsible for dispatching messages.
     */
    public Logger(final Class<?> source, final UserMetrix logManager) {
        logSource = source;
        manager = logManager;
    }

    /**
     * Log usage pattern.
     *
     * @param tag Unique tag for the triggering of a specific event.
     */
    public void event(final String tag) {
        manager.event(tag, logSource);
    }

    /**
     * @deprecated Please now use event()
     * @see event(final String tag)
     */
    public void usage(final String tag) {
        manager.event(tag, logSource);
    }

    /**
     * Log the presentation of a view.
     *
     * @param tag Unique tag for the presentation of a particular view.
     */
    public void view(final String tag) {
        manager.view(tag, logSource);
    }

    /**
     * Record that the user has been frustrated by your application.
     *
     * @param message The message that the user has given for frustration.
     */
    public void frustration(final String message) {
        manager.frustration(message, logSource);
    }

    /**
     * Log error.
     *
     * @param message Description of error.
     */
    public void error(final String message) {
        manager.error(message, logSource);
    }

    /**
     * Log error.
     *
     * @param message Description of error.
     * @param exception Exception that caused error.
     */
    public void error(final String message, final Throwable exception) {
        manager.error(message, exception, logSource);
    }

    /**
     * Log error.
     *
     * @param exception Exception that cause error.
     */
    public void error(final Throwable exception) {
        manager.error(exception, logSource);
    }
}
