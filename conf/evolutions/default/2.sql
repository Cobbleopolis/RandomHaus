# Channel series schema

# --- !Ups

CREATE TABLE channelSeries
(
  id VARCHAR(256) PRIMARY KEY NOT NULL,
  channelId VARCHAR(256) NOT NULL,
  name VARCHAR(256) NOT NULL,
  publishedAt DATETIME,
  CONSTRAINT channelSeries_fk FOREIGN KEY (channelId) REFERENCES channels (channelId) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE INDEX channelSeries_fk ON channelSeries (channelId);
CREATE UNIQUE INDEX channelSeries_id_uindex ON channelSeries (id);

# --- !Downs

DROP TABLE channelSeries;