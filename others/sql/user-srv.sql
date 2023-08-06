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
	PRIMARY KEY(`id`),
	UNIQUE(`permission`)
);

DROP TABLE if EXISTS `wall_role`;
CREATE TABLE `wall_role`(
	`id` int NOT NULL auto_increment,
	`name` VARCHAR(50) COMMENT"角色",
	`introduction` VARCHAR(200),
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	`gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	PRIMARY KEY(`id`),
	UNIQUE(`name`)
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