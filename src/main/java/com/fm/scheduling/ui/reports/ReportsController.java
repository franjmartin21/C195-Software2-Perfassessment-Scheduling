package com.fm.scheduling.ui.reports;

import com.fm.scheduling.exception.SchedulingException;
import com.fm.scheduling.ui.util.UtilUI;
import com.fm.scheduling.util.UtilMessages;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ReportsController implements Initializable{

    private enum TypeReportEnum{
        APPOINTMENT_TYPES_BY_MONTH("Appointment Types By Month", new ReportAppointmentTypesByMonthHelper()),
        SCHEDULE_BY_CONSULTANT("Schedule By Consultant", new ReportScheduleByConsultantHelper()),
        SCHEDULE_BY_LOCATION("Schedule By Location", new ReportScheduleByLocationHelper());

        private String labelTxt;

        private ReportHelper reportHelper;

        TypeReportEnum(String labelTxt, ReportHelper reportHelper){
            this.labelTxt = labelTxt;
            this.reportHelper = reportHelper;
        }

        public String getLabelTxt() {
            return labelTxt;
        }

        public ReportHelper getReportHelper() {
            return reportHelper;
        }
    }

    private UtilUI utilUI = UtilUI.getInstance();

    private UtilMessages utilMessages = UtilMessages.getInstance();

    private ReportHelper reportHelper;

    private final static String STYLE = "calendar-panel";

    @FXML
    private ComboBox<TypeReportEnum> calendarTypeCmb;

    @FXML
    private ComboBox<String> criteriaCmb;

    @FXML
    private Pane mainPanel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<TypeReportEnum> observableCustomer = calendarTypeCmb.getItems();
        calendarTypeCmb.setConverter(new TypeReportConverter());
        observableCustomer.addAll(TypeReportEnum.values());
    }

    private void fillReport(String filter) throws SchedulingException{
        mainPanel.getStyleClass().add(STYLE);
        mainPanel.getChildren().clear();
        TableView tableView = reportHelper.getReport(filter);
        if(tableView != null)
            mainPanel.getChildren().add(reportHelper.getReport(filter));
    }

    public void handleReturnControlPanel(ActionEvent actionEvent) throws IOException {
        utilUI.openUI(actionEvent, UtilUI.UIEnum.CONTROL_PANEL_UI);

    }

    public void handleReportTypeChange(ActionEvent actionEvent){
        try {
            reportHelper = calendarTypeCmb.getSelectionModel().getSelectedItem().getReportHelper();
            ObservableList<String> criteriaCmbItems = criteriaCmb.getItems();
            criteriaCmbItems.clear();
            criteriaCmbItems.addAll(reportHelper.getComboValues());
            criteriaCmb.getSelectionModel().select(reportHelper.getDefaultElement());
        } catch (SchedulingException e){
            List<String> messages = utilMessages.getMessageListBySchedulingException(e);
            utilUI.openWarningDialog(messages);
        }
    }

    public void handleReportCriteriaChange(ActionEvent actionEvent){
        try{
            String filter = criteriaCmb.getSelectionModel().getSelectedItem();
            fillReport(filter);
        } catch (SchedulingException e){
            List<String> messages = utilMessages.getMessageListBySchedulingException(e);
            utilUI.openWarningDialog(messages);
        }
    }

    public class TypeReportConverter extends StringConverter<TypeReportEnum> {

        private Map<String, TypeReportEnum> mapTypeReport = new HashMap<String, TypeReportEnum>();

        @Override
        public String toString(TypeReportEnum typeReportEnum) {
            mapTypeReport.put(typeReportEnum.getLabelTxt(), typeReportEnum);
            return typeReportEnum.getLabelTxt();
        }

        @Override
        public TypeReportEnum fromString(String descriptionLabel) {
            return mapTypeReport.get(descriptionLabel);
        }
    }
}
