package br.ufrn.imd.pbil.pde;

import java.util.ArrayList;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Factory;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class TesttingProb {

	public static void main(String[] args) throws InvalidParameterTypeException {
				
		Factory f = new Factory();
		
		/*System.out.println(f.getBaseClassifierPossibilities().possibilityAsString());
		System.out.println("----------------------------------------");
		System.out.println(f.getCommitteePossibilities().possibilityAsString());*/

		//Classifier c = f.getBaseclassifierFactory().getBuilders().get("MLP").defaultBuild();
		
		System.out.println(f.getFirstLevel().possibilityAsString());
		
		for (int i = 0; i < 10; i++) {
			Classifier c = f.buildSolutionFromRandom();
			System.out.println(c.toString());
		}
		/*ArrayList<String> s = new ArrayList<String>();
		s.add(c.getName());*/
	}

}
