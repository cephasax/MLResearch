package br.ufrn.imd.pbil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.ufrn.imd.pbil.Interval.Type;
import weka.classifiers.meta.Bagging;
import weka.classifiers.meta.Vote;
import weka.classifiers.trees.RandomForest;
import weka.core.Utils;

/**
 * Representa um modelo que pode ser tanto um classificador base quanto um comitê.<br><br>
 * Modelos possuem um conjunto de parâmetros a serem otimizados.<br>
 * Possui um conjunto de condições que determinam se uma configuração de parâmetros é válida ou não.<br>
 * Para os comitês é possível inserir restrições sobre a quantidade de classificadores base e quanto ao tipo homogêneo
 * ou heterogêneo.
 * 
 * @author antonino
 *
 */
public class Model implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	/** Nome do modelo. Por exemplo: weka.classifiers.lazy.KStar. */
	public final String name;

	/**
	 * Associa chaves de parâmetros com sua representação nas configurações. Por exemplo: "-K" associado as
	 * configurações do k para o kNN.
	 */
	public LinkedHashMap<String, Parameter> parameters;

	/** Conjunto de condições que determinam que um conjunto de configurações são inválidas. */
	public List<Conditional> conditionals;

	// only ensembles must have classifiers
	/** Indica se o modelo corresponde a um comitê. */
	public final boolean isEnsemble;

	/**
	 * Determina os classificadores base que o comitê pode manipular.<br>
	 * Se o comitê não aceitar classificadores base então será null.
	 */
	public final Parameter classifierParameter;

	/**
	 * Determina a quantidade mínima e máxima de classificadores base no comitê.<br>
	 * Se o modelo não for um comitê ou se o comitê não aceitar classificadores base então será null.<br>
	 * O intervalo de valores deve ser inteiro.<br>
	 * É ignorado caso o comitê aceite somente um único classificador base.
	 */
	public final Parameter numClassifiers;

	/**
	 * Determina o tipo do comitê quanto a homogêneo ou heterogêneo.<br>
	 * O intervalo de valores de valores deve ser real e indica que o comitê terá sempre um base classificador com a
	 * proporção indicada em relação a quatidade de base classificadores ativos.<br>
	 * Por exemplo, para 0.5 temos que ao menos metade dos classificadores base ativos serão sempre de um mesmo tipo.<br>
	 * São tratados os classificadores ativos, já que a quantidade de classificadores base pode ser especificada por um
	 * intervalo como [2,10].<br>
	 * Se for null então esse critério não é verificado.<br>
	 * Se o modelo não for um comitê ou se o comitê não aceitar classificadores base então será null.
	 */
	public final Parameter percentHomogeneous;

	/**
	 * Cria um modelo de um classificador base.
	 * @param name O nome da classe do classificador base.
	 *        Por exemplo: weka.classifiers.lazy.KStar.
	 */
	public Model(String name) {
		this.name = name;
		this.isEnsemble = false;
		this.numClassifiers = null;
		this.percentHomogeneous = null;
		this.classifierParameter = null;
		parameters = new LinkedHashMap<>();
		conditionals = new ArrayList<>();
	}

	/**
	 * Cria um modelo de um comitê.
	 * @param name O nome do comitê como weka.classifiers.meta.Vote.
	 * @param numClassifiers Determina a quantidade máxima e mínima de classificadores base caso o comitê aceite uma
	 *        quantidade variável. O intervalo de valores deve ser inteiro. Pode ser null.
	 * @param percentHomogeneous Controla o tipo do comitê em relação homogêneo/heterogêneo. O intervalo de valores deve
	 *        ser real. Pode ser null.
	 * @param classifierParameter Determina os classificadores base que o comitê aceita.
	 */
	public Model(String name, Parameter numClassifiers, Parameter percentHomogeneous, Parameter classifierParameter) {
		this.name = name;
		this.isEnsemble = true;
		this.numClassifiers = numClassifiers;
		assert numClassifiers == null || (numClassifiers.interval.type == Interval.Type.Integer);
		this.percentHomogeneous = percentHomogeneous;
		assert percentHomogeneous == null || (percentHomogeneous.interval.type == Interval.Type.Double);
		this.classifierParameter = classifierParameter;
		parameters = new LinkedHashMap<>();
		conditionals = new ArrayList<>();
	}

	/**
	 * Verifica se um determinado conjunto de parâmetro é valido segundo este modelo.<br>
	 * O primeiro parâmetro deve ser o nome do modelo.
	 * É utilizado o padrão de padrão de parâmetros do WEKA.
	 * @param parans Conjunto de parâmetros a serem verificados.
	 * @return true se a configuração for válida e false em caso contrário.
	 */
	@SuppressWarnings("rawtypes")
	public boolean isValid(String parans) {
		try {
			String[] options = Utils.splitOptions(parans);
			LinkedHashMap<String, Comparable> map = new LinkedHashMap<>();

			if (options.length == 0 || !name.equals(options[0])) {
				return false;
			}

			for (Entry<String, Parameter> entry : parameters.entrySet()) {
				map.put(entry.getKey(), entry.getValue().interval.convert(entry.getValue().defaultValue));
			}

			for (int i = 1; i < options.length; i++) {
				String key = options[i];

				if ("-W".equals(key) && classifierParameter != null) {
					i++;
					if (!classifierParameter.interval.contains(options[i])) {
						return false;
					}

					Model model = classifierParameter.subParameters.get(options[i]);
					StringBuilder baseClassifierParans = new StringBuilder(options[i]);
					for (i = i + 2; i < options.length; i++) {
						baseClassifierParans.append(" " + options[i]);
					}
					if (!model.isValid(baseClassifierParans.toString())) {
						return false;
					}
					break;
				}

				if (!parameters.containsKey(key) && !(classifierParameter != null && "-B".equals(key))) {
					continue;
				}

				Parameter parameter = parameters.containsKey(key) ? parameters.get(key) : classifierParameter;
				assert parameter != null;
				if (parameter.interval.type == Type.Boolean) {
					map.put(key, true);
				} else {
					assert i + 1 < options.length : "Access " + (i + 1) + " with size " + options.length + " in " + Arrays.toString(options);
					String[] split = options[++i].split(" ", 2);
					map.put(key, parameter.interval.convert(split[0]));
					if (parameter.subParameters != null && split.length > 1 && parameter.subParameters.containsKey(split[0])) {
						if (!parameter.subParameters.get(split[0]).isValid(options[i])) {
							return false;
						}
					}
				}
			}

			for (Conditional conditional : conditionals) {
				if (conditional.isValid(map)) {
					return false;
				}
			}

			return true;
		} catch (Exception e) {
			Error error = new Error("Is Valid Error " + e.getMessage());
			error.initCause(e);
			throw error;
		}
	}

	private static Model parseModelFields(String modelName, boolean isEnsemble, BufferedReader reader, Map<String, Model> classifiers) throws ParseException, IOException {
		LinkedHashMap<String, Parameter> parameters = new LinkedHashMap<>();
		List<Conditional> conditionals = new ArrayList<>();
		Parameter classifierParameter = null;
		Parameter percentHomogeneous = null;
		Parameter numClassifiers = null;

		String line = reader.readLine();
		while (line != null) {
			line = line.trim();
			if (line.startsWith("@parameter ")) {
				Parameter p = Parameter.parse(line);
				if (p.isClassifier) {
					if (!isEnsemble) {
						throw new ParseException("Invalid type classifier for no ensemble model.", 0);
					}
					if (!("-W".equals(p.key) || "-B".equals(p.key))) {
						throw new ParseException("Unsupported classifier parameter for ensemble model. Accept only -B for multiple base classifiers or -W for only one base classifier.", 0);
					}

					classifierParameter = p;
					for (Object base : p.interval.values) {
						if (!classifiers.containsKey(base)) {
							String msg = reportParseError("Unknown classifier " + base + ".", line.indexOf((String) base), line);
							throw new ParseException(msg, 0);
						} else {
							p.subParameters.put((String) base, classifiers.get(base));
						}
					}
					if (numClassifiers == null){
						numClassifiers = new Parameter("maxClassifiers", "defaultNumClassifiers", "1", new Interval(Interval.Type.Integer, Arrays.asList(new Integer(1))), false);
					}
				} else {
					parameters.put(p.key, p);
				}
			} else if (line.startsWith("@sub-parameter ")) {
				String[] split = line.split(" ", 3);

				if (split.length != 3) {
					String msg = reportParseError("Invalid arguments for @sub-parameter declaration. Expected: @sub-parameter <parameter key> <parameter value>", "@sub-parameter ".length(), line);
					throw new ParseException(msg, 0);
				} else {

					String parentKey = split[1];

					Model subModel = parseModelFields(split[2], false, reader, classifiers);

					if (parameters.containsKey(parentKey)) {
						Parameter parameter = parameters.get(parentKey);
						parameter.subParameters.put(subModel.name, subModel);
					} else {
						String msg = reportParseError("Invalid sub-parameter key value " + subModel.name + ".", split[0].length() + split[1].length() + 2, line);
						throw new ParseException(msg, 0);
					}
				}
			} else if (line.startsWith("@conditional ")) {
				Conditional conditional = Conditional.parse(line, parameters);
				conditionals.add(conditional);
			} else if (line.startsWith("@parameter-num-classifiers ")) {
				if (!isEnsemble) {
					throw new ParseException("Invalid declaration @parameter-num-classifiers for no ensemble model.", 0);
				}

				String[] split = line.split(" ", 3);

				if (split.length != 3) {
					String msg = reportParseError("Invalid arguments for @parameter-num-classifiers declaration. Expected: @parameter-num-classifiers <default number> <integer interval>.", "@parameter-num-classifiers ".length(), line);
					throw new ParseException(msg, 0);
				}
				
				Interval interval = Interval.parse(Interval.Type.Integer, split[2]);
				numClassifiers = new Parameter("maxClassifiers", null, split[1], interval, false);		
				
				if(classifierParameter != null && "-W".equals(classifierParameter.key) && (numClassifiers.interval.size() != 1 || 1 != (Integer) numClassifiers.interval.minValue)){
					throw new ParseException("Invalid num of classifiers of declaration @parameter-num-classifiers (for comite with -W parameter accepts only 1 classifier).", 0);
				}
				if(1 > (Integer) numClassifiers.interval.minValue){
					throw new ParseException("Invalid num of classifiers of declaration @parameter-num-classifiers (must positive).", 0);
				}
			} else if (line.startsWith("@parameter-percent-homogeneous ")) {
				if (!isEnsemble) {
					throw new ParseException("Invalid declaration @parameter-num-classifiers for no ensemble model.", 0);
				}

				String[] split = line.split(" ", 3);

				if (split.length != 3) {
					String msg = reportParseError("Invalid arguments for @parameter-percent-homogeneous declaration. Expected: @parameter-percent-homogeneous <default number> <integer interval>.", "@parameter-percent-homogeneous ".length(), line);
					throw new ParseException(msg, 0);
				} else {
					percentHomogeneous = new Parameter("percentHomogeneous", null, split[1], Interval.parse(Interval.Type.Double, split[2]), false);
				}
			} else {
				break;
			}
			line = reader.readLine();
		}

		if (line == null || !(line.startsWith("@end ") || line.equals("@end"))) {
			String msg = reportParseError("Expected @end declaration for model " + modelName + ".", 0, line);
			throw new ParseException(msg, 0);
		}

		Model model;
		if (isEnsemble) {
			model = new Model(modelName, numClassifiers, percentHomogeneous, classifierParameter);
		} else {
			model = new Model(modelName);
		}

		model.conditionals.addAll(conditionals);
		model.parameters.putAll(parameters);

		return model;
	}

	/**
	 * Cria um modelo a partir de uma descrição textual.<br><br>
	 * A descrição textual do modelo é composta por três partes separadas por uma linha cada.<br>
	 * A primeira parte deve corresonder a declaraçaõ do nomde e tipo do modelo e deve ser da forma
	 * 
	 * <pre>
	 * &#64;classifier &lt;name&gt; | &#64;ensemble &lt;name&gt;
	 * </pre>
	 * 
	 * .
	 * &lt;name&gt; deve corresponder ao nome de classificador no weka.<br><br>
	 * A segunda parte pode ser composta por zero ou mais declaraçõs (separados por uma nova linha) de
	 * {@link Parameter}, {@link Conditional}, sub-parâmetros, configuração da quantidade de classificadores no comitê
	 * e/ou configuração do tipo comitê quanto à homogêneo/heterogêneo.
	 * <br>
	 * A configuração da quantidade de classificadores base deve ser da forma
	 * 
	 * <pre>
	 * &#64;parameter-num-classifiers &lt;integer&gt; &lt;interval-size&gt;
	 * </pre>
	 * 
	 * .
	 * &lt;integer&gt; corresponde a quantidade padrão de classificadores base.<br>
	 * &lt;interval-size&gt; deve corresponder a um {@link Interval} de valores do tipo {@link Integer}.<br>
	 * Deve haver somente uma declaração de configuração da quantidade de classificadores base.<br>
	 * Em caso de múltiplas declarações somente a última é consideradas.<br>
	 * Caso não seja declarado, o modelo assume que o comitê não utiliza classificadores base.<br>
	 * <br>
	 * A configuração do tipo comitê quanto à homogêneo/heterogêneo deve ser da forma
	 * 
	 * <pre>
	 * &#64;parameter-num-classifiers &lt;double&gt; &lt;interval-type&gt;
	 * </pre>
	 * 
	 * &lt;double&gt; corresponde a distribuição padrão dos classificadores base de modo que sempre vai existir um
	 * classificadores base que estar presente entre os classificadores base nessa proporção ou mais.<br>
	 * Por exemplo, para 0.5 temos que metade dos classificadores base devem ser dos mesmo tipo e para 1.0 o comitê será sempre homogêneo.<br>
	 * &lt;integer&gt; corresponde ao {@link Interval} de distribuição dos tipos dos classificadores base. Os valores
	 * devem corresponder ao tipo {@link Double} e deve estar no intervalo (0,1).<br>
	 * Deve haver somente uma declaração de configuração de tipo.<br>
	 * Em caso de múltiplas declarações somente a última é consideradas.<br>
	 * Caso não seja declarado, o modelo não efetua nenhuma verificação sobre os tipos dos classificadores base.<br>
	 * <br>
	 * Um declaração de sub-parâmetros é usada para determina os parâmetros de valores de outros parâmetros.<br>
	 * Por exemplo, no kNN é possível especificar o algoritmo de busca e ele pode receber parâmetros, assim, podemos
	 * usar a declaração de sub-parâmetros para determinar quais os parâmetros dos algoritmo de busca que também devem
	 * ser otimizados.<br>
	 * A declaração de sub-parâmetros é similar a declaração de um modelo com exceção da primeira parte que deve estar
	 * no formato
	 * 
	 * <pre>
	 * &#64;sub-parameter &lt;key&gt; &lt;parameter-value%gt;
	 * </pre>
	 * 
	 * &lt;key&gt; deve corresponde a chave de um dos parâmetros do modelo previamente declarado na forma
	 * {@link Parameter}.<br>
	 * &lt;parameter-value&gt; deve corresponder a um dos valores para o parêmtro de mesma key especificada.
	 * <br><br>
	 * Além disso, caso o comitê aceite classificadores base como parâmetros, eles devem ser especificados conforme
	 * {@link Parameter} com o tipo de valores correspondente a "classifier".<br>
	 * Esse {@link Parameter} deve ter uma chave "-W", para comitês iterativos sobre um único classificador como o
	 * {@link Bagging}, ou "-B", para ensembles como {@link Vote}.<br>
	 * Caso não seja declarado, o modelo assume que o comitê não aceita classificadores base como argumento como no
	 * {@link RandomForest}.
	 * <br><br>
	 * A declaração de modelo assim como de sub-parâmetros deve terminar em uma nova linha na forma
	 * 
	 * <pre>
	 * &#64;end
	 * </pre>
	 * 
	 * <br>
	 * Por exemplo:
	 * 
	 * <pre>
		&#64;classifier weka.classifiers.functions.SMO
			&#64;parameter filterType -N 0 integer {0,1,2}
			&#64;parameter buildCalibrationModels -M false boolean
			&#64;parameter kernel -K weka.classifiers.functions.supportVector.NormalizedPolyKernel string {weka.classifiers.functions.supportVector.NormalizedPolyKernel, weka.classifiers.functions.supportVector.PolyKernel, weka.classifiers.functions.supportVector.Puk, weka.classifiers.functions.supportVector.RBFKernel}
			&#64;sub-parameter -K weka.classifiers.functions.supportVector.NormalizedPolyKernel
				&#64;parameter exponent -E 1 double [0.2,5]
				&#64;parameter useLowerOrder -L false boolean
			&#64;end
			&#64;sub-parameter -K weka.classifiers.functions.supportVector.RBFKernel
				&#64;parameter gamma -G 0.01 double [0.0001,1]
			&#64;end
		&#64;end
	 * </pre>
	 * 
	 * Descreve um modelo de classificador base correspondente ao algoritmo SMO onde os parâmetros filterType,
	 * buildCalibrationModels e kernel são otimizados.<br>
	 * Para o parêmtro kernel, são otimizados os parâmetros exponente e useLowerOrder caso
	 * weka.classifiers.functions.supportVector.NormalizedPolyKernel seja selecionado para -K.<br>
	 * De modo similar, é otimizado o parâmetro gamma caso seja selecionado
	 * weka.classifiers.functions.supportVector.RBFKernel para -K.<br>
	 * 
	 * <pre>
		&#64;ensemble weka.classifiers.meta.Vote
			&#64;parameter combinationRule -R AVG string {AVG, PROD, MAJ, MIN, MAX, MED}
			&#64;parameter-num-classifiers 2 [2,10]
			&#64;parameter-percent-homogeneous 0 {0.6}
			&#64;parameter classifier -B weka.classifiers.bayes.NaiveBayes classifier {weka.classifiers.bayes.BayesNet, weka.classifiers.bayes.NaiveBayes}
		&#64;end
	 * </pre>
	 * 
	 * Descreve um modelo de comitê correspondente ao algoritmo Vote onde os parâmetros combinationRule, quantidade de
	 * classificadores base e tipo do comitê são otimizados.<br>
	 * Esse comitê pode ter entre 2 e 10 (inclusive) classificadores base sendo esse valor determinado durante o
	 * processo de otimização.<br>
	 * Esse comitê deverá ter uma proporção de no mínimo 60% de um mesmo tipo de classificador base dentre os
	 * classificadores base ativos.<br>
	 * Classificadores ativos corresponde a quantidade corrente de classificadores base do comitê, já que a mesma é
	 * vairável depedendo da configuração usada.<br>
	 * Por exemplo, para 5 classificadores base ativos temos que ao menos 3 devem ser do mesmo tipo e para 10
	 * classificadores ativos temos que ao menos 6 devem ser do mesmo tipo.<br>
	 * Além disso, o parâmetro classifier determina os classificadores base aceitos por esse comitê.<br>
	 * As configurações dos classificadores base devem ser informadas os argumentos do método.
	 * 
	 * @param reader {@link Reader} de onde o texto será extraído.
	 * @param classifiers Associação de nomes de classificadores base com seus respectivos modelos. Por exemplo,
	 *        (weka.classifiers.functions.SMO, {@link Model} para o SMO).
	 * @return O modelo criado correspondente a descrição textual ou null seja lido todo o reader.
	 * @throws ParseException Em caso de erros sintaxe ou semântica na descrição textual do modelo.
	 * @throws IOException Em caso de IO erros.
	 */
	public static Model parse(BufferedReader reader, Map<String, Model> classifiers) throws ParseException, IOException {
		String line = reader.readLine();
		if (line == null) {
			return null;
		}

		line = line.trim();
		if (!(line.startsWith("@classifier ") || line.startsWith("@ensemble "))) {
			String msg = reportParseError("Unexpected declaration. Expected @classifier or @ensemble.", 0, line);
			throw new ParseException(msg, 0);
		}

		String[] split = line.split(" ");
		String modelType = line.startsWith("@classifier ") ? "classifier " : "ensemble ";

		if (split.length != 2) {
			String msg = reportParseError("Invalid arguments for @" + modelType + " declaration.", ("@" + modelType + " ").length(), line);
			throw new ParseException(msg, 0);
		}

		Model model = parseModelFields(split[1], line.startsWith("@ensemble "), reader, classifiers);
		return model;
	}

	private static String reportParseError(String error, int offset, String text) {
		StringBuilder msg = new StringBuilder(error);
		msg.append("\n");
		msg.append(text);
		msg.append("\n");
		for (int i = 0; i < offset; i++) {
			msg.append("-");
		}
		msg.append("^\n\n");
		return msg.toString();
	}

	@Override
	public String toString() {
		return "Model(" + name + ")";
	}
}
