package weka.classifiers.meta;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import br.ufrn.imd.Measures;
import br.ufrn.imd.pbil.Configuration;
import br.ufrn.imd.pbil.Model;
import br.ufrn.imd.pbil.Solve;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.TechnicalInformation;
import weka.core.TechnicalInformation.Field;
import weka.core.TechnicalInformation.Type;
import weka.core.Utils;
import weka.gui.GUIChooser;

/**
 * Representa um classificador que efetua a otimização de hyperparameters.<br>
 * <br>
 * Dada uma configuração de vários {@link Model} o algoritmo efetua um processo de otimização através do algoritmo
 * Population Based Incremental Learning para determinar o modelo e os valores dos parâmetros desse modelo que minimiza
 * erro percentual médio de uma base de dados fornecida.
 * 
 * @author antonino
 * @version 1.2
 *
 */
public class PBIL_Auto_Ens_V1 extends AbstractClassifier {
	
	/**
	 * Main method for testing this class.
	 *
	 * @param argv
	 *        should contain command line options (see setOptions)
	 */
	public static void main(String[] argv) {
		GUIChooser.main(argv);
		//runClassifier(new PBIL_Auto_Ens_V1(), argv);
	}

	private static final long serialVersionUID = 1L;

	protected int population;
	protected int generations;
	protected int maxMinutes;
	protected int seed;
	protected int numBestSolves;
	protected int numSamplesUpdate;
	protected double learningRate;
	protected int numFolds;
	protected boolean stratify;
	protected String configuration;
	protected Classifier classifier;
	protected List<Solve> bestSet;
	protected int maxSecondsBySolveEvaluation;
	private double timeProcessed;
	private int performedSteps;

	public PBIL_Auto_Ens_V1() {
		seed = 0;												// seed
		population = 30;										// population
		maxMinutes = 15;										// tempo de execução
		generations = 20;										// no. gerações
		numBestSolves = 1;										// no. de soluções
		learningRate = 0.1;										// taxa de aprendizagem
		numSamplesUpdate = 15;									// tamanho do vetor de melhores individuos
		numFolds = 10;											// no. de folds do CV
		stratify = false;										// estratificação da base (false - defaut)
		bestSet = new ArrayList<>();							// melhor solução encontrada
		maxSecondsBySolveEvaluation = 15; 						// quantidade máxima de segundos para avaliação de uma única solução, 
																// solução é inválida se ultrapssar esse limite
		configuration = "resources/pbil-parameters.txt";
	}

	@Override
	public double classifyInstance(Instance instance) throws Exception {
		return classifier.classifyInstance(instance);
	}

	@Override
	public double[] distributionForInstance(Instance instance) throws Exception {
		return classifier.distributionForInstance(instance);
	}

	/**
	 * <p>
	 * Efetua o processo de otimização selecionando o melhor modelo e depois treina o mesmo.<br>
	 * A base de dados fornecida é randomizada e dividida em k-folds para avaliação por cross-validation.
	 * A quantidade de folds e se a base é estratificada ou é controlada pela opções do algoritmo.<br>
	 * O algoritmo corresponde ao descrito em Population-Based Incremental Learning 1994 com a modificação do critério
	 * de parada.<br>
	 * Além da quantidade de iterações, o tempo de execução é considerado e a cada iteração é verificado se todas as
	 * soluções convergiram para uma mesma solução, se isso for verificado então o processo é interrompido.<br>
	 * Ao final do algoritmo temos a melhor ou melhores soluções encontrada durante todo o processo de otimização.<br>
	 * O modelo correspondente a melhor solução é treinado com todas as instâncias fornecidas e é utilizado para
	 * classificadores futuras.<br>
	 * Nenhuma modificação é efetuada sobre a base de dados fornecida como argumento e é utilizada ao menos uma
	 * instâncias para treinar e uma instância para testar.<br>
	 * Não há sobreposição de instâncias entre a base de treino e a base de teste.
	 * </p>
	 */

