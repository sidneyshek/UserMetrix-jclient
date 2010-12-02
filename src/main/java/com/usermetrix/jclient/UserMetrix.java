package com.usermetrix.jclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

/**
 * Class to send a log in the below format to usermetrix.com.
 *
 * ---
 * v: 1
 * system:
 *   id: <uuid>
 *   os: <tag>
 *   start: <time&date>
 * meta:
 *   - <key>: <value>
 * log:
 *   - type: <enum>
 *     time: <milliseconds>
 *     source: <source class>
 *     message: <msg>
 *     stack:
 *       - class: <source class>
 *         line: <line number>
 *         method: <method>
 * duration: <milliseconds>
 */
public final class UserMetrix {

    /** The current isntance of the UserMetrix logging tool. */
    private static UserMetrix instance = null;

    /** The current version of the log file this client generates. */
    private static final int LOG_VERSION = 1;

    /** The format to use when generating timestamps. */
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    /** The line ending to use when sending log files. */
    private static final String LINE_END = "\r\n";

    /** Definition of two hyphens to dump when sending log files. */
    private static final String TWO_HYPHENS = "--";

    /** Definition the boundary to use when sending log files. */
    private static final String BOUNDARY =  "*****";

    /** The size of the buffer to use when sending log files. */
    private static final int BUFFER_SIZE = 1048576;

    /* Members of UserMetrix.  -----------------------------------------------*/
    /** The destination stream for the tmp usermetrix log. */
    private FileWriter logStream;

    /** The destination tmp file for the UserMetrix log. */
    private BufferedWriter logWriter;

    /** The clock that we fetch the time from. */
    private Calendar clock;

    /** The UUID for this client. */
    private String clientID;

    /** The configuration for this UserMetrix client. */
    private Configuration config;

    /** Start time in milliseconds. */
    private long startTime;

    /** Are we permitted to send logs to the server - this defaults to true. */
    private boolean canSendLogs;

    /**
     * Private constructor.
     *
     * @param configuration The configuration class to use for this UserMetrix
     * client.
     */
    private UserMetrix(final Configuration configuration) {
        logWriter = null;

        config = configuration;
        clock = Calendar.getInstance();
        canSendLogs = true;
    }

    /**
     * Are we permitted to send usage logs to the UserMetrix server?
     *
     * @param canSend True if we are able to send logs to the UserMetrix server
     * false otherwise.
     */
    public static void setCanSendLogs(final boolean canSend) {
        instance.canSendLogs = canSend;
    }

    /**
     * Initalise the UserMetrix log - call this when you start your application.
     *
     * @param configuration The configuration client to use for this UserMetrix
     * client.
     */
    public static void initalise(final Configuration configuration) {
        try {
            if (instance == null) {
                instance = new UserMetrix(configuration);

                // Check if the temp directory exists - if not, create it.
                File tmpDirectory = new File(configuration.getTmpDirectory());
                if (!tmpDirectory.exists()) {
                    tmpDirectory.mkdirs();
                }

                // Determine UUID for this client.
                File idFile = new File(configuration.getTmpDirectory() + "usermetrix.id");

                // ID file exists on disk - read UUID from file.
                if (idFile.exists()) {
                    FileReader idStream = new FileReader(configuration.getTmpDirectory()
                                                         + "usermetrix.id");
                    BufferedReader idReader = new BufferedReader(idStream);
                    instance.setUniqueID(idReader.readLine());
                    idReader.close();
                    idStream.close();

                // ID file does not exist - generate a new UUID.
                } else {
                    UUID u = UUID.randomUUID();
                    instance.setUniqueID(u.toString());

                    FileWriter idStream = new FileWriter(configuration.getTmpDirectory()
                                                         + "usermetrix.id");
                    BufferedWriter idWriter = new BufferedWriter(idStream);
                    idWriter.write(u.toString());
                    idWriter.newLine();
                    idWriter.close();
                    idStream.close();
                }

                instance.setLogDestination(configuration.getTmpDirectory()
                                           + "usermetrix.log");
                instance.startLog();
            }

        } catch (Exception e) {
            System.err.println("UserMetrix: Unable to initalise usermetrix." + e);
            instance = null;
        }
    }

