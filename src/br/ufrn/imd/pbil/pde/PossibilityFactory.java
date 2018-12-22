package br.ufrn.imd.pbil.pde;

import java.util.Map;

import br.ufrn.imd.pbil.domain.ClassifierBuilder;
import br.ufrn.imd.pbil.domain.ClassifierFactory;
import br.ufrn.imd.pbil.domain.ParameterPrototype;

public class PossibilityFactory {
	
	public Possibility buildPossibilities(ClassifierFactory classifierFactory, String rootName) {
		Possibility poss = new Possibility(rootName);
		
		//FOR EACH BUILDER
		for(Map.Entry<String, ClassifierBuilder> builder: classifierFactory.getBuilders().entrySet()) {
			
			//FOR EACH PARAMETER_PROTOTYPE
			for(Map.Entry<String, ParameterPrototype> parameterPrototype: builder.getValue().getPrototype().getParameters().entrySet()) {
				Possibility p1 = new Possibility(parameterPrototype.getValue().getName());
				poss.addPossibility(p1);
				
				for(Object possibility: parameterPrototype.getValue().getPossibilities()) {
					String s = String.valueOf(possibility);
					Possibility p2 = new Possibility(s);
					p1.addPossibility(p2);
				}
			}
		}
		return poss;
	}

	
}
