package br.ufrn.imd.pbil.pde;

import weka.core.Instances;

public class ExecutorThread extends Thread {
	Solution s;
	Instances dataset;
	int folds;
	private boolean finish = false;
	private long initTime;
	
	private boolean isValid = false;
	
	public ExecutorThread () {
		initTime = System.currentTimeMillis(); // delay for start
	}

	public long getInitTime() { // in mili
		return initTime;
	}

	public void setInitTime(long initTime) {
		this.initTime = initTime;
	}

	public Solution getSolution() {
		return s;
	}
	
	public boolean isValid() {
		return isValid;
	}

	public ExecutorThread(Solution s, int folds, Instances dataset) {
		this.s = s;
		this.dataset = dataset;
		this.folds = folds;
	}

	public void run() {
		try {
			s.setMeanErrorPerFold((Executor.runSolution(dataset, s.getClassifier(), folds)));
			isValid = true;
		} catch (Exception e) {
			PbilWekaWorker.printErrorMessage(e, s.getPossibilityKeySet());
		}
		finish = true;
	}

	public boolean isFinish() {
		return finish;
	}
}
