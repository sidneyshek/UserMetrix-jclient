package com.usermetrix.jclient;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class to send a log in the below format to usermetrix.com.
 *
 * v:1
 * system:
 *   os: <tag>
 *   start: <time&date>
 * log:
 *   <nano> - <source> - <msg>
 * end: <time&date>
 */
public final class UserMetrix {

    /** The current isntance of the UserMetrix logging tool. */
    private static UserMetrix instance = null;

    /** The current version of the log file this client generates. */
    private static final int LOG_VERSION = 1;

    /* Members of UserMetrix.  -----------------------------------------------*/
    /** The current source for log messages. */
    private Class logSource;

    /** The destination tmp file for the UserMetrix log. */
    private BufferedWriter logWriter;

    /** Private constructor. */
    private UserMetrix() {
        logSource = null;
        logWriter = null;
    }

    /**
     * Initalise the UserMetrix log - call this when you start your application.
     */
    public static void initalise() {
        try {
            if (instance == null) {
                instance = new UserMetrix();
                FileWriter fstream = new FileWriter("usermetrix.log");
                instance.setLogWriter(new BufferedWriter(fstream));
                instance.startLog();
            }
        } catch (Exception e) {
            instance = null;
        }
    }

    /**
     * Gets the instance of the UserMetrix logging tool for the supplied source.
     *
     * @param logSource The source of the logging message (usually a class).
     * @return The instance of the UserMetrix class for the supplied message
     * source.
     */
    public static UserMetrix getInstance(final Class logSource) {
        if (instance != null) {
            instance.setLogSource(logSource);
        }

        return instance;
    }

    /**
     * Shutdown the UserMetrix log - call this when your application gracefully
     * exits.
     */
    public static void shutdown() {
        // Terminate the UserMetrix logging session.
        if (instance != null) {
            instance.finishLog();
            instance = null;
        }
    }

    /**
     * Append an error message to your log.
     *
     * @param message What caused this error within your application.
     */
    public void error(final String message) {
        try {
            if (logWriter != null) {
                logWriter.write("  <nano> - " + logSource + " - " + message);
                logWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("Unable to write to file:" + e.toString());
        }
    }

    /**
     * Appened an error message to your log.
     *
     * @param message What cause this error within your application.
     * @param exception The exception that caused this error.
     */
    public void error(final String message, final Throwable exception) {
        // ToDo: Build call stack message.

        this.error(message);
    }

    private void startLog() {
        try {
            if (logWriter != null) {
                logWriter.write("v:" + LOG_VERSION);
                logWriter.newLine();
                logWriter.write("system:");
                logWriter.newLine();
                logWriter.write("  os: <tag>");
                logWriter.newLine();
                logWriter.write("  start: <time&date>");
                logWriter.newLine();
                logWriter.write("log:");
                logWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("Unable to write file:" + e.toString());
        }
    }

    private void finishLog() {
        try {
            if (logWriter != null) {
                logWriter.write("end:<time&date>");
                logWriter.newLine();
                logWriter.close();
            }
        } catch (IOException e) {
            System.out.println("Unable to close file:" + e.toString());
        }
    }

    private void setLogSource(final Class s) {
        logSource = s;
    }

    private void setLogWriter(final BufferedWriter o) {
        logWriter = o;
    }
}

