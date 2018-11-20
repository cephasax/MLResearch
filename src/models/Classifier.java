package models;


import java.util.List;

public class Classifier {
	private String nome;
	private List<Parameter> parameters;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Parameter> getParameters() {
		return parameters;
	}
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
	@Override
	public String toString() {
		String parametros = "";
		for (Parameter p:parameters) {
			parametros = p.getName() +p.getValues() +"\n";
		}
		return nome + parametros;
	}
}
