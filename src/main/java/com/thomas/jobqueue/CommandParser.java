package com.thomas.jobqueue;

import com.thomas.jobqueue.jobs.HuffmanCompressJob;
import com.thomas.jobqueue.jobs.PrintJob;
import com.thomas.jobqueue.jobs.ReadFileJob;
import com.thomas.jobqueue.jobs.SleepJob;
import com.thomas.jobqueue.jobs.WriteFileJob;

public class CommandParser {

    public static Job parseJob(String input) {
        String[] parts = input.split(" ");

        if (parts.length == 0) {
            throw new IllegalArgumentException("Empty command");
        }

        switch (parts[0]) {

            case "print":
                if (parts.length < 2) {
                    throw new IllegalArgumentException("Usage: print <message>");
                }
                return new PrintJob(input.substring(6));

            case "sleep":
                if (parts.length < 2) {
                    throw new IllegalArgumentException("Usage: sleep <seconds>");
                }
                try {
                    int seconds = Integer.parseInt(parts[1]);
                    return new SleepJob(seconds);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Seconds must be a number");
                }

            case "read":
                if (parts.length < 2) {
                    throw new IllegalArgumentException("Usage: read <filename>");
                }
                return new ReadFileJob(parts[1]);

            case "write":
                if (parts.length < 3) {
                    throw new IllegalArgumentException("Usage: write <filename> <content>");
                }

                String filename = parts[1];
                String content = input.substring(
                    input.indexOf(filename) + filename.length() + 1
                );

                return new WriteFileJob(filename, content);

            case "compress":
                if (parts.length < 4) {
                    throw new IllegalArgumentException(
                        "Usage: compress huffman <input> <output>"
                    );
                }

                String algorithm = parts[1];
                String inputFile = parts[2];
                String outputFile = parts[3];

                if (algorithm.equals("huffman")) {
                    return new HuffmanCompressJob(inputFile, outputFile);
                }

                throw new IllegalArgumentException("Only huffman is supported right now");

            default:
                throw new IllegalArgumentException("Unknown job type: " + parts[0]);
            
            }

    }
}