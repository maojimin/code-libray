package com.maojm.code.pattern.command;

public class CommandImpl extends Command {

	public CommandImpl(Receiver receiver) {
		super(receiver);
	}

	@Override
	public void execute() {
		receiver.reciver();
	}

}
