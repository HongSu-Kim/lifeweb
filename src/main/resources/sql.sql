
DROP TABLE IF EXISTS review;

DROP TABLE IF EXISTS application_answer;
DROP TABLE IF EXISTS application;

DROP TABLE IF EXISTS application_question;
DROP TABLE IF EXISTS campaign_image;
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
    status                  VARCHAR(20)	    DEFAULT 'STAND',
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
);;

CREATE TABLE campaign_image (
    campaign_image_id	BIGINT	        NOT NULL AUTO_INCREMENT,
    campaign_id	        BIGINT	        NOT NULL,
    file_name	        VARCHAR(100)	NOT NULL,
    CONSTRAINT PK_CAMPAIGN_IMAGE PRIMARY KEY (campaign_image_id),
    CONSTRAINT FK_CAMPAIGN_IMAGE_CAMPAIGN FOREIGN KEY (campaign_id) REFERENCES campaign (campaign_id)
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
    review_title VARCHAR(255),
    review_img VARCHAR(500),
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

-- 캠페인
-- COMPLETE : 1 ~ 10
INSERT INTO campaign (campaign_category_id, campaign_type_id, sns_id, special, title, file_name, provision, created, review_notice, guideline, keywords,
                      application_start_date, application_end_date, filing_start_date, filing_end_date, headcount, status) VALUES
(1, 1, 1, FALSE, '테스트 제목1', '테스트 대표이미지1.jpg', '테스트 제공내역1', DATE_SUB(NOW(), INTERVAL 16 DAY), '테스트 리뷰주의사항1', '테스트 가이드라인1', '테스트 키워드1#테스트 캠페인1#테스트1',
 DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 10, 'COMPLETE'),
(1, 1, 2, FALSE, '테스트 제목2', '테스트 대표이미지2.jpg', '테스트 제공내역2', DATE_SUB(NOW(), INTERVAL 16 DAY), '테스트 리뷰주의사항2', '테스트 가이드라인2', '테스트 키워드2#테스트 캠페인2#테스트2',
 DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 10, 'COMPLETE'),
(1, 1, 3, TRUE, '테스트 제목3', '테스트 대표이미지3.jpg', '테스트 제공내역3', DATE_SUB(NOW(), INTERVAL 16 DAY), '테스트 리뷰주의사항3', '테스트 가이드라인3', '테스트 키워드3#테스트 캠페인3#테스트3',
 DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 10, 'COMPLETE'),
(1, 1, 1, FALSE, '테스트 제목4', '테스트 대표이미지4.jpg', '테스트 제공내역4', DATE_SUB(NOW(), INTERVAL 16 DAY), '테스트 리뷰주의사항4', '테스트 가이드라인4', '테스트 키워드4#테스트 캠페인4#테스트4',
 DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 10, 'COMPLETE'),
(1, 1, 1, FALSE, '테스트 제목5', '테스트 대표이미지5.jpg', '테스트 제공내역5', DATE_SUB(NOW(), INTERVAL 16 DAY), '테스트 리뷰주의사항5', '테스트 가이드라인5', '테스트 키워드5#테스트 캠페인5#테스트5',
 DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 10, 'COMPLETE'),
(1, 1, 1, FALSE, '테스트 제목6', '테스트 대표이미지6.jpg', '테스트 제공내역6', DATE_SUB(NOW(), INTERVAL 16 DAY), '테스트 리뷰주의사항6', '테스트 가이드라인6', '테스트 키워드6#테스트 캠페인6#테스트6',
 DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 10, 'COMPLETE'),
(1, 1, 1, FALSE, '테스트 제목7', '테스트 대표이미지7.jpg', '테스트 제공내역7', DATE_SUB(NOW(), INTERVAL 16 DAY), '테스트 리뷰주의사항7', '테스트 가이드라인7', '테스트 키워드7#테스트 캠페인7#테스트7',
 DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 10, 'COMPLETE'),
(1, 1, 1, FALSE, '테스트 제목8', '테스트 대표이미지8.jpg', '테스트 제공내역8', DATE_SUB(NOW(), INTERVAL 16 DAY), '테스트 리뷰주의사항8', '테스트 가이드라인8', '테스트 키워드8#테스트 캠페인8#테스트8',
 DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 10, 'COMPLETE'),
(1, 1, 1, FALSE, '테스트 제목9', '테스트 대표이미지9.jpg', '테스트 제공내역9', DATE_SUB(NOW(), INTERVAL 16 DAY), '테스트 리뷰주의사항9', '테스트 가이드라인9', '테스트 키워드9#테스트 캠페인9#테스트9',
 DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 10, 'COMPLETE'),
(1, 1, 1, FALSE, '테스트 제목10', '테스트 대표이미지10.jpg', '테스트 제공내역10', DATE_SUB(NOW(), INTERVAL 16 DAY), '테스트 리뷰주의사항10', '테스트 가이드라인10', '테스트 키워드10#테스트 캠페인10#테스트10',
 DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), 10, 'COMPLETE'),
