package jw05.anish.calabashbros;
import jw05.asciiPanel.AsciiPanel;

import java.awt.Color;
public class MapItem extends Thing{
    public MapItem(Color color,int itemId,World world) {
        super(color, (char) itemId, world);
    }
}
