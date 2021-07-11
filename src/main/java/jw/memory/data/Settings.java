package jw.memory.data;

import org.bukkit.Material;
import org.bukkit.Sound;

public class Settings {
    public int lifes = 3;
    public int prepear_time = 7;
    public int inventory_height = 4;
    public int streak  =3;


    public Sound sound_win = Sound.ENTITY_STRIDER_HAPPY;
    public Sound sound_lose = Sound.ENTITY_PLAYER_DEATH;
    public Sound sound_corrent = Sound.BLOCK_AMETHYST_BLOCK_PLACE;
    public Sound sound_miss = Sound.ENTITY_EXPERIENCE_BOTTLE_THROW;
    public Sound sound_click = Sound.BLOCK_AMETHYST_CLUSTER_HIT;
    public Sound sound_streak = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;

    public Material material_backgroud = Material.LIME_STAINED_GLASS_PANE;

    public String command_player_win = "";
}
