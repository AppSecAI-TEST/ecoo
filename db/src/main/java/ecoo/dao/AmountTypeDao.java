package ecoo.dao;


import ecoo.data.AmountType;

import java.util.List;


/**
 * @author Justin Rundle
 * @since June 2017
 */
public interface AmountTypeDao extends AuditLogDao<Integer, AmountType> {

    List<AmountType> findAmountTypesBySchema(String schema);

}
