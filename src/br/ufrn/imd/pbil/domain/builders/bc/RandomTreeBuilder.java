package br.ufrn.imd.pbil.domain.builders.bc;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.ClassifierBuilder;
import br.ufrn.imd.pbil.domain.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.baseclassifiers.RandomTree;
import br.ufrn.imd.pbil.enums.ParameterType;

public class RandomTreeBuilder extends ClassifierBuilder{

	public RandomTreeBuilder(ClassifierPrototype classifierPrototype) {
		super(classifierPrototype);
	}

	@Override
	public Classifier defaultBuild() {
		classifier = new RandomTree();
		classifier.setName("Default Random Tree");
		Parameter m = new Parameter("M",ParameterType.INT);
		m.setValue("1");
		classifier.addParameter(m);
		
		Parameter k = new Parameter("K",ParameterType.INT);
		k.setValue("2");
		classifier.addParameter(k);
		
		Parameter depth = new Parameter("depth",ParameterType.INT);
		depth.setValue("2");
		classifier.addParameter(depth);
		
		Parameter n = new Parameter("N",ParameterType.INT);
		n.setValue("3");
		classifier.addParameter(n);
		
		Parameter u = new Parameter("U",ParameterType.BOOLEAN);
		u.setValue("false");
		classifier.addParameter(u);
		
		return classifier;
	}

	@Override
	public Classifier randomBuild() {
		classifier = new RandomTree();
		classifier.setName("Random Random Tree");
		Parameter m = new Parameter("M",ParameterType.INT);
		m.setValue(randomValueForParameter(m));
		classifier.addParameter(m);
		
		Parameter k = new Parameter("K",ParameterType.INT);
		k.setValue(randomValueForParameter(k));
		classifier.addParameter(k);
		
		Parameter depth = new Parameter("depth",ParameterType.INT);
		depth.setValue(randomValueForParameter(depth));
		classifier.addParameter(depth);
		
		Parameter n = new Parameter("N",ParameterType.INT);
		n.setValue(randomValueForParameter(n));
		classifier.addParameter(n);
		
		Parameter u = new Parameter("U",ParameterType.BOOLEAN);
		u.setValue(randomValueForParameter(u));
		classifier.addParameter(u);
		return classifier;
	}

	@Override
	public Classifier weightedDrawBuild() {
		return null;
	}

}
