package com.intel.hadoop.graphbuilder.partition.mapreduce.output;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import com.intel.hadoop.graphbuilder.graph.EdgeFormatter;
import com.intel.hadoop.graphbuilder.graph.Graph;
import com.intel.hadoop.graphbuilder.graph.glgraph.GLGraph;
import com.intel.hadoop.graphbuilder.graph.glgraph.GLJsonFormatter;
import com.intel.hadoop.graphbuilder.io.MultiDirOutputFormat;

public class GLGraphOutput extends GraphResult {

	@Override
	public final void init(JobConf conf) {
		conf.setOutputFormat(MultiDirOutputFormat.class);
	}

	@Override
	public void configure(JobConf conf) {
	}

	@Override
	public final void write(Graph g, EdgeFormatter formatter, OutputCollector out, Reporter reporter) throws Exception {

		int pid = g.pid();

		/* Output vid2lvidmap. */
		LOG.info("Collecting vid2lvid: " + pid);
		StringWriter vid2lvidWriter = ((GLJsonFormatter) formatter).vid2lvidWriter((GLGraph) g);
		if (clearAfterWrite)
			((GLGraph) g).vid2lvid().clear();

		out.collect(new Text("partition" + pid + "/vid2lvid"), new Text(vid2lvidWriter.toString()));
		vid2lvidWriter.close();
		LOG.info("Done collecting vid2lvid: " + pid);

		/* Graph finalize. */
		LOG.info("Finalizing graph: " + pid);
		g.finalize();
		LOG.info("Done finalizing finished: " + pid);

		/* Output edge data. */
		LOG.info("Collecting edata: " + pid);
		StringWriter edataWriter = ((GLJsonFormatter) formatter).edataWriter((GLGraph) g);
		if (clearAfterWrite)
			((GLGraph) g).edatalist().clear();

		out.collect(new Text("partition" + pid + "/edata"), new Text(edataWriter.toString()));
		edataWriter.close();
		LOG.info("Done collecting edata: " + pid);

		/* Output graph structure. */
		LOG.info("Collecting graph structure: " + pid);
		StringWriter structureWriter = formatter.structWriter(g);

		out.collect(new Text("partition" + pid + "/structure"), new Text(structureWriter.toString()));
		if (clearAfterWrite)
			g.clear();
		structureWriter.close();
		LOG.info("Done collecting graph structure: " + pid);
	}

	@Override
	public final void writeAndClear(Graph g, EdgeFormatter formatter, OutputCollector out, Reporter reporter)
			throws Exception {
		clearAfterWrite = true;
		write(g, formatter, out, reporter);
	}

	@Override
	public void close() throws IOException {
	}

	private boolean clearAfterWrite = false;
}
