# Content series link schema

# --- !Ups

CREATE TABLE content_series
(
  contentId VARCHAR(256) NOT NULL,
  seriesId VARCHAR(256) NOT NULL,
  CONSTRAINT `PRIMARY` PRIMARY KEY (contentId, seriesId),
  CONSTRAINT content_series_channelContent_id_fk FOREIGN KEY (contentId) REFERENCES channelContent (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT content_series_channelSeries_id_fk FOREIGN KEY (seriesId) REFERENCES channelSeries (id) ON DELETE CASCADE ON UPDATE CASCADE
);

# --- !Downs

DROP TABLE content_series;