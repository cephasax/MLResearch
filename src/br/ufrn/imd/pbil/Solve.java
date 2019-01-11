package br.ufrn.imd.pbil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import br.ufrn.imd.pbil.Interval.Type;

import java.util.Random;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.meta.RandomCommittee;
import weka.core.Instances;
import weka.core.Randomizable;
import weka.core.Utils;

/**
 * Codificação de uma solução binária para uso com o PBIL.<br>
 * <br>
 * Representa modelos com parâmetros dinâmicos.<br>
 * Os modelos podem ser classificadores ou comitês de classificadores.<br>
 * A soluções manipula representações de parâmetros do formato WEKA no espaço binário.<br>
 * <br>
 * 
 * Uma solução é organizada nos do seguint modo:
 * 
 * <pre>
 * &lt;model-bits&gt; &lt;model-parans&gt; &lt;base-model-1&gt; ... &lt;base-model-n&gt; &lt;base-parans-1&gt; &lt;base-parans-2&gt; ...
 * </pre>
 * 
 * <br>
 * &lt;model-bits&gt; é um conjunto de bits que codifica o modelo representado pela solução.<br>
 * A cada modelo é associado a um inteiro e então esse inteiro é codificado no nesse conjunto de bits.<br>
 * Esse conjunto de bits possui cardinalidade igual a quantidade de bits necessária para representar a soma das
 * quantidades de comitês mais classificadores base.<br>
 * 
 * <br>
 * &lt;model-parans&gt; é um conjunto de bits que codifica os parâmetros e sub-parâmetros do modelo representado.<br>
 * Cada modelo pode possuir um conjunto de parâmetros de diferente e cada parâmetro possui um intervalo de valores
 * possíveis.<br>
 * Esse intervalo de valores é discretizado determinando a quantidade de bits necessários para representar o parâmetro
 * específico.<br>
 * A soma da quantidade de bits de todos os parâmetros determinar a cardinalidade desse conjunto de bits.<br>
 * Caso o parâmetro possua sub-parâmetros, isto é, ao menos um dos valores possíveis possui parâmetros que devem ser
 * otimizados, então eles também são codificados logo em seguida do parâmetro atual.<br>
 * 
 * <br>
 * Se modelo corresponder a um comitê e o mesmo aceitar classificadores base como parâmetros então eles são codificados
 * após &lt;model-parans&gt;.<br>
 * Para essa codificação é necessário determinar a quantidade máxima N de classificadores base dentre todos os modelos
 * de comitês possíveis.<br>
 * Em seguida, são codificados os N modelos e depois os correspondentes N conjuntos de parâmetros associados a cada
 * modelo.<br>
 * Caso o comitê utilize menos classificadores base que a quantidade máxima, o excedente é ignorado.<br>
 * A codificação do modelo de cada classificador base é efetuada de modo similar a codificação dos modelos, porém são
 * considerados somente os modelos de classificadores base.<br>
 * A codificação dos parâmetros de cada classificador base é efetuada do mesmo modo que a coficação dos parâmetros do
 * modelo.
 * <br>
 * <br>
 * Em todos os casos, cada conjunto de bits ocupa possições distintas na codificação. No entanto, soluções diferentes
 * podem interpretar a codificação de maneiras distintas.<br>
 * Por exemplo, para um modelo de classificador base os conjuntos de bits após &lt;model-parans&gt; são ignorados. No
 * entanto, os mesmo são considerados no caso de um comitê como o Vote por exemplo.
 * 
 * @author antonino
 *
 */
public class Solve implements Comparable<Solve>, Serializable {

	private static final long serialVersionUID = 1L;

	// classifiers + ensembles
	private final int numModels;

	// number of possible paramenters for ensembles and classifiers
	private final int bitsModelParans;

	// classifiers
	private final int numBaseClassifiers;

	// maximum number of base classifiers
	private final int numMaxBaseClassifiers;

	// number of different parameters
	private final int bitsBaseParans;

	/**
	 * Codificação dos modelos em formato binário.<br>
	 * A codificação segue o seguinte formato:
	 * 
	 * <pre>
		bits for model | bits for model parans | bits for 1 classifier | bits for 2 classifier | ... | bits for parans for 1 classifier | bits for 2 | ...
	 * </pre>
	 */
	public final boolean[] encode;

	/** Indica se a solução foi avaliada corretamente. */
	public boolean evaluated;

	/**
	 * Indica o tempo utilizado para avaliar a solução em ms.<br>
	 * Só é válido caso {@link #evaluated} seja true.
	 */
	public long timeProcessed;
	
	/**
	 * Indica o erro percentual médio de classificação.<br>
	 * Só é válido caso {@link #evaluated} seja true.
	 */
	public double percentualError;

	/**
	Indica o erro de cada um dos folds durante a avaliação.
	Só é válido caso {@link #evaluated} seja true.
	*/
	public double [] cvError;

	/** Configurações dos modelos. */
	protected final Configuration configuration;

	// for clone porpuse
	private Solve(Solve solve) {
		evaluated = solve.evaluated;
		numModels = solve.numModels;
		numMaxBaseClassifiers = solve.numMaxBaseClassifiers;
		numBaseClassifiers = solve.numBaseClassifiers;
		bitsBaseParans = solve.bitsBaseParans;
		timeProcessed = solve.timeProcessed;
		percentualError = solve.percentualError;
		configuration = solve.configuration;
		bitsModelParans = solve.bitsModelParans;

		cvError = new double[solve.cvError.length];
		System.arraycopy(solve.cvError, 0, cvError, 0, cvError.length);
		encode = new boolean[solve.encode.length];
		System.arraycopy(solve.encode, 0, encode, 0, encode.length);
	}

