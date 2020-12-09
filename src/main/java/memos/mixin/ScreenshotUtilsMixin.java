package memos.mixin;


import memos.Utils;
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
     * @author me
     */
    @Overwrite
    public static void saveScreenshot(File gameDirectory, int framebufferWidth, int framebufferHeight, Framebuffer framebuffer, Consumer<Text> messageReceiver) {
        ScreenshotUtils.saveScreenshot(gameDirectory, Utils.getFilename(), framebufferWidth, framebufferHeight, framebuffer, Utils.getMessageReceiver());
    }
}
