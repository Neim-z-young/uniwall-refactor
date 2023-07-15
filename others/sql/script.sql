INSERT INTO wall_role(`name`, `introduction`)
VALUES( 'default_user', '用户的基础角色');

INSERT INTO wall_role(`name`, `introduction`)
VALUES( 'default_admin', '管理员的基础角色');

INSERT INTO wall_permission(`permission`, `introduction`)
VALUES( 'basic_retrieve', '基础查询权限');

INSERT INTO wall_permission(`permission`, `introduction`)
VALUES( 'basic_update', '基础更新权限');

INSERT INTO wall_permission(`permission`, `introduction`)
VALUES( 'basic_create', '基础创建权限');

INSERT INTO wall_permission(`permission`, `introduction`)
VALUES( 'basic_delete', '基础删除权限');


INSERT INTO wall_role_permission_relation(`permission_id`, `role_id`)
VALUES( 1, 1);

INSERT INTO wall_role_permission_relation(`permission_id`, `role_id`)
VALUES( 1, 2);

INSERT INTO wall_role_permission_relation(`permission_id`, `role_id`)
VALUES( 2, 1);

INSERT INTO wall_role_permission_relation(`permission_id`, `role_id`)
VALUES( 2, 2);

INSERT INTO wall_role_permission_relation(`permission_id`, `role_id`)
VALUES( 2, 3);

INSERT INTO wall_role_permission_relation(`permission_id`, `role_id`)
VALUES( 2, 4);
