package com.thomas.jobqueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFileJob extends Job {

    private String filename;

    public ReadFileJob(String filename) {
        super();
        this.filename = filename;
    }

    @Override
    public void execute() {
        System.out.println("Reading file: " + filename);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "ReadFileJob{id=" + getJobId() +
               ", status=" + getJobStatus() +
               ", file='" + filename + "'}";
    }
}