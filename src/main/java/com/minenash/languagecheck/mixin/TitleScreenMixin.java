package com.minenash.languagecheck.mixin;

import com.minenash.languagecheck.gui.language_options_screen.LanguageOptionsScreen;
import com.minenash.languagecheck.gui.language_options_screen.LanguageOptionsScreenDef;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    protected TitleScreenMixin(Text title) {super(title);}

    @Inject(method = "init", at = @At("TAIL"))
    public void test(CallbackInfo info) {

        addDrawableChild(new ButtonWidget(this.width / 2 + 104, this.height / 4 + 48, 20, 20, new LiteralText("L"),
                button -> client.setScreen(new LanguageOptionsScreen(new LanguageOptionsScreenDef()))));

    }

}
