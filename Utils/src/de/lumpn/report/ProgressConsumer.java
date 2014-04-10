package de.lumpn.report;

public interface ProgressConsumer {

	void reset(String caption);

	void set(int current, int total);
}
