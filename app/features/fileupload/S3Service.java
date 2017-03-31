package features.fileupload;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

/**
 * Created by Dayel Ostraco
 * 10/3/15.
 */
public interface S3Service {

    String upload(String fileName, File file, String mimeType, String bucketName)
            throws FileTransferException;

    String upload(String fileName, String mimeType, Map<String, Object> metaData, File file,
                  String bucketName, String folderName)
            throws FileTransferException;

    String upload(String fileName, InputStream inputStream, String mimeType, String bucketName)
            throws FileTransferException;

    String upload(String fileName, String mimeType, Map<String, Object> metaData,
                  InputStream inputStream, String bucketName, String folderName)
            throws FileTransferException;

    String getTemporaryDownloadUrlForFileById(String bucketName, String fileKey, Date expirationDate);
}
