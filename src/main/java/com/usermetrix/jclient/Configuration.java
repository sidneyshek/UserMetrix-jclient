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
    private String tmpDirectory;

    /** The id of the project on the user metrix server. */
    private int projectID;

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

    public void addMetaData(final String key, final String value) {
        metadata.put(key, value);
    }

    /**
     * @return The ID of the project on the server that this UserMetrix client
     * logs too.
     */
    public int getProjectID() {
        return projectID;
    }

    public void setTmpDirectory(final String directory) {
        tmpDirectory = directory;
    }

    public String getTmpDirectory() {
        return tmpDirectory;
    }

    public Set<Entry<String, String>> getMetaData() {
        return metadata.entrySet();
    }
}
