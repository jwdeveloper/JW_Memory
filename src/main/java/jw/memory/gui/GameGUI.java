package jw.memory.gui;

import jw.api.gui.assets.ChestGUI;
import jw.api.gui.core.InventoryGUI;
import jw.memory.data.Settings;
import jw.memory.data.UserData;
import jw.memory.data.UserDataRepository;
import jw.memory.game.GameManger;
import jw.api.utilites.Emoticons;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class GameGUI extends ChestGUI {

    private GameManger gameManger;
    private UserDataRepository repository;

    public GameGUI(InventoryGUI parent, Settings settings, UserDataRepository repository) {
        super(parent, "Memory", settings.inventory_height);
        this.repository = repository;
        this.gameManger = new GameManger(settings, this);
    }

    @Override
    public void OnInitialize() {
        gameManger.setOnGameEnd((win, time, points) ->
        {
            if (win)
                this.EndGameScreen(ChatColor.GREEN + " You win " + Emoticons.smiley,win);
            else
                this.EndGameScreen(ChatColor.RED + " You lose " + Emoticons.sad,win);

            String playerUUID = this.player.getUniqueId().toString();
            UserData userData = repository.getOne(playerUUID);
            if (userData.isNull()) {
                userData.playerUUID = playerUUID;
                userData.icon = Material.PLAYER_HEAD;
                userData.name = player.getName();
                repository.insertOne(userData);
            }
            if (win) {
                userData.wins++;
                if (userData.bestTime < time)
                    userData.bestTime = time;
            } else
                userData.loses++;

            if (userData.mostPoints < points)
                userData.mostPoints = points;

        });
    }

    @Override
    public void OnOpen(Player player) {
        gameManger.startGame();
    }

    @Override
    public void OnClose(Player player) {
        gameManger.stopGame();
    }

    public void EndGameScreen(String title, boolean win) {
        this.SetTitle(title);
        this.ClearButtons();
        if (win)
            this.DrawBorder(Material.LIME_STAINED_GLASS_PANE);
        else
            this.DrawBorder(Material.RED_STAINED_GLASS_PANE);
        this.BuildButton()
                .SetMaterial(Material.DIAMOND)
                .SetClickSound(Sound.UI_BUTTON_CLICK)
                .SetName(ChatColor.GREEN + " Play again?")
                .SetPosition(this.height / 2, 3)
                .SetOnClick((a, b) ->
                {
                    this.OnOpen(player);
                }).BuildAndAdd();
        this.BuildButton()
                .SetMaterial(Material.ARROW)
                .SetClickSound(Sound.UI_BUTTON_CLICK)
                .SetName(ChatColor.GREEN + " Back to menu")
                .SetPosition(this.height / 2, 5)
                .SetOnClick((a, b) ->
                {
                    this.OpenParent();
                }).BuildAndAdd();
        this.Refresh();
    }

}
