package ecoo.dao.impl.es;

import ecoo.dao.ShipmentActivityGroupDao;
import ecoo.data.ShipmentActivityGroup;
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
 * @since July 2017
 */
@Component
public class ShipmentActivityGroupElasticsearchIndexLoader {

    private static final Logger LOG = LoggerFactory.getLogger(ShipmentActivityGroupElasticsearchIndexLoader.class);

    private static final int DEFAULT_THREAD_POOL_SIZE = 5;

    private static final int DEFAULT_THREAD_DOCUMENT_SIZE = 1000;

    private final ExecutorService threadPool;

    private final ShipmentActivityGroupDao shipmentActivityGroupDao;

    private final ShipmentActivityGroupElasticsearchRepository shipmentActivityGroupElasticsearchRepository;

    @Autowired
    public ShipmentActivityGroupElasticsearchIndexLoader(ShipmentActivityGroupDao shipmentActivityGroupDao
            , @Qualifier("shipmentActivityGroupElasticsearchRepository") ShipmentActivityGroupElasticsearchRepository shipmentActivityGroupElasticsearchRepository) {
        Assert.notNull(shipmentActivityGroupDao);
        Assert.notNull(shipmentActivityGroupElasticsearchRepository);
        this.shipmentActivityGroupDao = shipmentActivityGroupDao;
        this.shipmentActivityGroupElasticsearchRepository = shipmentActivityGroupElasticsearchRepository;
        this.threadPool = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
    }

    public Collection<Integer> loadAll() {
        final List<Integer> ids = shipmentActivityGroupDao.findAllIds();
        return loadAll(ids);
    }

    public Collection<Integer> loadAll(final List<Integer> ids) {
        final int totalSize = ids.size();
        LOG.info("Loading {} documents to elasticsearch.", totalSize);
        Assert.notNull(ids);

        final Collection<ShipmentActivityGroup> documents = new ArrayList<>();
        final Collection<Future<List<ShipmentActivityGroup>>> futures = new ArrayList<>();

        for (int i = 0; i <= totalSize / DEFAULT_THREAD_DOCUMENT_SIZE; i++) {
            final int listLocation = i * DEFAULT_THREAD_DOCUMENT_SIZE;
            final Callable<List<ShipmentActivityGroup>> call = () -> load(ids.subList(listLocation,
                    Math.min(listLocation + DEFAULT_THREAD_DOCUMENT_SIZE, totalSize)));
            final Future<List<ShipmentActivityGroup>> future = threadPool.submit(call);
            futures.add(future);
            if (LOG.isDebugEnabled()) LOG.debug(String.format("Added FutureTask-%s", futures.size()));
        }

        int importedDocumentsSize;
        final Iterator<Future<List<ShipmentActivityGroup>>> iterator = futures.iterator();
        while (iterator.hasNext()) {
            try {
                final Future<List<ShipmentActivityGroup>> future = iterator.next();
                final List<ShipmentActivityGroup> documentAdd = future.get();
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

    private List<ShipmentActivityGroup> load(final List<Integer> ids) {
        LOG.info("Group load {} entities", ids.size());
        List<ShipmentActivityGroup> data = new ArrayList<>(ids.size());
        for (Integer id : ids) {
            try {
                final ShipmentActivityGroup entity = shipmentActivityGroupDao.findByPrimaryId(id);
                if (entity != null) {
                    data.add(entity);
                }
            } catch (final Exception e) {
                LOG.error("Error loading: " + id);
                LOG.error(e.getMessage(), e);
            }
        }
        if (data.isEmpty()) {
            return data;
        } else {
            return (List<ShipmentActivityGroup>) shipmentActivityGroupElasticsearchRepository.save(data);
        }
    }

    public void deleteAll() {
        shipmentActivityGroupElasticsearchRepository.deleteAll();
    }
}
