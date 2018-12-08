package br.ufrn.imd.pbil.domain.builders;

import java.util.ArrayList;
import java.util.List;
<<<<<<< HEAD

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.DecisionTable;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.prototypes.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.prototypes.MlpPrototype;
=======
import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.DecisionTable;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.prototypes.DecisionTablePrototype;
import br.ufrn.imd.pbil.enums.ClassifierType;
>>>>>>> c267b4282e5a0cd7a46e6d4bf2bb6fab62c2f955
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class DecisionTableBuilder extends ClassifierBuilder {

	public DecisionTableBuilder(ClassifierPrototype classifierPrototype) {
		super(classifierPrototype);
	}

	public Classifier defautBuild() {

		classifier = new DecisionTable();
		List<Parameter> parameters = new ArrayList<Parameter>();
		
		Parameter e = new Parameter("E", ParameterType.STRING);
		e.setValue("acc");
		parameters.add(e);

		Parameter i = new Parameter("I", ParameterType.BOOLEAN);
		i.setValue("False");
		parameters.add(i);

		Parameter s = new Parameter("S", ParameterType.STRING);
		s.setValue("GreedStepWise");
		parameters.add(s);

		Parameter x = new Parameter("X", ParameterType.INT);
		x.setValue("1");
		parameters.add(x);

		classifier.setParameters(parameters);
		return classifier;
	}

	public Classifier randomBuild() {

		classifier = new DecisionTable();
<<<<<<< HEAD
		List<Parameter> parameters = new ArrayList<Parameter>();
=======
		

		int randomizedInt = 0;
>>>>>>> c267b4282e5a0cd7a46e6d4bf2bb6fab62c2f955
		
		try {
			prototype = new MlpPrototype();

			Parameter e = new Parameter("E", ParameterType.STRING);
			e.setValue(randomValueForParameter(e));
			parameters.add(e);
<<<<<<< HEAD

			Parameter i = new Parameter("I", ParameterType.BOOLEAN);
			i.setValue(randomValueForParameter(i));
			parameters.add(i);

			Parameter s = new Parameter("S", ParameterType.STRING);
			s.setValue(randomValueForParameter(s));
			parameters.add(s);

			Parameter x = new Parameter("X", ParameterType.STRING);
			x.setValue(randomValueForParameter(x));
			parameters.add(x);
		} catch (InvalidParameterTypeException e1) {
			e1.printStackTrace();
		}

		classifier.setParameters(parameters);
=======
			
			Parameter i = new Parameter("I",ParameterType.BOOLEAN);
			
			i.setValue(randomValueForParameter(i));
			parameters.add(i);
			
			Parameter s = new Parameter("S",ParameterType.STRING);
			s.setType(ParameterType.STRING);
			s.setName("S");
			
			
			s.setValue(randomValueForParameter(s));
			parameters.add(s);
			
			Parameter x = new Parameter("X",ParameterType.STRING);			
			x.setValue(randomValueForParameter(x));
			parameters.add(x);
			
			classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
			classifier.setName("Decision Table");
			classifier.setParameters(parameters);
			
		} catch (InvalidParameterTypeException e1) {
			e1.printStackTrace();
		}
>>>>>>> c267b4282e5a0cd7a46e6d4bf2bb6fab62c2f955
		return classifier;
	}

	public Classifier weightedDrawBuild() {
		// TODO Auto-generated method stub
		return null;
	}

}
