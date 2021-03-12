package net.dohaw.killreward;

import net.dohaw.corelib.Config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BaseConfig extends Config {

    public BaseConfig() {
        super("config.yml");
    }

    public Set<String> getPermissions(){
        Set<String> keys;
        try{
            keys = config.getConfigurationSection("Permissions").getKeys(false);
        }catch(NullPointerException e){
            plugin.getLogger().warning("The \"Permissions\" section in your config is missing! Please fix this");
            keys = new HashSet<>();
        }
        return keys;
    }

    public List<String> getCommandsRanPerPermission(String permission){
        return config.getStringList("Permissions." + permission + ".Commands Ran");
    }

    public String getApplicableWorld(){
        return config.getString("Applicable World", "world");
    }

}
