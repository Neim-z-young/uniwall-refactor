/* posting */
DROP TABLE if EXISTS `wall_category`;
CREATE TABLE `wall_category`(
	`id` int UNSIGNED NOT NULL auto_increment,
	`category` varchar(50) COMMENT"帖子类别",
	`introduction` VARCHAR(200),
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	`state` TINYINT COMMENT"帖子状态：0->审批中；1->可用；2->删除；3->审批失败",
	`create_user_id` BIGINT COMMENT"创建人id",
	PRIMARY KEY(`id`)
	);

DROP TABLE if EXISTS `wall_posting`;
CREATE table `wall_posting`(
	`id` BIGINT UNSIGNED NOT NULL auto_increment,
	`theme` VARCHAR(50) COMMENT"帖子主题",
	`brief_introduction` VARCHAR(50) COMMENT"简易说明，由后端截取",
	`detailed_introduction` VARCHAR(512) COMMENT"详细说明",
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	`user_id` int UNSIGNED COMMENT"发帖者id",
	`state` TINYINT COMMENT"帖子状态：0->审批中；1->可用；2->删除；3->审批失败",
	`category_id` int UNSIGNED,
	PRIMARY key(`id`)
	);

DROP TABLE if EXISTS `wall_remark`;
CREATE TABLE `wall_remark`(
	`id` BIGINT UNSIGNED NOT NULL auto_increment,
	`posting_id` BIGINT UNSIGNED,
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	`state` TINYINT COMMENT"帖子状态：0->审批中；1->可用；2->删除；3->审批失败",
	`content` VARCHAR(200),
	`user_id` int UNSIGNED,
	PRIMARY key(`id`)
	);

DROP TABLE if EXISTS `wall_inner_remark`;
CREATE table `wall_inner_remark`(
	`id` BIGINT(20) UNSIGNED NOT NULL auto_increment,
	`posting_id` BIGINT UNSIGNED COMMENT"帖子id",
	`remark_id` BIGINT UNSIGNED COMMENT"评论id",
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	`state` TINYINT COMMENT"帖子状态：0->审批中；1->可用；2->删除；3->审批失败",
	`content` varchar(200),
	`user_id` int UNSIGNED,
	PRIMARY KEY(`id`)
	);
