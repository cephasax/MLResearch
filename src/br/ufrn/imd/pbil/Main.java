package br.ufrn.imd.pbil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.baseclassifiers.ClassifierFactory;
import br.ufrn.imd.pbil.enums.BaseClassifierConfig;
import br.ufrn.imd.pbil.enums.BaseClassifierType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class Main {

	public static void main(String[] args) throws InvalidParameterTypeException {
		int generations =20;
		List<Classifier> classifiers = new ArrayList<Classifier>();
		Random random = new Random();
		while (generations>=0) {
			
			int indexOfClassifier = random.nextInt(9);
			int indexOfConfig = random.nextInt(2);
			
			BaseClassifierType type = BaseClassifierType.values()[indexOfClassifier];
			BaseClassifierConfig config  =BaseClassifierConfig.values()[indexOfConfig];
			
			Classifier solucao = ClassifierFactory.buildClassifier(type, config);
			classifiers.add(solucao);
			generations--;
		}
		generations=0;
		
		while (generations <=20) {
			classifiers.get(generations).print();
			generations++;
		}
	}
	


}
