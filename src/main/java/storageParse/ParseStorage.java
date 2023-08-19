package storageParse;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseStorage {

    private final JavaPlugin plugin; // Reference to the main plugin class

    public ParseStorage(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void parseAndStorePluginSizes() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
        Date now = new Date();
        String date = dateFormat.format(now);

        File folder = new File(plugin.getDataFolder(), date + "SP");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, "plugin_sizes.txt");
        try {
            FileWriter writer = new FileWriter(file);

            File pluginFolder = new File("plugins"); // Adjust the path to your plugin directory
            if (pluginFolder.exists() && pluginFolder.isDirectory()) {
                File[] pluginFiles = pluginFolder.listFiles();
                if (pluginFiles != null) {
                    for (File pluginFile : pluginFiles) {
                        if (pluginFile.isDirectory()) {
                            String pluginName = pluginFile.getName();
                            long folderSizeBytes = getFolderSize(pluginFile);
                            String folderSizeFormatted = formatSize(folderSizeBytes);
                            writer.write(pluginName + " - " + folderSizeFormatted + "\n");
                        }
                    }
                }
            }

            writer.close();
            plugin.getLogger().info("Plugin size log file created: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long getFolderSize(File folder) {
        long size = 0;
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    size += file.length();
                } else if (file.isDirectory()) {
                    size += getFolderSize(file);
                }
            }
        }
        return size;
    }

    private String formatSize(long size) {
        double kb = size / 1024.0;
        double mb = kb / 1024.0;
        double gb = mb / 1024.0;

        if (gb >= 1) {
            return String.format("%.2f GB", gb);
        } else if (mb >= 1) {
            return String.format("%.2f MB", mb);
        } else {
            return String.format("%.2f KB", kb);
        }
    }
}
