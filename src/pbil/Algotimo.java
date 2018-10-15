package pbil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import models.*;

public class Algotimo {
	static List<CommitteeProbability> pvRootCommittee = new ArrayList<CommitteeProbability>();
	public static void main(String[] args) {
		Random m = new Random();
		List<Committee> pool_1 = new ArrayList<Committee>();
		for (int i = 0; i < 7; i++) {
			CommitteeProbability cp = new CommitteeProbability();
			Parameter p = new Parameter(ConfigType.DOUBLE, "zé");
			Classifier a = new Classifier();
			a.nome = new String();
			a.nome = "Metodo: "+i+"";
			a.parameters = new ArrayList<Parameter>();
			a.parameters.add(p);
			Committee x = new Committee();
			x.nome = "metodo "+i;
			x.classifiers = new ArrayList<Classifier>();
			x.type = CommitteType.EMSEMBLE;
			x.classifiers.add(a);
			cp.Rootmethod=x;
			cp.value=1;
			pvRootCommittee.add(cp);
			pool_1.add(x);
		}
		List<Classifier> pool_2 = new ArrayList<Classifier>();
		for (int i = 0; i < 9; i++) {
			Classifier x = new Classifier();
			x.nome = "Classificador base "+i;
			pool_2.add(x);
		}
		Committee rootMethodValue = new Committee();
		Classifier baseClassifierValue = new Classifier();
		List<ClassifierProbability> pvBaseClassifier = new ArrayList<ClassifierProbability>();
		int numberOfGenerations = 0;
		int numberOfIndividuals = 50;
		int generations = 20;
		int timeLimit = 0;
		int indexRootMethod = 0;
		int indexBaseClassifier = 0;
		int indexRootMethodConfig = 0;
		int indexBaseClassifierConfig = 0;
		int c = 0;
		double learnigRate = 0.5;
		int x = 0;
		Individual bestSolution = new Individual();
		bestSolution.accuracy = 0.0;
		int sum =0;
		List<Individual> population = new ArrayList<Individual>();
		//List<Individual> newPopulation = new ArrayList<Individual>();
		double accuracy = 0;
		while ((timeLimit > 0) || numberOfGenerations <= generations) {
			for (int i = 0; i < numberOfIndividuals; i++) {
				sum = sumProbability();
				rootMethodValue = drawCommittee(pvRootCommittee, sum);
				Individual individual = new Individual();
				individual.rootMethod = rootMethodValue;
				if (rootMethodValue.type.equals(CommitteType.NOEMSEMBLE)) {
					x = m.nextInt(9);
					baseClassifierValue = pool_2.get(x);
					individual.classifiers.add(baseClassifierValue);
				}
				List<Classifier> baseClassifierConfigs = new ArrayList<Classifier>();
				if (rootMethodValue.type.equals(CommitteType.EMSEMBLE)) {
					Committee rootMethodConfigs = new Committee();// =sampleRootConfig
					rootMethodConfigs.classifiers = new ArrayList<Classifier>();
					c = rootMethodConfigs.classifiers.size();
					for (int j = 0; j < c; j++) {
						baseClassifierConfigs.add(rootMethodConfigs.classifiers.get(j));
					}
					individual.classifiers = baseClassifierConfigs;
					accuracy = m.nextDouble();
					individual.accuracy = accuracy;
				}
				individual.name = "ihuuu";
				population.add(individual);
			}
			population = bestIndividuals(population, 25);
			bestSolution = population.get(0);
			numberOfGenerations++;
		}
		System.out.println("Best Solution \n "+bestSolution);
		System.out.println(population.size());
	}
	private static List<Individual> bestIndividuals(List<Individual> individuals, int n) {
		Collections.sort(individuals);
		for (int i =n; i<individuals.size();i++) {
			individuals.remove(i);
		}
		return individuals;
	}
	private static Committee drawCommittee(List<CommitteeProbability> cp, int soma) {
		Random m = new Random();
		int aux =0;
		aux = m.nextInt(soma);
		int i =0;
		Committee drawed = new Committee();
		while (aux>=0) {
			aux -= cp.get(i).value;
			if (aux<=0) {
				drawed =cp.get(i).Rootmethod;
				cp.get(i).value++;
				pvRootCommittee = cp;
				break;
			}
			i++;
		}
		return drawed;
	}
	private static int sumProbability() {
		int sum =0;
		for (CommitteeProbability committeeProbability : pvRootCommittee) {
			sum+=committeeProbability.value;
		}
		return sum;
	}
}
