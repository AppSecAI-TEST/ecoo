package ecoo.service.impl;

import ecoo.dao.UserDocumentDao;
import ecoo.data.UserDocument;
import ecoo.service.UserDocumentService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Service
public class UserDocumentServiceImpl extends AuditTemplate<Integer, UserDocument, UserDocumentDao>
        implements UserDocumentService {

    private UserDocumentDao userDocumentDao;

    @Autowired
    public UserDocumentServiceImpl(UserDocumentDao userDocumentDao) {
        super(userDocumentDao);
        this.userDocumentDao = userDocumentDao;
    }

    /**
     * Returns the user document for the user and the type.
     *
     * @param userId       The user id.
     * @param documentType The document type.
     * @return The user document or null.
     */
    @Override
    public UserDocument findByUserAndType(Integer userId, String documentType) {
        return userDocumentDao.findByUserAndType(userId, documentType);
    }

    /**
     * Returns a list of user documents.
     *
     * @param userId The user pk.
     * @return The list of documents.
     */
    @Override
    public List<UserDocument> findByUser(Integer userId) {
        return userDocumentDao.findByUser(userId);
    }

    /**
     * Method called before save is called.
     *
     * @param entity The entity to save.
     */
    @Override
    protected void beforeSave(UserDocument entity) {
        if(entity.isNew()) {
            entity.setStartDate(new Date());
            entity.setEndDate(DateTime.parse("99991231", DateTimeFormat.forPattern("yyyyMMdd")).toDate());
        }
    }
}
