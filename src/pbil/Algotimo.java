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
			a.nome = new String("Metodo: "+i+" ");
			a.parameters = new ArrayList<Parameter>();
			a.parameters.add(p);
			Committee x = new Committee();
			x.nome = "metodo "+i;
			x.setClassifiers(new ArrayList<Classifier>());
			x.setType(CommitteType.EMSEMBLE);
			x.getClassifiers().add(a);
			cp.setRootmethod(x);
			cp.setValue(1);
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
		bestSolution.setAccuracy(0.0);
		float sum =0;
		List<Individual> population = new ArrayList<Individual>();
		//List<Individual> newPopulation = new ArrayList<Individual>();
		double accuracy = 0;
		while ((timeLimit > 0) || numberOfGenerations <= generations) {
			for (int i = 0; i < numberOfIndividuals; i++) {
				sum = sumProbability();
				rootMethodValue = drawCommittee(pvRootCommittee, sum);
				Individual individual = new Individual();
				individual.setRootMethod(rootMethodValue);
				List<Classifier> baseClassifierConfigs = new ArrayList<Classifier>();
				if (rootMethodValue.getType().equals(CommitteType.NOEMSEMBLE)) {
					x = m.nextInt(9);
					baseClassifierValue = pool_2.get(x);
					individual.getClassifiers().add(baseClassifierValue);
				}
				else {
					Committee rootMethodConfigs = new Committee();// =sampleRootConfig
					rootMethodConfigs.setClassifiers(new ArrayList<Classifier>());
					c = rootMethodConfigs.getClassifiers().size();
					for (int j = 0; j < c; j++) {
						baseClassifierConfigs.add(rootMethodConfigs.getClassifiers().get(j));
					}
					individual.setClassifiers(baseClassifierConfigs);
					accuracy = m.nextDouble();
					individual.setAccuracy(accuracy);
				}
				individual.setName("ihuuu");
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
		int size = individuals.size();
		for (int i =size-1; i>=n;i--) {
			individuals.remove(i);
		}
		return individuals;
	}
	private static Committee drawCommittee(List<CommitteeProbability> cp, float sum) {
		Random m = new Random();
		float aux =0;
		aux = m.nextInt((int) sum);
		System.out.print(aux);
		int i =0;
		Committee drawed = new Committee();
		while (aux>=0) {
			aux -= cp.get(i).getValue();
			if (aux<=0) {
				drawed =cp.get(i).getRootmethod();
				cp.get(i).setValue(cp.get(i).getValue()+1);
				System.out.println(" "+ cp.get(i));
				pvRootCommittee = cp;
				break;
			}
			i++;
		}
		return drawed;
	}
	private static float sumProbability() {
		float sum =0;
		for (CommitteeProbability committeeProbability : pvRootCommittee) {
			sum+=committeeProbability.getValue();
		}
		return sum;
	}
}