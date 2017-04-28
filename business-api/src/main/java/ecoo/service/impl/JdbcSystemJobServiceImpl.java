package ecoo.service.impl;

import ecoo.dao.SystemJobDao;
import ecoo.data.SystemJob;
import ecoo.service.SystemJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Service
public class JdbcSystemJobServiceImpl extends JdbcTemplateService<Integer, SystemJob> implements SystemJobService {

    private SystemJobDao systemJobDao;

    @Autowired
    public JdbcSystemJobServiceImpl(SystemJobDao systemJobDao) {
        super(systemJobDao);
        this.systemJobDao = systemJobDao;
    }

    /**
     * The method to save a new system job.
     *
     * @param systemJob The system job to save.
     * @return The saved system job.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public SystemJob register(SystemJob systemJob) {
        Assert.notNull(systemJob, "The variable systemJob cannot be null.");
        Assert.hasText(systemJob.getClassName(), "The className cannot be null or blank.");

        if (!systemJob.isNew()) {
            throw new IllegalArgumentException("systemJob is not new.");
        }
        systemJob.setStartTime(new Date());
        systemJob.setEndTime(null);
        systemJob.setProcessingTime(0);
        systemJob.setSuccessfulProcessingInd("N");

        systemJobDao.save(systemJob);
        return systemJob;
    }

    /**
     * Returns all the system jobs for the given class name.
     *
     * @param className The class name to evaluate.
     * @return The collection.
     */
    @Override
    public Collection<SystemJob> findByClassName(String className) {
        Assert.hasText(className);
        return systemJobDao.findByClassName(className);
    }

    /**
     * Returns all the most recent jobs for each class name.
     *
     * @return The collection.
     */
    @Override
    public Collection<SystemJob> findMostRecentJobs() {
        return systemJobDao.findMostRecentJobs();
    }
}
