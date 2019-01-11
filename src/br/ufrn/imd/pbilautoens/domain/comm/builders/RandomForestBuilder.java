package br.ufrn.imd.pbilautoens.domain.comm.builders;

import br.ufrn.imd.pbilautoens.ClassifierPrototype;
import br.ufrn.imd.pbilautoens.Committee;
import br.ufrn.imd.pbilautoens.Parameter;
import br.ufrn.imd.pbilautoens.PbilRandom;
import br.ufrn.imd.pbilautoens.domain.comm.RandomForest;
import br.ufrn.imd.pbilautoens.enums.ParameterType;
import br.ufrn.imd.pbilautoens.exception.InvalidParameterTypeException;

public class RandomForestBuilder extends CommitteeBuilder{

	public RandomForestBuilder(ClassifierPrototype classifierPrototype, PbilRandom pbilRandom) {
		super(classifierPrototype, pbilRandom);
	}
	
	@Override
	public Committee defaultBuild() {
		committee = new RandomForest();
		
		Parameter i = new Parameter("I", ParameterType.INT);
		i.setValue("10");
		committee.addParameter(i);
		
		Parameter k = new Parameter("K", ParameterType.INT);
		k.setValue("2");
		committee.addParameter(k);
		
		Parameter w = new Parameter("W", ParameterType.INT);
		w.setValue("2");
		committee.addParameter(w);
		try {
			committee.setClassifiers(buildClassifiers(1));
		} catch (InvalidParameterTypeException e) {
			e.printStackTrace();
		}
		
		
		return committee;
	}

	@Override
	public Committee randomBuild() {
		committee = new RandomForest();
		
		Parameter i = new Parameter("I", ParameterType.INT);
		i.setValue(randomValueForParameter(i));
		committee.addParameter(i);
		
		Parameter k = new Parameter("K", ParameterType.INT);
		k.setValue(randomValueForParameter(k));
		committee.addParameter(k);
		
		Parameter w = new Parameter("W", ParameterType.INT);
		w.setValue(randomValueForParameter(w));
		committee.addParameter(w);
		
		try {
			committee.setClassifiers(buildClassifiers(1));
		} catch (InvalidParameterTypeException e) {
			e.printStackTrace();
		}
		
		return committee;
	}
}
