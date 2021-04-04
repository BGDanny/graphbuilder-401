package com.intel.hadoop.graphbuilder.partition.mapreduce.output;

enum GraphOutputType {
	GLGGraphOuput, SimpleGraphOutput,
}

public class GraphResultAbstractFactory {
	static GraphResult getFactory(GraphOutputType type) {
		switch (type) {
		case GLGGraphOuput:
			return new GLGGraphOutput();
		case SimpleGraphOutput:
			return new SimpleGraphOutput();
		}
		return null;
	}

	public static void main(String[] args) {
		GraphResult factory = GraphResultAbstractFactory.getFactory(GraphOutputType.GLGGraphOuput);
		GraphResult factory2 = GraphResultAbstractFactory.getFactory(GraphOutputType.SimpleGraphOutput);

	}
}
