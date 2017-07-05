CREATE DATABASE webdoor;

DROP TABLE customer;
CREATE TABLE customer (CUSTOMER_ID INTEGER IDENTITY, CKEY INTEGER, CNIC BIGINT, NAME VARCHAR(30), FATHERNAME VARCHAR(30), USERNAME VARCHAR(30), PASSWORD VARCHAR(30), EMAIL VARCHAR(30), ZONE VARCHAR(30), DOR VARCHAR(30), TELEPHONE VARCHAR(30), MOBILE VARCHAR(30), ADDRESS VARCHAR(100));
INSERT INTO customer(CKEY,CNIC,NAME,FATHERNAME,USERNAME,PASSWORD,EMAIL,ZONE,DOR,TELEPHONE,MOBILE,ADDRESS) VALUES (111111111111111,'Rafaqath','Rahim Dad','Rafaqat','7077','','M.C','','','0315 7257077','Togh Bala');
INSERT INTO customer(CKEY,CNIC,NAME,FATHERNAME,USERNAME,PASSWORD,EMAIL,ZONE,DOR,TELEPHONE,MOBILE,ADDRESS) VALUES (111111111111112,'Mohammad naeem','Chand Badshah','naeem','iqra','','M.Z','','','0333 3501501','Mohammad Zai');
INSERT INTO customer(CKEY,CNIC,NAME,FATHERNAME,USERNAME,PASSWORD,EMAIL,ZONE,DOR,TELEPHONE,MOBILE,ADDRESS) VALUES (111111111111113,'Mohammad Mursalin','Mohammad amin','mursalin','','','M.Z','','','0333 8899277','Mohammad Zai');
INSERT INTO customer(CKEY,CNIC,NAME,FATHERNAME,USERNAME,PASSWORD,EMAIL,ZONE,DOR,TELEPHONE,MOBILE,ADDRESS) VALUES (111111111111114,'Dr.Syed Saleh Shah','Pir Wasil Shah','','','','city','','','0922-513513','Gulo Bera Chowk');
INSERT INTO customer(CKEY,CNIC,NAME,FATHERNAME,USERNAME,PASSWORD,EMAIL,ZONE,DOR,TELEPHONE,MOBILE,ADDRESS) VALUES (111111111111115,'Mohammad Munir Khan','Abdul Rauf','305','munir','','TB','','','0336-9905609','Togh Bala Post office As a clerk');
INSERT INTO customer(CKEY,CNIC,NAME,FATHERNAME,USERNAME,PASSWORD,EMAIL,ZONE,DOR,TELEPHONE,MOBILE,ADDRESS) VALUES (111111111111116,'Syed Sohaib Shah','Syed Amjad Shah','sohaib','friend','','WD-3','','','0333 9646344','Jungle Khel');
INSERT INTO customer(CKEY,CNIC,NAME,FATHERNAME,USERNAME,PASSWORD,EMAIL,ZONE,DOR,TELEPHONE,MOBILE,ADDRESS) VALUES (111111111111117,'Abdul manan','Abdurehman','manan','webdoor','','C.T','','','0336 9650979','Sheri vella Jungle khel');
INSERT INTO customer(CKEY,CNIC,NAME,FATHERNAME,USERNAME,PASSWORD,EMAIL,ZONE,DOR,TELEPHONE,MOBILE,ADDRESS) VALUES (111111111111118,'Mohammad Waqas','Mohammad Ilyas','wkhan','0,08','','M.C','','','0332 7039543','Meri colony');
INSERT INTO customer(CKEY,CNIC,NAME,FATHERNAME,USERNAME,PASSWORD,EMAIL,ZONE,DOR,TELEPHONE,MOBILE,ADDRESS) VALUES (111111111111119,'Mohammad Aamir','Nazir Mohammad','aamir','amir007','','M.C','','','0333 9614224','New Aadi Meroz');
INSERT INTO customer(CKEY,CNIC,NAME,FATHERNAME,USERNAME,PASSWORD,EMAIL,ZONE,DOR,TELEPHONE,MOBILE,ADDRESS) VALUES (111111111111110,'bashir  sahil','sarwar Khan','sahil','123','','KDA','','','0336-1921933','KDA Gate No 03 Near CNG Pump');
INSERT INTO customer(CKEY,CNIC,NAME,FATHERNAME,USERNAME,PASSWORD,EMAIL,ZONE,DOR,TELEPHONE,MOBILE,ADDRESS) VALUES (1111111111111111,'Arshid Bukhari','','','','','','','','0332-9648884','Jungel Khail Sheri Villa Kohat');
INSERT INTO customer(CKEY,CNIC,NAME,FATHERNAME,USERNAME,PASSWORD,EMAIL,ZONE,DOR,TELEPHONE,MOBILE,ADDRESS) VALUES (1111111111111112,'Muhammad Abbas','Resaal Khan','312','90007','','C.T','','','0336 9690007',' mer bahadar colony Hango Road');
INSERT INTO customer(CKEY,CNIC,NAME,FATHERNAME,USERNAME,PASSWORD,EMAIL,ZONE,DOR,TELEPHONE,MOBILE,ADDRESS) VALUES (1111111111111113,'Muhammad Haroon','Muhammmad Sharif','umbrella','Usc kohat','','M.z','','','0333 9606905','Ex Floor Mills');
INSERT INTO customer(CKEY,CNIC,NAME,FATHERNAME,USERNAME,PASSWORD,EMAIL,ZONE,DOR,TELEPHONE,MOBILE,ADDRESS) VALUES (1111111111111114,'zia Ur rehmanAftab Rehman ','abdur rehman c/o Gul Rehman','314','zia','','MMZ','','','0333 9688000','ZamZam MED, saif plaza bazaray mustafa');
INSERT INTO customer(CKEY,CNIC,NAME,FATHERNAME,USERNAME,PASSWORD,EMAIL,ZONE,DOR,TELEPHONE,MOBILE,ADDRESS) VALUES (1111111111111115,'Mubashir Khan','Ashraf Khan','mnaz','webdoor','','C.T','','','0331 7171749','Dicoration Hango Road');
INSERT INTO customer(CKEY,CNIC,NAME,FATHERNAME,USERNAME,PASSWORD,EMAIL,ZONE,DOR,TELEPHONE,MOBILE,ADDRESS) VALUES (1111111111111116,'Sajid Mehmood','Fanoos Khan','bangash','zurain','','T.B','','','0313 9782956','Village And Pos');

