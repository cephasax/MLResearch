package br.ufrn.imd.pbil.domain.baseclassifiers;

import java.util.List;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class SmoBuilder extends ClassifierBuilder{

	public SmoBuilder(ClassifierPrototype classifierPrototype) {
		super(classifierPrototype);
	}

	public Classifier classifier;
	
	public Classifier defaultBuild() {
		classifier = new Smo();
		classifier.setName("Default SMO");
		Parameter u = new Parameter("U",ParameterType.STRING);
		u.setValue("Defaut");
		List<Parameter> params = classifier.getParameters();
		params.add(u);
		classifier.setParameters(params);
		return classifier;
	}

	public Classifier randomBuild() {
		classifier = new Smo();
		classifier.setName("Random Smo");
		
		Parameter u = new Parameter("U",ParameterType.STRING);
		u.setValue(randomValueForParameter(u));
		classifier.addParameter(u);
		return classifier;
	}

	@Override
	public Classifier weightedDrawBuild() {
		// TODO Auto-generated method stub
		return null;
	}

}
