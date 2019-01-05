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
		
		CommitteeFactory cf = new CommitteeFactory();
		Classifier c = cf.buildClassifierRandomly("Vote");
		
		//BaseClassifierFactory bcf = new BaseClassifierFactory();
		//Classifier c = bcf.buildClassifierRandomly("SMO");
						
		try {
			con = new Conversor("C:\\Users\\JCX\\Documents\\x.arff");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int sh = 0;
		double acc = 0;
		for (int i = 0; i < 200; i++) {
			Classifier c = factory.buildSolutionFromRandom();
			Individual temp = new Individual();

			//factory.buildSolutionFromRandom();
			temp.setName(c.getName());
			temp.setRootMethod(c);
			try {
				acc = con.runSolution(temp);
				temp.setAccuracy(acc);
				population.add(temp);
				System.out.println(temp.getName()+" : "+temp.getAccuracy());
			} catch (Exception e) {
				//e.printStackTrace();
				sh++;
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
