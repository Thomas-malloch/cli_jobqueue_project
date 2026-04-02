package com.thomas.jobqueue.jobs;

import com.thomas.jobqueue.Job;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class WriteFileJob extends Job {

    private final String filename;
    private final String content;

    public WriteFileJob(String filename, String content) {
        this.filename = filename;
        this.content = content;
    }

    @Override
    protected void execute() throws Exception {
        Files.writeString(
            Path.of(filename),
            content,
            StandardOpenOption.CREATE,
            StandardOpenOption.APPEND
        );
        setResult("Wrote to " + filename);
    }
}