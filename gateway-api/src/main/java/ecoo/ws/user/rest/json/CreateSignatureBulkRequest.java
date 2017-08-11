package ecoo.ws.user.rest.json;

import ecoo.data.Signature;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since August 2017
 */
public class CreateSignatureBulkRequest {

    private Collection<Signature> signatures;

    public Collection<Signature> getSignatures() {
        return signatures;
    }

    public void setSignatures(Collection<Signature> signatures) {
        this.signatures = signatures;
    }

    @Override
    public String toString() {
        return "CreateSignatureBulkRequest{" +
                "signatures=" + signatures +
                '}';
    }
}
