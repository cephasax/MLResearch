package br.ufrn.imd.pbil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.domain.baseclassifiers.ClassifierFactory;
import br.ufrn.imd.pbil.domain.committees.CommitteeFactory;
import br.ufrn.imd.pbil.enums.ConfigurationType;
import br.ufrn.imd.pbil.enums.BaseClassifierType;
import br.ufrn.imd.pbil.enums.CommitteeType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class Main {

	public static void main(String[] args) throws InvalidParameterTypeException {
		int generations =0;
		List<Committee> population = new ArrayList<Committee>();
		Random random = new Random();
		while (generations>=0) {
			
			int indexOfClassifier = random.nextInt(6);
			int indexOfConfig = random.nextInt(2);
			
			CommitteeType type = CommitteeType.values()[indexOfClassifier];
			ConfigurationType config  =ConfigurationType.values()[indexOfConfig];
			Committee solucao = CommitteeFactory.builCommittee(type, config);
			population.add(solucao);
			generations--;
		}
		generations=0;
		population.get(generations).print();

	}
	


}
