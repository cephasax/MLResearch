package br.ufrn.imd.pbil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.LinkedHashMap;

/**
 * Efetua o parser de um arquivo de configura��es.<br><br>
 * Linhas iniciadas em # s�o ignoradas.<br>
 * Espa�os e tabula��es no in�cio e fim das linhas s�o ignorados {@link String#trim()};<br>
 * Os classificadores base devem ser listados antes dos comit�s ou n�o ser�o usados nos comit�s.<br>
 * Todas as declara��es devem ser de modelos conforme especificado em {@link Model}.
 * 
 * @author antonino
 *
 */
public class Configuration implements Serializable  {

	private static final long serialVersionUID = 1L;

	/** Modelos correspondentes aos comit�s. */
	public final LinkedHashMap<String, Model> ensembles;

	/** Modelos correspondentes aos classificadores base. */
	public final LinkedHashMap<String, Model> classifiers;

	/** Arquivo de onde as configura��es s�o recuperadas. */
	protected File file;

	/** Usado para indicar a linha em que ocorreu um erro na leitura das configura��es. */
	private int currentLine;

	/**
	 * Cria as configura��es a partir dos dados de um arquivo.<br>
	 * Espa�os e tabula��es no in�cio e fim das linhas s�o ignorados {@link String#trim()};
	 * @param file Arquivo de onde as configura��es ser�o extra�das.
	 * @throws ParseException Se houver algum erro de sintaxe ou sem�ntica nas declara��es contidas no arquivo.
	 * @throws IOException Em caso de IO erros.
	 */
	public Configuration(File file) throws IOException, ParseException {
		currentLine = 0;
		this.file = file;
		ensembles = new LinkedHashMap<>(30);
		classifiers = new LinkedHashMap<>(30);

		BufferedReader reader = new BufferedReader(new FileReader(file)) {
			@Override
			public String readLine() throws IOException {
				currentLine++;
				String line = super.readLine();
				if (line != null) {
					line = line.trim();
					while (line != null && (line.length() == 0 || line.startsWith("#"))) {
						line = super.readLine();
						currentLine++;
					}
				}
				return line;
			}
		};

		try {
			Model model = Model.parse(reader, classifiers);
			while (model != null) {
				if (model.isEnsemble) {
					ensembles.put(model.name, model);
				} else {
					classifiers.put(model.name, model);
				}
				model = Model.parse(reader, classifiers);
			}
		} catch (ParseException e) {
			String msg = "Line " + currentLine + "\n" + e.getMessage();
			throw new ParseException(msg, e.getErrorOffset());
		}

		reader.close();
	}
}
