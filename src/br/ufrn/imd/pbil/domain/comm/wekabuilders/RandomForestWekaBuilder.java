package br.ufrn.imd.pbil.domain.comm.wekabuilders;

import br.ufrn.imd.pbil.domain.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.comm.RandomForest;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class RandomForestWekaBuilder extends CommitteeWekaBuilder{

	public RandomForestWekaBuilder(ClassifierPrototype committeePrototype) {
		super(committeePrototype);
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
			committee.setClassifiers(buildClassifiers(0));
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
			committee.setClassifiers(buildClassifiers(0));
		} catch (InvalidParameterTypeException e) {
			e.printStackTrace();
		}
		
		return committee;
	}
}
