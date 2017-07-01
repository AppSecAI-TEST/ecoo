package ecoo.service.impl;

import ecoo.dao.ChamberRateDao;
import ecoo.data.ChamberRate;
import ecoo.service.ChamberRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Service
public class ChamberRateServiceImpl extends AuditTemplate<Integer, ChamberRate, ChamberRateDao>
        implements ChamberRateService {

    private ChamberRateDao chamberRateDao;

    @Autowired
    public ChamberRateServiceImpl(ChamberRateDao chamberRateDao) {
        super(chamberRateDao);
        this.chamberRateDao = chamberRateDao;
    }

    /**
     * Returns a list of Chamber rates for the given chamber.
     *
     * @param chamberId The chamber id to evaluate.
     * @param member    The member indicator.
     * @return The list of associated Chambers.
     */
    @Override
    public ChamberRate findRateByChamberAndMemberIndicator(Integer chamberId, boolean member) {
        return chamberRateDao.findRateByChamberAndMemberIndicator(chamberId, member);
    }

    /**
     * Returns a list of Chamber rates for the given chamber.
     *
     * @param chamberId The chamber id to evaluate.
     * @return The list of associated Chambers.
     */
    @Override
    public List<ChamberRate> findRatesByChamber(Integer chamberId) {
        return chamberRateDao.findRatesByChamber(chamberId);
    }
}
