package com.vxml.audio;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProcessTrigger {

    public String trigger(String[] cmd) throws IOException, InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Process p = Runtime.getRuntime().exec(cmd);
        ProcessRunThread task = new ProcessRunThread(p);
        Future<Process> f = executorService.submit(task);
        ProcessKillThread task2 = new ProcessKillThread(p);
        Future<String> f1 = executorService.submit(task2);
        f.cancel(true);
        
        return f1.get();
    }
    
    public static void main(String args[]) throws IOException, InterruptedException, ExecutionException {
        String p = new ProcessTrigger().trigger(new String[] {"play", "/tmp/ivr.wav"});
        System.out.println("Your input" + p);
        System.out.println("Your input  " + p);
    }
}
