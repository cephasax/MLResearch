package br.ufrn.imd.pbilautoens.fileManipulation;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFileChooser;


/**
 * 
 * @author cephas
 *
 */
public class FileManager {

	private String urlBase;

	public FileManager() {
		urlBase = new String("");
	}

	public FileManager(String url) {
		urlBase = new String(url);
	}

	public File openOne(String windowTitle) {
		JFileChooser chooser = null;
		chooser = new JFileChooser(urlBase);
		chooser.setDialogTitle(windowTitle);
		chooser.addChoosableFileFilter(new ExtensionFilter());
		chooser.setMultiSelectionEnabled(false);
		File file = new File("");
		
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
		}
		
		return file;
	}
	
	public ArrayList<File> openOneOrMany(String windowTitle) {

		ArrayList<File> files = new ArrayList<File>();

		JFileChooser chooser = null;
		chooser = new JFileChooser(urlBase);
		chooser.setDialogTitle(windowTitle);
		chooser.addChoosableFileFilter(new ExtensionFilter());
		chooser.setMultiSelectionEnabled(true);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File[] fs = chooser.getSelectedFiles();
			files = new ArrayList<File>(Arrays.asList(fs));
		}

		return files;
	}

	public String getnameFromFile(String windowTitle) {
		JFileChooser chooser = null;
		chooser = new JFileChooser(urlBase);
		chooser.setDialogTitle(windowTitle);
		chooser.addChoosableFileFilter(new ExtensionFilter());
		chooser.setMultiSelectionEnabled(false);
		File file = new File("");
		
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
		}
	
		return file.getAbsolutePath();
	}
	
	public String getUrlBase() {
		return urlBase;
	}
	
	public void setUrlBase(String urlBase) {
		this.urlBase = urlBase;
	}
	
}
