package ecoo.ws.system.rest;

import ecoo.data.Captcha;
import ecoo.service.CaptchaService;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@RestController
@RequestMapping("/api/captcha")
public class CaptchaResource extends BaseResource {

    private CaptchaService captchaService;

    @Autowired
    public CaptchaResource(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    public ResponseEntity<Captcha> findRandom() {
        return ResponseEntity.ok(captchaService.findRandom());
    }
}
