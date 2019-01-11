package br.ufrn.imd.pbil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.LinkedHashMap;

/**
 * Efetua o parser de um arquivo de configurações.<br><br>
 * Linhas iniciadas em # são ignoradas.<br>
 * Espaços e tabulações no início e fim das linhas são ignorados {@link String#trim()};<br>
 * Os classificadores base devem ser listados antes dos comitês ou não serão usados nos comitês.<br>
 * Todas as declarações devem ser de modelos conforme especificado em {@link Model}.
 * 
 * @author antonino
 *
 */
public class Configuration implements Serializable  {

	private static final long serialVersionUID = 1L;

	/** Modelos correspondentes aos comitês. */
	public final LinkedHashMap<String, Model> ensembles;

	/** Modelos correspondentes aos classificadores base. */
	public final LinkedHashMap<String, Model> classifiers;

	/** Arquivo de onde as configurações são recuperadas. */
	protected File file;

	/** Usado para indicar a linha em que ocorreu um erro na leitura das configurações. */
	private int currentLine;

	/**
	 * Cria as configurações a partir dos dados de um arquivo.<br>
	 * Espaços e tabulações no início e fim das linhas são ignorados {@link String#trim()};
	 * @param file Arquivo de onde as configurações serão extraídas.
	 * @throws ParseException Se houver algum erro de sintaxe ou semântica nas declarações contidas no arquivo.
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
