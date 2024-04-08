package client.utils;

import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class FlagListCell extends ListCell<Pair<String, Image>> {
    static List<Pair<String, Image>> languages = List.of(
            new ImmutablePair<>("en", new Image("/images/uk_flag.png")),
            new ImmutablePair<>("nl", new Image("/images/nl_flag.png"))
    );

    @Override
    protected void updateItem(Pair<String, Image> item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            ImageView flagView = new ImageView(item.getValue());
            flagView.setFitHeight(17); // Adjust size as needed
            flagView.setFitWidth(40);

            setGraphic(flagView);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY); // Display only the graphic
        }
    }

    public static List<Pair<String, Image>> getLanguages() {
        return languages;
    }
}