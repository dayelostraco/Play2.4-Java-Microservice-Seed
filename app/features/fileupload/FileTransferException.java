package features.fileupload;

/**
 * User: Dayel Ostraco
 * Date: 11/08/14
 * Time: 9:47 PM
 */
public class FileTransferException extends Exception {

    private static final long serialVersionUID = -4111293526621417418L;

    /**
     * Message only exception wrapper.
     *
     * @param message Custom exception message
     */
    public FileTransferException(String message) {
        super(message);
    }

    /**
     * Message and Exception wrapper.
     *
     * @param message Custom exception message
     * @param e Throwable Exception
     */
    public FileTransferException(String message, Throwable e) {
        super(message, e);
    }
}
