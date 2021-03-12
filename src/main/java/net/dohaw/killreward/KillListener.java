package net.dohaw.killreward;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.permissions.Permission;

import java.util.List;
import java.util.Map;

public class KillListener implements Listener {

    private KillRewardPlugin plugin;

    public KillListener(KillRewardPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e){
        Player killer = e.getEntity().getKiller();
        if(killer != null){
            if(killer.getWorld().getName().equalsIgnoreCase(plugin.getApplicableWorld())) {
                Map<Permission, List<String>> registeredPermissions = plugin.getPermissionsAndCommands();
                for (Map.Entry<Permission, List<String>> entry : registeredPermissions.entrySet()) {
                    Permission perm = entry.getKey();
                    if (killer.hasPermission(perm)) {
                        List<String> commandsRan = entry.getValue();
                        for (String command : commandsRan) {
                            String editedCommand = command.replace("%player%", killer.getName());
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), editedCommand);
                        }
                    }
                }
            }
        }
    }

}
