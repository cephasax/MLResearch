package br.ufrn.imd.pbil.pde;

import java.util.Random;

import br.ufrn.imd.pbil.domain.Factory;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class TesttingProb {

	public static float rate = 1;
	
	public static void main(String[] args) throws InvalidParameterTypeException {
				
		Factory f = new Factory();
		
		for(int i=0; i<5; i++){
			System.out.println(f.getFirstLevel().possibilityAsString());
			System.out.println();
			System.out.println(sort(f.getFirstLevel()));
		}
		
	}
	
	
	
}
