package de.lumpn.zelda.puzzle;

public final class VariableIdentifier {

	public VariableIdentifier(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return 31 + id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof VariableIdentifier)) return false;
		VariableIdentifier other = (VariableIdentifier) obj;
		return (id == other.id);
	}

	@Override
	public String toString() {
		return String.format("Var: %d", id);
	}

	private final int id;
}
