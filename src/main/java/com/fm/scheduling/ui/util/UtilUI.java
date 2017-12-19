package com.fm.scheduling.ui.util;

import com.fm.scheduling.ui.login.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


/**
 * Created by francisco on 5/17/17.
 */
public class UtilUI {

    private static UtilUI instance;

    private final String CSS_PATH = "../css/style.css";

    public enum UIEnum {
        INVENTORY_UI("login.fxml", "Login", 1700, 700, true);

        private String uiPath;
        private String title;
        private int width;
        private int height;
        private boolean hideParent;

        UIEnum(String uiPath, String title, int width, int height, boolean hideParent){
            this.uiPath = uiPath;
            this.title = title;
            this.width = width;
            this.height = height;
            this.hideParent = hideParent;
        }

        public String getUiPath() {
            return uiPath;
        }

        public String getTitle() {
            return title;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public boolean isHideParent() {
            return hideParent;
        }
    }

    public static synchronized UtilUI getInstance(){
        if(instance == null) instance = new UtilUI();

        return instance;
    }

    public void openUI(ActionEvent actionEvent, UIEnum uiEnum) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(uiEnum.getUiPath()));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle(uiEnum.getTitle());
        Scene scene = new Scene(root, uiEnum.getWidth(), uiEnum.getHeight());
        scene.getStylesheets().add(LoginController.class.getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
        stage.show();
        // Hide this current window (if this is what you want)
        if(uiEnum.isHideParent()) ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void openWarningDialog(String content){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Validation Problem");
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void openWarningDialog(List<String> contentList){
        if(contentList == null || contentList.isEmpty()) return;

        StringBuilder sb = new StringBuilder();
        for(String message: contentList)
            sb.append(message).append("\r");

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Validation Problem");
        alert.setContentText(sb.toString());
        alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label)node).setMinHeight(Region.USE_PREF_SIZE));
        alert.showAndWait();
    }

    public void openInformationDialog(String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Information");
        alert.setContentText(content);
        alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label)node).setMinHeight(Region.USE_PREF_SIZE));
        alert.showAndWait();
    }

    public boolean openConfirmationDialog(String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Confirmation Dialog");
        alert.setContentText(content);
        alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label)node).setMinHeight(Region.USE_PREF_SIZE));
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }


}
