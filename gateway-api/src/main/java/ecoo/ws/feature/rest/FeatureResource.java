package ecoo.ws.feature.rest;

import ecoo.data.Feature;
import ecoo.data.audit.Revision;
import ecoo.service.FeatureService;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/features")
public class FeatureResource extends BaseResource {

    @Autowired
    private FeatureService featureService;

    @RequestMapping(value = "/createdBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findCreatedBy(@PathVariable Integer id) {
        final Feature feature = featureService.findById(id);
        return ResponseEntity.ok(featureService.findCreatedBy(feature));
    }

    @RequestMapping(value = "/modifiedBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findModifiedBy(@PathVariable Integer id) {
        final Feature feature = featureService.findById(id);
        return ResponseEntity.ok(featureService.findCreatedBy(feature));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Feature> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(featureService.findById(id));
    }


    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Feature> delete(@PathVariable Integer id) {
        final Feature feature = featureService.findById(id);
        featureService.delete(feature);
        return ResponseEntity.ok(feature);
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<Feature> findFeatureByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(featureService.findByName(name));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Collection<Feature>> findAll() {
        return ResponseEntity.ok(featureService.findAll());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Feature> save(@RequestBody Feature feature) {
        return ResponseEntity.ok(featureService.save(feature));
    }
}
