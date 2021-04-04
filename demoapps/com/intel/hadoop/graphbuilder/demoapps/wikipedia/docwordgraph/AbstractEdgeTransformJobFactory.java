package com.intel.hadoop.graphbuilder.demoapps.wikipedia.docwordgraph;

import com.intel.hadoop.graphbuilder.job.AbstractEdgeTransformJob;

public class AbstractEdgeTransformJobFactory {
    public AbstractEdgeTransformJob getJobType(String jobType) {
        if (jobType == "JobTF") {
            return new TransformToTFIDF().new JobTF();
        } else if (jobType == "jobTFIDF") {
            return new TransformToTFIDF().new JobTFIDF();
        }
        return null;
    }
    // new TransformToTFIDF().new JobTF()
}
