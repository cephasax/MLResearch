package br.ufrn.imd.pbil.douglas;

import br.ufrn.imd.pbil.pde.Executor;
import br.ufrn.imd.pbil.pde.PbilWekaWorker;
import br.ufrn.imd.pbil.pde.Solution;
import weka.core.Instances;

public class ExecutorThread extends Thread {
	Solution s;
	Instances dataset;
	int folds;
	private boolean finish = false;
	private long initTime;
	
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

	public ExecutorThread(Solution s, int folds, Instances dataset) {
		this.s = s;
		this.dataset = dataset;
		this.folds = folds;
	}

	public void run() {
		try {
			s.setAccuracy(Executor.runSolution(dataset, s.getClassifier(), folds));
			System.out.println(s.getAccuracy() + " : " + s.getPossibilityKeySet().toString());
		} catch (Exception e) {
			PbilWekaWorker.printErrorMessage(e, s.getPossibilityKeySet());
		}
		finish = true;
	}

	public boolean isFinish() {
		return finish;
	}
}
