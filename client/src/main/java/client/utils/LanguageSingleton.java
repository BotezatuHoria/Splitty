package client.utils;


import javafx.scene.image.Image;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

public class LanguageSingleton {
    private static final LanguageSingleton instance = new LanguageSingleton();
    private Pair<String, Image> language;

    @Value("${client.language}")
    private String start_language;

    public LanguageSingleton() {
        Optional<Pair<String, Image>> current_language =  FlagListCell.getLanguages().stream().filter(x -> x.getKey().equals(start_language)).findFirst();
        this.language = current_language.orElseGet(() -> FlagListCell.getLanguages().get(0));
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
