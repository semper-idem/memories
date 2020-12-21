package memos.features;

import memos.config.Config;
import memos.utils.MemosScreenshotUtils;
import memos.utils.Stopwatch;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class StopwatchFeature {

    public void render(MatrixStack matrixStack){
        if(Config.stopwatchVisibility.isVisible() && Stopwatch.getTimeLeft() > 0){
            int x = (int) (MinecraftClient.getInstance().getWindow().getScaledWidth() * (Config.stopwatchX/100f));
            int y = (int) (MinecraftClient.getInstance().getWindow().getScaledHeight() * (Config.stopwatchY/100f));
            String time = MemosScreenshotUtils.formatTimeLeft(Stopwatch.getTimeLeft());
            String text = "["+time+"]";
            MinecraftClient.getInstance().textRenderer.drawWithShadow(matrixStack,text, x, y, 0xFFFFFFFF);
        }
    }

}
