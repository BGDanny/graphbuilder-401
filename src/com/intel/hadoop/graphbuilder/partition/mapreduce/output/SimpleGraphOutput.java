package com.intel.hadoop.graphbuilder.partition.mapreduce.output;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import com.intel.hadoop.graphbuilder.graph.EdgeFormatter;
import com.intel.hadoop.graphbuilder.graph.Graph;
import com.intel.hadoop.graphbuilder.graph.simplegraph.SimpleGraph;
import com.intel.hadoop.graphbuilder.graph.simplegraph.SimpleSubGraph;
import com.intel.hadoop.graphbuilder.io.MultiDirOutputFormat;

public class SimpleGraphOutput extends GraphResult {
	public SimpleGraphOutput() {
	}

	@Override
	public void init(JobConf conf) {
		conf.setOutputFormat(MultiDirOutputFormat.class);
	}

	@Override
	public void configure(JobConf conf) {
	}

	@Override
	public void write(Graph g, EdgeFormatter formatter, OutputCollector out, Reporter reporter) throws IOException {
		int pid = g.pid();
		int subpid = -1;
		if (g instanceof SimpleSubGraph) {
			subpid = ((SimpleSubGraph) g).subpid();
		}
		String basedir = subpid >= 0 ? "partition" + pid + "/subpart" + subpid : "partition" + pid;

		/* Output edge data. */
		LOG.debug("Collecting edata: " + pid);
		String emetaout = basedir + " meta";
		out.collect(new Text(emetaout), new Text("{\"numEdges\":" + g.numEdges() + "}"));

		StringWriter edataWriter = formatter.edataWriter((SimpleGraph) g);
		if (clearAfterWrite)
			((SimpleGraph) g).clearEdataList();
		String edataout = basedir + " edata";
		out.collect(new Text(edataout), new Text(edataWriter.toString()));
		edataWriter.close();
		LOG.debug("Done collecting edata: " + pid);

		/* Output the graph structure. */
		LOG.debug("Collecting graph structure: " + pid);
		StringWriter structureWriter = formatter.structWriter(g);
		String structout = basedir + " edgelist";
		out.collect(new Text(structout), new Text(structureWriter.toString()));
		if (clearAfterWrite)
			g.clear();
		structureWriter.close();
		LOG.info("Done collecting graph structure: " + pid);
	}

	@Override
	public void writeAndClear(Graph g, EdgeFormatter formatter, OutputCollector out, Reporter reporter)
			throws IOException {
		clearAfterWrite = true;
		write(g, formatter, out, reporter);
	}

	@Override
	public void close() throws IOException {
	}

	boolean clearAfterWrite = false;

}
