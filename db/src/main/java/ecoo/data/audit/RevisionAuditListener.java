package ecoo.data.audit;

import ecoo.data.KnownUser;
import ecoo.data.User;
import ecoo.security.UserAuthentication;
import org.hibernate.envers.RevisionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

/**
 * This Listener is invoked after the value has been saved to the audited tables and is used to
 * populate the username value of the {@link Revision} .
 *
 * @author Justin Rundle
 * @since August 2016
 */
public final class RevisionAuditListener implements RevisionListener {

    private static final Logger LOG = LoggerFactory.getLogger(RevisionAuditListener.class.getSimpleName());

    @Override
    public void newRevision(Object revisionEntity) {
        final User currentUser = currentUser();

        final Revision revision = (Revision) revisionEntity;
        revision.setModifiedBy(currentUser);
        revision.setDateModified(new Date());

        if (LOG.isTraceEnabled()) {
            LOG.trace(String.format("%s", revision.toString()));
        }
    }

    private User currentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication instanceof UserAuthentication) {
            return (User) authentication.getDetails();
        }
        return new User(KnownUser.Anonymous.getPrimaryId());
    }
}
