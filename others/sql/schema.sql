/* user-srv */
DROP TABLE if EXISTS `wall_user`;
CREATE TABLE `wall_user`(
	`id` int UNSIGNED NOT NULL auto_increment,
	`password` VARCHAR(255) NOT NULL COMMENT"加密后的密码",
	`email` VARCHAR(255),
	`nickname` VARCHAR(50) COMMENT"昵称, 可与他人重复",
	`phone_number` VARCHAR(50) COMMENT"电话号码",
	`last_login` datetime comment '上次登录时间',
    `gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	`status` TINYINT COMMENT"帐号启用状态：0->禁用；1->启用",
	`online` TINYINT COMMENT"是否在线",
	PRIMARY KEY(`id`),
	UNIQUE(`email`)
)auto_increment=100000;

DROP TABLE if EXISTS `wall_user_information`;
CREATE TABLE `wall_user_information`(
	`user_id` int UNSIGNED,
	`gender` TINYINT COMMENT"性别:0->未知",
	`growth` int COMMENT"成长值",
	`credit` int COMMENT"积分",
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	PRIMARY KEY(`user_id`)
);

DROP TABLE if EXISTS `wall_platform_user`;
CREATE TABLE `wall_platform_user`(
	`open_id` VARCHAR(255) COMMENT"第三方平台的id",
	`platform_type` VARCHAR(50) COMMENT"平台类型",
	`user_id` int UNSIGNED COMMENT"关联的用户id",
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	`gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	PRIMARY KEY(`open_id`, `platform_type`)
);

DROP TABLE if EXISTS `wall_admin`;
CREATE TABLE `wall_admin`(
	`id` int UNSIGNED NOT NULL auto_increment,
	`password` VARCHAR(255) NOT NULL COMMENT"加密后的密码",
	`email` VARCHAR(255),
	`nickname` VARCHAR(50) COMMENT"昵称, 可与他人重复",
	`phone_number` VARCHAR(50) COMMENT"电话号码",
	`last_login` datetime comment '上次登录时间',
    `gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	`status` TINYINT COMMENT"帐号启用状态：0->禁用；1->启用",
	`online` TINYINT COMMENT"是否在线",
	PRIMARY KEY(`id`),
	UNIQUE(`email`)
)auto_increment=10000;

DROP TABLE if EXISTS `wall_permission`;
CREATE TABLE `wall_permission`(
	`id` int NOT NULL auto_increment,
	`permission` VARCHAR(50) COMMENT"用户权限",
	`introduction` VARCHAR(200),
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	PRIMARY KEY(`id`)
);

DROP TABLE if EXISTS `wall_role`;
CREATE TABLE `wall_role`(
	`id` int NOT NULL auto_increment,
	`name` VARCHAR(50) COMMENT"角色",
	`introduction` VARCHAR(200),
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	`gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	PRIMARY KEY(`id`)
);

DROP TABLE if EXISTS `wall_role_permission_relation`;
CREATE TABLE `wall_role_permission_relation`(
	`id` int NOT NULL auto_increment,
	`permission_id` int,
	`role_id` int,
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	PRIMARY KEY(`id`)
);

DROP TABLE if EXISTS `wall_admin_role_relation`;
CREATE TABLE `wall_admin_role_relation`(
    `id` int UNSIGNED not NULL auto_increment,
    `admin_id` int UNSIGNED,
    `role_id` int,
    `gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    PRIMARY KEY(`id`),
	INDEX(`admin_id`)
);

DROP TABLE if EXISTS `wall_user_role_relation`;
CREATE TABLE `wall_user_role_relation`(
    `id` int UNSIGNED not NULL auto_increment,
    `user_id` int UNSIGNED,
    `role_id` int,
    `gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    PRIMARY KEY(`id`),
	INDEX(`user_id`)
);

/*  */

