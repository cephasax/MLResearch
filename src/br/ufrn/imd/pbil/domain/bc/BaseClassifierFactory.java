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
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class BaseClassifierFactory extends ClassifierFactory{
	
	public BaseClassifierFactory() throws InvalidParameterTypeException {
				
		builders = new HashMap<String, ClassifierBuilder>();
		buildNames();
		
		BayesNetBuilder bn = new BayesNetBuilder(new BayesNetPrototype());
		builders.put("weka.classifiers.bayes.BayesNet", bn);

		DecisionTableBuilder dt = new DecisionTableBuilder(new DecisionTablePrototype());
		builders.put("weka.classifiers.rules.DecisionTable", dt);
		
		IbkBuilder ibk = new IbkBuilder(new IbkPrototype());
		builders.put("weka.classifiers.lazy.IBk", ibk);

		J48Builder j48 = new J48Builder(new J48Prototype());
		builders.put("weka.classifiers.trees.J48", j48);
		
		KstarBuilder kstar = new KstarBuilder(new KstarPrototype());
		builders.put("weka.classifiers.lazy.KStar", kstar);
		
		MlpBuilder mlp = new MlpBuilder(new MlpPrototype());
		builders.put("weka.classifiers.functions.MultilayerPerceptron", mlp);
		
		NaiveBayesBuilder nb = new NaiveBayesBuilder(new NaiveBayesPrototype());
		builders.put("weka.classifiers.bayes.NaiveBayes", nb);
		
		RandomTreeBuilder rt = new RandomTreeBuilder(new RandomTreePrototype());
		builders.put("weka.classifiers.trees.RandomTree", rt);
		
		SmoBuilder smo = new SmoBuilder(new SmoPrototype());
		builders.put("weka.classifiers.functions.SMO", smo);
	}
	
	private void buildNames() {
		classifierNames = new ArrayList<String>();
		classifierNames.add("weka.classifiers.bayes.BayesNet");
		classifierNames.add("weka.classifiers.rules.DecisionTable");
		classifierNames.add("weka.classifiers.lazy.IBk");
		classifierNames.add("weka.classifiers.trees.J48");
		classifierNames.add("weka.classifiers.lazy.Kstar");
		classifierNames.add("weka.classifiers.functions.MultilayerPerceptron");
		classifierNames.add("weka.classifiers.bayes.NaiveBaye");
		classifierNames.add("weka.classifiers.trees.RandomTree");
		classifierNames.add("weka.classifiers.functions.SMO");
	}
	
}
