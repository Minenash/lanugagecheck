package com.minenash.languagecheck.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(targets = "io.github.cottonmc.cotton.gui.widget.WTabPanel$WTab", remap = false)
public class WTabPanelTabMixin {

    @Shadow private boolean selected;

    @ModifyArg(method = "paint", index = 4, at = @At(value = "INVOKE", target = "Lio/github/cottonmc/cotton/gui/client/ScreenDrawing;drawString(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/OrderedText;Lio/github/cottonmc/cotton/gui/widget/data/HorizontalAlignment;IIII)V"))
    private int moveSelectedTextUp(int old) {
        return selected ? old-2 : old;
    }

}
