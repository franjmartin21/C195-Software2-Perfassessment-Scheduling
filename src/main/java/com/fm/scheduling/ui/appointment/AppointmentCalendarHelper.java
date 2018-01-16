package com.fm.scheduling.ui.appointment;

import com.fm.scheduling.domain.Appointment;
import com.fm.scheduling.exception.SchedulingException;
import com.fm.scheduling.service.SchedulingService;
import com.fm.scheduling.ui.util.UtilUI;
import com.fm.scheduling.util.UtilMessages;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import javax.rmi.CORBA.Util;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;


public abstract class AppointmentCalendarHelper {

    protected LocalDate dateTime;

    private final static String STYLE_TITLE = "secundary-title";

    private SchedulingService schedulingService;

    private UtilUI utilUI;

    private UtilMessages utilMessages;

    protected List<Appointment> appointmentList;

    public AppointmentCalendarHelper(){
        this.utilMessages = UtilMessages.getInstance();
        this.utilUI = UtilUI.getInstance();
        this.schedulingService = SchedulingService.getInstance();
        this.dateTime = LocalDate.now();

    }

    public Label getTitleLabel(){
        String labelTxt = dateTime.getMonth().getDisplayName(TextStyle.FULL, SchedulingService.LocalesSupported.getLocaleSupported(Locale.getDefault()).getLocale());
        labelTxt = labelTxt + " " + dateTime.getYear();
        Label titleLabel = new Label(labelTxt);
        titleLabel.getStyleClass().add(STYLE_TITLE);
        return titleLabel;
    }

    public void fillAppointmentList(){
        try {
            this.appointmentList = schedulingService.getAppointmentList();
        } catch (SchedulingException e){
            utilUI.openInformationDialog(utilMessages.getMessageBySchedulingException(e));
        }
    }

    public abstract GridPane getCalendarPanel();

    public abstract void nextPeriod();

    public abstract void previousPeriod();

    public ContextMenu getContextMenu(Appointment appointment,final Label label){
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem edit = new MenuItem("Edit");
        MenuItem delete = new MenuItem("Delete");
        contextMenu.getItems().addAll(edit, delete);
        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                AppointmentCalendarHelper.this.schedulingService.setAppointmentSelected(appointment);
                try {
                    utilUI.openUI(actionEvent, UtilUI.UIEnum.APPOINTMENTS_EDIT_UI, label.getScene());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    AppointmentCalendarHelper.this.schedulingService.deleteAppointment(appointment);
                    utilUI.openUI(actionEvent, UtilUI.UIEnum.APPOINTMENTS_TABLE_UI, label.getScene());
                } catch (SchedulingException e) {
                    List<String> messages = utilMessages.getMessageListBySchedulingException(e);
                    utilUI.openWarningDialog(messages);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return contextMenu;
    }
}