	/***
	 * Cria uma solução a partir das configurações indicadas.<br>
	 * Todos os valores no vetor de condificação são false.
	 * @param configuration Configuração das soluções usadas para determinar os possíveis modelos assim como seus
	 *        parâmetros.
	 * @exception AssertionError Se a configuração não tiver modelos.
	 * @exception IllegalArgumentException Se quantidade de bits para representar um modelo for maior ou igual a
	 *            quantidade de bits num inteiro {@link Integer#SIZE}.
	 */
	public Solve(Configuration configuration) {
		this.evaluated = false;
		this.configuration = configuration;

		// configuração 1/15 (1.1) (já rodei)
		//numModels = configuration.classifiers.size() + configuration.ensembles.size();
		//System.out.println("numModels.: " + numModels);
		
		// configuração 1/6 (1.3) (já rodei)
		numModels = configuration.ensembles.size() + 3;
		//System.out.println("numModels.: " + numModels);
		
		// configuração 1/7 (1.2)
		//int seed = 1 + (int) (Math.random( ) * 7);
		//System.out.println("seed..: " + seed);
		//if (seed > 1)  	numModels = configuration.ensembles.size() 		+ 4;
		//else 			numModels = configuration.classifiers.size() 	+ 1;
		//System.out.println("numModels.: " + numModels);
		
		numBaseClassifiers = configuration.classifiers.size();

		assert numModels > 0;

		int numMaxClassifiers = 0;
		for (Entry<String, Model> entry : configuration.ensembles.entrySet()) {
			Model model = entry.getValue();
			if (model.numClassifiers != null) {
				int value = (Integer) model.numClassifiers.interval.maxValue;
				numMaxClassifiers = Math.max(numMaxClassifiers, value);
			}
		}
		this.numMaxBaseClassifiers = numMaxClassifiers;

		int max = 0;
		for (Entry<String, Model> entry : configuration.classifiers.entrySet()) {
			int num = getNumBits(entry.getValue());
			max = Math.max(num, max);
		}
		this.bitsBaseParans = max;

		max = 0;
		for (Entry<String, Model> entry : configuration.ensembles.entrySet()) {
			int num = getNumBits(entry.getValue());
			max = Math.max(num, max);
		}
		this.bitsModelParans = Math.max(max, this.bitsBaseParans);

		int[] numValues = { numModels, bitsModelParans, numBaseClassifiers, bitsBaseParans };
		String[] msg = { "models (classifiers and ensembles)", "ensemble parameters", "base classifiers", "base classifiers parameters" };

		for (int i = 0; i < numValues.length; i++) {
			if (getNumBits(numValues[i]) >= Integer.SIZE) {
				throw new IllegalArgumentException("Unsupported number of " + msg[i] + " " + numValues[i] + ". Num of " + msg[i] + " must be minor than " + (Integer.MAX_VALUE - 1) + ".");
			}
		}

		int numBitsModels = getNumBits(numModels - 1);
		// +1 for the base classifier type; use 0 inactive classifier
		int numBitsBaseClassifiers = getNumBits(numBaseClassifiers);

		encode = new boolean[numBitsModels + bitsModelParans + numBitsBaseClassifiers * numMaxBaseClassifiers + bitsBaseParans * numMaxBaseClassifiers];
	}

	/**
	 * Atribui valores aleatórios para codificação.
	 * @param rand Gerador de valores usado para gerar a nova configuração de valores.
	 */
	public void randomize(Random rand) {
		evaluated = false;
		assert encode != null;
		for (int i = 0; i < encode.length; i++) {
			encode[i] = rand.nextBoolean();
		}
	}

	// values are stored left to right in the right portion of the bits
	// example, for number 5 in length 4: 0101
	// for number 1 in length 4: 0001
	protected void setValue(int value, int offset, int length) {
		int numBits = getNumBits(value);
		assert numBits <= length;
		assert offset + length <= encode.length : "Index " + (offset + length) + "=offset(" + offset + ")+(" + length + ") is greater than the size " + encode.length;
		BitSet set = BitSet.valueOf(new long[] { value });
		for (int i = offset; i < offset + (length - numBits); i++) {
			encode[i] = false;
		}
		offset = offset + (length - numBits);
		for (int i = numBits - 1; i >= 0; i--) {
			encode[offset + (numBits - 1 - i)] = set.get(i);
		}
	}

	protected int getValue(int offset, int length) {
		assert length - offset < Integer.SIZE;
		BitSet set = new BitSet(length);
		int end = offset + length;
		for (int i = offset; i < end; i++) {
			assert i < encode.length : "Index of " + offset + " " + length + " " + i;
			if (encode[i]) {
				set.set(offset + length - 1 - i);
			}
		}
		return set.isEmpty() ? 0 : (int) set.toLongArray()[0];
	}

