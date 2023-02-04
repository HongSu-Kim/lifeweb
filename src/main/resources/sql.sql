
DROP TABLE IF EXISTS review;

DROP TABLE IF EXISTS application_answer;
DROP TABLE IF EXISTS application;

DROP TABLE IF EXISTS campaign_image;
DROP TABLE IF EXISTS application_question;
DROP TABLE IF EXISTS campaign_local;
DROP TABLE IF EXISTS campaign;
DROP TABLE IF EXISTS campaign_type;
DROP TABLE IF EXISTS campaign_category;
DROP TABLE IF EXISTS local;

DROP TABLE IF EXISTS member_sns;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS sns;


CREATE TABLE sns (
    sns_id  BIGINT      NOT NULL AUTO_INCREMENT,
    name    VARCHAR(30)	NOT NULL,
    CONSTRAINT PK_SNS PRIMARY KEY (sns_id)
);

CREATE TABLE member (
    member_id       BIGINT	        NOT NULL AUTO_INCREMENT,
    role            VARCHAR(10)     NOT NULL,
    email           VARCHAR(30)	    NOT NULL,
    pwd	            VARCHAR(60)	    NOT NULL,
    file_name       VARCHAR(100)	NULL,
    name            VARCHAR(30)	    NULL,
    nickname        VARCHAR(30)	    NOT NULL,
    gender          VARCHAR(10)     NULL,
    birth           DATE	        NULL,
    tel             CHAR(11)	    NULL,
    postcode        CHAR(5)	        NULL,
    address         VARCHAR(100)    NULL,
    detail_address  VARCHAR(100)    NULL,
    extra_address   VARCHAR(100)    NULL,
    point           INT	            DEFAULT 0,
    CONSTRAINT PK_MEMBER PRIMARY KEY (member_id)
);

CREATE TABLE member_sns (
    member_sns_id   BIGINT	        NOT NULL AUTO_INCREMENT,
    member_id	    BIGINT	        NOT NULL,
    sns_id	        BIGINT	        NOT NULL,
    sns_url         VARCHAR(255)    NOT NULL,
    CONSTRAINT PK_MEMBER_SNS PRIMARY KEY (member_sns_id),
    CONSTRAINT FK_MEMBER_SNS_MEMBER FOREIGN KEY (member_id) REFERENCES member (member_id),
    CONSTRAINT FK_MEMBER_SNS_SNS FOREIGN KEY (sns_id) REFERENCES sns (sns_id)
);

CREATE TABLE local (
    local_id	BIGINT	    NOT NULL AUTO_INCREMENT,
    name	    VARCHAR(30)	NOT NULL,
    CONSTRAINT PK_LOCAL PRIMARY KEY (local_id)
);

CREATE TABLE campaign_category (
    campaign_category_id	BIGINT	    NOT NULL AUTO_INCREMENT,
    name	                VARCHAR(30)	NOT NULL,
    CONSTRAINT PK_CAMPAIGN_CATEGORY PRIMARY KEY (campaign_category_id)
);

CREATE TABLE campaign_type (
    campaign_type_id	BIGINT	    NOT NULL AUTO_INCREMENT,
    name	            VARCHAR(30)	NOT NULL,
    CONSTRAINT PK_CAMPAIGN_TYPE PRIMARY KEY (campaign_type_id)
);

