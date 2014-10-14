package com.vxml.audio;

import java.util.Scanner;
import java.util.concurrent.Callable;

public class ProcessKillThread implements Callable<String> {

	private Process process;

	public ProcessKillThread(Process proc) {
		process = proc;
	}

	@Override
	public String call() throws Exception {
	    System.out.println("input>");
	    Scanner sc = new Scanner(System.in);
	    String v = sc.nextLine();
		process.destroy();
		return v;
	}

}
