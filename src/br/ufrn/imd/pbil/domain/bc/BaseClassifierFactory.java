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
		builders.put("BayesNet", bn);

		DecisionTableBuilder dt = new DecisionTableBuilder(new DecisionTablePrototype());
		builders.put("DecisionTable", dt);
		
		IbkBuilder ibk = new IbkBuilder(new IbkPrototype());
		builders.put("IBK", ibk);

		J48Builder j48 = new J48Builder(new J48Prototype());
		builders.put("J48", j48);
		
		KstarBuilder kstar = new KstarBuilder(new KstarPrototype());
		builders.put("Kstar", kstar);
		
		MlpBuilder mlp = new MlpBuilder(new MlpPrototype());
		builders.put("MLP", mlp);
		
		NaiveBayesBuilder nb = new NaiveBayesBuilder(new NaiveBayesPrototype());
		builders.put("NaiveBayes", nb);
		
		RandomTreeBuilder rt = new RandomTreeBuilder(new RandomTreePrototype());
		builders.put("RandomTree", rt);
		
		SmoBuilder smo = new SmoBuilder(new SmoPrototype());
		builders.put("SMO", smo);
	}
	
	private void buildNames() {
		classifierNames = new ArrayList<String>();
		classifierNames.add("BayesNet");
		classifierNames.add("DecisionTable");
		classifierNames.add("IBK");
		classifierNames.add("J48");
		classifierNames.add("Kstar");
		classifierNames.add("MLP");
		classifierNames.add("NaiveBayes");
		classifierNames.add("RandomTree");
		classifierNames.add("SMO");
	}
	
}
