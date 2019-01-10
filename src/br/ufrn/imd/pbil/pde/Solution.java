package br.ufrn.imd.pbil.pde;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import weka.classifiers.Classifier;

public class Solution implements Comparable<Solution> {

	private String name;
	private PossibilityKeySet possibilityKeySet;
	private float meanError;
	private Classifier classifier;
	private List<Float> minErrorPerFold;
	private boolean goodSolution;

	public Solution(PossibilityKeySet possibilityKeySet) {
		this.possibilityKeySet = possibilityKeySet;
		this.name = possibilityKeySet.toString();
		this.meanError = new Float(0.0);
		minErrorPerFold = new ArrayList<Float>();
		this.goodSolution = true;
	}

	public List<Float> getMinErrorPerFold() {
		return minErrorPerFold;
	}

	public void setMeanErrorPerFold(List<Float> minErrorPerFold) {
		float qtdFolds =0, sum=0;
		for (Float foldMinError : minErrorPerFold) {
			sum+=foldMinError;
			qtdFolds++;
		}
		this.meanError = sum/(float)qtdFolds; 
		this.minErrorPerFold = minErrorPerFold;
	}

	public Solution() {
		this.possibilityKeySet = null;
		this.name = null;
		this.meanError = new Float(0.0);
		this.goodSolution = true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PossibilityKeySet getPossibilityKeySet() {
		return possibilityKeySet;
	}

	public void setPossibilityKeySet(PossibilityKeySet possibilityKeySet) {
		this.possibilityKeySet = possibilityKeySet;
	}

	public float getAccuracy() {
		return meanError;
	}

	public void setAccuracy(float meanError) {
		this.meanError = meanError;
	}

	public Classifier getClassifier() {
		return classifier;
	}

	public void setClassifier(Classifier classifier) {
		this.classifier = classifier;
	}

	
	public float getMeanError() {
		return meanError;
	}

	public void setMeanError(float meanError) {
		this.meanError = meanError;
	}

	public boolean isGoodSolution() {
		return goodSolution;
	}

	public void setGoodSolution(boolean goodSolution) {
		this.goodSolution = goodSolution;
	}

	public void setMinErrorPerFold(List<Float> minErrorPerFold) {
		this.minErrorPerFold = minErrorPerFold;
	}

	@Override
	public int compareTo(Solution solution) {
		return (int) (this.meanError - solution.getAccuracy());
	}

	public static Comparator<Solution> meanErrorComparator = new Comparator<Solution>() {

		@Override
		public int compare(Solution s1, Solution s2) {
			return (int) ((s1.getAccuracy() * 100) - (s2.getAccuracy() * 100));
		}
	};

	public String toStringOnlyKey() {
		return "Solution [" + possibilityKeySet.toString() + "]";
	}

	@Override
	public String toString() {
		return "Solution [meanError=" + meanError + ", name=" + name + "]";
	}

}