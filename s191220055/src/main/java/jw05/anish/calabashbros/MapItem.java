package jw05.anish.calabashbros;
import jw05.asciiPanel.AsciiPanel;

public class MapItem extends Thing{
    public MapItem(int itemId,World world) {
        super(AsciiPanel.cyan, (char) itemId, world);
    }
}
