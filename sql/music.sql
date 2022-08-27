/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.7.36 : Database - my_music
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`my_music` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `my_music`;

/*Table structure for table `album` */

DROP TABLE IF EXISTS `album`;

CREATE TABLE `album` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '名字',
  `img` varchar(2048) DEFAULT NULL COMMENT '图片',
  `time` datetime DEFAULT NULL COMMENT '增加时间',
  `num` int(11) DEFAULT '0' COMMENT '播放次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2107 DEFAULT CHARSET=utf8;

/*Table structure for table `album_map` */

DROP TABLE IF EXISTS `album_map`;

CREATE TABLE `album_map` (
  `album_id` bigint(20) NOT NULL COMMENT '专辑ID',
  `song_id` bigint(20) NOT NULL COMMENT '歌曲ID',
  PRIMARY KEY (`album_id`,`song_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `song` */

DROP TABLE IF EXISTS `song`;

CREATE TABLE `song` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mid` varchar(32) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `singer` varchar(255) DEFAULT NULL,
  `album` varchar(255) DEFAULT NULL,
  `cover` varchar(255) DEFAULT NULL,
  `path` varchar(2048) DEFAULT NULL,
  `num` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  FULLTEXT KEY `mid` (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=5156 DEFAULT CHARSET=utf8;

/*Table structure for table `song_list` */

DROP TABLE IF EXISTS `song_list`;

CREATE TABLE `song_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `time` datetime NOT NULL,
  `num` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=utf8;

/*Table structure for table `song_list_map` */

DROP TABLE IF EXISTS `song_list_map`;

CREATE TABLE `song_list_map` (
  `song_list_id` bigint(20) NOT NULL,
  `song_id` bigint(20) NOT NULL,
  PRIMARY KEY (`song_list_id`,`song_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
