package br.ufrn.imd.pbil.pde;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import br.ufrn.imd.pbil.fileManipulation.FileOutputWriter;

public class Teste {

	public static void main(String[] args) throws IOException {
		ArrayList<String> nomes = new ArrayList<String>();
		nomes.add("jose");
		nomes.add("cephas");
		
		FileOutputWriter fow;
		
		fow = new FileOutputWriter("testando");

		fow.addContentLines(nomes);
		fow.writeInFile();
		
		nomes.clear();
		nomes.add("marquito");
		fow.addContentLines(nomes);
		
		fow.writeInFile();
		fow.saveAndClose();

	}
	
}
