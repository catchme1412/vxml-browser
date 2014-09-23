package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.audio.NativeCommand;
import com.vxml.core.browser.VxmlBrowser;
import com.vxml.core.browser.VxmlExecutionContext;

public class AudioTag extends AbstractTag {

	private String audioUrl;
	
	private boolean isSlientBackup;
	
    public AudioTag(Node node) {
        super(node);
    }

    @Override
    public void startTag() {
        isSlientBackup = VxmlExecutionContext.isSlientMode();
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
                    VxmlExecutionContext.setSlientMode(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endTag() {
        VxmlExecutionContext.setSlientMode(isSlientBackup);
    }

	public String getAudioUrl() {
		return audioUrl;
	}

}
