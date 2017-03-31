package features.image;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Dayel Ostraco
 * 10/3/15.
 */
public interface ImageResizeService {

    InputStream scaleImage(File imageFile, SupportedImageType supportedImageType,
                           Integer width, Integer height) throws ImageException;
}
