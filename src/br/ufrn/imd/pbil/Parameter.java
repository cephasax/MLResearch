package br.ufrn.imd.pbil;

import java.io.Serializable;
import java.text.ParseException;
import java.util.HashMap;

/**
 * Representa um �nico par�metro de um modelo.<br><br>
 * O objetivo � a representa��o dos poss�veis par�metros de um modelo e a leitura do arquivo de configura��es.<br>
 * Ele � representado por um nome, uma chave, um valor padr�o, um intervalo de poss�veis valores.<br>
 * Al�m disso, os valores presentes no intervalo tamb�m podem possuir par�metros.<br>
 * Utiliza a classe {@link Interval} para representar os intervalos de valores.<br>
 * Utiliza a classe {@link Model} para representar os conjuntos de par�metros dos valores caso seja necess�rio.
 * 
 * @author antonino
 *
 */
public class Parameter implements Serializable  {

	private static final long serialVersionUID = 1L;

	/** Nome que descreve o par�metro. */
	public final String name;

	/** Chave do par�metro utilizado para gerar o conjunto de par�metros. Por exemplo, -K 10 onde -K � a chave. */
	public final String key;

	/** Valor padr�o para o par�metro. */
	public final String defaultValue;

	/**
	 * Intervalo de valores que esse par�metro pode assumir.
	 * @see Interval
	 */
	public final Interval interval;

	/**
	 * Representa os par�metros dos valores desse par�metro que possuem par�metros.<br>
	 * Por exemplo, o par�metro classificadores de um ensemble ser� composto por cada um dos poss�veis classificadores
	 * base e cada um deles tamb�m possuem diferentes par�metros.<br>
	 * Os sub-par�metros s�o representados por modelos.<br>
	 * Cada chave corresponde a um dos poss�veis valores do intervalo poss�vel para o par�metro.<br>
	 * Se um valor n�o possui par�metros ent�o ele n�o deve ter uma entrada em subParameters.<br>
	 * Somente par�metros do tipo String devem conter sub-par�metros.
	 * @see Model
	 */
	public final HashMap<String, Model> subParameters;

	/**
	 * Determina se o par�metro representa um classificador base.<br>
	 * No arquivo de configura��es os ensembles podem possuir um par�metro para determinar quais os poss�veis
	 * classificadores base.
	 */
	public final boolean isClassifier;

	/**
	 * Cria um par�metro a partir dos valores indicados.
	 * @param name A descri��o do par�metro.
	 * @param key A chave do par�metro como descrito no Weka.
	 * @param defaultValue O valor padr�o para o par�metro.
	 * @param interval O {@link Interval} de poss�veis valores para o par�metro.
	 * @param isClassifier Indica se o par�metro representa os classificadores base de um ensemble.
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
	 * Cria um par�metro a partir de uma descri��o textual.<br>
	 * Aceita o formato <code>&#64;parameter &lt;name&gt; &lt;key&gt; &lt;default-value&gt; &lt;type&gt; &lt;interval&gt;</code>.
	 * &lt;name&gt; � uma pequena descri��o do par�metro sem espa�os.<br>
	 * &lt;key&gt; � uma chave usada pelo par�metro como -K indicando o k no kNN.<br>
	 * &lt;default-value&gt; � o valor padr�o do padr�o para o par�metro como 1 para k.<br>
	 * &lt;type&gt; � o tipo de valores manipulados pelo par�metro que pode ser "boolean", "integer", "double", "classifier" ou "string".<br>
	 * &lt;interval&gt; � a descri��o do intervalo de valores manipulados pelo par�metro.<br>
	 * Por exemplo, <pre>&#64;parameter numberK -K 1 integer [1,10]</pre> representa um par�metro de name numberK, key -K, default-value 1, type integer e interval [1,10].<br>
	 * O tipo do default-value deve corresponder ao tipo suportado pelo par�metro, por exemplo, 2 � integer enquanto 2.0 � double.<br>
	 * Espa�os em branco s�o ignorados.
	 * @see Interval para uma descri��o textual dos intervalos.  
	 * @param text Uma {@link String} representando o par�metro.
	 * @return Um {@link Parameter} correspondente ao texto no formato especificado.
	 * @throws ParseException Caso o texto n�o corresponda ao formato especificado.
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