	/**
	 * Repara a codifcação do modelo.<br>
	 * Por exemplo, se existem somente 5 modelos possíveis serão utilizados 3 bits na codificação para representar o
	 * modelo.<br>
	 * Assim, caso os valores 6 ou 7 forem selecionados então a condificação não corresponderá a uma solução válida.<br>
	 * Nesse caso, os valores inválidos são substituídos por valores válidos.<br>
	 * Não são efetuadas modificação nos campos válidos.
	 * @param rand Gerados de valores usado para gerar os novos valores válidos no caso de substuíções.
	 */
	public void repair(Random rand) {
		int offset = 0;
		int numBits = getNumBits(numModels - 1);
		if (!isValid(numModels - 1, 0)) {
			
			// pega apenas base classifier soluções se repair for chamado
			//int newModel = rand.nextInt(numModels);
			int newModel = rand.nextInt(configuration.classifiers.size());
			
			
			// pega apenas meta/ensemble soluções se repair for chamado
			//int newModel = rand.nextInt(configuration.ensembles.size());
			
			setValue(newModel, offset, numBits);
			evaluated = false;
		}
		offset += numBits;

		String modelName = getModelName();
		Model model = configuration.ensembles.containsKey(modelName) ? configuration.ensembles.get(modelName) : configuration.classifiers.get(modelName);

		numBits = bitsModelParans;
		if (!isValid(offset, model)) {
			repair(offset, model, rand);
			evaluated = false;
		}
		offset += numBits;

		numBits = getNumBits(numBaseClassifiers);
		if (model.classifierParameter != null) {
			Interval classifiers = model.classifierParameter.interval;
			List<Integer> choice = new ArrayList<Integer>();
			for (int i = 0; i < classifiers.size(); i++) {
				String name = (String) classifiers.getValue(i);
				int code = getModelCode(name);
				choice.add(code);
			}
			
			int offsetParans = offset + getNumBits(numBaseClassifiers) * numMaxBaseClassifiers;
			for (int i = 0; i < numMaxBaseClassifiers; i++) {
				boolean inValid = !isValid(numBaseClassifiers, offset);
				
				if(!inValid){
					int baseCode = getValue(offset, numBits);
					String baseName = getModelName(baseCode + configuration.ensembles.size() - 1);
					if(!model.classifierParameter.interval.contains(baseName)){
						inValid = true;
					}
				}
				
				if (inValid) {
					// +1 fot inactive classifier
					int newCode = choice.get(rand.nextInt(choice.size()));
					setValue(newCode - configuration.ensembles.size() + 1, offset, numBits);
					evaluated = false;
				}
				
				int baseCode = getValue(offset, numBits);
				if (baseCode != 0) { // != inactive classifier
					String baseName = getModelName(baseCode + configuration.ensembles.size() - 1);
					Model baseModel = configuration.ensembles.containsKey(baseName) ? configuration.ensembles.get(baseName) : configuration.classifiers.get(baseName);
					repair(offsetParans, baseModel, rand);
				}

				offsetParans += bitsBaseParans;
				offset += numBits;
			}
		}

		boolean changedNumClassifiers = repairNumClassifiers(model, rand);
		boolean changedTypeClassifiers = repairHomogeneousDistribution(model, rand);
		if (changedNumClassifiers || changedTypeClassifiers) {
			repairBaseModelsParans(rand);
		}
	}

	protected void repairBaseModelsParans(Random rand) {
		int offset = getNumBits(numModels - 1) + bitsModelParans + getNumBits(numBaseClassifiers) * numMaxBaseClassifiers;
		int numBits = bitsBaseParans;
		int[] baseClassifiers = getClassifiersCodes();
		for (int i = 0; i < baseClassifiers.length; i++) {
			if (baseClassifiers[i] != -1) {
				String baseModelName = getModelName(baseClassifiers[i]);
				assert configuration.classifiers.containsKey(baseModelName);
				Model baseModel = configuration.classifiers.get(baseModelName);
				if (!isValid(offset, baseModel)) {
					repair(offset, baseModel, rand);
					evaluated = false;
				}
			}
			offset += numBits;
		}
	}

	protected boolean repairNumClassifiers(Model model, Random rand) {
		boolean needRepairBaseParans = false;
		if (model.numClassifiers == null) {
			return false;
		}

		Interval classifiers = model.classifierParameter.interval;
		List<Integer> choice = new ArrayList<Integer>();
		for (int i = 0; i < classifiers.size(); i++) {
			String name = (String) classifiers.getValue(i);
			int code = getModelCode(name);
			choice.add(code);
		}

		// +1 for inactive classifiers (value 0)
		int offset = getNumBits(numModels - 1) + bitsModelParans;
		int startOffset = offset;
		int numActive = 0;
		int numBits = getNumBits(numBaseClassifiers);
		int[] baseClassifiers = getClassifiersCodes();
		choice.add(configuration.ensembles.size() - 1); // can choose inactive classifier
		for (int i = 0; i < baseClassifiers.length; i++) {
			if (!isValid(numBaseClassifiers, offset)) {
				int newModel = choice.remove(rand.nextInt(choice.size()));
				baseClassifiers[i] = newModel;
				setValue(newModel - configuration.ensembles.size() + 1, offset, numBits);
				needRepairBaseParans = true;
				evaluated = false;
			}
			if (baseClassifiers[i] != -1) { // -1 = inactive
				numActive++;
			}
			offset += numBits;
		}
		choice.remove(new Integer(configuration.ensembles.size() - 1));

		offset = startOffset;
		Interval numClassifiers = model.numClassifiers.interval;
		int minNumClassifiers = (Integer) numClassifiers.minValue;
		int maxNumClassifiers = (Integer) numClassifiers.maxValue;
		if (numActive < minNumClassifiers) {
			assert minNumClassifiers <= numMaxBaseClassifiers;
			for (int i = 0; i < numMaxBaseClassifiers; i++) {
				if (baseClassifiers[i] == -1) {
					// exclude -1 (inactive classifier)
					int newModel = choice.get(rand.nextInt(choice.size()));
					baseClassifiers[i] = newModel;
					setValue(newModel - configuration.ensembles.size() + 1, offset, numBits);
					numActive++;
					if (numActive >= minNumClassifiers) {
						break;
					}
				}
				offset += numBits;
			}
			assert numActive == minNumClassifiers;
			needRepairBaseParans = true;
			evaluated = false;
		}

		if (numActive > maxNumClassifiers) {
			assert maxNumClassifiers <= numMaxBaseClassifiers;
			for (int i = numMaxBaseClassifiers - 1; i >= 0; i--) {
				if (baseClassifiers[i] != -1) {
					baseClassifiers[i] = -1;
					setValue(0, startOffset + numBits * i, numBits);
					numActive--;
					if (numActive <= maxNumClassifiers) {
						break;
					}
				}
			}
			assert numActive == maxNumClassifiers;
			needRepairBaseParans = true;
			evaluated = false;
		}

		return needRepairBaseParans;
	}

