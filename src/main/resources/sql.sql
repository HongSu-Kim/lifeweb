
DROP TABLE IF EXISTS application;

DROP TABLE IF EXISTS review;

DROP TABLE IF EXISTS campaign_sns;
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
    sns_id  BIGINT      NOT NULL,
    name    VARCHAR(30)	NOT NULL,
    CONSTRAINT PK_SNS PRIMARY KEY (sns_id)
);

CREATE TABLE member (
    member_id       BIGINT	        NOT NULL,
    role            VARCHAR(10)     NOT NULL,
    email           VARCHAR(30)	    NOT NULL,
    pwd	            VARCHAR(60)	    NOT NULL,
    file_name       VARCHAR(100)	NULL,
    name            VARCHAR(30)	    NOT NULL,
    nickname        VARCHAR(30)	    NOT NULL,
    gender          VARCHAR(10)     NOT NULL,
    birth           DATE	        NOT NULL,
    tel             CHAR(11)	    NULL,
    postcode        CHAR(5)	        NOT NULL,
    address         VARCHAR(100)    NOT NULL,
    detail_address  VARCHAR(100)    NOT NULL,
    extra_address   VARCHAR(100)    NOT NULL,
    point           INT	            DEFAULT 0,
    CONSTRAINT PK_MEMBER PRIMARY KEY (member_id)
);

CREATE TABLE member_sns (
    member_sns_id   BIGINT	        NOT NULL,
    member_id	    BIGINT	        NOT NULL,
    sns_id	        BIGINT	        NOT NULL,
    sns_url         VARCHAR(255)    NOT NULL,
    CONSTRAINT PK_MEMBER_SNS PRIMARY KEY (member_sns_id),
    CONSTRAINT FK_MEMBER_SNS_MEMBER FOREIGN KEY (member_id) REFERENCES member (member_id),
    CONSTRAINT FK_MEMBER_SNS_SNS FOREIGN KEY (sns_id) REFERENCES sns (sns_id)
);

CREATE TABLE local (
    local_id	BIGINT	    NOT NULL,
    name	    VARCHAR(30)	NOT NULL,
    CONSTRAINT PK_LOCAL PRIMARY KEY (local_id)
);

CREATE TABLE campaign_category (
    campaign_category_id	BIGINT	    NOT NULL,
    name	                VARCHAR(30)	NOT NULL,
    CONSTRAINT PK_CAMPAIGN_CATEGORY PRIMARY KEY (campaign_category_id)
);

CREATE TABLE campaign_type (
    campaign_type_id	BIGINT	    NOT NULL,
    name	            VARCHAR(30)	NOT NULL,
    CONSTRAINT PK_CAMPAIGN_TYPE PRIMARY KEY (campaign_type_id)
);

CREATE TABLE campaign (
    campaign_id	            BIGINT	        NOT NULL,
    campaign_category_id	BIGINT	        NOT NULL,
    campaign_type_id	    BIGINT	        NOT NULL,
    special	                BOOLEAN	        NOT NULL,
    title	                VARCHAR(255)	NOT NULL,
    file_name	            VARCHAR(100)	NOT NULL,
    provision	            VARCHAR(500)	NOT NULL,
    created	                DATETIME	    DEFAULT NOW(),
    review_notice	        VARCHAR(6000)	NOT NULL,
    guideline	            VARCHAR(6000)	NOT NULL,
    receive_start_date	    DATE	        NOT NULL,
    receive_end_date	    DATE	        NOT NULL,
    start_date	            DATE	        NOT NULL,
    end_date	            DATE	        NOT NULL,
    keywords	            VARCHAR(255)	NOT NULL,
    status                  VARCHAR(10)	    NOT NULL,
    CONSTRAINT PK_CAMPAIGN PRIMARY KEY (campaign_id),
    CONSTRAINT FK_CAMPAIGN_CAMPAIGN_CATEGORY FOREIGN KEY (campaign_category_id) REFERENCES campaign_category (campaign_category_id),
    CONSTRAINT FK_CAMPAIGN_CAMPAIGN_TYPE FOREIGN KEY (campaign_type_id) REFERENCES campaign_type (campaign_type_id)
);

