package br.ufrn.imd.pbil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.domain.Individual;
import br.ufrn.imd.pbil.domain.committees.AdaBoostPrototype;
import br.ufrn.imd.pbil.domain.committees.CommitteeFactory;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;
public class Main {

	public static void main(String[] args) throws InvalidParameterTypeException {
		List<Individual	> population = new ArrayList<Individual>();
		CommitteeFactory factory = new CommitteeFactory();
		
		for(int i =0; i<20;i++) {
			
			Individual individual = new Individual();
			individual.setRootMethod(factory.buildClassifier("AdaBoost"));
			individual.setName(individual.getRootMethod().getName());
			population.add(individual);
			
		}

	}
}
