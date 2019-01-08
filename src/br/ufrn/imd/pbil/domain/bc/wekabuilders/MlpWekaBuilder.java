package br.ufrn.imd.pbil.domain.bc.wekabuilders;

import br.ufrn.imd.pbil.pde.PossibilityKeySet;
import weka.classifiers.functions.MultilayerPerceptron;

public class MlpWekaBuilder {

	public static MultilayerPerceptron buildForWeka(PossibilityKeySet pks) {
		MultilayerPerceptron mlp = new MultilayerPerceptron();
		
		mlp.setLearningRate(Float.parseFloat(pks.getKeyValuesPairs().get("L")));
		mlp.setMomentum(Float.parseFloat(pks.getKeyValuesPairs().get("M")));
		mlp.setNominalToBinaryFilter(Boolean.parseBoolean(pks.getKeyValuesPairs().get("B")));
		mlp.setHiddenLayers(pks.getKeyValuesPairs().get("H"));
		mlp.setNormalizeNumericClass(Boolean.parseBoolean(pks.getKeyValuesPairs().get("C")));
		mlp.setReset(Boolean.parseBoolean(pks.getKeyValuesPairs().get("R")));
		mlp.setDecay(Boolean.parseBoolean(pks.getKeyValuesPairs().get("D")));
		mlp.setSeed(Integer.parseInt(pks.getKeyValuesPairs().get("S")));
		
		
		return mlp;
	}
	
}
