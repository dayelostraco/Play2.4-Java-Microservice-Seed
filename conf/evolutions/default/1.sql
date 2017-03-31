# --- !Ups

CREATE TABLE `SampleModel` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  `created` TIMESTAMP NOT NULL,
  `modified` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`));

# --- !Downs

DROP TABLE IF IT EXISTS `SampleModel`;
