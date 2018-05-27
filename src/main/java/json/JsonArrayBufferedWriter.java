package json;

import com.google.gson.Gson;

import java.io.*;

public class JsonArrayBufferedWriter {

    private Gson gson;
    private BufferedWriter bw;
    private boolean first = true;

    private JsonArrayBufferedWriter(Gson gson, BufferedWriter bw) {

        this.gson = gson;
        this.bw = bw;
        this.openArray();
    }



    public static JsonArrayBufferedWriter from(Gson gson, String path) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));
            return new JsonArrayBufferedWriter(gson, bw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void openArray() {
        this.writeWithBw("[");
    }

    private void closeArray() {
        this.writeWithBw("]");
    }

    private void writeWithBw(String text) {
        try {
            bw.write(text);
        } catch (IOException e) {
            this.closeBw();
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
        this.closeArray();
        closeBw();

    }

    private void closeBw() {
        try {
            this.bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
