package br.ufrn.imd.pbil.domain.committees;

import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class RandomCommitteeBuilder extends CommitteeBuilder {
	
	public RandomCommitteeBuilder(CommitteePrototype committeePrototype) {
		super(committeePrototype);
		// TODO Auto-generated constructor stub
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
	@Override
	public Committee weightedBuild() {
		// TODO Auto-generated method stub
		return null;
	}

}
