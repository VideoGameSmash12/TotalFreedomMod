package me.totalfreedom.totalfreedommod.command;

import de.myzelyam.api.vanish.VanishAPI;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

<<<<<<< HEAD
@CommandPermissions(level = Rank.SUPER_ADMIN, source = SourceType.ONLY_IN_GAME)
=======
@CommandPermissions(level = Rank.TRIAL_MOD, source = SourceType.ONLY_IN_GAME)
>>>>>>> parent of da80f1b6... Revert "Merge branch 'development' of https://github.com/TFPatches/TotalFreedomMod into development"
@CommandParameters(description = "Hide yourself from other players", usage = "/<command> [-s]", aliases = "v,ev,evanish")
public class Command_vanish extends FreedomCommand
{
    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0)
        {
            if (!VanishAPI.isInvisible(playerSender))
            {
                VanishAPI.hidePlayer(playerSender);
            }
            else
            {
                VanishAPI.showPlayer(playerSender);
            }
        }
        else if (args[0].equalsIgnoreCase("-s") || args[0].equalsIgnoreCase("-v"))
        {
            if (!VanishAPI.isInvisible(playerSender))
            {
                VanishAPI.getPlugin().getVisibilityChanger().hidePlayer(playerSender, null, true);
            }
            else
            {
                VanishAPI.getPlugin().getVisibilityChanger().showPlayer(playerSender, null, true);
            }
        }
        else
        {
            return false;
        }
        return true;
    }
}