package com.vxml.parser.event;

import com.vxml.tag.Tag;

public interface OutputListener {

	void invoke(Tag tag);

}
