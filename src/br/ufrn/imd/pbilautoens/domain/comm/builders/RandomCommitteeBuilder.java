package br.ufrn.imd.pbilautoens.domain.comm.builders;

import br.ufrn.imd.pbilautoens.ClassifierPrototype;
import br.ufrn.imd.pbilautoens.Committee;
import br.ufrn.imd.pbilautoens.Parameter;
import br.ufrn.imd.pbilautoens.PbilRandom;
import br.ufrn.imd.pbilautoens.domain.comm.RandomCommittee;
import br.ufrn.imd.pbilautoens.enums.ParameterType;
import br.ufrn.imd.pbilautoens.exception.InvalidParameterTypeException;

public class RandomCommitteeBuilder extends CommitteeBuilder {
	
	public RandomCommitteeBuilder(ClassifierPrototype classifierPrototype, PbilRandom pbilRandom) {
		super(classifierPrototype, pbilRandom);
	}
	
	@Override
	public Committee defaultBuild() {
		committee = new RandomCommittee();
		
		Parameter i = new Parameter("I",ParameterType.INT);
		i.setValue("10");
		committee.addParameter(i);
		
		Parameter s = new Parameter("S",ParameterType.INT);
		s.setValue("9");
		committee.addParameter(s);
		
		try {
			committee.setClassifiers(buildClassifiers(1));
		} catch (InvalidParameterTypeException e) {
			e.printStackTrace();
		}
		
		return committee;
	}
	@Override
	public Committee randomBuild() {
		committee = new RandomCommittee();
		Parameter i = new Parameter("I",ParameterType.INT);
		i.setValue(randomValueForParameter(i));
		committee.addParameter(i);
		
		Parameter s = new Parameter("S",ParameterType.INT);
		s.setValue(randomValueForParameter(s));
		committee.addParameter(s);
		
		try {
			committee.setClassifiers(buildClassifiers(1));
		} catch (InvalidParameterTypeException e) {
			e.printStackTrace();
		}
		
		return committee;
	}
}
