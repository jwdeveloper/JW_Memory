package jw.memory.gui;

import jw.data.repositories.RepositoryGUI;
import jw.gui.button.Button;
import jw.gui.button.ButtonActionsEnum;
import jw.gui.core.InventoryGUI;
import jw.memory.data.UserData;
import jw.memory.data.UserDataRepository;
import jw.utilites.Emoticons;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class RankingGUI extends RepositoryGUI<UserData>
{

    public RankingGUI(InventoryGUI parent, UserDataRepository repositoryGUI) {
        super(parent, "Ranking gui", repositoryGUI);
    }

    @Override
    public void OnInitialize() {
        super.OnInitialize();
        this.SetTitle(ChatColor.YELLOW+""+ChatColor.BOLD+"   Ranking");
        SetActionButtonVisibility(ButtonActionsEnum.INSERT,false);
        SetActionButtonVisibility(ButtonActionsEnum.EDIT,false);
        SetActionButtonVisibility(ButtonActionsEnum.DELETE,false);
        SetActionButtonVisibility(ButtonActionsEnum.COPY,false);
        SetActionButtonVisibility(ButtonActionsEnum.SEARCH,false);
    }
    @Override
    public Button MapDataToButton(UserData data)
    {
        Button button = new Button(Material.PLAYER_HEAD,data.name);
        button.setObjectHolder(data);
        button.SetDescription(ChatColor.GREEN+ Emoticons.arrowright+" Wins: "+data.wins,
                ChatColor.GREEN+ Emoticons.arrowright+" Loses: "+data.loses,
                ChatColor.GREEN+ Emoticons.arrowright+" Best time: "+data.bestTime,
                ChatColor.GREEN+ Emoticons.arrowright+" Most points: "+data.mostPoints);
        return button;
    }
}
