package com.vxml.audio;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NativeCommand {

	private static ExecutorService executorService = Executors.newFixedThreadPool(1);
	private static Process currentProcess;

	//Doesn't wait for the process to finish
	public Process speak(String text) throws IOException, InterruptedException, ExecutionException {
		cancellCurrentTask();
		System.out.println("SPEAK:" + text);
		String[] cmd = { "/bin/sh", "-c", "echo '" + text + "' | festival --tts" };
		Future<Process> currentFuture = executorService.submit(new OutputCallable(cmd));
		currentProcess = currentFuture.get();
		return currentProcess;
	}

	//Doesn't wait for the process to finish
	public Process playAudioFile(String waveFile) throws InterruptedException, ExecutionException {
		cancellCurrentTask();
		String[] cmdWav = { "/bin/sh", "-c", "play " + waveFile };
		Future<Process> currentFuture = executorService.submit(new OutputCallable(cmdWav));
		currentProcess = currentFuture.get();
		return currentProcess;
	}

	private void cancellCurrentTask() {
		try {
			if (currentProcess != null) {
				currentProcess.destroy();
			}
		} catch (Exception e) {
			//Do nothing
		}
	}


	public static void main(String[] args) throws Exception {

		Process p = new NativeCommand().playAudioFile("http://localhost:8080/vxml-browser/testAudio.wav");
		// p.waitFor();
		Thread.sleep(5000);
		p = new NativeCommand().playAudioFile("http://localhost:8080/vxml-browser/testAudio.wav");
		Thread.sleep(5000);
		p.destroy();
	}
}
