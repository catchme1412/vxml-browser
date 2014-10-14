package com.vxml.audio;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import com.vxml.core.browser.VxmlBrowser;

public class NativeCommand {

    private static Process currentProcess;
    private static Process previousProcess;
    
    public Process speak(String text) throws IOException, InterruptedException {
        System.out.println("SPEAK:" + text);
        String[] cmd = { "/bin/sh", "-c", "echo '" + text + "' | festival --tts" };

        return execute(cmd);
    }

    
    public static void destroyCurrentProcess() {
        if (currentProcess != null) {
            currentProcess.destroy(); 
        }
    }
    
    public Process play(String waveFile) throws InterruptedException, IOException {
        waitForPreviousProcess();
        //download the audio file
        String[] cmd = { "/bin/sh", "-c", "wget " + waveFile + " -O /tmp/ivr.wav" };
        Process p = execute(cmd);
        p.waitFor();
        //play the audio file
        String[] cmdWav = { "/bin/sh", "-c", "play /tmp/ivr.wav" };
        return execute(cmdWav);

    }
    
    public String playNoWait(String waveFile) throws InterruptedException, IOException, ExecutionException {
        waitForPreviousProcess();
        //download the audio file
        String[] cmd = { "/bin/sh", "-c", "wget " + waveFile + " -O /tmp/ivr.wav" };
        Process p = execute(cmd);
        //play the audio file
        String[] cmdWav = { "/bin/sh", "-c", "play /tmp/ivr.wav" };
        String input = new ProcessTrigger().trigger(cmdWav);
        VxmlBrowser.getContext().setDtmfSource(new Scanner(input));
        return input;

    }

    private void waitForPreviousProcess() throws InterruptedException {
        if (currentProcess != null) {
            currentProcess.waitFor();
        }
        
    }

    private Process execute(String[] cmd) throws IOException, InterruptedException {

               currentProcess = Runtime.getRuntime().exec(cmd);
//            currentProcess.waitFor();
//            ExecutorService executorService = Executors.newFixedThreadPool(1);
//            Future<Process> f = executorService.submit(new ProcessWaitThread(currentProcess));
            return currentProcess;
    }

    public static void main(String[] args) throws Exception {
        new NativeCommand().speak("testing ");
        Process p = new NativeCommand().play("http://ivraudio.orbitz.net/common-audio/posOptions.wav");
//        ExecutorService executorService = Executors.newFixedThreadPool(1);
//        Future<Process> f = executorService.submit(new ProcessWaitThread(p));
        Thread.sleep(4500);
        p.destroy();
    }

}
