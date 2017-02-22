# Channel content schema

# --- !Ups

CREATE TABLE channelContent
(
  id VARCHAR(256) PRIMARY KEY NOT NULL,
  channelId VARCHAR(256) NOT NULL,
  CONSTRAINT channelID_fk FOREIGN KEY (channelId) REFERENCES channels (channelId) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE INDEX channelID_fk ON channelContent (channelId);
CREATE UNIQUE INDEX channelContent_id_uindex ON channelContent (id);

# --- !Downs

DROP TABLE channelContent;