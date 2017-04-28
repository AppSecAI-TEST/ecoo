package ecoo.service;

import ecoo.data.Country;
import ecoo.data.MetricType;
import ecoo.data.Province;
import ecoo.data.Title;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface DataService {

    /**
     * Returns a list of all the metric types.
     *
     * @return A list.
     */
    Collection<MetricType> metricTypes();

    /**
     * Returns a the metric type for the given id.
     *
     * @param id The pk.
     * @return A metric type.
     */
    MetricType metricTypeById(String id);

    /**
     * Returns a list of all the provinces.
     *
     * @return A list.
     */
    Collection<Province> provinces();

    /**
     * Returns a list of all the countries.
     *
     * @return A list.
     */
    Collection<Country> countries();

    /**
     * Returns a list of all the titles.
     *
     * @return A list.
     */
    Collection<Title> titles();
}
