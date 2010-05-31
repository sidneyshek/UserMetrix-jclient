package com.usermetrix.jclient;

/**
 * Class for logging messages to the central UserMetrix server.
 */
public final class Logger {
    /** The source class for messages from this logger. */
    private Class logSource;

    /** The UserMetrix manager responsible for dispatching messages. */
    private UserMetrix manager;

    /**
     * Constructor - Don't create this directly, rather obtain one from the
     * UserMetrix class (UserMetrix.getLogger(yourClass.class);.
     *
     * @param source The source of messages originating from this logger.
     * @param logManager The manager Responsible for dispatching messages.
     */
    public Logger(final Class source, final UserMetrix logManager) {
        logSource = source;
        manager = logManager;
    }

    /**
     * Log usage pattern.
     *
     * @param tag Unique tag for usage pattern.
     */
    public void usage(final String tag) {
        manager.usage(tag, logSource);
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