# Channel content schema

# --- !Ups

CREATE TABLE channelContent
(
  id        VARCHAR(256) NOT NULL PRIMARY KEY,
  channelId VARCHAR(256) NOT NULL,
  CONSTRAINT channelContent_id_uindex UNIQUE (id),
  CONSTRAINT channelID_fk FOREIGN KEY (channelId) REFERENCES randomHaus.channels (channelId) ON UPDATE CASCADE ON DELETE CASCADE
);

# --- !Downs

DROP TABLE channelContent;