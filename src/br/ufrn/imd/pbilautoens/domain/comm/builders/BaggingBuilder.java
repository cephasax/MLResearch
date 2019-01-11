package br.ufrn.imd.pbilautoens.domain.comm.builders;

import br.ufrn.imd.pbilautoens.ClassifierPrototype;
import br.ufrn.imd.pbilautoens.Committee;
import br.ufrn.imd.pbilautoens.Parameter;
import br.ufrn.imd.pbilautoens.PbilRandom;
import br.ufrn.imd.pbilautoens.domain.comm.Bagging;
import br.ufrn.imd.pbilautoens.enums.ParameterType;
import br.ufrn.imd.pbilautoens.exception.InvalidParameterTypeException;

public class BaggingBuilder extends CommitteeBuilder{

	public BaggingBuilder(ClassifierPrototype classifierPrototype, PbilRandom pbilRandom) {
		super(classifierPrototype, pbilRandom);
	}

	@Override
	public Committee defaultBuild() {
		committee = new Bagging();
		
		Parameter p = new Parameter("P", ParameterType.INT);
		p.setValue("100");
		committee.addParameter(p);
		
		Parameter i = new Parameter("I", ParameterType.INT);
		i.setValue("10");
		committee.addParameter(i);
		
		Parameter s = new Parameter("S", ParameterType.INT);
		s.setValue("10");
		committee.addParameter(s);
		
		Parameter o = new Parameter("O", ParameterType.BOOLEAN);
		o.setValue("false");
		committee.addParameter(o);
		try {
			committee.setClassifiers(buildClassifiers(1));
		} catch (InvalidParameterTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return committee;
	}

	@Override
	public Committee randomBuild() {
		committee = new Bagging();
		
		Parameter p = new Parameter("P", ParameterType.INT);
		p.setValue(randomValueForParameter(p));
		committee.addParameter(p);
		
		Parameter i = new Parameter("I", ParameterType.INT);
		i.setValue(randomValueForParameter(i));
		committee.addParameter(i);
		
		Parameter s = new Parameter("S", ParameterType.INT);
		s.setValue(randomValueForParameter(s));
		committee.addParameter(s);
		
		Parameter o = new Parameter("O", ParameterType.BOOLEAN);
		o.setValue(randomValueForParameter(o));
		committee.addParameter(o);
		
		try {
			committee.setClassifiers(buildClassifiers(1));
		} catch (InvalidParameterTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return committee;
	}
}
