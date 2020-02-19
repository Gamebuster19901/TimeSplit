package com.gamebuster19901.timesplit.timing;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

public class Timer extends Clock {

	private static final Clock SYSTEM_CLOCK = Clock.systemDefaultZone();
	
	private Duration startingDuration = Duration.ZERO;
	private Instant beginning;
	
	public Timer() {}
	
	public Timer(Duration startingDuration) {
		addTime(startingDuration);
	}
	
	@Override
	public ZoneId getZone() {
		return SYSTEM_CLOCK.getZone();
	}

	@Override
	public Clock withZone(ZoneId zone) {
		return SYSTEM_CLOCK.withZone(zone);
	}

	@Override
	public Instant instant() {
		return SYSTEM_CLOCK.instant();
	}
	
	public void start() {
		if(beginning == null) {
			beginning = instant();
		}
		else {
			throw new IllegalStateException("Timer has already been started previously");
		}
	}
	
	public void addTime(Duration duration) {
		if(beginning == null) {
			startingDuration = startingDuration.plus(duration);
		}
		else {
			throw new IllegalStateException("Cannot add time to a timer that has been started previously");
		}
	}
	
	public Duration getDuration() {
		if(beginning == null) {
			return startingDuration;
		}
		else {
			return Duration.between(beginning, instant()).plus(startingDuration);
		}
	}
	
	public String toString() {
		return format(this.getDuration());
	}
	
	public static String format(Duration duration) {
		String format = "%6$s";
		
		if(duration.toMinutes() != 0) {
			format = "%5$02d." + format;
			if (duration.toHours() == 0) {
				format = "%4$d:" + format;
			}
			else {
				format = "%4$02d:" + format;
			}
		}
		else {
			format = "%5$d." + format;
		}
		
		if(duration.toHours() != 0) {
			if (duration.toDays() == 0) {
				format = "%3$d:" + format;
			}
			else {
				format = "%3$02d:" + format;
			}
		}
		
		if(duration.toDays() != 0) {
			long localWeeks = duration.toDays() / 7;
			if (localWeeks == 0) {
				format = "%2$dd " + format;
			}
			else {
				format = "%1$dw %2$dd " + format;
			}
		}
		
		String readableTime = String.format(format, 
				duration.toDaysPart() / 7, //1
				duration.toDaysPart() % 7, //2
				duration.toHoursPart(),    //3
				duration.toMinutesPart(),  //4
				duration.toSecondsPart(),  //5
				String.format("%09d", duration.toNanosPart()).substring(0, 3) //6
		);
		
		return readableTime;
	}
	
}
