package de.lumpn.zelda.puzzle;

public class DotTransitionBuilder {

	public void setStart(int id) {
		this.start = id;
	}

	public void setDestination(int id) {
		this.end = id;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void express(DotBuilder builder) {
		builder.addTransition(start, end, label);
	}

	private int start, end;

	private String label;
}
