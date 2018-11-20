package models;

import java.util.List;

public class CommitteeProbability {
	private Committee rootmethod;
	private float value;
	private List<CommitteeProbability> committeeProbability;
	public Committee getRootmethod() {
		return rootmethod;
	}
	public void setRootmethod(Committee rootmethod) {
		this.rootmethod = rootmethod;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public List<CommitteeProbability> getCommitteeProbability() {
		return committeeProbability;
	}
	public void setCommitteeProbability(List<CommitteeProbability> committeeProbability) {
		this.committeeProbability = committeeProbability;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return rootmethod.getNome() +": "+ value;
	}
}
