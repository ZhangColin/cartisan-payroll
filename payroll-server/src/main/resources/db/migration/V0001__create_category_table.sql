-- 雇员表
DROP TABLE IF EXISTS `pyl_employees`;

CREATE TABLE `pyl_employees` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类Id',
    `name` varchar(50) NOT NULL COMMENT '名称',
    `address` varchar(50) NOT NULL COMMENT '地址',
    `schedule_type` tinyint NOT NULL DEFAULT 0 COMMENT '支付方式',
    `payment_method_type` tinyint NOT NULL DEFAULT 0 COMMENT '支付方式',
    `payment_classification_type` tinyint NOT NULL DEFAULT 0 COMMENT '支付方式',
    `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `index_category_parent_id`(`parent_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='雇员';

-- 雇员表
DROP TABLE IF EXISTS `pyl_direct_deposit_accounts`;

CREATE TABLE `pyl_direct_deposit_accounts` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类Id',
    `employee_id` bigint NOT NULL COMMENT '分类Id',
    `bank` varchar(50) NOT NULL COMMENT '名称',
    `account` varchar(50) NOT NULL COMMENT '地址',
    `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `index_category_parent_id`(`parent_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='雇员表';

-- Email
DROP TABLE IF EXISTS `pyl_paycheck_addresses`;

CREATE TABLE `pyl_paycheck_addresses` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类Id',
    `employee_id` bigint NOT NULL COMMENT '分类Id',
    `email` varchar(50) NOT NULL COMMENT '地址',
    `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `index_category_parent_id`(`parent_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='雇员表';

-- Email
DROP TABLE IF EXISTS `pyl_hourly_classifications`;

CREATE TABLE `pyl_hourly_classifications` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类Id',
    `employee_id` bigint NOT NULL COMMENT '分类Id',
    `hourly_rate` decimal NOT NULL COMMENT '地址',
    `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `index_category_parent_id`(`parent_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='雇员表';

-- Email
DROP TABLE IF EXISTS `pyl_salaried_classifications`;

CREATE TABLE `pyl_salaried_classifications` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类Id',
    `employee_id` bigint NOT NULL COMMENT '分类Id',
    `salary` decimal NOT NULL COMMENT '地址',
    `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `index_category_parent_id`(`parent_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='雇员表';

-- Email
DROP TABLE IF EXISTS `pyl_commissioned_classifications`;

CREATE TABLE `pyl_commissioned_classifications` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类Id',
    `employee_id` bigint NOT NULL COMMENT '分类Id',
    `base_rate` decimal NOT NULL COMMENT '地址',
    `commission_rate` decimal NOT NULL COMMENT '地址',
    `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `index_category_parent_id`(`parent_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='雇员表';



