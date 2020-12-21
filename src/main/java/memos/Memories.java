package memos;

import memos.config.Config;
import memos.config.ConfigScreen;
import memos.utils.Stopwatch;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;



public class Memories implements ClientModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "memos";
    public static final String MOD_NAME = "Memories";

    private static KeyBinding configKey;


    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

    @Override
    public void onInitializeClient() {
        log(Level.INFO, "Initializing");
        Config.loadConfig();
        configKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.memos.name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_4,
                "category.memos.name"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (configKey.wasPressed()) {
                MinecraftClient.getInstance().openScreen(ConfigScreen.getConfigScreen(MinecraftClient.getInstance().currentScreen));
            }
        });
        new Thread(Stopwatch.getInstance()).start();
    }
}