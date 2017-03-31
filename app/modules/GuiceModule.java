package modules;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import features.email.EmailService;
import features.email.ses.SimpleEmailServiceImpl;
import features.fileupload.S3Service;
import features.fileupload.s3.S3ServiceImpl;
import features.image.ImageResizeService;
import features.image.resizer.ThumbnailatorServiceImpl;
import features.sms.SmsService;
import features.sms.twilio.TwilioSmsServiceImpl;
import services.SampleModelService;
import services.TransformService;
import services.impl.OrikaTransformServiceImpl;
import services.impl.SampleModelServiceImpl;

public class GuiceModule extends AbstractModule {
    protected void configure() {

        // Core Bindings
        bind(TransformService.class)
                .annotatedWith(Names.named("OrikaTransformService"))
                .to(OrikaTransformServiceImpl.class);

        bind(SampleModelService.class)
                .annotatedWith(Names.named("SampleModelService"))
                .to(SampleModelServiceImpl.class);

        // Feature Bindings
        bind(EmailService.class)
                .annotatedWith(Names.named("SimpleEmailService"))
                .to(SimpleEmailServiceImpl.class);

        bind(S3Service.class)
                .annotatedWith(Names.named("S3Service"))
                .to(S3ServiceImpl.class);

        bind(ImageResizeService.class)
                .annotatedWith(Names.named("ThumbnailatorService"))
                .to(ThumbnailatorServiceImpl.class);

        bind(SmsService.class)
                .annotatedWith(Names.named("TwilioService"))
                .to(TwilioSmsServiceImpl.class);
    }
}
