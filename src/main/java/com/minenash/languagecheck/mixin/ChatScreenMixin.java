package com.minenash.languagecheck.mixin;

import com.minenash.languagecheck.LanguageCheck;
import com.minenash.languagecheck.gui.language_options_screen.CheckerGui;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin extends Screen {

	@Shadow protected TextFieldWidget chatField;

	protected ChatScreenMixin(Text title) {super(title);}

	CheckerGui gui = new CheckerGui((ChatScreen) (Object)this);

	long lastChecked = 0;
	boolean needsChecking = false;

	@Inject(method = "onChatFieldUpdate", at = @At("HEAD"))
	private void getText(String text, CallbackInfo info) {
		gui.text = text;
		gui.textField = chatField;
		this.needsChecking = true;
		this.lastChecked = System.currentTimeMillis();
	}

	boolean block = false;

	@Inject(method = "tick", at = @At("HEAD"))
	private void checkText(CallbackInfo info) {
		if (!needsChecking || block)
			return;

		CompletableFuture.runAsync(() -> {
			block = true;
			try {
				gui.matches = LanguageCheck.langTool.check(gui.text);
			} catch (IOException e) {
				e.printStackTrace();
				gui.matches.clear();
			}
			block = false;
		});

		needsChecking = false;
	}

	@Inject(method = "mouseClicked", at = @At("HEAD"))
	private void suggestionClick(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> info) {
		gui.onClick(mouseX, mouseY, button);
	}

	@Inject(method = "render", at = @At("RETURN"))
	private void renderErrors(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
		gui.render(matrices, mouseX, mouseY);
	}

}