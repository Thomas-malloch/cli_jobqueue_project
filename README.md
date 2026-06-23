# CLI Job Queue System (Java)

## Overview

This project is a multithreaded job processing system built in Java.
It allows users to submit jobs via a command-line interface, which are then executed asynchronously by a background worker.

The system demonstrates core backend concepts including:

* job abstraction
* asynchronous processing
* thread management
* command parsing
* basic persistence

## Features

### Core System

* Submit jobs via CLI
* Background worker thread processes jobs automatically
* Track job status (PENDING, RUNNING, COMPLETED, FAILED, CANCELLED)
* View all jobs with `list`
* View completed jobs with `history`
* Check job status with `status <id>`
* Cancel jobs with `cancel <id>`


### Architecture

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
* Added to a queue
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

Jobs are saved to disk using serialization: jobs.dat
* Located in project root
* Automatically loaded on startup
* Updated when jobs are submitted or completed

## Future Improvements
Planned extensions:
* Huffman decompression with stored metadata
* Lempel-Ziv compression jobs
* TCP server for multi-client support
* URL classification job using machine learning
* Improved binary compression (bit-packing)
* Job result retrieval.

## Key Concepts Demonstrated
* Multithreading (worker thread + CLI thread)
* Producer-consumer pattern (job queue)
* Object-oriented design (abstract classes, polymorphism)
* File I/O and serialization
* Command parsing
