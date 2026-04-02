package com.thomas.jobqueue.jobs;

import com.thomas.jobqueue.Job;

public class PrintJob extends Job {

    private final String message;

    public PrintJob(String message) {
        this.message = message;
    }

    @Override
    protected void execute() {
        setResult("Printed: " + message);
    }
}