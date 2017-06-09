package ecoo.service;


import ecoo.data.Captcha;

/**
 * @author Justin Rundle
 * @since June 2017
 */
public interface CaptchaService {

    /**
     * Returns the {@link Boolean} representation of whether the value matches the given captcha
     * value.
     *
     * @param plainString The plain string value to evaluate.
     * @param captcha     The captcha to match.
     * @return true if value matches captcha value
     */
    boolean validate(String plainString, Captcha captcha);

    /**
     * Returns a collection a random captcha.
     *
     * @return a captcha
     */
    Captcha findRandom();
}
