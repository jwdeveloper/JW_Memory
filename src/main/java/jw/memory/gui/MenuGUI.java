package jw.memory.gui;

import jw.gui.assets.ChestGUI;
import jw.memory.Main;
import jw.memory.data.DataManager;
import org.bukkit.Material;
import org.bukkit.Sound;

public class MenuGUI extends ChestGUI {

    public MenuGUI() {
        super("Memory", 5);
    }

    private GameGUI gameGUI;
    private RankingGUI rankingGUI;

    @Override
    public void OnInitialize()
    {
        DataManager dataManager = Main.getPlugin(Main.class).dataManager;

        gameGUI = new GameGUI(this,dataManager.getSettings(),dataManager.getUserDataRepository());
        rankingGUI = new RankingGUI(this,dataManager.getUserDataRepository());

        this.SetTitle("Memory Game");
        this.DrawBorder(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        this.BuildButton()
                .SetName("Play")
                .SetMaterial(Material.CRAFTING_TABLE)
                .SetClickSound(Sound.UI_BUTTON_CLICK)
                .SetPosition(2, 3)
                .SetOnClick((player, button) ->
                {
                    gameGUI.Open(player);
                })
                .BuildAndAdd();

        this.BuildButton()
                .SetName("Players ranking")
                .SetMaterial(Material.BOOKSHELF)
                .SetClickSound(Sound.UI_BUTTON_CLICK)
                .SetPosition(2, 5)
                .SetOnClick((player, button) ->
                {
                    rankingGUI.Open(player);
                })
                .BuildAndAdd();
    }
}
