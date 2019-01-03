package br.ufrn.imd.pbil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Factory;
import br.ufrn.imd.pbil.domain.Individual;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class Main {

	public static void main(String[] args) throws InvalidParameterTypeException, IOException {
		List<Individual> population = new ArrayList<Individual>();
		Factory factory = new Factory();
		Conversor con = null;
		
		try {
			con = new Conversor("/home/douglas/x.arff");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int sh = 0;
		double acc = 0;
		for (int i = 0; i < 5; i++) {
			Individual temp = new Individual();
			Classifier a = factory.buildSolutionFromRandom();
			temp.setName(a.getName());
			temp.setRootMethod(a);
			try {
				acc = con.runSolution(temp);
				temp.setAccuracy(acc);
				population.add(temp);
			} catch (Exception e) {
				e.printStackTrace();
				sh++;
				population.add(temp);
			}
		}
		System.out.println(sh);
		/*while (sh != 0 ) {
			for (int i = 0; i < 2; i++) {
				Individual temp = new Individual();
				Classifier a = factory.buildSolutionFromRandom();
				temp.setName(a.getName());
				temp.setRootMethod(a);
				try {
					acc = con.runSolution(temp);
					temp.setAccuracy(acc);
					population.add(temp);
					sh--;
				} catch (Exception e) {
					sh++;
					population.add(temp);
				}
			}
		}*/
	}
}
