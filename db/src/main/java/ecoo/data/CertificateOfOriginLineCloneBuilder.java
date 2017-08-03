package ecoo.data;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public final class CertificateOfOriginLineCloneBuilder {

    private CertificateOfOriginLine certificateOfOriginLine;

    private CertificateOfOriginLineCloneBuilder() {
    }

    public static CertificateOfOriginLineCloneBuilder aCertificateOfOriginLine() {
        return new CertificateOfOriginLineCloneBuilder();
    }

    public CertificateOfOriginLineCloneBuilder withCertificateOfOriginLine(CertificateOfOriginLine certificateOfOriginLine) {
        this.certificateOfOriginLine = certificateOfOriginLine;
        return this;
    }

    public CertificateOfOriginLine clone() {
        CertificateOfOriginLine certificateOfOriginLine = new CertificateOfOriginLine();
        certificateOfOriginLine.setPrimaryId(null);
        certificateOfOriginLine.setParentId(null);
        certificateOfOriginLine.setMarks(this.certificateOfOriginLine.getMarks());
        certificateOfOriginLine.setDescr(this.certificateOfOriginLine.getDescr());
        certificateOfOriginLine.setOrigin(this.certificateOfOriginLine.getOrigin());
        certificateOfOriginLine.setQty(this.certificateOfOriginLine.getQty());
        certificateOfOriginLine.setPrice(this.certificateOfOriginLine.getPrice());
        certificateOfOriginLine.setAmount(this.certificateOfOriginLine.getAmount());
        certificateOfOriginLine.setPriceStated(this.certificateOfOriginLine.isPriceStated());
        return certificateOfOriginLine;
    }
}
