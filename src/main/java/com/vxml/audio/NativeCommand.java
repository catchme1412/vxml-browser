package com.vxml.audio;

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

    public Process play(String waveFile) {
        if ("http://ivraudio.orbitz.net/common-audio/posOptions.wav".equals(waveFile)) {
            return null;
        }
        StringBuffer output = new StringBuffer();
        String[] cmd = { "/bin/sh", "-c", "wget " + waveFile + " -O /tmp/ivr.wav" };
        execute(output, cmd);
        String[] cmdWav = { "/bin/sh", "-c", "play /tmp/ivr.wav" };
        return execute(output, cmdWav);

    }

    private Process execute(StringBuffer output, String[] cmd) {
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
            return p;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        new NativeCommand().speak("testing ");
        Process p = new NativeCommand().play("http://ivraudio.orbitz.net/common-audio/posOptions.wav");
        p.destroy();
    }

}
