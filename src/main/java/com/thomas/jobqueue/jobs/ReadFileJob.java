package com.thomas.jobqueue.jobs;

import com.thomas.jobqueue.Job;

import java.nio.file.Files;
import java.nio.file.Path;

public class ReadFileJob extends Job {

    private final String filename;

    public ReadFileJob(String filename) {
        this.filename = filename;
    }

    @Override
    protected void execute() throws Exception {
        String content = Files.readString(Path.of(filename));
        setResult(content);
    }
}