package com.fm.scheduling.ui.appointment;

import com.fm.scheduling.service.SchedulingService;
import com.fm.scheduling.ui.util.UtilUI;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentTableController implements Initializable{

    private UtilUI utilUI = UtilUI.getInstance();

    private final static String CALENDAR_STYLE = "calendar-panel";

    private enum CalendarTypeEnum{
        WEEK("Week", new AppointmentWeekCalendarHelper()),
        MONTH("Month", new AppointmentMonthCalendarHelper());

        String labelCmb;
        AppointmentCalendarHelper appointmentCalendarHelper;

        CalendarTypeEnum(String labelCmb, AppointmentCalendarHelper appointmentCalendarHelper){
            this.labelCmb = labelCmb;
            this.appointmentCalendarHelper = appointmentCalendarHelper;
        }

        public String getLabelCmb() {
            return labelCmb;
        }

        public AppointmentCalendarHelper getAppointmentCalendarHelper() {
            return appointmentCalendarHelper;
        }
    }

    private SchedulingService schedulingService = SchedulingService.getInstance();

    private AppointmentCalendarHelper appointmentCalendarHelper;

    @FXML
    private ComboBox<CalendarTypeEnum> calendarTypeCmb;

    @FXML
    private Pane mainPanel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<CalendarTypeEnum> observableCountry = calendarTypeCmb.getItems();
        //countryCmb.setConverter(new CustomerDetailController.CountryConverter());
        observableCountry.addAll(CalendarTypeEnum.values());
        //cityCmb.setConverter(new CustomerDetailController.CityConverter());
        //fillCityComboBox();
    }

    public void showCalendar(){
        mainPanel.getChildren().clear();
        mainPanel.getStyleClass().add(CALENDAR_STYLE);
        GridPane gridPane = appointmentCalendarHelper.getCalendarPanel();
        mainPanel.getChildren().add(appointmentCalendarHelper.getTitleLabel());
        mainPanel.getChildren().add(gridPane);
        gridPane.setAlignment(Pos.CENTER);
    }

    public void handleCalendarTypeChange(ActionEvent actionEvent) throws IOException {
        appointmentCalendarHelper = calendarTypeCmb.getSelectionModel().getSelectedItem().getAppointmentCalendarHelper();
        showCalendar();
    }

    public void handleNextPeriod(ActionEvent actionEvent) throws IOException {
        if(calendarTypeCmb.getSelectionModel().getSelectedItem() != null) {
            appointmentCalendarHelper.nextPeriod();
            showCalendar();
        }
    }

    public void handlePreviousPeriod(ActionEvent actionEvent) throws IOException {
        if(calendarTypeCmb.getSelectionModel().getSelectedItem() != null) {
            appointmentCalendarHelper.previousPeriod();
            showCalendar();
        }
    }

    public void handleReturnControlPanel(ActionEvent actionEvent) throws IOException {
        utilUI.openUI(actionEvent, UtilUI.UIEnum.CONTROL_PANEL_UI);
    }

    public void handleAddAppointment(ActionEvent actionEvent) throws IOException {
        schedulingService.clearAppointmentSelected();
        utilUI.openUI(actionEvent, UtilUI.UIEnum.APPOINTMENTS_ADD_UI);
    }
}
