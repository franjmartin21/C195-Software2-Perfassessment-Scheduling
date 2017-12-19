package com.fm.scheduling.dao;

import com.fm.scheduling.domain.Address;
import com.fm.scheduling.domain.City;
import com.fm.scheduling.domain.Country;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;


public class AddressDaoTest extends BaseDaoTest{

    private CountryDao countryDao;

    private CityDao cityDao;

    private AddressDao addressDao;

    private Country country;

    private Country country2;

    private City city;

    private City city2;

    @Before
    public void setup() throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
        super.init();
        cityDao = new CityDao();
        countryDao = new CountryDao();
        addressDao = new AddressDao();
        country = Country.createInstance(Country.class);
        country.setCountry("España");
        country.setCountryId(countryDao.insert(country, userCreator));

        country2 = Country.createInstance(Country.class);
        country2.setCountry("Portugal");
        country2.setCountryId(countryDao.insert(country2, userCreator));

        city = City.createInstance(City.class);
        city.setCity("Aranda de Duero");
        city.setCountry(country);
        city.setCityId(cityDao.insert(city, userCreator));

        city2 = City.createInstance(City.class);
        city2.setCity("Miranda do Douro");
        city2.setCountry(country2);
        city2.setCityId(cityDao.insert(city2, userCreator));
    }

    @Test
    public void testInsertAddress_success() throws SQLException {
        Address address = Address.createInstance(Address.class);
        address.setAddress("La Miel, 2");
        address.setAddress2("Sta. Catalina");
        address.setCity(city);
        address.setPostalCode("98767");
        address.setPhone("8797879876");
        int addressId = addressDao.insert(address, userCreator);
        Address address1 = addressDao.getById(addressId);
        Assert.assertNotNull(address1);
        Assert.assertEquals("La Miel, 2", address1.getAddress());
    }

    @Test
    public void testUpdateAddress_success() throws SQLException {
        Address address = Address.createInstance(Address.class);
        address.setAddress("La Miel, 2");
        address.setAddress2("Sta. Catalina");
        address.setCity(city);
        address.setPostalCode("98767");
        address.setPhone("8797879876");
        int addressId = addressDao.insert(address, userCreator);
        Address address1 = addressDao.getById(addressId);
        address1.setAddress("Rua do Douro, 5");
        address1.setAddress2("Bairro Novo");
        address1.setPhone("123456789");
        address1.setPostalCode("12345");
        address1.setCity(city2);
        addressDao.update(address1, userCreator, addressId);
        Address address2 = addressDao.getById(addressId);
        Assert.assertNotNull(address2);
        Assert.assertEquals("Rua do Douro, 5", address2.getAddress());
    }

    @Test
    public void testDeleteAddress_success() throws SQLException {
        Address address = Address.createInstance(Address.class);
        address.setAddress("La Miel, 2");
        address.setAddress2("Sta. Catalina");
        address.setCity(city);
        address.setPostalCode("98767");
        address.setPhone("8797879876");
        int addressId = addressDao.insert(address, userCreator);
        addressDao.delete(addressId);
        Address address1 = addressDao.getById(addressId);
        Assert.assertNull(address1);
    }
}
