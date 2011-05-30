package com.usermetrix.jclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import junit.framework.TestCase;

/**
 *
 */
public final class UserMetrixTest extends TestCase {

    /** The instance of UserMetrix we are testing. */
    private Logger um;

    /**
     * Called before each test.
     *
     * @throws Exception If unable to setUp.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Configuration c = new Configuration(14);
        UserMetrix.initalise(c);
        um = UserMetrix.getLogger(UserMetrixTest.class);
    }

    /**
     * Called after each test.
     *
     * @throws Exception If unable to tearDown.
     *//*
    @Override
    protected void tearDown() throws Exception {
        //UserMetrix.shutdown();
        super.tearDown();
    }*/
    
    public void testDeleteOnSend() throws Exception {
        UserMetrix.setCanSendLogs(false);
        File destinationLog = new File("usermetrix.log");
        
        // After we have started a log - it should exist.
        um.event("myEvent");
        assertTrue(destinationLog.exists());
        
        // After we have sent a log - it should no longer exist.
        UserMetrix.shutdown();
        assertFalse(destinationLog.exists());
        
        // If we start UserMetrix again - with an existing log it should send
        // that log first - delete it and start a new one.
        
        // Log should not exist at this point - create a new log.
        FileWriter tmpStream = new FileWriter(destinationLog);
        BufferedWriter tmpWriter = new BufferedWriter(tmpStream);
        tmpWriter.write("Dummy File");
        tmpWriter.close();
        tmpStream.close();

        Configuration c = new Configuration(14);
        UserMetrix.initalise(c);
        um = UserMetrix.getLogger(UserMetrixTest.class);
        // File should exist - but not contain "dummy file".
        assertTrue(destinationLog.exists());
        FileReader inStream = new FileReader(destinationLog);
        BufferedReader tmpReader = new BufferedReader(inStream);
        assertFalse(tmpReader.readLine().equals("DummyFile"));
        inStream.close();
        tmpReader.close();
        
        // Sending log should mean it no longer exists.
        UserMetrix.shutdown();
        assertFalse(destinationLog.exists());
    }

    /*
    public void testEvent() throws Exception {
        um.event("myEvent");
        
    }*/

    /**
     * Test of error method, of class UserMetrix.
     *//*
    public void testError() throws Exception {
        try {
            myMethod();
        } catch (Exception e) {
            um.error("Test Message", e);
            um.error(e);
            um.error("Just a message");
        }
    }*/


//    public void testEmpty() throws Exception {
//
//    }

//    public void testUsage() throws Exception {
//        try {
//            um.event("newEvent");
//            um.view("a new view");
//            um.frustration("I am frustrated");
//
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            um.usage("testUsage");
//            otherMethod();
//            myMethod();
//        } catch (Exception e) {
//            um.error("Test Exception", e);
//        }
//
//    }

    /*
    private void myMethod() throws Exception {
        otherMethod();
        throw new Exception("blah");
    }

    private void otherMethod() throws Exception {
        um.usage("otherMethod");
    }*/
}
