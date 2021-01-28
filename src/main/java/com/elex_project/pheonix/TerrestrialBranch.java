/*
Copyright 2021 (c) Elex. All Rights Reserved.
https://www.elex-project.com/
 */
package com.elex_project.pheonix;

import lombok.extern.slf4j.Slf4j;

/**
 * 12지지
 *
 * @author Elex
 *
 */
@Slf4j
public enum TerrestrialBranch {
	JA, CHUK, IN, MYO, JIN, SA, O, MI, SIN, YU, SUL, HAE;

	@Override
	public String toString(){
		switch(this){
			case JA:
				return "자";
			case CHUK:
				return "축";
			case IN:
				return "인";
			case MYO:
				return "묘";
			case JIN:
				return "진";
			case SA:
				return "사";
			case O:
				return "오";
			case MI:
				return "미";
			case SIN:
				return "신";
			case YU:
				return "유";
			case SUL:
				return "술";
			case HAE:
				return "해";
			default:
				return "";
		}
	}
}
