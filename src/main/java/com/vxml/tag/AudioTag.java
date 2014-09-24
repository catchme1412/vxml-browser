package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;

public class AudioTag extends AbstractTag {

	private String audioUrl;
	
	
    public AudioTag(Node node) {
        super(node);
    }

    @Override
    public void execute() {
        String src = getAttribute("src");
        String expr = getAttribute("expr");

        try {
            Object convert = null;
            if (expr != null) {
                convert = VxmlBrowser.getContext().executeScript(expr);
            }
            audioUrl = (String) (src != null ? src : convert);
            if (audioUrl != null) {
                System.out.println("Audio:" + audioUrl);
                try {
                	audioUrl = audioUrl.replaceAll("audio.en-US.tellme.com", "ivraudio.orbitz.net");
                	VxmlBrowser.getContext().playAudio(audioUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public String getAudioUrl() {
		return audioUrl;
	}

}
