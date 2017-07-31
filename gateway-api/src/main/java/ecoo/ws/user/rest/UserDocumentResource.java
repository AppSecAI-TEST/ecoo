

package ecoo.ws.user.rest;

import ecoo.bpm.BusinessRuleViolationException;
import ecoo.data.Role;
import ecoo.data.UserDocument;
import ecoo.data.audit.Revision;
import ecoo.service.UserDocumentService;
import ecoo.ws.common.command.DownloadUserDocument;
import ecoo.ws.common.rest.BaseResource;
import ecoo.ws.user.rest.json.CreateUserDocumentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@RestController
@RequestMapping("/api/users/docs")
public class UserDocumentResource extends BaseResource {

    private UserDocumentService userDocumentService;

    @Autowired
    public UserDocumentResource(UserDocumentService userDocumentService) {
        this.userDocumentService = userDocumentService;
    }

    @SuppressWarnings("Duplicates")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<UserDocument> create(@RequestBody CreateUserDocumentRequest createUserDocumentRequest) throws IOException {
        UserDocument userDocument = userDocumentService.findById(createUserDocumentRequest.getPrimaryId());
        if (userDocument == null) {
            userDocument = userDocumentService.findByUserAndType(createUserDocumentRequest.getUserId()
                    , createUserDocumentRequest.getDocumentType());
            if (userDocument == null) {
                userDocument = new UserDocument();
                userDocument.setStartDate(new Date());
            }
        }
        userDocument.setUserId(createUserDocumentRequest.getUserId());
        userDocument.setDocumentType(createUserDocumentRequest.getDocumentType());

        final File srcFile = new File(createUserDocumentRequest.getFileName());
        userDocument.setFileName(srcFile.getName());

        FileInputStream fis = null;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            fis = new FileInputStream(srcFile);

            byte[] buffer = new byte[102400];
            int n;
            while (-1 != (n = fis.read(buffer))) {
                out.write(buffer, 0, n);
            }
            userDocument.setEncodedImage(Base64.getEncoder().encodeToString(out.toByteArray()));
        } finally {
            out.close();
            if (fis != null) fis.close();
        }
        userDocument.setMimeType(createUserDocumentRequest.getMimeType());
        userDocument.setSizeInKb(createUserDocumentRequest.getSizeInKb());

        return ResponseEntity.ok(userDocumentService.save(userDocument));
    }

    @SuppressWarnings("unused")
    @RequestMapping(value = "/id/{id}/download", method = RequestMethod.GET)
    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) throws IOException {
        final UserDocument userDocument = userDocumentService.findById(id);

        final DownloadUserDocument downloadUserDocument = new DownloadUserDocument();
        downloadUserDocument.execute(response, userDocument);
    }

    @RequestMapping(value = "/userId/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<UserDocument>> findByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(userDocumentService.findByUser(userId));
    }

    @RequestMapping(value = "/userId/{userId}/type/{type}", method = RequestMethod.GET)
    public ResponseEntity<UserDocument> findByUserAndType(@PathVariable Integer userId
            , @PathVariable String type) {
        return ResponseEntity.ok(userDocumentService.findByUserAndType(userId, type));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDocument> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(userDocumentService.findById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<UserDocument> save(@RequestBody UserDocument entity) {
        return ResponseEntity.ok(userDocumentService.save(entity));
    }

    @SuppressWarnings("Duplicates")
    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<UserDocument> delete(@PathVariable Integer id) {
        final UserDocument entity = userDocumentService.findById(id);
        if (entity == null) {
            return ResponseEntity.ok(null);
        } else {
            if (currentUser().isInRole(Role.ROLE_SYSADMIN)) {
                return ResponseEntity.ok(userDocumentService.delete(entity));
            } else {
                throw new BusinessRuleViolationException(String.format("System cannot delete document. " +
                        "You do not have permission to delete the document " + "%s.", entity.getPrimaryId()));
            }
        }
    }

    @RequestMapping(value = "/createdBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findCreatedBy(@PathVariable Integer id) {
        final UserDocument entity = userDocumentService.findById(id);
        return ResponseEntity.ok(userDocumentService.findCreatedBy(entity));
    }

    @RequestMapping(value = "/modifiedBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findModifiedBy(@PathVariable Integer id) {
        final UserDocument entity = userDocumentService.findById(id);
        return ResponseEntity.ok(userDocumentService.findModifiedBy(entity));
    }
}