package jw.memory;

import jw.InicializerAPI;
import jw.memory.commands.MemoryCommand;
import jw.memory.data.DataManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public DataManager dataManager;
    public MemoryCommand memoryCommand;

    @Override
    public void onEnable() {
        InicializerAPI.AttachePlugin(this);
        dataManager = new DataManager();
        dataManager.Load();

        memoryCommand = new MemoryCommand();
    }

    @Override
    public void onDisable() {
        dataManager.Save();
    }
}
