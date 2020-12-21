package memos.features;

import memos.config.Config;
import memos.utils.Stopwatch;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class BorderFeature {
    private int len = 30;
    private int thickness = 2;

    public void render(MatrixStack matrixStack){
        if(Config.borderEnabled) {
            if (Stopwatch.getTimeLeft() <= 5 && Stopwatch.getTimeLeft() > 0) {
                if (Stopwatch.getTimeLeft() % 2 == 1) {
                    renderBorder(matrixStack);
                }
            }
        }
    }

    private void renderBorder(MatrixStack matrixStack){
        int offset = 10;
        int left = offset;
        int right = MinecraftClient.getInstance().getWindow().getScaledWidth() - offset;
        int top = offset;
        int bottom = MinecraftClient.getInstance().getWindow().getScaledHeight() - offset;

        renderCorner(matrixStack,left,top);
        renderCorner(matrixStack,right,top);
        renderCorner(matrixStack,right,bottom);
        renderCorner(matrixStack,left,bottom);
    }

    private void renderCorner(MatrixStack matrixStack, int x, int y){
        int a = MinecraftClient.getInstance().getWindow().getScaledWidth()/2 > x ? 1 : -1;
        int b = MinecraftClient.getInstance().getWindow().getScaledHeight()/2 > y ? 1 : -1;
        renderCornerShadow(matrixStack, x,y,a,b);
        renderCornerInner(matrixStack, x,y,a,b);
    }

    private void renderCornerInner(MatrixStack matrixStack, int x, int y, int a, int b){
        int color = 0xFFFFFFFF;
        DrawableHelper.fill(matrixStack,x,y,x + thickness*a, y + thickness*b, color);
        DrawableHelper.fill(matrixStack,x,y + thickness*b,x + thickness*a, y + len*b, color);
        DrawableHelper.fill(matrixStack,x + thickness*a,y,x + len*a, y + thickness*b, color);

    }
    private void renderCornerShadow(MatrixStack matrixStack, int x, int y, int a, int b){
        int shadowColor = 0xFF000000;
        DrawableHelper.fill(matrixStack,x,y,x + thickness*a, y + thickness*b, shadowColor);
        DrawableHelper.fill(matrixStack,x,y + thickness*b,x + thickness*a, y + len*b, shadowColor);
        DrawableHelper.fill(matrixStack,x + thickness*a,y,x + len*a, y + thickness*b, shadowColor);

    }

}
