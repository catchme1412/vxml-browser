package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.core.browser.VxmlBrowser;

public class AudioTag extends AbstractTag {

	private String audioUrl;
	
	private boolean isSlientBackup;
	
    public AudioTag(Node node) {
        super(node);
    }

    @Override
    public void startTag() {
        isSlientBackup = VxmlBrowser.getContext().isSlientMode();
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
            if (audioUrl != null && !VxmlBrowser.getContext().isSlientMode()) {
                System.out.println("Audio:" + audioUrl);
                try {
                	audioUrl = audioUrl.replaceAll("audio.en-US.tellme.com", "ivraudio.orbitz.net");
                	boolean isPlayed = VxmlBrowser.getContext().playAudio(audioUrl);
                	if(isPlayed) {
                	    VxmlBrowser.getContext().setSlientMode(true);
                	} else {
                	    VxmlBrowser.getContext().setSlientMode(false);
                	}
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            VxmlBrowser.getContext().setSlientMode(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public String getAudioUrl() {
		return audioUrl;
	}
	
	@Override
	public void endTag() {
	    VxmlBrowser.getContext().setSlientMode(isSlientBackup);
	}

}
