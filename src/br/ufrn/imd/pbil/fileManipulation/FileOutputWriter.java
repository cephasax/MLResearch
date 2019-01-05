package br.ufrn.imd.pbil.fileManipulation;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileOutputWriter {

	public StringBuilder toText;
	private String fileName;	
	
	public FileOutputWriter(String partOfFileName) {
		toText = new StringBuilder();
		buildFileName(partOfFileName);
	}
	
	private void buildFileName(String partOfFileName) {
		
		long date = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH_mm");
	    Date resultdate = new Date(date);
	    StringBuilder sb = new StringBuilder();
	    
	    //compose filename
	    sb.append(partOfFileName.replace(".arff", ""));
	    sb.append("_");
	    sb.append(sdf.format(resultdate));
	    sb.append(".txt");
	    
	    //name will be like "<originalFileName>_23/04/2017.txt"
	    fileName = new String(sb.toString());
	}

	public void addContentline(String line){
		toText.append(line);
	}
	
	public void writeAndSaveFile() throws IOException {
		FileOutputStream fos = new FileOutputStream(fileName);    
		String saida = new String(toText.toString());
	    fos.write(saida.getBytes());  
	    fos.close();        
	    System.out.println("Arquivo " + fileName + " salvo!");
	}

}
