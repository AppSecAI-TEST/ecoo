package ecoo.service.impl;

import ecoo.dao.ChamberAdminDao;
import ecoo.data.ChamberAdmin;
import ecoo.service.ChamberAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Service
public class ChamberAdminServiceImpl extends AuditTemplate<Integer, ChamberAdmin, ChamberAdminDao>
        implements ChamberAdminService {

    private ChamberAdminDao chamberAdminDao;

    @Autowired
    public ChamberAdminServiceImpl(ChamberAdminDao chamberAdminDao) {
        super(chamberAdminDao);
        this.chamberAdminDao = chamberAdminDao;
    }

    @Override
    public Collection<ChamberAdmin> findByUser(Integer userId) {
        return chamberAdminDao.findByUser(userId);
    }
}