-- APPLICATION : 11 ~ 12
(1, 1, 1, FALSE, '테스트 제목11', '테스트 대표이미지11.jpg', '테스트 제공내역11', DATE_SUB(NOW(), INTERVAL 11 DAY), '테스트 리뷰주의사항11', '테스트 가이드라인11', '테스트 키워드11#테스트 캠페인11#테스트11',
 DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_ADD(NOW(), INTERVAL 5 DAY), 10, 'APPLICATION'),
(1, 1, 2, TRUE, '테스트 제목12', '테스트 대표이미지12.jpg', '테스트 제공내역12', DATE_SUB(NOW(), INTERVAL 11 DAY), '테스트 리뷰주의사항12', '테스트 가이드라인12', '테스트 키워드12#테스트 캠페인12#테스트12',
 DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_ADD(NOW(), INTERVAL 5 DAY), 10, 'APPLICATION'),
-- FILING : 13 ~ 14
(1, 1, 3, FALSE, '테스트 제목13', '테스트 대표이미지13.jpg', '테스트 제공내역13', DATE_SUB(NOW(), INTERVAL 5 DAY), '테스트 리뷰주의사항13', '테스트 가이드라인13', '테스트 키워드13#테스트 캠페인13#테스트13',
 DATE_SUB(NOW(), INTERVAL 4 DAY), DATE_ADD(NOW(), INTERVAL 3 DAY), DATE_ADD(NOW(), INTERVAL 4 DAY), DATE_ADD(NOW(), INTERVAL 11 DAY), 10, 'FILING'),
(1, 1, 1, TRUE, '테스트 제목14', '테스트 대표이미지14.jpg', '테스트 제공내역14', DATE_SUB(NOW(), INTERVAL 5 DAY), '테스트 리뷰주의사항14', '테스트 가이드라인14', '테스트 키워드14#테스트 캠페인14#테스트14',
 DATE_SUB(NOW(), INTERVAL 4 DAY), DATE_ADD(NOW(), INTERVAL 3 DAY), DATE_ADD(NOW(), INTERVAL 4 DAY), DATE_ADD(NOW(), INTERVAL 11 DAY), 10, 'FILING'),
-- STAND : 15 ~ 16
(1, 1, 1, FALSE, '테스트 제목15', '테스트 대표이미지15.jpg', '테스트 제공내역15', NOW(), '테스트 리뷰주의사항15', '테스트 가이드라인15', '테스트 키워드15#테스트 캠페인15#테스트15',
 DATE_ADD(NOW(), INTERVAL 1 DAY), DATE_ADD(NOW(), INTERVAL 8 DAY), DATE_ADD(NOW(), INTERVAL 9 DAY), DATE_ADD(NOW(), INTERVAL 15 DAY), 10, 'STAND'),
(1, 1, 1, TRUE, '테스트 제목16', '테스트 대표이미지16.jpg', '테스트 제공내역16', NOW(), '테스트 리뷰주의사항16', '테스트 가이드라인16', '테스트 키워드16#테스트 캠페인16#테스트16',
 DATE_ADD(NOW(), INTERVAL 1 DAY), DATE_ADD(NOW(), INTERVAL 8 DAY), DATE_ADD(NOW(), INTERVAL 9 DAY), DATE_ADD(NOW(), INTERVAL 15 DAY), 10, 'STAND');

