package ecoo.service;

import ecoo.data.CertificateOfOrigin;
import ecoo.data.CertificateOfOriginLine;

/**
 * @author Justin Rundle
 * @since June 2017
 */
public interface CertificateOfOriginService extends CrudService<Integer, CertificateOfOrigin>
        , AuditedModelAware<Integer, CertificateOfOrigin> {

    CertificateOfOrigin delete(CertificateOfOrigin certificateOfOrigin, CertificateOfOriginLine line);

    CertificateOfOriginLine findLineById(Integer id);

    CertificateOfOriginLine save(CertificateOfOriginLine certificateOfOriginLine);
}
