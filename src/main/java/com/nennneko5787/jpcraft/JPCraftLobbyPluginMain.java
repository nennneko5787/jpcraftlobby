package com.nennneko5787.jpcraft;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import java.util.HashMap;
import java.util.Map;

import java.util.UUID;

import com.nennneko5787.jpcraft.events.onPlayerChatEvent;
import com.nennneko5787.jpcraft.commands.JPCraftLobbyCommandExecutor;

public class JPCraftLobbyPluginMain extends JavaPlugin
{
    public static ChatCounterTask chatCounterTask;
    public static FileConfiguration config;
    public static Map<UUID, Integer> chatCount;

    @Override
    public void onEnable() {
        // config.ymlのパスを取得
        File configFile = new File(getDataFolder(), "config.yml");

        // もしconfig.ymlが存在しない場合は、jar内のデフォルトのconfig.ymlをコピーする
        if (!configFile.exists()) {
            saveDefaultConfig();
            jp_saveDefaultConfig();
            reloadConfig();
        }

        config = getConfig();

        // タスクの実行間隔をコンフィグから読み取る
        int intervalSeconds = config.getInt("anti-spam.counter-reset", 10);

        chatCount = new HashMap<>();

        // タスクを生成し、実行間隔を設定する
        chatCounterTask = new ChatCounterTask();
        chatCounterTask.runTaskTimer(this, 0, intervalSeconds*20);

        getServer().getPluginManager().registerEvents(new onPlayerChatEvent(), this);
        getCommand("jpcraft.lobby").setExecutor(new JPCraftLobbyCommandExecutor());

        getLogger().info("JPCraft Lobby Plugin がロードされました。");
    }

    // jarファイル内のデフォルトconfig.ymlをコピーするメソッド
    private void jp_saveDefaultConfig() {
        // デフォルトのconfig.ymlをjarファイルから読み込む
        InputStream inputStream = getResource("config.yml");

        if (inputStream == null) {
            getLogger().warning("Failed to load default config.yml from jar.");
            return;
        }

        // config.ymlをプラグインのデータフォルダにコピーする
        File configFile = new File(getDataFolder(), "config.yml");

        try {
            Files.copy(inputStream, configFile.toPath());
            getLogger().info("Default config.yml copied from jar.");
        } catch (IOException e) {
            getLogger().warning("Failed to copy default config.yml from jar: " + e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("JPCraft Lobby Plugin がアンロードされました。");
    }

    public class ChatCounterTask extends BukkitRunnable {

        @Override
        public void run() {
            for (UUID uuid : chatCount.keySet()) {
                chatCount.put(uuid, 0);
            }
        }
    }
}
