package br.ufrn.imd.pbil;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/***
 * Representa intervalo de valores. Suporta os tipos inteiro, real, string e booleano.<br><br>
 * O objetivo � a representa��o dos poss�veis valores dos par�metros e a leitura do arquivo de configura��es.<br>
 * Utiliza um parser que aceita os seguintes formatos:
 * 
 * <pre>
 * Indica um intervalo de valores em forma de conjunto de poss�veis valores.
 * {v1, v2, ..., vn}
 * Onde v1, v2, ..., vn s�o n valores pertencentes a um mesmo tipo dentre dos tipos suportados.
 * Por exemplo: {1} indica um intervalo em que os valores s� podem assumir o valor 1.
 * </pre>
 * 
 * <pre>
 * Indica um intervalo de valores em forma de menor e maior valor.
 * [v1, v2]
 * Onde v1 � menor valor que um valor nesse intervalo pode assumir e v2 � o maior valor.
 * Por exemplo: [0,3] indica um intervalo onde os valores podem assumir o valor 0, 1, 2 ou 3.
 * Intervalos reais nesse formato ter�o um m�ximo de duas casas decimais.
 * Esse formato n�o aceita o tipo string.
 * </pre>
 * 
 * <pre>
 * Indica um intervalo de valores reais em forma de menor e maior valor e tamb�m determinar a quantidade m�xima de casas decimais.
 * [v1, v2, v3]
 * Onde v1 � menor valor que um valor nesse intervalo pode assumir, v2 � o maior valor e v3 � a quantidade de casas decimais.
 * Esse formato n�o aceita o tipo string.
 * </pre>
 * 
 * Espa�os em branco s�o ignorados. Strings n�o podem iniciar ou terminar com espa�o em branco, os mesmos s�o ignorados.
 * 
 * @author antonino
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class Interval implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Type dos valores no intervalo. */
	public final Type type;

	/** Indica se o intervalo utiliza um conjunto ou � delimitado por valores de m�ximo e m�nimo */
	public final boolean isSet;

	/** Lista de poss�veis valores do intervalo. � null caso o intervalo n�o seja declarado por um conjunto. */
	public final List<Comparable> values;

	/** Menor valor poss�vel no intervalo. No caso de declara��es por conjunto ele ser� o menor valor do conjunto. */
	public final Comparable minValue;

	/** Maior valor poss�vel no intervalo. No caso de declara��es por conjunto ele ser� o maior valor do conjunto. */
	public final Comparable maxValue;

	/**
	 * Quantidade de casas decimais que os intervalos reais podem assumir.<br>
	 * Caso o intervalo seja declarado por um conjunto ou o tipo n�o seja real ent�o o valor corresponde a null.
	 */
	public final Integer decimalPlaces;

	/**
	 * Cria um intervalo de valores a partir de um tipo e conjunto de valores.
	 * @param type O tipo dos valores presentes nesse intervalo.
	 * @param values Conjunto de valores que uma vari�vel nesse intervalo pode assumir. N�o pode ser vazio.
	 */
	public Interval(Type type, List<Comparable> values) {
		assert !values.isEmpty();

		isSet = true;
		this.type = type;
		this.values = values;
		decimalPlaces = null; // only double type has decimal places

		Comparable min = values.get(0);
		Comparable max = values.get(0);
		for (int i = 1; i < values.size(); i++) {
			Comparable value = values.get(i);
			if (value.compareTo(min) < 0) {
				min = value;
			}
			if (value.compareTo(max) > 0) {
				max = value;
			}
		}
		minValue = min;
		maxValue = max;
	}

	/**
	 * Cria um intervalo de valores a partir de valores de m�ximo e m�nimo.<br>
	 * Tamb�m aceita a quantidade de casas decimais que � obrigat�ria para o tipo real. N�o aceita o tipo String.
	 * @param type O tipo dos valores no intervalo.
	 * @param minValue O valor m�nimo no intervalo.
	 * @param maxValue O valor m�ximo no intervalo.
	 * @param decimalPlaces A quantidade de casas decimais. � obrigat�rio para o tipo real e deve ser null para os
	 *        demais tipos.
	 * @exception AssertionError Se decimalPlaces n�o for especificado para o tipo real ou se ele for especificado e o
	 *            tipo n�o for real.
	 *            Use -ea como par�metro da m�quina virtual ou n�o ser�o efetuadas as verifica��es.
	 * @exception AssertionError Se decimalPlaces &lt; 0.
	 *            Use -ea como par�metro da m�quina virtual ou n�o ser�o efetuadas as verifica��es.
	 * @exception AssertionError Se minValue &gt; maxValue.
	 *            Use -ea como par�metro da m�quina virtual ou n�o ser�o efetuadas as verifica��es.
	 * @exception AssertionError Se o tipo for String.
	 */
	public Interval(Type type, Comparable minValue, Comparable maxValue, Integer decimalPlaces) {
		assert type != Type.String;
		assert (type == Type.Double && decimalPlaces != null) || (type != Type.Double && decimalPlaces == null);
		assert (decimalPlaces == null) || (decimalPlaces >= 0);
		assert minValue.compareTo(maxValue) <= 0;

		isSet = false;
		this.type = type;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.decimalPlaces = decimalPlaces;
		values = null;
	}

	/**
	 * Cria um intervalo de valores a partir de valores de m�ximo e m�nimo.<br>
	 * Tamb�m aceita a quantidade de casas decimais que � obrigat�ria para o tipo real.<br>
	 * N�o aceita o tipo String.
	 * @param type O tipo dos valores no intervalo.
	 * @param minValue O valor m�nimo no intervalo.
	 * @param maxValue O valor m�ximo no intervalo.
	 * @exception AssertionError Se minValue &gt; maxValue.
	 *            Use -ea como par�metro da m�quina virtual ou n�o ser�o efetuadas as verifica��es.
	 * @exception AssertionError Se o tipo for String.
	 *            Use -ea como par�metro da m�quina virtual ou n�o ser�o efetuadas as verifica��es.
	 */
	public Interval(Type type, Comparable minValue, Comparable maxValue) {
		assert type != Type.String;
		assert minValue.compareTo(maxValue) <= 0;

		isSet = false;
		this.type = type;
		this.minValue = minValue;
		this.maxValue = maxValue;
		decimalPlaces = type == Type.Double ? 2 : null;
		values = null;
	}

	/**
	 * Cria um {@link Interval} a partir de uma {@link String}.<br><br>
	 * A {@link String} utilizada para criar o {@link Interval} que pode estar em tr�s formatos.<br>
	 * O primeiro formato <code>{v1, v2, ..., vn}</code> cria um {@link Interval} a partir de um conjunto de valores v1,
	 * v2, ..., vn.
	 * Esse conjunto n�o pode ser vazio.<br>
	 * O segundo formato <code>[v1, v2]</code> cria um {@link Interval} a partir de valores de m�nimo e m�ximo (ambos os
	 * valores pertencem ao intervalo).
	 * O valor m�nimo n�o pode ser maior que o m�ximo e n�o podem ser do tipo String.
	 * Para o tipo real ser� considerado duas casas decimais.<br>
	 * O terceiro formato <code>[v1, v2, v3]</code> � utilizado para criar intervalos do tipo real de modo similar ao
	 * segundo formato com a adi��o do valor v3 que � um valor natural que indica a quantidade casas decimais.
	 * @param type O tipo do intervalo a ser criado.
	 * @param text A {@link String} a contendo o texto.
	 * @return Um {@link Interval} corresponde ao texto indicado.
	 * @throws ParseException Caso o texto esteja em algum formato diferente do formato especificado.
	 */
	public static Interval parse(Type type, String text) throws ParseException {
		text = text.replace(", ", ",");
		text = text.trim();
		char start = text.length() == 0 ? '\0' : text.charAt(0);
		char end = text.length() == 0 ? '\0' : text.charAt(text.length() - 1);
		if (start != '{' && start != '[') {
			throw new ParseException("Inexpected char " + start + " at begining of interval declararion.", 0);
		} else if (end != '}' && end != ']') {
			throw new ParseException("Inexpected char " + end + " at ending of interval declararion.", 0);
		} else if ((start == '{' && end != '}') || (start == '[' && end != ']')) {
			throw new ParseException("Begining interval declaration " + start + " is different of end declararion " + end + ".", 0);
		}

		text = text.substring(1, text.length() - 1);
		String[] split = text.split(",");
		if (start == '{') {
			List<Comparable> list = new ArrayList<Comparable>(split.length);
			for (String value : split) {
				list.add(type.convert(value.trim()));
			}
			if (list.isEmpty()) {
				throw new ParseException("Expected at least one value in interval list", 1);
			}
			return new Interval(type, list);
		} else {
			if (!(type == Type.Double || type == Type.Integer || type == Type.Boolean)) {
				throw new ParseException("Can not specify a interval for categorical type " + type + ".", 0);
			}

			if (split.length == 0) {
				throw new ParseException("Expected two values in interval " + text + ".", 1);
			} else if (split.length == 1) {
				throw new ParseException("Missing the second value in interval " + text + ".", split[0].length() + 2);
			} else if ((type == Type.Integer || type == Type.Boolean) && split.length > 2) {
				throw new ParseException("Expected only two value in interval " + text + ".", split[0].length() + split[1].length() + 3);
			} else if (type == Type.Double && split.length > 3) {
				throw new ParseException("Expected only two or three value in interval " + text + ".", split[0].length() + split[1].length() + split[2].length() + 3);
			}

			Comparable minValue = type.convert(split[0]);
			Comparable maxValue = type.convert(split[1]);

			if (minValue.compareTo(maxValue) > 0) {
				throw new ParseException("Invalid interval [" + minValue + "," + maxValue + "]. The first value must be minor than second: " + minValue + " < " + maxValue + ". " + text + ".", 1);
			}

			Integer decimalPlaces = null;
			if (type == Type.Double) {
				if (split.length == 2) {
					decimalPlaces = 2;
				} else {
					try {
						// has 3 positions in split
						decimalPlaces = Integer.parseInt(split[2]);
					} catch (NumberFormatException e) {
						throw new ParseException("Invalid number of decimal places " + split[2] + ". Expected a integer value.", split[0].length() + split[1].length() + 2);
					}
				}
			}

			return new Interval(type, minValue, maxValue, decimalPlaces);
		}
	}

	/**
	 * Determina a quantidade de valores no intervalo.<br>
	 * Por exemplo, para o intervalo inteiro [0,10] temos 11 poss�veis valores: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 e 10.<br>
	 * Para intervalos do tipo real s�o considerados todos os poss�veis valores para a quantidade de casas decimais.
	 * @return A quantidade de valores no intervalo.
	 */
	public int size() {
		int size = -1;
		if (isSet) {
			size = values.size();
		} else if (type == Type.Boolean) {
			size = minValue.equals(maxValue) ? 1 : 2;
		} else if (type == Type.Integer) {
			int min = (Integer) minValue;
			int max = (Integer) maxValue;
			// max value inclusive
			size = max - min + 1;
		} else if (type == Type.Double) {
			double pow = Math.pow(10, decimalPlaces);
			int max = (int) Math.round(((Double) maxValue) * pow);
			int min = (int) Math.round(((Double) minValue) * pow);
			// max value inclusive
			size = max - min + 1;
		} else {
			assert false; // instantiation error
		}
		return size;
	}

	/**
	 * Determina qual o valor correspondente assumindo que todos os valores poss�veis est�o dispostos em uma lista
	 * ordenada do menor para o maior valor.<br>
	 * No caso de intervalos indicados por conjuntos a ordem � a mesma que a especificada.
	 * @param index A posi��o na lista de valores do intervalo.
	 * @return O valor do tipo correspondente do intervalo para o index indicado.
	 * @exception AssertionError Caso o index n�o corresponda a uma posi��o na lista de valores que inicia em zero e vai
	 *            at� a quantidade de valores no intervalo menos 1.
	 */
	public Object getValue(int index) {
		assert index >= 0;
		if (isSet) {
			assert index < values.size();
			return values.get(index);
		} else if (type == Type.Boolean) {
			assert index <= 1;
			if (minValue.compareTo(maxValue) == 0) {
				return minValue;
			} else {
				return index == 0 ? false : true;
			}
		} else if (type == Type.Integer) {
			int value = index + ((Integer) minValue);
			assert value <= ((Integer) maxValue) : value + " > " + maxValue + " " + this;
			return value;
		} else if (type == Type.Double) {
			double p = Math.pow(10, decimalPlaces);
			double value = (int) (((Double) minValue) * p + index);
			value = value / p;
			assert value <= ((Double) maxValue) : value + " > " + maxValue + " " + this;
			return value;
		}
		assert false; // instantiation error
		return null;
	}

	/**
	 * Determina o index no intervalo de valores para um correspondente valor.<br>
	 * O valor � convertido para o tipo do intervalo e comparado ou com os valores de m�nimo e m�ximo ou com o conjunto de valores fornecido.<br>
	 * As casa decimais al�m da quantidade de casas decimais especificadas para o tipo real s�o ignoradas.
	 * @param value O valor a ser calculado o index no intervalo.
	 * @return Um valor entre 0 e a quantidade de valores no intervalo menos 1 corresponde ao index do valor indicado.
	 * @throws ParseException Caso o valor n�o possa ser convertido para o tipo de valores do intervalo.
	 * @exception AssertionError Se o valor n�o pertencer ao intervalo de valores.
	 */
	public int getIndex(String value) throws ParseException {
		int index = -1;
		if (isSet) {
			Object converted = convert(value);
			assert values.contains(converted) : "Do not contains " +converted + " in " + values;
			index = values.indexOf(converted);
		} else {
			switch (type) {
			case Boolean:
				if (minValue.compareTo(maxValue) == 0) {
					index = 0;
					;
				} else {
					index = "true".equals(value) ? 1 : 0;
				}
				break;
			case Integer:
				index = ((Integer) convert(value)) - ((Integer) minValue);
				break;
			case Double:
				index = (int) ((((Double) convert(value)) - ((Double) minValue)) * Math.pow(10, decimalPlaces));
				break;
			default:
			}
		}
		assert index >= 0 && index < size() : "Invalid index " + index + " " + this;
		return index;
	}

	/**
	 * Verifica se um valor pertence ao intervalo de valores.
	 * @param value O valor a ser verificado.
	 * @return true caso o valor pertence ao intervalo ou false em caso contr�rio.
	 */
	public boolean contains(Object value) {
		if (isSet) {
			return values.contains(value);
		} else {
			return minValue.compareTo(value) <= 0 && maxValue.compareTo(value) >= 0;
		}
	}

	/**
	 * Converte uma {@link String} para o tipo de valores do intervalo resultando em um valor do tipo {@link Boolean},
	 * {@link Integer}, {@link Double} ou {@link String}.
	 * @param value O valor a ser convertido.
	 * @return Um valor do tipo {@link Boolean}, {@link Integer}, {@link Double} ou {@link String} correspondente �
	 *         {@link String} fornecida.
	 * @throws ParseException Caso n�o seja poss�vel converter o valor para o tipo de valores do intervalo.
	 */
	public Comparable convert(String value) throws ParseException {
		return type.convert(value);
	}

	/**
	 * Representa os tipos de valores do intervalo. Cada valor na enumera��o corresponde a uma das classes
	 * {@link Boolean}, {@link Integer}, {@link Double} ou {@link String}.
	 * @author antonino
	 */
	public enum Type {
		String(String.class), Integer(Integer.class), Double(Double.class), Boolean(Boolean.class);

		public final Class<?> currentClass;

		Type(Class<?> currentClass) {
			this.currentClass = currentClass;
		}

		public Comparable convert(String value) throws ParseException {
			value = value.trim();
			switch (this) {
			case Boolean:
				if ("true".equals(value)) {
					return true;
				} else if ("false".equals(value)) {
					return false;
				}
				throw new ParseException("Can not convert " + value + " to boolean.", 0);
			case Double:
				try {
					return new Double(value);
				} catch (NumberFormatException e) {
					throw new ParseException("Can not convert " + value + " to double.", 0);
				}
			case Integer:
				try {
					return new Integer(value);
				} catch (NumberFormatException e) {
					throw new ParseException("Can not convert " + value + " to integer.", 0);
				}
			case String:
				return value;
			}
			return null;
		}
	};

	@Override
	public String toString() {
		if (isSet) {
			return "(" + type + ", " + values.toString().replace('[', '{').replace(']', '}') + ")";
		} else if (type == Type.Boolean) {
			return "(" + type + ")";
		} else if (type == Type.Integer) {
			return "(" + type + ", [" + minValue + ", " + maxValue + "])";
		} else if (type == Type.Double) {
			return "(" + type + ", [" + minValue + ", " + maxValue + ", " + decimalPlaces + "])";
		}
		return null;
	}
}
