package com.minenash.languagecheck.mixin;

import io.github.cottonmc.cotton.gui.widget.WTabPanel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = WTabPanel.class, remap = false)
public class WTabPanelMixin {

     private static final int NEW_TAB_HEIGHT = 16;

    @ModifyArg(method = "<init>", index = 2, at = @At(value = "INVOKE", ordinal = 1,target = "Lio/github/cottonmc/cotton/gui/widget/WTabPanel;add(Lio/github/cottonmc/cotton/gui/widget/WWidget;II)V"))
    public int changeHeight(int _old) {
        return NEW_TAB_HEIGHT;
    }

    @ModifyArg(method = "add(Lio/github/cottonmc/cotton/gui/widget/WTabPanel$Tab;)V", index = 2, at = @At(value = "INVOKE", target = "Lio/github/cottonmc/cotton/gui/widget/WBox;add(Lio/github/cottonmc/cotton/gui/widget/WWidget;II)V"))
    public int changeHeight2(int _old) {
        return NEW_TAB_HEIGHT + 7;
    }

    @ModifyArg(method = "setSize", index = 1, at = @At(value = "INVOKE", target = "Lio/github/cottonmc/cotton/gui/widget/WBox;setSize(II)V"))
    public int changeHeight3(int _old) {
        return NEW_TAB_HEIGHT;
    }



}
