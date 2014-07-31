package com.maojm.code.pattern.command;

public class Invoker {
	private Command command;

	public void setCommand(Command command) {
		this.command = command;
	}

	public void execute() {
		command.execute();
	}

}
