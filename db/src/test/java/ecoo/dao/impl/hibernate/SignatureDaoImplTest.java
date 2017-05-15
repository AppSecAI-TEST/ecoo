package ecoo.dao.impl.hibernate;

import ecoo.dao.SignatureDao;
import ecoo.data.Signature;
import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/ecoo-db-test-context.xml"})
@Transactional
public class SignatureDaoImplTest {

    @Autowired
    private SignatureDao signatureDao;

    @BeforeClass
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testFindAll() {
        final Collection<Signature> data = signatureDao.findAll();
        Assert.assertNotNull(data);
    }

    @Test
    public void testFindByPersonalReference() {
        final String personalReference = UUID.randomUUID().toString();

        Signature signature = signatureDao.findByPersonalReference(personalReference);
        Assert.assertNull(signature);

        final Signature newSignature = new Signature();
        newSignature.setPersonalRefValue(personalReference);
        newSignature.setFirstName("BART");
        newSignature.setLastName("SIMPSON");
        newSignature.setEncodedImage("image");
        newSignature.setDateCreated(new Date());

        signatureDao.save(newSignature);

        signature = signatureDao.findByPersonalReference(personalReference);
        Assert.assertNotNull(signature);
        Assert.assertEquals("BART", signature.getFirstName());
        Assert.assertEquals("SIMPSON", signature.getLastName());
        Assert.assertEquals("image", signature.getEncodedImage());
    }
}