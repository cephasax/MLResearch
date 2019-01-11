package br.ufrn.imd.pbilautoens.domain.bc.wekabuilders;

import br.ufrn.imd.pbilautoens.PossibilityKeySet;
import weka.classifiers.lazy.KStar;
import weka.core.SelectedTag;
import weka.core.Tag;

public class KstarWekaBuilder {

	public static KStar buildForWeka(PossibilityKeySet pks) {
		KStar kstar = new KStar();
		
		Tag tag = new Tag();
		tag.setReadable(pks.getKeyValuesPairs().get("M"));
		Tag tags[] = new Tag[1];
		tags[0] = tag;
		kstar.setGlobalBlend(Integer.parseInt(pks.getKeyValuesPairs().get("B")));
		kstar.setEntropicAutoBlend(Boolean.parseBoolean(pks.getKeyValuesPairs().get("E")));
		kstar.setMissingMode(new SelectedTag(0, tags));
		
		return kstar;
	}
	
}
