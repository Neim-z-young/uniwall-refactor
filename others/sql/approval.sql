DROP TABLE if EXISTS `approval_bill`;
CREATE table `approval_bill`(
	`id` BIGINT not NULL auto_increment,
	`approval_introduction` VARCHAR(500) COMMENT"详细说明",
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	`user_id` BIGINT COMMENT"审批者id",
	`user_type` TINYINT COMMENT"审批者类型",
	`type` TINYINT COMMENT"审批方式：0->自动审批；1->手动审批"
	`state` TINYINT COMMENT"审批单状态：0->待审批；1->通过；2->拒绝",
	PRIMARY key(`id`)
	);

DROP TABLE if EXISTS `approval_business_relation`;
CREATE table `approval_business_relation`(
	`id` BIGINT not NULL auto_increment,
	`gmt_create`   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `gmt_modified` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
	`business_id` VARCHAR(128) COMMENT"业务id",
	`type` VARCHAR(32) COMMENT"业务类型"
	PRIMARY key(`id`)
	);