	@Override
	public void buildClassifier(Instances data) throws Exception {

		classifier = null;
		bestSet.clear();
		timeProcessed = System.currentTimeMillis();

		Random rand = new Random(seed);

		Instances training = new Instances(data);
		training.randomize(rand);
		if(stratify)
			training.stratify(numFolds);

		File fileConfiguration = new File(getConfiguration());
		if (!fileConfiguration.exists() || !fileConfiguration.canRead()) {
			throw new IllegalArgumentException("File configuration \"" + getConfiguration() + "\" can not exists or can not be read.");
		}

		Configuration configuration = new Configuration(fileConfiguration);

		Solve model = new Solve(configuration);
		double[] probabilities = new double[model.encode.length];
		Arrays.fill(probabilities, 0.5);

		bestSet = new ArrayList<>(getNumBestSolves() * 2);
		bestSet.add(model);

		Solve[] population = new Solve[getPopulation()];
		// forma os indivíduos
		// System.out.println("... " + getPopulation());
		
		for (int i = 0; i < getPopulation(); i++) {
			population[i] = new Solve(configuration);
			population[i].randomize(rand);
		}

		long maxMinutes = this.maxMinutes * 60000;
		
		System.out.println("-------------------------------------------------------");
		System.out.println("Dataset: " + data.relationName());
		
		for (performedSteps = 0; performedSteps < getGenerations() && (System.currentTimeMillis() - timeProcessed < maxMinutes); performedSteps++) {
			System.out.println("-------------------------------------------------------");
			System.out.println("Generation " + (performedSteps + 1)); // TODO
			System.out.println("-------------------------------------------------------"); // TODO
			
			int num_evaluated_solves = 0;
			while(num_evaluated_solves < population.length){
				Solve solve = population[num_evaluated_solves];
				for (int j = 0; j < solve.encode.length; j++) {
					solve.encode[j] = rand.nextDouble() < probabilities[j];
				}
				// se solução inválida?
				if (!solve.isValid()){
					// repara solução no Solve
					solve.repair(rand);
				}	

				assert solve.isValid() : solve.getParans();
				evaluateSolveWithTimeout(solve, new Instances(training), numFolds);
				
				if(solve.evaluated){
					num_evaluated_solves++;
					System.out.println(num_evaluated_solves + ": " + solve); // TODO
				}
					
				if ((System.currentTimeMillis() - timeProcessed >= maxMinutes)) {
					break;
				}
			}

			Arrays.sort(population);
			updateBestSolves(population, bestSet);

			for (int j = 0; j < getNumSamplesUpdate(); j++) {
				Solve solve = population[j];
				for (int k = 0; k < probabilities.length; k++) {
					probabilities[k] = probabilities[k] * (1.0 - getLearningRate()) + (solve.encode[k] ? getLearningRate() : 0);
				}
			}

			if (population[0].evaluated && population[population.length - 1].evaluated && population[0].equals(population[population.length - 1])) {
				// if sorted array then all solves are equals
				break;
			}
		}

		PrintStream out = System.out;
		System.setOut(new PrintStream(new OutputStream() {
			public void write(int b) throws IOException {}
		}));
		try {
			classifier = bestSet.get(0).getClassifier();
			classifier.buildClassifier(data);
			timeProcessed = (System.currentTimeMillis() - timeProcessed) / 60000.0;
		} catch (Throwable e) {
			System.setOut(out);
			throw e;
		}
		System.setOut(out);
		
		System.out.println();
		System.out.println(this);
	}

	private void evaluateSolveWithTimeout(Solve solve, Instances train, int numFolds) {
		PrintStream out = System.out;
		System.setOut(new PrintStream(new OutputStream() {
			public void write(int b) throws IOException {}
		}));
		
		Thread thread = new Thread(){
			public void run(){
				try {
					solve.evaluate(train, numFolds);
				} catch(Throwable e){}
			}
		};
		thread.start();
		
		long timeout = (long) Math.min(maxSecondsBySolveEvaluation * 1000, maxMinutes * 60000 - (System.currentTimeMillis() - timeProcessed));
		timeout = Math.max(timeout, 0);
		
		try {
			thread.join(timeout);
		} catch (InterruptedException e) {}
		
		thread.stop();
		
		System.setOut(out);
	}

	// bestSet is not empty
	private void updateBestSolves(Solve[] sortedPopulation, List<Solve> bestSet) {
		int index = 0;
		for (Solve solve : sortedPopulation) {
			while (index < bestSet.size() && bestSet.get(index).compareTo(solve) <= 0) {
				index++;
			}
			if (index < getNumBestSolves()) {
				bestSet.add(index, solve.clone());
				if (bestSet.size() > getNumBestSolves()) {
					bestSet.remove(bestSet.size() - 1);
				}
			}
		}
	}
	