-- 캠페인 지역
INSERT INTO campaign_local(campaign_id, local_id, address, latitude, longitude, visit_notice) VALUES
(1, 1, '서울특별시 강남구 역삼1동', '37.4954841', '127.0333574', '테스트 방문주의사항1'),
(3, 2, '경기도', '37.4959674', '127.0468034', '테스트 방문주의사항3'),
(4, 3, '대전광역시', '37.5115706', '127.028461', '테스트 방문주의사항4'),
(5, 1, '서울특별시 강남구 논현2동', '37.517342', '127.037213', '테스트 방문주의사항5'),
(7, 1, '서울특별시 강남구 삼성1동', '37.5144424', '127.062532', '테스트 방문주의사항7'),
(9, 1, '서울특별시 강남구 삼성2동', '37.5112', '127.04595', '테스트 방문주의사항8'),
(11, 4, '대구광역시', '37.4931821', '127.0567047', '테스트 방문주의사항11'),
(13, 1, '서울특별시 강남구 대치2동', '37.5022848', '127.0642072', '테스트 방문주의사항13'),
(14, 1, '서울특별시 강남구 대치4동', '37.4997415', '127.0579127', '테스트 방문주의사항14'),
(15, 1, '서울특별시 강남구 도곡1동', '37.488238', '127.0390246', '테스트 방문주의사항15');

-- 캠페인 이미지
INSERT INTO campaign_image(campaign_id, file_name) VALUES
(1, '이미지1_1.jpg'), (1, '이미지1_2.jpg'), (1, '이미지1_3.jpg'),
(2, '이미지2_1.jpg'), (2, '이미지2_2.jpg'), (2, '이미지2_3.jpg'),
(3, '이미지3_1.jpg'),
(4, '이미지4_1.jpg'), (4, '이미지4_2.jpg'), (4, '이미지4_3.jpg'),
(6, '이미지6_1.jpg'), (6, '이미지6_2.jpg'),
(8, '이미지8_1.jpg'),
(9, '이미지9_1.jpg'), (9, '이미지9_2.jpg'), (9, '이미지9_3.jpg'),
(10, '이미지11_1.jpg'), (10, '이미지11_2.jpg'),
(13, '이미지13_1.jpg'), (13, '이미지13_2.jpg'),
(14, '이미지14_1.jpg'),
(15, '이미지15_1.jpg'), (15, '이미지15_2.jpg'), (15, '이미지15_3.jpg'), (15, '이미지15_4.jpg'),
(16, '이미지16_1.jpg');

-- 캠페인 질문
INSERT INTO application_question(campaign_id, question, type, items) VALUES
(1, '테스트 TEXT 질문1', 'TEXT', NULL),
(1, '테스트 RADIO 질문1', 'RADIO', '테스트 RADIO 항목1#테스트 RADIO 항목2#테스트 RADIO 항목3'),
(1, '테스트 CHECKBOX 질문1', 'CHECKBOX', '테스트 CHECKBOX 항목1#테스트 CHECKBOX 항목2#테스트 CHECKBOX 항목3'),
(2, '테스트 TEXT 질문2', 'TEXT', NULL),
(3, '테스트 TEXTAREA 질문3', 'TEXTAREA', NULL),
(4, '테스트 RADIO 질문4', 'RADIO', '테스트 RADIO 항목1#테스트 RADIO 항목2#테스트 RADIO 항목3'),
(5, '테스트 CHECKBOX 질문5', 'CHECKBOX', '테스트 CHECKBOX 항목1#테스트 CHECKBOX 항목2#테스트 CHECKBOX 항목3'),
(11, '테스트 TEXT 질문11', 'TEXT', NULL),
(12, '테스트 RADIO 질문12', 'RADIO', '테스트 RADIO 항목1#테스트 RADIO 항목2#테스트 RADIO 항목3'),
(13, '테스트 TEXTAREA 질문13', 'TEXTAREA', NULL), -- 10
(14, '테스트 TEXT 질문14', 'TEXT', NULL),
(14, '테스트 TEXTAREA 질문14', 'TEXTAREA', NULL),
(14, '테스트 RADIO 질문14', 'RADIO', '테스트 RADIO 항목1#테스트 RADIO 항목2#테스트 RADIO 항목3'),
(14, '테스트 CHECKBOX 질문14', 'CHECKBOX', '테스트 CHECKBOX 항목1#테스트 CHECKBOX 항목2#테스트 CHECKBOX 항목3'),
(15, '테스트 CHECKBOX 질문15', 'CHECKBOX', '테스트 CHECKBOX 항목1#테스트 CHECKBOX 항목2#테스트 CHECKBOX 항목3');

