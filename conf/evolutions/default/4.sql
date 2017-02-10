# Content tags schema

# --- !Ups

CREATE TABLE contentTags
(
  id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  contentId VARCHAR(256) NOT NULL,
  tag TEXT NOT NULL,
  CONSTRAINT contentTags_channelContent_id_fk FOREIGN KEY (contentId) REFERENCES channelContent (id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE INDEX contentTags_fk ON contentTags (contentId);
CREATE UNIQUE INDEX contentTags_id_uindex ON contentTags (id);

# --- !Downs

DROP TABLE contentTags;