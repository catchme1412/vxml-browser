package com.vxml.audio;

import java.util.concurrent.Callable;

public class ProcessWaitThread implements Callable<Process> {

	private Process process;

	public ProcessWaitThread(Process proc) {
		process = proc;
	}

	@Override
	public Process call() throws Exception {
		process.waitFor();
		return process;
	}

}
