package client.utils;

import client.scenes.MainCtrl;
import com.google.inject.Inject;
import javafx.scene.image.Image;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class LanguageManager {
    private Pair<String, Image> language;
    private MainCtrl mainCtrl;
    private ResourceBundle resourceBundle;
    private final ServerUtils serverUtils;
    private final FlagListCell flagListCell;

    @Inject
    public LanguageManager(ServerUtils serverUtils, FlagListCell flagListCell) {
        this.serverUtils = serverUtils;
        this.flagListCell = flagListCell;
        initializeLanguage();
    }

    private void initializeLanguage() {
        String clientLanguage = serverUtils.getConfig().getClientsLanguage();
        if (clientLanguage == null) {
            this.language = flagListCell.getLanguages().get(0);
        } else {
            setLanguageByCode(clientLanguage);
        }
        this.resourceBundle = ResourceBundle.getBundle("messages", new Locale.Builder().setLanguage(this.language.getKey()).build());
    }

    public Pair<String, Image> getLanguage() {
        return language;
    }

    public void setLanguage(Pair<String, Image> language) {
        this.language = language;
        this.resourceBundle = ResourceBundle.getBundle("messages", new Locale.Builder().setLanguage(this.language.getKey()).build());
    }

    public void setLanguageByCode(String language) {
        Optional<Pair<String, Image>> current_language = flagListCell.getLanguages().stream()
                .filter(x -> x.getKey().equals(language))
                .findFirst();
        this.language = current_language.orElse(this.language);
    }

    public void setLanguageText() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", new Locale.Builder().setLanguage(language.getKey()).build());
        this.mainCtrl.setLanguageText(resourceBundle);
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    @Autowired
    public void setMainCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }
}
