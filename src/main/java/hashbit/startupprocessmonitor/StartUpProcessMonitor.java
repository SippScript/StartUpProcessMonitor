package hashbit.startupprocessmonitor;

import RecordProcess.RecordProcess;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import storageParse.ParseStorage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class StartUpProcessMonitor extends JavaPlugin implements Listener {

    private RecordProcess.RecordProcessListener recordProcessListener; // Reference to RecordProcessListener



    @Override
    public void onEnable() {
        recordProcessListener = new RecordProcess.RecordProcessListener(); // Initialize RecordProcessListener

        getServer().getPluginManager().registerEvents(recordProcessListener, this);

        getServer().getPluginManager().registerEvents(this, this); // Register the main class as a listener
    }

    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        // After the server has fully loaded, get plugin enable times and create the log file
        Map<String, Long> pluginEnableTimes = recordProcessListener.getPluginEnableTimes();
        createLogFile(pluginEnableTimes);

        // Call the parseAndStorePluginSizes method
        ParseStorage parseStorage = new ParseStorage(this);
        parseStorage.parseAndStorePluginSizes();
    }

    private void createLogFile(Map<String, Long> pluginEnableTimes) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh_mm_a");
        Date now = new Date();
        String date = dateFormat.format(now);
        String time = timeFormat.format(now);

        File folder = new File(getDataFolder(), date);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, time + ".txt");
        try {
            FileWriter writer = new FileWriter(file);
            for (Map.Entry<String, Long> entry : pluginEnableTimes.entrySet()) {
                String pluginName = entry.getKey();
                long enableTimeNano = entry.getValue();
                double elapsedTimeSeconds = (System.nanoTime() - enableTimeNano) / 1e9; // Convert to seconds
                writer.write(pluginName + " - " + formatElapsedTime(elapsedTimeSeconds) + " seconds\n");
            }
            writer.close();
            getLogger().info("Log file created: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatElapsedTime(double elapsedTime) {
        return String.format("%.3f seconds", elapsedTime);
    }
//should be finished
}
