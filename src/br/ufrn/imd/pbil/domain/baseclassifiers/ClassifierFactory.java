package br.ufrn.imd.pbil.domain.baseclassifiers;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.enums.BaseClassifierConfig;
import br.ufrn.imd.pbil.enums.BaseClassifierType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class ClassifierFactory {
	static Classifier classifier;
	static ClassifierBuilder builder;
	static ClassifierPrototype prototype;
	public static Classifier buildClassifier(BaseClassifierType type, BaseClassifierConfig config) throws InvalidParameterTypeException {
		switch(type) {
			case DECISION_TABLE:
				prototype = new DecisionTablePrototype();
				builder = new DecisionTableBuilder(prototype);
				switch(config) {
					case DEFAULT:
						classifier = builder.defaultBuild();
						break;
					case RANDOM:
						classifier = builder.randomBuild();
						break;
					case WHIETED:
						//whieted
						classifier = builder.defaultBuild();
				}
				break;
			case J48:
				prototype = new J48Prototype();
				builder = new J48Builder(prototype);
				switch(config) {
					case DEFAULT:
						classifier = builder.defaultBuild();
						break;
					case RANDOM:
						classifier = builder.randomBuild();
						break;
					case WHIETED:
						//whieted
						classifier = builder.defaultBuild();
				}
				break;
			case RANDOM_TREE:
				prototype = new RandomTreePrototype();
				builder = new RandomTreeBuilder(prototype);
				switch(config) {
					case DEFAULT:
						classifier = builder.defaultBuild();
						break;
					case RANDOM:
						classifier = builder.randomBuild();
						break;
					case WHIETED:
						//whieted
						classifier = builder.defaultBuild();
				}
				break;
			case NAIVE_BAYES:
				prototype = new NaiveBayesPrototype();
				builder = new NaiveBayesBuilder(prototype);
				switch(config) {
					case DEFAULT:
						classifier = builder.defaultBuild();
						break;
					case RANDOM:
						classifier = builder.randomBuild();
						break;
					case WHIETED:
						//whieted
						classifier = builder.defaultBuild();
				}
				break;
			case NET:
				prototype = new BayesNetPrototype();
				builder = new BayesNetBuilder(prototype);
				switch(config) {
					case DEFAULT:
						classifier = builder.defaultBuild();
						break;
					case RANDOM:
						classifier = builder.randomBuild();
						break;
					case WHIETED:
						//whieted
						classifier = builder.defaultBuild();
						break;
				}
				break;
			case K_STAR:
				prototype = new KstarPrototype();
				builder = new KstarBuilder(prototype);
				switch(config) {
				case DEFAULT:
					classifier = builder.defaultBuild();
					break;
				case RANDOM:
					classifier = builder.randomBuild();
					break;
				case WHIETED:
					//whieted
					classifier = builder.defaultBuild();
					break;
				}
				break;
			case IBK:
				prototype = new IbkPrototype();
				builder = new IbkBuilder(prototype);
				switch(config) {
				case DEFAULT:
					classifier = builder.defaultBuild();
					break;
				case RANDOM:
					classifier = builder.randomBuild();
					break;
				case WHIETED:
					//whieted
					classifier = builder.defaultBuild();
				}
				break;
			case SMO:
				prototype = new SmoPrototype();
				builder = new SmoBuilder(prototype);
				switch(config) {
				case DEFAULT:
					classifier = builder.defaultBuild();
					break;
				case RANDOM:
					classifier = builder.randomBuild();
					break;
				case WHIETED:
					//whieted
					classifier = builder.defaultBuild();
				}
				break;
			case MULTI_LAYER_PECEPTRON:
				prototype = new KstarPrototype();
				builder = new KstarBuilder(prototype);
				switch(config) {
				case DEFAULT:
					classifier = builder.defaultBuild();
					break;
				case RANDOM:
					classifier = builder.randomBuild();
					break;
				case WHIETED:
					//whieted
					classifier = builder.defaultBuild();
				}
				break;
		}
		return classifier;
	}
}
