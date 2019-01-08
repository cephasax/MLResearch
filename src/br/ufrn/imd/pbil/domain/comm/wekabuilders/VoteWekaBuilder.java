package br.ufrn.imd.pbil.domain.comm.wekabuilders;

import br.ufrn.imd.pbil.domain.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.comm.Vote;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class VoteWekaBuilder extends CommitteeWekaBuilder{

	public VoteWekaBuilder(ClassifierPrototype committeePrototype) {
		super(committeePrototype);

	}

	@Override
	public Committee defaultBuild() {
		committee = new Vote();
		Parameter s = new Parameter("S", ParameterType.INT);
		s.setValue("10");
		committee.addParameter(s);
		
		Parameter r = new Parameter("R", ParameterType.STRING);
		r.setValue("AVG");
		committee.addParameter(r);
		
		Parameter b = new Parameter("B", ParameterType.INT);
		b.setValue("2");
		committee.addParameter(b);
		
		Parameter num = new Parameter("num",ParameterType.INT);
		String value = "1";
		num.setValue(value);
		committee.addParameter(num);
		
		try {
			committee.setClassifiers(buildClassifiers(Integer.parseInt(value)));
		} catch (InvalidParameterTypeException e) {
			e.printStackTrace();
		}
		return committee;
	}

	@Override
	public Committee randomBuild() {
		committee = new Vote();
		committee.setBranchClassifierParameter("B");
		Parameter s = new Parameter("S", ParameterType.INT);
		s.setValue(randomValueForParameter(s));
		committee.addParameter(s);
		
		Parameter r = new Parameter("R", ParameterType.STRING);
		r.setValue(randomValueForParameter(r));
		committee.addParameter(r);
		
		Parameter b = new Parameter("B", ParameterType.INT);
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
