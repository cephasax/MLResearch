package br.ufrn.imd.pbil.domain.committees;

import java.util.HashMap;
import java.util.Map;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.baseclassifiers.BayesNetBuilder;
import br.ufrn.imd.pbil.domain.baseclassifiers.BayesNetPrototype;
import br.ufrn.imd.pbil.domain.baseclassifiers.ClassifierBuilder;
import br.ufrn.imd.pbil.domain.baseclassifiers.DecisionTableBuilder;
import br.ufrn.imd.pbil.domain.baseclassifiers.DecisionTablePrototype;
import br.ufrn.imd.pbil.domain.baseclassifiers.IbkBuilder;
import br.ufrn.imd.pbil.domain.baseclassifiers.IbkPrototype;
import br.ufrn.imd.pbil.domain.baseclassifiers.J48Builder;
import br.ufrn.imd.pbil.domain.baseclassifiers.J48Prototype;
import br.ufrn.imd.pbil.domain.baseclassifiers.KstarBuilder;
import br.ufrn.imd.pbil.domain.baseclassifiers.KstarPrototype;
import br.ufrn.imd.pbil.domain.baseclassifiers.MlpBuilder;
import br.ufrn.imd.pbil.domain.baseclassifiers.MlpPrototype;
import br.ufrn.imd.pbil.domain.baseclassifiers.NaiveBayesBuilder;
import br.ufrn.imd.pbil.domain.baseclassifiers.NaiveBayesPrototype;
import br.ufrn.imd.pbil.domain.baseclassifiers.RandomTreeBuilder;
import br.ufrn.imd.pbil.domain.baseclassifiers.RandomTreePrototype;
import br.ufrn.imd.pbil.domain.baseclassifiers.SmoBuilder;
import br.ufrn.imd.pbil.domain.baseclassifiers.SmoPrototype;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class CommitteeFactory {
	
	private Map<String,ClassifierBuilder> builders;

	public CommitteeFactory() throws InvalidParameterTypeException {
		
		builders= new HashMap<String, ClassifierBuilder>();
		
		AdaBoostBuilder adaBuilder = new AdaBoostBuilder(new AdaBoostPrototype());
		builders.put("AdaBoost",adaBuilder);
		
		RandomCommitteeBuilder rcbuilder = new RandomCommitteeBuilder(new RandomCommitteePrototype());
		builders.put("RandomCommittee",rcbuilder);
		
		BaggingBuilder baggignbuilder = new BaggingBuilder( new BaggingPrototype());
		builders.put("Bagging",baggignbuilder);
		
		RandomForestBuilder rfbuilder = new RandomForestBuilder(new RandomForestPrototype());
		builders.put("RandomForest",rfbuilder);

		VoteBuilder votebuilder = new VoteBuilder(new VotePrototype());
		builders.put("Vote",votebuilder);
		
		StackingBuilder stbuilder = new StackingBuilder( new StackingPrototype());
		builders.put("Stacking",stbuilder);
		//Classifiers
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
		builders.put("RandomTree",rt);
		
		IbkBuilder ibk = new IbkBuilder(new IbkPrototype());
		builders.put("IBK", ibk);
		
		DecisionTableBuilder dt = new DecisionTableBuilder(new DecisionTablePrototype());
		builders.put("DecisionTable", dt);
	}
	
	public Classifier buildClassifier(String name) {
		return builders.get(name).randomBuild();
	}
	
}