CREATE TABLE campaign (
    campaign_id	            BIGINT	        NOT NULL AUTO_INCREMENT,
    campaign_category_id	BIGINT	        NOT NULL,
    campaign_type_id	    BIGINT	        NOT NULL,
    sns_id	                BIGINT	        NOT NULL,
    special	                BOOLEAN	        DEFAULT FALSE,
    title	                VARCHAR(255)	NOT NULL,
    file_name	            VARCHAR(100)	NOT NULL,
    provision	            VARCHAR(500)	NOT NULL,
    created	                DATETIME	    DEFAULT NOW(),
    review_notice	        VARCHAR(6000)	NOT NULL,
    guideline	            VARCHAR(6000)	NOT NULL,
    keywords	            VARCHAR(255)	NOT NULL,
    application_start_date	DATE	        NOT NULL,
    application_end_date	DATE	        NOT NULL,
    filing_start_date	    DATE	        NOT NULL,
    filing_end_date	        DATE	        NOT NULL,
    headcount	            INT	            NOT NULL,
    status                  VARCHAR(10)	    DEFAULT 'STAND',
    CONSTRAINT PK_CAMPAIGN PRIMARY KEY (campaign_id),
    CONSTRAINT FK_CAMPAIGN_CAMPAIGN_CATEGORY FOREIGN KEY (campaign_category_id) REFERENCES campaign_category (campaign_category_id),
    CONSTRAINT FK_CAMPAIGN_CAMPAIGN_TYPE FOREIGN KEY (campaign_type_id) REFERENCES campaign_type (campaign_type_id),
    CONSTRAINT FK_CAMPAIGN_SNS FOREIGN KEY (sns_id) REFERENCES sns (sns_id)
);

CREATE TABLE campaign_local (
    campaign_id	        BIGINT	        NOT NULL,
    local_id	        BIGINT	        NOT NULL,
    address         	VARCHAR(255)	NOT NULL,
    latitude	        VARCHAR(30)	    NOT NULL,
    longitude	        VARCHAR(30)	    NOT NULL,
    visit_notice	    VARCHAR(1000)	NOT NULL,
    CONSTRAINT PK_CAMPAIGN_LOCAL PRIMARY KEY (campaign_id),
    CONSTRAINT FK_CAMPAIGN_LOCAL_CAMPAIGN FOREIGN KEY (campaign_id) REFERENCES campaign (campaign_id),
    CONSTRAINT FK_CAMPAIGN_LOCAL_LOCAL FOREIGN KEY (local_id) REFERENCES local (local_id)
);

CREATE TABLE application_question (
    application_question_id	BIGINT	        NOT NULL AUTO_INCREMENT,
    campaign_id	            BIGINT	        NOT NULL,
    question	            VARCHAR(300)	NOT NULL,
    type	                VARCHAR(10)	    NOT NULL,
    items	                VARCHAR(100)	NULL,
    CONSTRAINT PK_APPLICATION_QUESTION PRIMARY KEY (application_question_id),
    CONSTRAINT FK_APPLICATION_QUESTION_CAMPAIGN FOREIGN KEY (campaign_id) REFERENCES campaign (campaign_id)
);

CREATE TABLE campaign_image (
    campaign_image_id	BIGINT	        NOT NULL AUTO_INCREMENT,
    campaign_id	        BIGINT	        NOT NULL,
    file_name	        VARCHAR(100)	NOT NULL,
    CONSTRAINT PK_CAMPAIGN_IMAGE PRIMARY KEY (campaign_image_id),
    CONSTRAINT FK_CAMPAIGN_IMAGE_CAMPAIGN FOREIGN KEY (campaign_id) REFERENCES campaign (campaign_id)
);

CREATE TABLE application (
    application_id	BIGINT	        NOT NULL AUTO_INCREMENT,
    member_id	    BIGINT	        NOT NULL,
    campaign_id	    BIGINT	        NOT NULL,
    created	        DATETIME	    DEFAULT NOW(),
    memo	        VARCHAR(500)    NULL,
    status	        VARCHAR(10)     NOT NULL,
    CONSTRAINT PK_REVIEW_IMAGE PRIMARY KEY (application_id),
    CONSTRAINT FK_REVIEW_IMAGE_MEMBER FOREIGN KEY (member_id) REFERENCES member (member_id),
    CONSTRAINT FK_REVIEW_IMAGE_CAMPAIGN FOREIGN KEY (campaign_id) REFERENCES campaign (campaign_id)
);

