package com.vxml.core;

import javax.script.ScriptException;


public class VxmlException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public VxmlException(Exception e) {
		super(e);
	}

	public VxmlException(String string) {
		super(string);
	}

    public VxmlException(String string, ScriptException e) {
        super(string, e);
    }

}
