package br.ufrn.imd.pbilautoens;

import java.util.Random;

public class PbilRandom {

	private int randomSeed;
	private Random random;

	public PbilRandom(int seed) {
		this.randomSeed = seed;
		this.random = new Random(this.randomSeed);
	}
	
	public int getRandomSeed() {
		return randomSeed;
	}

	public Random getRandom() {
		return random;
	}

	public float nextFloat() {
		return this.random.nextFloat();
	}
	
	public int nextInt() {
		return this.random.nextInt();
	}
	
	public int nextInt(int limit) {
		return random.nextInt(limit);
	}
	
	public double nextDouble() {
		return this.random.nextDouble();
	}
	
}
