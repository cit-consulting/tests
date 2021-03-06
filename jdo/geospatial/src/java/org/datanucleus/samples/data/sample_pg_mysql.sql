-- DROP TABLE `SAMPLEPGPOINT`;
-- CREATE TABLE `SAMPLEPGPOINT` (
--   `SAMPLEPGPOINT_ID` bigint(20) NOT NULL,
--   `GEOM` point default NULL,
--   `ID` bigint(20) NOT NULL,
--   `NAME` varchar(256) character set latin1 collate latin1_bin default NULL,
--   PRIMARY KEY  (`SAMPLEPGPOINT_ID`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
INSERT INTO `SAMPLEPGPOINT` (`SAMPLEPGPOINT_ID`, `ID`, `NAME`, `GEOM`) VALUES (1, 1000, 'Point 0', NULL);
INSERT INTO `SAMPLEPGPOINT` (`SAMPLEPGPOINT_ID`, `ID`, `NAME`, `GEOM`) VALUES (2, 1001, 'Point 1', GeomFromText('POINT(10 10)',4326));
INSERT INTO `SAMPLEPGPOINT` (`SAMPLEPGPOINT_ID`, `ID`, `NAME`, `GEOM`) VALUES (3, 1002, 'Point 2', GeomFromText('POINT(75 75)',4326));
--
-- DROP TABLE `SAMPLEPGLINESTRING`;
-- CREATE TABLE `SAMPLEPGLINESTRING` (
--   `SAMPLEPGLINESTRING_ID` bigint(20) NOT NULL,
--   `GEOM` linestring default NULL,
--   `ID` bigint(20) NOT NULL,
--   `NAME` varchar(256) character set latin1 collate latin1_bin default NULL,
--   PRIMARY KEY  (`SAMPLEPGLINESTRING_ID`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
INSERT INTO `SAMPLEPGLINESTRING` (`SAMPLEPGLINESTRING_ID`, `ID`, `NAME`, `GEOM`) VALUES (1, 2000, 'LineString 0', NULL);
INSERT INTO `SAMPLEPGLINESTRING` (`SAMPLEPGLINESTRING_ID`, `ID`, `NAME`, `GEOM`) VALUES (2, 2001, 'LineString 1', GeomFromText('LINESTRING(0 50,100 50)',4326));
INSERT INTO `SAMPLEPGLINESTRING` (`SAMPLEPGLINESTRING_ID`, `ID`, `NAME`, `GEOM`) VALUES (3, 2002, 'LineString 2', GeomFromText('LINESTRING(50 0,50 100)',4326));
INSERT INTO `SAMPLEPGLINESTRING` (`SAMPLEPGLINESTRING_ID`, `ID`, `NAME`, `GEOM`) VALUES (4, 2003, 'LineString 3', GeomFromText('LINESTRING(100 25,120 25,110 10,110 45)',4326));
--
-- DROP TABLE `SAMPLEPGPOLYGON`;
-- CREATE TABLE `SAMPLEPGPOLYGON` (
--   `SAMPLEPGPOLYGON_ID` bigint(20) NOT NULL,
--   `GEOM` polygon default NULL,
--   `ID` bigint(20) NOT NULL,
--   `NAME` varchar(256) character set latin1 collate latin1_bin default NULL,
--   PRIMARY KEY  (`SAMPLEPGPOLYGON_ID`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
INSERT INTO `SAMPLEPGPOLYGON` (`SAMPLEPGPOLYGON_ID`, `ID`, `NAME`, `GEOM`) VALUES (1, 3000, 'Polygon 0', NULL);
INSERT INTO `SAMPLEPGPOLYGON` (`SAMPLEPGPOLYGON_ID`, `ID`, `NAME`, `GEOM`) VALUES (2, 3001, 'Polygon 1', GeomFromText('POLYGON((25 25,75 25,75 75,25 75,25 25),(45 45,55 45,55 55,45 55,45 45))',4326));
INSERT INTO `SAMPLEPGPOLYGON` (`SAMPLEPGPOLYGON_ID`, `ID`, `NAME`, `GEOM`) VALUES (3, 3002, 'Polygon 2', GeomFromText('POLYGON((75 75,100 75,100 100,75 75))',4326));
--
-- DROP TABLE `SAMPLEPGGEOMETRYCOLLECTION`;
-- CREATE TABLE `SAMPLEPGGEOMETRYCOLLECTION` (
--   `SAMPLEPGGEOMETRYCOLLECTION_ID` bigint(20) NOT NULL,
--   `GEOM` geometrycollection default NULL,
--   `ID` bigint(20) NOT NULL,
--   `NAME` varchar(256) character set latin1 collate latin1_bin default NULL,
--   PRIMARY KEY  (`SAMPLEPGGEOMETRYCOLLECTION_ID`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
INSERT INTO `SAMPLEPGGEOMETRYCOLLECTION` (`SAMPLEPGGEOMETRYCOLLECTION_ID`, `ID`, `NAME`, `GEOM`) VALUES (1, 7000, 'Collection 0', NULL);
INSERT INTO `SAMPLEPGGEOMETRYCOLLECTION` (`SAMPLEPGGEOMETRYCOLLECTION_ID`, `ID`, `NAME`, `GEOM`) VALUES (2, 7001, 'Collection 1', GeomFromText('GEOMETRYCOLLECTION(POINT(10 10),LINESTRING(0 50, 100 50),POLYGON((25 25,75 25,75 75,25 75,25 25),(45 45,55 45,55 55,45 55,45 45)))',4326));
INSERT INTO `SAMPLEPGGEOMETRYCOLLECTION` (`SAMPLEPGGEOMETRYCOLLECTION_ID`, `ID`, `NAME`, `GEOM`) VALUES (3, 7002, 'Collection 2', GeomFromText('GEOMETRYCOLLECTION(POINT(75 75),LINESTRING(50 0,50 100),POLYGON((75 75,100 75,100 100,75 75)))',4326));
INSERT INTO `SAMPLEPGGEOMETRYCOLLECTION` (`SAMPLEPGGEOMETRYCOLLECTION_ID`, `ID`, `NAME`, `GEOM`) VALUES (4, 7003, 'Collection 3', GeomFromText('GEOMETRYCOLLECTION(LINESTRING(100 25,120 25,110 10,110 45))',4326));