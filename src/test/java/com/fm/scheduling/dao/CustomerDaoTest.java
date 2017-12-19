package com.fm.scheduling.dao;

import com.fm.scheduling.domain.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;


public class CustomerDaoTest extends BaseDaoTest{

    private CustomerDao customerDao;

    private CountryDao countryDao;

    private CityDao cityDao;

    private AddressDao addressDao;

    private Address address;

    private Address address2;

    @Before
    public void setup() throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
        super.init();
        customerDao = new CustomerDao();
        addressDao = new AddressDao();
        countryDao = new CountryDao();
        cityDao = new CityDao();

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

        address = Address.createInstance(Address.class);
        address.setAddress("La Miel, 2");
        address.setAddress2("Sta. Catalina");
        address.setCity(city);
        address.setPostalCode("98767");
        address.setPhone("8797879876");
        address.setAddressId(addressDao.insert(address, userCreator));

        address2 = Address.createInstance(Address.class);
        address2.setAddress("Rua do Douro, 5");
        address2.setAddress2("Bairro Novo");
        address2.setCity(city2);
        address2.setPhone("123456789");
        address2.setPostalCode("12345");
        address2.setAddressId(addressDao.insert(address2, userCreator));
    }

    @Test
    public void testInsertCustomer_success() throws SQLException {
        Customer customer = Customer.createInstance(Customer.class);
        customer.setCustomerName("Joaquin Vermudez");
        customer.setActive(true);
        customer.setAddress(address);
        int customerId = customerDao.insert(customer, userCreator);
        Customer customer1 = customerDao.getById(customerId);
        Assert.assertNotNull(customer1);
        Assert.assertEquals("Joaquin Vermudez", customer1.getCustomerName());
    }

    @Test
    public void testUpdateAddress_success() throws SQLException {
        Customer customer = Customer.createInstance(Customer.class);
        customer.setCustomerName("Joaquin Vermudez");
        customer.setActive(true);
        customer.setAddress(address);
        int customerId = customerDao.insert(customer, userCreator);
        Customer customer1 = customerDao.getById(customerId);
        customer1.setAddress(address2);
        customer1.setActive(false);
        customer1.setCustomerName("Ivan Sanchez");
        customerDao.update(customer1, userCreator, customerId);
        Customer customer2 = customerDao.getById(customerId);

        Assert.assertNotNull(customer2);
        Assert.assertEquals("Ivan Sanchez", customer2.getCustomerName());
    }

    @Test
    public void testDeleteAddress_success() throws SQLException {
        Customer customer = Customer.createInstance(Customer.class);
        customer.setCustomerName("Joaquin Vermudez");
        customer.setActive(true);
        customer.setAddress(address);
        int customerId = customerDao.insert(customer, userCreator);
        customerDao.delete(customerId);
        Customer customer1 = customerDao.getById(customerId);
        Assert.assertNull(customer1);
    }
}
