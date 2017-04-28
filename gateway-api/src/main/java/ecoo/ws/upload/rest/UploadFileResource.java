package ecoo.ws.upload.rest;

import ecoo.service.FeatureService;
import ecoo.ws.common.rest.BaseResource;
import ecoo.ws.upload.json.UploadFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@RestController
@RequestMapping("/api/uploads/files")
public class UploadFileResource extends BaseResource {

    private static final Logger LOG = LoggerFactory.getLogger(UploadFileResource.class);

    private FeatureService featureService;

    @Autowired
    public UploadFileResource(FeatureService featureService) {
        this.featureService = featureService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Collection<Object>> findAll() {
        return ResponseEntity.ok(new ArrayList<>());
    }


    @SuppressWarnings("ThrowFromFinallyBlock")
    @RequestMapping(method = RequestMethod.POST, value = "/ref/{reference}")
    public ResponseEntity<UploadFileResponse> uploadFile(@PathVariable String reference
            , @RequestParam("file") MultipartFile file) throws IOException {
        Assert.notNull(file);
        Assert.isTrue(!file.isEmpty());

        final File srcFile = createAndStoreTemporaryFile(reference, file);

        String mimeType = URLConnection.guessContentTypeFromName(srcFile.getAbsolutePath());
        if (mimeType == null) {
            mimeType = "unknown";
        }
        final long kilobytes = srcFile.length() / 1024;
        return ResponseEntity.ok(new UploadFileResponse(srcFile.getAbsolutePath(), mimeType, kilobytes));
    }


    private File createAndStoreTemporaryFile(String reference, MultipartFile file) throws IOException {
        final String path = featureService.tempDirectory() + reference;

        final File targetDir = new File(path);
        if (!targetDir.exists() && targetDir.mkdirs()) {
            LOG.info(String.format("Directory %s created.", targetDir.getAbsolutePath()));
        }

        final File srcFile = new File(targetDir.getAbsolutePath(), file.getOriginalFilename());
        LOG.info(srcFile.getAbsolutePath());

        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(srcFile));
            FileCopyUtils.copy(file.getInputStream(), stream);

        } catch (final IOException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } finally {
            if (stream != null) stream.close();
        }
        return srcFile;
    }
}
