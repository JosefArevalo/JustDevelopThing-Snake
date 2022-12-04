package org.psnbtech;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ClockTest {
	//Clock c;
	
	@Before
	public void setUp() throws Exception {
		c = new Clock(1);
	}


	@After
	public void tearDown() throws Exception {
		c = null;
	}


	@Test
	public void testIsPaused() {
		c.isPaused();
	}

	@Test
	public void testPeekElapsedCycle() {
		c.peekElapsedCycle();
	}
	
	@Test
	public void testPeekElapsedCycle2() throws InterruptedException {
		TimeUnit.SECONDS.sleep(10);
		c.update();
		c.peekElapsedCycle();
	}
	
    @Test 
    public void testSetPaused() {
        c.setPaused(true);
        assertTrue("true", c.isPaused());
    }
    
    @Test 
    public void testHasElapsedCycle() throws InterruptedException{
        TimeUnit.SECONDS.sleep(0);
        c.update();
        c.peekElapsedCycle();
        assertFalse("false", c.hasElapsedCycle());
        
        TimeUnit.SECONDS.sleep(10);
        c.update();
        c.peekElapsedCycle();
        assertTrue("true", c.hasElapsedCycle());
    }


}
