package com.minenash.languagecheck.mixin;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = ScreenDrawing.class, remap = false)
public class ScreenDrawingMixin {

    @ModifyArg( method = "drawString(Lnet/minecraft/client/util/math/MatrixStack;Ljava/lang/String;III)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/client/util/math/MatrixStack;Ljava/lang/String;FFI)I"),
            index = 4)
    private static int changeLightDefaultColor1(int old) { return change(old); }

    @ModifyArg( method = "drawString(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/OrderedText;III)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/OrderedText;FFI)I"),
            index = 4)
    private static int changeLightDefaultColor2(int old) { return change(old); }

    @ModifyArg( method = "drawString(Lnet/minecraft/client/util/math/MatrixStack;Ljava/lang/String;Lio/github/cottonmc/cotton/gui/widget/data/HorizontalAlignment;IIII)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/client/util/math/MatrixStack;Ljava/lang/String;FFI)I"),
                index = 4)
    private static int changeLightDefaultColor3(int old) { return change(old); }

    @ModifyArg( method = "drawString(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/OrderedText;Lio/github/cottonmc/cotton/gui/widget/data/HorizontalAlignment;IIII)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/OrderedText;FFI)I"),
        index = 4)
    private static int changeLightDefaultColor4(int old) { return change(old); }

    @ModifyArg( method = "drawStringWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Ljava/lang/String;Lio/github/cottonmc/cotton/gui/widget/data/HorizontalAlignment;IIII)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Ljava/lang/String;FFI)I"),
        index = 4)
    private static int changeLightDefaultColor5(int old) { return change(old); }

    @ModifyArg( method = "drawStringWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/OrderedText;Lio/github/cottonmc/cotton/gui/widget/data/HorizontalAlignment;IIII)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/OrderedText;FFI)I"),
            index = 4)
    private static int changeLightDefaultColor6(int old) { return change(old); }

    private static int change(int old) {
        if (old == WLabel.DEFAULT_TEXT_COLOR)
            return 0x000000;
        if (old == 0xEEEEEE)
            return 0xFFFFFF;
        return old;
    }

}
