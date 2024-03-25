package client.utils;


import javafx.scene.image.Image;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class LanguageSingleton {
    private static final LanguageSingleton instance = new LanguageSingleton();
    private Pair<String, Image> language;

    public LanguageSingleton() {
        this.language = FlagListCell.getLanguages().get(0);
    }

    public Pair<String, Image> getLanguage() {
        return language;
    }

    public void setLanguage(Pair<String, Image> language) {
        this.language = language;
    }

    public static LanguageSingleton getInstance() {
        return instance;
    }
}
