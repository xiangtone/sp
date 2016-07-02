/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.6.27 : Database - daily_base
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`daily_base` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `daily_base`;

/*Table structure for table `tbl_group` */

DROP TABLE IF EXISTS `tbl_group`;

CREATE TABLE `tbl_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL COMMENT '组名',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `status` int(1) DEFAULT NULL COMMENT '状态，1为正常，0为锁定，锁定状态组别下的所有用户都不能访问',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

/*Data for the table `tbl_group` */

insert  into `tbl_group`(`id`,`name`,`remark`,`status`) values (2,'系统管理员','系统管理员',NULL),(24,'普通用户','普通用户',NULL);

/*Table structure for table `tbl_group_group` */

DROP TABLE IF EXISTS `tbl_group_group`;

CREATE TABLE `tbl_group_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) DEFAULT NULL COMMENT '组别ID',
  `group_list` varchar(200) DEFAULT NULL COMMENT '组别下能管理的组别,以逗号隔开',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_id` (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tbl_group_group` */

insert  into `tbl_group_group`(`id`,`group_id`,`group_list`,`remark`,`create_date`) values (1,2,'24','123','2016-05-05 11:23:41');

/*Table structure for table `tbl_group_right` */

DROP TABLE IF EXISTS `tbl_group_right`;

CREATE TABLE `tbl_group_right` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL,
  `menu_2_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1200 DEFAULT CHARSET=utf8;

/*Data for the table `tbl_group_right` */

insert  into `tbl_group_right`(`id`,`group_id`,`menu_2_id`) values (921,13,51),(922,13,1),(967,19,51),(968,19,1),(1038,2,12),(1039,2,52),(1040,2,13),(1041,2,51),(1042,2,50),(1043,2,14),(1044,2,15),(1045,2,1),(1198,18,51),(1199,18,1);

/*Table structure for table `tbl_group_user` */

DROP TABLE IF EXISTS `tbl_group_user`;

CREATE TABLE `tbl_group_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=304 DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;

/*Data for the table `tbl_group_user` */

insert  into `tbl_group_user`(`id`,`group_id`,`user_id`) values (291,2,77);

/*Table structure for table `tbl_menu_1` */

DROP TABLE IF EXISTS `tbl_menu_1`;

CREATE TABLE `tbl_menu_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `head_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `sort` int(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Data for the table `tbl_menu_1` */

insert  into `tbl_menu_1`(`id`,`head_id`,`name`,`remark`,`sort`) values (1,1,'缓存管理','缓存相关的都在这里',3),(4,1,'运营配置','运营人员和权限的配置',1),(5,1,'系统配置','系统配置',2);

/*Table structure for table `tbl_menu_2` */

DROP TABLE IF EXISTS `tbl_menu_2`;

CREATE TABLE `tbl_menu_2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_1_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `url` varchar(100) DEFAULT NULL,
  `sort` int(3) NOT NULL DEFAULT '0',
  `action_url` varchar(200) DEFAULT NULL COMMENT '当前URL所用到的action jsp,必须得放行',
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;

/*Data for the table `tbl_menu_2` */

insert  into `tbl_menu_2`(`id`,`menu_1_id`,`name`,`url`,`sort`,`action_url`,`remark`) values (1,1,'缓存刷新','refresh/main.jsp',1,'refresh/action.jsp',NULL),(2,1,'测试页面','wel.htm',2,NULL,NULL),(3,1,'增加DEMO','refresh/demoadd.jsp',3,NULL,NULL),(12,4,'角色管理','user/group.jsp',0,'user/groupadd.jsp,user/groupaction.jsp,user/groupedit.jsp,user/groupright.jsp',NULL),(13,4,'用户管理(Admin)','user/user.jsp',2,'user/action.jsp,user/usergroup.jsp,user/useredit.jsp,user/useradd.jsp,user/usergroup.jsp',NULL),(14,5,'组别管理','menu/menu1.jsp',1,'menu/menu1add.jsp,menu/menu1edit.jsp,menu/action.jsp',NULL),(15,5,'菜单管理','menu/menu2.jsp',2,'menu/menu2add.jsp,menu/menu2edit.jsp,menu/menu2action.jsp',NULL),(16,1,'来个中文的','testurl',5,'aldk.jsp,jfdsfwel.jsp',NULL),(50,5,'模块管理','menu/menu3.jsp',0,'menu/menu3add.jsp,menu/menu3action.jsp,menu/menu3edit.jsp',NULL),(51,4,'用户管理(User)','user/user_priv.jsp',3,'user/user_priv_action.jsp,user/user_priv_add.jsp,user/user_priv_edit.jsp,user/user_priv_group.jsp',NULL),(52,4,'角色授权','ad/group_right.jsp',1,'ad/groupRightEdit.jsp,ad/groupRightAdd.jsp,ad/groupRightAction.jsp',NULL);

/*Table structure for table `tbl_menu_head` */

DROP TABLE IF EXISTS `tbl_menu_head`;

CREATE TABLE `tbl_menu_head` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `sort` int(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `tbl_menu_head` */

insert  into `tbl_menu_head`(`id`,`name`,`remark`,`sort`) values (1,'系统管理',NULL,4),(2,'数据报表',NULL,2),(3,'基本配置',NULL,1),(4,'帐务数据',NULL,3);

/*Table structure for table `tbl_public_url` */

DROP TABLE IF EXISTS `tbl_public_url`;

CREATE TABLE `tbl_public_url` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(50) NOT NULL,
  `remark` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `tbl_public_url` */

insert  into `tbl_public_url`(`id`,`url`,`remark`) values (1,'/login.jsp',NULL),(2,'/head.jsp',NULL),(3,'/left.jsp',NULL),(4,'/index.jsp',NULL),(5,'/loginaction.jsp',NULL),(6,'/righttop.jsp',NULL),(7,'/test.jsp',NULL),(8,'/user/userinfo.jsp',NULL),(9,'/user/action.jsp',NULL),(10,'/ajaction.jsp',NULL);

/*Table structure for table `tbl_user` */

DROP TABLE IF EXISTS `tbl_user`;

CREATE TABLE `tbl_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '用户登录名',
  `pwd` varchar(32) DEFAULT NULL COMMENT 'password用MD5加密',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '显示在系统的昵称',
  `mail` varchar(50) DEFAULT NULL COMMENT 'mail',
  `qq` varchar(20) DEFAULT NULL COMMENT 'qq',
  `phone` varchar(20) DEFAULT NULL COMMENT 'phone',
  `group_list` varchar(200) DEFAULT NULL COMMENT '所属组别，ID对应为tbl_group的主键，用英文逗号隔开，表示同一用户可以从属于多个组别',
  `create_user` int(11) DEFAULT '0' COMMENT '创建者',
  `status` int(1) DEFAULT '1' COMMENT '状态，1为正常，0为锁定，锁定状态下不能登录',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=320 DEFAULT CHARSET=utf8;

/*Data for the table `tbl_user` */

insert  into `tbl_user`(`id`,`name`,`pwd`,`nick_name`,`mail`,`qq`,`phone`,`group_list`,`create_user`,`status`,`create_date`) values (77,'Admin','a66abb5684c45962d887564f08346e8d','Admin','qq.com','','',NULL,77,1,'2016-05-05 11:48:29');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
