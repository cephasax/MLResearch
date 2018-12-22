package br.ufrn.imd.pbil.domain.committees;

import java.util.HashMap;
import java.util.Map;

import br.ufrn.imd.pbil.domain.ClassifierBuilder;
import br.ufrn.imd.pbil.domain.ClassifierFactory;
import br.ufrn.imd.pbil.domain.builders.comm.AdaBoostBuilder;
import br.ufrn.imd.pbil.domain.builders.comm.BaggingBuilder;
import br.ufrn.imd.pbil.domain.builders.comm.RandomCommitteeBuilder;
import br.ufrn.imd.pbil.domain.builders.comm.RandomForestBuilder;
import br.ufrn.imd.pbil.domain.builders.comm.StackingBuilder;
import br.ufrn.imd.pbil.domain.builders.comm.VoteBuilder;
import br.ufrn.imd.pbil.domain.prototypes.comm.AdaBoostPrototype;
import br.ufrn.imd.pbil.domain.prototypes.comm.BaggingPrototype;
import br.ufrn.imd.pbil.domain.prototypes.comm.RandomCommitteePrototype;
import br.ufrn.imd.pbil.domain.prototypes.comm.RandomForestPrototype;
import br.ufrn.imd.pbil.domain.prototypes.comm.StackingPrototype;
import br.ufrn.imd.pbil.domain.prototypes.comm.VotePrototype;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class CommitteeFactory extends ClassifierFactory{
	
	private Map<String,ClassifierBuilder> builders;

	public CommitteeFactory() throws InvalidParameterTypeException {
		
		builders = new HashMap<String, ClassifierBuilder>();
		
		AdaBoostBuilder adaBuilder = new AdaBoostBuilder(new AdaBoostPrototype());
		builders.put("AdaBoost",adaBuilder);
		
		RandomCommitteeBuilder rcbuilder = new RandomCommitteeBuilder(new RandomCommitteePrototype());
		builders.put("RandomCommittee",rcbuilder);
		
		BaggingBuilder baggignbuilder = new BaggingBuilder(new BaggingPrototype());
		builders.put("Bagging",baggignbuilder);
		
		RandomForestBuilder rfbuilder = new RandomForestBuilder(new RandomForestPrototype());
		builders.put("RandomForest",rfbuilder);

		VoteBuilder votebuilder = new VoteBuilder(new VotePrototype());
		builders.put("Vote",votebuilder);
		
		StackingBuilder stbuilder = new StackingBuilder(new StackingPrototype());
		builders.put("Stacking",stbuilder);
		
	}
}
