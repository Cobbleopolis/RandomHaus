#Channels schema

# --- !Ups

CREATE TABLE channels
(
  channelId VARCHAR(256) PRIMARY KEY NOT NULL,
  name TINYTEXT NOT NULL,
  backgroundCss TEXT,
  currentlyUpdating TINYTEXT
);
CREATE UNIQUE INDEX channels_channelID_uindex ON channels (channelId);

# --- !Downs

DROP TABLE channels;

