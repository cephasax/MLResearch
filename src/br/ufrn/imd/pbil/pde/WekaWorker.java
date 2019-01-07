package br.ufrn.imd.pbil.pde;

import java.util.Set;

import br.ufrn.imd.pbil.domain.Committee;
import br.ufrn.imd.pbil.domain.Individual;
import br.ufrn.imd.pbil.enums.ClassifierType;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class WekaWorker {
	
	private String base;
	DataSource data ;
	Instances dataset;
		
	public WekaWorker(String base) throws Exception{
		this.base = base;
		data = new DataSource(this.base);
		dataset = data.getDataSet();
	}
		
	public double runSolution(PossibilityKeySet possibilityKeySet) throws Exception {
		
		dataset.setClassIndex(dataset.numAttributes() - 1);
		String[] options = builOptios(possibilityKeySet);
		float right = 0;
		float []accuracy = new float[5];
		
	}
	
	private String[] builOptios(PossibilityKeySet possibilityKeySet) {
		
		String[] options = new String[sizeOfOptions(possibilityKeySet)];
		Set<String> keys = possibilityKeySet.getKeyValuesPairs().keySet();
		int i =0;
		for (String key: keys) {
			if(possibilityKeySet.getKeyValuesPairs().get(key).equals("true") 
					||possibilityKeySet.getKeyValuesPairs().get(key).equals("false")) {
				if(possibilityKeySet.getKeyValuesPairs().get(key).equals("true")) {
					options[i] = "-"+key;					
					i++;
				}
			}
			else if (key.equals("num")) {
				continue;
			}
			else {				
				options[i] = "-"+key;
				options[i+1] = possibilityKeySet.getKeyValuesPairs().get(key);
				i+=2;
			}
		}
		Committee committee = null;
		if(possibilityKeySet.getBranchClassifiers().size() > 0) {
			committee = (Committee) classifier;
		}
		for (PossibilityKeySet pk : pks.getBranchClassifiers()) {
			options[i] = "-" + committee.getParameterClassifier();
			options[i+1] = pk.getKey();
			i += 2;
		}
		return options;
	}
		
		private int sizeOfOptions(PossibilityKeySet pks) {
			Set<String> keys = pks.getKeyValuesPairs().keySet();
			int i =0;
			for (String key: keys) {
				if(pks.getKeyValuesPairs().get(key).equals("true")) {
					i++;
				}
				else if (key.equals("num")) {
					continue;
				}
				else if (!pks.getKeyValuesPairs().get(key).equals("false")) {
					i+=2;
				}
			}
			for (PossibilityKeySet pk : pks.getBranchClassifiers()) {
				i+=2;
			}
			return i;
		}
	}
}
