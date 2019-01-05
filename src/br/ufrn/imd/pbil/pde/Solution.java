package br.ufrn.imd.pbil.pde;

import java.util.Comparator;

public class Solution implements Comparable<Solution>{

	private String name;
	private PossibilityKeySet possibilityKeySet;
	private Double accuracy;

	public Solution(PossibilityKeySet possibilityKeySet) {
		this.possibilityKeySet = possibilityKeySet;
		this.name = possibilityKeySet.toString();
		this.accuracy = new Double(0.0);
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

	public Double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(Double accuracy) {
		this.accuracy = accuracy;
	}

	@Override
	public int compareTo(Solution solution) {
		return (int)(this.accuracy - solution.getAccuracy());
	}
	
	public static Comparator<Solution> AccuracyComparator = new Comparator<Solution>() {

        @Override
        public int compare(Solution s1, Solution s2) {
            return (int) (s1.getAccuracy() - s2.getAccuracy());
        }
        
        public Solution getBestSolutionFromAccuracy(Solution s1, Solution s2) {
           
        	double dif = s1.getAccuracy() - s2.getAccuracy();
        	if(dif >= 0) {
        	   return s1;
        	}
        	else {
        	   return s2;
        	}
        }
    };

}