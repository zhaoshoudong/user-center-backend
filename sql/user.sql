-- auto-generated definition
create table user
(
    id           varchar(256)                         not null
        primary key,
    username     varchar(256)                         not null comment '用户名称',
    user_account varchar(256) charset latin1          not null comment '登录账号',
    password     varchar(256) charset latin1          not null comment '密码',
    avatar_url   varchar(256) charset latin1          null comment '头像',
    phone        varchar(128) charset latin1          null comment '电话',
    email        varchar(256) charset latin1          null comment '邮箱',
    gender       tinyint(8)                           null comment '性别（0-男，1-女）',
    user_status  tinyint(8)                           null comment '用户状态（）',
    user_role    tinyint(8) default 0                 null comment '权限（0-普通用户，1-管理员）',
    create_time  timestamp  default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  timestamp  default CURRENT_TIMESTAMP null comment '修改时间',
    deleted      tinyint(8) default 0                 not null comment '是否删除（0-否，1-是）'
)
    comment '用户基础信息';

