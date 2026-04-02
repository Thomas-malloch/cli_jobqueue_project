package com.thomas.jobqueue.jobs;

import java.nio.file.Files;
import java.nio.file.Path;

import com.thomas.jobqueue.HuffmanCoding;
import com.thomas.jobqueue.Job;

public class HuffmanCompressJob extends Job {

    private final String inputFile;
    private final String outputFile;

    public HuffmanCompressJob(String inputFile, String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    @Override
    protected void execute() throws Exception {
        System.out.println("[DEBUG] Starting Huffman compression");

        String content = Files.readString(Path.of(inputFile));
        System.out.println("[DEBUG] Read content length: " + content.length());

        HuffmanCoding hc = new HuffmanCoding(content);
        String encoded = hc.encode(content);

        Files.writeString(Path.of(outputFile), encoded);

        System.out.println("[DEBUG] Written encoded output");

        int originalBytes = content.length(); 
        int encodedBits = encoded.length();
        int encodedBytes = (encodedBits + 7) / 8;

        double ratio = (double) encodedBytes / originalBytes;

        setResult(
            "Compressed " + inputFile +
            " | original bytes: " + originalBytes +
            " | estimated compressed bytes: " + encodedBytes +
            " | ratio: " + String.format("%.2f", ratio)
        );
    }
}