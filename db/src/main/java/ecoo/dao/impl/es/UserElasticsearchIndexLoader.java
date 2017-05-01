package ecoo.dao.impl.es;

import ecoo.dao.ChamberAdminDao;
import ecoo.dao.UserDao;
import ecoo.data.ChamberAdmin;
import ecoo.data.ChamberGroupIdentityFactory;
import ecoo.data.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Component
public class UserElasticsearchIndexLoader {

    private static final Logger LOG = LoggerFactory.getLogger(UserElasticsearchIndexLoader.class);

    private static final int DEFAULT_THREAD_POOL_SIZE = 5;

    private static final int DEFAULT_THREAD_DOCUMENT_SIZE = 1000;

    private final ExecutorService threadPool;

    private final UserDao userDao;

    private final ChamberAdminDao chamberAdminDao;

    private final UserElasticsearchRepository userElasticsearchRepository;

    @Autowired
    public UserElasticsearchIndexLoader(UserDao userDao, ChamberAdminDao chamberAdminDao
            , @Qualifier("userElasticsearchRepository") UserElasticsearchRepository userElasticsearchRepository) {
        Assert.notNull(userDao);
        Assert.notNull(chamberAdminDao);
        Assert.notNull(userElasticsearchRepository);
        this.userDao = userDao;
        this.chamberAdminDao = chamberAdminDao;
        this.userElasticsearchRepository = userElasticsearchRepository;
        this.threadPool = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
    }

    public Collection<Integer> loadAll() {
        final List<Integer> ids = userDao.findAllIds();
        return loadAll(ids);
    }

    public Collection<Integer> loadAll(final List<Integer> ids) {
        final int totalSize = ids.size();
        LOG.info("Loading {} documents to elasticsearch.", totalSize);
        Assert.notNull(ids);

        final Collection<User> documents = new ArrayList<>();
        final Collection<Future<List<User>>> futures = new ArrayList<>();

        for (int i = 0; i <= totalSize / DEFAULT_THREAD_DOCUMENT_SIZE; i++) {
            final int listLocation = i * DEFAULT_THREAD_DOCUMENT_SIZE;
            final Callable<List<User>> call = () -> load(ids.subList(listLocation,
                    Math.min(listLocation + DEFAULT_THREAD_DOCUMENT_SIZE, totalSize)));
            final Future<List<User>> future = threadPool.submit(call);
            futures.add(future);
            if (LOG.isDebugEnabled()) LOG.debug(String.format("Added FutureTask-%s", futures.size()));
        }

        int importedDocumentsSize;
        final Iterator<Future<List<User>>> iterator = futures.iterator();
        while (iterator.hasNext()) {
            try {
                final Future<List<User>> future = iterator.next();
                final List<User> documentAdd = future.get();
                if (documentAdd == null) continue;
                documents.addAll(documentAdd);

                importedDocumentsSize = documents.size();
                double percentage = Math.round((double) importedDocumentsSize * 100 / totalSize);
                LOG.info("Loaded {} of {} documents to elasticsearch ({}%)", importedDocumentsSize, totalSize, percentage);

            } catch (final InterruptedException | ExecutionException e) {
                LOG.error(e.getMessage(), e);
            } finally {
                iterator.remove();
            }
        }
        LOG.info("Successfully loaded {} documents to elasticsearch.", documents.size());
        return ids;
    }

    public List<User> load(final List<Integer> ids) {
        LOG.info("Group load {} entities", ids.size());
        List<User> data = new ArrayList<>(ids.size());
        for (Integer id : ids) {
            try {
                final User entity = userDao.findByPrimaryId(id);
                if (entity != null) {
                    for (ChamberAdmin chamberAdmin : chamberAdminDao.findByUser(id)) {
                        final String chamberGroupIdentity = ChamberGroupIdentityFactory.build(chamberAdmin.getChamberId());
                        entity.addGroupIdentity(chamberGroupIdentity);
                    }
                    data.add(entity);
                }
            } catch (final Exception e) {
                LOG.error("Error loading: " + id);
                LOG.error(e.getMessage(), e);
            }
        }
        return (List<User>) userElasticsearchRepository.save(data);
    }

    public void deleteAll() {
        userElasticsearchRepository.deleteAll();
    }
}
