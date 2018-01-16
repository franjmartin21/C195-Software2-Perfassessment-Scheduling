package com.fm.scheduling.dao;

import com.fm.scheduling.domain.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;


public class AppointmentDaoTest extends BaseDaoTest{

    private CustomerDao customerDao;

    private CountryDao countryDao;

    private CityDao cityDao;

    private AddressDao addressDao;

    private Customer customer;

    private Customer customer2;

    private AppointmentDao appointmentDao;


    @Before
    public void setup() throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
        super.init();
        customerDao = new CustomerDao();
        addressDao = new AddressDao();
        countryDao = new CountryDao();
        cityDao = new CityDao();
        appointmentDao = new AppointmentDao();

        Country country = Country.createInstance(Country.class);
        country.setCountry("España");
        country.setCountryId(countryDao.insert(country, userCreator));

        Country country2 = Country.createInstance(Country.class);
        country2.setCountry("Portugal");
        country2.setCountryId(countryDao.insert(country2, userCreator));

        City city = City.createInstance(City.class);
        city.setCity("Aranda de Duero");
        city.setCountry(country);
        city.setCityId(cityDao.insert(city, userCreator));

        City city2 = City.createInstance(City.class);
        city2.setCity("Miranda do Douro");
        city2.setCountry(country2);
        city2.setCityId(cityDao.insert(city2, userCreator));

        Address address = Address.createInstance(Address.class);
        address.setAddress("La Miel, 2");
        address.setAddress2("Sta. Catalina");
        address.setCity(city);
        address.setPostalCode("98767");
        address.setPhone("8797879876");
        address.setAddressId(addressDao.insert(address, userCreator));

        Address address2 = Address.createInstance(Address.class);
        address2.setAddress("Rua do Douro, 5");
        address2.setAddress2("Bairro Novo");
        address2.setCity(city2);
        address2.setPhone("123456789");
        address2.setPostalCode("12345");
        address2.setAddressId(addressDao.insert(address2, userCreator));

        customer = Customer.createInstance(Customer.class);
        customer.setCustomerName("Joaquin Vermudez");
        customer.setActive(true);
        customer.setAddress(address);
        customer.setCustomerId(customerDao.insert(customer, userCreator));

        customer2 = Customer.createInstance(Customer.class);
        customer2.setCustomerName("Ivan Sanchez");
        customer2.setAddress(address2);
        customer2.setActive(false);
        customer2.setCustomerId(customerDao.insert(customer2, userCreator));
    }

    @Test
    public void testInsertCustomer_success() throws SQLException {
        Appointment appointment = Appointment.createInstance(Appointment.class);
        appointment.setStart(LocalDateTime.of(2017,9,9, 9,45));
        appointment.setEnd(LocalDateTime.of(2017, 9, 9, 10, 0));
        appointment.setUrl("www.google.com");
        appointment.setContact("Julio");
        //appointment.setLocation("Madrid");
        //appointment.setDescription("Lo que sea");
        appointment.setTitle("Appointment 1");
        appointment.setCustomer(customer);
        int appointmentId = appointmentDao.insert(appointment, userCreator);
        Appointment appointment1 = appointmentDao.getById(appointmentId);
        Assert.assertNotNull(appointment1);
        Assert.assertEquals( appointment1.getTitle(),"Appointment 1");
    }

    @Test
    public void testUpdateAddress_success() throws SQLException {
        Appointment appointment = Appointment.createInstance(Appointment.class);
        appointment.setStart(LocalDateTime.of(2017,9,9, 9,45));
        appointment.setEnd(LocalDateTime.of(2017, 9, 9, 10, 0));
        appointment.setUrl("www.google.com");
        appointment.setContact("Julio");
        //appointment.setLocation("Madrid");
        //appointment.setDescription("Lo que sea");
        appointment.setTitle("Appointment 1");
        appointment.setCustomer(customer);
        int appointmentId = appointmentDao.insert(appointment, userCreator);
        Appointment appointment1 = appointmentDao.getById(appointmentId);

        appointment1.setCustomer(customer2);
        appointment1.setTitle("Appointment 2");
        //appointment1.setDescription("Lo que sea 2");
        appointment1.setContact("Pepe");
        //appointment1.setLocation("Burgos");
        appointment1.setUrl("www.yahoo.com");
        appointment.setStart(LocalDateTime.of(2017,9,10, 9,45));
        appointment.setEnd(LocalDateTime.of(2017, 9, 10, 10, 0));
        appointmentDao.update(appointment1, userCreator, appointmentId);
        Appointment appointment2 = appointmentDao.getById(appointmentId);
        Assert.assertNotNull(appointment2);
        Assert.assertEquals("Lo que sea 2", appointment2.getDescription());
    }

    @Test
    public void testDeleteAddress_success() throws SQLException {
        Appointment appointment = Appointment.createInstance(Appointment.class);
        appointment.setStart(LocalDateTime.of(2017,9,9, 9,45));
        appointment.setEnd(LocalDateTime.of(2017, 9, 9, 10, 0));
        appointment.setUrl("www.google.com");
        appointment.setContact("Julio");
        //appointment.setLocation("Madrid");
        //appointment.setDescription("Lo que sea");
        appointment.setTitle("Appointment 1");
        appointment.setCustomer(customer);
        int appointmentId = appointmentDao.insert(appointment, userCreator);
        appointmentDao.delete(appointmentId);
        Appointment appointment1 = appointmentDao.getById(appointmentId);
        Assert.assertNull(appointment1);
    }
}
