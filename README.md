# CLI Job Queue System (Java)

## Overview

This project is a **multithreaded job processing system built in Java**.
It allows users to submit jobs via a command-line interface, which are then executed asynchronously by a background worker.

The system demonstrates core backend concepts including:

* job abstraction
* asynchronous processing
* thread management
* command parsing
* basic persistence
* integration of real algorithms (Huffman encoding)

## Features

### Core System

* Submit jobs via CLI (`submit <job>`)
* Background worker thread processes jobs automatically
* Track job status (PENDING, RUNNING, COMPLETED, FAILED, CANCELLED)
* View all jobs with `list`
* View completed jobs with `history`
* Check job status with `status <id>`
* Cancel jobs with `cancel <id>`

### Built-in Jobs

* `print <message>` — prints a message
* `sleep <seconds>` — pauses execution
* `read <file>` — reads file contents
* `write <file> <content>` — writes to a file

### Advanced Job

* `compress huffman <input> <output>`

  * Applies Huffman encoding to a file
  * Outputs encoded bitstring to file
  * Reports estimated compression statistics

Example:
submit compress huffman file.txt compressed.txt

## Example Usage

> submit print hello
> submit sleep 3
> submit write notes.txt hello world
> submit read notes.txt
> submit compress huffman notes.txt notes.huff
> list
> history
> status 3
> cancel 2

## How It Works

### Architecture

The system is divided into two layers:

#### Core System

* `Job` — abstract base class for all jobs
* `JobManager` — manages queue, execution, and job tracking
* `CommandParser` — parses CLI input into jobs
* `Main` — CLI interface

#### Job Implementations
* `PrintJob`
* `SleepJob`
* `ReadFileJob`
* `WriteFileJob`
* `HuffmanCompressJob`

### Execution Model

* Jobs are submitted via CLI
* Added to a queue (`BlockingQueue`)
* A background worker thread continuously:
  * takes jobs from the queue
  * executes them
  * updates their status

## Huffman Compression

The project includes a Huffman encoding implementation used as a job.

Current implementation:

* Reads file as text
* Builds Huffman tree from input
* Encodes text into a bitstring
* Writes encoded output to file
Note:
* Output is stored as a string of `0`s and `1`s
* Compression size is estimated (not bit-packed to disk yet)
* Decoding is not fully implemented as a standalone file format
Example result:
Compressed file.txt → compressed.txt | original bytes: 120 | estimated compressed bytes: 64 | ratio: 0.53

## Persistence

Jobs are saved to disk using serialization:
jobs.dat
* Located in project root
* Automatically loaded on startup
* Updated when jobs are submitted or completed

## Project Structure
cli_jobqueue_project/
├── pom.xml
├── jobs.dat
├── src/
│   └── main/java/com/thomas/jobqueue/
│       ├── Main.java
│       ├── Job.java
│       ├── JobManager.java
│       ├── CommandParser.java
│       ├── HuffmanCoding.java
│       └── jobs/
│           ├── PrintJob.java
│           ├── SleepJob.java
│           ├── ReadFileJob.java
│           ├── WriteFileJob.java
│           └── HuffmanCompressJob.java
└── target/

## How to Run

### Compile
mvn clean compile

### Run
mvn exec:java -Dexec.mainClass="com.thomas.jobqueue.Main"

## Future Improvements
Planned extensions:
* Huffman decompression with stored metadata
* Lempel-Ziv compression jobs
* TCP server for multi-client support
* URL classification job using machine learning
* Improved binary compression (bit-packing)
* Job result retrieval (`result <id>`)

## Key Concepts Demonstrated
* Multithreading (worker thread + CLI thread)
* Producer-consumer pattern (job queue)
* Object-oriented design (abstract classes, polymorphism)
* File I/O and serialization
* Command parsing
* Integration of algorithms into backend systems

## Notes

* `.class` files are excluded from source and generated in `target/`
* `jobs.dat` stores runtime data and should not be committed
* Use Maven for building and running

## Author

Thomas Malloch
Victoria University of Wellington
Computer Science & Artificial Intelligence
