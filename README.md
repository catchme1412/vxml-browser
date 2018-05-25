Please refer https://github.com/catchme1412/vxml-browser-jdk8 for a better implementation using JDK 8

VXML browser (VXML interpreter)
==============================

A simple VXML Browser for VXML development and testing. 

Only major tags are supported as of now. Other tag supports are being developed.

You should be able to enhance it very quickly for your needs.

For TTS output its is using a tts engine named festival. (Ref: NativeCommand.java)

The core classes of the frameworks are 

* VxmlBrowser.java
* VxmlExecutionContext.java
* VxmlScriptEngine.java
* AbstractTag.java (Tag.java)
* NativeCommand.java
 
TODO
====
1. Implement the Unit testing options (completed)
2. Create an IDE for a better visualization 
3. Integrate with eclipse plugin

