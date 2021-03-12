package net.dohaw.killreward;

import net.dohaw.corelib.ResponderFactory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KillRewardCommand implements CommandExecutor {

    private KillRewardPlugin plugin;

    public KillRewardCommand(KillRewardPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ResponderFactory rf = new ResponderFactory(sender);
        if(sender.hasPermission("killreward.use")){
            if(args[0].equalsIgnoreCase("reload")){
                plugin.getBaseConfig().reloadConfig();
                plugin.loadConfigValues();
                rf.sendMessage("You have reloaded the KillReward config!");
            }else if(args[0].equalsIgnoreCase("help")){
                rf.sendMessage("&f&lCommands:");
                rf.sendMessage("&6/killr reload &f - Reloads the plugin config");
            }
        }else{
            rf.sendMessage("&cYou do not have permission to use this command!");
        }
        return false;
    }
}
