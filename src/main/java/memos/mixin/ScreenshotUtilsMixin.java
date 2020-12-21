package memos.mixin;


import memos.utils.MemosScreenshotUtils;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.util.ScreenshotUtils;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.io.File;
import java.util.function.Consumer;

@Mixin(ScreenshotUtils.class)
public class ScreenshotUtilsMixin {


    /**
     * @author memories
     * @reason changing screenshot directory and name
     */
    @Overwrite
    public static void saveScreenshot(File gameDirectory, int framebufferWidth, int framebufferHeight, Framebuffer framebuffer, Consumer<Text> messageReceiver) {
            ScreenshotUtils.saveScreenshot(gameDirectory, MemosScreenshotUtils.getLocation(), framebufferWidth, framebufferHeight, framebuffer, MemosScreenshotUtils.getMessageReceiver());
    }
}
