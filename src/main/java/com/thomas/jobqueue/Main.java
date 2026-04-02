package com.thomas.jobqueue;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        JobManager manager = new JobManager();
        manager.loadJobs();
        manager.startWorker();

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            String[] parts = input.split(" ");
            String command = parts[0];

            switch (command) {

                case "submit":
                    if (parts.length < 2) {
                        System.out.println("Usage: submit <job>");
                        break;
                    }

                    String jobInput = input.substring(7);

                    try {
                        Job job = CommandParser.parseJob(jobInput);
                        manager.submit(job);
                        System.out.println("Submitted job " + job.getJobId());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;
                    
                case "list":
                    manager.listAll();
                    break;

                case "status":
                    if (parts.length < 2) {
                        System.out.println("Usage: status <jobId>");
                        break;
                    }

                    try {
                        int id = Integer.parseInt(parts[1]);
                        manager.status(id);
                    } catch (NumberFormatException e) {
                        System.out.println("Job ID must be a number");
                    }

                    break;

                case "cancel":
                    if (parts.length < 2) {
                        System.out.println("Usage: cancel <jobId>");
                        break;
                    }

                    try {
                        int id = Integer.parseInt(parts[1]);
                        manager.cancel(id);
                    } catch (NumberFormatException e) {
                        System.out.println("Job ID must be a number");
                    }

                    break;
                
                case "history":
                    manager.history();
                    break;

                case "exit":
                    scanner.close();
                    return;

                default:
                    System.out.println("Unknown command");
            }
        }
    }
}