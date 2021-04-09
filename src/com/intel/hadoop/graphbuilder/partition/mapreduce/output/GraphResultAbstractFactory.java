package com.intel.hadoop.graphbuilder.partition.mapreduce.output;

enum GraphOutputType {
	GLGraphOuput, SimpleGraphOutput,
}

public class GraphResultAbstractFactory {
	static GraphResult getFactory(GraphOutputType type) {
		switch (type) {
		case GLGraphOuput:
			return new GLGraphOutput();
		case SimpleGraphOutput:
			return new SimpleGraphOutput();
		}
		return null;
	}

	public static void main(String[] args) {
		GraphResult factory = GraphResultAbstractFactory.getFactory(GraphOutputType.GLGraphOuput);
		GraphResult factory2 = GraphResultAbstractFactory.getFactory(GraphOutputType.SimpleGraphOutput);

	}
}
