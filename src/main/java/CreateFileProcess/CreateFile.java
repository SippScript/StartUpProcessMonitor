package CreateFileProcess;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CreateFile {

    private final JavaPlugin plugin; // Reference to the main plugin class

    public CreateFile(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void createLogFile(Map<String, Long> pluginEnableTimes) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh_mm_a");
        Date now = new Date();
        String date = dateFormat.format(now);
        String time = timeFormat.format(now);

        File folder = new File(plugin.getDataFolder(), date);
        if (!folder.exists()) {
            folder.mkdirs();
        }

    }



    private String formatElapsedTime(long elapsedTimeSeconds) {
        return elapsedTimeSeconds + " seconds";
    }



}
