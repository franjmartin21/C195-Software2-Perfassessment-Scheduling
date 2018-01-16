package com.fm.scheduling.ui.controlpanel;

import com.fm.scheduling.ui.util.UtilUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ControlPanelController{

    private UtilUI utilUI = UtilUI.getInstance();

    @FXML
    private Button customerBtn;

    @FXML
    private Button appointmentsBtn;

    @FXML
    private Button reportsBtn;


    public void handleGoCustomerPanel(ActionEvent actionEvent) throws IOException {
        utilUI.openUI(actionEvent, UtilUI.UIEnum.CUSTOMER_TABLE_UI);
    }

    public void handleGoAppointmentsPanel(ActionEvent actionEvent) throws IOException {
        utilUI.openUI(actionEvent, UtilUI.UIEnum.APPOINTMENTS_TABLE_UI);
    }

    public void handleGoReportsPanel(ActionEvent actionEvent) throws IOException {
        utilUI.openUI(actionEvent, UtilUI.UIEnum.REPORTS_UI);
    }
}
