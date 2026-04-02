package com.thomas.jobqueue;

public class JobCommand {

    public String type;
    public String[] args;

    public JobCommand(String type, String[] args) {
        this.type = type;
        this.args = args;
    }
}