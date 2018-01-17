package com.fm.scheduling.ui.login;

import com.fm.scheduling.domain.Appointment;
import com.fm.scheduling.exception.SchedulingException;
import com.fm.scheduling.service.SchedulingService;
import com.fm.scheduling.ui.util.UtilUI;
import com.fm.scheduling.util.UtilLog;
import com.fm.scheduling.util.UtilMessages;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LoginController extends Application{

    private static final int MINUTES_START_TIME_APPOINTMENTS_TO_SHOW = 15;

    private static final String APPOINTMENT_ALERT_TITLE = "Appointment reminder";

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("hh:mm");

    private SchedulingService schedulingService = SchedulingService.getInstance();

    private UtilMessages utilMessages = UtilMessages.getInstance();

    private UtilUI utilUI = UtilUI.getInstance();

    private UtilLog utilLog = UtilLog.getInstance();

    @FXML
    private TextField usernameTxt;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private Label messageLabel;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/login.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(LoginController.class.getResource("../css/style.css").toExternalForm());
        stage.setTitle("Scheduling App");
        stage.setScene(scene);
        stage.show();
    }

    public void handleLogin(ActionEvent actionEvent) throws IOException {
        boolean loggedIn = false;
        try {
            loggedIn = schedulingService.login(usernameTxt.getText(), passwordTxt.getText());
        } catch (SchedulingException e){
            utilMessages.getMessageBySchedulingException(e);
        }
        if(loggedIn){
            showIncomingAppointments();
            utilLog.append(schedulingService.getUserLoggedIn());
            utilUI.openUI(actionEvent, UtilUI.UIEnum.CONTROL_PANEL_UI);
        } else {
            messageLabel.setText(utilMessages.getMessageByKey("login.problem"));
        }
    }

    private void showIncomingAppointments(){
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusMinutes(MINUTES_START_TIME_APPOINTMENTS_TO_SHOW);
        try {
            List<Appointment> appointmentList = schedulingService.getAppointmentOnLocalDateTimeRange(startTime, endTime);
            if(appointmentList != null && !appointmentList.isEmpty()){
                StringBuilder sb = new StringBuilder();
                for(Appointment appointment: appointmentList){
                    sb.append(TIME_FORMAT.format(appointment.getStart())).append(" - ");
                    sb.append(TIME_FORMAT.format(appointment.getEnd())).append(" ");
                    sb.append(appointment.getTitle()).append(" - ");
                    sb.append(appointment.getCustomerName()).append("\n");
                }
                utilUI.openInformationDialog(APPOINTMENT_ALERT_TITLE, sb.toString());
            }
        } catch (SchedulingException e){
            List<String> messages = utilMessages.getMessageListBySchedulingException(e);
            utilUI.openWarningDialog(messages);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
