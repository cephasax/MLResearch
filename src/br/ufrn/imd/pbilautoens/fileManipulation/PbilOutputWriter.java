package br.ufrn.imd.pbilautoens.fileManipulation;

import java.io.IOException;

import br.ufrn.imd.pbilautoens.Solution;

public class PbilOutputWriter extends FileOutputWriter {

	private StringBuilder sb;
	
	public PbilOutputWriter(String partOfFileName) throws IOException {
		super(partOfFileName);
		sb = new StringBuilder();
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
	
	public void printLine(String string) {
		System.out.println(string);
	}
	
	public void appendContentLine(String string) {
		sb.append(string);
		sb.append("\n");
	}
	
	public void appendContent(String string) {
		sb.append(string);
		sb.append(" ");
	}
	
	public void write() {
		System.out.println(sb.toString());
		this.sb = new StringBuilder();
	}
	
	public void outputDatasetInfo(String dataset){
		appendContentLine("");
		appendContentLine("------------------------------------------------------------------------");
		appendContentLine("Dataset: " + dataset);
		appendContentLine("------------------------------------------------------------------------");
		appendContentLine("");
		write();
	}
	
	public void outputBestSolutionInfo(int i, String bestSolution){
		appendContentLine("------------------------------------------------------------------------");
		appendContentLine("GENERATION: " + i + " - BEST SOLUTION: " + bestSolution);
		appendContentLine("------------------------------------------------------------------------");
		write();
	}
}
