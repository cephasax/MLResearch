package models;


import java.util.List;

public class Classifier {
	public String nome;
	public List<Parameter> parameters;
	@Override
	public String toString() {
		String parametros = "";
		for (Parameter p:parameters) {
			parametros = p.name +p.values +"\n";
		}
		return nome + parametros;
	}
}
