package features.fileupload.s3;

import features.fileupload.FileTransferException;
import features.fileupload.S3Service;
import org.apache.commons.lang.StringUtils;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;
import play.Configuration;
import play.Logger;
import play.Play;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

/**
 * Created by Dayel Ostraco
 * 9/29/15.
 */
@SuppressWarnings("unused")
public class S3ServiceImpl implements S3Service {

    private static final Logger.ALogger logger = Logger.of(S3ServiceImpl.class);

    private static final String AWS_ACCESS_KEY = "aws.accessKey";
    private static final String AWS_SECRET_KEY = "aws.secretKey";
    private static final String AWS_S3_BASE_URL = "aws.s3.baseUrl";

    private String s3BaseUrl;
    private org.jets3t.service.S3Service s3Service;

    public S3ServiceImpl() {
        Configuration config = Play.application().configuration();
        s3BaseUrl = config.getString(AWS_S3_BASE_URL);
        s3Service = new RestS3Service(
                new AWSCredentials(config.getString(AWS_ACCESS_KEY), config.getString(AWS_SECRET_KEY)));
    }

    /**
     * Uploads a file's InputStream to the imagesBucket and sets the files content type in S3. The content length is
     * set to avoid memory being used by the S3Object to determine the InputStream's length. A UUID is generated for the
     * file and returned upon a successful upload.
     *
     * @param fileName   Desired name of the file to be uploaded
     * @param file       File to be uploaded
     * @param mimeType   MimeType of the file to be uploaded
     * @param bucketName String of the bucket name file will be uploaded to
     * @return Full URL of the successfully uploaded file
     * @throws FileTransferException
     */
    public String upload(String fileName, File file, String mimeType, String bucketName)
            throws FileTransferException {

        if (StringUtils.isEmpty(bucketName)) {
            throw new FileTransferException("No bucket name was supplied.");
        }

        try {
            S3Object s3Object = new S3Object(file);
            s3Object.setKey(fileName);
            s3Object.setContentType(mimeType);
            s3Object.setBucketName(bucketName);
            s3Object.setServerSideEncryptionAlgorithm(S3Object.SERVER_SIDE_ENCRYPTION__AES256);

            S3Object uploadedFile = s3Service.putObject(bucketName, s3Object);

            return s3BaseUrl + uploadedFile.getBucketName() + "/" + uploadedFile.getName();

        } catch (S3ServiceException e) {
            logger.error("Error when uploading file to the S3 Bucket.", e);
            throw new FileTransferException("Error when uploading file.", e);
        } catch (IOException e) {
            logger.error("Could not read in File.", e);
            throw new FileTransferException("Could not read in File.", e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Could not find correct encryption algorithm.", e);
            throw new FileTransferException("Could not find correct encryption algorithm.", e);
        }
    }

    /**
     * Uploads a File to the supplied bucketName and sets the files content type in S3. The content length is
     * set to avoid memory being used by the S3Object to determine the File's length. A UUID is generated for the
     * file and returned upon a successful upload.
     *
     * @param fileName   Desired name of the file to be uploaded
     * @param mimeType   MimeType of the file to be uploaded
     * @param metaData   Metadata Map<String, Object>
     * @param file       File to be uploaded
     * @param bucketName String of the bucket name file will be uploaded to
     * @param folderName String of the subfolder you want the file to be uploaded to within the passed in bucketName
     * @return Full URL of the successfully uploaded file
     * @throws FileTransferException
     */
    public String upload(String fileName, String mimeType, Map<String, Object> metaData, File file,
                          String bucketName, String folderName)
            throws FileTransferException {

        if (StringUtils.isEmpty(bucketName)) {
            throw new FileTransferException("No bucket name was supplied.");
        }

        try {
            S3Object s3Object = new S3Object(file);
            if (StringUtils.isNotBlank(folderName)) {
                s3Object.setKey(folderName + "/" + fileName);
            } else {
                s3Object.setKey(fileName);
            }
            s3Object.setContentType(mimeType);
            s3Object.setContentLength(file.length());
            s3Object.addAllMetadata(metaData);
            s3Object.setBucketName(bucketName);
            s3Object.setServerSideEncryptionAlgorithm(S3Object.SERVER_SIDE_ENCRYPTION__AES256);

            S3Object uploadedFile = s3Service.putObject(bucketName, s3Object);

            return s3BaseUrl + uploadedFile.getBucketName() + "/" + uploadedFile.getKey();

        } catch (S3ServiceException e) {
            logger.error("Error when uploading file to the S3 Bucket.", e);
            throw new FileTransferException("Error when uploading file.", e);
        } catch (IOException e) {
            logger.error("Could not read in File.", e);
            throw new FileTransferException("Could not read in File.", e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Could not find correct encryption algorithm.", e);
            throw new FileTransferException("Could not find correct encryption algorithm.", e);
        }
    }

    /**
     * Uploads a file's InputStream to the imagesBucket and sets the files content type in S3. The content length is
     * set to avoid memory being used by the S3Object to determine the InputStream's length. A UUID is generated for the
     * file and returned upon a successful upload.
     *
     * @param fileName    desired file name
     * @param inputStream InputStream to be uploaded
     * @param mimeType    MimeType of the file to be uploaded
     * @param bucketName  String of the bucket name file will be uploaded to
     * @return Full URL of the successfully uploaded file
     * @throws FileTransferException
     */
    public String upload(String fileName, InputStream inputStream, String mimeType, String bucketName)
            throws FileTransferException {

        if (StringUtils.isEmpty(bucketName)) {
            throw new FileTransferException("No bucket name was supplied.");
        }

        try {
            S3Object s3Object = new S3Object();
            s3Object.setDataInputStream(inputStream);
            s3Object.setKey(fileName);
            s3Object.setContentType(mimeType);
            s3Object.setBucketName(bucketName);
            s3Object.setServerSideEncryptionAlgorithm(S3Object.SERVER_SIDE_ENCRYPTION__AES256);

            S3Object uploadedFile = s3Service.putObject(bucketName, s3Object);

            return s3BaseUrl + uploadedFile.getBucketName() + "/" + uploadedFile.getName();

        } catch (S3ServiceException e) {
            logger.error("Error when uploading file to the S3 Bucket.", e);
            throw new FileTransferException("Error when uploading file.", e);
        }
    }

    /**
     * Uploads a file's InputStream to the imagesBucket and sets the files content type in S3. The content length is
     * set to avoid memory being used by the S3Object to determine the InputStream's length. A UUID is generated for the
     * file and returned upon a successful upload.
     *
     * @param fileName    desired file name
     * @param mimeType    MimeType of the file to be uploaded
     * @param metaData    Metadata Map<String, Object>
     * @param inputStream InputStream to be uploaded
     * @param bucketName  String of the bucket name file will be uploaded to
     * @param folderName  String of the subfolder you want the file to be uploaded to within the passed in bucketName
     * @return Full URL of the successfully uploaded file
     * @throws FileTransferException
     */
    public String upload(String fileName, String mimeType, Map<String, Object> metaData,
                          InputStream inputStream, String bucketName, String folderName)
            throws FileTransferException {

        if (StringUtils.isEmpty(bucketName)) {
            throw new FileTransferException("No bucket name was supplied.");
        }

        try {
            S3Object s3Object = new S3Object();
            s3Object.setDataInputStream(inputStream);
            if (StringUtils.isNotBlank(folderName)) {
                s3Object.setKey(folderName + "/" + fileName);
            } else {
                s3Object.setKey(fileName);
            }
            s3Object.setContentType(mimeType);
            s3Object.addAllMetadata(metaData);
            s3Object.setBucketName(bucketName);
            s3Object.setServerSideEncryptionAlgorithm(S3Object.SERVER_SIDE_ENCRYPTION__AES256);

            S3Object uploadedFile = s3Service.putObject(bucketName, s3Object);

            return s3BaseUrl + uploadedFile.getBucketName() + "/" + uploadedFile.getKey();

        } catch (S3ServiceException e) {
            logger.error("Error when uploading file to the S3 Bucket.", e);
            throw new FileTransferException("Error when uploading file.", e);
        }
    }

    /**
     * Returns a temporary URL for the passed in fileName in the imagesBucket complete with a Signature based on the
     * AWS_SECRET_KEY, fileName and encryptionExpirationInHours.
     *
     * @param bucketName     bucketName where the object is stored in S3
     * @param fileKey        File key of the object in the S3
     * @param expirationDate The Date the temporary dowload URL will expire
     * @return Temporary and one-time use URL for GET'ing an S3 Object
     */
    public String getTemporaryDownloadUrlForFileById(String bucketName, String fileKey, Date expirationDate) {
        return s3Service.createSignedGetUrl(bucketName, fileKey, expirationDate, false);
    }
}
