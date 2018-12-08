package br.ufrn.imd.pbil.domain.builders;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.RandomTree;
import br.ufrn.imd.pbil.domain.prototypes.ClassifierPrototype;
import br.ufrn.imd.pbil.enums.ParameterType;

public class RandomTreeBuilder extends ClassifierBuilder{

	public RandomTreeBuilder(ClassifierPrototype classifierPrototype) {
		super(classifierPrototype);
	}

	@Override
	public Classifier defautBuild() {
		classifier = new RandomTree();
		
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
		
		return classifier;
	}

	@Override
	public Classifier randomBuild() {
		classifier = new RandomTree();
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
		
		return classifier;
	}

	@Override
	public Classifier weightedDrawBuild() {
		return null;
	}

}