	public List<Solve> getBestSolves() {
		return bestSet;
	}

	/**
	 * Recupera o tempo total utilizado no processo de otimização mais o tempo necessário para treinar o modelo
	 * escolhido.
	 * @return O tempo utilziado em ms.
	 */
	public double getPerformedTime() {
		return timeProcessed;
	}

	/**
	 * Recupera a quantiade de iterações utilizadas no processo de otimização.
	 * @return A quantidade de iterações.
	 */
	public int getPerformedGenerations() {
		return performedSteps;
	}

	/**
	 * Returns a string describing classifier.
	 * 
	 * @return a description suitable for displaying in the
	 *         explorer/experimenter gui
	 */
	public String globalInfo() {
		return "Population-Based Incremental Learning:" + " A Method for Integrating Genetic Search Based Function Optimization and Competitive Learning." + " Optimization method applied to select the best ensemble model for the current base." + "\n\nFor more information, see\n\n" + getTechnicalInformation().toString();
	}

	/**
	 * Returns an instance of a TechnicalInformation object, containing detailed
	 * information about the technical background of this class, e.g., paper
	 * reference or book this class is based on.
	 * 
	 * @return the technical information about this class
	 */
	public TechnicalInformation getTechnicalInformation() {
		TechnicalInformation result1 = new TechnicalInformation(Type.TECHREPORT);
		result1.setValue(Field.AUTHOR, "Shumeet Baluja");
		result1.setValue(Field.YEAR, "1994");
		result1.setValue(Field.TITLE, "Population-Based Incremental Learning");
		result1.setValue(Field.INSTITUTION, "Carnegie Mellon University (CMU–CS–94–163)");
		result1.setValue(Field.LOCATION, "Pittsburgh, PA");

		TechnicalInformation result2 = new TechnicalInformation(Type.INPROCEEDINGS);
		result2.setValue(Field.AUTHOR, "Shumeet Baluja and Rich Caruana");
		result2.setValue(Field.YEAR, "1995");
		result2.setValue(Field.TITLE, "Removing the Genetics from the Standard Genetic Algorithm");
		result2.setValue(Field.PUBLISHER, "Morgan Kaufmann Publishers");
		result2.setValue(Field.PAGES, "38--46");

		result1.add(result2);

		return result1;
	}
	
	public String timeLimitBySolveInSecondsTipText() {
		return "The maximum seconds to evaluate a single solve.";
	}

	public void setTimeLimitBySolveInSeconds(int seconds) {
		assert seconds >= 0;
		this.maxSecondsBySolveEvaluation = seconds;
	}

	public int getTimeLimitBySolveInSeconds() {
		return maxSecondsBySolveEvaluation;
	}

	/**
	 * Returns the tip text for this property.
	 * 
	 * @return tip text for this property suitable for displaying in the
	 *         explorer/experimenter gui
	 */
	public String samplesTipText() {
		return "The population size, number of samples to produce per generation.";
	}

	public void setPopulation(int samples) {
		this.population = samples;
	}

	public int getPopulation() {
		return population;
	}

	public String timeLimitTipText() {
		return "The maximum time in minutes for execution of PBIL. The execution may exceed that limit because of the training of the model selected by the PBIL.";
	}

	public void setTimeLimit(int maxMinutes) {
		this.maxMinutes = maxMinutes;
	}

	public int getTimeLimit() {
		return maxMinutes;
	}

	public String numSamplesUpdateTipText() {
		return "The number of vectors in the current population which are used to update the probability vector.";
	}

	public void setNumSamplesUpdate(int update) {
		this.numSamplesUpdate = update;
	}

	public int getNumSamplesUpdate() {
		return numSamplesUpdate;
	}

	public String seedTipText() {
		return "Seed for generation of pseudorandom numbers.";
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}

	public int getSeed() {
		return seed;
	}

	public String numBestSolvesTipText() {
		return "The number of best solves to be reported.";
	}

	public void setNumBestSolves(int numBestSolves) {
		this.numBestSolves = numBestSolves;
	}

	public int getNumBestSolves() {
		return numBestSolves;
	}

	public String numFoldsTipText() {
		return "Number of folds used for cross validation applied in each model evaluation.";
	}
	
	public void setNumFolds(int numFolds) {
		this.numFolds = numFolds;
	}
	
