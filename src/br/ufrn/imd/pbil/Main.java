package br.ufrn.imd.pbil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Factory;
import br.ufrn.imd.pbil.domain.Individual;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class Main {

	public static void main(String[] args) throws InvalidParameterTypeException, IOException {
		List<Individual	> population = new ArrayList<Individual>();
		Factory factory = new Factory();
		StringBuilder str = new StringBuilder();
		for(int i =0; i<2; i++) {
			Individual temp = new Individual();
			Classifier a = factory.buildSolutionFromRandom();
			temp.setName(a.getName());
			temp.setRootMethod(a);
			population.add(temp);
			str.append(population.get(i).print()+"\n");
		}
		individualToComandLine(population);
	}

	private static void individualToComandLine(List<Individual> population) throws IOException {
		for (Individual a : population) {
			StringBuilder command = new StringBuilder();
			command.append("java -cp weka.jar weka.classifier."+a.getName());
			command.append(" -T /home/douglas/diabets.arff");
			for (Parameter parameter: a.getRootMethod().getParameters()) {
				if(!(parameter.getName().equals("num"))){
				command.append(" -"+parameter.getName()+ " "+parameter.getValue());
				}
			}
			for (Classifier c: a.getRootMethod().getClassifiers()) {
				command.append(" -w weka.classifier."+c.getName());
				for (Parameter parameter: c.getParameters()) {
					command.append(" -"+parameter.getName()+ " "+parameter.getValue());			
				}
			}
			System.out.println(command.toString());
			Runtime.getRuntime().exec(command.toString());
		}
	}
}
