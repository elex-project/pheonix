package com.elex_project.pheonix;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KoreanLunisolarCalendarTest {
	static final Object[][] samples = {
			{2028, 5, 24, 2028, 5, 1, false, "무신", "무오", "기유"},
			{2017, 10, 13, 2017, 8, 24, false, "정유", "기유", "계유"},
			{2036, 8, 7, 2036, 6, 16, true, "병진", "을미", "병오"},
			{1919, 3, 1, 1919, 1, 29, false, "기미", "병인", "임자"},
			{1905, 3, 1, 1905, 1, 26, false, "을사", "무인", "기해"},
			{1907, 5, 1, 1907, 3, 19, false, "정미", "갑진", "경술"},
			{1908, 5, 1, 1908, 4, 2, false, "무신", "정사", "병진"},
			{1910, 3, 1, 1910, 1, 20, false, "경술", "무인", "을축"},
			{1946, 11, 18, 1946, 10, 25, false, "병술", "기해", "병신"},
			{1976, 2, 7, 1976, 1, 8, false, "병진", "경인", "기축"},
			{2002, 7, 17, 2002, 6, 8, false, "임오", "정미", "병술"},
			{2018, 2, 16, 2018, 1, 1, false, "무술", "갑인", "기묘"},
			{2024, 2, 10, 2024, 1, 1, false, "갑진", "병인", "갑진"},
	};


	@Test
	public void checkDates() {
		KoreanLunisolarCalendar kCal = null;
		try {
			kCal = new KoreanLunisolarCalendar(1919, 3 - 1, 1);
		} catch (OutOfCalendarDataException e) {
			e.printStackTrace();
		}
		//kCal.set(2014, 9, 23);
		for (int i = 0; i < 3; i++) {
			System.out.println(kCal.toString());
			kCal.add(KoreanLunisolarCalendar.DATE, 1);
		}
	}

	@Test
	public void checkDatesL2S() {
		KoreanLunisolarCalendar kCal = null;
		try {
			kCal = new KoreanLunisolarCalendar(2056, 12 - 1, 30, false);
		} catch (OutOfCalendarDataException e) {
			e.printStackTrace();
		}
		System.out.println(kCal.toString());
	}

	@Test
	public void checkS2L() {

		for (int i = 0; i < samples.length; i++) {
			KoreanLunisolarCalendar kCal = null;
			try {
				kCal = new KoreanLunisolarCalendar((int) samples[i][0], (int) samples[i][1] - 1, (int) samples[i][2]);
			} catch (OutOfCalendarDataException e) {
				e.printStackTrace();
			}
			assertEquals((int) samples[i][3], kCal.getLunarYear());
			assertEquals((int) samples[i][4] - 1, kCal.getLunarMonth());
			assertEquals((int) samples[i][5], kCal.getLunarDate());
			assertEquals((boolean) samples[i][6], kCal.isLunarLeapMonth());
			assertEquals(samples[i][7].toString(), kCal.getSexagenaryYear().toString());
			assertEquals(samples[i][8].toString(), kCal.getSexagenaryMonth().toString());
			assertEquals(samples[i][9].toString(), kCal.getSexagenaryDay().toString());
			System.out.println(kCal.toString());
		}

	}

	@Test
	public void checkL2S() {
		for (int i = 0; i < samples.length; i++) {
			KoreanLunisolarCalendar kCal = null;
			try {
				kCal = new KoreanLunisolarCalendar((int) (samples[i][3]), (int) (samples[i][4]) - 1, (int) (samples[i][5]), (boolean) (samples[i][6]));
			} catch (OutOfCalendarDataException e) {
				e.printStackTrace();
			}
			assertEquals((int) samples[i][0], kCal.getYear());
			assertEquals((int) samples[i][1] - 1, kCal.getMonth());
			assertEquals((int) samples[i][2], kCal.getDate());
			//assertEquals((int)samples[i][2], kCal.getDate());
			assertEquals(samples[i][7].toString(), kCal.getSexagenaryYear().toString());
			assertEquals(samples[i][8].toString(), kCal.getSexagenaryMonth().toString());
			assertEquals(samples[i][9].toString(), kCal.getSexagenaryDay().toString());
			System.out.println(kCal.toString());
		}

	}

	//@Test
	public void internalTest() {
		Calendar c0 = Calendar.getInstance();
		c0.set(1901, 2 - 1, 19);

		Calendar c1 = Calendar.getInstance();
		c1.set(1908, 4 - 1, 1);
		Calendar c2 = Calendar.getInstance();
		c2.set(1908, 4 - 1, 2);

		long diff = c1.getTimeInMillis() - c0.getTimeInMillis();
		int diffDates = (int) (Math.floor(diff / 1000 / 60 / 60 / 24));
		System.out.println("날짜 1: " + diffDates);
		diff = c2.getTimeInMillis() - c0.getTimeInMillis();
		diffDates = (int) (Math.floor(diff / 1000 / 60 / 60 / 24));
		System.out.println("날짜 2: " + diffDates);
	}

	@Test
	public void mx() throws OutOfCalendarDataException {
		KoreanLunisolarCalendar kCal = null;
		kCal = new KoreanLunisolarCalendar(1919, 3 - 1, 1);
		System.out.println(kCal);

		kCal = new KoreanLunisolarCalendar(1919, 1 - 1, 29, false);
		System.out.println(kCal);
	}

	//@Test
	public void mmm() {
		Calendar c = new GregorianCalendar(TimeZone.getTimeZone("Asia/Seoul"));
		c.set(1901, 1, 19);
		c.getTimeInMillis();
		//(1901, 1, 19)
	}
}
