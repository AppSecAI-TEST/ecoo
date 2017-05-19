package ecoo.dao;

import ecoo.data.CompanySignatory;

import java.util.List;

/**
 * @author Justin Rundle
 * @since May 2017
 */
public interface CompanySignatoryDao extends AuditLogDao<Integer, CompanySignatory> {

    List<CompanySignatory> findByCompanyId(Integer companyId);
}
