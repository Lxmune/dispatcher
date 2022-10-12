package dev.dispatcher;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import redis.clients.jedis.JedisPooled;

public class Dispatcher extends JavaPlugin implements PluginMessageListener {

    enum MiniGames {
        Spleef
    }

    // We need to allocate server ports beforehand (ex: 25565-25570)

    class Spleef {

        class Server1 {
            public String ip = "localhost";
            public int port = 25565;
        }

        class Server2 {
            public String ip = "localhost";
            public int port = 25566;
        }

    }

    // Initializing Redis (To get server status values)
    JedisPooled pool = new JedisPooled("localhost", 6379);


    public static Plugin plugin = null;

    @Override
    public void onEnable() {
        plugin = this;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {

        // Checks if the message is destined for bungeecord
        if (!channel.equals("BungeeCord")) return;

        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
            String subchannel = in.readUTF();
            
            if (subchannel.equals("Dispatcher")) {
                String game = in.readUTF();
                MiniGames gameEnum = MiniGames.valueOf(game);

                switch (gameEnum) {
                    case Spleef:
                        // Get the server status withserver1Status redis
                        String server1_ingame = pool.get("spleef.(PORT).ingame");
                        if (server1_ingame == "true") {
                            // Send player to server
                        }
                        break;
                    default:
                        break;
                }
            }
        }

        // Showe error in case of problem
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}