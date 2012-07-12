/*
 * Configuration.java
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

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.yaml.snakeyaml.Yaml;

/**
 * Configuration object for UserMetrix.
 */
public final class Configuration {

    /** Metadata associated with this run of UserMetrix. */
    @Deprecated
    private Map<String, String> metadata;

    /** The directory to store log files temporarily - including id files. */
    private String tmpDirectory = "";

    /** The id of the project on the UserMetrix server. */
    private int projectID = 0;

    /** Are we permitted to send logs to the UserMetrix server? */
    private boolean canSendLogs = true;

    /**
     * Constructor.
     *
     * Creates a configuration object from a file on disk. Searches the
     * classpath for a file named "UserMetrix.yml" and uses that to populate the
     * configuration object.
     */
    public Configuration() throws Exception {
        // Look for a UserMetrix.yml file in the class path.
        InputStream in = this.getClass().getClassLoader()
                                .getResourceAsStream("UserMetrix.yml");

        if (in == null) {
            throw new Exception("Unable to configure UserMetrix, because I can't"
                                + "find UserMetrix.yml on the classpath.");
        }

        // Have managed to find a UserMetrix.yml file on the class path - parse
        // it and populate the configuration object.
        Yaml yaml = new Yaml();
        Map<String, Object> configContents = (Map<String, Object>) yaml.load(in);
        projectID = (Integer) configContents.get("projectID");
        canSendLogs = (Boolean) configContents.get("canSend");

        if (configContents.get("tmpDirectory") != null) {
            tmpDirectory = (String) configContents.get("tmpDirectory");
        }
    }

    /**
     * Constructor.
     *
     * @param newProjectID The ID of the project on the server that this
     * UserMetrix client logs too.
     */
    public Configuration(final int newProjectID) {
        projectID = newProjectID;
        metadata = new HashMap<String, String>();
    }

    /**
     * @return The ID of the project on the server that this UserMetrix client
     * logs too.
     */
    public int getProjectID() {
        return projectID;
    }

    /**
     * Sets the temporary directory that we output usermetrix.log and
     * usermetrix.id files too. The usermetrix.id file should be persistant.
     *
     * @param directory The new temp directory to use.
     */
    public void setTmpDirectory(final String directory) {
        tmpDirectory = directory;
    }

    /**
     * @return The temporary directory that we are outputing usermetrix.log and
     * usermetrix.id files too.
     */
    public String getTmpDirectory() {
        return tmpDirectory;
    }

    /**
     *
     *
     * @params canSend Set to true if you want to transmit logs to the central
     * UserMetrix server.
     */
    public void setCanSendLogs(final boolean canSend) {
       this.canSendLogs = canSend;
    }

    /**
     * @return True if permitted to send logs to the central UserMetrix server,
     * false otherwise.
     */
    public boolean canSendLogs() {
        return this.canSendLogs;
    }

    @Deprecated
    public void addMetaData(final String key, final String value) {
        metadata.put(key, value);
    }

    @Deprecated
    public Set<Entry<String, String>> getMetaData() {
        return metadata.entrySet();
    }
}
