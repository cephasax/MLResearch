package br.ufrn.imd.pbil.domain.baseclassifiers;

import java.util.HashMap;
import java.util.Map;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.enums.BaseClassifierType;
import br.ufrn.imd.pbil.enums.ConfigurationType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class ClassifierFactory {
	Map<Integer, ClassifierBuilder >classifier;
	static ClassifierBuilder builder;
	static ClassifierPrototype prototype;
	
	public ClassifierFactory() throws InvalidParameterTypeException {
		classifier = new HashMap<Integer, ClassifierBuilder>();
		
		DecisionTablePrototype dcP = new DecisionTablePrototype();
		DecisionTableBuilder dcBuilder = new DecisionTableBuilder(dcP);
		classifier.put(0,dcBuilder);
	
		IbkPrototype ibP = new IbkPrototype();
		IbkBuilder ibBuilder = new IbkBuilder(ibP);
		classifier.put(2,ibBuilder);
		
		J48Prototype j48P = new J48Prototype();
		J48Builder j48Builder = new J48Builder(j48P);
		classifier.put(3,j48Builder);

		KstarPrototype ksP = new KstarPrototype();
		KstarBuilder ksBuilder = new KstarBuilder(ksP);
		classifier.put(4,ksBuilder);

		MlpPrototype mlpP = new MlpPrototype();
		MlpBuilder mlpBuilder = new MlpBuilder(mlpP);
		classifier.put(5,mlpBuilder);
						
		NaiveBayesPrototype nbP = new NaiveBayesPrototype();
		NaiveBayesBuilder nbBuilder = new NaiveBayesBuilder(nbP);
		classifier.put(1,nbBuilder);
		
		BayesNetPrototype bnP = new BayesNetPrototype();
		BayesNetBuilder bnBuilder = new BayesNetBuilder(bnP);
		classifier.put(6,bnBuilder);
		
		RandomTreePrototype rtP = new RandomTreePrototype();
		RandomTreeBuilder rtBuilder = new RandomTreeBuilder(rtP);
		classifier.put(7,rtBuilder);

		SmoPrototype smoP = new SmoPrototype();
		SmoBuilder smoBuilder = new SmoBuilder(smoP);
		classifier.put(8,smoBuilder);
		
	
	}
	public Classifier buildClassifier(int index, int config) throws InvalidParameterTypeException {
		if(config==0) {
			return classifier.get(index).defaultBuild();
		}
		else {
			return classifier.get(index).randomBuild();
		}
	}
}
