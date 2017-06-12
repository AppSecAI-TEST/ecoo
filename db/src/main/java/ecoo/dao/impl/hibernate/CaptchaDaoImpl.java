package ecoo.dao.impl.hibernate;

import ecoo.dao.CaptchaDao;
import ecoo.data.Captcha;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@SuppressWarnings("unused")
@Repository(value = "captchaDao")
public class CaptchaDaoImpl extends BaseAuditLogDaoImpl<Integer, Captcha> implements CaptchaDao {

    @Autowired
    public CaptchaDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, Captcha.class);
    }
}
