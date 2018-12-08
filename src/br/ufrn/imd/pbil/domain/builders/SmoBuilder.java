package br.ufrn.imd.pbil.domain.builders;

import java.util.List;
import java.util.Random;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.Smo;
import br.ufrn.imd.pbil.domain.prototypes.SMOPrototype;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class SmoBuilder extends ClassifierBuilder{

	public Classifier classifier;
	
	public Classifier defautBuild() {
		classifier = new Smo();
		Parameter u = new Parameter("U",ParameterType.STRING);
		u.setValue("Defaut");
		List<Parameter> params = classifier.getParameters();
		params.add(u);
		classifier.setParameters(params);
		return classifier;
	}

	public Classifier randomBuild() {
		classifier = new Smo();
		try {
			prototype = new SMOPrototype();
			Parameter u = new Parameter("U",ParameterType.STRING);
			u.setValue(randomValueForParameter(u));
		}
		catch(InvalidParameterTypeException e) {
			e.printStackTrace();
		}
		return classifier;
	}

	public Classifier wheitedDrawBuild() {
		// TODO Auto-generated method stub
		return null;
	}

}
