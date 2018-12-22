package br.ufrn.imd.pbil.domain;

import java.util.HashMap;

import br.ufrn.imd.pbil.domain.builders.bc.BayesNetBuilder;
import br.ufrn.imd.pbil.domain.builders.bc.DecisionTableBuilder;
import br.ufrn.imd.pbil.domain.builders.bc.IbkBuilder;
import br.ufrn.imd.pbil.domain.builders.bc.J48Builder;
import br.ufrn.imd.pbil.domain.builders.bc.KstarBuilder;
import br.ufrn.imd.pbil.domain.builders.bc.MlpBuilder;
import br.ufrn.imd.pbil.domain.builders.bc.NaiveBayesBuilder;
import br.ufrn.imd.pbil.domain.builders.bc.RandomTreeBuilder;
import br.ufrn.imd.pbil.domain.builders.bc.SmoBuilder;
import br.ufrn.imd.pbil.domain.prototypes.bc.BayesNetPrototype;
import br.ufrn.imd.pbil.domain.prototypes.bc.DecisionTablePrototype;
import br.ufrn.imd.pbil.domain.prototypes.bc.IbkPrototype;
import br.ufrn.imd.pbil.domain.prototypes.bc.J48Prototype;
import br.ufrn.imd.pbil.domain.prototypes.bc.KstarPrototype;
import br.ufrn.imd.pbil.domain.prototypes.bc.MlpPrototype;
import br.ufrn.imd.pbil.domain.prototypes.bc.NaiveBayesPrototype;
import br.ufrn.imd.pbil.domain.prototypes.bc.RandomTreePrototype;
import br.ufrn.imd.pbil.domain.prototypes.bc.SmoPrototype;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class BaseClassifierFactory extends ClassifierFactory{
	
	public BaseClassifierFactory() throws InvalidParameterTypeException {
		
		builders = new HashMap<String, ClassifierBuilder>();
		
		MlpBuilder mlp = new MlpBuilder(new MlpPrototype());
		builders.put("MLP", mlp);
		
		J48Builder j48 = new J48Builder(new J48Prototype());
		builders.put("J48", j48);
		
		SmoBuilder smo = new SmoBuilder(new SmoPrototype());
		builders.put("SMO", smo);
		
		KstarBuilder kstar = new KstarBuilder(new KstarPrototype());
		builders.put("Kstar", kstar);
		
		NaiveBayesBuilder nb = new NaiveBayesBuilder(new NaiveBayesPrototype());
		builders.put("NaiveBayes", nb);
		
		BayesNetBuilder bn = new BayesNetBuilder(new BayesNetPrototype());
		builders.put("BayesNet", bn);
		
		RandomTreeBuilder rt = new RandomTreeBuilder(new RandomTreePrototype());
		builders.put("RandomTree", rt);
		
		IbkBuilder ibk = new IbkBuilder(new IbkPrototype());
		builders.put("IBK", ibk);
		
		DecisionTableBuilder dt = new DecisionTableBuilder(new DecisionTablePrototype());
		builders.put("DecisionTable", dt);
	}
	
}
