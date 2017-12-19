package com.fm.scheduling.ui.login;

import com.fm.scheduling.service.SchedulingService;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController extends Application{

    private SchedulingService schedulingService = SchedulingService.getInstance();



    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/login.fxml"));

        Scene scene = new Scene(root);
        //scene.getStylesheets().add(InventoryManagementFXML.class.getResource("../css/style.css").toExternalForm());
        stage.setTitle("Scheduling App");
        stage.setScene(scene);
        stage.show();
    }

    public void handleLogin(ActionEvent actionEvent){
        System.out.println("Testing");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
