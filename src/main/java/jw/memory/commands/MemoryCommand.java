package jw.memory.commands;

import jw.commands.JwCommandGUI;
import jw.gui.core.InventoryGUI;
import jw.memory.gui.MenuGUI;

public class MemoryCommand extends JwCommandGUI
{
    public MemoryCommand() {
        super("MemoryGame");
    }

    @Override
    public InventoryGUI SetInventoryGUI() {
        return new MenuGUI();
    }
    @Override
    public void OnInitialize() {

    }
}
