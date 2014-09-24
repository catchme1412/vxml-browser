package com.vxml.tag;

import org.w3c.dom.Node;

import com.vxml.audio.NativeCommand;
import com.vxml.browser.event.Event;

public class DisconnectTag extends AbstractTag {

    public DisconnectTag(Node node) {
        super(node);
    }

    @Override
    public void execute() throws Event {
        try {
            new NativeCommand().speak("Disconnecting I V R");
        } catch (Exception e) {
			e.printStackTrace();
		}
    }

}
