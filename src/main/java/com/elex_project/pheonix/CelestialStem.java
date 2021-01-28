/*
Copyright 2021 (c) Elex. All Rights Reserved.
https://www.elex-project.com/
 */
package com.elex_project.pheonix;

import lombok.extern.slf4j.Slf4j;

/**
 * 10천간
 *
 * @author Elex
 *
 */
@Slf4j
public enum CelestialStem {
	GAP, EUL, BYUNG, JEONG, MU, KI, KYUNG, SIN, IM, GYE;

	@Override
	public String toString(){
		switch (this) {

			case GAP:
				return "갑";
			case EUL:
				return "을";
			case BYUNG:
				return "병";
			case JEONG:
				return "정";
			case MU:
				return "무";
			case KI:
				return "기";
			case KYUNG:
				return "경";
			case SIN:
				return "신";
			case IM:
				return "임";
			case GYE:
				return "계";
			default:
				return "";
		}
	}
}
