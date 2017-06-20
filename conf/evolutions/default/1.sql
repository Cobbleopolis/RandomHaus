#Channels schema

# --- !Ups

CREATE TABLE channels
(
  channelId VARCHAR(256) PRIMARY KEY NOT NULL,
  name TINYTEXT NOT NULL,
  backgroundCss TEXT,
  currentlyUpdating TINYINT(1) DEFAULT '0',
  lastUpdated DATETIME DEFAULT '1000-01-01 00:00:00'
);
CREATE UNIQUE INDEX channels_channelID_uindex ON channels (channelId);

# --- !Downs

DROP TABLE channels;

