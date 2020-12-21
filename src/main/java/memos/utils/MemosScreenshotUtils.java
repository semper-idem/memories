package memos.utils;

import memos.config.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.ScreenshotUtils;
import net.minecraft.text.Text;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.Consumer;

public class MemosScreenshotUtils {
    private static MinecraftClient client = MinecraftClient.getInstance();
    private static String pattern = "[\\w^._\\-\\[\\](),']+";
    private static ArrayList<String> dateFormats = new ArrayList<>();

    static {
        dateFormats.add("yyyy-MM-dd_HH.mm.ss");
        dateFormats.add("yyyy-MM-dd_HH.mm");
        dateFormats.add("EEE,_MMM_d,_''yy");
        dateFormats.add("h.mm.ss_a");
        dateFormats.add("h.mm_a");
        dateFormats.add("EEE,_d_MMM_yyyy_HH.mm.ss");
        dateFormats.add("EEE,_d_MMM_yyyy_HH.mm");

    }

    public static String getLocation(){
        return  Config.catalogScreenshots ? getLevelName() + "/" + getFileName() + ".png": getFileName() + ".png";
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

    private static File getDirectory(){
        File directory = Config.catalogScreenshots ? new File(client.runDirectory + "/screenshots", getLevelName()) : new File(client.runDirectory + "/screenshots");
        if(!directory.exists()){
            directory.mkdir();
        }
        return  directory;
    }

    private static String getFileName() {
        String filename = "";
        File directory = getDirectory();
        try{
            filename = new SimpleDateFormat(Config.fileName).format(new Date());
        } catch (Exception e){
            filename = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date());
        }
        int i = 1;
        while(true) {
            File file = new File(directory, filename + (i == 1 ? "" : "_" + i) + ".png");
            if (!file.exists()) {
                return filename;
            }
            ++i;
        }
    }

    public static String formatTimeLeft(int timeLeft){
        int h = timeLeft / 3600;
        int m = (timeLeft % 3600) / 60;
        int s = timeLeft % 60;
        String hours = h > 0 ? String.valueOf(h) : "";
        String minutes = h > 0 ?(m > 9 ? String.valueOf(m) : "0" + m) : String.valueOf(m);
        String seconds = s > 9 ? String.valueOf(s) : "0" + s;
        return h > 0 ? hours + ":" + minutes + ":" + seconds : minutes + ":" + seconds;
    }

    public static Consumer<Text> getMessageReceiver() {
        return (text) -> MinecraftClient.getInstance().execute(() -> {
            if (Config.chatMessagesEnabled) {
                if(Config.chatMessage.length() == 0){
                    client.inGameHud.getChatHud().addMessage(text);
                } else {
                    client.inGameHud.getChatHud().addMessage(Text.of(Config.chatMessage));
                }
            }
        });
    }

    public static Iterable<String> getSuggestionList(){
        return dateFormats;
    }

    public static boolean validateFileName(String filename){
        try{
            if(filename.matches(pattern)){
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }
    public static void makeScreenshot() {
        ScreenshotUtils.saveScreenshot(client.runDirectory, MemosScreenshotUtils.getLocation(), client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight(), client.getFramebuffer(), MemosScreenshotUtils.getMessageReceiver());
    }
}
