package hudson.plugins.spotinst.jobs;

import hudson.Extension;
import hudson.model.AsyncPeriodicWork;
import hudson.model.TaskListener;
import hudson.plugins.spotinst.cloud.BaseSpotinstCloud;
import hudson.slaves.Cloud;
import jenkins.model.Jenkins;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by ohadmuchnik on 25/05/2016.
 */
@Extension
public class SpotinstInstancesMonitor extends AsyncPeriodicWork {

    //region Members
    final long recurrencePeriod;
    //endregion

    //region Constructor
    public SpotinstInstancesMonitor() {
        super("Instances monitor");
        recurrencePeriod = TimeUnit.SECONDS.toMillis(30);
    }
    //endregion

    //region Public Methods
    @Override
    protected void execute(TaskListener taskListener) throws IOException, InterruptedException {
        List<Cloud> cloudList = Jenkins.getInstance().clouds;
        if (cloudList != null && cloudList.size() > 0) {
            for (Cloud cloud : cloudList) {
                if (cloud instanceof BaseSpotinstCloud) {
                    BaseSpotinstCloud spotinstCloud = (BaseSpotinstCloud) cloud;
                    spotinstCloud.monitorInstances();
                }
            }
        }
    }

    @Override
    public long getRecurrencePeriod() {
        return recurrencePeriod;
    }
    //endregion
}
