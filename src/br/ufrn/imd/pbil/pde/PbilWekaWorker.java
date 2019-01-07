package br.ufrn.imd.pbil.pde;

import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class PbilWekaWorker {
	
	private String base;
	DataSource data ;
	Instances dataset;
	
	public PbilWekaWorker(String base) throws Exception{
		this.base = base;
		data = new DataSource(this.base);
		dataset = data.getDataSet();
		dataset.setClassIndex(dataset.numAttributes() - 1);
	}
		
	public double runSolution(PossibilityKeySet possibilityKeySet) throws Exception {
		
		String[] options = builOptios(possibilityKeySet);
		float right = 0;
		float []accuracy = new float[5];
		
	}
}
