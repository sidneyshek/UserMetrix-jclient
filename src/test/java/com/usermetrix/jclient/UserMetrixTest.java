package com.usermetrix.jclient;

import junit.framework.Test;
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
    protected void setUp() throws Exception {
        super.setUp();
        UserMetrix.initalise();
        um = UserMetrix.getInstance(UserMetrixTest.class);
    }

    /**
     * Called after each test.
     *
     * @throws Exception If unable to tearDown.
     */
    protected void tearDown() throws Exception {
        UserMetrix.shutdown();
        super.tearDown();
    }

    /**
     * Test of error method, of class UserMetrix.
     */
    public void testError2args() {
        um.error("Test Message");
    }

    /**
     * Test of error method, of class UserMetrix.
     */
    public void testError3args() {
        um.error("Test Message", null);
    }

}
