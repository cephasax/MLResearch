package br.ufrn.imd.pbil.domain.committees;

import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.enums.CommitteeType;
import br.ufrn.imd.pbil.enums.ConfigurationType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class CommitteeFactory {
	
	private static Committee committe;
	private static CommitteeBuilder builder;
	private static CommitteePrototype prototype;

	
	public static Committee builCommittee(CommitteeType type, ConfigurationType config) throws InvalidParameterTypeException {
		switch(type) {
		case ADA_BOOST:
			prototype = new AdaBoostPrototype();
			builder = new AdaBoostBuilder(prototype);
			switch(config) {
			case DEFAULT:
				committe = builder.defaultBuild();
				break;
			case RANDOM:
				committe = builder.randomBuild();
				break;
			case WHEIGHTED:
				committe = builder.defaultBuild();
				break;
			}
			break;
		case BAGGING:
			prototype = new BaggingPrototype();
			builder = new BaggingBuilder(prototype);
			switch(config) {
			case DEFAULT:
				committe = builder.defaultBuild();
				break;
			case RANDOM:
				committe = builder.randomBuild();
				break;
			case WHEIGHTED:
				committe = builder.defaultBuild();
				break;
			}
			break;
		case RANDOM_COMMITTEE:
			prototype = new RandomCommitteePrototype();
			builder = new RandomCommitteeBuilder(prototype);
			switch(config) {
			case DEFAULT:
				committe = builder.defaultBuild();
				break;
			case RANDOM:
				committe = builder.randomBuild();
				break;
			case WHEIGHTED:
				committe = builder.defaultBuild();
				break;
			}
			break;
		case RANDOM_FOREST:
			prototype = new RandomForestPrototype();
			builder = new RandomForestBuilder(prototype);
			switch(config) {
			case DEFAULT:
				committe = builder.defaultBuild();
				break;
			case RANDOM:
				committe = builder.randomBuild();
				break;
			case WHEIGHTED:
				committe = builder.defaultBuild();
				break;
			}
			break;
		case STACKING:
			prototype = new StackingPrototype();
			builder = new StackingBuilder(prototype);
			switch(config) {
			case DEFAULT:
				committe = builder.defaultBuild();
				break;
			case RANDOM:
				committe = builder.randomBuild();
				break;
			case WHEIGHTED:
				committe = builder.defaultBuild();
				break;
			}
			break;
		case VOTE:
			prototype = new VotePrototype();
			builder = new VoteBuilder(prototype);
			switch(config) {
			case DEFAULT:
				committe = builder.defaultBuild();
				break;
			case RANDOM:
				committe = builder.randomBuild();
				break;
			case WHEIGHTED:
				committe = builder.defaultBuild();
				break;
			}
			break;
		}
		return committe;
	}

}
