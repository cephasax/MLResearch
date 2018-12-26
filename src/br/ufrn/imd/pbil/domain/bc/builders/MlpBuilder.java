package br.ufrn.imd.pbil.domain.bc.builders;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.ClassifierBuilder;
import br.ufrn.imd.pbil.domain.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.bc.Mlp;
import br.ufrn.imd.pbil.enums.ParameterType;

public class MlpBuilder extends ClassifierBuilder{

	public MlpBuilder(ClassifierPrototype classifierPrototype) {
		super(classifierPrototype);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Classifier defaultBuild() {
		classifier = new Mlp();
		
		Parameter l =new Parameter("L",ParameterType.DOUBLE);
		l.setValue("0.3");
		classifier.addParameter(l);
		
		Parameter m =new Parameter("M",ParameterType.DOUBLE);
		m.setValue("0.2");
		classifier.addParameter(m);
		
		Parameter b =new Parameter("B",ParameterType.BOOLEAN);
		b.setValue("false");
		classifier.addParameter(b);
		
		Parameter h =new Parameter("H",ParameterType.STRING);
		h.setValue("a");
		classifier.addParameter(h);
		
		Parameter c =new Parameter("C",ParameterType.BOOLEAN);
		c.setValue("false");
		classifier.addParameter(c);
		
		Parameter r =new Parameter("R",ParameterType.BOOLEAN);
		r.setValue("false");
		classifier.addParameter(r);
		
		Parameter d =new Parameter("D",ParameterType.BOOLEAN);
		d.setValue("false");
		classifier.addParameter(d);
		
		Parameter s =new Parameter("S",ParameterType.INT);
		s.setValue("10");
		classifier.addParameter(s);
		
		return classifier;
	}

	@Override
	public Classifier randomBuild() {
		classifier = new Mlp();
		
		Parameter l =new Parameter("L",ParameterType.DOUBLE);
		l.setValue(randomValueForParameter(l));
		classifier.addParameter(l);
		
		Parameter m =new Parameter("M",ParameterType.DOUBLE);
		m.setValue(randomValueForParameter(m));
		classifier.addParameter(m);
		
		Parameter b =new Parameter("B",ParameterType.BOOLEAN);
		b.setValue(randomValueForParameter(b));
		classifier.addParameter(b);
		
		Parameter h =new Parameter("H",ParameterType.STRING);
		h.setValue(randomValueForParameter(h));
		classifier.addParameter(h);
		
		Parameter c =new Parameter("C",ParameterType.BOOLEAN);
		c.setValue(randomValueForParameter(c));
		classifier.addParameter(c);
		
		Parameter r =new Parameter("R",ParameterType.BOOLEAN);
		r.setValue(randomValueForParameter(r));
		classifier.addParameter(r);
		
		Parameter d =new Parameter("D",ParameterType.BOOLEAN);
		d.setValue(randomValueForParameter(d));
		classifier.addParameter(d);
		
		Parameter s =new Parameter("S",ParameterType.INT);
		s.setValue(randomValueForParameter(s));
		classifier.addParameter(s);
		
		return classifier;
	}

}