CREATE TABLE application_answer (
    application_answer_id	BIGINT	        NOT NULL AUTO_INCREMENT,
    application_id	        BIGINT	        NOT NULL,
    application_question_id	BIGINT	        NOT NULL,
    answer	                VARCHAR(300)	NULL,
    CONSTRAINT PK_APPLICATION_ANSWER PRIMARY KEY (application_answer_id),
    CONSTRAINT FK_APPLICATION_ANSWER_APPLICATION FOREIGN KEY (application_id) REFERENCES application (application_id),
    CONSTRAINT FK_APPLICATION_ANSWER_APPLICATION_QUESTION FOREIGN KEY (application_question_id) REFERENCES application_question (application_question_id)
);

CREATE TABLE review (
    review_id	BIGINT	        NOT NULL AUTO_INCREMENT,
    member_id	BIGINT	        NOT NULL,
    campaign_id	BIGINT	        NOT NULL,
    review_url	VARCHAR(255)	NOT NULL,
    created	    DATETIME	    DEFAULT NOW(),
    CONSTRAINT PK_REVIEW PRIMARY KEY (review_id),
    CONSTRAINT FK_REVIEW_MEMBER FOREIGN KEY (member_id) REFERENCES member (member_id),
    CONSTRAINT FK_REVIEW_CAMPAIGN FOREIGN KEY (campaign_id) REFERENCES campaign (campaign_id)
);

INSERT INTO sns(name) VALUES('naver');
INSERT INTO sns(name) VALUES('instagram');
INSERT INTO sns(name) VALUES('youtube');

INSERT INTO local(name) VALUES('서울');
INSERT INTO local(name) VALUES('경기/인천');
INSERT INTO local(name) VALUES('대전/충청');
INSERT INTO local(name) VALUES('대구/경북');
INSERT INTO local(name) VALUES('부산/경남');
INSERT INTO local(name) VALUES('광주/전라');
INSERT INTO local(name) VALUES('다른지역');

INSERT INTO campaign_category(name) VALUES('맛집');
INSERT INTO campaign_category(name) VALUES('뷰티');
INSERT INTO campaign_category(name) VALUES('숙박');
INSERT INTO campaign_category(name) VALUES('문화');
INSERT INTO campaign_category(name) VALUES('배달');
INSERT INTO campaign_category(name) VALUES('테이크아웃');
INSERT INTO campaign_category(name) VALUES('기타');

INSERT INTO campaign_type(name) VALUES('방문형');
INSERT INTO campaign_type(name) VALUES('배송형');
INSERT INTO campaign_type(name) VALUES('기자단');
INSERT INTO campaign_type(name) VALUES('방문기자단');


