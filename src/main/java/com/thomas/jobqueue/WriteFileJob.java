package com.thomas.jobqueue;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFileJob extends Job {

    private String filename;
    private String content;

    public WriteFileJob(String filename, String content) {
        super();
        this.filename = filename;
        this.content = content;
    }

    @Override
    public void execute() {
        System.out.println("Writing to file: " + filename);

        try {
            FileWriter writer = new FileWriter(filename, true);
            writer.write(content);
            writer.close();

            System.out.println("Write successful");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "WriteFileJob{id=" + getJobId() +
               ", status=" + getJobStatus() +
               ", file='" + filename +
               "', content='" + content + "'}";
    }
}