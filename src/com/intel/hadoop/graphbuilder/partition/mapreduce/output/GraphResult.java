package com.intel.hadoop.graphbuilder.partition.mapreduce.output;

import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.log4j.Logger;

import java.io.IOException;

import com.intel.hadoop.graphbuilder.graph.EdgeFormatter;
import com.intel.hadoop.graphbuilder.graph.Graph;
import com.intel.hadoop.graphbuilder.graph.GraphOutput;

public abstract class GraphResult {
	protected static final Logger LOG = Logger.getLogger(GraphResult.class);

	/**
	 * Set the {@code OutputFormat} in the {@code JobConf}.
	 * 
	 * @param conf
	 */
	abstract void init(JobConf conf);

	/**
	 * Configure this GraphOutput using {@code JobConf}.
	 * 
	 * @param conf
	 */
	abstract void configure(JobConf conf);

	/**
	 * Write out the graph to the {@code OutputCollector}.
	 * 
	 * @param g         the graph to output.
	 * @param formatter formatter for the graph string representation.
	 * @param out
	 * @param reporter
	 * @throws Exception
	 */
	abstract void write(Graph g, EdgeFormatter formatter, OutputCollector out, Reporter reporter) throws Exception;

	/**
	 * Write out the graph and clearing the fields written to free up memory.
	 * 
	 * @param g         the graph to output.
	 * @param formatter formatter of the graph string representation.
	 * @param out
	 * @param reporter
	 * @throws IOException
	 */
	abstract void writeAndClear(Graph g, EdgeFormatter formatter, OutputCollector out, Reporter reporter)
			throws Exception;

	/**
	 * Close the output.
	 * 
	 * @throws IOException
	 */
	abstract void close() throws IOException;
}