DROP TABLE PACKAGETOCUST;
CREATE TABLE PACKAGETOCUST (PACKTOCUST_ID INTEGER IDENTITY, WDPACKAGE_ID INTEGER, CUSTOMER_ID INTEGER);
INSERT INTO PACKAGETOCUST (WDPACKAGE_ID,CUSTOMER_ID) VALUES (1, 1);
INSERT INTO PACKAGETOCUST (WDPACKAGE_ID,CUSTOMER_ID) VALUES (2, 1);
INSERT INTO PACKAGETOCUST (WDPACKAGE_ID,CUSTOMER_ID) VALUES (1, 2);
INSERT INTO PACKAGETOCUST (WDPACKAGE_ID,CUSTOMER_ID) VALUES (1, 3);
INSERT INTO PACKAGETOCUST (WDPACKAGE_ID,CUSTOMER_ID) VALUES (1, 4);
INSERT INTO PACKAGETOCUST (WDPACKAGE_ID,CUSTOMER_ID) VALUES (1, 5);
INSERT INTO PACKAGETOCUST (WDPACKAGE_ID,CUSTOMER_ID) VALUES (1, 6);
INSERT INTO PACKAGETOCUST (WDPACKAGE_ID,CUSTOMER_ID) VALUES (1, 7);
INSERT INTO PACKAGETOCUST (WDPACKAGE_ID,CUSTOMER_ID) VALUES (1, 8);
INSERT INTO PACKAGETOCUST (WDPACKAGE_ID,CUSTOMER_ID) VALUES (1, 9);
INSERT INTO PACKAGETOCUST (WDPACKAGE_ID,CUSTOMER_ID) VALUES (1, 10);
INSERT INTO PACKAGETOCUST (WDPACKAGE_ID,CUSTOMER_ID) VALUES (1, 11);
INSERT INTO PACKAGETOCUST (WDPACKAGE_ID,CUSTOMER_ID) VALUES (1, 12);
INSERT INTO PACKAGETOCUST (WDPACKAGE_ID,CUSTOMER_ID) VALUES (1, 13);
INSERT INTO PACKAGETOCUST (WDPACKAGE_ID,CUSTOMER_ID) VALUES (1, 14);
INSERT INTO PACKAGETOCUST (WDPACKAGE_ID,CUSTOMER_ID) VALUES (1, 15);
INSERT INTO PACKAGETOCUST (WDPACKAGE_ID,CUSTOMER_ID) VALUES (1, 16);
INSERT INTO PACKAGETOCUST (WDPACKAGE_ID,CUSTOMER_ID) VALUES (1, 17);

