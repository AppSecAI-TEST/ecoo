package ecoo.service.impl;

import ecoo.dao.*;
import ecoo.data.*;
import ecoo.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Component
public class DataServiceImpl implements DataService {

    private ProvinceDao provinceDao;

    private CountryDao countryDao;

    private TitleDao titleDao;

    private MetricTypeDao metricTypeDao;

    private TransportTypeDao transportTypeDao;

    private CurrencyDao currencyDao;

    @Autowired
    public DataServiceImpl(ProvinceDao provinceDao, CountryDao countryDao
            , TitleDao titleDao
            , MetricTypeDao metricTypeDao
            , TransportTypeDao transportTypeDao
            , CurrencyDao currencyDao) {
        this.provinceDao = provinceDao;
        this.countryDao = countryDao;
        this.titleDao = titleDao;
        this.metricTypeDao = metricTypeDao;
        this.transportTypeDao = transportTypeDao;
        this.currencyDao = currencyDao;
    }

    /**
     * Returns a list of all the currencies.
     *
     * @return A list.
     */
    @Override
    public Collection<Currency> currencies() {
        return currencyDao.findAll();
    }

    /**
     * Returns a list of all the transport types.
     *
     * @return A list.
     */
    @Override
    public Collection<TransportType> transportTypes() {
        return transportTypeDao.findAll();
    }

    /**
     * Returns a list of all the metric types.
     *
     * @return A list.
     */
    @Override
    public Collection<MetricType> metricTypes() {
        return metricTypeDao.findAll();
    }

    /**
     * Returns a the metric type for the given id.
     *
     * @param id The pk.
     * @return A metric type.
     */
    @Override
    public MetricType metricTypeById(String id) {
        return metricTypeDao.findByPrimaryId(id);
    }

    /**
     * Returns a list of all the provinces.
     *
     * @return A list.
     */
    @Override
    public Collection<Province> provinces() {
        return provinceDao.findAll();
    }

    /**
     * Returns a list of all the countries.
     *
     * @return A list.
     */
    @Override
    public Collection<Country> countries() {
        return countryDao.findAll();
    }

    /**
     * Returns a list of all the titles.
     *
     * @return A list.
     */
    @Override
    public Collection<Title> titles() {
        return titleDao.findAll();
    }
}
