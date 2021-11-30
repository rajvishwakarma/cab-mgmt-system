package com.cms.utils;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class GenericUtils {
	private static TimeUnit chooseUnit(long nanos) {
		if (DAYS.convert(nanos, MILLISECONDS) > 0) {
			return DAYS;
		}
		if (HOURS.convert(nanos, MILLISECONDS) > 0) {
			return HOURS;
		}
		if (MINUTES.convert(nanos, MILLISECONDS) > 0) {
			return MINUTES;
		}
		if (SECONDS.convert(nanos, MILLISECONDS) > 0) {
			return SECONDS;
		}
		return MILLISECONDS;
	}

	private static String abbreviate(TimeUnit unit) {
		switch (unit) {
		case MILLISECONDS:
			return "ms";
		case SECONDS:
			return "s";
		case MINUTES:
			return "min";
		case HOURS:
			return "h";
		case DAYS:
			return "d";
		default:
			throw new AssertionError();
		}
	}
	
	public static String getDuration(long startTS, long endTS) {
		long diff = endTS - startTS;
		TimeUnit unit = GenericUtils.chooseUnit(diff);
		double value = (double) diff / MILLISECONDS.convert(1, unit);
		return String.format(Locale.ROOT, "%.4g", value) + " " + abbreviate(unit);
	}
	
	public static String getDuration(long diffTS) {
		TimeUnit unit = GenericUtils.chooseUnit(diffTS);
		double value = (double) diffTS / MILLISECONDS.convert(1, unit);
		return String.format(Locale.ROOT, "%.4g", value) + " " + abbreviate(unit);
	}
}
