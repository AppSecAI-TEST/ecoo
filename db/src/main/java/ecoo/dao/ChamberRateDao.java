package ecoo.dao;

import ecoo.data.ChamberRate;

import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public interface ChamberRateDao extends AuditLogDao<Integer, ChamberRate> {
    /**
     * Returns a list of Chamber rates for the given chamber.
     *
     * @param chamberId The chamber id to evaluate.
     * @param member    The member indicator.
     * @return The list of associated Chambers.
     */
    ChamberRate findRateByChamberAndMemberIndicator(Integer chamberId, boolean member);

    /**
     * Returns a list of Chamber rates for the given chamber.
     *
     * @param chamberId The chamber id to evaluate.
     * @return The list of associated Chambers.
     */
    List<ChamberRate> findRatesByChamber(Integer chamberId);
}
