# chinabook_crawler

### 表格

```sql
create table china_book_info
(
	id int auto_increment
		primary key,
	image_url varchar(125) null comment '书的图片地址',
	book_name varchar(125) null comment '书名',
	detail_url varchar(125) null comment '详细地址',
	author_info varchar(125) null comment '作者信息',
	publish_time varchar(125) null comment '出版时间',
	publisher varchar(125) null comment '出版社',
	sell_price double null comment '出售价格',
	discount double null comment '折扣',
	price_tit double null comment '定价',
	recoLagu text null comment '摘要',
	category1 varchar(20) null comment '类别1',
	category2 varchar(20) null comment '类别2',
	insert_time datetime null,
	update_time datetime null,
	origin_url varchar(128) null comment '原始链接'
);

create table role
(
	id int auto_increment
		primary key,
	role_name varchar(20) null comment '角色名称',
	create_time datetime null comment '创建时间',
	update_time datetime null comment '更新时间'
);

create table spider_list
(
	id int auto_increment
		primary key,
	spider_name varchar(128) null comment '爬虫名称',
	spider_status varchar(20) null comment '爬虫状态',
	insert_time datetime null,
	create_time datetime null,
	spider_description varchar(128) null comment '爬虫描述'
);

create table spider_monitor
(
	id int auto_increment
		primary key,
	spider_name varchar(128) null comment '爬虫名称',
	spider_init_count int null,
	spider_ing_count int null,
	spider_stop_count int null,
	spider_status varchar(128) null,
	insert_time datetime null,
	create_time datetime null
);

create table user
(
	id int auto_increment
		primary key,
	username varchar(125) null comment '用户名',
	realname varchar(125) null comment '真实姓名',
	sex varchar(20) null comment '性别',
	age varchar(20) null comment '年龄',
	email varchar(125) null comment '邮箱',
	phone varchar(125) null comment '手机号',
	create_time datetime null comment '创建时间',
	update_time datetime null comment '更改时间',
	password varchar(125) null comment '密码',
	state varchar(125) null comment '状态'
);

create table user_role_relation
(
	id int auto_increment
		primary key,
	userid int null comment '用户id',
	roleid int null comment '角色id'
);
```



