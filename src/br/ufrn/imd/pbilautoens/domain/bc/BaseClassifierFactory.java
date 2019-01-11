package br.ufrn.imd.pbilautoens.domain.bc;

import java.util.ArrayList;
import java.util.HashMap;

import br.ufrn.imd.pbilautoens.ClassifierBuilder;
import br.ufrn.imd.pbilautoens.ClassifierFactory;
import br.ufrn.imd.pbilautoens.PbilRandom;
import br.ufrn.imd.pbilautoens.domain.bc.builders.BayesNetBuilder;
import br.ufrn.imd.pbilautoens.domain.bc.builders.DecisionTableBuilder;
import br.ufrn.imd.pbilautoens.domain.bc.builders.IbkBuilder;
import br.ufrn.imd.pbilautoens.domain.bc.builders.J48Builder;
import br.ufrn.imd.pbilautoens.domain.bc.builders.KstarBuilder;
import br.ufrn.imd.pbilautoens.domain.bc.builders.MlpBuilder;
import br.ufrn.imd.pbilautoens.domain.bc.builders.NaiveBayesBuilder;
import br.ufrn.imd.pbilautoens.domain.bc.builders.RandomTreeBuilder;
import br.ufrn.imd.pbilautoens.domain.bc.builders.SmoBuilder;
import br.ufrn.imd.pbilautoens.domain.bc.prototypes.BayesNetPrototype;
import br.ufrn.imd.pbilautoens.domain.bc.prototypes.DecisionTablePrototype;
import br.ufrn.imd.pbilautoens.domain.bc.prototypes.IbkPrototype;
import br.ufrn.imd.pbilautoens.domain.bc.prototypes.J48Prototype;
import br.ufrn.imd.pbilautoens.domain.bc.prototypes.KstarPrototype;
import br.ufrn.imd.pbilautoens.domain.bc.prototypes.MlpPrototype;
import br.ufrn.imd.pbilautoens.domain.bc.prototypes.NaiveBayesPrototype;
import br.ufrn.imd.pbilautoens.domain.bc.prototypes.RandomTreePrototype;
import br.ufrn.imd.pbilautoens.domain.bc.prototypes.SmoPrototype;
import br.ufrn.imd.pbilautoens.enums.BaseClassifierType;
import br.ufrn.imd.pbilautoens.exception.InvalidParameterTypeException;

public class BaseClassifierFactory extends ClassifierFactory{
	
	public BaseClassifierFactory(PbilRandom pbilRandom) throws InvalidParameterTypeException {
				
		builders = new HashMap<String, ClassifierBuilder>();
		buildNames();
		
		BayesNetBuilder bn = new BayesNetBuilder(new BayesNetPrototype(), pbilRandom);
		builders.put(BaseClassifierType.NET.name(), bn);

		DecisionTableBuilder dt = new DecisionTableBuilder(new DecisionTablePrototype(), pbilRandom);
		builders.put(BaseClassifierType.DECISION_TABLE.getInfo(), dt);
		
		IbkBuilder ibk = new IbkBuilder(new IbkPrototype(), pbilRandom);
		builders.put(BaseClassifierType.IBK.getInfo(), ibk);

		J48Builder j48 = new J48Builder(new J48Prototype(), pbilRandom);
		builders.put(BaseClassifierType.J48.getInfo(), j48);
		
		KstarBuilder kstar = new KstarBuilder(new KstarPrototype(), pbilRandom);
		builders.put(BaseClassifierType.K_STAR.getInfo(), kstar);
		
		MlpBuilder mlp = new MlpBuilder(new MlpPrototype(), pbilRandom);
		builders.put(BaseClassifierType.MULTI_LAYER_PECEPTRON.getInfo(), mlp);
		
		NaiveBayesBuilder nb = new NaiveBayesBuilder(new NaiveBayesPrototype(), pbilRandom);
		builders.put(BaseClassifierType.NAIVE_BAYES.getInfo(), nb);
		
		RandomTreeBuilder rt = new RandomTreeBuilder(new RandomTreePrototype(), pbilRandom);
		builders.put(BaseClassifierType.RANDOM_TREE.getInfo(), rt);
		
		SmoBuilder smo = new SmoBuilder(new SmoPrototype(), pbilRandom);
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
