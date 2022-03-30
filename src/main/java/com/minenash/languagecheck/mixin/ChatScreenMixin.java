//package com.minenash.languagecheck.mixin;
//
//import com.minenash.languagecheck.LanguageCheck;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.gui.DrawableHelper;
//import net.minecraft.client.gui.screen.ChatScreen;
//import net.minecraft.client.gui.screen.Screen;
//import net.minecraft.client.gui.widget.TextFieldWidget;
//import net.minecraft.client.util.math.MatrixStack;
//import net.minecraft.text.Text;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//
//@Mixin(ChatScreen.class)
//public abstract class ChatScreenMixin extends Screen {
//	@Shadow protected TextFieldWidget chatField;
//
//	@Shadow protected abstract void setText(String text);
//
//	protected ChatScreenMixin(Text title) {super(title);}
//
//	MinecraftClient client = MinecraftClient.getInstance();
//
//	long lastChecked = 0;
//	boolean needsChecking = false;
//	String text = "";
//	List<RuleMatch> matches = new ArrayList<>();
//
//	@Inject(method = "onChatFieldUpdate", at = @At("HEAD"))
//	private void getText(String text, CallbackInfo info) {
//		this.text = text;
//		this.needsChecking = true;
//		this.lastChecked = System.currentTimeMillis();
//	}
//
//	boolean block = false;
//
//	@Inject(method = "tick", at = @At("HEAD"))
//	private void checkText(CallbackInfo info) {
//		if (!needsChecking || block | System.currentTimeMillis() - lastChecked < 300)
//			return;
//
//		CompletableFuture.runAsync(() -> {
//			block = true;
////			try {
////				matches = LanguageCheck.langTool.check(text);
////			} catch (IOException e) {
////				e.printStackTrace();
////				matches.clear();
////			}
//			block = false;
//		});
//
//
//		needsChecking = false;
//	}
//
//	@Inject(method = "mouseClicked", at = @At("HEAD"))
//	private void suggestionClick(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> info) {
//		if (mouseX < openStart || mouseX > openEnd || open == -1)
//			return;
//
//		RuleMatch match = this.matches.get(open);
//		List<String> suggestions = match.getSuggestedReplacements();
//
//
//		int size = suggestions.size();
//		int top = height - 16 - size*9;
//		if (mouseY > top && mouseY < height - 16) {
//			int index = size - ((int)mouseY - top)/9 - 1;
//			if (index >= 0 && index <= size)
//				setText(text.substring(0, match.getFromPos()) + suggestions.get(index) + text.substring(match.getToPos()));
//		}
//
//	}
//
//	int open = 0;
//	int openStart = 0;
//	int openEnd = 0;
//
//	@Inject(method = "render", at = @At("RETURN"))
//	private void renderErrors(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
//
//
//
//		boolean set = false;
//
//		int y = 1;
//		for (int i = 0; i < matches.size(); i++) {
//			RuleMatch match = matches.get(i);
//			String error = match.getFromPos() + "-" + match.getToPos() + ": " + match.getMessage();
//			DrawableHelper.fill(matrices, 2, y, 4 + client.textRenderer.getWidth(error), y + 9, 0x44000000);
//			client.textRenderer.drawWithShadow(matrices, error, 4, y, 0xFFFFFF);
//			y+=9;
//
//			int fromIndex = Math.max(0, Math.min(text.length(), match.getFromPos()) - ((TextFieldWidgetAccessor)chatField).getFirstCharacterIndex());
//			int toIndex = Math.min(text.length(), match.getToPos()) - ((TextFieldWidgetAccessor)chatField).getFirstCharacterIndex();
//
//
//			if (toIndex - fromIndex < 1)
//			  continue;
//
//			int prefixWidth = client.textRenderer.getWidth(text.substring(0, fromIndex));
//			int errorWidth = client.textRenderer.getWidth(text.substring(fromIndex, toIndex));
//
//			int color = 0xFFFFFF00;
//
//			boolean inX = mouseX >= 4 + prefixWidth && mouseX <= 3 + prefixWidth + errorWidth;
//
//
//			if (inX && (i == open || mouseY > height - 20)) {
//				color = 0xFF55FF55;
//				open = i;
//				set = true;
//				if (!match.getSuggestedReplacements().isEmpty())
//					renderSuggestions(matrices, match.getSuggestedReplacements(), prefixWidth + errorWidth/2.0 + 4);
//			}
//
//			DrawableHelper.fill(matrices, 5 + prefixWidth, height - 2, 2 + prefixWidth + errorWidth, height - 3, color);
//
//		}
//
//		if (!set)
//			open = -1;
//
//	}
//
//	private void renderSuggestions(MatrixStack matrices, List<String> suggestions, double centerX) {
//
//		int y = height - 16;
//
//		int[] lengths = new int[suggestions.size()];
//		int max_length = 0;
//
//		for (int i = 0; i < suggestions.size(); i++) {
//			lengths[i] = client.textRenderer.getWidth(suggestions.get(i));
//			if (lengths[i] > max_length)
//				max_length = lengths[i];
//		}
//
//		openStart = (int) (centerX - max_length/2.0 - 2);
//		openEnd = (int)(centerX + max_length/2.0 + 1);
//
//		DrawableHelper.fill(matrices, openStart, y - suggestions.size()*9 - 2, openEnd, y, 0x88000000);
//
//		for (int i = 0; i < suggestions.size(); i++)
//			client.textRenderer.drawWithShadow(matrices, suggestions.get(i), (int)(centerX - lengths[i]/2.0), y-=9, 0xFFFFFFFF);
//
//	}
//
//}
