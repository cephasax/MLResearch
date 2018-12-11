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
		ClassifierBuilder builder;
		ClassifierPrototype prototype;
		switch(type) {
			case DECISION_TABLE:
				switch(config) {
					case DEFAUT:
						prototype = new DecisionTablePrototype();
						builder = new DecisionTableBuilder(prototype);
						classifier = builder.defautBuild();
						break;
					case RANDOM:
						prototype = new DecisionTablePrototype();
						builder = new DecisionTableBuilder(prototype);
						classifier = builder.randomBuild();
						break;
					case WHIETED:
						prototype = new DecisionTablePrototype();
						builder = new DecisionTableBuilder(prototype);
						//whieted
						classifier = builder.defautBuild();
				}
				break;
			case J48:
				switch(config) {
					case DEFAUT:
						prototype = new J48Prototype();
						builder = new J48Builder(prototype);
						classifier = builder.defautBuild();
						break;
					case RANDOM:
						prototype = new J48Prototype();
						builder = new J48Builder(prototype);
						classifier = builder.randomBuild();
						break;
					case WHIETED:
						prototype = new J48Prototype();
						builder = new J48Builder(prototype);
						//whieted
						classifier = builder.defautBuild();
				}
				break;
			case RANDOM_TREE:
				switch(config) {
					case DEFAUT:
						prototype = new RandomTreePrototype();
						builder = new RandomTreeBuilder(prototype);
						classifier = builder.defautBuild();
						break;
					case RANDOM:
						prototype = new RandomTreePrototype();
						builder = new RandomTreeBuilder(prototype);
						classifier = builder.randomBuild();
						break;
					case WHIETED:
						prototype = new RandomTreePrototype();
						builder = new RandomTreeBuilder(prototype);
						//whieted
						classifier = builder.defautBuild();
				}
				break;
			case NAIVE_BAYES:
				switch(config) {
					case DEFAUT:
						prototype = new NaiveBayesPrototype();
						builder = new NaiveBayesBuilder(prototype);
						classifier = builder.defautBuild();
						break;
					case RANDOM:
						prototype = new RandomTreePrototype();
						builder = new RandomTreeBuilder(prototype);
						classifier = builder.randomBuild();
						break;
					case WHIETED:
						prototype = new RandomTreePrototype();
						builder = new RandomTreeBuilder(prototype);
						//whieted
						classifier = builder.defautBuild();
				}
				break;
			case NET:
				switch(config) {
					case DEFAUT:
						prototype = new BayesNetPrototype();
						builder = new BayesNetBuilder(prototype);
						classifier = builder.defautBuild();
						break;
					case RANDOM:
						prototype = new BayesNetPrototype();
						builder = new BayesNetBuilder(prototype);
						classifier = builder.randomBuild();
						break;
					case WHIETED:
						prototype = new BayesNetPrototype();
						builder = new BayesNetBuilder(prototype);
						//whieted
						classifier = builder.defautBuild();
						break;
				}
				break;
			case K_STAR:
				switch(config) {
				case DEFAUT:
					prototype = new KstarPrototype();
					builder = new KstarBuilder(prototype);
					classifier = builder.defautBuild();
					break;
				case RANDOM:
					prototype = new KstarPrototype();
					builder = new KstarBuilder(prototype);
					classifier = builder.randomBuild();
					break;
				case WHIETED:
					prototype = new KstarPrototype();
					builder = new KstarBuilder(prototype);
					//whieted
					classifier = builder.defautBuild();
					break;
				}
				break;
			case IBK:
				switch(config) {
				case DEFAUT:
					prototype = new IbkPrototype();
					builder = new IbkBuilder(prototype);
					classifier = builder.defautBuild();
					break;
				case RANDOM:
					prototype = new IbkPrototype();
					builder = new IbkBuilder(prototype);
					classifier = builder.randomBuild();
					break;
				case WHIETED:
					prototype = new IbkPrototype();
					builder = new IbkBuilder(prototype);
					//whieted
					classifier = builder.defautBuild();
				}
				break;
			case SMO:
				switch(config) {
				case DEFAUT:
					prototype = new SmoPrototype();
					builder = new SmoBuilder(prototype);
					classifier = builder.defautBuild();
					break;
				case RANDOM:
					prototype = new SmoPrototype();
					builder = new SmoBuilder(prototype);
					classifier = builder.randomBuild();
					break;
				case WHIETED:
					prototype = new SmoPrototype();
					builder = new SmoBuilder(prototype);
					//whieted
					classifier = builder.defautBuild();
				}
				break;
			case MULTI_LAYER_PECEPTRON:
				switch(config) {
				case DEFAUT:
					prototype = new KstarPrototype();
					builder = new KstarBuilder(prototype);
					classifier = builder.defautBuild();
					break;
				case RANDOM:
					prototype = new KstarPrototype();
					builder = new KstarBuilder(prototype);
					classifier = builder.randomBuild();
					break;
				case WHIETED:
					prototype = new KstarPrototype();
					builder = new KstarBuilder(prototype);
					//whieted
					classifier = builder.defautBuild();
				}
				break;
		}
		return classifier;
	}
}
