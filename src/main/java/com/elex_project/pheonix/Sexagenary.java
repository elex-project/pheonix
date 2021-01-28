/*
Copyright 2021 (c) Elex. All Rights Reserved.
https://www.elex-project.com/
 */
package com.elex_project.pheonix;

import lombok.extern.slf4j.Slf4j;

/**
 * 60갑자
 *
 * @author Elex
 *
 */
@Slf4j
public class Sexagenary {
	CelestialStem celestialStem;
	TerrestrialBranch terrestrialBranch;

	Sexagenary(CelestialStem celestialStem, TerrestrialBranch terrestrialBranch) {
		this.celestialStem = celestialStem;
		this.terrestrialBranch = terrestrialBranch;
	}

	Sexagenary(int celestialStem, int terrestrialBranch) {
		this.celestialStem = CelestialStem.values()[celestialStem];
		this.terrestrialBranch = TerrestrialBranch.values()[terrestrialBranch];
	}

	public String toString() {
		return celestialStem + "" + terrestrialBranch;
	}

	public void add(int step) {
		step %= 60;
		int c, t, tmp;
		c = this.celestialStem.ordinal();
		t = this.terrestrialBranch.ordinal();
		tmp = step % 10;
		for (int i = 0; i < tmp; i++) {
			c++;
			if (c == 10)
				c = 0;
		}
		tmp = step % 12;
		for (int i = 0; i < tmp; i++) {
			t++;
			if (t == 12)
				t = 0;
		}
		this.celestialStem = CelestialStem.values()[c];
		this.terrestrialBranch = TerrestrialBranch.values()[t];
	}

}