	protected boolean repairHomogeneousDistribution(Model model, Random rand) {
		boolean needRepairBaseParans = false;
		int[] baseClassifiers = getClassifiersCodes();
		Parameter homogeneous = model.percentHomogeneous;
		if (homogeneous == null) {
			return false;
		}

		int numActive = 0;
		int homoClassifierIndex = -1;
		HashMap<Integer, Integer> count = new HashMap<>();
		for (int i = 0; i < baseClassifiers.length; i++) {
			if (baseClassifiers[i] != -1) {
				if (!count.containsKey(baseClassifiers[i])) {
					count.put(baseClassifiers[i], 0);
				}
				count.put(baseClassifiers[i], count.get(baseClassifiers[i]) + 1);
				if (homoClassifierIndex == -1 || count.get(baseClassifiers[i]) > count.get(homoClassifierIndex)) {
					homoClassifierIndex = baseClassifiers[i];
				}
				numActive++;
			}
		}
		assert homoClassifierIndex >= 0;

		int numBits = getNumBits(numBaseClassifiers);
		int startOffset = getNumBits(numModels - 1) + bitsModelParans;
		double percentHomogeneous = count.get(homoClassifierIndex) / (double) numActive;
		if (!homogeneous.interval.contains(percentHomogeneous)) {
			double newPercent = (Double) homogeneous.interval.getValue(rand.nextInt(homogeneous.interval.size()));

			if (percentHomogeneous < newPercent) {
				Interval classifiers = model.classifierParameter.interval;
				List<Integer> choice = new ArrayList<Integer>();
				for (int i = 0; i < classifiers.size(); i++) {
					String name = (String) classifiers.getValue(i);
					int code = getModelCode(name);
					if (!count.containsKey(code)) {
						choice.add(code);
					}
				}

				int offset = startOffset;
				for (int i = 0; i < numMaxBaseClassifiers; i++) {
					if (baseClassifiers[i] != -1 && baseClassifiers[i] != homoClassifierIndex) {
						setValue(homoClassifierIndex - configuration.ensembles.size() + 1, offset, numBits);
						baseClassifiers[i] = homoClassifierIndex;
						count.put(homoClassifierIndex, count.get(homoClassifierIndex) + 1);
						percentHomogeneous = count.get(homoClassifierIndex) / (double) numActive;
						if (percentHomogeneous >= newPercent) {
							break;
						}
					}
					offset += numBits;
				}
			}
			assert percentHomogeneous >= newPercent : percentHomogeneous + " < " + newPercent;
			needRepairBaseParans = true;
			evaluated = false;
		}

		return needRepairBaseParans;
	}

	protected void repair(int offset, Model model, Random rand) {
		for (Entry<String, Parameter> entry : model.parameters.entrySet()) {
			repair(offset, entry.getValue(), rand);
			offset += getNumBits(entry.getValue());
		}
	}

	protected void repair(int offset, Parameter parameter, Random rand) {
		int size = parameter.interval.size();
		int numBits = getNumBits(size - 1);
		int code = getValue(offset, numBits);
		if (code >= size) {
			code = rand.nextInt(size);
			setValue(code, offset, numBits);
		}
		offset += numBits;
		String value = parameter.interval.getValue(code).toString();
		if (parameter.subParameters != null) {
			if (parameter.subParameters.containsKey(value)) {
				repair(offset, parameter.subParameters.get(value), rand);
			}
		}
	}

	/**
	 * Verifica se a codificação é válida em relação a codificação.<br>
	 * Não verifica condições sobre os valores dos parâmetros dos modelos (@see {@link Solve#isSatisfiable()}).
	 * @return true caso a codificação seja válida.
	 */
	public boolean isValid() {
		// alterei aqui para zerar o percentualError - média divergente
		percentualError = 0;
		
		if (!isValid(numModels - 1, 0)) {
			return false;
		}

		String modelName = getModelName();
		Model model = configuration.ensembles.containsKey(modelName) ? configuration.ensembles.get(modelName) : configuration.classifiers.get(modelName);

		if (!isValid(getNumBits(numModels - 1), model)) {
			return false;
		}

		// only ensembles
		if (model.classifierParameter == null || model.numClassifiers == null) {
			return true;
		}

		int numActiveClassifiers = 0;
		int[] classifiers = getClassifiersCodes();
		HashMap<Integer, Integer> count = new HashMap<>();
		for (int i = 0; i < classifiers.length; i++) {
			if (classifiers[i] != -1) {
				numActiveClassifiers++;
				if (!count.containsKey(classifiers[i])) {
					count.put(classifiers[i], 1);
				} else {
					count.put(classifiers[i], count.get(classifiers[i]) + 1);
				}
			}
		}

		if (!model.numClassifiers.interval.contains(numActiveClassifiers)) {
			return false;
		}

		int offset = getNumBits(numModels - 1) + bitsModelParans + getNumBits(numBaseClassifiers) * numMaxBaseClassifiers;
		for (int i = 0; i < numMaxBaseClassifiers; i++) {
			if (classifiers[i] != -1) {
				Model baseClassifier = configuration.classifiers.get(getModelName(classifiers[i]));
				if (baseClassifier == null || !isValid(offset, baseClassifier) || !model.classifierParameter.interval.contains(baseClassifier.name)) {
					return false;
				}
			}
			offset += bitsBaseParans;
		}

		if (model.percentHomogeneous == null || "-W".equals(model.classifierParameter.key)) {
			return true;
		}

		int maximum = -1;
		for (Entry<Integer, Integer> entry : count.entrySet()) {
			if (maximum == -1 || entry.getValue() > count.get(maximum)) {
				maximum = entry.getKey();
			}
		}
		double percent = count.get(maximum) / (double) numActiveClassifiers;
		if (percent < (Double) model.percentHomogeneous.interval.minValue) {
			return false;
		}

		return true;
	}

