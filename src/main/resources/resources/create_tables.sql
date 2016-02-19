DROP SEQUENCE IF EXISTS auto_post_id CASCADE;
DROP SEQUENCE IF EXISTS auto_id_users CASCADE;

DROP TABLE IF EXISTS posts CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE SEQUENCE auto_id_users;
CREATE SEQUENCE auto_post_id;

CREATE TABLE posts
(post_id INT UNIQUE PRIMARY KEY DEFAULT nextval('auto_post_id'),
 post_parent_id INT,
 post_title VARCHAR(255),
 post_content VARCHAR(255),
 post_likes INT DEFAULT 0,
 post_dislikes INT DEFAULT 0,
 author_id INT,
 creation_date TIMESTAMP NOT NULL default now());

CREATE TABLE users
(user_id INT UNIQUE PRIMARY KEY DEFAULT nextval('auto_id_users'),
 group_id INT,
 user_name VARCHAR(16),
 user_password VARCHAR(16));