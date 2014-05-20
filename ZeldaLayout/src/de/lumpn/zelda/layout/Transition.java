package de.lumpn.zelda.layout;

public final class Transition {

	public Transition(RoomIdentifier location, ScriptIdentifier scriptIdentifier) {
		this.source = location;
		this.destination = location;
		this.scriptIdentifier = scriptIdentifier;
		this.isLocal = true;
	}

	public Transition(RoomIdentifier source, RoomIdentifier destination, ScriptIdentifier scriptIdentifier) {
		this.source = source;
		this.destination = destination;
		this.scriptIdentifier = scriptIdentifier;
		this.isLocal = false;
	}

	public RoomIdentifier getSource() {
		return source;
	}

	public RoomIdentifier getDestination() {
		return destination;
	}

	public ScriptIdentifier getScript() {
		return scriptIdentifier;
	}

	public boolean isLocal() {
		return isLocal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + source.hashCode();
		result = prime * result + destination.hashCode();
		result = prime * result + scriptIdentifier.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Transition)) return false;
		Transition other = (Transition) obj;
		if (!source.equals(other.source)) return false;
		if (!destination.equals(other.destination)) return false;
		if (!scriptIdentifier.equals(other.scriptIdentifier)) return false;
		return true;
	}

	private final RoomIdentifier source, destination;

	private final ScriptIdentifier scriptIdentifier;

	private final boolean isLocal;
}
