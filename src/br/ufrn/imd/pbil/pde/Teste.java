package br.ufrn.imd.pbil.pde;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import br.ufrn.imd.pbil.fileManipulation.FileOutputWriter;
import weka.classifiers.rules.DecisionTable;

public class Teste {

	public static void main(String[] args) throws IOException {
		DecisionTable dt = new DecisionTable();

		System.out.println(dt.listOptions().);
		
	}
	
}
