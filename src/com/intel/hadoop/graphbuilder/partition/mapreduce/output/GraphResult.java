package com.intel.hadoop.graphbuilder.partition.mapreduce.output;

import org.apache.log4j.Logger;

import com.intel.hadoop.graphbuilder.graph.GraphOutput;
import com.intel.hadoop.graphbuilder.graph.simplegraph.SimpleGraphOutput;



public abstract class GraphResult implements GraphOutput {
	protected static final Logger LOG = Logger.getLogger(GraphResult.class);
}
