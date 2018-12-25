package br.ufrn.imd.pbil.domain.comm;

import java.util.ArrayList;
import java.util.HashMap;

import br.ufrn.imd.pbil.domain.ClassifierBuilder;
import br.ufrn.imd.pbil.domain.ClassifierFactory;
import br.ufrn.imd.pbil.domain.comm.builders.AdaBoostBuilder;
import br.ufrn.imd.pbil.domain.comm.builders.BaggingBuilder;
import br.ufrn.imd.pbil.domain.comm.builders.RandomCommitteeBuilder;
import br.ufrn.imd.pbil.domain.comm.builders.RandomForestBuilder;
import br.ufrn.imd.pbil.domain.comm.builders.StackingBuilder;
import br.ufrn.imd.pbil.domain.comm.builders.VoteBuilder;
import br.ufrn.imd.pbil.domain.comm.prototypes.AdaBoostPrototype;
import br.ufrn.imd.pbil.domain.comm.prototypes.BaggingPrototype;
import br.ufrn.imd.pbil.domain.comm.prototypes.RandomCommitteePrototype;
import br.ufrn.imd.pbil.domain.comm.prototypes.RandomForestPrototype;
import br.ufrn.imd.pbil.domain.comm.prototypes.StackingPrototype;
import br.ufrn.imd.pbil.domain.comm.prototypes.VotePrototype;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class CommitteeFactory extends ClassifierFactory{

	public CommitteeFactory() throws InvalidParameterTypeException {
		
		builders = new HashMap<String, ClassifierBuilder>();
		buildNames();
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
	
	private void buildNames() {
		classifierNames = new ArrayList<String>();
		classifierNames.add("AdaBoost");
		classifierNames.add("Bagging");
		classifierNames.add("RandomCommittee");
		classifierNames.add("RandomForest");
		classifierNames.add("Stacking");
		classifierNames.add("Vote");
	}
}