-- 신청서
INSERT INTO application(member_id, campaign_id, created, memo, status) VALUES
-- CampaignStatus -> COMPLETE : campaign_id -> 1 ~ 10
(1, 1, NOW(), '테스트 메모1-1', 'SELECT'), (1, 2, NOW(), '테스트 메모1-2', 'SELECT'),
(1, 3, NOW(), '테스트 메모1-3', 'SELECT'), (1, 4, NOW(), '테스트 메모1-4', 'SELECT'),
(1, 5, NOW(), '테스트 메모1-5', 'SELECT'), (1, 6, NOW(), '테스트 메모1-6', 'SELECT'),
(1, 7, NOW(), '테스트 메모1-7', 'SELECT'), (1, 8, NOW(), '테스트 메모1-8', 'SELECT'),
(1, 9, NOW(), '테스트 메모1-9', 'SELECT'), (1, 10, NOW(), '테스트 메모1-10', 'SELECT'), -- 10
(2, 1, NOW(), '테스트 메모2-1', 'SELECT'), (2, 2, NOW(), '테스트 메모2-2', 'SELECT'),
(2, 3, NOW(), '테스트 메모2-3', 'SELECT'), (2, 4, NOW(), '테스트 메모2-4', 'SELECT'),
(2, 5, NOW(), '테스트 메모2-5', 'SELECT'), (2, 6, NOW(), '테스트 메모2-6', 'SELECT'),
(2, 7, NOW(), '테스트 메모2-7', 'SELECT'), (2, 8, NOW(), '테스트 메모2-8', 'SELECT'),
(2, 9, NOW(), '테스트 메모2-9', 'SELECT'), (2, 10, NOW(), '테스트 메모2-10', 'SELECT'), -- 20
(3, 1, NOW(), '테스트 메모3-1', 'SELECT'), (3, 2, NOW(), '테스트 메모3-2', 'SELECT'),
(3, 3, NOW(), '테스트 메모3-3', 'SELECT'), (3, 4, NOW(), '테스트 메모3-4', 'SELECT'),
(3, 5, NOW(), '테스트 메모3-5', 'SELECT'), (3, 6, NOW(), '테스트 메모3-6', 'SELECT'),
(3, 7, NOW(), '테스트 메모3-7', 'SELECT'), (3, 8, NOW(), '테스트 메모3-8', 'SELECT'),
(3, 9, NOW(), '테스트 메모3-9', 'SELECT'), (3, 10, NOW(), '테스트 메모3-10', 'SELECT'), -- 30
(4, 1, NOW(), '테스트 메모4-1', 'SELECT'), (4, 2, NOW(), '테스트 메모4-2', 'SELECT'),
(4, 3, NOW(), '테스트 메모4-3', 'SELECT'), (4, 4, NOW(), '테스트 메모4-4', 'SELECT'),
(4, 5, NOW(), '테스트 메모4-5', 'SELECT'), (4, 6, NOW(), '테스트 메모4-6', 'SELECT'),
(4, 7, NOW(), '테스트 메모4-7', 'SELECT'), (4, 8, NOW(), '테스트 메모4-8', 'SELECT'),
(4, 9, NOW(), '테스트 메모4-9', 'SELECT'), (4, 10, NOW(), '테스트 메모4-10', 'SELECT'), -- 40
(5, 1, NOW(), '테스트 메모5-1', 'SELECT'), (5, 2, NOW(), '테스트 메모5-2', 'SELECT'), (5, 3, NOW(), '테스트 메모5-3', 'SELECT'),
(6, 1, NOW(), '테스트 메모6-1', 'SELECT'), (6, 2, NOW(), '테스트 메모6-2', 'SELECT'), (6, 3, NOW(), '테스트 메모6-3', 'SELECT'),
(7, 1, NOW(), '테스트 메모7-1', 'SELECT'), (7, 2, NOW(), '테스트 메모7-2', 'SELECT'), (7, 3, NOW(), '테스트 메모7-3', 'SELECT'),
(8, 1, NOW(), '테스트 메모8-1', 'SELECT'), (8, 2, NOW(), '테스트 메모8-2', 'SELECT'), (8, 3, NOW(), '테스트 메모8-3', 'SELECT'),
(9, 1, NOW(), '테스트 메모9-1', 'SELECT'), (9, 2, NOW(), '테스트 메모9-2', 'SELECT'), (9, 3, NOW(), '테스트 메모9-3', 'SELECT'),
(10, 1, NOW(), '테스트 메모10-1', 'SELECT'), (10, 2, NOW(), '테스트 메모10-2', 'SELECT'), (10, 3, NOW(), '테스트 메모10-3', 'SELECT'), -- 58
-- CampaignStatus -> FILING : campaign_id -> 11 ~ 12
(1, 11, NOW(), '테스트 메모1-11', 'SELECT'), (1, 12, NOW(), '테스트 메모1-12', 'SELECT'),
(2, 11, NOW(), '테스트 메모2-11', 'SELECT'), (2, 12, NOW(), '테스트 메모2-12', 'SELECT'),
(3, 11, NOW(), '테스트 메모3-11', 'SELECT'), (3, 12, NOW(), '테스트 메모3-12', 'SELECT'),
(4, 11, NOW(), '테스트 메모4-11', 'SELECT'), (4, 12, NOW(), '테스트 메모4-12', 'SELECT'),
(5, 11, NOW(), '테스트 메모5-11', 'SELECT'), (5, 12, NOW(), '테스트 메모5-12', 'SELECT'),
(6, 11, NOW(), '테스트 메모6-11', 'SELECT'), (6, 12, NOW(), '테스트 메모6-12', 'SELECT'),
(7, 11, NOW(), '테스트 메모7-11', 'SELECT'), (7, 12, NOW(), '테스트 메모7-12', 'SELECT'),
(8, 11, NOW(), '테스트 메모8-11', 'SELECT'), (8, 12, NOW(), '테스트 메모8-12', 'SELECT'),
(9, 11, NOW(), '테스트 메모9-11', 'SELECT'), (9, 12, NOW(), '테스트 메모9-12', 'SELECT'),
(10, 11, NOW(), '테스트 메모10-11', 'SELECT'), (10, 12, NOW(), '테스트 메모10-12', 'SELECT'), -- 78
-- CampaignStatus -> APPLICATION : campaign_id -> 13 ~ 14
(1, 13, NOW(), '테스트 메모1-11', 'UNSELECT'),
(3, 13, NOW(), '테스트 메모1-11', 'UNSELECT'),
(5, 13, NOW(), '테스트 메모1-11', 'UNSELECT'),
(7, 13, NOW(), '테스트 메모1-11', 'UNSELECT'),
(9, 13, NOW(), '테스트 메모1-11', 'UNSELECT'),
(8, 14, NOW(), '테스트 메모1-11', 'UNSELECT'),
(10, 14, NOW(), '테스트 메모1-11', 'UNSELECT'); -- 85

