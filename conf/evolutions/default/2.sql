# Channel series schema

# --- !Ups

CREATE TABLE channelSeries
(
  id          VARCHAR(256) NOT NULL PRIMARY KEY,
  channelId   VARCHAR(256) NOT NULL,
  name        VARCHAR(256) NOT NULL,
  publishedAt DATETIME     NULL,
  CONSTRAINT channelSources_id_uindex UNIQUE (id),
  CONSTRAINT channelSources_fk FOREIGN KEY (channelId) REFERENCES randomHaus.channels (channelId) ON UPDATE CASCADE ON DELETE CASCADE
);

# --- !Downs

DROP TABLE channelSeries;