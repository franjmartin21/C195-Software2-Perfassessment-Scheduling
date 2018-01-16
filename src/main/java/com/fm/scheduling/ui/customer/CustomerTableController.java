package com.fm.scheduling.ui.customer;

import com.fm.scheduling.domain.Customer;
import com.fm.scheduling.exception.SchedulingException;
import com.fm.scheduling.service.SchedulingService;
import com.fm.scheduling.ui.util.UtilUI;
import com.fm.scheduling.util.UtilMessages;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerTableController implements Initializable{

    private UtilUI utilUI;

    private SchedulingService schedulingService;

    private UtilMessages utilMessages;

    @FXML
    private TableView<Customer> customerTable;

    private final String DELETE_DIALOG_KEY = "dialog.customer.DELETE_BUTTON_CONFIRMATION";

    public CustomerTableController(){
        utilUI = UtilUI.getInstance();
        schedulingService = SchedulingService.getInstance();
        utilMessages = UtilMessages.getInstance();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startTable();
    }

    private void startTable(){
        ObservableList<Customer> customerObservableList = customerTable.getItems();
        customerObservableList.clear();
        try {
            customerObservableList.addAll(schedulingService.getCustomerList());
        } catch (SchedulingException e){
            utilUI.openInformationDialog(utilMessages.getMessageBySchedulingException(e));
        }
    }

    public void handleAddCustomer(ActionEvent actionEvent) throws IOException {
        schedulingService.clearCustomerSelected();
        utilUI.openUI(actionEvent, UtilUI.UIEnum.CUSTOMER_ADD_UI);
    }

    public void handleEditCustomer(ActionEvent actionEvent) throws IOException {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        if(customer == null) utilUI.openInformationDialog(utilMessages.getMessageBySchedulingException(SchedulingException.SchedulingExceptionTypeEnum.NO_ROW_SELECTED));

        else {
            schedulingService.setCustomerSelected(customer);
            utilUI.openUI(actionEvent, UtilUI.UIEnum.CUSTOMER_EDIT_UI);
        }
    }

    public void handleDeleteCustomer(ActionEvent actionEvent) throws IOException {
        try{
            if(utilUI.openConfirmationDialog(utilMessages.getMessageByKey(DELETE_DIALOG_KEY))) {
                Customer customer= customerTable.getSelectionModel().getSelectedItem();
                schedulingService.deleteCustomer(customer);
                startTable();
            }
        } catch (SchedulingException e){
            utilUI.openWarningDialog(utilMessages.getMessageBySchedulingException(e));
        }
    }

    public void handleReturnControlPanel(ActionEvent actionEvent) throws IOException {
        utilUI.openUI(actionEvent, UtilUI.UIEnum.CONTROL_PANEL_UI);
    }
}
