package com.vxml.audio;

import java.util.concurrent.Callable;

public class ProcessRunThread implements Callable<Process> {

	private Process process;

	public ProcessRunThread(Process proc) {
		process = proc;
	}

	@Override
	public Process call() throws Exception {
		process.waitFor();
		return process;
	}

}
