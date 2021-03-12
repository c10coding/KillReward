package net.dohaw.killreward;

import lombok.Getter;
import net.dohaw.corelib.CoreLib;
import net.dohaw.corelib.JPUtils;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/*
    Signature
    ==========
    Author: Caleb Owens (c10coding on Github)
    For: Person I work for
    Email: caleb.ja.owens@gmail.com
    Date Created: 3/12/2021
    Date Finished: 3/12/2021
    Description: Runs specific commands when a player dies. The commands ran depends on what permissions the killer has
    Comments: Hello future person :-)
    ==========
 */
public final class KillRewardPlugin extends JavaPlugin {

    @Getter
    private Map<Permission, List<String>> permissionsAndCommands = new HashMap<>();

    @Getter
    private BaseConfig baseConfig;

    @Override
    public void onEnable() {
        // CoreLib is a libary I made. You can find it on my Github
        CoreLib.setInstance(this);
        JPUtils.validateFiles("config.yml");
        this.baseConfig = new BaseConfig();
        loadPermissionsAndCommands();
        JPUtils.registerCommand("killreward", new KillRewardCommand(this));
        JPUtils.registerEvents(new KillListener(this));
    }

    @Override
    public void onDisable() {}

    public void loadPermissionsAndCommands(){
        this.permissionsAndCommands.clear();
        Set<String> permissions = baseConfig.getPermissions();
        PluginManager pm = Bukkit.getPluginManager();
        for(String permStr : permissions){
            // yml doesn't like periods in keys
            String editedPermStr = permStr.replace("-", ".");
            if(pm.getPermission(editedPermStr) == null){
                Permission perm = new Permission(editedPermStr, PermissionDefault.OP);
                List<String> commandsRan = baseConfig.getCommandsRanPerPermission(permStr);
                permissionsAndCommands.put(perm, commandsRan);
                getLogger().info("Registered the permission " + editedPermStr);
            }
        }
    }

}
