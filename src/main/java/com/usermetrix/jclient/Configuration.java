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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 */
public final class Configuration {

    /** Metadata assocated with this run of UserMetrix. */
    private Map<String, String> metadata;

    /** The directory to store log files temporarily - including id files. */
    private String tmpDirectory = "";

    /** The id of the project on the UserMetrix server. */
    private int projectID = 0;

    /**
     * Private default constructor.
     */
    private Configuration() {
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

    public void addMetaData(final String key, final String value) {
        metadata.put(key, value);
    }

    public Set<Entry<String, String>> getMetaData() {
        return metadata.entrySet();
    }
}
