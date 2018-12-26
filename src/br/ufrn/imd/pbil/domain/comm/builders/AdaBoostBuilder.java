
package br.ufrn.imd.pbil.domain.comm.builders;

import br.ufrn.imd.pbil.domain.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.comm.AdaBoost;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class AdaBoostBuilder extends CommitteeBuilder {

	public AdaBoostBuilder(ClassifierPrototype committeePrototype) {
		super(committeePrototype);
		// TODO Auto-generated constructor stub
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
