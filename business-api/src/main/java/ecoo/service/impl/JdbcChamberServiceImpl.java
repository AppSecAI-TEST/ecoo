package ecoo.service.impl;

import ecoo.dao.ChamberDao;
import ecoo.data.Chamber;
import ecoo.service.ChamberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Service
public class JdbcChamberServiceImpl extends JdbcAuditTemplate<Integer, Chamber, ChamberDao>
        implements ChamberService {

    @Autowired
    public JdbcChamberServiceImpl(ChamberDao chamberDao) {
        super(chamberDao);
    }
}
