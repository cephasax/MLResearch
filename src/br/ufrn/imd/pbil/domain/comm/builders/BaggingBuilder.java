package br.ufrn.imd.pbil.domain.comm.builders;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.comm.Bagging;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class BaggingBuilder extends CommitteeBuilder{

	public BaggingBuilder(ClassifierPrototype committeePrototype) {
		super(committeePrototype);
		// TODO Auto-generated constructor stub
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

	@Override
	public Classifier weightedDrawBuild() {
		// TODO Auto-generated method stub
		return null;
	}


}
