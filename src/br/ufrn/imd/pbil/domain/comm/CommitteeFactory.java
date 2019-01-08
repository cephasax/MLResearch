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
import br.ufrn.imd.pbil.enums.CommitteeType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class CommitteeFactory extends ClassifierFactory{

	public CommitteeFactory() throws InvalidParameterTypeException {
		
		builders = new HashMap<String, ClassifierBuilder>();
		buildNames();
		AdaBoostBuilder adaBuilder = new AdaBoostBuilder(new AdaBoostPrototype());
		builders.put(CommitteeType.ADA_BOOST.getInfo(),adaBuilder);
		
		BaggingBuilder baggignbuilder = new BaggingBuilder(new BaggingPrototype());
		builders.put(CommitteeType.BAGGING.getInfo(),baggignbuilder);
		
		RandomCommitteeBuilder rcbuilder = new RandomCommitteeBuilder(new RandomCommitteePrototype());
		builders.put(CommitteeType.RANDOM_COMMITTEE.getInfo(),rcbuilder);
		
		RandomForestBuilder rfbuilder = new RandomForestBuilder(new RandomForestPrototype());
		builders.put(CommitteeType.RANDOM_FOREST.getInfo(),rfbuilder);

		StackingBuilder stbuilder = new StackingBuilder(new StackingPrototype());
		builders.put(CommitteeType.STACKING.getInfo(),stbuilder);
		
		VoteBuilder votebuilder = new VoteBuilder(new VotePrototype());
		builders.put(CommitteeType.VOTE.getInfo(),votebuilder);
	}
	
	private void buildNames() {
		classifierNames = new ArrayList<String>();
		classifierNames.add(CommitteeType.ADA_BOOST.getInfo());
		classifierNames.add(CommitteeType.BAGGING.getInfo());
		classifierNames.add(CommitteeType.RANDOM_COMMITTEE.getInfo());
		classifierNames.add(CommitteeType.RANDOM_FOREST.getInfo());
		classifierNames.add(CommitteeType.STACKING.getInfo());
		classifierNames.add(CommitteeType.VOTE.getInfo());
	}
}
