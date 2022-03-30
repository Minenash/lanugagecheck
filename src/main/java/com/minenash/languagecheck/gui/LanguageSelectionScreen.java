package com.minenash.languagecheck.gui;

import com.minenash.languagecheck.Languages;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.option.LanguageOptionsScreen;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class LanguageSelectionScreen extends Screen {

    private LanguageSelectionListWidget languageSelectionList;
    private final Screen parent;

    public LanguageSelectionScreen(Screen parent) {
        super(new LiteralText("Language Selector"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.languageSelectionList = new LanguageSelectionListWidget(this.client);
        this.addSelectableChild(this.languageSelectionList);

        this.addDrawableChild(new ButtonWidget(this.width / 2 - 75, this.height - 38, 150, 20, ScreenTexts.DONE, button -> {
            LanguageSelectionListWidget.LanguageEntry languageEntry = this.languageSelectionList.getSelectedOrNull();
            if (languageEntry != null) {
                System.out.println(languageEntry.language + " (" + languageEntry.variant + ")");
            }
            this.client.setScreen(this.parent);
        }));
        super.init();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.languageSelectionList.render(matrices, mouseX, mouseY, delta);
        LanguageOptionsScreen.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 12, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Environment(value= EnvType.CLIENT)
    class LanguageSelectionListWidget extends AlwaysSelectedEntryListWidget<LanguageSelectionListWidget.LanguageEntry> {
        public LanguageSelectionListWidget(MinecraftClient client) {
            super(client, LanguageSelectionScreen.this.width / 2, LanguageSelectionScreen.this.height, 32, LanguageSelectionScreen.this.height - 45 + 4, 16);

            for (var entry : Languages.languages.entrySet()) {
                String language = entry.getKey();

                this.addEntry(new LanguageEntry(entry.getValue().get(0)));

                for (Languages.Entry variant : entry.getValue()) {
                    this.addEntry(new LanguageEntry(variant));
                }
            }

            if (this.getSelectedOrNull() != null) {
                this.centerScrollOn(this.getSelectedOrNull());
            }
        }

        @Override
        protected int getScrollbarPositionX() {
            return super.getScrollbarPositionX() + 20;
        }

        @Override
        public int getRowWidth() {
            return super.getRowWidth();
        }

        @Override
        protected boolean isFocused() {
            return LanguageSelectionScreen.this.getFocused() == this;
        }

        @Environment(value=EnvType.CLIENT)
        public class LanguageEntry extends AlwaysSelectedEntryListWidget.Entry<LanguageEntry> {
            final String language;
            final String variant;

            public LanguageEntry(Languages.Entry entry) {
                this.language = entry.language();
                this.variant = entry.variant();
            }

            @Override
            public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
                String string = variant == null ? language : "   " + variant;

                LanguageSelectionScreen.this.textRenderer.drawWithShadow(matrices, string, 20, y + 3, 0xFFFFFF, true);
            }

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                if (button == 0) {
                    this.onPressed();
                    return true;
                }
                return false;
            }

            private void onPressed() {
                LanguageSelectionListWidget.this.setSelected(this);
            }

            @Override
            public Text getNarration() {
                return new TranslatableText("narrator.select", language + " " + variant);
            }
        }

    }


}
