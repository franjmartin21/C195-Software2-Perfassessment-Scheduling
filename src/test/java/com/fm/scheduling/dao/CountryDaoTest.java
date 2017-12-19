package com.fm.scheduling.dao;

import com.fm.scheduling.domain.Country;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;


public class CountryDaoTest extends BaseDaoTest{

    private CountryDao countryDao;

    @Before
    public void setup() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        super.init();
        countryDao = new CountryDao();
    }

    @Test
    public void testInsertCountry_success() throws SQLException {
        Country country = Country.createInstance(Country.class);
        country.setCountry("España");
        int numRecord = countryDao.insert(country, userCreator);
        Assert.assertTrue(numRecord > 0);
    }

    @Test
    public void testGetByIdCountry_success() throws SQLException {
        Country country = Country.createInstance(Country.class);
        country.setCountry("España");
        int numRecord = countryDao.insert(country, userCreator);
        Country country2 = countryDao.getById(numRecord);
        Assert.assertNotNull(country2);
        Assert.assertEquals("España", country2.getCountry());
    }

    @Test
    public void testUpdateCountry_success() throws SQLException {
        Country country = Country.createInstance(Country.class);
        country.setCountry("España");
        int numRecord = countryDao.insert(country, userCreator);
        Country country2 = countryDao.getById(numRecord);
        country2.setCountry("Portugal");
        countryDao.update(country2, userCreator, country2.getCountryId());
        Country country3 = countryDao.getById(numRecord);
        Assert.assertNotNull(country3);
        Assert.assertEquals("Portugal", country3.getCountry());
    }

    @Test
    public void testDeleteCountry_success() throws SQLException {
        Country country = Country.createInstance(Country.class);
        country.setCountry("España");
        int numRecord = countryDao.insert(country, userCreator);
        Country country2 = countryDao.getById(numRecord);
        Assert.assertNotNull(country2);
        countryDao.delete(numRecord);
        country2 = countryDao.getById(numRecord);
        Assert.assertNull(country2);
    }
}
