package memos.utils;

import memos.Memories;
import memos.config.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;
import org.apache.logging.log4j.Level;


public class Stopwatch implements Runnable {
    private static Stopwatch INSTANCE;
    private int secondsLeft = -1;
    private boolean initialized = false;
    private boolean reset = false;
    private MinecraftClient client = MinecraftClient.getInstance();


    private Stopwatch(){
    }

    public static Stopwatch getInstance(){
        if(INSTANCE == null){
            INSTANCE = new Stopwatch();
        }
        return INSTANCE;
    }

    public static void reset(){
        INSTANCE.secondsLeft = -1;
        INSTANCE.initialized = false;
        INSTANCE.reset = true;
    }

    public static int getTimeLeft(){
        return INSTANCE.secondsLeft;
    }


    @Override
    public void run() {
        try{
            while(client.isRunning()){
                if(Config.stopwatchEnabled){
                    initialize();
                    tick();
                }
            }
        } catch (Exception e){
            if(MinecraftClient.getInstance().player != null){
                MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.SYSTEM, Text.of("Couldn't save screenshot"), MinecraftClient.getInstance().player.getUuid());
            }
        }

    }


    private void initialize(){
        Memories.log(Level.DEBUG, "Initialize: Starting");
        while(!INSTANCE.initialized){
            if(client.world != null){
                Memories.log(Level.DEBUG, "Initialize: world found sleeping 60s");
                INSTANCE.reset = false;
                INSTANCE.initialized = true;
                sleep(60);
            } else {
                Memories.log(Level.DEBUG, "Initialize: world not found sleeping 1s");
                sleep();
            }
        }
        Memories.log(Level.DEBUG, "Initialize: Finished");
    }

    private void tick(){
        Memories.log(Level.DEBUG, "Tick: Starting");
        while (INSTANCE.initialized){
            Memories.log(Level.DEBUG, "Tick: Make screenshot and wait 60s");
            MemosScreenshotUtils.makeScreenshot();
            sleep((int) (60 * Config.stopwatchInterval));
        }
        Memories.log(Level.DEBUG, "Tick: Finished");
    }



    private void sleep(){
        try{
            do{
                Thread.sleep(1000);
            } while(client.isPaused());
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void sleep(int seconds){
        Memories.log(Level.DEBUG, "Sleep: Starting");
        for(int i = 0; i <= seconds; i++){
            sleep();
            INSTANCE.secondsLeft = seconds - i;
            if(INSTANCE.reset){
                Memories.log(Level.DEBUG, "Sleep: reset = true, breaking loop");

                break;
            }
        }
        Memories.log(Level.DEBUG, "Sleep: Finished");
    }
}
