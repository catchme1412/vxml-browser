package com.vxml.dtmf;

import java.util.Scanner;

import com.vxml.core.browser.VxmlExecutionContext;

public class DtmfInput {

    private Object input;

    private Scanner stdin;

    public DtmfInput(Scanner in) {
        stdin = in;
    }

    public String read() {
        String value = null;
        System.out.print("Input>");
        try {
            if (stdin.hasNext()) {
                value = stdin.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public Object readWithTimeOut(int timeout) {
        System.out.print("Input(wait " + (timeout / 1000) + " sec)>");
        ReadThread myThread = new ReadThread();
        myThread.start();
        if(!VxmlExecutionContext.isSlientMode()) {
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                // Do nothing
            }
        } else {
        	try {
				myThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        myThread.interrupt();
        // }
        return input;
    }

    private class ReadThread extends Thread {

        @Override
        public void run() {
            while (!isInterrupted()) {
                // try {
                if (stdin.hasNext()) {
                	if (stdin.hasNextBoolean()) {
                		input = stdin.nextBoolean();
                	} if (stdin.hasNextInt()) {
                	    input = stdin.nextInt();
                	} else {
                		input = stdin.next();
                	}
                    System.out.println("Got: " + input);
                    break;
                }
                // } catch (IOException e) {
                // e.printStackTrace();
                // } finally {
                //
                // }
            }
            if (input == null) {
                System.out.println("Aborted.");
            }
        }
    }
}
