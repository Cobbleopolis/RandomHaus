# Channel content schema

# --- !Ups

CREATE TABLE channelContent
(
  id VARCHAR(256) NOT NULL,
  channelId VARCHAR(256) NOT NULL,
  seriesId VARCHAR(256) DEFAULT '' NOT NULL,
  CONSTRAINT `PRIMARY` PRIMARY KEY (id, seriesId),
  CONSTRAINT channelID_fk FOREIGN KEY (channelId) REFERENCES channels (channelId) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT channelContent_channelSeries_id_fk FOREIGN KEY (seriesId) REFERENCES channelSeries (id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE INDEX channelContent_channelSeries_id_fk ON channelContent (seriesId);
CREATE INDEX channelID_fk ON channelContent (channelId);

# --- !Downs

DROP TABLE channelContent;