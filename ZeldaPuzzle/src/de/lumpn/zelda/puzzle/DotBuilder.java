package de.lumpn.zelda.puzzle;

import java.io.PrintStream;

public class DotBuilder {

	public DotBuilder() {
		this.out = System.out;
	}

	public void begin() {
		out.println("digraph {");
	}

	public void end() {
		out.println("}");
	}

	public void addNode(int id) {
		out.format("node%d [label=\"%d\"];\n", id, id);
	}

	public void addTransition(int start, int end, String script) {
		out.format("node%d -> node%d [label=\"%s\"];\n", start, end, script);
	}

	private final PrintStream out;
}
