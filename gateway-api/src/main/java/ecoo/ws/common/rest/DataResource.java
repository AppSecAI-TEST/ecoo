package ecoo.ws.common.rest;

import ecoo.data.*;
import ecoo.data.upload.UploadType;
import ecoo.service.DataService;
import ecoo.ws.common.json.DataQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/data")
public class DataResource extends BaseResource {

    private DataService dataService;

    @RequestMapping(value = "/amountTypes/schema/{schema}", method = RequestMethod.GET)
    public ResponseEntity<List<AmountType>> amountTypes(@PathVariable String schema) {
        return ResponseEntity.ok(dataService.amountTypes(schema));
    }

    @RequestMapping(value = "/currencies", method = RequestMethod.GET)
    public ResponseEntity<Collection<Currency>> currencies() {
        return ResponseEntity.ok(dataService.currencies());
    }

    @RequestMapping(value = "/transportTypes", method = RequestMethod.GET)
    public ResponseEntity<Collection<TransportType>> transportTypes() {
        return ResponseEntity.ok(dataService.transportTypes());
    }

    @RequestMapping(value = "/uploadTypes", method = RequestMethod.GET)
    public ResponseEntity<Collection<UploadType>> uploadTypes() {
        final Collection<UploadType> data = new ArrayList<>();
        for (UploadType.Type type : UploadType.Type.values()) {
            data.add(new UploadType(type));
        }
        return ResponseEntity.ok(data);
    }

    @Autowired
    public DataResource(DataService dataService) {
        this.dataService = dataService;
    }

    @RequestMapping(value = "/provinces/q/{q}", method = RequestMethod.GET)
    public ResponseEntity<Collection<DataQueryResponse>> queryProvinces(@PathVariable String q) {
        return ResponseEntity.ok(dataService.provinces().stream()
                .filter(province -> province.getDescription().toLowerCase().contains(q.toLowerCase()))
                .map(province -> new DataQueryResponse(province, "world.png"))
                .collect(Collectors.toCollection(HashSet::new)));
    }

    @RequestMapping(value = "/provinces/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Province> provinceById(@PathVariable Integer id) {
        for (Province province : dataService.provinces()) {
            if (province.getPrimaryId().equals(id)) {
                return ResponseEntity.ok(province);
            }
        }
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/provinces", method = RequestMethod.GET)
    public ResponseEntity<Collection<Province>> provinces() {
        return ResponseEntity.ok(dataService.provinces());
    }

    @RequestMapping(value = "/countries", method = RequestMethod.GET)
    public ResponseEntity<Collection<Country>> countries() {
        return ResponseEntity.ok(dataService.countries());
    }

    @RequestMapping(value = "/documentTypes", method = RequestMethod.GET)
    public ResponseEntity<Collection<DocumentType>> documentTypes() {
        final Collection<DocumentType> data = new ArrayList<>();
        // TODO: Read from db in the future.
        data.add(new DocumentType("PCR", "PROOF OF COMPANY REGISTRATION"));
        return ResponseEntity.ok(data);
    }

    @RequestMapping(value = "/companyTypes", method = RequestMethod.GET)
    public ResponseEntity<Collection<CompanyType>> companyTypes() {
        final Collection<CompanyType> data = new ArrayList<>();
        // TODO: Read from db in the future.
        data.add(new CompanyType("E", "EXPORTER"));
        data.add(new CompanyType("FF", "FREIGHT FORWARDER"));
        return ResponseEntity.ok(data);
    }

    @RequestMapping(value = "/titles", method = RequestMethod.GET)
    public ResponseEntity<Collection<String>> titles() {
        final Collection<String> data = dataService.titles()
                .stream()
                .map(Title::getPrimaryId)
                .collect(Collectors.toCollection(ArrayList::new));
        return ResponseEntity.ok(data);
    }

    @RequestMapping(value = "/personalReferenceTypes", method = RequestMethod.GET)
    public ResponseEntity<Collection<PersonalReferenceType>> personalReferenceTypes() {
        final Collection<PersonalReferenceType> data = new ArrayList<>();
        // TODO: Read from db in the future.
        data.add(new PersonalReferenceType("RSA", "SOUTH AFRICAN ID"));
        data.add(new PersonalReferenceType("P", "PASSPORT"));
        data.add(new PersonalReferenceType("OTH", "OTHER"));
        return ResponseEntity.ok(data);
    }

    @RequestMapping(value = "/communicationPreferenceTypes", method = RequestMethod.GET)
    public ResponseEntity<Collection<CommunicationType>> communicationPreferenceTypes() {
        final Collection<CommunicationType> data = new ArrayList<>();
        // TODO: Read from db in the future.
//        data.add(new CommunicationType("S", "SMS"));
        data.add(new CommunicationType("E", "EMAIL"));
        return ResponseEntity.ok(data);
    }
}