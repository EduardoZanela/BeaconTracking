package com.eduardozanela.beacondetector.scheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.eduardozanela.beacondetector.entities.DistanceEntity;
import com.eduardozanela.beacondetector.model.InterscityResourceDataRequest;
import com.eduardozanela.beacondetector.service.InterscityService;

import java.util.Iterator;

public class SendDataToApiScheduler extends JobService {

    private static final String LOG_TAG = "API_SCHEDULER";
    private boolean jobCancelled = false;


    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(LOG_TAG, "Job started");
        doBackgroundWork(params);

        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Log.d(LOG_TAG, "run: " + i);
                    if (jobCancelled) {
                        //return;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Log.d(LOG_TAG, "Job finished");
                jobFinished(params, false);
            }
        }).start();
    }

    public void sendtoApi(){
        Iterator<DistanceEntity> allDistances = DistanceEntity.findAll(DistanceEntity.class);
        InterscityService.getInstance().postResourceData("test", new InterscityResourceDataRequest());
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(LOG_TAG, "Job cancelled before completion");
        jobCancelled = true;
        return true;
    }



}
