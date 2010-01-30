package com.usermetrix.jclient;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

/**
 * Class to send a log in the below format to usermetrix.com.
 *
 * v:1
 * system:
 *   id: <uuid>
 *   os: <tag>
 *   start: <time&date>
 * meta:
 *   <key> - <value>
 * log:
 *   - type: <enum>
 *     time: <time&date>
 *     source: <source class>
 *     message: <msg>
 * end: <time&date>
 */
public final class UserMetrix {

    /** The current isntance of the UserMetrix logging tool. */
    private static UserMetrix instance = null;

    /** The current version of the log file this client generates. */
    private static final int LOG_VERSION = 1;

    /** The format to use when generating timestamps. */
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    /* Members of UserMetrix.  -----------------------------------------------*/
    /** The current source for log messages. */
    private Class logSource;

    /** The destination tmp file for the UserMetrix log. */
    private BufferedWriter logWriter;

    /** The clock that we fetch the time from. */
    private Calendar clock;

    private Configuration config;

    /** Private constructor. */
    private UserMetrix(final Configuration configuration) {
        logSource = null;
        logWriter = null;

        config = configuration;
        clock = Calendar.getInstance();
    }

    /**
     * Initalise the UserMetrix log - call this when you start your application.
     */
    public static void initalise(final Configuration configuration) {
        try {
            if (instance == null) {
                instance = new UserMetrix(configuration);
                FileWriter fstream = new FileWriter("usermetrix.log");
                instance.setLogWriter(new BufferedWriter(fstream));
                instance.startLog();
                UUID u = UUID.randomUUID();
                System.out.println(u.toString());
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
                logWriter.write("  - type: error");
                logWriter.newLine();
                logWriter.write("    time: " + SDF.format(clock.getTime()));
                logWriter.newLine();
                logWriter.write("    source: " + logSource);
                logWriter.newLine();
                logWriter.write("    message: " + message);
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

                // Write the unique client identifier to the log.
                logWriter.write("  id: ");
                logWriter.write("<UUID>");
                logWriter.newLine();

                // Write details of the operating system out to the log.
                logWriter.write("  os: ");
                logWriter.write(System.getProperty("os.name") + " - ");
                logWriter.write(System.getProperty("os.version"));
                logWriter.newLine();

                // Write the application start time out to the log.
                logWriter.write("  start: ");
                logWriter.write(SDF.format(clock.getTime()));
                logWriter.newLine();

                // Dump meta data stored in the configuration.
                logWriter.write("meta:");
                for (Map.Entry<String, String> e : config.getMetaData()) {
                    logWriter.write("   - [" + e.getKey() + ", " + e.getValue() + "]");
                }
                logWriter.newLine();

                // Begin the log.
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
                // Write the application end time out to the log.
                logWriter.write("end: ");
                logWriter.write(SDF.format(clock.getTime()));
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