-- 회원 DB
-- 비밀번호는 a123123x# x = 회원 1번부터 1 2 3 ... 회원1번 -> a1231231# 회원2번 -> a1231232#
INSERT INTO member (role, email, pwd, name, nickname, gender, birth, tel, postcode, address, detail_address, extra_address) VALUES
('ADMIN', 'test1@naver.com', '$2a$12$rV476b82BNuz4PFTzJfCaOV7OhBeZZXr9s4zHCFT6MD5llAmka4Wi', '테스트이름1', '테스트닉네임1', '남자', now(), '01000000001', '11900', '경기 구리시 갈매동 215-56', '1층', ''),
('USER', 'test2@naver.com', '$2a$12$oPS02PZ6PKhUt8vy3MMm6.zCBN4Zcj0Ae3YQEQZ2dv26OKpzpZtc2', '테스트이름2', '테스트닉네임2', '여자', now(), '01000000002', '12202', '경기 남양주시 와부읍 월문리 293-4', '2층', ''),
('USER', 'test3@naver.com', '$2a$12$gM5myqWV3crcI.mpjy9VI.8A7GTCJh90SZfnVfoxYgYgFSq7pe02u', '테스트이름3', '테스트닉네임3', '남자', now(), '01000000003', '13544', '경기 성남시 분당구 대장동 403-7', '3층', ''),
('USER', 'test4@naver.com', '$2a$12$pmyj4ZrfEZkFUS/9d2ogOeQsVG0bwRqNH7ftAOorrfRC03H5Uc1z2', '테스트이름4', '테스트닉네임4', '여자', now(), '01000000004', '13540', '경기 성남시 분당구 하산운동 362-5', '4층', ''),
('USER', 'test5@naver.com', '$2a$12$zbscNN9fHaxjd5jBYjIFYecJUA2E2y0Yp6miQYhwjLk9TfBunwIQy', '테스트이름5', '테스트닉네임5', '남자', now(), '01000000005', '13450', '경기 성남시 수정구 금토동 179-5', '5층', ''),
('USER', 'test6@naver.com', '$2a$12$w5tuXgZa/Ogq2aW4CxkaeusWY6cUa7SrBSQEPa8NKycmBo/O/ANke', '테스트이름6', '테스트닉네임6', '여자', now(), '01000000006', '13103', '경기 성남시 수정구 심곡동 396-155', '6층', ''),
('USER', 'test7@naver.com', '$2a$12$jDVUHVwTIyP2FpwFpC4hu.mWCxWI0zOFudVR/K5.N/ebF5WPbtqky', '테스트이름7', '테스트닉네임7', '남자', now(), '01000000007', '08826', '서울 관악구 신림동 산 56-1', '7층', ''),
('USER', 'test8@naver.com', '$2a$12$2ruUSmSrFyMPwEQ7JVqpze73G1F9MnewTsSVdK43p1d0Atngw3pPG', '테스트이름8', '테스트닉네임8', '여자', now(), '01000000008', '08825', '서울 관악구 신림동 211-11', '8층', ''),
('USER', 'test9@naver.com', '$2a$12$MDjQmnqZn9WbcwIp7yqmhe19CQnhp6FxhfDmvFBzVCL.QCxncPBne', '테스트이름9', '테스트닉네임9', '남자', now(), '01000000009', '34303', '대전 대덕구 문평동 400-4', '9층', ''),
('USER', 'test10@naver.com', '$2a$12$.dLIbKLBJ7uDLyqOkBs6GO0qR3zAp0XODuJIkMMLdh1zZdFYyYHjq', '테스트이름10', '테스트닉네임10', '여자', now(), '01000000010', '34335', '대전 대덕구 상서동 산 66-11', '10층', '');

-- 회원 SNS DB
-- 네이버
INSERT INTO member_sns (member_id, sns_id, sns_url) VALUES
(1, 1, 'https://blog.naver.com/songsong14'),
(2, 1, 'https://blog.naver.com/kccpe'),
(3, 1, 'https://blog.naver.com/rladnjstjd55'),
(4, 1, 'https://blog.naver.com/daisy_review'),
(5, 1, 'https://blog.naver.com/swkim3499'),
(6, 1, 'https://blog.naver.com/kudoseed'),
(7, 1, 'https://blog.naver.com/no_comm'),
(8, 1, 'https://blog.naver.com/pitteos'),
(9, 1, 'https://blog.naver.com/haru__1'),
(10, 1, 'https://blog.naver.com/01048720226');

-- 인스타그램
INSERT INTO member_sns (member_id, sns_id, sns_url) VALUES
(1, 2, 'https://www.instagram.com/chorong_v_v/'),
(2, 2, 'https://www.instagram.com/poodlemylife/'),
(3, 2, 'https://www.instagram.com/boriharu_store/'),
(4, 2, 'https://www.instagram.com/taepung._.ari/'),
(5, 2, 'https://www.instagram.com/gogo_ato0.0/'),
(6, 2, 'https://www.instagram.com/nadle_nezz/');