-- 신청서 답변
INSERT INTO application_answer(application_id, application_question_id, answer) VALUES
(1, 1, '테스트 TEXT 답변1-1'), (1, 2, '테스트 RADIO 항목1'), (1, 3, '테스트 CHECKBOX 항목1'),
(11, 1, '테스트 TEXT 답변11-1'), (11, 2, '테스트 RADIO 항목2'), (11, 3, '테스트 CHECKBOX 항목2'),
(21, 1, '테스트 TEXT 답변1-3'), (21, 2, '테스트 RADIO 항목3'), (21, 3, '테스트 CHECKBOX 항목3'),
(31, 1, '테스트 TEXT 답변1-4'), (31, 2, '테스트 RADIO 항목1'), (31, 3, '테스트 CHECKBOX 항목1#테스트 CHECKBOX 항목2'),
(41, 1, '테스트 TEXT 답변1-5'), (41, 2, '테스트 RADIO 항목2'), (41, 3, '테스트 CHECKBOX 항목2#테스트 CHECKBOX 항목3'),
(44, 1, '테스트 TEXT 답변1-6'), (44, 2, '테스트 RADIO 항목3'), (44, 3, '테스트 CHECKBOX 항목1#테스트 CHECKBOX 항목3'),
(47, 1, '테스트 TEXT 답변1-7'), (47, 2, '테스트 RADIO 항목1'), (47, 3, '테스트 CHECKBOX 항목1#테스트 CHECKBOX 항목2#테스트 CHECKBOX 항목3'),
(50, 1, '테스트 TEXT 답변1-8'), (50, 2, '테스트 RADIO 항목2'), (50, 3, '테스트 CHECKBOX 항목1#테스트 CHECKBOX 항목2'),
(53, 1, '테스트 TEXT 답변1-9'), (53, 2, '테스트 RADIO 항목3'), (53, 3, '테스트 CHECKBOX 항목2#테스트 CHECKBOX 항목3'),
(56, 1, '테스트 TEXT 답변1-10'), (56, 2, '테스트 RADIO 항목1'), (56, 3, '테스트 CHECKBOX 항목1#테스트 CHECKBOX 항목3'),
(2, 4, '테스트 TEXT 답변2-1'), (12, 4, '테스트 TEXT 답변2-2'),
(22, 4, '테스트 TEXT 답변2-3'), (32, 4, '테스트 TEXT 답변2-4'),
(42, 4, '테스트 TEXT 답변2-5'), (45, 4, '테스트 TEXT 답변2-6'),
(48, 4, '테스트 TEXT 답변2-7'), (51, 4, '테스트 TEXT 답변2-8'),
(54, 4, '테스트 TEXT 답변2-9'), (57, 4, '테스트 TEXT 답변2-10'),
(3, 5, '테스트 TEXTAREA 답변3-1'), (13, 5, '테스트 TEXTAREA 답변3-2'),
(23, 5, '테스트 TEXTAREA 답변3-3'), (23, 5, '테스트 TEXTAREA 답변3-4'),
(43, 5, '테스트 TEXTAREA 답변3-5'), (46, 5, '테스트 TEXTAREA 답변3-6'),
(49, 5, '테스트 TEXTAREA 답변3-7'), (52, 5, '테스트 TEXTAREA 답변3-8'),
(55, 5, '테스트 TEXTAREA 답변3-7'), (58, 5, '테스트 TEXTAREA 답변3-8'),
(4, 6, '테스트 RADIO 항목1'), (14, 6, '테스트 RADIO 항목2'),
(24, 6, '테스트 RADIO 항목3'), (34, 6, '테스트 RADIO 항목1'),
(5, 7, '테스트 CHECKBOX 항목1#테스트 CHECKBOX 항목2'), (15, 7, '테스트 CHECKBOX 항목2#테스트 CHECKBOX 항목3'),
(25, 7, '테스트 CHECKBOX 항목1#테스트 CHECKBOX 항목3'), (35, 7, '테스트 CHECKBOX 항목1#테스트 CHECKBOX 항목2#테스트 CHECKBOX 항목3'),

