package com.fm.scheduling.service;

import com.fm.scheduling.dao.*;
import com.fm.scheduling.domain.*;
import com.fm.scheduling.exception.SchedulingException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class SchedulingService {

    public enum LocalesSupported{
        en(new Locale("en")),
        es(new Locale("es"));

        LocalesSupported(Locale locale){
            this.locale = locale;
        }

        private Locale locale;

        public Locale getLocale() {
            return locale;
        }

        public static LocalesSupported getLocaleSupported(Locale locale){

            for(LocalesSupported localesSupported: LocalesSupported.values()){
                if(localesSupported.name().equals(localesSupported.getLocale().getLanguage())) return localesSupported;
            }
            return LocalesSupported.en;
        }
    }

    private LocalesSupported locale = LocalesSupported.en;

    private Customer customerSelected;

    private Appointment appointmentSelected;

    private static SchedulingService instance;

    private User userLoggedIn;

    private ZoneId localZoneId;

    private ZoneId utcZoneId = ZoneId.of("UTC");

    private AppointmentDao appointmentDao = new AppointmentDao();

    private UserDao userDao = new UserDao();

    private CustomerDao customerDao = new CustomerDao();

    private AddressDao addressDao = new AddressDao();

    private CityDao cityDao = new CityDao();

    private CountryDao countryDao = new CountryDao();

    private SchedulingService(){
    }


    public boolean login(String userName, String password) throws SchedulingException {
        boolean loggedIn = false;
        User user = null;
        try {
            user = userDao.getByName(userName);
        } catch(Exception e){
            e.printStackTrace();
            throw new SchedulingException(SchedulingException.SchedulingExceptionTypeEnum.DB_CONNECTION_PROBLEM);
        }
        if(user != null && user.getPassword().equals(password)){
            loggedIn = true;
            userLoggedIn = user;
            localZoneId = ZoneId.systemDefault();
        }
        return loggedIn;
    }

    public LocalDateTime getUTCLocalDateTime(LocalDateTime localDateTime){
        ZonedDateTime ldtZoned = localDateTime.atZone(localZoneId);
        ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(utcZoneId);
        return utcZoned.toLocalDateTime();
    }

    public LocalDateTime getLocalDateTimeFromUTCTime(LocalDateTime localDateTime){
        ZonedDateTime utcZoned = localDateTime.atZone(utcZoneId);
        ZonedDateTime ldtZoned = utcZoned.withZoneSameInstant(localZoneId);
        return ldtZoned.toLocalDateTime();
    }

    public static synchronized SchedulingService getInstance(){
        if(instance == null) instance = new SchedulingService();

        return instance;
    }

    public User getUserLoggedIn() {
        return userLoggedIn;
    }

    public LocalesSupported getLocale() {
        return locale;
    }

    public void setLocale(LocalesSupported locale) {
        this.locale = locale;
    }


    public List<Country> getCountryList() throws SchedulingException{
        List<Country> countryList;
        try{
            countryList = countryDao.getList();
        } catch (SQLException ex){
            throw new SchedulingException(SchedulingException.SchedulingExceptionTypeEnum.DB_CONNECTION_PROBLEM);
        }
        return countryList;
    }

    public List<City> getCityListByCountry(int countryId) throws SchedulingException{
        List<City> cityList;
        try{
            cityList = cityDao.getListByCountryId(countryId);
        } catch (SQLException ex){
            throw new SchedulingException(SchedulingException.SchedulingExceptionTypeEnum.DB_CONNECTION_PROBLEM);
        }
        return cityList;
    }

    public List<User> getUserList() throws SchedulingException{
        List<User> userList;
        try{
            userList = userDao.getList();
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new SchedulingException(SchedulingException.SchedulingExceptionTypeEnum.DB_CONNECTION_PROBLEM);
        }
        return userList;
    }

    public Map<String, String> getAppointmentsGroupedByDescriptionByMonth(LocalDate month) throws SchedulingException{
        Map<String, String> mapAppointmentsByDescriptionString;

        if(month == null) return null;

        LocalDate startMonth = month.withDayOfMonth(1);
        LocalDate endMonth = month.withDayOfMonth(month.lengthOfMonth());
        try{
            mapAppointmentsByDescriptionString = appointmentDao.getAppointmentsGroupedByDescriptionByDateRange(startMonth, endMonth).entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            e -> (e.getValue().toString())));
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new SchedulingException(SchedulingException.SchedulingExceptionTypeEnum.DB_CONNECTION_PROBLEM);
        }
        return mapAppointmentsByDescriptionString;
    }

    public List<Appointment> getAppointmentOnLocalDateTimeRange(LocalDateTime startDateTime, LocalDateTime endDateTime) throws SchedulingException{
        if(startDateTime == null || endDateTime == null) return null;

        List<Appointment> appointmentList;
        appointmentList = getAppointmentList();
        appointmentList = appointmentList.stream().filter(x -> x.getStart().isAfter(startDateTime) && x.getStart().isBefore(endDateTime)).collect(Collectors.toList());
        return appointmentList;
    }

    public List<Appointment> getAppointmentList() throws SchedulingException{
        List<Appointment> appointmentList;
        try{
            appointmentList = appointmentDao.getList();
            for(Appointment appointment: appointmentList){
                appointment.setStart(getLocalDateTimeFromUTCTime(appointment.getStart()));
                appointment.setEnd(getLocalDateTimeFromUTCTime(appointment.getEnd()));
                appointment.setCustomer(getFullCustomer(appointment.getCustomerId()));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new SchedulingException(SchedulingException.SchedulingExceptionTypeEnum.DB_CONNECTION_PROBLEM);
        }
        return appointmentList;
    }

    public List<Appointment> getAppointmentByConsultant(String consultant) throws SchedulingException{
        if(consultant == null || consultant.equals("")) return null;

        List<Appointment> appointmentList;
        try{
            appointmentList = appointmentDao.getByConsultant(consultant);
            for(Appointment appointment: appointmentList){
                appointment.setStart(getLocalDateTimeFromUTCTime(appointment.getStart()));
                appointment.setEnd(getLocalDateTimeFromUTCTime(appointment.getEnd()));
                appointment.setCustomer(getFullCustomer(appointment.getCustomerId()));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new SchedulingException(SchedulingException.SchedulingExceptionTypeEnum.DB_CONNECTION_PROBLEM);
        }
        return appointmentList;
    }

    public List<Appointment> getAppointmentByLocation(Appointment.LocationEnum location) throws SchedulingException{
        if(location == null) return null;

        List<Appointment> appointmentList;
        try{
            appointmentList = appointmentDao.getByLocation(location);
            for(Appointment appointment: appointmentList){
                appointment.setStart(getLocalDateTimeFromUTCTime(appointment.getStart()));
                appointment.setEnd(getLocalDateTimeFromUTCTime(appointment.getEnd()));
                appointment.setCustomer(getFullCustomer(appointment.getCustomerId()));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new SchedulingException(SchedulingException.SchedulingExceptionTypeEnum.DB_CONNECTION_PROBLEM);
        }
        return appointmentList;
    }

    public List<Customer> getCustomerList() throws SchedulingException {
        List<Customer> customerList;
        try{
            customerList = customerDao.getList();
            for(Customer customer: customerList){
                customer.setAddress(getFullAddress(customer.getAddressId()));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new SchedulingException(SchedulingException.SchedulingExceptionTypeEnum.DB_CONNECTION_PROBLEM);
        }
        return customerList;
    }

    public void deleteAppointment(Appointment appointment) throws SchedulingException{
        try {
            appointmentDao.delete(appointment.getAppointmentId());
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new SchedulingException(SchedulingException.SchedulingExceptionTypeEnum.DB_CONNECTION_PROBLEM);
        }
    }

    public void deleteCustomer(Customer customer) throws SchedulingException {
        try {
            checkCustomerOnAppointment(customer);
            customerDao.delete(customer.getCustomerId());
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new SchedulingException(SchedulingException.SchedulingExceptionTypeEnum.DB_CONNECTION_PROBLEM);
        }
    }

    private void checkCustomerOnAppointment(Customer customer) throws SchedulingException, SQLException{
        List<Appointment> appointmentList = appointmentDao.getByCustomer(customer.getCustomerId());
        if(appointmentList != null && !appointmentList.isEmpty()) throw new SchedulingException(SchedulingException.SchedulingExceptionTypeEnum.CUSTOMER_ON_APPOINMENT);
    }

    public int storeAppointment(Appointment appointment) throws SchedulingException{
        try {
            appointment.setStart(getUTCLocalDateTime(appointment.getStart()));
            appointment.setEnd(getUTCLocalDateTime(appointment.getEnd()));
            return appointmentDao.insert(appointment, userLoggedIn);
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new SchedulingException(SchedulingException.SchedulingExceptionTypeEnum.DB_CONNECTION_PROBLEM);
        }
    }

    public void updateAppointment(Appointment appointment, Appointment originalAppointment) throws SchedulingException{
        try {
            appointment.setStart(getUTCLocalDateTime(appointment.getStart()));
            appointment.setEnd(getUTCLocalDateTime(appointment.getEnd()));
            appointmentDao.update(appointment, userLoggedIn, originalAppointment.getAppointmentId());
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SchedulingException(SchedulingException.SchedulingExceptionTypeEnum.DB_CONNECTION_PROBLEM);
        }
    }

    public void updateCustomer(Customer customer, Customer originalCustomer) throws SchedulingException {
        try {
            updateAddress(customer.getAddress(), originalCustomer.getAddress());
            customer.setAddressId(originalCustomer.getAddressId());
            customerDao.update(customer, userLoggedIn, originalCustomer.getCustomerId());
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SchedulingException(SchedulingException.SchedulingExceptionTypeEnum.DB_CONNECTION_PROBLEM);
        }
    }

    private void updateAddress(Address address, Address originalAddress) throws SQLException{
        addressDao.update(address, userLoggedIn, originalAddress.getAddressId());
    }

    public int storeCustomer(Customer customer) throws SchedulingException{
        try {
            customer.setAddressId(storeAddress(customer.getAddress()));
            return customerDao.insert(customer, userLoggedIn);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SchedulingException(SchedulingException.SchedulingExceptionTypeEnum.DB_CONNECTION_PROBLEM);
        }
    }

    private int storeAddress(Address address) throws SQLException{
        return addressDao.insert(address, userLoggedIn);
    }

    private Customer getFullCustomer(int customerId) throws SQLException{
        Customer customer = customerDao.getById(customerId);
        customer.setAddress(getFullAddress(customer.getAddressId()));
        return customer;
    }

    private Address getFullAddress(int addressId)throws SQLException{
        Address address = addressDao.getById(addressId);
        address.setCity(getFullCity(address.getCityId()));
        return address;
    }

    private City getFullCity(int cityId) throws SQLException{
        City city = cityDao.getById(cityId);
        city.setCountry(getFullCountry(city.getCountryId()));
        return city;
    }

    private Country getFullCountry(int countryId) throws SQLException{
        return countryDao.getById(countryId);
    }

    public Customer getCustomerSelected() {
        return customerSelected;
    }

    public void setCustomerSelected(Customer customerSelected) {
        this.customerSelected = customerSelected;
    }

    public void clearCustomerSelected(){
        this.customerSelected = null;
    }

    public Appointment getAppointmentSelected() {
        return appointmentSelected;
    }

    public void setAppointmentSelected(Appointment appointmentSelected) {
        this.appointmentSelected = appointmentSelected;
    }

    public void clearAppointmentSelected(){
        this.appointmentSelected = null;
    }
}
