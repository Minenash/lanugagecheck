package com.minenash.languagecheck.gui.language_options_screen;

import com.minenash.languagecheck.mixin.TextFieldWidgetAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import org.languagetool.rules.RuleMatch;

import java.util.ArrayList;
import java.util.List;

public class CheckerGui {

    private static final MinecraftClient client = MinecraftClient.getInstance();
    private final ChatScreen screen;
    public TextFieldWidget textField;

    public CheckerGui(ChatScreen screen) {
        this.screen = screen;
    }




    public List<RuleMatch> matches = new ArrayList<>();
    public String text = "";

    private int open = -1;
    private int openStart = 0;
    private int openEnd = 0;

    public void onClick(double mouseX, double mouseY, int button) {
        if (mouseX < openStart || mouseX > openEnd || open == -1)
            return;

        RuleMatch match = this.matches.get(open);
        List<String> suggestions = match.getSuggestedReplacements();


        int size = suggestions.size();
        int top = screen.height - 16 - 9 - size*9;
        if (mouseY > top && mouseY < screen.height - 16 - 9) {
            int index = size - ((int)mouseY - top)/9 - 1;
            if (index >= 0 && index <= size)
               textField.setText(text.substring(0, match.getFromPos()) + suggestions.get(index) + text.substring(match.getToPos()));
        }

    }


    public int textWidth(String text) {
        return client.textRenderer.getWidth(text);
    }


    private int openPrefixWidth = 0;
    private int openErrorWidth = 0;
    private int openMinY = Integer.MAX_VALUE;

    public void render(MatrixStack matrices, int mouseX, int mouseY) {

        String editorMode = "[ALT] Quick Fix Mode";
        int width = textWidth(editorMode);
        DrawableHelper.fill(matrices, screen.width - width - 5, 2, screen.width - 2, 13, 0xAA000000);
        client.textRenderer.drawWithShadow(matrices, editorMode, screen.width - width - 3, 4, 0xDDDDDD);

        for (int i = 0; i < matches.size(); i++) {
            RuleMatch match = matches.get(i);

            int fromIndex = Math.max(0, Math.min(text.length(), match.getFromPos()) - ((TextFieldWidgetAccessor)textField).getFirstCharacterIndex());
            int toIndex = Math.min(text.length(), match.getToPos()) - ((TextFieldWidgetAccessor)textField).getFirstCharacterIndex();


            if (toIndex - fromIndex < 1)
                continue;

            int prefixWidth = client.textRenderer.getWidth(text.substring(0, fromIndex));
            int errorWidth = client.textRenderer.getWidth(text.substring(fromIndex, toIndex));

            int color = 0xFFFFFF00;

            boolean inX = mouseX >= 4 + prefixWidth && mouseX <= 3 + prefixWidth + errorWidth;


            if (inX && (i == open || mouseY > screen.height - 16)) {
                open = i;
                openPrefixWidth = prefixWidth;
                openErrorWidth = errorWidth;
                openMinY = screen.height - 16 - 11 - match.getSuggestedReplacements().size()*9;
            }

            if (i == open) {
                String error = match.getRule().getCategory().getName() + ": " + match.getMessage()
                        .replace("<suggestion>", "§e§o")
                        .replace("</suggestion>", "§r");

                color = 0xFF55FF55;

                DrawableHelper.fill(matrices, 2, screen.height - 9 - 6, screen.width -2, screen.height - 26, 0xAA000000);
                client.textRenderer.drawWithShadow(matrices, error, 4, screen.height - 24, 0xDDDDDD);

                if (!match.getSuggestedReplacements().isEmpty())
                    renderSuggestions(matrices, match.getSuggestedReplacements(), prefixWidth + errorWidth/2.0 + 4);
            }


            DrawableHelper.fill(matrices, 5 + prefixWidth, screen.height - 2, 2 + prefixWidth + errorWidth, screen.height - 3, color);

        }

        if ( shouldResetOpen(mouseX, mouseY, openPrefixWidth, openErrorWidth) ) {
            open = -1;
            openPrefixWidth = 0;
            openErrorWidth = 0;
            openMinY = Integer.MAX_VALUE;

        }

    }

    private boolean shouldResetOpen(int mouseX, int mouseY, int prefixWidth, int errorWidth) {
        if (mouseX >= 4 + prefixWidth && mouseX <= 3 + prefixWidth + errorWidth && mouseY > screen.height - 16)
            return false;
        if (mouseY <= screen.height - 16 && mouseY > screen.height - 16 - 11)
            return false;

        if (mouseY > openMinY && mouseX >= openStart && mouseX <= openEnd)
            return false;


        return true;
    }

    private void renderSuggestions(MatrixStack matrices, List<String> suggestions, double centerX) {

        int y = screen.height - 16 - 9 - 2;

        int[] lengths = new int[suggestions.size()];
        int max_length = 0;

        for (int i = 0; i < suggestions.size(); i++) {
            lengths[i] = textWidth(suggestions.get(i));
            if (lengths[i] > max_length)
                max_length = lengths[i];
        }

        openStart = (int) (centerX - max_length/2.0 - 2);
        openEnd = (int)(centerX + max_length/2.0 + 1);

        DrawableHelper.fill(matrices, openStart, y - suggestions.size()*9 - 2, openEnd, y, 0xAA000000);

        for (int i = 0; i < suggestions.size(); i++)
            client.textRenderer.drawWithShadow(matrices, suggestions.get(i), (int)(centerX - lengths[i]/2.0), y-=9, 0xFFFFFFFF);

    }



}
