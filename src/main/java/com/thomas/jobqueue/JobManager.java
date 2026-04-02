package com.thomas.jobqueue;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;



public class JobManager {

    private final BlockingQueue<Job> queue = new LinkedBlockingQueue<>();
    private final Map<Integer, Job> allJobs = new HashMap<>();

    public Job submit(Job job) {
        queue.add(job);
        allJobs.put(job.getJobId(), job);
        job.run();
        saveJobs();
        return job;
    }

    public void runNext() {
        if (queue.isEmpty()) {
            System.out.println("No jobs to run");
            return;
        }

        Job job = queue.poll();
        System.out.println("Running job " + job.getJobId());

        job.run();

        if (job.getStatus() == Job.JobStatus.COMPLETED) {
            System.out.println("Completed: " + job.getResult());
        } else if (job.getStatus() == Job.JobStatus.FAILED) {
            System.out.println("Failed: " + job.getError());
        }
    }

    public void listAll() {
        if (allJobs.isEmpty()) {
            System.out.println("No jobs");
            return;
        }

        for (Job job : allJobs.values()) {
            System.out.println(job);
        }
    }

    public void status(int id) {
        Job job = allJobs.get(id);

        if (job == null) {
            System.out.println("Job not found");
            return;
        }

        System.out.println(job);

        if (job.getResult() != null) {
            System.out.println("Result: " + job.getResult());
        }

        if (job.getError() != null) {
            System.out.println("Error: " + job.getError());
        }
    }

    public void cancel(int id) {
        Job job = allJobs.get(id);

        if (job == null) {
            System.out.println("Job not found");
            return;
        }

        job.cancel();
        queue.remove(job);

        System.out.println("Cancelled job " + id);
    }

    public void startWorker() {
        Thread worker = new Thread(() -> {
            while (true) {
                try {
                    Job job = queue.take(); // waits if empty
                    System.out.println("Running job " + job.getJobId());

                    job.run();

                    if (job.getStatus() == Job.JobStatus.COMPLETED) {
                        System.out.println("Completed: " + job.getResult());
                    } else if (job.getStatus() == Job.JobStatus.FAILED) {
                        System.out.println("Failed: " + job.getError());
                    }

                } catch (InterruptedException e) {
                    System.out.println("Worker interrupted");
                    break;
                }
            }
        });

        worker.start();
    }

    public void saveJobs() {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("jobs.dat"))) {

            out.writeObject(allJobs);

        } catch (IOException e) {
            System.out.println("Error saving jobs: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadJobs() {
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("jobs.dat"))) {

            Map<Integer, Job> loaded = (Map<Integer, Job>) in.readObject();
            allJobs.putAll(loaded);

            System.out.println("Loaded " + allJobs.size() + " jobs");

        } catch (Exception e) {
            System.out.println("No previous jobs found");
        }
    }

    public void history() {
        boolean found = false;

        for (Job job : allJobs.values()) {
            Job.JobStatus status = job.getStatus();

            if (status == Job.JobStatus.COMPLETED ||
                status == Job.JobStatus.FAILED ||
                status == Job.JobStatus.CANCELLED) {

                System.out.println(job);

                if (job.getResult() != null) {
                    System.out.println("  Result: " + job.getResult());
                }

                if (job.getError() != null) {
                    System.out.println("  Error: " + job.getError());
                }

                found = true;
            }
        }

        if (!found) {
            System.out.println("No completed jobs yet");
        }
    }
}