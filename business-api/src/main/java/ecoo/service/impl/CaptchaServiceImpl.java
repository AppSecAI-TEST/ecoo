package ecoo.service.impl;


import ecoo.dao.impl.CaptchaDao;
import ecoo.data.Captcha;
import ecoo.service.CaptchaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Service(value = "captchaService")
public final class CaptchaServiceImpl extends AuditTemplate<Integer, Captcha, CaptchaDao> implements CaptchaService {

    private CaptchaDao captchaDao;

    @Autowired
    public CaptchaServiceImpl(CaptchaDao captchaDao) {
        super(captchaDao);
        this.captchaDao = captchaDao;
    }

    /**
     * Returns the {@link Boolean} representation of whether the value matches the given captcha
     * value.
     *
     * @param plainString The plain string value to evaluate.
     * @param captcha     The captcha to match.
     * @return true if value matches captcha value
     */
    @Override
    public boolean validate(String plainString, Captcha captcha) {
        if (StringUtils.isBlank(plainString) || captcha == null) {
            return false;
        }
        return plainString.equalsIgnoreCase(captcha.getValue());
    }

    /**
     * Returns a collection a random captcha.
     *
     * @return a captcha
     */
    @Override
    public Captcha findRandom() {
        final List<Captcha> allData = new LinkedList<>();
        allData.addAll(captchaDao.findAll());

        int max = allData.size() - 1;
        return allData.get(new SecureRandom().nextInt(max));
    }
}
