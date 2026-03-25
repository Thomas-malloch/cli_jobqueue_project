package com.thomas.jobqueue;

public abstract class Job {

    public enum JobStatus {
        PENDING,
        RUNNING,
        COMPLETED
    }

    private static int nextId = 1;

    private int jobId;
    private JobStatus status;

    public Job() {
        this.jobId = nextId++;
        this.status = JobStatus.PENDING;
    }

    public int getJobId() {
        return jobId;
    }

    public JobStatus getJobStatus() {
        return status;
    }

    public void changeStatus(JobStatus j) {
        this.status = j;
        System.out.println("Status: " + this.status);
        if (j == JobStatus.COMPLETED) {
            System.out.println("JOB HAS BEEN COMPLETED");
        }
    }

    public abstract void execute();

    @Override
    public String toString() {
        return "Job{id=" + jobId + ", status=" + status + "}";
    }
}