package br.ufrn.imd.pbil.fileManipulation;

import java.io.File;
import javax.swing.filechooser.*;

public class ExtensionFilter extends FileFilter {

	// Accept all directories, ARFF and MODEL files.
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String extension = Utils.getExtension(f);
		if (extension != null) {
			if (extension.equals(Utils.arff)) {
				return true;
			} 
			else {
				return false;
			}
		}
		return false;
	}

	// The description of this filter
	public String getDescription() {
		return "Only types listed in Utils can be openned";
	}
}