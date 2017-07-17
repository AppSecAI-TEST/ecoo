package ecoo.data;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public final class CertificateOfOriginCloneBuilder {

    private CertificateOfOrigin certificateOfOrigin;

    private CertificateOfOriginCloneBuilder() {
    }

    public static CertificateOfOriginCloneBuilder aCertificateOfOrigin() {
        return new CertificateOfOriginCloneBuilder();
    }

    public CertificateOfOriginCloneBuilder withCertificateOfOrigin(CertificateOfOrigin certificateOfOrigin) {
        this.certificateOfOrigin = certificateOfOrigin;
        return this;
    }

    public CertificateOfOrigin clone(Integer shipmentId) {
        CertificateOfOrigin certificateOfOrigin = new CertificateOfOrigin();
        certificateOfOrigin.setPrimaryId(shipmentId);
        certificateOfOrigin.setRemarks(this.certificateOfOrigin.getRemarks());

        for (CertificateOfOriginLine certificateOfOriginLine : this.certificateOfOrigin.getLines()) {
            certificateOfOrigin.getLines().add(CertificateOfOriginLineCloneBuilder.aCertificateOfOriginLine()
                    .withCertificateOfOriginLine(certificateOfOriginLine)
                    .clone());
        }
        return certificateOfOrigin;
    }
}