    /**
     * Creates a logger that can be used to log messages.
     *
     * @param logSource The source of the logging message (usually a class).
     * @return The instance of the UserMetrix class for the supplied message
     * source.
     */
    public static Logger getLogger(final Class logSource) {
        if (instance != null) {
            return new Logger(logSource, instance);
        }

        return null;
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

    public void view(final String tag, final Class source) {
        try {
            if (logWriter != null) {
                logWriter.write("  - type: view");
                writeMessageDetails(tag, source);
            }
        } catch (IOException e) {
            System.err.println("UserMetrix: Unable to write to file." + e.toString());
        }
    }

    /**
     * Append a usage tag to your log.
     *
     * @param tag The unique tag to use for this particular type of software usage.
     * @param source The source of the log message.
     */
    public void event(final String tag, final Class source) {
        try {
            if (logWriter != null) {
                logWriter.write("  - type: usage");
                writeMessageDetails(tag, source);
            }
        } catch (IOException e) {
            System.err.println("UserMetrix: Unable to write to file." + e.toString());
        }
    }

    /**
     * Append a frustration tag to your log.
     *
     * @param tag The tag (perhaps user specified) for this frustration.
     * @param source The source of the frustration (this is not mega-relevant, frustrations
     * are global to their origin).
     */
    public void frustration(final String tag, final Class source) {
        try {
            if (logWriter != null) {
                logWriter.write("  - type: frustration");
                writeMessageDetails(tag, source);
            }
        } catch (IOException e) {
            System.err.println("UserMetrix: Unable to write to file." + e.toString());
        }
    }

    /**
     * Append an error message to your log.
     *
     * @param message What caused this error within your application.
     * @param source The source of the log message.
     */
    public void error(final String message, final Class source) {
        try {
            if (logWriter != null) {
                logWriter.write("  - type: error");
                writeMessageDetails(message, source);
            }
        } catch (IOException e) {
            System.err.println("UserMetrix: Unable to write to file." + e.toString());
        }
    }

    /**
     * Dumps additional message details to disk.
     *
     * @param message The message that is being reported.
     * @param source The source of the log message.
     *
     * @throws IOException If unable to write the details to the log.
     */
    private void writeMessageDetails(final String message,
                                     final Class source) throws IOException {
        if (logWriter != null) {
            logWriter.newLine();
            logWriter.write("    time: " + (System.currentTimeMillis() - this.startTime));
            logWriter.newLine();
            logWriter.write("    source: " + source);
            logWriter.newLine();
            logWriter.write("    message: " + message);
            logWriter.newLine();
        }
    }

    /**
     * Append an error message to your log.
     *
     * @param message What cause this error within your application.
     * @param exception The exception that caused this error.
     * @param source The source of the log message.
     */
    public void error(final String message, final Throwable exception, final Class source) {
        this.error(message, source);
        this.logStack(exception);
    }

    /**
     * Append an error message to your log.
     *
     * @param exception The exception that caused this error.
     * @param source The source of the log message
     */
    public void error(final Throwable exception, final Class source) {
        this.error("null", source);
        this.logStack(exception);
    }

    /**
     * Writes the stack trace of an exception to your log.
     *
     * @param exception The exception to write to the log.
     */
    private void logStack(final Throwable exception) {
        try {
            if (logWriter != null) {
                logWriter.write("    stack:");
                logWriter.newLine();
                for (StackTraceElement e : exception.getStackTrace()) {
                    logWriter.write("      - class: " + e.getClassName());
                    logWriter.newLine();
                    logWriter.write("        line: " + e.getLineNumber());
                    logWriter.newLine();
                    logWriter.write("        method: " + e.getMethodName());
                    logWriter.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("UserMetrix: Unable to write to file." + e.toString());
        }
    }

    private void startLog() {
        try {
            if (logWriter != null) {
                logWriter.write("---");
                logWriter.newLine();
                logWriter.write("v: " + LOG_VERSION);
                logWriter.newLine();
                logWriter.write("system:");
                logWriter.newLine();

                // Write the unique client identifier to the log.
                logWriter.write("  id: " + clientID);
                logWriter.newLine();

                // Write details of the operating system out to the log.
                logWriter.write("  os: ");
                logWriter.write(System.getProperty("os.name") + " - ");
                logWriter.write(System.getProperty("os.version"));
                logWriter.newLine();

                // Write the application start time out to the log.
                logWriter.write("  start: ");
                logWriter.write(SDF.format(clock.getTime()));
                startTime = System.currentTimeMillis();
                logWriter.newLine();

                // Dump meta data stored in the configuration.
                logWriter.write("meta:");
                logWriter.newLine();
                for (Map.Entry<String, String> e : config.getMetaData()) {
                    logWriter.write("  - " + e.getKey() + ": " + e.getValue());
                    logWriter.newLine();
                }

                // Begin the log.
                logWriter.write("log:");
                logWriter.newLine();
            }
        } catch (IOException e) {
            System.err.println("UserMetrix: Unable to write file." + e.toString());
        }
    }

    private void finishLog() {
        try {
            if (logWriter != null) {
                // Write the application end time out to the log.
                logWriter.write("duration: " + (System.currentTimeMillis() - this.startTime));
                logWriter.newLine();
                logWriter.close();

                if (config.getProjectID() != 0) {
                    sendLog();
                }
            }
        } catch (IOException e) {
            System.err.println("UserMetrix: Unable to close file." + e.toString());
        }
    }

    private void sendLog() {
        if (!canSendLogs) {
            // Not permitted to send logs - leave method.
            return;
        }

        try {
            // Send data
            URL url = new URL("http://usermetrix.com/projects/" + config.getProjectID() + "/log");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);

            // Write the header of the multipart HTTP POST request.
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(TWO_HYPHENS + BOUNDARY + LINE_END);
            wr.writeBytes("Content-Disposition: form-data; name=\"upload\";"
                    + " filename=\"" + "usermetrix.log" +"\"" + LINE_END);
            wr.writeBytes(LINE_END);


            // Read the log and append it as an attachment to the POST request.
            FileInputStream fileInputStream = new FileInputStream(new File(config.getTmpDirectory()
                                                                           + "usermetrix.log"));
            int bytesAvailable = fileInputStream.available();
            int bufferSize = Math.min(bytesAvailable, BUFFER_SIZE);
            byte[] buffer = new byte[bufferSize];

            int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                wr.write(buffer, 0, bufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // Write the footer of the multipart HTTP POST request.
            wr.writeBytes(LINE_END);
            wr.writeBytes(TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + LINE_END);
            wr.flush();

            // Get the response from the server.
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line; while ((line = rd.readLine()) != null) {
                System.err.println(line);
            }

            // Close all our streams.
            fileInputStream.close();
            wr.close();
            rd.close();
        } catch (UnknownHostException e) {
            // Silently ignore unknown host exception - can't connect to the internet.
        } catch (Exception e) {
            System.err.println("Unable to send log - " + e);
        }
    }

    private void setLogDestination(final String logFile) {
        try {
            logStream = new FileWriter(logFile);
            logWriter = new BufferedWriter(logStream);
        } catch (IOException e) {
            System.err.println("UserMetrix: Unable to set log location." + e.toString());
        }
    }

    private void setUniqueID(final String id) {
        clientID = id;
    }
}

