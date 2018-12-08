package br.ufrn.imd.pbil.domain.builders;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.J48;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.prototypes.J48Prototype;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class J48Builder extends ClassifierBuilder{

	@Override
	public Classifier defautBuild() {
		classifier = new J48();
		Parameter o = new Parameter("O",ParameterType.BOOLEAN);
		o.setValue("false");
		classifier.addParameter(o);
		
		Parameter u = new Parameter("U",ParameterType.BOOLEAN);
		u.setValue("false");
		classifier.addParameter(u);
		
		Parameter a = new Parameter("A",ParameterType.BOOLEAN);
		a.setValue("false");
		classifier.addParameter(a);
		
		Parameter b = new Parameter("B",ParameterType.BOOLEAN);
		b.setValue("false");
		classifier.addParameter(b);
		
		Parameter j = new Parameter("J",ParameterType.BOOLEAN);
		j.setValue("false");
		classifier.addParameter(j);
		
		Parameter s = new Parameter("S",ParameterType.BOOLEAN);
		s.setValue("false");
		classifier.addParameter(s);
		
		Parameter m = new Parameter("M",ParameterType.INT);
		m.setValue("2");
		classifier.addParameter(m);
		
		Parameter c = new Parameter("C",ParameterType.BOOLEAN);
		c.setValue("0.25");
		classifier.addParameter(c);
		return classifier;
	}

	@Override
	public Classifier randomBuild() {
		classifier = new J48();
		try {
			prototype = new J48Prototype();
		} catch (InvalidParameterTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Parameter o = new Parameter("O",ParameterType.BOOLEAN);
		o.setValue(randomValueForParameter(o));
		classifier.addParameter(o);
		
		Parameter u = new Parameter("U",ParameterType.BOOLEAN);
		u.setValue(randomValueForParameter(u));
		classifier.addParameter(u);
		
		Parameter a = new Parameter("A",ParameterType.BOOLEAN);
		a.setValue(randomValueForParameter(a));
		classifier.addParameter(a);
		
		Parameter b = new Parameter("B",ParameterType.BOOLEAN);
		b.setValue(randomValueForParameter(b));
		classifier.addParameter(b);
		
		Parameter j = new Parameter("J",ParameterType.BOOLEAN);
		j.setValue(randomValueForParameter(j));
		classifier.addParameter(j);
		
		Parameter s = new Parameter("S",ParameterType.BOOLEAN);
		s.setValue(randomValueForParameter(s));
		classifier.addParameter(s);
		
		Parameter m = new Parameter("M",ParameterType.INT);
		m.setValue(randomValueForParameter(m));
		classifier.addParameter(m);
		
		Parameter c = new Parameter("C",ParameterType.BOOLEAN);
		c.setValue(randomValueForParameter(c));
		classifier.addParameter(c);
		return classifier;
	}

	@Override
	public Classifier wheitedDrawBuild() {
		// TODO Auto-generated method stub
		return null;
	}

}
