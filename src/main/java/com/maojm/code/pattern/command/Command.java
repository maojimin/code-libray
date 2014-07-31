package com.maojm.code.pattern.command;

public abstract class Command {
	protected Receiver receiver;
	public Command(Receiver receiver) {
        this.receiver = receiver;
    }
	
	public abstract void execute();

}
