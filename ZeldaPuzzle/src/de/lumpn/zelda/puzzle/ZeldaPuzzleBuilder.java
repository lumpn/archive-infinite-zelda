package de.lumpn.zelda.puzzle;

import java.util.HashMap;
import java.util.Map;
import de.lumpn.zelda.puzzle.script.ZeldaScript;

/**
 * Mutable puzzle builder
 */
public class ZeldaPuzzleBuilder {

	private static final int entranceNode = 0;
	private static final int exitNode = 1;

	public VariableLookup lookup() {
		return lookup;
	}

	public void addDirectedTransition(int start, int end, ZeldaScript script) {
		Node startNode = getOrCreateNode(start);
		Node endNode = getOrCreateNode(end);
		Transition transition = new Transition(endNode, script);
		startNode.addTransition(transition);
	}

	public void addUndirectedTransition(int node1, int node2, ZeldaScript script) {
		addDirectedTransition(node1, node2, script);
		addDirectedTransition(node2, node1, script);
	}

	public void addScript(int node, ZeldaScript script) {
		addDirectedTransition(node, node, script);
	}

	public ZeldaPuzzle puzzle() {
		DotBuilder builder = new DotBuilder();
		builder.begin();
		for (Node node : nodes.values()) {
			node.express(builder);
		}
		builder.end();
		return null; // TODO
	}

	private Node getOrCreateNode(int id) {
		Node node = nodes.get(id);
		if (node == null) {
			node = new Node(id);
			nodes.put(id, node);
		}
		return node;
	}

	private final Map<Integer, Node> nodes = new HashMap<Integer, Node>();

	private final VariableLookup lookup = new VariableLookup();
}
