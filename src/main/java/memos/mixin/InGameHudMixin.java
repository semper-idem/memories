package memos.mixin;

import memos.features.BorderFeature;
import memos.features.StopwatchFeature;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    private static StopwatchFeature stopwatchFeature = new StopwatchFeature();
    private static BorderFeature borderFeature = new BorderFeature();
    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(MatrixStack matrixStack,float deltaTicks,  CallbackInfo cbi){
        stopwatchFeature.render(matrixStack);
        borderFeature.render(matrixStack);
    }
}
