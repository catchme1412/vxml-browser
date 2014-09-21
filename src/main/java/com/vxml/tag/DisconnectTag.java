package com.vxml.tag;

import java.io.IOException;

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
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