	// do not check base classifiers
	protected boolean isValid(int offset, Model model) {
		for (Entry<String, Parameter> entry : model.parameters.entrySet()) {
			if (!isValid(offset, entry.getValue())) {
				return false;
			}
			offset += getNumBits(entry.getValue());
		}
		return true;
	}

	protected boolean isValid(int offset, Parameter parameter) {
		int size = getNumBits(parameter.interval.size() - 1);
		int code = getValue(offset, size);
		boolean valid = code < parameter.interval.size();
		if (valid && parameter.subParameters != null) {
			String value = parameter.interval.getValue(code).toString();
			if (parameter.subParameters.containsKey(value)) {
				valid = isValid(offset + getNumBits(size), parameter.subParameters.get(value));
			}
		}
		return valid;
	}
	
	// serve para testar se value (valor do classifier) será maior que 14 (0:14) - 15 possíveis
	protected boolean isValid(int maxValue, int offset) {
		int length = getNumBits(maxValue);
		int value = getValue(offset, length);
		//System.out.println("value = " + value + " maxValue = " + maxValue + " " + (value <= maxValue));
		return value <= maxValue;
	}

	// do not check classifier capabilities or ensemble retrictions over base classifiers
	/**
	 * Verifica se a solução é válida em relação as condições sobre os valores dos parâmetros do modelo.<br>
	 * Se a solução for válida (@see {{@link #isValid()}}) em relação a codificação e em relação a codificação então ela
	 * pode ser avaliada.<br>
	 * <b>Atenção:</b> só deve ser invocado para soluções válidas (@see {{@link #isValid()}}).<br>
	 * <b>Atenção:</b> existem outras condições que invalidam uma solução impedindo sua avaliação como as capacidades
	 * dos
	 * modelos {@link Classifier#getCapabilities()} ou outras restrições.<br>
	 * Por exemplo, o {@link RandomCommittee} só aceita classificadores base que implementem {@link Randomizable}.<br>
	 * Tais condições não são verificadas e lançam {@link Exception} caso {@link Solve#evaluate(Instances, Instances)}
	 * seja invocado.
	 * @return true se a solução for válida em reação as condições ({@link Model#conditionals}) sobre os modelos.
	 */
	public boolean isSatisfiable() {
		String parans = getParans();
		String modelName = getModelName();
		if (!(configuration.ensembles.containsKey(modelName) ? configuration.ensembles.get(modelName).isValid(parans) : configuration.classifiers.get(modelName).isValid(parans))) {
			return false;
		}
		return true;
	}

	/**
	 * Avalia um solução para a base de dados indicada usando o modelo codificado.<br>
	 * É invocado {@link #isValid()} e {@link #isSatisfiable()} e caso alguns dos dois seja falso a solução não pode ser
	 * avaliada.<br>
	 * A solução é avaliada por k-fold cross-validation sem efetuar randomização ou estratificação da base de dados.
	 * Após a execução de {@link #evaluate(Instances, Instances)} o campo {@link #percentualError} irá conter o erro
	 * percentual médio resultante dos k-folds e o campo {@link #timeProcessed} irá conter o tempo consumido para avaliação.<br>
	 * Esses campos são utilizados para comparação de soluções.
	 * @param train Base utilizada para treinar o modelo. Ela não sofre alterações durante a execução.
	 * @param test Base utilizada para testar o modelo. Ela não sofre alterações durante a execução.
	 * @exception UnknownError caso não seja criar o modelo a partir da condificação.
	 * @exception UnknownError caso não seja possível treinar o modelo com a base de dados de treino indicada.
	 * @exception UnknownError caso não seja possível classificar alguma das instâncias presente na base de dados de
	 *            teste.
	 */
	// do not perform stratification
	public void evaluate(Instances train, int numFolds) {
		if (!isValid() || !isSatisfiable()) {
			evaluated = false;
			percentualError = 1;
			// return;
			throw new UnknownError("Isn't valid or isn't satisfiable.");
		}

		evaluated = false;
		long time = System.currentTimeMillis();
		Classifier classifier;
		try {
			classifier = getClassifier();
		} catch (Exception e) {
			UnknownError error = new UnknownError("Can not create the classifier.");
			error.initCause(e);
			throw error;
		}

		cvError = new double[numFolds];
		for (int fold = 0; fold < numFolds; fold++) {

			try {
				classifier.buildClassifier(train.trainCV(numFolds, fold));
			} catch (Exception e) {
				UnknownError err = new UnknownError("Warning: " + e.getMessage() + " " + getParans());
				err.initCause(e);
				throw err;
			} catch (Throwable e) {
				UnknownError err = new UnknownError("Fatal Error (build fold " + fold + "): " + e.getMessage());
				err.initCause(e);
				throw e;
			}

			int error = 0;
			Instances test = train.testCV(numFolds, fold);
			for (int i = 0; i < test.numInstances(); i++) {
				try {
					double c = classifier.classifyInstance(test.get(i));
					if (test.get(i).classValue() != c) {
						error++;
					}
				} catch (Exception e) {
					UnknownError err = new UnknownError("Can not classify instance " + test.get(i));
					err.initCause(e);
					throw err;
				}
			}

			cvError[fold] = error / (double) test.numInstances();
			percentualError += cvError[fold];
		}

		time = System.currentTimeMillis() - time;
		timeProcessed = time;
		percentualError = percentualError / (double) numFolds;
		evaluated = true;
	}
	
