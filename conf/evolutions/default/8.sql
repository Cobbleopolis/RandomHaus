# Filters schema

# --- !Ups

CREATE TABLE filters
(
  id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  filterGroupId INT(11) NOT NULL,
  name TEXT NOT NULL,
  tagName TEXT NOT NULL,
  CONSTRAINT filters_filterGroups_id_fk FOREIGN KEY (filterGroupId) REFERENCES filterGroups (id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE UNIQUE INDEX filters_id_uindex ON filters (id);

# --- !Downs

DROP TABLE filters;