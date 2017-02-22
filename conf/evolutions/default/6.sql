# Channel links schema

# --- !Ups

CREATE TABLE channelLinks
(
  id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  channelId VARCHAR(256) NOT NULL,
  label VARCHAR(256) NOT NULL,
  link TEXT NOT NULL,
  CONSTRAINT channelLinks_channels_channelID_fk FOREIGN KEY (channelId) REFERENCES channels (channelId) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE INDEX channelLinks_channels_channelID_fk ON channelLinks (channelId);
CREATE UNIQUE INDEX channelLinks_id_uindex ON channelLinks (id);

# --- !Downs

DROP TABLE channelLinks;