package jw.memory.game;

import jw.api.commands.CommandHelper;
import jw.api.gui.button.Button;
import jw.api.gui.core.InventoryGUI;
import jw.memory.data.Settings;
import jw.api.task.TaskTimer;
import jw.api.utilites.Emoticons;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class GameManger {
    private InventoryGUI inventoryGUI;
    private int hearts;
    private Button lastPick;
    private boolean blockClicking;
    private Settings settings;
    private int points;
    private float time;
    private boolean gameEnd;
    private int streak;
    private int maxpoints;
    LocalDateTime startTime;
    LocalDateTime endTime;
    private GameEndEvent onGameEnd = (a, b, c) -> {
    };

    public GameManger(Settings settings, InventoryGUI inventoryGUI) {
        this.settings = settings;
        this.inventoryGUI = inventoryGUI;
    }

    public void reset() {
        gameEnd = false;
        points = 0;
        maxpoints =inventoryGUI.GetSize()/2;
        hearts = this.settings.lifes;
        lastPick = null;
        blockClicking = true;
        startTime = LocalDateTime.now();
        endTime = LocalDateTime.now();
    }

    public void drawTitle() {
        String heartsString = " [" + ChatColor.RED;
        for (int i = 0; i < hearts; i++) {
            heartsString += " " + Emoticons.heart;
        }
        heartsString += ChatColor.YELLOW + " ] ";
        if(this.hearts <=0)
            heartsString ="";
        String points = ChatColor.BOLD + "Points: " + ChatColor.RESET + ChatColor.WHITE + this.points + ChatColor.YELLOW;
        String streak = this.streak != 0 ? ChatColor.GOLD + ""+ this.streak+" "+Emoticons.star : "";
        inventoryGUI.SetName(points + heartsString  + streak);
    }

    public void hideTiles() {
        for (int i = 0; i < inventoryGUI.GetSize(); i++) {
            inventoryGUI.GetButton(i).setMaterial(settings.material_backgroud);
        }
    }

    public void startGame() {
        inventoryGUI.ClearButtons();
        this.time = LocalDateTime.now().getSecond();
        this.reset();
        this.createBoard();
        inventoryGUI.Refresh();
        new TaskTimer(20, (a, b) ->
        {
            inventoryGUI.SetTitle(ChatColor.BOLD + " " + ChatColor.GREEN + "        " + (this.getPrepareTime() - a));
        })
                .StopAfter(this.getPrepareTime())
                .OnStop((a) ->
                {
                    this.drawTitle();
                    this.hideTiles();
                    this.setBlockClicking(false);
                    inventoryGUI.Refresh();
                }).Run();
    }


    private Button createTile(Material material) {
        Button button = new Button(material, " ");
        button.setObjectHolder(material);
        button.setSound(settings.sound_click);
        button.setOnClick((a, b) ->
        {
            if (blockClicking)
                return;
            if (button.isHighlighted())
                return;
            if (lastPick != null && lastPick.equals(button))
                return;

            if (lastPick == null) {
                lastPick = button;
                button.setMaterial(button.GetHoldingObject());
                inventoryGUI.Refresh();
            } else {
                if (lastPick.getMaterial() != button.GetHoldingObject()) {
                    hearts--;
                    streak = 0;
                    tileSelectCheck(false, lastPick, button);
                } else {
                    points++;
                    streak++;
                    if (isStreak())
                    {
                        inventoryGUI.PlaySound(settings.sound_streak);
                        hearts++;
                    }

                    tileSelectCheck(true, lastPick, button);
                }
                lastPick = null;
            }
        });
        return button;
    }


    private void tileSelectCheck(boolean isGood, Button button1, Button button2) {
        button2.setMaterial(button2.GetHoldingObject());
        inventoryGUI.Refresh();
        this.setBlockClicking(true);
        new TaskTimer(5, (a, b) ->
        {
            if (isGood) {
                button1.setMaterial(button1.GetHoldingObject());
                button2.setMaterial(button2.GetHoldingObject());
                button1.SetHighlighted(true);
                button2.SetHighlighted(true);
                inventoryGUI.PlaySound(settings.sound_corrent);
            } else {
                inventoryGUI.PlaySound(settings.sound_miss);
                button1.setMaterial(settings.material_backgroud);
                button2.setMaterial(settings.material_backgroud);
            }
            this.drawTitle();
            inventoryGUI.Refresh();
        }).StartAfter(10).StopAfter(1).OnStop((task) ->
        {
            if (this.isPlayerLose()) {
                inventoryGUI.PlaySound(settings.sound_lose);
                this.onGameEnd.execute(false, 0, points);
                return;
            }
            else if(this.isPlayerWin())
            {
                inventoryGUI.PlaySound(settings.sound_win);
                endTime = LocalDateTime.now();
                time = Duration.between(startTime, endTime).getSeconds();
                CommandHelper.Invoke(inventoryGUI.GetPlayer(),"[s]"+settings.command_player_win);
                this.onGameEnd.execute(true, time, points);
                return;
            }

            this.setBlockClicking(false);
        }).Run();
    }

    public void createBoard() {
        Random generator = new Random();
        Material[] materials = Material.values();

        ArrayList<Material> randomMaterials = new ArrayList<>();
        randomMaterials.add(settings.material_backgroud);
        for (int i = 0; i < inventoryGUI.GetSize() / 2; i++) {
            Material material = materials[generator.nextInt(materials.length - 133)];

            while (randomMaterials.contains(material)) {
                material = materials[generator.nextInt(materials.length - 133)];
            }

            inventoryGUI.AddButton(createTile(material), findEmptySlot(generator));
            inventoryGUI.AddButton(createTile(material), findEmptySlot(generator));
            randomMaterials.add(material);
        }
    }

    private int findEmptySlot(Random generator) {
        int inventorySize = inventoryGUI.GetSize();
        int randomSlot = generator.nextInt(inventorySize);
        while (!inventoryGUI.IsSlotEmpty(randomSlot)) {
            randomSlot = generator.nextInt(inventorySize);
        }
        return randomSlot;
    }

    private boolean isStreak() {
        if (streak >= settings.streak) {
            streak = 0;
            return true;
        }
        return false;
    }

    public int getPrepareTime() {
        return settings.prepear_time;
    }

    public boolean isPlayerLose() {
        return this.hearts == 0;
    }

    public boolean isPlayerWin(){return this.points >=maxpoints;}

    public void setBlockClicking(boolean blockClicking) {
        this.blockClicking = blockClicking;
    }

    public void stopGame() {
        inventoryGUI.ClearButtons();
        this.reset();
    }

    public void setOnGameEnd(GameEndEvent gameEvent) {
        this.onGameEnd = gameEvent;
    }

}
