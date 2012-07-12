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
 *
 */
public class UMLoggerAdapter extends MarkerIgnoringBase {

    /** The UserMetrix logger this adapter is pumping messages too. */
    final transient com.usermetrix.jclient.Logger logger;

    UMLoggerAdapter(com.usermetrix.jclient.Logger logger) {
        this.logger = logger;
    }

    com.usermetrix.jclient.Logger getUMLogger() {
        return logger;
    }

    public boolean isTraceEnabled() {
        return true;
    }

    public void trace(String msg) {
        this.logger.view(msg);
    }

    public void trace(String format, Object arg1) {
        FormattingTuple ft = MessageFormatter.format(format, arg1);
        this.logger.view(ft.getMessage());
    }

    public void trace(String format, Object arg1, Object arg2) {
        FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
        this.logger.view(ft.getMessage());
    }

    public void trace(String format, Object[] argArray) {
        FormattingTuple ft = MessageFormatter.format(format, argArray);
        this.logger.view(ft.getMessage());
    }

    public void trace(String msg, Throwable t) {
        this.logger.view(msg);
    }

    public boolean isDebugEnabled() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void debug(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void debug(String string, Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void debug(String string, Object o, Object o1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void debug(String string, Object[] os) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void debug(String string, Throwable thrwbl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isInfoEnabled() {
        return false;
    }

    public void info(String string) {
        // info calls are ignored by UserMetrix.
    }

    public void info(String string, Object o) {
        // info calls are ignored by UserMetrix.
    }

    public void info(String string, Object o, Object o1) {
        // info calls are ignored by UserMetrix.
    }

    public void info(String string, Object[] os) {
        // info calls are ignored by UserMetrix.
    }

    public void info(String string, Throwable thrwbl) {
        // info calls are ignored by UserMetrix.
    }

    public boolean isWarnEnabled() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void warn(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void warn(String string, Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void warn(String string, Object[] os) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void warn(String string, Object o, Object o1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void warn(String string, Throwable thrwbl) {
        throw new UnsupportedOperationException("Not supported yet.");
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