	// do not check if the parans are valid with num of base classifiers and homogeneous
	/**
	 * Transforma os parâmetros inidicados numa codificação binária correspondente e a armazena na solução.<br>
	 * Não efetua verificações sobre a quantidade de classificadores base ou sobre o tipo do comitê.
	 * @param parans Parâmetros a serem codificados.
	 * @throws Exception Caso os parâmetros não estejam num formato especificado pelo Weka.
	 */
	public void setParans(String parans) throws Exception {
		evaluated = false;
		String[] split = parans.split(" ", 2);
		assert split.length == 2;
		String modelName = split[0];
		int modelCode = getModelCode(modelName);
		Model model = configuration.ensembles.containsKey(modelName) ? configuration.ensembles.get(modelName) : configuration.classifiers.get(modelName);

		assert model != null;
		setValue(modelCode, 0, getNumBits(numModels - 1));

		if (model.classifierParameter == null) {
			int offset = getNumBits(numModels - 1);
			setParans(parans, model, offset);
			offset += bitsModelParans;
			int numBits = getNumBits(numBaseClassifiers + 1);
			for (int i = 0; i < numMaxBaseClassifiers; i++) {
				setValue(0, offset, numBits);
				offset += numBits;
			}
		} else if (model.classifierParameter.key.equals("-B")) {
			StringBuilder ensembleParans = new StringBuilder();
			String[] paransSplit = Utils.splitOptions(parans);
			int countBaseClassifiers = 0;
			int numBits = getNumBits(numBaseClassifiers);
			int baseClassifierOffset = getNumBits(numModels - 1) + bitsModelParans;
			int baseClassifierParansOffset = baseClassifierOffset + numBits * numMaxBaseClassifiers;
			for (int i = 0; i < paransSplit.length; i++) {
				if (paransSplit[i].equals(model.classifierParameter.key)) {
					countBaseClassifiers++;
					assert countBaseClassifiers <= (Integer) model.numClassifiers.interval.maxValue;
					assert i + 1 < paransSplit.length;
					String[] baseParansSplit = paransSplit[i + 1].split(" ", 2);
					int code = getModelCode(baseParansSplit[0]);
					// +1 for null classifier(0)
					setValue(code - configuration.ensembles.size() + 1, baseClassifierOffset, numBits);
					assert configuration.classifiers.containsKey(getModelName(code));
					Model baseModel = configuration.classifiers.get(getModelName(code));
					setParans(paransSplit[i + 1], baseModel, baseClassifierParansOffset);
					baseClassifierOffset += numBits;
					baseClassifierParansOffset += bitsBaseParans;
					i++;
				} else {
					ensembleParans.append(paransSplit[i]);
					ensembleParans.append(" ");
				}
			}
			for (int i = countBaseClassifiers; i < numMaxBaseClassifiers; i++) {
				setValue(0, baseClassifierOffset, numBits);
				baseClassifierOffset += numBits;
			}
			if (ensembleParans.length() > 0) {
				ensembleParans.delete(ensembleParans.length() - 1, ensembleParans.length());
			}
			setParans(ensembleParans.toString(), model, getNumBits(numModels - 1));
		} else if (model.classifierParameter.key.equals("-W")) { // -B -W hack, change model to correct
			StringBuilder ensembleParans = new StringBuilder();
			String[] paransSplit = Utils.splitOptions(parans);
			int numBits = getNumBits(numBaseClassifiers);
			int baseClassifierOffset = getNumBits(numModels - 1) + bitsModelParans;
			int baseClassifierParansOffset = baseClassifierOffset + numBits * numMaxBaseClassifiers;
			for (int i = 0; i < paransSplit.length; i++) {
				if (paransSplit[i].equals(model.classifierParameter.key)) {
					assert i + 1 < paransSplit.length;
					String baseModelName = paransSplit[++i];
					String baseParans = baseModelName;
					if (i + 1 < paransSplit.length && paransSplit[i + 1].equals("--")) {
						i += 2;
						while (i < paransSplit.length) {
							baseParans += " " + (paransSplit[i].indexOf(' ') != -1 ? "\"" + paransSplit[i] + "\"" : paransSplit[i]);
							i++;
						}
					}
					int code = getModelCode(baseModelName);
					// +1 for null classifier(0)
					assert configuration.classifiers.containsKey(getModelName(code)) : baseModelName;
					setValue(code - configuration.ensembles.size() + 1, baseClassifierOffset, numBits);
					Model baseModel = configuration.classifiers.get(getModelName(code));
					setParans(baseParans, baseModel, baseClassifierParansOffset);
					baseClassifierOffset += numBits;
				} else {
					ensembleParans.append(paransSplit[i]);
					ensembleParans.append(" ");
				}
			}
			for (int i = 1; i < numMaxBaseClassifiers; i++) {
				setValue(0, baseClassifierOffset, numBits);
				baseClassifierOffset += numBits;
			}
			if (ensembleParans.length() > 0) {
				ensembleParans.delete(ensembleParans.length() - 1, ensembleParans.length());
			}
			setParans(ensembleParans.toString(), model, getNumBits(numModels - 1));
		}
	}

	protected void setParans(String parans, Model model, int offset) throws Exception {
		// do not set base classifiers parans
		HashMap<String, String> mapParans = new HashMap<>();
		String[] split = Utils.splitOptions(parans);
		for (int i = 1; i < split.length; i++) {
			// parameter classifier is removed previously
			assert model.parameters.containsKey(split[i]) : "Not key " + split[i] + " on " + model;
			Parameter parameter = model.parameters.get(split[i]);
			if (parameter.interval.type == Interval.Type.Boolean) {
				mapParans.put(split[i], "true");
			} else {
				assert i + 1 < split.length : parans;
				mapParans.put(split[i], split[i + 1]);
				i++;
			}
		}

		for (Entry<String, Parameter> entry : model.parameters.entrySet()) {
			String key = entry.getKey();
			Parameter parameter = entry.getValue();
			String value = mapParans.containsKey(key) ? mapParans.get(key) : parameter.defaultValue;
			String paransParameter = key;
			int add = 0;
			if (parameter.interval.type == Type.Boolean && "false".equals(value)) {
				add = getNumBits(parameter.interval.size() - 1);
				setValue(parameter.interval.getIndex("false"), offset, add);
			} else {
				if (!(parameter.interval.type == Type.Boolean && "true".equals(value))) {
					paransParameter += " " + value;
				}
				setParans(paransParameter, parameter, offset);
			}
			offset += getNumBits(parameter);
		}
	}