DROP TABLE WDPACKAGE;
CREATE TABLE WDPACKAGE (WDPACKAGE_ID INTEGER IDENTITY, PACKAGENAME VARCHAR(30), PACKAGEDETAILS INTEGER, CREATED Date);
INSERT INTO WDPACKAGE (PACKAGENAME,PACKAGEDETAILS,CREATED) VALUES ('1', 1, SYSDATE());
INSERT INTO WDPACKAGE (PACKAGENAME,PACKAGEDETAILS,CREATED) VALUES ('2', 2, SYSDATE());

DROP TABLE product;
CREATE TABLE product (PRODUCT_ID INTEGER IDENTITY, MONTH VARCHAR(30), ISSUEDATE DATE, DUEDATE DATE, PAYMENT INTEGER, FINE INTEGER, CREATED DATE);
INSERT INTO product (MONTH, ISSUEDATE, DUEDATE, PAYMENT, FINE) VALUES ('JANUARY', '2013-01-01 00:00:00', '2013-01-10 00:00:00', 699, 50);
INSERT INTO product (MONTH, ISSUEDATE, DUEDATE, PAYMENT, FINE) VALUES ('FEBRUARY', '2013-02-01 00:00:00', '2013-02-10 00:00:00', 699, 50);
INSERT INTO product (MONTH, ISSUEDATE, DUEDATE, PAYMENT, FINE) VALUES ('MARCH', '2013-03-01 00:00:00', '2013-03-10 00:00:00', 699, 50);
INSERT INTO product (MONTH, ISSUEDATE, DUEDATE, PAYMENT, FINE) VALUES ('APRIL', '2013-04-01 00:00:00', '2013-04-10 00:00:00', 699, 50);

DROP TABLE invoicetocust;
CREATE TABLE invoicetocust (INTOCUST_ID INTEGER IDENTITY, PAIDAMOUNT INTEGER, PAIDON DATE, STATUS VARCHAR(20), product_PRODUCT_ID INTEGER);
INSERT INTO invoicetocust (PAIDAMOUNT, PAIDON, STATUS, product_PRODUCT_ID) VALUES (749, '2013-01-16 00:00:00', 'PAID', 1);
INSERT INTO invoicetocust (PAIDAMOUNT, PAIDON, STATUS, product_PRODUCT_ID) VALUES (699, '2013-02-10 00:00:00', 'PAID', 2);
INSERT INTO invoicetocust (PAIDAMOUNT, PAIDON, STATUS, product_PRODUCT_ID) VALUES (699, '2013-03-10 00:00:00', 'PAID', 3);
INSERT INTO invoicetocust (PAIDAMOUNT, PAIDON, STATUS, product_PRODUCT_ID) VALUES (749, '2013-01-16 00:00:00', 'PAID', 1);
INSERT INTO invoicetocust (PAIDAMOUNT, PAIDON, STATUS, product_PRODUCT_ID) VALUES (699, '2013-02-10 00:00:00', 'PAID', 2);
INSERT INTO invoicetocust (PAIDAMOUNT, PAIDON, STATUS, product_PRODUCT_ID) VALUES (699, '2013-03-10 00:00:00', 'PAID', 3);


DROP TABLE customer_invoicetocust;
CREATE TABLE customer_invoicetocust (CUSTOMER_CUSTOMER_ID INTEGER, invoices_INTOCUST_ID INTEGER);
INSERT INTO customer_invoicetocust (CUSTOMER_CUSTOMER_ID, invoices_INTOCUST_ID) VALUES (7, 1);
INSERT INTO customer_invoicetocust (CUSTOMER_CUSTOMER_ID, invoices_INTOCUST_ID) VALUES (7, 2);
INSERT INTO customer_invoicetocust (CUSTOMER_CUSTOMER_ID, invoices_INTOCUST_ID) VALUES (7, 3);
INSERT INTO customer_invoicetocust (CUSTOMER_CUSTOMER_ID, invoices_INTOCUST_ID) VALUES (1, 4);
INSERT INTO customer_invoicetocust (CUSTOMER_CUSTOMER_ID, invoices_INTOCUST_ID) VALUES (1, 5);
INSERT INTO customer_invoicetocust (CUSTOMER_CUSTOMER_ID, invoices_INTOCUST_ID) VALUES (1, 6);


load data local infile 'C:/Users/Abid.Ur-Rehman/Desktop/Webdoor/Data.csv' into table customer fields terminated by ';' lines terminated by '\n'