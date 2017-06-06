package ecoo.service;

import ecoo.data.CertificateOfOrigin;

import java.util.List;

/**
 * @author Justin Rundle
 * @since June 2017
 */
public interface CertificateOfOriginService extends CrudService<Integer, CertificateOfOrigin>, AuditedModelAware<CertificateOfOrigin> {

    List<CertificateOfOrigin> findCertificateOfOriginsByShipmentId(Integer shipmentId);
}
