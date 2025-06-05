-- MySQL 데이터베이스 및 사용자 설정 스크립트

-- 1. 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS sales CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 2. 사용자 생성 및 권한 부여 (MySQL 8.0 이상)
CREATE USER IF NOT EXISTS 'root'@'localhost' IDENTIFIED BY 'csedbadmin';
GRANT ALL PRIVILEGES ON sales.* TO 'root'@'localhost';

-- 또는 새로운 사용자 생성 (권장)
CREATE USER IF NOT EXISTS 'springuser'@'localhost' IDENTIFIED BY 'springpass';
GRANT ALL PRIVILEGES ON sales.* TO 'springuser'@'localhost';

-- 권한 적용
FLUSH PRIVILEGES;

-- 데이터베이스 선택
USE sales;

-- 테이블이 존재하는지 확인
SHOW TABLES;
