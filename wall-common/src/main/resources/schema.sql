
DROP TABLE if EXISTS `wall_user`;
CREATE TABLE `wall_user`(
	`id` BIGINT not NULL auto_increment,
	`username` VARCHAR(50) NOT NULL COMMENT"用户唯一名称",
	`password` VARCHAR(256) COMMENT"加密后的密码",
	`email` VARCHAR(256),
	`open_id` VARCHAR(30) COMMENT"微信openid",
	`nick_name` VARCHAR(50) COMMENT"昵称, 可与他人重复",
	`last_login` datetime comment '上次登录时间',
    `gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	`status` TINYINT(1) COMMENT"帐号启用状态：0->禁用；1->启用",
	`is_online` TINYINT(1) COMMENT"是否在线",
	`gender` int(1) COMMENT"0->未知,1->男,2->女",
	`growth` int COMMENT"成长值",
	`points` int COMMENT"用户积分",
	PRIMARY KEY(`id`),
	unique(`open_id`),
	UNIQUE(`username`),
	unique(`email`)
)auto_increment=100000;

DROP TABLE if EXISTS `wall_user_information`;
CREATE TABLE `wall_user_information`(
	`user_id` BIGINT,
	`role_id` int,
	`university` VARCHAR(100),
	`school_number` VARCHAR(64),
	`is_real_name_registered` TINYINT(1) COMMENT"是否实名",
	`id_card` VARCHAR(64),
	`first_name` VARCHAR(30),
	`last_name` VARCHAR(150),
	`phone_number` VARCHAR(64),
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	PRIMARY KEY(`user_id`),
	UNIQUE(`school_number`)
);

DROP TABLE if EXISTS `wall_admin`;
CREATE TABLE `wall_admin`(
	`id` BIGINT not NULL auto_increment,
	`username` VARCHAR(50),
	`password` VARCHAR(256) COMMENT"加密后的密码",
	`email` VARCHAR(256),
	`nick_name` VARCHAR(50),
	`note` VARCHAR(200) COMMENT"备注信息",
	`last_login` datetime,
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	`state` TINYINT(1) COMMENT"帐号启用状态：0->禁用；1->启用",
	`is_online` TINYINT(1) COMMENT"是否在线",
	PRIMARY KEY(`id`),
	UNIQUE(`username`),
	unique(`email`)
)auto_increment=10000;

DROP TABLE if EXISTS `wall_user_login_log`;
CREATE TABLE `wall_user_login_log`(
	`id` BIGINT not NULL auto_increment,
	`user_id` BIGINT,
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

DROP TABLE if EXISTS `permission`;
CREATE TABLE `permission`(
	`id` int not NULL auto_increment,
	`peimission` varchar(50) COMMENT"用户权限",
	`introduction` varchar(200),
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	PRIMARY KEY(`id`)
	);

DROP TABLE if EXISTS `role`;
CREATE TABLE `role`(
	`id` int not NULL auto_increment,
	`name` varchar(50) COMMENT"角色",
	`introduction` varchar(200),
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	`gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	PRIMARY KEY(`id`)
	);

DROP TABLE if EXISTS `role_permission_relation`;
CREATE TABLE `role_permission_relation`(
	`id` int not NULL auto_increment,
	`permission_id` int,
	`role_id` int,
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	PRIMARY KEY(`id`)
	);

DROP TABLE if EXISTS `admin_role_relation`;
CREATE TABLE `admin_role_relation`(
    `id` int not NULL auto_increment,
    `admin_id` int,
    `role_id` int,
    `gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    PRIMARY KEY(`id`)
    );

DROP TABLE if EXISTS `user_role_relation`;
CREATE TABLE `user_role_relation`(
    `id` int not NULL auto_increment,
    `user_id` BIGINT,
    `role_id` int,
    `gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    PRIMARY KEY(`id`)
    );