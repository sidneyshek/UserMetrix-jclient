package com.usermetrix.jclient;

import junit.framework.TestCase;

/**
 *
 * @author cfreeman
 */
public final class UserMetrixTest extends TestCase {

    /** The instance of UserMetrix we are testing. */
    private UserMetrix um;

    /**
     * Constructor.
     *
     * @param testName The name of the test.
     */
    public UserMetrixTest(final String testName) {
        super(testName);
    }

    /**
     * Called before each test.
     *
     * @throws Exception If unable to setUp.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Configuration c = new Configuration(1);
        //c.addMetaData("build", "v1.02b");
        UserMetrix.initalise(c);
        um = UserMetrix.getInstance(UserMetrixTest.class);
    }

    /**
     * Called after each test.
     *
     * @throws Exception If unable to tearDown.
     */
    @Override
    protected void tearDown() throws Exception {
        UserMetrix.shutdown();
        super.tearDown();
    }

    /**
     * Test of error method, of class UserMetrix.
     */
    public void testError2args() throws Exception {
        Thread.sleep(20);
        um.error("Test Message");
        Thread.sleep(200);
    }

    /**
     * Test of error method, of class UserMetrix.
     *//*
    public void testError3args() {
        um.error("Test Message 2", null);
    }*/

}
