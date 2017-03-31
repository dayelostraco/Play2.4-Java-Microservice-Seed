# Abstract
A Docker-based Microservice seed using Java [Play](https://www.playframework.com/) 2.4.3 with EBeans (http://ebean-orm.github.io/).

## Introduction
A starting template if you want to develop a microservice with Play! using Java and EBeans.

Then, we have the following objectives:

  * Development should be simple. `activator run` should be enough to run all services at the same time. 
  * Common code, dependencies and modules should be easily shared.
  * Feature code should be separated from core code and considered for sharable code packaging.
  * We should be able to compile, test and run each service separately in development and production. 
  * We should distribute each service separately within a Docker Container.
  * It should be a template ready to use with the following features: 
    * Dependency Injection with Google Guice
    * JPA implementation using EBean
    * JPA Caching via EHCache
    * Testable JPA with In-Memory or Database Implementations complete with data cleanup
    * OAuth2 Implementation
    * S3 Integration
    * Email Services
    * APN Services
    * SMS Services
    * GZip
  * It should explain: 
    * How to share every common code to avoid duplications (models, controllers, ...).
    * How to separate feature code from core code.
    * How to use it for development, test and production.

## Binding

This template uses the dependency injected router [Dependency Injection](https://www.playframework.com/documentation/2.4.x/JavaRouting)

## Database

This template uses HikariCP for the connection pool against a MySQL database. For local development and integration tests, we have configured the application to leverage the H2 in-memory database using the MYSQL dialect.

## Dependency-Injection

Using Google Guice to create Singleton and Scope based components that can be injected into other classed. NOTE: Every route starts with an @ like `@controllers.Application.index()` which leads to non-static actions.

## Logging

The logger is configured for a better output and contains a rolling file
appender. The logs are look like 
`2015-08-17 10:21:17,914 INFO [ForkJoinPool-1-worker-3] application: Class::Method:#Line - Message`.

## API Documentation

Using a modified version of [Swagger Codegen](https://github.com/swagger-api/swagger-codegen/tree/master/samples/client/petstore/java/retrofit2-play24) for Play 2.4.
See `SampleModelController.java` for an example of the usage of Annotated Controller methods. 
The default route for the live Swagger documentation is `/api/1/swagger/documentation`. For ex: [Local Swagger API Docs](http://localhost:9000/api/1/swagger/documentation). 

## JPA

This template uses Play's EBean plugin along . We developed a base model MappedSuperclass that sets up entities with a Long id and created/modified date columns.

## Caching

This template leverages the EHCache implementation that is built into Play 2.4

## Auditing

TODO: Still debating where the responsibility for auditing should reside (application or database)

## Transformation (DTOs)
This template uses Orika to transform objects from one class type to another. This should be used to prevent module specific classes (like Entities) from being exposed client-side. It should be standard practice to have Data Transfer Objects (DTOs) accepted and returned from API endpoints and convert them to module specific classes.

## OAuth2

This template leverages its own self-contained OAuth2 provider and generates the required database tables. You can mark Controller methods with the `@OAuthAuthenticated` annotation to confirm that a user is logged in.

## Role Based Permissions

TODO: Role based permissions via Deadbolt?

## GZIP Compression

This template has enabled a GZip Filter to accept gzipped responses for all routes.

## Testing

This template has basic JUnit and Integration tests that create instances of the Play Application with an In-Memory database that loads in created Evolutions files. After every @Test execution, the database is purged.

## Performance Metrics

TODO: New Relic Integration or Takipi?
Not available for Play 2.4 just yet. See: (https://discuss.newrelic.com/t/metrics-not-shown-for-play-2-4/26637/29)

## Deployment

This template is designed to use Docker as the deployed container. In order to facilitate deployments
to AWS ElasticBeanstalk, there is an included .ebextensions folder that configures nginx.

1.) From the command line, navigate to the project folder.

2.) Run ./activator clean docker:stage

3.) Run "cp -r .ebextensions target/docker/stage/opt/docker/.ebextensions" to copy the .ebextensions folder contents to the generated target/docker directory.

4.) Run "cp -r conf/newrelic.yml target/docker/stage/opt/docker/lib" to copy the newrelic.yml file contents to the generated target/docker/lib directory.

5.) cd target/docker/stage

6.) "zip -r ../AppName-v.X.X.X.zip ." where X.X.X is the new version number for the application. You can log into AWS Elastic Beanstalk to see the latest version number.

7.) Login into the desired AWS Account and navigate to Elastic Beanstalk. Select the applicable application.

8.) Click on the "Upload and Deploy" button. Click "Choose File" option and select the ZIP file created on Step 5.

9.) Select the "Fixed" deployment limit option and enter in 1 as the option. This will deploy the latest version of the application to one instance at a time to maintain the uptime of the app.

## AWS S3 Feature

This injectable S3Service leverages the Jets3t and AWS SDK libraries to allow developers to upload files along with their metadata to an S3 bucket. This feature can also generate time sensitive URLs to a file contained within a protected S3 bucket. You only need to configure the `aws.s3.baseUrl`, `aws.accessKey` and `aws.secretKey` in the `application.conf`.

## AWS SES Email Feature

An injectable SimpleEmailService can be used to send HTML and Plain Text emails with our without file attachments via AWS's Simple Email Service. You only need to configure the `aws.accessKey` and `aws.secretKey` in the `application.conf` with a valid AWS IAM User that has SES Send Privileges.

## Apple Push Notification Feature

Check out the [Notiflowcate](https://github.com/dayelostraco/Notiflowcate) project for a common Push Notification Framework using Google's [Firebase](https://firebase.google.com/).

## Google Play Push Notifications Feature

Check out the [Notiflowcate](https://github.com/dayelostraco/Notiflowcate) project for a common Push Notification Framework using Google's [Firebase](https://firebase.google.com/).

## Twilio SMS Feature

This feature contains an injectable SmsService that leverages the Twilio REST API to send SMS and MMS messages. You only need to configure the `twilio.sms.phoneNumber`, `twilio.mms.phoneNumber`, `twilio.accountSid` and `twilio.primaryToken` in the `application.conf` with a valid Twilio account.