package br.ufrn.imd.pbil.pde;

import br.ufrn.imd.pbil.domain.Factory;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class TesttingProb {

	public static void main(String[] args) throws InvalidParameterTypeException {
		Possibility posBc1 = new Possibility("MLP");
		Possibility posBc2 = new Possibility("SVM");

		Possibility posBc = new Possibility("BC");
		posBc.addPossibility(posBc1);
		posBc.addPossibility(posBc2);
		System.out.println(posBc.possibilityAsString());
		
		Possibility posC1 = new Possibility("RC");
		Possibility posC2 = new Possibility("VOTE");

		Possibility posC = new Possibility("COMMITTEES");
		posBc.addPossibility(posC1);
		posBc.addPossibility(posC2);
		System.out.println(posC.possibilityAsString());
		
		Factory f = new Factory();
		
		System.out.println(f.getBaseClassifierPossibilities().possibilityAsString());

		
	}

}
