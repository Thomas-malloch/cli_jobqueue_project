package com.thomas.jobqueue;

public class PrintJob extends Job {
    private String message;

    public PrintJob(String message) {
        super();
        this.message = message;
    }

    @Override
    public void execute() {
        System.out.println("Executing print job: " + message);
    }

    @Override
    public String toString() {
        return "PrintJob{id=" + getJobId() +
               ", status=" + getJobStatus() +
               ", message='" + message + "'}";
    }
}