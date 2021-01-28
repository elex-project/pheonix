/*
Copyright 2021 (c) Elex. All Rights Reserved.
https://www.elex-project.com/
 */
package com.elex_project.pheonix;

interface ILunisolarCalendar {
	long getMaxSupportedDateTime();

	long getMinSupportedDateTime();

	/**
	 * 음력 년도
	 * @return year
	 */
	int getLunarYear();

	/**
	 * 음력 월
	 * @return month, 0~11
	 */
	int getLunarMonth();

	/**
	 * 윤달 여부
	 * @return leap month?
	 */
	boolean isLunarLeapMonth();

	/**
	 * 음력 일
	 * @return day
	 */
	int getLunarDate();

	CelestialStem getCelestialStemYear();

	CelestialStem getCelestialStemMonth();

	CelestialStem getCelestialStemDay();

	TerrestrialBranch getTerrestrialBranchYear();

	TerrestrialBranch getTerrestrialBranchMonth();

	TerrestrialBranch getTerrestrialBranchDay();

	/**
	 * 60갑자
	 * @return year
	 */
	Sexagenary getSexagenaryYear();
	/**
	 * 60갑자
	 * @return month
	 */
	Sexagenary getSexagenaryMonth();
	/**
	 * 60갑자
	 * @return day
	 */
	Sexagenary getSexagenaryDay();
}
