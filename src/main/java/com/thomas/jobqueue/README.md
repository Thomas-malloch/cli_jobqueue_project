# Jobqueue-cli
Java Task Runner CLI

A lightweight command-line task runner built in Java that supports queuing and executing different types of jobs such as printing messages, sleeping, and file operations.

Features
Interactive CLI (REPL-style interface)
Job queue with FIFO execution
Multiple job types:
Print messages
Sleep (simulate long-running tasks)
Write to files
Read from files
Job status tracking (PENDING, RUNNING, COMPLETED)
Unique job IDs
Basic command parsing
Project Structure
src/
  main/java/com/thomas/jobqueue/
    Main.java
    Job.java
    PrintJob.java
    SleepJob.java
    WriteFileJob.java
    ReadFileJob.java
  test/java/com/thomas/jobqueue/
    (tests go here)

pom.xml
README.md
How to Run

From the project root (where pom.xml is located):

mvn compile
mvn exec:java

Or, if configured in your IDE, run Main.java.

Usage

Once running, you can enter commands in the CLI:

Submit Jobs
submit hello
submit sleep 3
submit write test.txt hello world
submit read test.txt
Other Commands
list        # show all jobs
run-next    # run the next job in the queue
exit        # exit the program
Example
> submit write test.txt hello world
Added: WriteFileJob{id=1, status=PENDING, file='test.txt', content='hello world'}

> run-next
Writing to file: test.txt
Write successful

> submit read test.txt
> run-next
Reading file: test.txt
hello world
Future Improvements
Better command parsing (support quotes and flags)
Job prioritization
Concurrency (run multiple jobs in parallel)
File system sandboxing for security
Persistent job storage
Improved error handling and validation
Notes
Files are created relative to the working directory.
Existing files are overwritten unless append mode is implemented.

This project is designed as a learning exercise to explore backend design, CLI development, and job scheduling concepts in Java.