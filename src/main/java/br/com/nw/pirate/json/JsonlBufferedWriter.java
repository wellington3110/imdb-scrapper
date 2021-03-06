package br.com.nw.pirate.json;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class JsonlBufferedWriter {

    private final Gson gson;
    private final BufferedWriter bw;

    private JsonlBufferedWriter(Gson gson, BufferedWriter bw) {

        this.gson = gson;
        this.bw = bw;
    }

    public static JsonlBufferedWriter from(Gson gson, String path) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));
            return new JsonlBufferedWriter(gson, bw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeWithBw(String text) {
        try {
            bw.write(text);
        } catch (IOException e) {
            this.close();
        }
    }

    public void writeAndBreakLine(Object obj) {
        this.writeWithBw(this.gson.toJson(obj) + "\n");
    }

    public void write(Object obj) {
        String json = this.gson.toJson(obj);
        this.writeWithBw(json);

    }

    public void close() {
        try {
            this.bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
