package com.minenash.languagecheck.mixin.libgui;

import io.github.cottonmc.cotton.gui.widget.WScrollPanel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = WScrollPanel.class, remap = false)
public class WScrollPanelMixin {

    @ModifyArg(method = "onMouseScroll", index = 2, at = @At(value = "INVOKE", target = "Lio/github/cottonmc/cotton/gui/widget/WScrollBar;onMouseScroll(IID)Lio/github/cottonmc/cotton/gui/widget/data/InputResult;"))
    private double fastScroll(double amount) {
        return amount*2;
    }
}
