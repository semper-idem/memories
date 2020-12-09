package memos;

import memos.config.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.ScreenshotUtils;
import net.minecraft.text.Text;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

public class Utils {
    private static final DateFormat OG_DF = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    private static final DateFormat MOD_DF = new SimpleDateFormat("dd-MM-yyyy_HH.mm");
    private static MinecraftClient client = MinecraftClient.getInstance();

    public static String getFilename(){
        Memories.log(Level.DEBUG, "getFilename attempt");
        String name = getLevelName();
        String date = getDate(getDirectory(name));
        Memories.log(Level.DEBUG, "getFilename success");
        return  name + "/" + date + ".png";
    }

    private static String getLevelName(){
        String levelName;
        if(client.getServer() != null){
            levelName = client.getServer().getSaveProperties().getLevelInfo().getLevelName();
        } else if(client.getCurrentServerEntry() != null){
            levelName = client.getCurrentServerEntry().name;
        } else {
            levelName = "other";
        }
        return  levelName;
    }

    private static File getDirectory(String levelName){
        File directory = new File(client.runDirectory + "/screenshots",  levelName);
        if(!directory.exists()){
            directory.mkdir();
        }
        return  directory;
    }

    // :p
    private static String getDate(File directory) {
        Memories.log(Level.DEBUG, "getDate attempt.");
        String string = Config.modifiedFileName ? MOD_DF.format(new Date()) : OG_DF.format(new Date());
        int i = 1;
        while(true) {
            File file = new File(directory, string + (i == 1 ? "" : "_" + i) + ".png");
            if (!file.exists()) {
                Memories.log(Level.DEBUG, "getDate success.");
                return string;
            }
            ++i;
        }
    }

    public static Consumer<Text> getMessageReceiver() {
        return (text) -> {
            MinecraftClient.getInstance().execute(() -> {
                if (Config.chatMessage) {
                    client.inGameHud.getChatHud().addMessage(text);
                }
            });
        };
    }

    public static void makeScreenshot() {
        ScreenshotUtils.saveScreenshot(client.runDirectory, Utils.getFilename(), client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight(), client.getFramebuffer(), Utils.getMessageReceiver());
    }
}
