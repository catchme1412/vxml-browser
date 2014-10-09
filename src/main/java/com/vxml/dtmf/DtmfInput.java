package com.vxml.dtmf;

import java.util.Scanner;

import com.vxml.core.browser.VxmlBrowser;

public class DtmfInput {

	private Object input;

	private Scanner stdin;

	public DtmfInput(Scanner in) {
		stdin = in;
	}

	public String read() {
		String value = null;
		System.out.print("Input>");
		if (stdin.hasNext()) {
			value = stdin.next();
		}
		return value;
	}

	public Object readWithTimeOut(int timeout) {
		System.out.print("Input(wait " + (timeout / 1000) + " sec)>");
		ReadThread myThread = new ReadThread();
		myThread.start();
		sleepIfNeeded(timeout);
		myThread.interrupt();
		
		return input;
	}

	private void sleepIfNeeded(int timeout) {
		if (!VxmlBrowser.getContext().isSlientMode()) {
			try {
				Thread.sleep(timeout);
			} catch (InterruptedException e) {
				// Do nothing
			}
		}
	}

	private class ReadThread extends Thread {

		@Override
		public void run() {
			while (!isInterrupted()) {
				input = readInput();
				break;
			}
			if (input == null) {
				System.out.println("Aborted.");
			}
		}

		private Object readInput() {
		    try {
		        if (stdin.hasNext()) {
		            if (stdin.hasNextBoolean()) {
		                input = stdin.nextBoolean();
		            } else {
		                input = stdin.next();
		            }
		            
		            System.out.println("Got: " + input);
		        }
		    } catch(Exception e) {
		    }
			return input;
		}
	}
}
