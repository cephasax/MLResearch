package br.ufrn.imd.pbil.domain.comm.wekabuilders;

import br.ufrn.imd.pbil.domain.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.comm.Stacking;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class StackingWekaBuilder extends CommitteeWekaBuilder{

	public StackingWekaBuilder(ClassifierPrototype committeePrototype) {
		super(committeePrototype);
		// TODO Auto-generated constructor stub
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
		committee.setBranchClassifierParameter("M");
		
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