	protected void setParans(String parans, Parameter parameter, int offset) throws Exception {
		int numBits = getNumBits(parameter.interval.size() - 1);
		String[] split = parans.split(" ", 2);
		assert split.length >= 1;
		String key = split[0];
		assert parameter.key.equals(key);
		if (parameter.interval.type == Type.Boolean) {
			if (split.length == 1) {
				if (parameter.interval.size() > 1) {
					setValue(parameter.interval.getIndex("true"), offset, numBits);
				}
			} else {
				assert false;
			}
		} else {
			String value = split[1];
			if (value.indexOf(' ') != -1) {
				value = value.replaceAll("\\\\\"", "\\\"");
				value = value.replaceAll("^\"|(?<!\\\\)\"", "");
				split = value.split(" ", 2);
				setValue(parameter.interval.getIndex(split[0]), offset, numBits);
				if (parameter.subParameters.containsKey(split[0])) {
					setParans(value, parameter.subParameters.get(split[0]), offset + numBits);
				} else {
					assert false;
				}
			} else {
				setValue(parameter.interval.getIndex(value), offset, numBits);
			}
		}
	}

	/**
	 * Recupera a codificação dessa solução e a apresenta no formato do Weka.
	 * @return Nome do modelo mais seus parâmetros e valores correntes no formato Weka.
	 */
	public String getParans() {
		String modelName = getModelName();
		if (modelName == null) {
			return null;
		}

		Model model = configuration.ensembles.containsKey(modelName) ? configuration.ensembles.get(modelName) : configuration.classifiers.get(modelName);
		StringBuilder parans = new StringBuilder(getParans(getNumBits(numModels - 1), model));

		if (model.classifierParameter != null) {
			int offset = getNumBits(numModels - 1) + bitsModelParans + getNumBits(numBaseClassifiers) * numMaxBaseClassifiers;
			int[] baseClassifier = getClassifiersCodes();
			// hack -B -W, change model to correct
			if (model.classifierParameter.key.equals("-B")) { // vector of classifiers
				for (int i = 0; i < baseClassifier.length; i++) {
					if (baseClassifier[i] == -1) {
						offset += bitsBaseParans;
						continue;
					}
					Model base = configuration.classifiers.get(getModelName(baseClassifier[i]));
					String par = getParans(offset, base);
					offset += bitsBaseParans;
					if (par != null) {
						par = par.replaceAll("\\\"", "\\\\\"");
						parans.append(" ");
						parans.append(model.classifierParameter.key);
						parans.append(" \"");
						parans.append(par);
						parans.append(" \"");
					}
				}
			} else if (model.classifierParameter.key.equals("-W")) { // only 1 base classifier
				if (baseClassifier[0] != -1) {
					Model base = configuration.classifiers.get(getModelName(baseClassifier[0]));
					String par = getParans(offset, base);
					if (par != null) {
						parans.append(" ");
						parans.append(model.classifierParameter.key);
						String[] s = par.split(" ", 2);
						parans.append(" " + s[0] + (s.length > 1 ? (" -- " + s[1]) : ""));
					}
				}
			}
		}
		return parans.toString();
	}

	protected String getParans(int offset, Parameter parameter) {
		Interval interval = parameter.interval;
		int numBits = getNumBits(interval.size() - 1);
		int code = getValue(offset, numBits);
		Object value = interval.getValue(code);
		if (parameter.subParameters != null && parameter.subParameters.containsKey(value.toString())) {
			String sub = getParans(offset + numBits, parameter.subParameters.get(value.toString()));
			sub = sub.replaceAll("\\\"", "\\\\\"");
			value = sub.isEmpty() ? value : "\"" + sub + "\"";
		}
		if (interval.type == Interval.Type.Boolean) {
			return Boolean.FALSE.equals(value) ? "" : parameter.key;
		}
		return parameter.key + " " + value;
	}

	protected String getParans(int offset, Model model) {
		// Do not retrieve base classifiers parameters for ensemble models
		StringBuilder parans = new StringBuilder(model.name);
		for (Entry<String, Parameter> entry : model.parameters.entrySet()) {
			String value = getParans(offset, entry.getValue());
			if (!value.isEmpty()) {
				parans.append(" ");
				parans.append(value);
			}
			offset += getNumBits(entry.getValue());
		}
		return parans.toString();
	}

	/**
	 * Recupera o nome de cada um dos classificadores base ativos para o modelo.<br>
	 * São retornado N nome onde N é a quantidade máxima de classificadores entre todos os comitês.<br>
	 * Os classificadores base que não utilizados pelo comitê são representados por null.<br>
	 * Para modelos de classificadores base todos os campos retornados são null.
	 * @return Vetor contendo o nome de cada classificador base.
	 */
	public String[] getBaseClassifiersNames() {
		int[] codes = getClassifiersCodes();
		String[] names = new String[codes.length];
		for (int i = 0; i < codes.length; i++) {
			names[i] = codes[i] != -1 ? getModelName(codes[i]) : null;
		}
		return names;
	}

