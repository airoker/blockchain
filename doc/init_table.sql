
use block_chain;

CREATE TABLE `t_config` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `key` varchar(50) NOT NULL DEFAULT '' COMMENT '配置key',
  `value` varchar(500) NOT NULL DEFAULT '' COMMENT '配置value',
  `desc` varchar(50) NOT NULL DEFAULT '' COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_key` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='配置数据表';


CREATE TABLE `t_transaction` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `transaction_no` varchar(64) NOT NULL DEFAULT '' COMMENT '交易号',
  `context` varchar(100) NOT NULL DEFAULT '' COMMENT '交易内容',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '交易状态',
  `block_no` varchar(64) NOT NULL DEFAULT '' COMMENT '所属区块号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_transaction` (`transaction_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易表';


CREATE TABLE `t_block` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `block_no` varchar(64) NOT NULL DEFAULT '' COMMENT '区块号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_block` (`block_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='区块表';