DROP TABLE if EXISTS `wall_user_login_log`;
CREATE TABLE `wall_user_login_log`(
	`id` BIGINT UNSIGNED not NULL auto_increment,
	`user_id` BIGINT UNSIGNED,
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	`ip_address` BIGINT COMMENT"通过INET_ATON()/INET_NTOA()转换",
	PRIMARY KEY(`id`)
);

DROP TABLE if EXISTS `wall_admin_login_log`;
CREATE TABLE `wall_admin_login_log`(
	`id` int not NULL auto_increment,
	`admin_id` int,
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	`ip_address` BIGINT COMMENT"通过INET_ATON()/INET_NTOA()转换",
	PRIMARY KEY(`id`)
);

DROP TABLE if EXISTS `posting`;
CREATE table `posting`(
	`id` BIGINT not NULL auto_increment,
	`theme` VARCHAR(50) COMMENT"帖子主题",
	`brief_introduction` VARCHAR(50) COMMENT"简易说明，由后端截取",
	`detailed_introduction` VARCHAR(500) COMMENT"详细说明",
	`picture_introduction` VARCHAR(1000) COMMENT"图片说明",
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	`user_id` BIGINT COMMENT"发帖者id",
	`approval_id` BIGINT COMMENT"审批单id",
	`state` TINYINT(1) COMMENT"帖子状态：0->审批中；1->可用；2->删除；3->审批失败",
	`approval_introduction` VARCHAR(500) COMMENT"审批说明",
	`category_id` int,
	PRIMARY key(`id`)
	);

DROP TABLE if EXISTS `posting_approval`;
CREATE table `posting`(
	`id` BIGINT not NULL auto_increment,
	`approval_introduction` VARCHAR(500) COMMENT"详细说明",
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	`user_id` BIGINT COMMENT"审批者id",
	`type` TINYINT COMMENT"审批方式：0->自动审批；1->手动审批"
	`state` TINYINT COMMENT"审批单状态：0->待审批；1->通过；2->拒绝",
	PRIMARY key(`id`)
	);

DROP TABLE if EXISTS `posting_statistic`;
CREATE table `posting_statistic`(
	`posting_id` BIGINT,
	`click_times` BIGINT COMMENT"点击次数",
    `gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	`thumb_up` int,
	`user_id` int,
	`category_id` int,
	PRIMARY KEY(`posting_id`)
	);

DROP TABLE if EXISTS `remark`;
CREATE TABLE `remark`(
	`id` BIGINT not NULL auto_increment,
	`posting_id` BIGINT,
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	`content` VARCHAR(200),
	`user_id` BIGINT,
	`thumb_up` int,
	PRIMARY key(`id`)
	);
DROP TABLE if EXISTS `inner_remark`;
CREATE table `inside_floor`(
	`id` BIGINT not NULL auto_increment,
	`posting_id` BIGINT COMMENT"帖子id",
	`remark_id` BIGINT COMMENT"评论id",
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	`content` varchar(200),
	`user_id` BIGINT,
	`thumb_up` int,
	PRIMARY KEY(`id`)
	);

DROP TABLE if EXISTS `wall_user_message`;
CREATE TABLE `wall_user_message`(
	`id` BIGINT not NULL auto_increment,
	`user_id` BIGINT,
	`message` varchar(100) COMMENT"key:value(posting:int;remark:int;inner_remark:int)",
	PRIMARY KEY(`id`)
	);

DROP TABLE if EXISTS `wall_user_statistic`;
CREATE TABLE `wall_user_statistic`(
	`user_id` BIGINT,
	`posting_num` int COMMENT"发帖数",
	`unread_messages` int COMMENT"未读消息",
	PRIMARY KEY(`user_id`)
	);

DROP TABLE if EXISTS `category`;
CREATE TABLE `category`(
	`id` int not NULL auto_increment,
	`category` varchar(50) COMMENT"帖子类别",
	`introduction` VARCHAR(200),
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	`top_postings` varchar(640) COMMENT"置顶帖",
	`create_user_id` BIGINT COMMENT"创建人id",
	PRIMARY KEY(`id`)
	);
