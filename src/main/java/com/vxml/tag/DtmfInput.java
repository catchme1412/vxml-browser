package com.vxml.tag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class DtmfInput {

    private String input;

    public String read() {
        String value = null;
        System.out.print("Input>");
        try {
            
            Scanner in = new Scanner(System.in);
            value = in.next();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public String readWithTimeOut() {
        System.out.print("Input(wait 5 sec)>");
        MyThread myThread = new MyThread();
        myThread.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // Do nothing
        }

        myThread.interrupt();
        return input;
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

            while (!isInterrupted()) {
                try {
                    if (stdin.ready()) {
                        input = stdin.readLine();
                        System.out.println("Got: " + input);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                }
            }
            System.out.println("Aborted.");
        }
    }
}
