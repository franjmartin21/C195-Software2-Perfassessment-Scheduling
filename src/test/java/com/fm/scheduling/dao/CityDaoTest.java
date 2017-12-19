package com.fm.scheduling.dao;

import com.fm.scheduling.domain.City;
import com.fm.scheduling.domain.Country;
import static junit.framework.TestCase.fail;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;


public class CityDaoTest extends BaseDaoTest{

    private CountryDao countryDao;

    private CityDao cityDao;

    private Country country;

    private Country country2;

    @Before
    public void setup() throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
        super.init();
        cityDao = new CityDao();
        countryDao = new CountryDao();
        country = Country.createInstance(Country.class);
        country.setCountry("España");
        country.setCountryId(countryDao.insert(country, userCreator));

        country2 = Country.createInstance(Country.class);
        country2.setCountry("Portugal");
        country2.setCountryId(countryDao.insert(country2, userCreator));
    }

    @Test
    public void testInsertCity_success() throws SQLException {
        City city = City.createInstance(City.class);
        city.setCity("Aranda de Duero");
        city.setCountry(country);
        int numRecord = cityDao.insert(city, userCreator);
        Assert.assertTrue(numRecord > 0);
    }

    @Test
    public void testGetByIdCity_success() throws SQLException {
        City city = City.createInstance(City.class);
        city.setCity("Aranda de Duero");
        city.setCountry(country);
        int numRecord = cityDao.insert(city, userCreator);
        City city2 = cityDao.getById(numRecord);
        Assert.assertNotNull(city2);
        Assert.assertEquals("Aranda de Duero", city2.getCity());
    }

    @Test
    public void testUpdateCity_success() throws SQLException {
        City city = City.createInstance(City.class);
        city.setCity("Aranda de Duero");
        city.setCountry(country);
        int numRecord = cityDao.insert(city, userCreator);
        City city2 = cityDao.getById(numRecord);
        city2.setCity("Miranda do Douro");
        city2.setCountry(country2);
        cityDao.update(city2, userCreator, city2.getCityId());
        City city3 = cityDao.getById(numRecord);
        city3.setCountry(countryDao.getById(city3.getCountryId()));
        Assert.assertNotNull(city3);
        Assert.assertEquals("Miranda do Douro", city3.getCity());
        Assert.assertEquals("Portugal", city3.getCountry().getCountry());
    }

    @Test
    public void testDeleteCity_success() throws SQLException {
        City city = City.createInstance(City.class);
        city.setCity("Aranda de Duero");
        city.setCountry(country);
        int numRecord = cityDao.insert(city, userCreator);
        cityDao.delete(numRecord);
        City city2 = cityDao.getById(numRecord);
        Assert.assertNull(city2);
    }
}
