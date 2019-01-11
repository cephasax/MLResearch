package br.ufrn.imd.pbil.domain.comm.wekabuilders;

import br.ufrn.imd.pbil.domain.bc.wekabuilders.WekaBuilder;
import br.ufrn.imd.pbil.pde.PossibilityKeySet;
import weka.classifiers.Classifier;
import weka.classifiers.meta.Vote;
import weka.core.SelectedTag;
import weka.core.Tag;

public class VoteWekaBuilder {
	
	public static Vote buildForWeka(PossibilityKeySet pks) {
		Vote vote = new Vote();
		Classifier[] classifiers = new Classifier[pks.getBranchClassifiers().size()];

		Tag tag = new Tag();
		tag.setReadable(pks.getKeyValuesPairs().get("R"));
		Tag[] tags = new Tag[1];
		tags[0] = tag;

		for (int i = 0; i < classifiers.length; i++) {
			classifiers[i] = WekaBuilder.buildClassifier(pks.getBranchClassifiers().get(i));
		}

		vote.setClassifiers(classifiers);

		vote.setSeed(Integer.parseInt(pks.getKeyValuesPairs().get("S")));
		vote.setCombinationRule(new SelectedTag(0, tags));

		return vote;
	}
}
