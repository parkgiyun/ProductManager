-- MySQL 관리자 권한으로 실행

-- 1. 기존 사용자 확인
SELECT User, Host FROM mysql.user WHERE User = 'root';

-- 2. root 사용자 비밀번호 재설정
ALTER USER 'root'@'localhost' IDENTIFIED BY 'csedbadmin';

-- 3. 또는 새로운 사용자 생성 (권장)
DROP USER IF EXISTS 'springuser'@'localhost';
CREATE USER 'springuser'@'localhost' IDENTIFIED BY 'springpass';

-- 4. 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS sales CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 5. 권한 부여
GRANT ALL PRIVILEGES ON sales.* TO 'springuser'@'localhost';
FLUSH PRIVILEGES;

-- 6. 연결 테스트
SELECT 'MySQL 설정 완료' AS status;
