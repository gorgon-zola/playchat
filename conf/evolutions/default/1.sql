# Board schema
 
# --- !Ups
 
CREATE TABLE Board (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    likes char(1) NOT NULL,
    comment varchar(280),
    reg_datetime datetime,
    PRIMARY KEY (id)
);

INSERT INTO Board (name, likes, comment, reg_datetime) VALUES ('이현수', 'Y', '플레이 신기해요', now());
INSERT INTO Board (name, likes, comment, reg_datetime) VALUES ('홍길동', 'N', '너무 너무 어려우네요 ㅠㅠ', now());
INSERT INTO Board (name, likes, comment, reg_datetime) VALUES ('초난강', 'Y', '괜찮아요~', now());

# --- !Downs
 
DROP TABLE Board;