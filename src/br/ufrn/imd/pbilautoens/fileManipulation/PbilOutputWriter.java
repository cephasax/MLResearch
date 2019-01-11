package br.ufrn.imd.pbilautoens.fileManipulation;

import java.io.IOException;

import br.ufrn.imd.pbilautoens.Solution;

public class PbilOutputWriter extends FileOutputWriter {

	public PbilOutputWriter(String partOfFileName) throws IOException {
		super(partOfFileName);
	}
	
	public void logDetailsAboutStep(String dataset, int generation) throws IOException{
		addContentline("");
		addContentline("------------------------------------------------------------------------");
		addContentline("Generation: " + generation + " for dataset: " + dataset);
		addContentline("------------------------------------------------------------------------");
		addContentline("");
		
		writeInFile();
	}
	
	public void logSolution(Solution s) {
		addContentline(s.toStringOnlyKey());
	}
	
	public void logSolutionAccuracyFirst(Solution s) {
		addContentline(s.toString());
	}
}
