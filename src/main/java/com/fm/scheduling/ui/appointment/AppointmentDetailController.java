package com.fm.scheduling.ui.appointment;

import com.fm.scheduling.domain.Address;
import com.fm.scheduling.domain.Appointment;
import com.fm.scheduling.domain.Customer;
import com.fm.scheduling.exception.SchedulingException;
import com.fm.scheduling.service.SchedulingService;
import com.fm.scheduling.ui.customer.CustomerDetailController;
import com.fm.scheduling.ui.util.UtilUI;
import com.fm.scheduling.util.UtilMessages;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class AppointmentDetailController implements Initializable{

    enum OperationEnum {
        ADD("Add Appointment"),
        MODIFY("Modify Appointment");

        private String labelTxt;

        OperationEnum(String labelTxt){
            this.labelTxt = labelTxt;
        }

        public String getLabelTxt() {
            return labelTxt;
        }
    }

    private final String CANCEL_DIALOG_KEY = "dialog.appointment.CANCEL_BUTTON_CONFIRMATION";

    private Appointment appointment;

    @FXML
    private Label operationLbl;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField titleTxt;

    @FXML
    private ComboBox<Customer> customerCmb;

    @FXML
    private ComboBox<Appointment.DescriptionEnum> descriptionCmb;

    @FXML
    private ComboBox<Appointment.LocationEnum> locationCmb;

    @FXML
    private TextField contactTxt;

    @FXML
    private TextField urlTxt;

    @FXML
    private DateTimePicker startDateTimePicker;

    @FXML
    private DateTimePicker endDateTimePicker;

    private OperationEnum operation;

    private SchedulingService schedulingService;

    private UtilMessages utilMessages;

    private UtilUI utilUI;

    public AppointmentDetailController(){
        this.schedulingService = SchedulingService.getInstance();
        this.utilMessages = UtilMessages.getInstance();
        this.utilUI = UtilUI.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            fillComboBoxes();
            operation = OperationEnum.ADD;
            operationLbl.setText(operation.getLabelTxt());

            if (schedulingService.getAppointmentSelected() != null) {
                operation = OperationEnum.MODIFY;
                appointment = schedulingService.getAppointmentSelected();
                informFields();
            }
        } catch (SchedulingException e){
            List<String> messages = utilMessages.getMessageListBySchedulingException(e);
            utilUI.openWarningDialog(messages);
        }
    }

    private void informFields() throws SchedulingException{
        operationLbl.setText(operation.getLabelTxt());

        idTxt.setText(String.valueOf(appointment.getAppointmentId()));
        titleTxt.setText(appointment.getTitle());
        customerCmb.getSelectionModel().select(appointment.getCustomer());
        descriptionCmb.getSelectionModel().select(appointment.getDescription());
        locationCmb.getSelectionModel().select(appointment.getLocation());
        contactTxt.setText(appointment.getContact());
        urlTxt.setText(appointment.getUrl());
        startDateTimePicker.setDateTimeValue(appointment.getStart());
        endDateTimePicker.setDateTimeValue(appointment.getEnd());
    }

    private void fillComboBoxes() throws SchedulingException{
        ObservableList<Customer> observableCustomer = customerCmb.getItems();
        customerCmb.setConverter(new CustomerConverter());
        observableCustomer.addAll(schedulingService.getCustomerList());

        ObservableList<Appointment.DescriptionEnum> observableDescription= descriptionCmb.getItems();
        descriptionCmb.setConverter(new DescriptionConverter());
        observableDescription.addAll(Appointment.DescriptionEnum.values());

        ObservableList<Appointment.LocationEnum> observableLocation = locationCmb.getItems();
        locationCmb.setConverter(new LocationConverter());
        observableLocation.addAll(Appointment.LocationEnum.values());
    }

    private Appointment getAppointmentFromFields(){
        Appointment appointment= new Appointment();
        if(idTxt.getText()  != null && !idTxt.getText().equals(""))
            appointment.setAppointmentId(Integer.valueOf(idTxt.getText()));
        appointment.setTitle(titleTxt.getText());
        appointment.setCustomerId(customerCmb.getSelectionModel().getSelectedItem().getCustomerId());
        appointment.setDescription(descriptionCmb.getSelectionModel().getSelectedItem());
        appointment.setLocation(locationCmb.getSelectionModel().getSelectedItem());
        appointment.setContact(contactTxt.getText());
        appointment.setUrl(urlTxt.getText());
        appointment.setStart(startDateTimePicker.getDateTimeValue());
        appointment.setEnd(endDateTimePicker.getDateTimeValue());
        return appointment;
    }

    private void validateCustomer() throws SchedulingException {
        SchedulingException schedulingException = new SchedulingException();
        if(titleTxt.getText().equals("")) schedulingException.addSchedulingExceptionType(SchedulingException.SchedulingExceptionTypeEnum.TITLE_APPOINTMENT_NOT_INFORMED);

        if(customerCmb.getSelectionModel().getSelectedItem() == null) schedulingException.addSchedulingExceptionType(SchedulingException.SchedulingExceptionTypeEnum.CUSTOMER_APPOINTMENT_NOT_INFORMED);

        if(descriptionCmb.getSelectionModel().getSelectedItem() == null) schedulingException.addSchedulingExceptionType(SchedulingException.SchedulingExceptionTypeEnum.DESCRIPTION_APPOINTMENT_NOT_INFORMED);

        if(locationCmb.getSelectionModel().getSelectedItem() == null) schedulingException.addSchedulingExceptionType(SchedulingException.SchedulingExceptionTypeEnum.LOCATION_APPOINTMENT_NOT_INFORMED);

        if(contactTxt.getText().equals("")) schedulingException.addSchedulingExceptionType(SchedulingException.SchedulingExceptionTypeEnum.CONTACT_APPOINTMENT_NOT_INFORMED);

        if(urlTxt.getText().equals("")) schedulingException.addSchedulingExceptionType(SchedulingException.SchedulingExceptionTypeEnum.URL_APPOINTMENT_NOT_INFORMED);

        if(startDateTimePicker.getValue() == null) schedulingException.addSchedulingExceptionType(SchedulingException.SchedulingExceptionTypeEnum.START_APPOINTMENT_NOT_INFORMED);

        if(endDateTimePicker.getValue() == null) schedulingException.addSchedulingExceptionType(SchedulingException.SchedulingExceptionTypeEnum.END_APPOINTMENT_NOT_INFORMED);

        if(endDateTimePicker.getDateTimeValue().isBefore(startDateTimePicker.getDateTimeValue())) schedulingException.addSchedulingExceptionType(SchedulingException.SchedulingExceptionTypeEnum.START_END_DATE_APPOINTMENT_INCORRECT);

        if(endDateTimePicker.getDateTimeValue().isAfter(startDateTimePicker.getDateTimeValue().plusHours(Appointment.MAX_HOURS_LENGHT_APPOINTMENT))) schedulingException.addSchedulingExceptionType(SchedulingException.SchedulingExceptionTypeEnum.APPOINTMENT_TOO_LONG);

        if(!schedulingException.getSchedulingExceptionTypeEnumList().isEmpty()) throw schedulingException;
    }

    private void storeFields(ActionEvent actionEvent) throws IOException, SchedulingException{
        validateCustomer();
        Appointment newAppointment = getAppointmentFromFields();
        if (operation == OperationEnum.ADD) {
            schedulingService.storeAppointment(newAppointment);
        }
        else
            schedulingService.updateAppointment(newAppointment, appointment);
    }

    public void handleSaveAction(ActionEvent actionEvent) throws IOException {
        try {
            storeFields(actionEvent);
            utilUI.openUI(actionEvent, UtilUI.UIEnum.APPOINTMENTS_TABLE_UI);
        } catch (SchedulingException e){
            List<String> messages = utilMessages.getMessageListBySchedulingException(e);
            utilUI.openWarningDialog(messages);
        }
    }

    public void handleCancelAction(ActionEvent actionEvent) throws IOException {
        if(utilUI.openConfirmationDialog(utilMessages.getMessageByKey(CANCEL_DIALOG_KEY)))
            utilUI.openUI(actionEvent, UtilUI.UIEnum.APPOINTMENTS_TABLE_UI);
    }

    public class CustomerConverter extends StringConverter<Customer> {

        private Map<String, Customer> mapCustomer = new HashMap<String, Customer>();

        @Override
        public String toString(Customer customer) {
            mapCustomer.put(customer.getCustomerName(), customer);
            return customer.getCustomerName();
        }

        @Override
        public Customer fromString(String customerName) {
            return mapCustomer.get(customerName);
        }
    }

    public class DescriptionConverter extends StringConverter<Appointment.DescriptionEnum> {

        private Map<String, Appointment.DescriptionEnum> mapDescription = new HashMap<String, Appointment.DescriptionEnum>();

        @Override
        public String toString(Appointment.DescriptionEnum descriptionEnum) {
            mapDescription.put(descriptionEnum.getLabelTxt(), descriptionEnum);
            return descriptionEnum.getLabelTxt();
        }

        @Override
        public Appointment.DescriptionEnum fromString(String descriptionLabel) {
            return mapDescription.get(descriptionLabel);
        }
    }

    public class LocationConverter extends StringConverter<Appointment.LocationEnum>{

        private Map<String, Appointment.LocationEnum> mapLocation = new HashMap<String, Appointment.LocationEnum>();

        @Override
        public String toString(Appointment.LocationEnum locationEnum) {
            mapLocation.put(locationEnum.getLabelTxt(), locationEnum);
            return locationEnum.getLabelTxt();
        }

        @Override
        public Appointment.LocationEnum fromString(String locationLabel) {
            return mapLocation.get(locationLabel);
        }
    }
}
