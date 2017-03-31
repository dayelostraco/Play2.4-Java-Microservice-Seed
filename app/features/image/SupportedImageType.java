package features.image;

/**
 * Created by Dayel Ostraco
 * 9/25/15.
 */
public enum SupportedImageType {

    JPEG("jpg", "image/jpeg"),
    PNG("png", "image/png"),
    GIF("gif", "image/gif");

    private String extension;
    private String mimeType;

    SupportedImageType(String extension, String mimeType) {
        this.extension = extension;
        this.mimeType = mimeType;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public static SupportedImageType getSupportedImageTypeByExtension(String extension) {
        SupportedImageType supportedImageType = null;

        for (SupportedImageType imageType : SupportedImageType.values()) {
            if (imageType.getExtension().equalsIgnoreCase(extension)) {
                supportedImageType = imageType;
                break;
            }
        }

        return supportedImageType;
    }

    public static SupportedImageType getSupportedImageTypeByMimeType(String mimeType) {
        SupportedImageType supportedImageType = null;

        for (SupportedImageType imageType : SupportedImageType.values()) {
            if (imageType.getMimeType().equalsIgnoreCase(mimeType)) {
                supportedImageType = imageType;
                break;
            }
        }

        return supportedImageType;
    }
}
