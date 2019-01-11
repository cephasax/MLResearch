
package weka.classifiers.meta;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import br.ufrn.imd.pbilautoens.Pbil;
import br.ufrn.imd.pbilautoens.Solution;
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
import weka.gui.explorer.Explorer;

/**
 * Representa um classificador que efetua a otimiza��o de hyperparameters.<br>
 * <br>
 * Dada uma configura��o de v�rios {@link Model} o algoritmo efetua um processo de otimiza��o atrav�s do algoritmo
 * Population Based Incremental Learning para determinar o modelo e os valores dos par�metros desse modelo que minimiza
 * erro percentual m�dio de uma base de dados fornecida.
 * 
 * @author antonino
 *
 */
public class PbilAutoEns extends AbstractClassifier {

	/**
	 * Main method for testing this class.
	 *
	 * @param argv
	 *             should contain command line options (see setOptions)
	 */
	public static void main(String[] argv) {
		Explorer.main(argv);
		// runClassifier(new PopulationBasedIncrementalLearning(), argv);
	}

	private static final long serialVersionUID = 1L;

	protected int population;
	protected int generations;
	protected int maxMinutes;
	protected int maxSecondsBySolve;
	protected int seed;
	protected int numBestSolves;
	protected int numSamplesUpdate;
	protected double learningRate;
	protected int numFolds;
	protected boolean stratify;
	protected Classifier classifier;
	protected List<Solution> bestSet;
	private double timeProcessed;
	private int performedSteps;

