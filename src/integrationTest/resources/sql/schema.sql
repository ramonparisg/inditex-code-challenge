CREATE TABLE BRAND
(
    ID   INT PRIMARY KEY AUTO_INCREMENT,
    NAME varchar(255) NOT NULL
);

CREATE TABLE PRICES
(
    PRICE_LIST INT PRIMARY KEY AUTO_INCREMENT,
    START_DATE TIMESTAMP,
    END_DATE   TIMESTAMP,
    PRODUCT_ID INT,
    PRIORITY   INT,
    PRICE      FLOAT,
    CURR       CHAR(3),
    BRAND_ID   INT,
    CONSTRAINT FK_BRAND FOREIGN KEY (BRAND_ID) REFERENCES BRAND (ID)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
