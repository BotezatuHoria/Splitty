package client.utils;


import client.Config;
import client.scenes.MainCtrl;
import javafx.scene.image.Image;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class LanguageSingleton {
    private static final LanguageSingleton instance = new LanguageSingleton();
    private Pair<String, Image> language;
    private MainCtrl mainCtrl;

    public LanguageSingleton() {
        String startLanguage = new Config().getClientsLanguage();
        Optional<Pair<String, Image>> current_language =  FlagListCell.getLanguages().stream().filter(x -> x.getKey().equals(startLanguage)).findFirst();
        this.language = current_language.orElseGet(() -> FlagListCell.getLanguages().get(0));
    }

    public Pair<String, Image> getLanguage() {
        return language;
    }

    public void setLanguage(Pair<String, Image> language) {
        this.language = language;
    }

    public void setLanguageText() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", new Locale.Builder().setLanguage(language.getKey()).build());
        mainCtrl.setLanguageText(resourceBundle);
    }

    public void setMainCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    public static LanguageSingleton getInstance() {
        return instance;
    }
}
