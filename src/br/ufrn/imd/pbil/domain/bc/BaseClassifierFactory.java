package br.ufrn.imd.pbil.domain.bc;

import java.util.ArrayList;
import java.util.HashMap;

import br.ufrn.imd.pbil.domain.ClassifierBuilder;
import br.ufrn.imd.pbil.domain.ClassifierFactory;
import br.ufrn.imd.pbil.domain.bc.builders.BayesNetBuilder;
import br.ufrn.imd.pbil.domain.bc.builders.DecisionTableBuilder;
import br.ufrn.imd.pbil.domain.bc.builders.IbkBuilder;
import br.ufrn.imd.pbil.domain.bc.builders.J48Builder;
import br.ufrn.imd.pbil.domain.bc.builders.KstarBuilder;
import br.ufrn.imd.pbil.domain.bc.builders.MlpBuilder;
import br.ufrn.imd.pbil.domain.bc.builders.NaiveBayesBuilder;
import br.ufrn.imd.pbil.domain.bc.builders.RandomTreeBuilder;
import br.ufrn.imd.pbil.domain.bc.builders.SmoBuilder;
import br.ufrn.imd.pbil.domain.bc.prototypes.BayesNetPrototype;
import br.ufrn.imd.pbil.domain.bc.prototypes.DecisionTablePrototype;
import br.ufrn.imd.pbil.domain.bc.prototypes.IbkPrototype;
import br.ufrn.imd.pbil.domain.bc.prototypes.J48Prototype;
import br.ufrn.imd.pbil.domain.bc.prototypes.KstarPrototype;
import br.ufrn.imd.pbil.domain.bc.prototypes.MlpPrototype;
import br.ufrn.imd.pbil.domain.bc.prototypes.NaiveBayesPrototype;
import br.ufrn.imd.pbil.domain.bc.prototypes.RandomTreePrototype;
import br.ufrn.imd.pbil.domain.bc.prototypes.SmoPrototype;
import br.ufrn.imd.pbil.enums.BaseClassifierType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class BaseClassifierFactory extends ClassifierFactory{
	
	public BaseClassifierFactory() throws InvalidParameterTypeException {
				
		builders = new HashMap<String, ClassifierBuilder>();
		buildNames();
		
		BayesNetBuilder bn = new BayesNetBuilder(new BayesNetPrototype());
		builders.put(BaseClassifierType.NET.name(), bn);

		DecisionTableBuilder dt = new DecisionTableBuilder(new DecisionTablePrototype());
		builders.put(BaseClassifierType.DECISION_TABLE.getInfo(), dt);
		
		IbkBuilder ibk = new IbkBuilder(new IbkPrototype());
		builders.put(BaseClassifierType.IBK.getInfo(), ibk);

		J48Builder j48 = new J48Builder(new J48Prototype());
		builders.put(BaseClassifierType.J48.getInfo(), j48);
		
		KstarBuilder kstar = new KstarBuilder(new KstarPrototype());
		builders.put(BaseClassifierType.K_STAR.getInfo(), kstar);
		
		MlpBuilder mlp = new MlpBuilder(new MlpPrototype());
		builders.put(BaseClassifierType.MULTI_LAYER_PECEPTRON.getInfo(), mlp);
		
		NaiveBayesBuilder nb = new NaiveBayesBuilder(new NaiveBayesPrototype());
		builders.put(BaseClassifierType.NAIVE_BAYES.getInfo(), nb);
		
		RandomTreeBuilder rt = new RandomTreeBuilder(new RandomTreePrototype());
		builders.put(BaseClassifierType.RANDOM_TREE.getInfo(), rt);
		
		SmoBuilder smo = new SmoBuilder(new SmoPrototype());
		builders.put(BaseClassifierType.SMO.getInfo(), smo);
	}
	
	private void buildNames() {
		classifierNames = new ArrayList<String>();
		classifierNames.add(BaseClassifierType.NET.getInfo());
		classifierNames.add(BaseClassifierType.DECISION_TABLE.getInfo());
		classifierNames.add(BaseClassifierType.IBK.getInfo());
		classifierNames.add(BaseClassifierType.J48.getInfo());
		classifierNames.add(BaseClassifierType.K_STAR.getInfo());
		classifierNames.add(BaseClassifierType.MULTI_LAYER_PECEPTRON.getInfo());
		classifierNames.add(BaseClassifierType.NAIVE_BAYES.getInfo());
		classifierNames.add(BaseClassifierType.RANDOM_TREE.getInfo());
		classifierNames.add(BaseClassifierType.SMO.getInfo());
	}
	
}
