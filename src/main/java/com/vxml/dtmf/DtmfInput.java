package com.vxml.dtmf;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

import com.vxml.core.browser.VxmlBrowser;

public class DtmfInput {

    private String input;

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

    public String readWithTimeOut(int timeout) {
        System.out.print("Input(wait " + (timeout / 1000) + " sec)>");
        ReadThread myThread = new ReadThread();
        myThread.start();
        if (!VxmlBrowser.isSuspend()) {
        	try {
        		Thread.sleep(timeout);
        	} catch (InterruptedException e) {
        		// Do nothing
        	}
        	
        	myThread.interrupt();
        }
        return input;
    }

    private class ReadThread extends Thread {

		@Override
        public void run() {
            while (!isInterrupted()) {
//                try {
                    if (stdin.hasNext()) {
                        input = stdin.next();
                        System.out.println("Got: " + input);
                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//
//                }
            }
            if (input == null) {
                System.out.println("Aborted.");
            } else {
                System.out.println("Got: " + input);
            }
        }
    }
}