	public PbilAutoEns() {
		seed = 0;						// seed
		population = 30;				// population
		maxMinutes = 15;				// tempo de execu��o
		maxSecondsBySolve = 5;
		generations = 100;				// no. gera��es
		numBestSolves = 1;				// no. de solu��es
		learningRate = 0.1;				// taxa de aprendizagem
		numSamplesUpdate = 15;			// tamanho do vetor de melhores individuos
		numFolds = 10;					// no. de folds do CV
		stratify = false;				// estratifica��o da base (false - defaut)
		bestSet = new ArrayList<>();
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
	 * Efetua o processo de otimiza��o selecionando o melhor modelo e depois treina o mesmo.<br>
	 * A base de dados fornecida � randomizada e dividida em k-folds para avalia��o por cross-validation.
	 * A quantidade de folds e se a base � estratificada ou � controlada pela op��es do algoritmo.<br>
	 * O algoritmo corresponde ao descrito em Population-Based Incremental Learning 1994 com a modifica��o do crit�rio
	 * de parada.<br>
	 * Al�m da quantidade de itera��es, o tempo de execu��o � considerado e a cada itera��o � verificado se todas as
	 * solu��es convergiram para uma mesma solu��o, se isso for verificado ent�o o processo � interrompido.<br>
	 * Ao final do algoritmo temos a melhor ou melhores solu��es encontrada durante todo o processo de otimiza��o.<br>
	 * O modelo correspondente a melhor solu��o � treinado com todas as inst�ncias fornecidas e � utilizado para
	 * classificadores futuras.<br>
	 * Nenhuma modifica��o � efetuada sobre a base de dados fornecida como argumento e � utilizada ao menos uma
	 * inst�ncias para treinar e uma inst�ncia para testar.<br>
	 * N�o h� sobreposi��o de inst�ncias entre a base de treino e a base de teste.
	 * </p>
	 */

	@Override
	public void buildClassifier(Instances data) throws Exception {

		if (stratify) {
			data.stratify(numFolds);
		}
		
		// TODO remove
		//population = 4;				// population
		//maxMinutes = 1;				// tempo de execu��o
		//numSamplesUpdate = 2;
		

		Pbil pbil = new Pbil();
		pbil.setPopulationSize(population);
		pbil.setMaxSecondsPerSolution(maxSecondsBySolve);
		pbil.setGenerations(generations);
		pbil.setLearningRate((float) learningRate);
		pbil.setUpdateReason(population / numSamplesUpdate);
		pbil.setNumFolds(numFolds);
		pbil.setInstances(data);
		pbil.setRandomSeed(seed);
		
		boolean[] status = { true };
		Thread run = new Thread() {
			public void run() {
				try {
					pbil.run();
				} catch (Exception e) {
					e.printStackTrace();
				}
				status[0] = false;
			}
		};
		
		long maxMinutes = this.maxMinutes * 60000;
		timeProcessed = System.currentTimeMillis();
		run.start();

		while (System.currentTimeMillis() - timeProcessed < maxMinutes && status[0]) {
			Thread.sleep(100);
		}
		if (status[0]) {
			run.stop();
		}
		performedSteps = pbil.getPerformedSteps();
		bestSet.add(pbil.getBestSolution());

		PrintStream out = System.out;
		System.setOut(new PrintStream(new OutputStream() {
			public void write(int b) throws IOException {
			}
		}));
		try {
			classifier = pbil.getBestSolution().getClassifier();
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

	/**
	 * Recupera o tempo total utilizado no processo de otimiza��o mais o tempo necess�rio para treinar o modelo
	 * escolhido.
	 * 
	 * @return O tempo utilziado em ms.
	 */
	public double getPerformedTime() {
		return timeProcessed;
	}

	/**
	 * Recupera a quantiade de itera��es utilizadas no processo de otimiza��o.
	 * 
	 * @return A quantidade de itera��es.
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
		result1.setValue(Field.INSTITUTION, "Carnegie Mellon University (CMU�CS�94�163)");
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

	public String timeLimitBySolveInSecondsTipText() {
		return "The maximum time in seconds by solve evaluation. The execution may exceed that limit because of the training of the model selected by the PBIL.";
	}

	public void setTimeLimitBySolveInSeconds(int seconds) {
		this.maxSecondsBySolve = seconds;
	}

	public int getTimeLimitBySolveInSeconds() {
		return maxSecondsBySolve;
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
				+ "\t(Default = 500)", "I", 1, "-I <number of samples>"));

		// -C
		newVector.addElement(new Option("\tPath to file with configuration of allowed ensembles, classifiers and applicable parameters for each one.\n"
				+ "\t(Default = ./pbil-parameters.txt)", "C", 1, "-C <file>"));

		// -P
		newVector.addElement(new Option("\tThe percent of dataset used for training the PBIL. Value between 0 and 1 (exclusive values), i.e. the values "
				+ "must be bigger than 0 and minor than 1.\n"
				+ "\t(Default = 0.66 (If is 25% for validation then 0.66 results in 25% for test and 50% for training))", "P", 1, "-P <value>"));

		// -S
		newVector.addElement(new Option("\tSeed for generation of pseudo-random numbers. Must be natural a value.\n"
				+ "\t(Default = 0)", "S", 0, "-S <value>"));

		// -R
		newVector.addElement(new Option("\tThe number of best solves to be reported. Must be bigger than 0. "
				+ "The output will have util the specified number of best solves.\n" + "\t(Default = 1)", "R", 1, "-R <value>"));

		// -T
		newVector.addElement(new Option("\tThe maximum time in minutes for execution of PBIL. The execution may exceed that limit because of "
				+ "the training of the model selected by the PBIL. Use 0 for ilimited time (in this case set the generations).\n"
				+ "\t(Default = 5 min)", "T", 1, "-T <minutes>"));

		// -M
		newVector.addElement(new Option("\tThe maximum time in seconds for solve evaluation. The execution may exceed that limit because of "
				+ "the training of the model selected by the PBIL.\n"
				+ "\t(Default = 5 seconds)", "T", 1, "-M <seconds>"));

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
	 *  -C
	 *  Path to file with configuration of allowed ensembles, classifiers and applicable parameters for each one
	 *  (Default = ./pbil-parameters.txt and the file exists)
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
	 *                the list of options as an array of strings
	 * @throws Exception
	 *                   if an option is not supported
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
				throw new Exception("Invalid maximum minutes " + time + ". Maximum minutes must be a natural value.");
			} else {
				setTimeLimit(time);
			}
		} else {
			setTimeLimit(15);
		}

		String timeStringBySolve = Utils.getOption('M', options);
		if (timeStringBySolve.length() != 0) {
			int time = Integer.parseInt(timeString);
			if (time < 0) {
				throw new Exception("Invalid maximum seconds " + time + ". Maximum seconds must be a natural value.");
			} else {
				setTimeLimitBySolveInSeconds(time);
			}
		} else {
			setTimeLimitBySolveInSeconds(5);
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
			setGenerations(500);
		}

		String numFoldsString = Utils.getOption("cv", options);
		if (numFoldsString.length() != 0) {
			int numFolds = Integer.parseInt(numFoldsString);
			if (numFolds <= 1) {
				throw new Exception("Invalid number of folds " + numFolds + ". Must be greater than 1.");
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
			setGenerations(500);
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
		options.add("-cv");
		options.add(Integer.toString(getNumFolds()));
		if (getStratify()) {
			options.add("-stratify");
		}
		options.add("-S");
		options.add(Integer.toString(getSeed()));
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
		str.append("\tSeed:                           " + getSeed() + "\n");
		str.append("\tNum Folds for Cross-Validation: " + getNumFolds() + "\n");
		str.append("\tStratify Instances:             " + getStratify() + "\n");
		str.append("\tNumber of Best Solves:          " + getNumBestSolves() + "\n");
		str.append(String.format("\n\tTime Processed (m): %.4f", timeProcessed));
		str.append("\n\tPerfomed Steps: " + performedSteps + " generations\n\n");
		str.append("\tBest Solves:\n");
		for (Solution solve : bestSet) {
			str.append("\t\t" + solve + "\n");
		}
		str.append("\nCurrent Model: \"" + bestSet.get(0) + "\"\n\n");

		return str.toString();
	}
}
