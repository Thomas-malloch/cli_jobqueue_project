package com.thomas.jobqueue;

public class SleepJob extends Job {

    private int seconds;

    public SleepJob(int seconds) {
        super();
        this.seconds = seconds;
    }

    @Override
    public void execute() {
        System.out.println("Sleeping for " + seconds + " seconds...");

        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            System.out.println("Sleep interrupted");
        }

        System.out.println("Finished sleeping");
    }

    @Override
    public String toString() {
        return "SleepJob{id=" + getJobId() +
               ", status=" + getJobStatus() +
               ", seconds=" + seconds + "}";
    }
}