package br.ufrn.imd.pbil.domain.bc.wekabuilders;

import br.ufrn.imd.pbil.pde.PossibilityKeySet;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.Kernel;
import weka.classifiers.functions.supportVector.NormalizedPolyKernel;
import weka.classifiers.functions.supportVector.PolyKernel;
import weka.classifiers.functions.supportVector.Puk;
import weka.classifiers.functions.supportVector.RBFKernel;

public class SmoWekaBuilder {

	public static SMO buildForWeka(PossibilityKeySet pks) {
		SMO smo = new SMO();
		
		smo.setKernel(buildKernel(
				pks.getKeyValuesPairs().get("SEL")));
		
		return smo;
	}

	private static Kernel buildKernel(String kernel) {
		switch (kernel) {
			
			case "weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 2.0 -C 250007":
				return new NormalizedPolyKernel();
				
			case "weka.classifiers.functions.supportVector.PolyKernel -E 1.0 -C 250007":
				return new PolyKernel();
			
			case "weka.classifiers.functions.supportVector.Puk -O 1.0 -S 1.0 -C 250007":
				return new Puk();
			
			case "weka.classifiers.functions.supportVector.RBFKernel -C 250007 -G 0.01":
				return new RBFKernel();
			
			default:
				return new PolyKernel();
		}
	}
	
}
