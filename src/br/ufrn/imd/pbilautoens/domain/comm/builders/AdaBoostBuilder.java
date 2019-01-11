
package br.ufrn.imd.pbilautoens.domain.comm.builders;

import br.ufrn.imd.pbilautoens.ClassifierPrototype;
import br.ufrn.imd.pbilautoens.Committee;
import br.ufrn.imd.pbilautoens.Parameter;
import br.ufrn.imd.pbilautoens.PbilRandom;
import br.ufrn.imd.pbilautoens.domain.comm.AdaBoost;
import br.ufrn.imd.pbilautoens.enums.ParameterType;
import br.ufrn.imd.pbilautoens.exception.InvalidParameterTypeException;

public class AdaBoostBuilder extends CommitteeBuilder {

	public AdaBoostBuilder(ClassifierPrototype classifierPrototype, PbilRandom pbilRandom) {
		super(classifierPrototype, pbilRandom);
	}

	@Override
	public Committee defaultBuild() {
		committee = new AdaBoost();
		Parameter q = new Parameter("Q",ParameterType.BOOLEAN);
		q.setValue("false");
		committee.addParameter(q);
		
		Parameter p = new Parameter("P",ParameterType.INT);
		p.setValue("100");
		committee.addParameter(p);
		
		Parameter i = new Parameter("I",ParameterType.INT);
		i.setValue("10");
		committee.addParameter(i);
		
		Parameter s = new Parameter("S",ParameterType.INT);
		s.setValue("100");
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
		committee = new AdaBoost();
		Parameter q = new Parameter("Q",ParameterType.BOOLEAN);
		q.setValue(randomValueForParameter(q));
		committee.addParameter(q);
		
		Parameter p = new Parameter("P",ParameterType.INT);
		p.setValue(randomValueForParameter(p));
		committee.addParameter(p);
		
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
