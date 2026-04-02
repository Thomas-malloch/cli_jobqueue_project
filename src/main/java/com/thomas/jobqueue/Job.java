package com.thomas.jobqueue;
import java.io.Serializable;


public abstract class Job implements Serializable{

    public enum JobStatus {
        PENDING,
        RUNNING,
        COMPLETED,
        FAILED,
        CANCELLED
    }

    private static int nextId = 1;

    private final int jobId;
    private JobStatus status;
    private String result;
    private String error;

    public Job() {
        this.jobId = nextId++;
        this.status = JobStatus.PENDING;
    }

    public final void run() {
        if (status == JobStatus.CANCELLED) return;

        status = JobStatus.RUNNING;

        try {
            execute();
            status = JobStatus.COMPLETED;
        } catch (Exception e) {
            status = JobStatus.FAILED;
            error = e.getMessage();
        }
    }

    protected abstract void execute() throws Exception;

    public int getJobId() {
        return jobId;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void cancel() {
        if (status == JobStatus.PENDING) {
            status = JobStatus.CANCELLED;
        }
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getResult() {
        return result;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "Job{id=" + jobId +
                ", status=" + status + "}";
    }
}