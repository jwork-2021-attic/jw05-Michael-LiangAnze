package jw05.anish.screen;

import java.awt.event.KeyEvent;

import jw05.asciiPanel.AsciiPanel;

public interface Screen {

    public void displayOutput(AsciiPanel terminal);

    public Screen respondToUserInput(KeyEvent key);
}
