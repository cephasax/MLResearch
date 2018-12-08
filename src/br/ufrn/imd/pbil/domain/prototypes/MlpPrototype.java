package br.ufrn.imd.pbil.domain.prototypes;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class MlpPrototype extends ClassifierPrototype{
	
	public MlpPrototype() throws InvalidParameterTypeException {
		
		ParameterPrototype l = new ParameterPrototype("L", ParameterType.DOUBLE);
		List<Double> lValaues = new ArrayList<Double>();
		for(double i = 0.1; i <= 1; i = i + 0.1) {
			lValaues.add(i);
		}
		l.setPossibilities(lValaues);
		parameters.put("L", l);
		
		ParameterPrototype m = new ParameterPrototype("M", ParameterType.DOUBLE);
		List<Double> mValaues = new ArrayList<Double>();
		for(double i = 0.1; i <= 1; i = i + 0.1) {
			mValaues.add(i);
		}
		m.setPossibilities(mValaues);
		parameters.put("M", m);
		
		ParameterPrototype b = new ParameterPrototype("B", ParameterType.BOOLEAN);
		List<Boolean> bValaues = new ArrayList<Boolean>();
		bValaues.add(true);
		bValaues.add(false);
		b.setPossibilities(bValaues);
		parameters.put("B", b);
				
		ParameterPrototype h = new ParameterPrototype("H", ParameterType.STRING);
		List<String> hValaues = new ArrayList<String>();
		hValaues.add("a");
		hValaues.add("i");
		hValaues.add("o");
		hValaues.add("t");
		h.setPossibilities(hValaues);
		parameters.put("H", h);
		
		ParameterPrototype c = new ParameterPrototype("C", ParameterType.BOOLEAN);
		List<Boolean> cValaues = new ArrayList<Boolean>();
		cValaues.add(true);
		cValaues.add(false);
		c.setPossibilities(cValaues);
		parameters.put("C", c);
		
		ParameterPrototype r = new ParameterPrototype("R", ParameterType.BOOLEAN);
		List<Boolean> rValaues = new ArrayList<Boolean>();
		rValaues.add(true);
		rValaues.add(false);
		r.setPossibilities(rValaues);
		parameters.put("R", r);
		
		ParameterPrototype d = new ParameterPrototype("D", ParameterType.BOOLEAN);
		List<Boolean> dValaues = new ArrayList<Boolean>();
		dValaues.add(true);
		dValaues.add(false);
		r.setPossibilities(dValaues);
		parameters.put("D", d);
		
		ParameterPrototype s = new ParameterPrototype("S", ParameterType.STRING);
		List<Integer> sValaues = new ArrayList<Integer>();
		for(int i = 1; i <= 255; i++) {
			sValaues.add(i);
		}
		s.setPossibilities(sValaues);
		parameters.put("S", s);
	}
}
