package ecoo.service.impl;

import ecoo.dao.UserDocumentDao;
import ecoo.data.UserDocument;
import ecoo.service.UserDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
