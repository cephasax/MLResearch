package br.ufrn.imd.pbil.pde;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import weka.classifiers.Classifier;

public class Solution implements Comparable<Solution>, Serializable {

	private static final long serialVersionUID = 3262143841020467210L;
	
	private String name;
	private PossibilityKeySet possibilityKeySet;
	private float meanError;
	private Classifier classifier;
	private List<Float> minErrorPerFold;
	private boolean goodSolution;

	public Solution(PossibilityKeySet possibilityKeySet) {
		this.possibilityKeySet = possibilityKeySet;
		this.name = possibilityKeySet.toString();
		this.meanError = 1.0f;
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
		this.meanError = 1.0f;
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
		return (int) (this.meanError - solution.getMeanError());
	}

	public static Comparator<Solution> meanErrorComparator = new Comparator<Solution>() {

		@Override
		public int compare(Solution s1, Solution s2) {
			double x = s1.getMeanError() - s2.getMeanError();
			x = x * 100000;
			System.out.println("compare: s1.meanError=" + s1.getMeanError() + " - s2.meanError=" + s2.getMeanError() 
			+ " result = " + x);
			return (int)x;
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