	public int getNumFolds() {
		return numFolds;
	}
	public String stratifyInstancesTipText() {
		return "Stratify the instances used in the model evaluation";
	}

	public void setStratify(boolean stratfy) {
		this.stratify = stratfy;
	}

	public boolean getStratify() {
		return stratify;
	}

	public String learningRateTipText() {
		return "The learning rate.";
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	public double getLearningRate() {
		return learningRate;
	}

	public String generationsTipText() {
		return "Number of iterations to allow learning.";
	}

	public void setGenerations(int generations) {
		this.generations = generations;
	}

	public int getGenerations() {
		return generations;
	}

	public String configurationTipText() {
		return "Path to file with configuration of allowed ensembles, classifiers and applicable parameters for each one.";
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public String getConfiguration() {
		return configuration;
	}

	/**
	 * All capabilities are enabled.
	 * The capabilities are loaded dinamically to the current model in optimization process.
	 *
	 * @return the capabilities of this classifier
	 */
	public Capabilities getCapabilities() {
		Capabilities result = super.getCapabilities();
		result.enableAll();

		// instances
		result.setMinimumNumberInstances(1);

		return result;
	}

	/**
	 * Returns an enumeration describing the available options.
	 *
	 * @return an enumeration of all the available options.
	 */
	public Enumeration<Option> listOptions() {

		Vector<Option> newVector = new Vector<Option>(9);
		// -N
		newVector.addElement(new Option("\tThe population size, number of samples to produce per generation.\n" 
		+ "\t(Default = 30)", "N", 1, "-N <number of samples>"));
		
		// -U
		newVector.addElement(new Option("\tThe number of vectors in the current population which are used to update the probability vector.\n" 
		+ "\t(Default = 15)", "U", 1, "-U <number of samples>"));
		
		// -L
		newVector.addElement(new Option("\tThe learning rate.\n" + "\t(Default = 0.1)", "L", 1, "-L <learning rate>"));
		
		// -I
		newVector.addElement(new Option("\tNumber of iterations to allow learning. Use 0 for ilimited iterations (in this case set the maximum minutes).\n" 
		+ "\t(Default = 20)", "I", 1, "-I <number of samples>"));
		
		// -S
		newVector.addElement(new Option("\tSeed for generation of pseudo-random numbers. Must be natural a value.\n" 
		+ "\t(Default = 0)", "S", 1, "-S <value>"));
		
		
		// -R
		newVector.addElement(new Option("\tThe number of best solves to be reported. Must be bigger than 0. "
				+ "The output will have util the specified number of best solves.\n" + "\t(Default = 1)", "R", 1, "-R <value>"));
		
		// -M
		newVector.addElement(new Option("\tThe maximum seconds to evaluate a single solve. Must be natural a value.\n" 
		+ "\t(Default = 15 secs)", "M", 1, "-M <value>"));
		
		
		// -T
		newVector.addElement(new Option("\tThe maximum time in ms for execution of PBIL. The execution may exceed that limit because of "
				+ "the training of the model selected by the PBIL. Use 0 for ilimited time (in this case set the generations).\n" 
				+ "\t(Default = 15 min)", "T", 1, "-T <minutes>"));
		
		// -C
		newVector.addElement(new Option("\tPath to file with configuration of allowed ensembles, classifiers and applicable parameters for each one.\n" 
		+ "\t(Default = ./resources/pbil-parameters.txt)", "C", 1, "-C <file>"));

		newVector.addAll(Collections.list(super.listOptions()));

		return newVector.elements();
	}

	/**
	 * Parses a given list of options.
	 * <p>
	 * <!-- options-start --> Valid options are:
	 * </p>
	 * 
	 * <pre>
	 *  -N
	 *  The population size, number of samples to produce per generation.
	 *  (default = 30 and -S &gt; 0)
	 * </pre>
	 * 
	 * <pre>
	 *  -T
	 * The maximum time in minutes for execution of PBIL. The execution may exceed that limit because of the training of the model selected by the PBIL.
	 * (Default = 5 min)
	 * </pre>
	 * 
	 * <pre>
	 *  -U
	 *  The number of vectors in the current population which are used to update the probability vector.
	 *  (default = 15 and -U &gt; 0)
	 * </pre>
	 * 
	 * <pre>
	 *  -L
	 *  The learning rate.
	 *  (default = 0.1 and -L &gt; 0)
	 * </pre>
	 * 
	 * <pre>
	 *  -I
	 *  Number of iterations to allow learning.
	 *  (default = 500 and -I &gt; 0)
	 * </pre>
	 * 
	 * <pre>
	 *  -M
	 *  The maximum seconds to evaluate a single solve. Must be natural a value.
	 *  (default = 75 and -I &gt; 0)
	 * </pre>
	 * 
	 * <pre>
	 *  -C
	 *  Path to file with configuration of allowed ensembles, classifiers and applicable parameters for each one
	 *  (Default = ./resources/pbil-parameters.txt and the file exists)
	 * </pre>
	 * 
	 * <pre>
	 *  -cv
	 *  The number of folds used in each model evaluation.
	 *  (Default = 10)
	 * </pre>
	 * 
	 * <pre>
	 *  -stratify
	 *  Stratify instances used in evaluation.
	 *  (Default = false)
	 * </pre>
	 * 
	 * <pre>
	 *  -S
	 *  Seed for generation of pseudo-random numbers. Must be natural a value.
	 *  (Default = 0)
	 * </pre>
	 * 
	 * <pre>
	 *  -R
	 *  The number of best solves to be reported. Must be bigger than 0.
	 *  (Default = 1)
	 * </pre>
	 * 
	 * <!-- options-end -->
	 *
	 * @param options
	 *        the list of options as an array of strings
	 * @throws Exception
	 *         if an option is not supported
	 */
	public void setOptions(String[] options) throws Exception {

		String sampleString = Utils.getOption('N', options);
		if (sampleString.length() != 0) {
			int samples = Integer.parseInt(sampleString);
			if (samples <= 0) {
				throw new Exception("Invalid number of samples " + samples + ". Number of samples must be greater than 0.");
			} else {
				setPopulation(samples);
			}
		} else {
			setPopulation(30);
		}

		String timeString = Utils.getOption('T', options);
		if (timeString.length() != 0) {
			int time = Integer.parseInt(timeString);
			if (time < 0) {
				throw new Exception("Invalid maximum minutes " + population + ". Maximum minutes must be a natural value.");
			} else {
				setTimeLimit(time);
			}
		} else {
			setTimeLimit(15);
		}

		String updateString = Utils.getOption('U', options);
		if (updateString.length() != 0) {
			int samples = Integer.parseInt(updateString);
			if (samples <= 0) {
				throw new Exception("Invalid number of update samples " + samples + ". Number of update samples must be greater than 0 and minor than number of samples " + getPopulation() + ".");
			} else {
				setNumSamplesUpdate(samples);
			}
		} else {
			setNumSamplesUpdate(Math.max(1, getPopulation() / 2));
		}

		String learningRateString = Utils.getOption('L', options);
		if (learningRateString.length() != 0) {
			double learningRate = Double.parseDouble(learningRateString);
			if (learningRate <= 0) {
				throw new Exception("Invalid learning rate " + learningRate + ". Learning rate must be greater than 0.");
			} else {
				setLearningRate(learningRate);
			}
		} else {
			setLearningRate(0.1);
		}

		String generationsString = Utils.getOption('I', options);
		if (generationsString.length() != 0) {
			int generations = Integer.parseInt(generationsString);
			if (generations < 0) {
				throw new Exception("Invalid number of generations " + generations + ". Number of generations must be a natural value.");
			} else {
				setGenerations(generations);
			}
		} else {
			setGenerations(20);
		}

		String configurationString = Utils.getOption('C', options);
		if (configurationString.length() != 0) {
			setConfiguration("");
			File conf = new File(configurationString);
			if (!conf.exists()) {
				throw new Exception("The File " + configurationString + " does not exist.");
			} else if (!conf.canRead()) {
				throw new Exception("The File " + configurationString + " can not be read.");
			} else {
				setConfiguration(configurationString);
			}
		} else {
			throw new Exception("Invalid Configuration File.");
		}

		String numFoldsString = Utils.getOption("cv", options);
		if (numFoldsString.length() != 0) {
			int numFolds = Integer.parseInt(numFoldsString);
			if (numFolds <= 1) {
				throw new Exception("Invalid number of folds "+ numFolds +". Must be greater than 1.");
			} else {
				setNumFolds(numFolds);
			}
		} else {
			setNumFolds(10);
		}
		
		setStratify(Utils.getFlag("stratify", options));

		String sampleSeed = Utils.getOption('S', options);
		if (sampleSeed.length() != 0) {
			int seed = Integer.parseInt(sampleSeed);
			if (seed < 0) {
				throw new Exception("Invalid value of seed " + seed + ". Value of seed must be a natural value (value >= 0).");
			} else {
				setSeed(seed);
			}
		} else {
			setSeed(0);
		}
		
		String sampleMaxSecondsEvaluation = Utils.getOption('M', options);
		if (sampleMaxSecondsEvaluation.length() != 0) {
			int maxSeconds = Integer.parseInt(sampleMaxSecondsEvaluation);
			if (maxSeconds < 0) {
				throw new Exception("Invalid value of max seconds by solve evaluation " + maxSeconds + ". Value of seed must be a natural value (value >= 0).");
			} else {
				setTimeLimitBySolveInSeconds(maxSeconds);
			}
		} else {
			setTimeLimitBySolveInSeconds(15);
		}

		String sampleOutput = Utils.getOption('R', options);
		if (sampleOutput.length() != 0) {
			int output = Integer.parseInt(sampleOutput);
			if (output <= 0) {
				throw new Exception("Invalid number of best solves " + output + ". Number of best solves must be greater than 0.");
			} else {
				setNumBestSolves(output);
			}
		} else {
			setNumBestSolves(1);
		}

		if (getGenerations() == 0 && getTimeLimit() == 0) {
			setGenerations(20);
			setTimeLimit(15);
			throw new Exception("Invalid stop condition, the maximum minutes or the number of generations must be bigger than 0.");
		}

		super.setOptions(options);

		Utils.checkForRemainingOptions(options);
	}

	/**
	 * Gets the current settings of IBk.
	 *
	 * @return an array of strings suitable for passing to setOptions()
	 */
	public String[] getOptions() {
		Vector<String> options = new Vector<String>();
		options.add("-N");
		options.add(Integer.toString(getPopulation()));
		options.add("-U");
		options.add(Integer.toString(getNumSamplesUpdate()));
		options.add("-L");
		options.add(Double.toString(getLearningRate()));
		options.add("-I");
		options.add(Integer.toString(getGenerations()));
		options.add("-T");
		options.add(Integer.toString(getTimeLimit()));
		options.add("-R");
		options.add(Integer.toString(getNumBestSolves()));
		options.add("-S");
		options.add(Integer.toString(getSeed()));
		options.add("-M");
		options.add(Integer.toString(getTimeLimitBySolveInSeconds()));
		
		options.add("-cv");
		options.add(Integer.toString(getNumFolds()));
		if(getStratify()){
			options.add("-stratify");
		}
		
		if (configuration.length() > 0) {
			options.add("-C");
			options.add(configuration);
		}
		
		Collections.addAll(options, super.getOptions());
		return options.toArray(new String[0]);
	}

	public String toString() {
		if (classifier == null) {
			return "PBIL: No model built yet.";
		}

		StringBuilder str = new StringBuilder("Population-Based Incremental Learning\n");
		str.append("\tPopulation:                     " + getPopulation() + "\n");
		str.append("\tLearning Rate:                  " + getLearningRate() + "\n");
		str.append("\tSamples Used for Update:        " + getNumSamplesUpdate() + "\n");
		str.append("\tGenerations:                    " + getGenerations() + "\n");
		str.append("\tMaximum Time (m):               " + getTimeLimit() + "\n");
		str.append("\tMax Seconds By Sove Evaluation: " + getTimeLimitBySolveInSeconds() + "\n");
		str.append("\tSeed:                           " + getSeed() + "\n");
		str.append("\tNum Folds for Cross-Validation: " + getNumFolds() + "\n");
		str.append("\tStratify Instances:             " + getStratify() + "\n");
		str.append("\tConfiguration File:             " + getConfiguration() + " \n");
		str.append("\tNumber of Best Solves:          " + getNumBestSolves() + "\n");
		str.append(String.format("\n\tTime Processed (m): %.4f", timeProcessed));
		str.append("\n\tPerfomed Steps: " + (performedSteps + 1) + " generations\n\n");
		str.append("\tBest Solves:\n");
		for (Solve solve : bestSet) {
			str.append("\t\t" + solve + "\n");
		}
		str.append("\nCurrent Model: \"" + bestSet.get(0).getParans() + "\"\n\n");

		return str.toString();
	}
}