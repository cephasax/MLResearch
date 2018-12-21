package br.ufrn.imd.pbil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;

import br.ufrn.imd.pbil.domain.Individual;
import br.ufrn.imd.pbil.domain.committees.CommitteeFactory;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class Main {

	public static void main(String[] args) throws InvalidParameterTypeException {
		List<Individual	> population = new ArrayList<Individual>();
		Map<Integer, String> committees = inciateCommittees( new HashMap<Integer, String>());
		Map<Integer, String> classifiers = inciateClassifiers( new HashMap<Integer, String>());
		CommitteeFactory factory = new CommitteeFactory();
		Random random = new Random();
		int aux =0;
		StringBuilder str = new StringBuilder();
		String solucao;
		for(int i =0; i<2; i++) {
			Individual temp = new Individual();
			aux = random.nextInt(7);
			if(aux<6) {
				solucao = committees.get(aux);
				temp.setName(i+"-"+solucao);
				temp.setRootMethod(factory.buildClassifier(solucao));
			} else {
				aux = random.nextInt(9);
				solucao = classifiers.get(aux);
				temp.setName(i+"-"+solucao );
				temp.setRootMethod(factory.buildClassifier(solucao));
			}
			population.add(temp);
			str.append(population.get(i).print()+"\n");
		}
		
		String ze = str.toString();
		File file = new File("//home//douglas//Documentos//solution.json");
		try {
			file.createNewFile();
			FileOutputStream aaaa= new FileOutputStream(file);
			PrintWriter printer = new PrintWriter(aaaa);
			printer.write(ze);
			printer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		classifie(population.get(0).print());
	}

	private static Map<Integer,String> inciateCommittees(Map<Integer,String> committees){
		committees.put(0, "AdaBoost");
		committees.put(1, "RandomCommittee");
		committees.put(2, "Bagging");
		committees.put(3, "RandomForest");
		committees.put(4, "Vote");
		committees.put(5, "Stacking");
		return committees;
	}
	
	private static Map<Integer,String> inciateClassifiers(Map<Integer,String> classifiers){
		classifiers.put(0, "MLP");
		classifiers.put(1, "J48");
		classifiers.put(2, "SMO");
		classifiers.put(3, "Kstar");
		classifiers.put(4, "NaiveBayes");
		classifiers.put(5, "BayesNet");
		classifiers.put(6, "RandomTree");
		classifiers.put(7, "IBK");
		classifiers.put(8, "DecisionTable");
		return classifiers;
	}
	private static void classifie(String a) {
		Gson g =new Gson();
		Individual sol = g.fromJson(a,Individual.class);
		
	}
}
