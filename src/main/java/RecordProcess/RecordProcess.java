package RecordProcess;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

import java.util.HashMap;
import java.util.Map;



public class RecordProcess {

    public static class RecordProcessListener implements Listener {

        private Map<String, Long> pluginEnableTimes = new HashMap<>();

        @EventHandler
        public void onPluginEnable(PluginEnableEvent event) {
            String pluginName = event.getPlugin().getName();
            long enableTimeMillis = System.nanoTime();
            pluginEnableTimes.put(pluginName, enableTimeMillis);
        }


        public Map<String, Long> getPluginEnableTimes() {
            return pluginEnableTimes;
        }
    }
}




