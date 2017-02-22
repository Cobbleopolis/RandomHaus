# Filter groups schema

# --- !Ups

CREATE TABLE filterGroups
(
  id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  channelId VARCHAR(256) NOT NULL,
  name VARCHAR(256) NOT NULL,
  CONSTRAINT filterGroup_channels_channelId_fk FOREIGN KEY (channelId) REFERENCES channels (channelId) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE INDEX filterGroup_channels_channelId_fk ON filterGroups (channelId);
CREATE UNIQUE INDEX filterGroup_id_uindex ON filterGroups (id);

# --- !Downs

DROP TABLE filterGroups;