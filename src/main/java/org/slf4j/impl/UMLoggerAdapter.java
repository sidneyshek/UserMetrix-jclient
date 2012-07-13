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

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;

/**
 * This adapter maps SLF4J calls to the underlying UserMetrix logger, it also
 * provides a helper method for you to fetch the native UserMetrix logger
 * itself.
 */
public class UMLoggerAdapter extends MarkerIgnoringBase {

    /** The UserMetrix logger this adapter is pumping messages too. */
    final transient com.usermetrix.jclient.Logger logger;

    UMLoggerAdapter(com.usermetrix.jclient.Logger logger) {
        this.logger = logger;
    }

    /**
     * @return The native UserMetrix logger that this adapter wraps around.
     */
    com.usermetrix.jclient.Logger getUMLogger() {
        return logger;
    }

    public boolean isTraceEnabled() {
        return true;
    }

    public void trace(String msg) {
        this.logger.event(msg);
    }

    public void trace(String format, Object arg1) {
        FormattingTuple ft = MessageFormatter.format(format, arg1);
        this.logger.event(ft.getMessage());
    }

    public void trace(String format, Object arg1, Object arg2) {
        FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
        this.logger.event(ft.getMessage());
    }

    public void trace(String format, Object[] argArray) {
        FormattingTuple ft = MessageFormatter.format(format, argArray);
        this.logger.event(ft.getMessage());
    }

    public void trace(String msg, Throwable t) {
        this.logger.event(msg);
    }

    public boolean isDebugEnabled() {
        return true;
    }

    public void debug(String msg) {
        this.logger.event(msg);
    }

    public void debug(String format, Object arg1) {
        FormattingTuple ft = MessageFormatter.format(format, arg1);
        this.logger.event(ft.getMessage());
    }

    public void debug(String format, Object arg1, Object arg2) {
        FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
        this.logger.event(ft.getMessage());
    }

    public void debug(String format, Object[] argArray) {
        FormattingTuple ft = MessageFormatter.format(format, argArray);
        this.logger.event(ft.getMessage());
    }

    public void debug(String msg, Throwable t) {
        this.logger.event(msg);
    }

    public boolean isInfoEnabled() {
        return true;
    }

    public void info(String msg) {
        this.logger.event(msg);
    }

    public void info(String format, Object arg1) {
        FormattingTuple ft = MessageFormatter.format(format, arg1);
        this.logger.event(ft.getMessage());
    }

    public void info(String format, Object arg1, Object arg2) {
        FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
        this.logger.event(ft.getMessage());
    }

    public void info(String format, Object[] argArray) {
        FormattingTuple ft = MessageFormatter.format(format, argArray);
        this.logger.event(ft.getMessage());
    }

    public void info(String msg, Throwable t) {
        this.logger.event(msg);
    }

    public boolean isWarnEnabled() {
        return true;
    }

    public void warn(String msg) {
        logger.error(msg);
    }

    public void warn(String format, Object arg1) {
        FormattingTuple ft = MessageFormatter.format(format, arg1);
        this.logger.error(ft.getMessage());
    }

    public void warn(String format, Object arg1, Object arg2) {
        FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
        this.logger.error(ft.getMessage());
    }

    public void warn(String format, Object[] argArray) {
        FormattingTuple ft = MessageFormatter.format(format, argArray);
        this.logger.error(ft.getMessage());
    }

    public void warn(String msg, Throwable t) {
        this.logger.error(msg, t);
    }

    public boolean isErrorEnabled() {
        return true;
    }

    public void error(String msg) {
        this.logger.error(msg);
    }

    public void error(String format, Object arg1) {
        FormattingTuple ft = MessageFormatter.format(format, arg1);
        this.logger.error(ft.getMessage());
    }

    public void error(String format, Object arg1, Object arg2) {
        FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
        this.logger.error(ft.getMessage());
    }

    public void error(String format, Object[] argArray) {
        FormattingTuple ft = MessageFormatter.format(format, argArray);
        this.logger.error(ft.getMessage());
    }

    public void error(String msg, Throwable t) {
        this.logger.error(msg, t);
    }
}
