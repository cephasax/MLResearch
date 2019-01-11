package br.ufrn.imd.pbil;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Representa uma condição sobre os valores dos parâmetros de um modelo.<br><br>
 * Permite verificar se a condição é válida ou não.<br>
 * É composta por uma sequência de expressões relacionais agregadas pelo operador booleano and.
 * @author antonino
 *
 */
public class Conditional implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	/** Sequência de expressões. */
	protected List<Expression> expressions;

	/** Cria uma condição sem expressões. Sua avalação é sempre verdadeira. */
	public Conditional() {
		expressions = new ArrayList<>();
	}

	/**
	 * Verifica se a condição é verificada no conjunto de valores dos parâmetros indicados.
	 * @param parans Associação das chaves dos parâmetros com os respectivos valores, por exemplo, ("-K",5).
	 * @return true se a condição é válida e false em caso contrário.
	 */
	@SuppressWarnings("rawtypes")
	public boolean isValid(HashMap<String, Comparable> parans) {
		for (Expression e : expressions) {
			assert parans.containsKey(e.parameter.key);
			if (!e.eval(parans.get(e.parameter.key))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Cria uma condição a partir de texto.<br><br>
	 * A condição deve estar no formato
	 * 
	 * <pre>
	 * &#64;conditional &lt;exp&gt; [and &lt;exp&gt;]*
	 * </pre>
	 * 
	 * onde &lt;exp&gt; corresponde ao formato
	 * 
	 * <pre>
	 * &lt;key&gt; &lt;operator&gt; &lt;value&gt;
	 * </pre>
	 * 
	 * .
	 * &lt;key&gt; deve corresponder a chave de um parâmetro e a mesma será usada na avaliação da condição.<br>
	 * &lt;operator&gt; deve ser um operador relacional dentre (mesma sintaxe e semântica que em Java): ==, !=,
	 * &lt;, &lt;=, &gt;, &gt;=.<br>
	 * &lt;value&gt; deve corresonder a um valor no intervalo de valores válidos para o parâmetro da chave indicada.<br>
	 * Por exemplo:
	 * 
	 * <pre>
	 * &#64;conditional -K &gt;= 30
	 * </pre>
	 * 
	 * corresponde a descrição textual de uma condição em que um parâmetro de chave "-K" não pode ser maior ou igual a
	 * 30. Nesse exemplo, os valores do parâmetro devem ser do tipo inteiro.
	 * @param text O texto de onde a condição será criada.
	 * @param parameters Associação de chaves com parâmetros. Eles devem ser os mesmo que serão utilizados na avaliação
	 *        da condição.
	 * @return Uma nova condição correspondente a descrição textual.
	 * @throws ParseException Caso o texto não esteja no formato especificado.
	 */
	public static Conditional parse(String text, HashMap<String, Parameter> parameters) throws ParseException {

		String[] split = text.trim().replaceAll("( )+", " ").split(" ");
		if (split.length == 0 || !split[0].equals("@conditional")) {
			throw new ParseException("Expected @conditional declaration in: " + text + ".", 0);
		}
		if (4 > split.length) {
			throw new ParseException("Invalid expression (missing arguments): " + text + ".", 0);
		}

		Conditional result = new Conditional();

		int offset = split[0].length() + 1;
		for (int i = 1; i < split.length; i += 4) {
			if (i + 3 > split.length) {
				StringBuilder msg = new StringBuilder();
				for (int j = i; j < split.length; j++) {
					msg.append(split[j]);
					msg.append(" ");
				}
				msg.deleteCharAt(msg.length() - 1);
				throw new ParseException("Invalid expression (missing arguments): " + msg + ".", offset);
			}

			String key = split[i];
			String operator = split[i + 1];
			String value = split[i + 2];

			if (!parameters.containsKey(key)) {
				throw new ParseException("Unknown parameter key " + key + ".", offset);
			}
			offset += key.length() + 1;

			if (!(operator.equals("==") || operator.equals("!=") || operator.equals(">") || operator.equals("<") || operator.equals(">=") || operator.equals("<="))) {
				throw new ParseException("Unknown operator " + operator + ". Valid operators are == != > < >= <=.", offset);
			}
			offset += operator.length() + 1;

			Parameter parameter = parameters.get(key);
			if (!parameter.interval.contains(parameter.interval.convert(value))) {
				throw new ParseException("Value " + value + " is not in the interval of values of parameter " + parameter.name + ".", offset);
			}
			offset += value.length() + 1;

			if (i + 3 < split.length && !split[i + 3].equals("and")) {
				throw new ParseException("Expected operator and in: " + split[i + 3], offset);
			}
			if (i + 3 < split.length && split[i + 3].equals("and") && i + 4 >= split.length) {
				throw new ParseException("Expected a operator key after \"and\" in: " + split[i + 3], offset);
			}
			offset += 5;

			result.expressions.add(new Expression(parameter, operator, parameter.interval.convert(value)));
		}

		return result;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("[");
		for (Expression e : expressions) {
			if (str.length() > 1) {
				str.append(" and ");
			}
			str.append(e);
		}
		return str.append("]").toString();
	}

	protected static class Expression implements Serializable {

		private static final long serialVersionUID = 1L;

		public final Parameter parameter;

		public final String operator;

		public final Object keyValue;

		public Expression(Parameter parameter, String operator, Object value) {
			this.parameter = parameter;
			this.operator = operator;
			this.keyValue = value;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public boolean eval(Comparable value) {
			switch (operator) {
			case "==":
				return value.equals(keyValue);
			case "!=":
				return !value.equals(keyValue);
			case "<":
				return value.compareTo(keyValue) < 0;
			case ">":
				return value.compareTo(keyValue) > 0;
			case "<=":
				return value.compareTo(keyValue) <= 0;
			case ">=":
				return value.compareTo(keyValue) >= 0;
			default:
				return false;
			}
		}

		@Override
		public String toString() {
			return parameter.key + " " + operator + " " + keyValue;
		}
	}
}
