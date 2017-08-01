package ecoo.dao.impl.hibernate;

import ecoo.dao.ChamberDocumentDao;
import ecoo.data.ChamberDocument;
import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;
import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/ecoo-db-test-context.xml"})
public class ChamberDocumentDaoImplTest {

    @Autowired
    private ChamberDocumentDao chamberDocumentDao;

    @BeforeClass
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testFindByUserAndType() {
        final ChamberDocument doc = chamberDocumentDao.findByChamberAndType(1, "L");
        Assert.assertNotNull(doc);
    }

    @Test
    public void testFindByUser() {
        final List<ChamberDocument> docs = chamberDocumentDao.findByChamber(1);
        Assert.assertNotNull(docs);
    }

    @Test
    public void testFindAll() {
        final Collection<ChamberDocument> data = chamberDocumentDao.findAll();
        Assert.assertNotNull(data);
        Assert.assertTrue(data.size() >= 0);
    }
}