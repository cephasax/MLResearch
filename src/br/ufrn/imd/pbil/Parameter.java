package br.ufrn.imd.pbil;

import java.io.Serializable;
import java.text.ParseException;
import java.util.HashMap;

/**
 * Representa um único parâmetro de um modelo.<br><br>
 * O objetivo é a representação dos possíveis parâmetros de um modelo e a leitura do arquivo de configurações.<br>
 * Ele é representado por um nome, uma chave, um valor padrão, um intervalo de possíveis valores.<br>
 * Além disso, os valores presentes no intervalo também podem possuir parâmetros.<br>
 * Utiliza a classe {@link Interval} para representar os intervalos de valores.<br>
 * Utiliza a classe {@link Model} para representar os conjuntos de parâmetros dos valores caso seja necessário.
 * 
 * @author antonino
 *
 */
public class Parameter implements Serializable  {

	private static final long serialVersionUID = 1L;

	/** Nome que descreve o parâmetro. */
	public final String name;

	/** Chave do parâmetro utilizado para gerar o conjunto de parâmetros. Por exemplo, -K 10 onde -K é a chave. */
	public final String key;

	/** Valor padrão para o parâmetro. */
	public final String defaultValue;

	/**
	 * Intervalo de valores que esse parâmetro pode assumir.
	 * @see Interval
	 */
	public final Interval interval;

	/**
	 * Representa os parâmetros dos valores desse parâmetro que possuem parâmetros.<br>
	 * Por exemplo, o parâmetro classificadores de um ensemble será composto por cada um dos possíveis classificadores
	 * base e cada um deles também possuem diferentes parâmetros.<br>
	 * Os sub-parâmetros são representados por modelos.<br>
	 * Cada chave corresponde a um dos possíveis valores do intervalo possível para o parâmetro.<br>
	 * Se um valor não possui parâmetros então ele não deve ter uma entrada em subParameters.<br>
	 * Somente parâmetros do tipo String devem conter sub-parâmetros.
	 * @see Model
	 */
	public final HashMap<String, Model> subParameters;

	/**
	 * Determina se o parâmetro representa um classificador base.<br>
	 * No arquivo de configurações os ensembles podem possuir um parâmetro para determinar quais os possíveis
	 * classificadores base.
	 */
	public final boolean isClassifier;

	/**
	 * Cria um parâmetro a partir dos valores indicados.
	 * @param name A descrição do parâmetro.
	 * @param key A chave do parâmetro como descrito no Weka.
	 * @param defaultValue O valor padrão para o parâmetro.
	 * @param interval O {@link Interval} de possíveis valores para o parâmetro.
	 * @param isClassifier Indica se o parâmetro representa os classificadores base de um ensemble.
	 */
	public Parameter(String name, String key, String defaultValue, Interval interval, boolean isClassifier) {
		this.name = name;
		this.key = key;
		this.defaultValue = defaultValue;
		this.interval = interval;
		this.isClassifier = isClassifier;
		subParameters = new HashMap<>();
	}

	/**
	 * Cria um parâmetro a partir de uma descrição textual.<br>
	 * Aceita o formato <code>&#64;parameter &lt;name&gt; &lt;key&gt; &lt;default-value&gt; &lt;type&gt; &lt;interval&gt;</code>.
	 * &lt;name&gt; é uma pequena descrição do parâmetro sem espaços.<br>
	 * &lt;key&gt; é uma chave usada pelo parâmetro como -K indicando o k no kNN.<br>
	 * &lt;default-value&gt; é o valor padrão do padrão para o parâmetro como 1 para k.<br>
	 * &lt;type&gt; é o tipo de valores manipulados pelo parâmetro que pode ser "boolean", "integer", "double", "classifier" ou "string".<br>
	 * &lt;interval&gt; é a descrição do intervalo de valores manipulados pelo parâmetro.<br>
	 * Por exemplo, <pre>&#64;parameter numberK -K 1 integer [1,10]</pre> representa um parâmetro de name numberK, key -K, default-value 1, type integer e interval [1,10].<br>
	 * O tipo do default-value deve corresponder ao tipo suportado pelo parâmetro, por exemplo, 2 é integer enquanto 2.0 é double.<br>
	 * Espaços em branco são ignorados.
	 * @see Interval para uma descrição textual dos intervalos.  
	 * @param text Uma {@link String} representando o parÂmetro.
	 * @return Um {@link Parameter} correspondente ao texto no formato especificado.
	 * @throws ParseException Caso o texto não corresponda ao formato especificado.
	 */
	public static Parameter parse(String text) throws ParseException {
		String[] split = text.trim().replaceAll("( )+", " ").split(" ", 6);

		if (split.length < 5) {
			throw new ParseException("Invalid @parameter declaration (missing arguments): " + text + ". Use only space as separator.", 0);
		} else if (split.length > 6) {
			throw new ParseException("Invalid @parameter declaration (many arguments): " + text + ". Use only space as separator.", 0);
		}

		if (!split[0].equals("@parameter")) {
			throw new ParseException("Expected @parameter token in: " + text, 0);
		}

		final String name = split[1];
		final String key = split[2];
		final String defaultValue = split[3];
		final Interval.Type type = convertType(split[4]);
		final boolean isClassifier = "classifier".equals(split[4]);

		if (type == null) {
			throw new ParseException("Invalid type " + split[4] + ".", split[0].length() + split[1].length() + split[2].length() + split[3].length() + 4);
		}

		final Interval interval;
		if (split.length == 5) { // only boolean has 5 arguments
			interval = new Interval(Interval.Type.Boolean, Boolean.FALSE, Boolean.TRUE);
		} else {
			try {
				interval = Interval.parse(type, split[5]);
			} catch (ParseException e) {
				throw new ParseException(e.getMessage(), split[0].length() + split[1].length() + split[2].length() + split[3].length() + split[4].length() + 5 + e.getErrorOffset());
			}
		}

		if (!interval.contains(interval.convert(defaultValue))) {
			throw new ParseException("Default value " + defaultValue + " is not in the interval " + interval + " of values for the parameter.", 0);
		}

		return new Parameter(name, key, defaultValue, interval, isClassifier);
	}

	private static Interval.Type convertType(String type) {
		switch (type) {
		case "boolean":
			return Interval.Type.Boolean;
		case "integer":
			return Interval.Type.Integer;
		case "double":
			return Interval.Type.Double;
		case "classifier":
		case "string":
			return Interval.Type.String;
		default:
			return null;
		}
	}

	@Override
	public String toString() {
		return "@parameter " + name + " " + key + " " + defaultValue + " " + interval;
	}
}
