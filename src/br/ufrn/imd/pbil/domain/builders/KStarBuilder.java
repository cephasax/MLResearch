package br.ufrn.imd.pbil.domain.builders;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.KStar;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.prototypes.KStarPrototype;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class KStarBuilder extends ClassifierBuilder{

	@Override
	public Classifier defautBuild() {
		// TODO Auto-generated method stub
		classifier = new KStar();
		
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
		classifier = new KStar();
		try {
			prototype = new KStarPrototype();
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
	public Classifier wheitedDrawBuild() {
		// TODO Auto-generated method stub
		return null;
	}

}
