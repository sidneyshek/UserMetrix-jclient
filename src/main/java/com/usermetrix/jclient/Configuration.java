package com.usermetrix.jclient;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 */
public final class Configuration {

    private Map<String, String> metadata;

    private String tmpDirectory;

    public Configuration() {
        metadata = new HashMap<String, String>();
    }

    public void addMetaData(final String key, final String value) {
        metadata.put(key, value);
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
