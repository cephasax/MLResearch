package br.ufrn.imd.pbil.domain.builders;

import java.util.List;
import java.util.Random;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.SMO;
import br.ufrn.imd.pbil.domain.Smo;
import br.ufrn.imd.pbil.domain.prototypes.SMOPrototype;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class SmoBuilder implements ClassifierBuilder{

	public Classifier classifier;
	
	public SmoClassifier defautBuild() {
		classifier = new Smo();
		Parameter u = new Parameter("U",ParameterType.STRING);
		u.setValue("Defaut");
		List<Parameter> params = classifier.getParameters();
		params.add(u);
		classifier.setParameters(params);
		return classifier;
	}

	public Classifier randomBuild() {
		Smo randomSmo = new SMO("SMO");
		Random m = new Random();
		try {
			SMOPrototype possibilities = new SMOPrototype();
			Parameter u = new Parameter("U",ParameterType.STRING);
			String value = (String) possibilities.getParameters().get(0).getPossibilities().get(m.nextInt(3));
			u.setValue(value);
		}
		catch(InvalidParameterTypeException e) {
			e.printStackTrace();
		}
		return randomSMO;
	}

	public Classifier wheitedDrawBuild() {
		// TODO Auto-generated method stub
		return null;
	}

}
