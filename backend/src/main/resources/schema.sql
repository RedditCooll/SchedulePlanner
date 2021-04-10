CREATE TABLE SCHEDULE (
    SCHEDULE_ID varchar(64),
    _DATE date,
    USER_ID varchar(16),
    PRIORITY int,
    STATUS varchar(16),
    CLASSIFICATION varchar(16),
    CONTENT varchar(1024),
    ADDRESS varchar(255),
    VERY_GOOD int,
    GOOD int,
    _LIKE int,
    PRIMARY KEY (SCHEDULE_ID)
);