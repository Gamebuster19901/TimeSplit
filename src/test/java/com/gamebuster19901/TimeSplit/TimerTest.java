package com.gamebuster19901.TimeSplit;

import java.time.Duration;

import org.junit.Test;
import static org.junit.Assert.*;

import com.gamebuster19901.timesplit.timing.Timer;

public class TimerTest {
	
	@Test
	public void doTimerTesting() {
		Timer timer = new Timer();
		
		testTimer(timer, "0.000");
		
		timer.addTime(Duration.ofNanos(9999));
		
		testTimer(timer, "0.000");
		
		timer.addTime(Duration.ofNanos(9999999));
		
		testTimer(timer, "0.009");
		
		timer.addTime(Duration.ofNanos(99999999));
		
		testTimer(timer, "0.099");
		
		timer.addTime(Duration.ofNanos(999999999));
		
		testTimer(timer, "0.999");
		
		timer.addTime(Duration.ofSeconds(1));
		
		testTimer(timer, "1.000");
		
		timer.addTime(Duration.ofSeconds(10));
		
		testTimer(timer, "10.000");
		
		timer.addTime(Duration.ofMinutes(1));
		
		testTimer(timer, "1:00.000");
		
		timer.addTime(Duration.ofMinutes(10));
		
		testTimer(timer, "10:00.000");
		
		timer.addTime(Duration.ofHours(1));
		
		testTimer(timer, "1:00:00.000");
		
		timer.addTime(Duration.ofHours(10));
		
		testTimer(timer, "10:00:00.000");
		
		timer.addTime(Duration.ofDays(1));
		
		testTimer(timer, "1d 00:00:00.000");
		
		timer.addTime(Duration.ofDays(7));
		
		testTimer(timer, "1w 0d 00:00:00.000");
		
		timer.addTime(Duration.ofDays(8).plus(Duration.ofHours(1)).plus(Duration.ofMinutes(1)).plus(Duration.ofSeconds(1)).plus(Duration.ofNanos(1000000)));
		
		testTimer(timer, "1w 1d 01:01:01.001");
	}
	
	private static void testTimer(Timer timer, String expectedValue) {
		assertEquals("\n\n" + timer.toString() + "!=" + expectedValue + "\n\n" + (timer.getDuration()), timer.toString(), expectedValue);
	}
	
}
