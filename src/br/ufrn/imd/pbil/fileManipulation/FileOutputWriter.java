package br.ufrn.imd.pbil.fileManipulation;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileOutputWriter {

	public StringBuilder toText;
	private String fileName;	
	FileOutputStream fos;
	
	public FileOutputWriter(String partOfFileName) throws IOException {
		toText = new StringBuilder();
		buildFileName(partOfFileName);
		this.fos = new FileOutputStream(fileName);
		this.fos.close();
	}
	
	private void buildFileName(String partOfFileName) {
		
		long date = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
	    Date resultdate = new Date(date);
	    StringBuilder sb = new StringBuilder();
	    
	    //compose filename
	    sb.append(partOfFileName);
	    sb.append("_");
	    sb.append(sdf.format(resultdate));
	    sb.append(".txt");
	    
	    //name will be like "partOfFileName_2017_12_21_21_05.txt"
	    fileName = new String(sb.toString());
	}

	public void addContentline(String line){
		toText.append(line);
		toText.append("\n");
	}
	
	public void addContentLines(List<String> lines) {
		for(String line: lines) {
			addContentline(line);
		}
	}
	
	public void writeInFile() throws IOException { 
		this.fos = new FileOutputStream(fileName, true);  
		String saida = new String(toText.toString());
		this.fos.write(saida.getBytes());
	    this.toText = new StringBuilder();
	}
	
	public void saveAndClose() throws IOException {
		this.fos.close();
	    System.out.println("Arquivo: " + fileName + " salvo!");
	}
	
}
