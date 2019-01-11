package br.ufrn.imd.pbilautoens.domain.comm.builders;

import br.ufrn.imd.pbilautoens.ClassifierPrototype;
import br.ufrn.imd.pbilautoens.Committee;
import br.ufrn.imd.pbilautoens.Parameter;
import br.ufrn.imd.pbilautoens.PbilRandom;
import br.ufrn.imd.pbilautoens.domain.comm.Stacking;
import br.ufrn.imd.pbilautoens.enums.ParameterType;
import br.ufrn.imd.pbilautoens.exception.InvalidParameterTypeException;

public class StackingBuilder extends CommitteeBuilder{

	public StackingBuilder(ClassifierPrototype classifierPrototype, PbilRandom pbilRandom) {
		super(classifierPrototype, pbilRandom);
	}
	
	@Override
	public Committee defaultBuild() {
		committee = new Stacking();
		
		Parameter x = new Parameter("X",ParameterType.INT);
		x.setValue("10");
		committee.addParameter(x);
		
		Parameter s = new Parameter("S",ParameterType.INT);
		s.setValue("10");
		committee.addParameter(s);
		
		Parameter b = new Parameter("B",ParameterType.INT);
		b.setValue("10");
		committee.addParameter(b);
		
		Parameter num = new Parameter("num",ParameterType.INT);
		num.setValue("1");
		committee.addParameter(num);
		
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
		committee = new Stacking();
		
		Parameter x = new Parameter("X",ParameterType.INT);
		x.setValue(randomValueForParameter(x));
		committee.addParameter(x);
		
		Parameter s = new Parameter("S",ParameterType.INT);
		s.setValue(randomValueForParameter(s));
		committee.addParameter(s);
		
		Parameter b = new Parameter("B",ParameterType.INT);
		b.setValue(randomValueForParameter(b));
		committee.addParameter(b);
		
		Parameter num = new Parameter("num",ParameterType.INT);
		String value = randomValueForParameter(num);
		num.setValue(value);
		committee.addParameter(num);
		
		try {
			committee.setClassifiers(buildClassifiers(Integer.parseInt(value)));
		} catch (InvalidParameterTypeException e) {
			e.printStackTrace();
		}
		
		return committee;
	}

}
