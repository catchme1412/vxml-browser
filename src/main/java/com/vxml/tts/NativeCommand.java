package com.vxml.tts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NativeCommand {

    public void speak(String text) throws IOException, InterruptedException {
        StringBuffer output = new StringBuffer();
        System.out.println("SPEAK:" + text);
        String[] cmd = { "/bin/sh", "-c", "echo '" + text + "' | festival --tts" };

        execute(output, cmd);
    }

    public void play(String waveFile) {

        StringBuffer output = new StringBuffer();
        String[] cmd = { "/bin/sh", "-c", "wget " + waveFile + " -O /tmp/ivr.wav" };
        execute(output, cmd);
        String[] cmdWav = { "/bin/sh", "-c", "play /tmp/ivr.wav" };
        execute(output, cmdWav);

    }

    private void execute(StringBuffer output, String[] cmd) {
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new NativeCommand().speak("testing ");
        new NativeCommand().play("http://ivraudio.orbitz.net/common-audio/posOptions.wav");
    }

}
