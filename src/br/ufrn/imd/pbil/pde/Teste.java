package br.ufrn.imd.pbil.pde;

import java.util.ArrayList;

public class Teste {

	public static void main(String[] args) {
		ArrayList<String> nomes = new ArrayList<String>();
		nomes.add("jose");
		nomes.add("cephas");
		int i = 1;
		for(String s: nomes) {
			System.out.println(s);
			nomes.add(String.valueOf(i));
		}
	}
	
}
