
CREATE DATABASE IF NOT EXISTS splitmate_db;

USE splitmate_db;

CREATE TABLE IF NOT EXISTS `groups` (
    group_id INT PRIMARY KEY AUTO_INCREMENT,
    group_name VARCHAR(100) NOT NULL,
    total_budget DECIMAL(12,2) NOT NULL
);