(59, 8, '테스트 TEXT 답변11-1'), (61, 8, '테스트 TEXT 답변11-2'),
(63, 8, '테스트 TEXT 답변11-3'), (65, 8, '테스트 TEXT 답변11-4'),
(67, 8, '테스트 TEXT 답변11-5'), (69, 8, '테스트 TEXT 답변11-6'),
(71, 8, '테스트 TEXT 답변11-7'), (73, 8, '테스트 TEXT 답변11-8'),
(75, 8, '테스트 TEXT 답변11-9'), (77, 8, '테스트 TEXT 답변11-10'),
(60, 9, '테스트 RADIO 항목1'), (62, 9, '테스트 RADIO 항목1'),
(64, 9, '테스트 RADIO 항목1'), (66, 9, '테스트 RADIO 항목1'),
(68, 9, '테스트 RADIO 항목1'), (70, 9, '테스트 RADIO 항목1'),
(72, 9, '테스트 RADIO 항목1'), (74, 9, '테스트 RADIO 항목1'),
(76, 9, '테스트 RADIO 항목1'), (78, 9, '테스트 RADIO 항목1'),

(79, 10, '테스트 TEXT 답변11-1'),
(80, 10, '테스트 TEXT 답변11-1'),
(81, 10, '테스트 TEXT 답변11-1'),
(82, 10, '테스트 TEXT 답변11-1'),
(83, 10, '테스트 TEXT 답변11-1'),
(84, 11, '테스트 TEXT 답변11-1'),
(84, 12, '테스트 TEXTAREA 답변11-1'),
(84, 13, '테스트 RADIO 항목1'),
(84, 14, '테스트 CHECKBOX 항목1#테스트 CHECKBOX 항목2'),
(85, 11, '테스트 TEXT 답변11-1'),
(85, 12, '테스트 TEXTAREA 답변11-1'),
(85, 13, '테스트 RADIO 항목2'),
(85, 14, '테스트 CHECKBOX 항목2#테스트 CHECKBOX 항목3');

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
(1, 4, '리뷰주소4'),
(1, 5, '리뷰주소5'),
(1, 6, '리뷰주소6'),
(1, 7, '리뷰주소7'),
(1, 8, '리뷰주소8'),
(1, 9, '리뷰주소9'),
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
(3, 6, '리뷰주소6'),
(3, 7, '리뷰주소7'),
(3, 8, '리뷰주소8'),
(3, 9, '리뷰주소9'),
(3, 10, '리뷰주소10'),
(4, 4, '리뷰주소4'),
(4, 5, '리뷰주소5'),
(4, 6, '리뷰주소6'),
(4, 7, '리뷰주소7'),
(4, 8, '리뷰주소8'),
(4, 9, '리뷰주소9'),
(4, 10, '리뷰주소10');
