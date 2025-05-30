package it.unina.loader;
import java.io.InputStream;
import java.net.URL;

public class MFXloader {
    private MFXloader() {}

    public static URL loadURL(String path) {
        return MFXloader.class.getResource(path);

    }

    public static String load(String path) {
        return loadURL(path).toString();
    }

    public static InputStream loadStream(String name) {
        return MFXloader.class.getResourceAsStream(name);    }
}
