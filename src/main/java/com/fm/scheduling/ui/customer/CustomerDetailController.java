package com.fm.scheduling.ui.customer;

import com.fm.scheduling.domain.Address;
import com.fm.scheduling.domain.City;
import com.fm.scheduling.domain.Country;
import com.fm.scheduling.domain.Customer;
import com.fm.scheduling.exception.SchedulingException;
import com.fm.scheduling.service.SchedulingService;
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

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class CustomerDetailController implements Initializable {

    private UtilUI utilUI = UtilUI.getInstance();

    private SchedulingService schedulingService;

    private UtilMessages utilMessages;

    private Customer customer;

    @FXML
    private Label operationLbl;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField address1Txt;

    @FXML
    private TextField address2Txt;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TextField postalCodeTxt;

    @FXML
    private ComboBox<City> cityCmb;

    @FXML
    private ComboBox<Country> countryCmb;

    private final String CANCEL_DIALOG_KEY = "dialog.customer.CANCEL_BUTTON_CONFIRMATION";

    enum OperationEnum{
        ADD("Add Customer"),
        MODIFY("Modify Customer");

        private String labelTxt;

        OperationEnum(String labelTxt){
            this.labelTxt = labelTxt;
        }

        public String getLabelTxt() {
            return labelTxt;
        }
    }

    private OperationEnum operation;

    public CustomerDetailController(){
        this.schedulingService = SchedulingService.getInstance();
        this.utilMessages = UtilMessages.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            fillComboBoxes();
            operation = OperationEnum.ADD;
            operationLbl.setText(operation.getLabelTxt());

            if (schedulingService.getCustomerSelected() != null) {
                operation = OperationEnum.MODIFY;
                customer = schedulingService.getCustomerSelected();
                informFields();
            }
        } catch (SchedulingException e){
            List<String> messages = utilMessages.getMessageListBySchedulingException(e);
            utilUI.openWarningDialog(messages);
        }
    }

    private void fillComboBoxes() throws SchedulingException{
        ObservableList<Country> observableCountry = countryCmb.getItems();
        countryCmb.setConverter(new CountryConverter());
        observableCountry.addAll(schedulingService.getCountryList());
        cityCmb.setConverter(new CityConverter());
        //fillCityComboBox();
    }

    private void fillCityComboBox() throws SchedulingException{
        cityCmb.getSelectionModel().select(null);
        ObservableList<City> observableCity = cityCmb.getItems();
        observableCity.clear();
        int countryIdSelected = countryCmb.getSelectionModel().getSelectedItem().getCountryId();
        List<City> cityList = schedulingService.getCityListByCountry(countryIdSelected);
        observableCity.addAll(cityList);
    }

    private void informFields() throws SchedulingException{
        operationLbl.setText(OperationEnum.MODIFY.getLabelTxt());

        idTxt.setText(String.valueOf(customer.getCustomerId()));
        nameTxt.setText(customer.getCustomerName());
        address1Txt.setText(String.valueOf(customer.getAddress().getAddress()));
        address2Txt.setText(String.valueOf(customer.getAddress().getAddress2()));
        postalCodeTxt.setText(String.valueOf(customer.getAddress().getPostalCode()));
        phoneTxt.setText(String.valueOf(customer.getAddress().getPhone()));
        countryCmb.getSelectionModel().select(customer.getAddress().getCity().getCountry());
        fillCityComboBox();
        cityCmb.getSelectionModel().select(customer.getAddress().getCity());
    }

    private void storeFields(ActionEvent actionEvent) throws IOException, SchedulingException{
        validateCustomer();
        Customer newCustomer = getCustomerFromFields();
        if (operation == OperationEnum.ADD) {
            schedulingService.storeCustomer(newCustomer);
        }
        else
            schedulingService.updateCustomer(newCustomer, customer);
        utilUI.openUI(actionEvent, UtilUI.UIEnum.CUSTOMER_TABLE_UI);
    }

    private void validateCustomer() throws SchedulingException {

        SchedulingException schedulingException = new SchedulingException();

        if(nameTxt.getText().equals("")) schedulingException.addSchedulingExceptionType(SchedulingException.SchedulingExceptionTypeEnum.CUSTOMER_NAME_NOT_INFORMED);

        if(address1Txt.getText().equals("")) schedulingException.addSchedulingExceptionType(SchedulingException.SchedulingExceptionTypeEnum.ADDRESS_NOT_INFORMED);

        if(cityCmb.getSelectionModel().getSelectedItem() == null) schedulingException.addSchedulingExceptionType(SchedulingException.SchedulingExceptionTypeEnum.CITY_NOT_INFORMED);

        if(postalCodeTxt.getText().equals("")) schedulingException.addSchedulingExceptionType(SchedulingException.SchedulingExceptionTypeEnum.POSTAL_CODE_NOT_INFORMED);

        if(phoneTxt.getText().equals("")) schedulingException.addSchedulingExceptionType(SchedulingException.SchedulingExceptionTypeEnum.PHONE_NOT_INFORMED);

        if(!schedulingException.getSchedulingExceptionTypeEnumList().isEmpty()) throw schedulingException;
    }


    private Customer getCustomerFromFields(){
        Customer customer = new Customer();
        customer.setCustomerName(nameTxt.getText());
        if(idTxt.getText()  != null && !idTxt.getText().equals(""))
            customer.setCustomerId(Integer.valueOf(idTxt.getText()));
        Address address = new Address();
        address.setAddress(address1Txt.getText());
        address.setAddress2(address2Txt.getText());
        address.setPhone(phoneTxt.getText());
        address.setPostalCode(postalCodeTxt.getText());
        address.setCityId(cityCmb.getSelectionModel().getSelectedItem().getCityId());
        customer.setAddress(address);
        return customer;
    }

    public void handleSaveAction(ActionEvent actionEvent) throws IOException {
        try {
            storeFields(actionEvent);
        } catch (SchedulingException e){
            List<String> messages = utilMessages.getMessageListBySchedulingException(e);
            utilUI.openWarningDialog(messages);
        }
    }

    public void handleCancelAction(ActionEvent actionEvent) throws IOException {
        if(utilUI.openConfirmationDialog(utilMessages.getMessageByKey(CANCEL_DIALOG_KEY)))
            utilUI.openUI(actionEvent, UtilUI.UIEnum.CONTROL_PANEL_UI);
    }

    public void handleCountryCmbOnChange(ActionEvent actionEvent) throws IOException {
        try {
            fillCityComboBox();
        } catch (SchedulingException e){
            List<String> messages = utilMessages.getMessageListBySchedulingException(e);
            utilUI.openWarningDialog(messages);
        }
    }

    public class CountryConverter extends StringConverter<Country> {

        private Map<String, Country> mapCountries = new HashMap<String, Country>();

        @Override
        public String toString(Country country) {
            mapCountries.put(country.getCountry(), country);
            return country.getCountry();
        }

        @Override
        public Country fromString(String codigo) {
            return mapCountries.get(codigo);
        }
    }

    public class CityConverter extends StringConverter<City> {

        private Map<String, City> mapCity = new HashMap<String, City>();

        @Override
        public String toString(City city) {
            mapCity.put(city.getCity(), city);
            return city.getCity();
        }

        @Override
        public City fromString(String codigo) {
            return mapCity.get(codigo);
        }
    }
}
