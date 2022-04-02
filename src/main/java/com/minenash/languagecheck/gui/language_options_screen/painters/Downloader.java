package com.minenash.languagecheck.gui.language_options_screen.painters;

import com.minenash.languagecheck.gui.language_options_screen.URLByteChannel;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import joptsimple.internal.Strings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class Downloader implements BackgroundPainter {

    int dotCounter = 1;
    long fileSize = 1;
    long downloaded = 0;
    long downloadedLast = 0;
    long downloadedStored = 0;
    boolean large;
    String error = null;

    public Downloader(String link, Path dir, boolean large, Consumer<Boolean> onFinish) {
        this.large = large;
        CompletableFuture.runAsync(() -> {
            try {
                URLConnection con = new URL(link).openConnection();
                fileSize = con.getContentLengthLong() / 1000;
                URLByteChannel bc = new URLByteChannel(con, (bytes) -> {
                    long l = Integer.toUnsignedLong(bytes)/1000;
                    if (downloadedLast > l)
                        downloadedStored = downloaded;
                    downloaded = downloadedStored + l;
                    downloadedLast = l;
                });

                FileOutputStream fos = new FileOutputStream(dir.resolve(link.substring(link.lastIndexOf("/")+1)).toFile());
                fos.getChannel().transferFrom(bc, 0, Long.MAX_VALUE);
                onFinish.accept(true);
            }
            catch (Exception e) {
                e.printStackTrace();
                onFinish.accept(false);
            }
        });


    }

    @Override
    public void paintBackground(MatrixStack matrices, int left, int top, WWidget p) {
        int bottom = p.getY()+top+p.getHeight();
        int width = p.getWidth()-12;
        int dots = dotCounter / 20;
        dotCounter = dotCounter >= 79 ? 1 : dotCounter +1;

//        System.out.println("D: " + downloaded + ", FS: " + fileSize + ", P:" + Math.round((float)downloaded / fileSize * width));
        int progress = Math.round((float)downloaded / fileSize * width);

        ScreenDrawing.coloredRect(matrices, left + 5, bottom - 10, width + 2, 5, 0xFF000000);

        if (error == null) {

            String numStr = "§o(" + getSizeStr(downloaded) + " / " + getSizeStr(fileSize) + ")";
            int numStrLength = MinecraftClient.getInstance().textRenderer.getWidth(numStr);

            if (fileSize != 1)
                ScreenDrawing.drawString(matrices, "Downloading" + Strings.repeat('.', dots), left + 5, bottom - 21, 0xFF000000);
            ScreenDrawing.drawString(matrices, numStr, left + p.getWidth() - 6 - numStrLength, bottom - 21, 0xFF000000);
            ScreenDrawing.coloredRect(matrices, left + 6, bottom - 9, width, 3, 0xFFC6C6C6);
            ScreenDrawing.coloredRect(matrices, left + 6, bottom - 9, progress, 3, 0xFF218756);
        }
        else {
            ScreenDrawing.drawString(matrices, "Error: " + error, left + 5, bottom - 21, 0xFF000000);
            ScreenDrawing.coloredRect(matrices, left + 6, bottom - 9, width, 3, 0xFFAA0000);
        }
    }

    public String getSizeStr(long size) {
        if (large && size >= 1000_000)
            return (long)(size/1000_000F*100)/100F + " GB";
        if (size >= 1000_000)
            return (long)(size/1000_000F*10)/10F + " GB";
        if (large)
            return size/1000 + " MB";
        if (size >= 10000)
            return (long)(size/1000F*10)/10F + " MB";
        return size + " KB";
    }
}