	protected int[] getClassifiersCodes() {
		int[] codes = new int[numMaxBaseClassifiers];
		int offset = getNumBits(numModels - 1) + bitsModelParans;
		// +1 for inactive classifiers (value 0)
		int sizeBaseClassifiers = getNumBits(numBaseClassifiers);
		for (int i = 0; i < numMaxBaseClassifiers; i++) {
			codes[i] = getValue(offset, sizeBaseClassifiers) - 1; // 0 is the null classifier
			if (codes[i] != -1) {
				codes[i] += configuration.ensembles.size();
			}
			offset += sizeBaseClassifiers;
		}
		return codes;
	}

	/**
	 * Recupera o classificador representado por essa solução.
	 * @return Um classificador correspondente a codificação correte.
	 * @throws Exception Caso não seja possível criar o classificador para a codificação corrente.
	 */
	public Classifier getClassifier() throws Exception {
		String modelName = getModelName();
		String parans = getParans();
		if (parans.startsWith(modelName)) {
			parans = parans.substring(modelName.length(), parans.length());
			parans = parans.trim();
		}
		return AbstractClassifier.forName(modelName, Utils.splitOptions(parans));
	}

	/**
	 * Recupera o nome do modelo representado por essa codificação.
	 * @return O nome do modelo representado ou null cado a solução seja inválida.
	 */
	public String getModelName() {
		int modelCode = numModels == 1 ? 0 : getValue(0, getNumBits(numModels - 1));
		return getModelName(modelCode);
	}

	// start by ensembles, conserve configuration declaration order
	protected String getModelName(int codeValue) {
		assert codeValue >= 0;
		if (codeValue < configuration.ensembles.size()) {
			int cont = -1;
			for (Entry<String, Model> entry : configuration.ensembles.entrySet()) {
				cont++;
				if (cont == codeValue) {
					return entry.getKey();
				}
			}
		} else if (codeValue < configuration.classifiers.size() + configuration.ensembles.size()) {
			int cont = configuration.ensembles.size() - 1;
			for (Entry<String, Model> entry : configuration.classifiers.entrySet()) {
				cont++;
				if (cont == codeValue) {
					return entry.getKey();
				}
			}
		}
		return null;
	}

	protected int getModelCode(String modelName) {
		int modelCode = -1;
		if (configuration.ensembles.containsKey(modelName)) {
			for (Entry<String, Model> entry : configuration.ensembles.entrySet()) {
				modelCode++;
				if (entry.getKey().equals(modelName)) {
					return modelCode;
				}
			}
		} else if (configuration.classifiers.containsKey(modelName)) {
			modelCode = configuration.ensembles.size() - 1;
			for (Entry<String, Model> entry : configuration.classifiers.entrySet()) {
				modelCode++;
				if (entry.getKey().equals(modelName)) {
					return modelCode;
				}
			}
		}
		return modelCode;
	}

	/**
	 * Cria uma cópia dessa solução incluindo a cópia de todos os campos.<br>
	 * Alteraçõs na cópia não são propagadas para solução corrente.
	 */
	public Solve clone() {
		return new Solve(this);
	}

	/**
	 * Compara soluções.<br>
	 * Uma solução que foi avaliada é sempre menor que uma que não foi avaliada.<br>
	 * Se a duas não foram avaliadas então elas são iguais.<br>
	 * Se a duas foram avaliadas então elas são comparadas em relação ao erro percentual médio.<br>
	 * Se elas forem iguais em relação ao erro percentual médio então elas são comparadas em relação ao tempo necessário
	 * para avaliação.
	 */
	@Override
	public int compareTo(Solve solve) {
		if (evaluated && solve.evaluated) {
			int cmp = Double.compare(percentualError, solve.percentualError);
			return cmp == 0 ? Long.compare(timeProcessed, solve.timeProcessed) : cmp;
		} else if (evaluated && !solve.evaluated) {
			return -1;
		} else if (!evaluated && solve.evaluated) {
			return 1;
		}
		return 0;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	/**
	 * Verifica se duas soluções são iguais.<br>
	 * Soluções não avaliadas são sempre iguais.<br>
	 * Se ambas foram avaliadas então elas são iguas se representarem o mesmo modelo com extamente os mesmos valores
	 * para todos os parâmetros.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Solve)) {
			return super.equals(obj);
		}

		Solve solve = (Solve) obj;
		if (evaluated != solve.evaluated) {
			return false;
		}

		return getParans().equals(solve.getParans());
	}

	// impressão no console
	@Override
	public String toString() {
		if (evaluated) {
			StringBuilder str = new StringBuilder(500);
			if(cvError != null){
				for(double value : cvError){
					str.append(String.format(Locale.ENGLISH, "%.4f", value));
					str.append(", ");
				}
				if(cvError.length > 0){
					str.deleteCharAt(str.length() -1);
					str.deleteCharAt(str.length() -1);
				}
			}
			
			return String.format("[%.4f, %d (ms), %s, %s]", percentualError, timeProcessed, getParans(), str.toString());
		} else {
			return "[-, -, -, -]";
		}
	}

	protected static int getNumBits(Model model) {
		int bits = 0;
		for (Entry<String, Parameter> entry : model.parameters.entrySet()) {
			bits += getNumBits(entry.getValue());
		}
		return bits;
	}

	protected static int getNumBits(Parameter parameter) {
		int size = parameter.interval.size();
		assert size > 0;
		int bits = size == 1 ? 0 : getNumBits(parameter.interval.size() - 1);
		if (parameter.subParameters != null) {
			int max = 0;
			for (Entry<String, Model> entry : parameter.subParameters.entrySet()) {
				int subNum = getNumBits(entry.getValue());
				max = Math.max(subNum, max);
			}
			bits += max;
		}
		return bits;
	}

	protected static int getNumBits(int value) {
		assert value >= 0;
		if (value == 0) {
			return 0;
		}
		return 1 + (int) (Math.log(value) / Math.log(2));
	}
}