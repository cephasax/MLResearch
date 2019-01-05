package br.ufrn.imd.pbil.fileManipulation;

import java.io.IOException;
import java.util.ArrayList;

import br.ufrn.imd.zeca.accuracy.domain.ResultFromMethod;

public class FileOutputWriterCatchAccuracy extends FileOutputWriter{

	public FileOutputWriterCatchAccuracy(String partOfFileName) {
		super(partOfFileName);
	}

	public void writeFile(ArrayList<ResultFromMethod> results) throws IOException {
		for(ResultFromMethod r: results) {
			toText.append(r.toString() + "\n");

		}
		writeAndSaveFile();
	}
	
	
}
