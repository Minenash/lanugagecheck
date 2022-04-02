package com.minenash.languagecheck.gui.language_options_screen;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

public class URLByteChannel implements ReadableByteChannel {

    private final ReadableByteChannel rbc;
    private final IntConsumer onRead;

    private int totalByteRead;

    public URLByteChannel(URLConnection connection, IntConsumer onBytesRead) throws IOException {
        this.rbc = Channels.newChannel(connection.getInputStream());
        this.onRead = onBytesRead;
    }

    @Override
    public int read(ByteBuffer dst) throws IOException {
        int nRead = rbc.read(dst);
        notifyBytesRead(nRead);
        return nRead;
    }

    protected void notifyBytesRead(int nRead){
        if(nRead<=0) {
            return;
        }
        totalByteRead += nRead;
        onRead.accept(totalByteRead);
    }
    @Override
    public boolean isOpen() {
        return rbc.isOpen();
    }

    @Override
    public void close() throws IOException {
        rbc.close();
    }

}
