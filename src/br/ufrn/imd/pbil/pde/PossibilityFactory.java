package br.ufrn.imd.pbil.pde;

import java.util.Map;

import br.ufrn.imd.pbil.domain.ClassifierBuilder;
import br.ufrn.imd.pbil.domain.ClassifierFactory;
import br.ufrn.imd.pbil.domain.ParameterPrototype;

public class PossibilityFactory {
	
	public Possibility buildPossibilitiesForBaseClassifiers(ClassifierFactory classifierFactory, String rootName) {
		Possibility poss = new Possibility(rootName);
		
		//FOR EACH BUILDER
		for(Map.Entry<String, ClassifierBuilder> builder: classifierFactory.getBuilders().entrySet()) {
			
			Possibility p1 = new Possibility(builder.getKey());
			poss.addPossibility(p1);
			
			//FOR EACH PARAMETER_PROTOTYPE
			for(Map.Entry<String, ParameterPrototype> parameterPrototype: builder.getValue().getPrototype().getParameters().entrySet()) {
				Possibility p2 = new Possibility(parameterPrototype.getValue().getName());
				p1.addPossibility(p2);
				
				for(Object possibility: parameterPrototype.getValue().getPossibilities()) {
					String s = String.valueOf(possibility);
					Possibility p3 = new Possibility(s);
					p2.addPossibility(p3);
				}
			}
		}
		return poss;
	}

	public Possibility buildPossibilitiesForCommittes(ClassifierFactory classifierFactory, String rootName) {
		Possibility poss = new Possibility(rootName);
		
		//FOR EACH BUILDER
		for(Map.Entry<String, ClassifierBuilder> builder: classifierFactory.getBuilders().entrySet()) {
			
			Possibility p1 = new Possibility(builder.getKey());
			poss.addPossibility(p1);
			
			//FOR EACH PARAMETER_PROTOTYPE
			for(Map.Entry<String, ParameterPrototype> parameterPrototype: builder.getValue().getPrototype().getParameters().entrySet()) {
				Possibility p2 = new Possibility(parameterPrototype.getValue().getName());
				p1.addPossibility(p2);
				
				for(Object possibility: parameterPrototype.getValue().getPossibilities()) {
					String s = String.valueOf(possibility);
					Possibility p3 = new Possibility(s);
					p2.addPossibility(p3);
				}
				
			}
		}
		return poss;
	}

	
}
