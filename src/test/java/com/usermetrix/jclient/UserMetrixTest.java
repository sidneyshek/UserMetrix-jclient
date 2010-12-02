    package com.usermetrix.jclient;

import junit.framework.TestCase;

/**
 *
 */
public final class UserMetrixTest extends TestCase {

    /** The instance of UserMetrix we are testing. */
    private Logger um;

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
        //c.setTmpDirectory("/Users/cfreeman/Library/Application Support/OpenSHAPA/");
        c.addMetaData("build", "v1.02c");
        c.addMetaData("moo", "foo");
        UserMetrix.initalise(c);
        um = UserMetrix.getLogger(UserMetrixTest.class);
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
    public void testError() throws Exception {
        try {
            myMethod();
        } catch (Exception e) {
            um.error("Test Message", e);
            um.error(e);
            um.error("Just a message");
        }
    }


//    public void testEmpty() throws Exception {
//
//    }

    public void testUsage() throws Exception {
        try {
            um.event("newEvent");
            um.view("a new view");

            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            um.usage("testUsage");
            otherMethod();
            myMethod();
        } catch (Exception e) {
            um.error("Test Exception", e);
        }

    }

    private void myMethod() throws Exception {
        throw new Exception("blah");
    }

    private void otherMethod() throws Exception {
        um.usage("otherMethod");
    }
}
