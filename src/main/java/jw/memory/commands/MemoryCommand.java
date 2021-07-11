package jw.memory.commands;

import jw.api.commands.JwCommandGUI;
import jw.api.gui.core.InventoryGUI;
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
