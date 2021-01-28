/*
Copyright 2021 (c) Elex. All Rights Reserved.
https://www.elex-project.com/
 */
package com.elex_project.pheonix;

import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 태음태양력<br>
 * <p>
 * 지원 범위: <br>
 * 양력 1901/2/19 ~ 2057/2/3<br>
 *
 * @author Elex
 * 2014/4/12
 */
@Slf4j
public final class KoreanLunisolarCalendar
		extends GregorianCalendar implements ILunisolarCalendar {
	private static final String TZ = "Asia/Seoul";

	private static final class Core {

		private static final long LIMITS_MIN //(+)1901년 2월 19일
				= calcLimitDateTime(1901, 1, 19);
		private static final long LIMITS_MAX
				= calcLimitDateTime(2057, 1, 3);

		private static final int LIMITS_LUNAR_MIN = 1901;
		private static final int LIMITS_LUNAR_MAX = 2056;

		// (-)1901년1월1일은 (+)1901년 2월 19일
		// (-)2000년1월1일은 (+)2000년 2월 5일
		private static final Criteria[] CRITERIA = new Criteria[]{
				// (-)2018년1월1일은 (+)2018-2-16 무술-갑인-기묘
				new Criteria(2018, 1, 16, 117,
						new Sexagenary(CelestialStem.MU, TerrestrialBranch.SUL),
						new Sexagenary(CelestialStem.GAP, TerrestrialBranch.IN),
						new Sexagenary(CelestialStem.KI, TerrestrialBranch.MYO)),
				// (-)2014년1월1일은 (+)2014-1-31 갑오-병인-임인
				new Criteria(2014, 0, 31, 113,
						new Sexagenary(CelestialStem.GAP, TerrestrialBranch.O),
						new Sexagenary(CelestialStem.BYUNG, TerrestrialBranch.IN),
						new Sexagenary(CelestialStem.IM, TerrestrialBranch.IN)),
				// (-)2000년1월1일은 (+)2000년 2월 5일 // 경진-무인-계사
				new Criteria(2000, 1, 5, 99,
						new Sexagenary(CelestialStem.KYUNG, TerrestrialBranch.JIN),
						new Sexagenary(CelestialStem.MU, TerrestrialBranch.IN),
						new Sexagenary(CelestialStem.GYE, TerrestrialBranch.SA)),
				// (-)1901년1월1일은 (+)1901년 2월 19일 	// 신축-경인-무진
				new Criteria(1901, 1, 19, 0,
						new Sexagenary(CelestialStem.SIN, TerrestrialBranch.CHUK),
						new Sexagenary(CelestialStem.KYUNG, TerrestrialBranch.IN),
						new Sexagenary(CelestialStem.MU, TerrestrialBranch.JIN))
		};

		private static class Criteria {
			private int solYear, solMonth, solDate;
			private int index;
			private Sexagenary sexYear, sexMonth, sexDate;

			private Criteria(int solYear, int solMonth, int solDate, int index,
			                 Sexagenary sexYear, Sexagenary sexMonth, Sexagenary sexDate) {
				this.solYear = solYear;
				this.solMonth = solMonth;
				this.solDate = solDate;
				this.index = index;
				this.sexYear = sexYear;
				this.sexMonth = sexMonth;
				this.sexDate = sexDate;
			}

			private long getDatetime() {
				//byte b = 0x11;
				GregorianCalendar cal = new GregorianCalendar(solYear, solMonth, solDate);
				return cal.getTimeInMillis();
			}
		}


		/**
		 * 음력 월별 날짜 길이 데이터
		 */
		private static final byte[][] CONVERSION_TABLE = {
				{0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0}, // (+)1901-2-19 신축-경인-무진
				{1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1}, // 1902
				{0, 1, 0, 1, 2, 1, 0, 0, 1, 1, 0, 1}, // 1903
				{1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0}, // 1904
				{1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1}, // 1905
				{0, 1, 1, 3, 0, 1, 0, 1, 0, 1, 0, 1}, // 1906
				{0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0}, // 1907
				{1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1}, // 1908
				{0, 4, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1}, // 1909
				{0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0}, // 1910
				{1, 0, 1, 0, 0, 4, 0, 1, 1, 0, 1, 1}, // 1911
				{1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1}, // 1912
				{1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1}, // 1913
				{1, 1, 0, 1, 4, 0, 1, 0, 1, 0, 0, 1}, // 1914
				{1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1}, // 1915
				{0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0}, // 1916
				{1, 2, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0}, // 1917
				{1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1}, // 1918
				{0, 1, 0, 0, 1, 0, 4, 1, 1, 0, 1, 1}, // 1919
				{0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1}, // 1920
				{1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1}, // 1921
				{1, 0, 1, 1, 2, 1, 0, 0, 1, 0, 1, 1}, // 1922
				{0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1}, // 1923
				{1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0}, // 1924
				{1, 0, 1, 4, 1, 0, 1, 1, 0, 1, 0, 1}, // 1925
				{0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0}, // 1926
				{1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1}, // 1927
				{0, 4, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1}, // 1928
				{0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1}, // 1929
				{0, 1, 1, 0, 0, 4, 0, 1, 0, 1, 1, 0}, // 1930
				{1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0}, // 1931
				{1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1}, // 1932
				{0, 1, 1, 0, 5, 0, 1, 0, 1, 0, 0, 1}, // 1933
				{0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1}, // 1934
				{0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0}, // 1935
				{1, 0, 3, 0, 1, 0, 1, 0, 1, 1, 1, 0}, // 1936
				{1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0}, // 1937
				{1, 1, 0, 0, 1, 0, 3, 0, 1, 1, 0, 1}, // 1938
				{1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1}, // 1939
				{1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0}, // 1940
				{1, 1, 0, 1, 1, 3, 0, 0, 1, 0, 1, 0}, // 1941
				{1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1}, // 1942
				{0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1}, // 1943
				{0, 0, 1, 3, 0, 1, 0, 1, 1, 0, 1, 1}, // 1944
				{0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1}, // 1945
				{1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1}, // 1946
				{1, 4, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1}, // 1947
				{1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1}, // 1948
				{1, 1, 0, 1, 0, 1, 2, 1, 0, 1, 0, 1}, // 1949
				{1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0}, // 1950
				{1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1}, // 1951
				{0, 1, 0, 1, 3, 1, 0, 1, 0, 1, 0, 1}, // 1952
				{0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1}, // 1953
				{0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1}, // 1954
				{1, 0, 3, 0, 0, 1, 0, 1, 0, 1, 1, 1}, // 1955
				{0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1}, // 1956
				{1, 0, 1, 0, 1, 0, 0, 4, 1, 0, 1, 1}, // 1957
				{0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1}, // 1958
				{0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0}, // 1959
				{1, 0, 1, 0, 1, 4, 1, 0, 1, 0, 1, 0}, // 1960
				{1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1}, // 1961
				{0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0}, // 1962
				{1, 0, 1, 2, 1, 0, 1, 0, 1, 1, 1, 0}, // 1963
				{1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1}, // 1964
				{0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1}, // 1965
				{0, 1, 4, 1, 0, 0, 1, 0, 0, 1, 1, 0}, // 1966
				{1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1}, // 1967
				{0, 1, 1, 0, 1, 0, 4, 1, 0, 1, 0, 1}, // 1968
				{0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0}, // 1969
				{1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1}, // 1970
				{0, 1, 0, 0, 4, 1, 0, 1, 1, 1, 0, 1}, // 1971
				{0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0}, // 1972
				{1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0}, // 1973
				{1, 1, 0, 4, 0, 1, 0, 0, 1, 1, 0, 1}, // 1974
				{1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1}, // 1975
				{1, 1, 0, 1, 0, 1, 0, 4, 1, 0, 0, 1}, // 1976
				{1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0}, // 1977
				{1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0}, // 1978
				{1, 0, 0, 1, 0, 5, 0, 1, 1, 0, 1, 0}, // 1979
				{1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1}, // 1980
				{0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1}, // 1981
				{1, 0, 1, 2, 1, 0, 0, 1, 1, 0, 1, 1}, // 1982
				{1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1}, // 1983
				{1, 0, 1, 1, 0, 0, 1, 0, 0, 4, 1, 1}, // 1984
				{0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1}, // 1985
				{0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0}, // 1986
				{1, 0, 1, 1, 0, 4, 1, 1, 0, 1, 0, 1}, // 1987
				{0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0}, // 1988
				{1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1}, // 1989
				{0, 1, 0, 0, 4, 0, 1, 1, 0, 1, 1, 1}, // 1990
				{0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1}, // 1991
				{0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1}, // 1992
				{0, 1, 4, 1, 0, 1, 0, 0, 1, 0, 1, 0}, // 1993
				{1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1}, // 1994
				{0, 1, 1, 0, 1, 1, 0, 4, 1, 0, 0, 1}, // 1995
				{0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1}, // 1996
				{0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0}, // 1997
				{1, 0, 0, 1, 2, 1, 1, 0, 1, 1, 1, 0}, // 1998
				{1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0}, // 1999
				{1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0}, // 2000-2-5 경진-무인-계사
				{1, 1, 1, 2, 1, 0, 0, 1, 0, 1, 0, 1}, // 2001
				{1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0}, // 2002
				{1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1}, // 2003
				{0, 4, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1}, // 2004
				{0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0}, // 2005
				{1, 0, 1, 0, 1, 0, 4, 1, 1, 0, 1, 1}, // 2006
				{0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1}, // 2007
				{1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1}, // 2008
				{1, 1, 0, 0, 4, 0, 1, 0, 1, 0, 1, 1}, // 2009
				{1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1}, // 2010
				{1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0}, // 2011
				{1, 0, 5, 1, 0, 1, 0, 0, 1, 0, 1, 0}, // 2012
				{1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1}, // 2013
				{0, 1, 0, 1, 0, 1, 0, 1, 4, 1, 0, 1}, // 2014-1-31 갑오-병인-임인
				{0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0}, // 2015
				{1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1}, // 2016
				{0, 1, 0, 1, 2, 1, 0, 1, 0, 1, 1, 1}, // 2017
				{0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1}, // 2018
				{1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1}, // 2019
				{1, 0, 1, 4, 1, 0, 0, 1, 0, 1, 0, 1}, // 2020
				{0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0}, // 2021
				{1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1}, // 2022
				{0, 4, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1}, // 2023
				{0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0}, // 2024
				{1, 0, 1, 0, 0, 4, 1, 0, 1, 1, 1, 0}, // 2025
				{1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1}, // 2026
				{0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1}, // 2027
				{0, 1, 1, 0, 4, 0, 1, 0, 0, 1, 1, 0}, // 2028
				{1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1}, // 2029
				{0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0}, // 2030
				{1, 0, 4, 1, 0, 1, 1, 0, 1, 0, 1, 0}, // 2031
				{1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1}, // 2032
				{0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 4, 1}, // 2033
				{0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0}, // 2034
				{1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1}, // 2035
				{1, 1, 0, 1, 0, 3, 0, 0, 1, 1, 0, 1}, // 2036
				{1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1}, // 2037
				{1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0}, // 2038
				{1, 1, 0, 1, 4, 1, 0, 1, 0, 1, 0, 0}, // 2039
				{1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0}, // 2040
				{1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1}, // 2041
				{0, 4, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1}, // 2042
				{0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1}, // 2043
				{1, 0, 1, 0, 0, 1, 2, 1, 0, 1, 1, 1}, // 2044
				{1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1}, // 2045
				{1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1}, // 2046
				{1, 0, 1, 1, 3, 0, 1, 0, 0, 1, 0, 1}, // 2047
				{0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0}, // 2048
				{1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0}, // 2049
				{1, 0, 3, 0, 1, 0, 1, 1, 0, 1, 1, 0}, // 2050
				{1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1}, //2051-2-11 (315p) 신미-경인-정묘
				{0, 1, 0, 0, 1, 0, 0, 5, 0, 1, 1, 1}, //2052
				{0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1}, //2053
				{0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1}, //2054
				{0, 1, 1, 0, 1, 3, 0, 0, 1, 0, 1, 0}, //2055
				{1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1} //2056
		};

		/**
		 * 음력 평달에는 큰 달과 작은 달이 있고,
		 * 윤달이 있는 때에는 4가지의 조합이 있게 된다.
		 * 뒷쪽 달이 윤달이다.
		 *
		 * @author Elex
		 */
		enum M {
			// 작은 달은 29일, 큰달은 30일
			소(29, 0), 대(30, 0), 소소(29, 29), 소대(29, 30), 대소(30, 29), 대대(30, 30);

			private final int len1, len2;

			M(int len1, int len2) {
				this.len1 = len1;
				this.len2 = len2;
			}

			private int getLengthOfMonth() {
				return len1 + len2;
			}

			private int getLengthOfFirstMonth() {
				return len1;
			}

			private int getLengthOfLastMonth() {
				return len2;
			}

			private static M get(byte val) throws OutOfCalendarDataException {
				try {
					return M.values()[val];
				} catch (IndexOutOfBoundsException e) {
					throw new OutOfCalendarDataException((byte) 0);
				}
			}
		}

		private static long calcLimitDateTime(int y, int m, int d) {
			Calendar c = new GregorianCalendar(TimeZone.getTimeZone(TZ));
			c.set(y, m, d);
			return c.getTimeInMillis();
		}
	}

	private int lunarYear, lunarMonth, lunarDate;
	private boolean lunarLeapMonth;
	private Sexagenary sexagenaryYear, sexagenaryMonth, sexagenaryDate;
	private boolean hasLunarDate;

	/**
	 * 오늘
	 *
	 * @throws OutOfCalendarDataException 계산 가능 범위 제한 초과
	 */
	public KoreanLunisolarCalendar() throws OutOfCalendarDataException {
		super(TimeZone.getTimeZone(TZ));

		calcSolarToLunar();
	}

	/**
	 * 음력 날짜를 입력 받는다.
	 *
	 * @param year      년
	 * @param month     월 (0~11)
	 * @param date      일
	 * @param leapMonth 윤달
	 * @throws OutOfCalendarDataException 계산 가능 범위 제한 초과
	 */
	public KoreanLunisolarCalendar(int year, int month, int date, boolean leapMonth)
			throws OutOfCalendarDataException {
		super(TimeZone.getTimeZone(TZ));

		this.lunarYear = year;
		this.lunarMonth = month;
		this.lunarDate = date;
		this.lunarLeapMonth = leapMonth;

		calcLunarToSolar();
	}

	/**
	 * 음력 날짜를 입력 받는다.
	 *
	 * @param year
	 * @param month
	 * @param date
	 * @param leapMonth
	 * @param hourOfDay
	 * @param minute
	 * @throws OutOfCalendarDataException
	 */
	public KoreanLunisolarCalendar(int year, int month, int date, boolean leapMonth,
	                               int hourOfDay, int minute) throws OutOfCalendarDataException {
		super(TimeZone.getTimeZone(TZ));

		this.lunarYear = year;
		this.lunarMonth = month;
		this.lunarDate = date;
		this.lunarLeapMonth = leapMonth;

		calcLunarToSolar();

		set(Calendar.HOUR, hourOfDay);
		set(Calendar.MINUTE, minute);
	}

	/**
	 * 음력 날짜를 입력 받는다.
	 *
	 * @param year
	 * @param month
	 * @param date
	 * @param leapMonth
	 * @param hourOfDay
	 * @param minute
	 * @param second
	 * @throws OutOfCalendarDataException
	 */
	public KoreanLunisolarCalendar(int year, int month, int date, boolean leapMonth,
	                               int hourOfDay, int minute, int second) throws OutOfCalendarDataException {
		super(TimeZone.getTimeZone(TZ));

		this.lunarYear = year;
		this.lunarMonth = month;
		this.lunarDate = date;
		this.lunarLeapMonth = leapMonth;

		calcLunarToSolar();

		set(Calendar.HOUR, hourOfDay);
		set(Calendar.MINUTE, minute);
		set(Calendar.SECOND, second);
	}

	/**
	 * 양력 날짜를 입력 받는다.
	 *
	 * @param year  년
	 * @param month 월 0~11
	 * @param date  일
	 * @throws OutOfCalendarDataException 계산 가능 범위 제한 초과
	 */
	public KoreanLunisolarCalendar(int year, int month, int date) throws OutOfCalendarDataException {
		super(TimeZone.getTimeZone(TZ));

		this.set(year, month, date);

		calcSolarToLunar();
	}

	/**
	 * 양력 날짜를 입력 받는다.
	 *
	 * @param year
	 * @param month
	 * @param dayOfMonth
	 * @param hourOfDay
	 * @param minute
	 * @throws OutOfCalendarDataException
	 */
	public KoreanLunisolarCalendar(int year, int month, int dayOfMonth,
	                               int hourOfDay, int minute) throws OutOfCalendarDataException {
		super(TimeZone.getTimeZone(TZ));

		this.set(year, month, dayOfMonth, hourOfDay, minute);

		calcSolarToLunar();
	}

	/**
	 * 양력 날짜를 입력 받는다.
	 *
	 * @param year
	 * @param month
	 * @param dayOfMonth
	 * @param hourOfDay
	 * @param minute
	 * @param second
	 * @throws OutOfCalendarDataException
	 */
	public KoreanLunisolarCalendar(int year, int month, int dayOfMonth,
	                               int hourOfDay, int minute, int second) throws OutOfCalendarDataException {
		super(TimeZone.getTimeZone(TZ));

		this.set(year, month, dayOfMonth, hourOfDay, minute, second);

		calcSolarToLunar();
	}

	/**
	 * 음력일이 계산되었으면 true, 아니면 false
	 *
	 * @return true / false
	 */
	public boolean hasLunarDate() {
		return hasLunarDate;
	}

	/**
	 * 윤달 여부
	 *
	 * @return true / false
	 */
	@Override
	public boolean isLunarLeapMonth() {
		return this.lunarLeapMonth;
	}

	@Override
	public int getLunarYear() {
		return this.lunarYear;
	}

	@Override
	public int getLunarMonth() {
		return this.lunarMonth;
	}

	@Override
	public int getLunarDate() {
		return this.lunarDate;
	}

	/**
	 * 양력 년
	 *
	 * @return year
	 */
	public int getYear() {
		return this.get(Calendar.YEAR);
	}

	/**
	 * 양력 월
	 *
	 * @return month, 0~11
	 */
	public int getMonth() {
		return this.get(Calendar.MONTH);
	}

	/**
	 * 양력 일
	 *
	 * @return day
	 */
	public int getDate() {
		return this.get(Calendar.DATE);
	}

	private void calcSolarToLunar() throws OutOfCalendarDataException {
		// 1. 입력된 양력 날짜와 기준일 사이의 날짜수를 구한다.
		// 2. 기준일의 음력날짜로부터 날짜수를 더한다.

		long datetime = this.getTimeInMillis();

		// 계산 가능 범위 확인
		if (datetime < Core.LIMITS_MIN) {
			this.hasLunarDate = false;
			throw new OutOfCalendarDataException(OutOfCalendarDataException.UNDER_MINIMUM);
		} else if (datetime > Core.LIMITS_MAX) {
			this.hasLunarDate = false;
			throw new OutOfCalendarDataException(OutOfCalendarDataException.OVER_MAXIMUM);
		}

		// 적합한 기지값 찾기
		Core.Criteria criterium = Core.CRITERIA[0];
		for (Core.Criteria c : Core.CRITERIA) {
			if (c.getDatetime() <= datetime) {
				criterium = c;
				break;
			}
		}

		// 기지값과의 차이를 일수로 계산
		long diff = datetime - criterium.getDatetime();
		int diffDates = (int) (Math.floor(diff / 1000 / 60 / 60 / 24));

		this.lunarYear = criterium.solYear;
		this.lunarMonth = 0;//1월
		this.lunarDate = 1;//1일
		int sumDates = 0;
		int sexY = 0, sexM = 0, sexD = diffDates;
		int indexY = criterium.index;
		while (sumDates < diffDates) {

			for (this.lunarMonth = 0; this.lunarMonth < 12; this.lunarMonth++) {
				sumDates += Core.M.get(Core.CONVERSION_TABLE[indexY][this.lunarMonth]).getLengthOfMonth();
				sexM++;
				if (sumDates >= diffDates) {
					//월 단위로 더하다가 어느 순간 넘어서게 된다.
					break;
				}
			}

			if (sumDates > diffDates) {
				//날짜 수가 넘어선 때에는 마지막으로 더한 날짜 수를 뺀다.
				sumDates -= Core.M.get(Core.CONVERSION_TABLE[indexY][this.lunarMonth]).getLengthOfMonth();
				this.lunarDate = diffDates - sumDates + 1;
				sexM -= 1;
				break;
			} else if (sumDates == diffDates) {
				this.lunarDate = 1;
				this.lunarMonth += 1;
				//sexM-=1;
				if (this.lunarMonth > 11) {//overflow
					this.lunarMonth = 0;
					this.lunarYear++;
					sexY++;
				}
				break;
			}
			sexY++;
			indexY++;
			this.lunarYear++;
		}
		//System.out.println(this.lunarMonth+" " + this.lunarDate);
		//윤달 처리
		Core.M monthType = Core.M.get(Core.CONVERSION_TABLE[indexY][this.lunarMonth]);
		if (Core.CONVERSION_TABLE[indexY][this.lunarMonth % 12] > 1 &&
				this.lunarDate > monthType.getLengthOfFirstMonth()) {
			this.lunarLeapMonth = true;
			this.lunarDate -= monthType.getLengthOfFirstMonth();

			if (this.lunarDate > monthType.getLengthOfLastMonth()) {
				this.lunarMonth++;
				this.lunarDate -= monthType.getLengthOfMonth();
			}
			if (this.lunarMonth > 11) {
				this.lunarYear += 1;
				this.lunarMonth = 0;
			}
		} else {
			this.lunarLeapMonth = false;
		}

		//
		sexagenaryYear = new Sexagenary(criterium.sexYear.celestialStem, criterium.sexYear.terrestrialBranch);
		sexagenaryYear.add(sexY);
		sexagenaryMonth = new Sexagenary(criterium.sexMonth.celestialStem, criterium.sexMonth.terrestrialBranch);
		sexagenaryMonth.add(sexM);
		sexagenaryDate = new Sexagenary(criterium.sexDate.celestialStem, criterium.sexDate.terrestrialBranch);
		sexagenaryDate.add(sexD);

		this.hasLunarDate = true;
	}

	private void calcLunarToSolar() throws OutOfCalendarDataException {
		// 1. 입력된 음력 날짜와 기준일 사이의 날짜수를 구한다.
		// 기준일 : 1901년1월1일 또는 2000년1월1일 => 1901년2월19일 또는 2000년2월5일
		// 2. 기준일의 양력날짜로부터 날짜수를 더한다.

		// 계산 가능 범위 확인
		if (this.lunarYear < Core.LIMITS_LUNAR_MIN) {
			this.hasLunarDate = false;
			throw new OutOfCalendarDataException(OutOfCalendarDataException.UNDER_MINIMUM);
		} else if (this.lunarYear > Core.LIMITS_LUNAR_MAX) {
			this.hasLunarDate = false;
			throw new OutOfCalendarDataException(OutOfCalendarDataException.OVER_MAXIMUM);
		}
		// 적합한 기지값 찾기
		Core.Criteria criterium = Core.CRITERIA[0];
		for (Core.Criteria c : Core.CRITERIA) {
			if (c.solYear <= this.lunarYear) {
				criterium = c;
				break;
			}
		}

		//
		int sexY = 0, sexM = 0, sexD = 0;
		int sumDate = 0;
		int diffYear = this.lunarYear - criterium.solYear;
		for (int i = 0; i < diffYear; i++) {
			for (int j = 0; j < 12; j++) {
				sumDate += Core.M.get(Core.CONVERSION_TABLE[criterium.index + i][j]).getLengthOfMonth();
				sexM++;
			}
			sexY++;
		}
		for (int i = 0; i < this.lunarMonth; i++) {
			sumDate += Core.M.get(Core.CONVERSION_TABLE[criterium.index + diffYear][i]).getLengthOfMonth();
			sexM++;
		}
		Core.M monthType = Core.M.get(Core.CONVERSION_TABLE[criterium.index + diffYear][this.lunarMonth]);
		if (this.lunarLeapMonth) { // 윤월
			sumDate += monthType.getLengthOfFirstMonth();
		}
		sumDate += this.lunarDate - 1;
		sexD = sumDate;
		GregorianCalendar criteriaCal = new GregorianCalendar(criterium.solYear, criterium.solMonth, criterium.solDate);
		criteriaCal.add(Calendar.DATE, sumDate);
		//noinspection ResourceType
		this.set(criteriaCal.get(Calendar.YEAR), criteriaCal.get(Calendar.MONTH), criteriaCal.get(Calendar.DATE));

		sexagenaryYear = new Sexagenary(criterium.sexYear.celestialStem, criterium.sexYear.terrestrialBranch);
		sexagenaryYear.add(sexY);
		sexagenaryMonth = new Sexagenary(criterium.sexMonth.celestialStem, criterium.sexMonth.terrestrialBranch);
		sexagenaryMonth.add(sexM);
		sexagenaryDate = new Sexagenary(criterium.sexDate.celestialStem, criterium.sexDate.terrestrialBranch);
		sexagenaryDate.add(sexD);

		this.hasLunarDate = true;

	}

	public String toString() {
		return "양력:" + this.get(Calendar.YEAR) + "/" + (this.get(Calendar.MONTH) + 1) + "/" + this.get(Calendar.DATE) +
				"\n음력: " + this.lunarYear + "/" + ((this.lunarLeapMonth) ? "윤" : "") + (this.lunarMonth + 1) + "/" + this.lunarDate + "\n" +
				"간지: " + this.sexagenaryYear.toString() + " " + this.sexagenaryMonth + " " + this.sexagenaryDate + "\n";
	}

	@Override
	public long getMaxSupportedDateTime() {
		return Core.LIMITS_MAX;
	}

	@Override
	public long getMinSupportedDateTime() {
		return Core.LIMITS_MIN;
	}

	@Override
	public CelestialStem getCelestialStemYear() {
		return this.sexagenaryYear.celestialStem;
	}

	@Override
	public CelestialStem getCelestialStemMonth() {
		return this.sexagenaryMonth.celestialStem;
	}

	@Override
	public CelestialStem getCelestialStemDay() {
		return this.sexagenaryDate.celestialStem;
	}

	@Override
	public TerrestrialBranch getTerrestrialBranchYear() {
		return this.sexagenaryYear.terrestrialBranch;
	}

	@Override
	public TerrestrialBranch getTerrestrialBranchMonth() {
		return this.sexagenaryMonth.terrestrialBranch;
	}

	@Override
	public TerrestrialBranch getTerrestrialBranchDay() {
		return this.sexagenaryDate.terrestrialBranch;
	}

	@Override
	public Sexagenary getSexagenaryYear() {
		return this.sexagenaryYear;
	}

	@Override
	public Sexagenary getSexagenaryMonth() {
		return this.sexagenaryMonth;
	}

	@Override
	public Sexagenary getSexagenaryDay() {
		return this.sexagenaryDate;
	}


	@Override
	public void add(int field, int amount) {
		super.add(field, amount);
		try {
			calcSolarToLunar();
		} catch (OutOfCalendarDataException e) {
			log.error("Out of calendar data.", e);
		}
	}

	@Override
	public void roll(int field, boolean up) {
		super.roll(field, up);
		try {
			calcSolarToLunar();
		} catch (OutOfCalendarDataException e) {
			log.error("Out of calendar data.", e);
		}
	}

	@Override
	public void roll(int field, int amount) {
		super.roll(field, amount);
		try {
			calcSolarToLunar();
		} catch (OutOfCalendarDataException e) {
			log.error("Out of calendar data.", e);
		}
	}

	@Override
	public void set(int field, int value) {
		super.set(field, value);
		try {
			calcSolarToLunar();
		} catch (OutOfCalendarDataException e) {
			log.error("Out of calendar data.", e);
		}
	}

	/**
	 * 음력 특정일을 지정
	 *
	 * @param year      년
	 * @param month     월 0~11
	 * @param date      일
	 * @param leapMonth 윤달
	 * @throws OutOfCalendarDataException 계산 가능 범위 제한 초과
	 */
	public void set(int year, int month, int date, boolean leapMonth)
			throws OutOfCalendarDataException {
		this.lunarYear = year;
		this.lunarMonth = month;
		this.lunarDate = date;
		this.lunarLeapMonth = leapMonth;

		calcLunarToSolar();
	}
}
