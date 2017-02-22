# Content tags schema

# --- !Ups

CREATE TABLE contentTags
(
  contentId VARCHAR(256) NOT NULL,
  tag TEXT NOT NULL,
  CONSTRAINT contentTags_channelContent_id_fk FOREIGN KEY (contentId) REFERENCES channelContent (id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE INDEX contentTags_fk ON contentTags (contentId);

# --- !Downs

DROP TABLE contentTags;