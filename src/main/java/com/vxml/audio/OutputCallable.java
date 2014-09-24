package com.vxml.audio;

import java.util.concurrent.Callable;

public class OutputCallable implements Callable<Process> {

	private String[] cmd;

	public OutputCallable(String[] cmd) {
		this.cmd = cmd;
	}

	@Override
	public Process call() throws Exception {
		return Runtime.getRuntime().exec(cmd);
	}

}
