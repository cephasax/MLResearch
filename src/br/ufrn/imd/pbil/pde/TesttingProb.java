package br.ufrn.imd.pbil.pde;

import br.ufrn.imd.pbil.domain.Factory;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class TesttingProb {

	public static float rate = 1;

	public static void main(String[] args) throws InvalidParameterTypeException {

		Factory f = new Factory();

	 	for(int i = 0; i < 100; i++) {
			PossibilityKeySet s = new PossibilityKeySet(f.buildSolutionFromRandom());
			System.out.println(s.toString());
		}

	}

}