CREATE TABLE campaign_local (
    campaign_local_id	BIGINT	        NOT NULL,
    campaign_id	        BIGINT	        NOT NULL,
    local_id	        BIGINT	        NOT NULL,
    address         	VARCHAR(255)	NOT NULL,
    latitude	        VARCHAR(30)	    NOT NULL,
    longitude	        VARCHAR(30)	    NOT NULL,
    visit_notice	    VARCHAR(1000)	NOT NULL,
    CONSTRAINT PK_CAMPAIGN_LOCAL PRIMARY KEY (campaign_local_id),
    CONSTRAINT FK_CAMPAIGN_LOCAL_CAMPAIGN FOREIGN KEY (campaign_id) REFERENCES campaign (campaign_id),
    CONSTRAINT FK_CAMPAIGN_LOCAL_LOCAL FOREIGN KEY (local_id) REFERENCES local (local_id)
);

CREATE TABLE campaign_image (
    campaign_image_id	BIGINT	        NOT NULL,
    campaign_id	        BIGINT	        NOT NULL,
    file_name	        VARCHAR(100)	NOT NULL,
    CONSTRAINT PK_CAMPAIGN_IMAGE PRIMARY KEY (campaign_image_id),
    CONSTRAINT FK_CAMPAIGN_IMAGE_CAMPAIGN FOREIGN KEY (campaign_id) REFERENCES campaign (campaign_id)
);

CREATE TABLE campaign_sns (
    campaign_sns_id	BIGINT	NOT NULL,
    campaign_id	    BIGINT	NOT NULL,
    sns_id	        BIGINT	NOT NULL,
    CONSTRAINT PK_CAMPAIGN_SNS PRIMARY KEY (campaign_sns_id),
    CONSTRAINT FK_CAMPAIGN_SNS_CAMPAIGN FOREIGN KEY (campaign_id) REFERENCES campaign (campaign_id),
    CONSTRAINT FK_CAMPAIGN_SNS_SNS FOREIGN KEY (sns_id) REFERENCES sns (sns_id)
);

CREATE TABLE review (
    review_id	BIGINT	        NOT NULL,
    member_id	BIGINT	        NOT NULL,
    campaign_id	BIGINT	        NOT NULL,
    review_url	VARCHAR(255)	NOT NULL,
    created	    DATETIME	    DEFAULT NOW(),
    CONSTRAINT PK_REVIEW PRIMARY KEY (review_id),
    CONSTRAINT FK_REVIEW_MEMBER FOREIGN KEY (member_id) REFERENCES member (member_id),
    CONSTRAINT FK_REVIEW_CAMPAIGN FOREIGN KEY (campaign_id) REFERENCES campaign (campaign_id)
);

CREATE TABLE application (
    application_id	BIGINT	        NOT NULL,
    member_id	    BIGINT	        NOT NULL,
    campaign_id	    BIGINT	        NOT NULL,
    sns_id	        BIGINT	        NOT NULL,
    created	        DATETIME	    DEFAULT NOW(),
    memo	        VARCHAR(500)    NOT NULL,
    CONSTRAINT PK_REVIEW_IMAGE PRIMARY KEY (application_id),
    CONSTRAINT FK_REVIEW_IMAGE_MEMBER FOREIGN KEY (member_id) REFERENCES member (member_id),
    CONSTRAINT FK_REVIEW_IMAGE_CAMPAIGN FOREIGN KEY (campaign_id) REFERENCES campaign (campaign_id),
    CONSTRAINT FK_REVIEW_IMAGE_SNS FOREIGN KEY (sns_id) REFERENCES sns (sns_id)
);
