package com.thomas.jobqueue.jobs;

import com.thomas.jobqueue.Job;

public class SleepJob extends Job {

    private final int seconds;

    public SleepJob(int seconds) {
        this.seconds = seconds;
    }

    @Override
    protected void execute() throws Exception {
        Thread.sleep(seconds * 1000);
        setResult("Slept for " + seconds + " seconds");
    }
}