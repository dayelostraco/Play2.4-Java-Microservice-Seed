package features.image.resizer;

import features.image.ImageException;
import features.image.ImageResizeService;
import features.image.SupportedImageType;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.filters.Canvas;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ThumbnailatorServiceImpl implements ImageResizeService {

    public InputStream scaleImage(File imageFile, SupportedImageType supportedImageType,
                                  Integer width, Integer height)
            throws ImageException {

        try {
            // Resize the image preserving aspect ratio and fit the image to
            BufferedImage resizedImage = Thumbnails.of(imageFile)
                    .size(new Double(width * 1.5).intValue(), new Double(height * 1.5).intValue())
                    .addFilter(new Canvas(width, height, Positions.CENTER, true))
                    .keepAspectRatio(true)
                    .asBufferedImage();

            // Write to BufferedImage to a byte[]
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, supportedImageType.getExtension(), byteArrayOutputStream);
            byte[] imageInBytes = byteArrayOutputStream.toByteArray();

            // Convert to InputStream
            return new ByteArrayInputStream(imageInBytes);
        } catch (IOException e) {
            throw new ImageException("Could not resize the provide imageFile.", e);
        }
    }
}
