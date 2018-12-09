package br.ufrn.imd.pbil.domain.baseclassifiers;


import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class BayesNetBuilder extends ClassifierBuilder{

	public BayesNetBuilder(ClassifierPrototype classifierPrototype) {
		super(classifierPrototype);
	}

	@Override
	public Classifier defautBuild() {
		classifier =new BayesNet();
		
		Parameter q = new Parameter("Q",ParameterType.STRING);
		q.setValue("K2");
		classifier.addParameter(q);
		
		Parameter d = new Parameter("D", ParameterType.BOOLEAN);
		d.setValue("false");
		classifier.addParameter(d);
		
		return classifier;
	}

	@Override
	public Classifier randomBuild() {
		classifier = new BayesNet();
		try {
			prototype = new BayesNetPrototype();
			Parameter q = new Parameter("Q", ParameterType.STRING);
			q.setValue(randomValueForParameter(q));
			classifier.addParameter(q);
			
			Parameter d = new Parameter("D", ParameterType.BOOLEAN);
			d.setValue(randomValueForParameter(d));
			classifier.addParameter(d);
			
		} catch (InvalidParameterTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return classifier;
	}

	@Override
	public Classifier weightedDrawBuild() {
		// TODO Auto-generated method stub
		return null;
	}

}
