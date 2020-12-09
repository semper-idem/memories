package memos;

import memos.config.Config;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.Level;

public class Stopwatch implements Runnable {
    private static Stopwatch INSTANCE;
    private boolean initialized = false;
    private long unlockAt = 0;
    private boolean lock = false;
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
        INSTANCE.initialized = false;
        INSTANCE.lock = false;
        INSTANCE.unlockAt = 0;
    }

    @Override
    public void run() {
        while(client.isRunning()){
            if(client.world != null && !client.isPaused() && Config.stopwatchEnabled){
                if(!lock){
                    if(initialized){
                        Memories.log(Level.DEBUG, "Making screenshot.");
                        Utils.makeScreenshot();
                        lock();
                    } else {
                        Memories.log(Level.DEBUG, "World initialized, waiting to load properly.");
                        initialized = true;
                        sleep(1);
                        //Let world load
                    }
                } else if(unlockAt < System.currentTimeMillis()/1000) {
                    unlock();
                } else {
                    sleep(1);
                }
            } else {
                sleep(1);
            }
        }

    }

    private void sleep(int min){
        try {
            Memories.log(Level.DEBUG, "Thread sleeping " + (min * 60) + "s");
            Thread.sleep(60000 * min);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void lock(){
        Memories.log(Level.DEBUG, "Lock.");
        lock = true;
        unlockAt = System.currentTimeMillis()/1000 + 60 * (Config.interval - 1);
    }

    private void unlock(){
        Memories.log(Level.DEBUG, "Unlock.");
        lock = false;
    }
}
