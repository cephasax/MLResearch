package br.ufrn.imd.pbil.domain.baseclassifiers;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class KstarBuilder extends ClassifierBuilder{

	public KstarBuilder(ClassifierPrototype classifierPrototype) {
		super(classifierPrototype);
	}

	@Override
	public Classifier defautBuild() {
		// TODO Auto-generated method stub
		classifier = new Kstar();
		
		Parameter b = new Parameter("B",ParameterType.INT);
		b.setValue("20");
		classifier.addParameter(b);
		
		Parameter e = new Parameter("E", ParameterType.BOOLEAN);
		e.setValue("false");
		classifier.addParameter(e);
		
		Parameter x = new Parameter("X", ParameterType.STRING);
		x.setValue("a");
		classifier.addParameter(x);
		return classifier;
	}

	@Override
	public Classifier randomBuild() {
		classifier = new Kstar();
		try {
			prototype = new KstarPrototype();
			Parameter b = new Parameter("B", ParameterType.INT);
			b.setValue(randomValueForParameter(b));
			classifier.addParameter(b);
			
			Parameter e = new Parameter("E", ParameterType.BOOLEAN);
			e.setValue(randomValueForParameter(e));
			classifier.addParameter(e);
			
			Parameter x = new Parameter("X", ParameterType.BOOLEAN);
			x.setValue(randomValueForParameter(x));
			classifier.addParameter(x);
			
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
