package com.thomas.jobqueue;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Job> jobs = new ArrayList<>();

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            String[] parts = input.split(" ");
            String command = parts[0];

            switch (command) {
                case "submit":
                    if (parts.length < 2) {
                        System.out.println("Usage: submit <payload>");
                        break;
                    }
                    String fullInput = input.substring("submit ".length());
                    submitJob(jobs, fullInput);
                    break;

                case "list":
                    listJobs(jobs);
                    break;

                case "run-next":
                    runNextJob(jobs);
                    break;

                case "exit":
                    scanner.close();
                    return;

                default:
                    System.out.println("Unknown command");
            }
        }
    }

       public static void submitJob(ArrayList<Job> jobs, String input) {
            String[] parts = input.split(" ");

            if (parts[0].equals("write")) {
                if (parts.length < 3) {
                    System.out.println("Usage: submit write <filename> <content>");
                    return;
                }

                String filename = parts[1];

                String content = input.substring(
                    input.indexOf(filename) + filename.length() + 1
                );

                Job job = new WriteFileJob(filename, content);
                jobs.add(job);
                System.out.println("Added: " + job);
                return;
            }

            if (parts[0].equals("read")) {
                if (parts.length < 2) {
                    System.out.println("Usage: submit read <filename>");
                    return;
                }

                String filename = parts[1];

                Job job = new ReadFileJob(filename);
                jobs.add(job);
                System.out.println("Added: " + job);
                return;
            }

            if (parts[0].equals("sleep")) {
                int seconds = Integer.parseInt(parts[1]);
                Job job = new SleepJob(seconds);
                jobs.add(job);
                System.out.println("Added: " + job);
                return;
            }

            Job job = new PrintJob(input);
            jobs.add(job);
            System.out.println("Added: " + job);
        }
        public static void listJobs(ArrayList<Job> jobs) {
            if (jobs.isEmpty()) {
                System.out.println("No jobs in queue");
                return;
            }

            for (Job job : jobs) {
                System.out.println(job);
            }
        }

        public static void runNextJob(ArrayList<Job> jobs) {
            if (jobs.isEmpty()) {
                System.out.println("No jobs to run");
                return;
            }

            Job job = jobs.get(0);

            job.changeStatus(Job.JobStatus.RUNNING);
            System.out.println("Running: " + job);

            job.execute();

            job.changeStatus(Job.JobStatus.COMPLETED);

            jobs.remove(0);
        }

        public static Job findJobById(ArrayList<Job> jobs, int jobId) {
            for (Job job : jobs) {
                if (job.getJobId() == jobId) {
                    return job;
                }
            }
            return null;
        }  

        public static void cancelJob(ArrayList<Job> jobs, int jobId) {
            Job job = findJobById(jobs, jobId);

            if (job == null) {
                System.out.println("Job not found");
                return;
            }
            jobs.remove(job);
            System.out.println("Job: "+jobId+" has been cancelled.");
        }
    
}