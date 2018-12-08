package br.ufrn.imd.pbil.domain.builders;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Ibk;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.prototypes.IbkPrototype;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class IbkBuilder extends ClassifierBuilder{

	@Override
	public Classifier defautBuild() {
		classifier = new Ibk();
		
		Parameter e = new Parameter("E", ParameterType.BOOLEAN);
		e.setValue("false");
		classifier.addParameter(e);
		
		Parameter k = new Parameter("K",ParameterType.INT);
		k.setValue("1");
		classifier.addParameter(k);
		
		Parameter i = new Parameter("I", ParameterType.BOOLEAN);
		i.setValue("false");
		classifier.addParameter(i);
		
		Parameter f = new Parameter("F", ParameterType.BOOLEAN);
		f.setValue("false");
		classifier.addParameter(f);
		
		Parameter x = new Parameter("X", ParameterType.BOOLEAN);
		x.setValue("false");
		classifier.addParameter(x);
		
		return classifier;
	}

	@Override
	public Classifier randomBuild() {
		classifier = new Ibk();
		try {
			prototype = new IbkPrototype();
			Parameter e = new Parameter("E", ParameterType.BOOLEAN);
			e.setValue(randomValueForParameter(e));
			classifier.addParameter(e);
			
			Parameter k = new Parameter("K",ParameterType.INT);
			k.setValue(randomValueForParameter(k));
			classifier.addParameter(k);
			
			Parameter i = new Parameter("I", ParameterType.BOOLEAN);
			i.setValue(randomValueForParameter(i));
			classifier.addParameter(i);
			
			Parameter f = new Parameter("F", ParameterType.BOOLEAN);
			f.setValue(randomValueForParameter(f));
			classifier.addParameter(f);
			
			Parameter x = new Parameter("X", ParameterType.BOOLEAN);
			x.setValue(randomValueForParameter(x));
			classifier.addParameter(x);
		} catch (InvalidParameterTypeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return classifier;
	}

	@Override
	public Classifier wheitedDrawBuild() {
		// TODO Auto-generated method stub
		return null;
	}

}
