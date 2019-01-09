package br.ufrn.imd.pbil.pde;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import weka.classifiers.Classifier;

public class Solution implements Comparable<Solution> {

	private String name;
	private PossibilityKeySet possibilityKeySet;
	private float accuracy;
	private Classifier classifier;
	private List<Float> minErrorPerFold;

	public Solution(PossibilityKeySet possibilityKeySet) {
		this.possibilityKeySet = possibilityKeySet;
		this.name = possibilityKeySet.toString();
		this.accuracy = new Float(0.0);
		minErrorPerFold = new ArrayList<Float>();
		
	}

	public List<Float> getMinErrorPerFold() {
		return minErrorPerFold;
	}

	public void setMinErrorPerFold(List<Float> minErrorPerFold) {
		float qtdFolds =0, sum=0;
		for (Float foldMinError : minErrorPerFold) {
			sum+=foldMinError;
			qtdFolds++;
		}
		this.accuracy = sum/(float)qtdFolds; 
		this.minErrorPerFold = minErrorPerFold;
	}

	public Solution() {
		this.possibilityKeySet = null;
		this.name = null;
		this.accuracy = new Float(0.0);
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
		return accuracy;
	}

	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}

	public Classifier getClassifier() {
		return classifier;
	}

	public void setClassifier(Classifier classifier) {
		this.classifier = classifier;
	}

	@Override
	public int compareTo(Solution solution) {
		return (int) (this.accuracy - solution.getAccuracy());
	}

	public static Comparator<Solution> AccuracyComparator = new Comparator<Solution>() {

		@Override
		public int compare(Solution s1, Solution s2) {
			return (int) ((s2.getAccuracy() * 100) - (s1.getAccuracy() * 100));
		}
	};

	public String toStringOnlyKey() {
		return "Solution [" + possibilityKeySet.toString() + "]";
	}

	@Override
	public String toString() {
		return "Solution [accuracy=" + accuracy + ", name=" + name + "]";
	}

}