-- 유투브
INSERT INTO member_sns (member_id, sns_id, sns_url) VALUES
(7, 3, 'https://www.youtube.com/@1min_music'),
(8, 3, 'https://www.youtube.com/@sugirit'),
(9, 3, 'https://www.youtube.com/@KoreanSongs7');

-- 리뷰 (캠페인 1번은 블로그 2번은 인스타 3번은 유튜브여야 함)
INSERT INTO review (member_id, campaign_id, review_url) VALUES
(1, 1, 'https://blog.naver.com/songsong14/222301042183'),
(2, 1, 'https://blog.naver.com/songsong14/222862327183'),
(3, 1, 'https://blog.naver.com/songsong14/222973249245'),
(4, 1, 'https://blog.naver.com/songsong14/222973249245'),
(5, 1, 'https://blog.naver.com/songsong14/222973244513'),
(6, 1, 'https://blog.naver.com/songsong14/222973147532'),
(7, 1, 'https://blog.naver.com/songsong14/222992447251'),
(8, 1, 'https://blog.naver.com/songsong14/222992447251'),
(9, 1, 'https://blog.naver.com/songsong14/222989910602'),
(10, 1, 'https://blog.naver.com/songsong14/222982921850'),
(1, 2, 'https://www.instagram.com/p/CoKer7gr3Mq/'),
(2, 2, 'https://www.instagram.com/p/CoIEizrpexY/'),
(3, 2, 'https://www.instagram.com/p/CoID2aop92r/'),
(4, 2, 'https://www.instagram.com/p/Cn_1yc_L7j-/'),
(5, 2, 'https://www.instagram.com/p/Cn4LH40rZ3W/'),
(6, 2, 'https://www.instagram.com/p/Cn4KEp9LAwz/'),
(7, 2, 'https://www.instagram.com/p/CnTMW2mBLnp/'),
(8, 2, 'https://www.instagram.com/p/Cm_VCQwJXYA/'),
(9, 2, 'https://www.instagram.com/p/Cm39vk7L9LI/'),
(10, 2, 'https://www.instagram.com/p/Cm3k8lVL62s/'),
(1, 3, 'https://www.youtube.com/watch?v=bhJoZmAXuF'),
(2, 3, 'https://www.youtube.com/watch?v=ddsvjQ_S3wU'),
(3, 3, 'https://www.youtube.com/watch?v=WexRskfMaLQ'),
(4, 3, 'https://www.youtube.com/watch?v=9F9a_fFx45o'),
(5, 3, 'https://www.youtube.com/watch?v=xvzKNy0-VZw'),
(6, 3, 'https://www.youtube.com/watch?v=JYJ4OfB6ztc'),
(7, 3, 'https://www.youtube.com/watch?v=9aHLkpOhvO8'),
(8, 3, 'https://www.youtube.com/watch?v=IV_fNz4ojlI'),
(9, 3, 'https://www.youtube.com/watch?v=S7cvx-swESk'),
(10, 3, 'https://www.youtube.com/watch?v=PfvQplooctA'),
(1, 4, '리뷰주소2'),
(1, 5, '리뷰주소3'),
(1, 6, '리뷰주소4'),
(1, 7, '리뷰주소5'),
(1, 8, '리뷰주소6'),
(1, 9, '리뷰주소7'),
(1, 10, '리뷰주소10'),
(2, 4, '리뷰주소4'),
(2, 5, '리뷰주소5'),
(2, 6, '리뷰주소6'),
(2, 7, '리뷰주소7'),
(2, 8, '리뷰주소8'),
(2, 9, '리뷰주소9'),
(2, 10, '리뷰주소10'),
(3, 4, '리뷰주소4'),
(3, 5, '리뷰주소5'),
(4, 4, '리뷰주소4'),
(4, 5, '리뷰주소5'),
(4, 6, '리뷰주소6'),
(4, 7, '리뷰주소7'),
(4, 8, '리뷰주소8'),
(4, 5, '리뷰주소5'),
(4, 7, '리뷰주소7');


