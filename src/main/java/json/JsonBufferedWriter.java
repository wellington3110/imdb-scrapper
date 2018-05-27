package json;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.IOException;

public class JsonBufferedWriter {

    private Gson gson;
    private BufferedWriter bw;
    private boolean first = true;

    public JsonBufferedWriter(Gson gson, BufferedWriter bw) {

        this.gson = gson;
        this.bw = bw;
        this.openArray();
    }

    public void openArray() {
        this.writeWithBw("[");
    }

    public void closeArray() {
        this.writeWithBw("]");
    }

    private void writeWithBw(String text) {
        try {
            bw.write(text);
        } catch (IOException e) {
            this.close();
        }
    }

    public void write(Object obj) {
        if(!first) {
            this.writeWithBw(",");
        } else {
            first = false;
        }
        this.writeWithBw(this.gson.toJson(obj));

    }

    public void close() {
        try {
            this.closeArray();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
