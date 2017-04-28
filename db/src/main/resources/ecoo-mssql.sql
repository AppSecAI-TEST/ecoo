USE [master]
GO

IF  EXISTS (SELECT name FROM sys.databases WHERE name = N'ecoo_bpm')
BEGIN
	DECLARE @sql VARCHAR(MAX)
	
	ALTER DATABASE ecoo_bpm
	SET SINGLE_USER
	WITH ROLLBACK IMMEDIATE;
	ALTER DATABASE ecoo_bpm
	SET MULTI_USER;

	DROP DATABASE [ecoo_bpm]
END
GO

IF  EXISTS (SELECT name FROM sys.databases WHERE name = N'ecoo')
BEGIN
	DECLARE @sql VARCHAR(MAX)
	
	ALTER DATABASE ecoo
	SET SINGLE_USER
	WITH ROLLBACK IMMEDIATE;
	ALTER DATABASE ecoo
	SET MULTI_USER;

	DROP DATABASE [ecoo]
END
GO
CREATE DATABASE [ecoo]   
GO
EXEC dbo.sp_dbcmptlevel @dbname=N'ecoo', @new_cmptlevel=100
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
EXEC [ecoo].[dbo].[sp_fulltext_database] @action = 'enable'
GO
ALTER DATABASE [ecoo] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [ecoo] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [ecoo] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [ecoo] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [ecoo] SET ARITHABORT ON 
GO
ALTER DATABASE [ecoo] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [ecoo] SET AUTO_CREATE_STATISTICS OFF 
GO
ALTER DATABASE [ecoo] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [ecoo] SET AUTO_UPDATE_STATISTICS OFF 
GO
ALTER DATABASE [ecoo] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [ecoo] SET CURSOR_DEFAULT GLOBAL 
GO
ALTER DATABASE [ecoo] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [ecoo] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [ecoo] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [ecoo] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [ecoo] SET  DISABLE_BROKER 
GO
ALTER DATABASE [ecoo] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [ecoo] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [ecoo] SET TRUSTWORTHY ON 
GO
ALTER DATABASE [ecoo] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [ecoo] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [ecoo] SET  READ_WRITE 
GO
ALTER DATABASE [ecoo] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [ecoo] SET  MULTI_USER 
GO	
ALTER DATABASE [ecoo] SET PAGE_VERIFY TORN_PAGE_DETECTION  
GO
ALTER DATABASE [ecoo] SET DB_CHAINING OFF 
GO
ALTER DATABASE [ecoo] COLLATE SQL_Latin1_General_CP1_CI_AS
GO
ALTER DATABASE [ecoo] SET COMPATIBILITY_LEVEL = 100
GO





/****** Object:  Login [ecoo]    Script Date: 01/23/2017 05:05:27 ******/
IF  EXISTS (SELECT * FROM sys.server_principals WHERE name = N'ecoo')
DROP LOGIN [ecoo]
GO


USE [master]
GO
CREATE LOGIN [ecoo] WITH PASSWORD=N'jd3rcJuiWN4sExXB', DEFAULT_DATABASE=[master], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF
GO







USE [ecoo]
GO
IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = N'db_executor')
BEGIN
	CREATE ROLE [db_executor] AUTHORIZATION [dbo]
	GRANT EXECUTE TO [db_executor]
	GRANT EXECUTE ON SCHEMA::[dbo] TO [db_executor]
END
GO

CREATE USER [ecoo] FOR LOGIN [ecoo] WITH DEFAULT_SCHEMA=[dbo]
GO
EXEC sp_addrolemember N'db_datareader', N'ecoo'
GO
EXEC sp_addrolemember N'db_datawriter', N'ecoo'
GO
EXEC sp_addrolemember N'db_executor', N'ecoo'
GO
EXEC sp_addrolemember N'db_ddladmin', N'ecoo'
GO
EXEC master..sp_addsrvrolemember @loginame = N'ecoo', @rolename = N'bulkadmin'
GO

ALTER LOGIN [ecoo] WITH DEFAULT_DATABASE=[ecoo], DEFAULT_LANGUAGE=[us_english], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF
GO

USE [ecoo]
GO


-- =====================================================================================
-- REVISION
-- =====================================================================================
CREATE TABLE "rev_type" (
  "id" tinyint NOT NULL,
  "desrc" varchar(20) NOT NULL,
CONSTRAINT [pk_rev_type] PRIMARY KEY CLUSTERED 
([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

CREATE TABLE "revision" (
  "id" int IDENTITY(1,1) NOT NULL,
  "modified_by" smallint NOT NULL,
  "timestamp" bigint NOT NULL,
  "date_modified" datetime NOT NULL,
CONSTRAINT [pk_revision] PRIMARY KEY CLUSTERED 
([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

INSERT INTO "rev_type" ("id", "desrc") VALUES
(0, 'INSERTED'),
(1, 'UPDATED'),
(2, 'DELETED');


-- =====================================================================================
-- TITLE
-- =====================================================================================
CREATE TABLE "title" (
  "id" varchar(10) NOT NULL,
  "descr" varchar(10) NOT NULL,
CONSTRAINT [pk_title] PRIMARY KEY CLUSTERED 
([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

CREATE TABLE "title_log" (
  "rev" int NOT NULL,
  "revType" tinyint NOT NULL,
	[id] [varchar](10) NOT NULL,
	[descr] [varchar](10) NULL,
CONSTRAINT pk_title_log PRIMARY KEY CLUSTERED 
([rev] ASC,[id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

ALTER TABLE "title_log" ADD CONSTRAINT "fk_title_log_rev" FOREIGN KEY ("rev") REFERENCES "revision" ("id");  
ALTER TABLE "title_log" ADD CONSTRAINT "fk_title_log_rev_type" FOREIGN KEY ("revType") REFERENCES "rev_type" ("id");
GO

INSERT INTO "title" ("id", "descr") VALUES
('ADV', 'ADV'),
('COL', 'COL'),
('DR', 'DR'),
('MISS', 'MISS'),
('MNR', 'MNR'),
('MR', 'MR'),
('MRS', 'MRS'),
('MS', 'MS'),
('PROF', 'PROF'),
('REV', 'REV'),
('SGT', 'SGT'),
('SIR', 'SIR'),
('SISTER', 'SISTER');


-- =====================================================================================
-- COUNTRY
-- =====================================================================================
CREATE TABLE "country" (
  "id" varchar(2) NOT NULL,
  "descr" varchar(20) NOT NULL,
CONSTRAINT [pk_country] PRIMARY KEY CLUSTERED 
([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

INSERT INTO "country" ("id", "descr") VALUES
('AD', 'ANDORRA'),
('AE', 'UNITED ARAB EMIRATES'),
('AF', 'AFGHANISTAN'),
('AG', 'ANTIGUA AND BARBUDA'),
('AI', 'ANGUILLA'),
('AL', 'ALBANIA'),
('AM', 'ARMENIA'),
('AN', 'NETHERLANDS ANTILLES'),
('AO', 'ANGOLA'),
('AR', 'ARGENTINA'),
('AS', 'AMERICAN SAMOA'),
('AT', 'AUSTRIA'),
('AU', 'AUSTRALIA'),
('AW', 'ARUBA'),
('AZ', 'AZERBAIJAN'),
('BB', 'BARBADOS'),
('BD', 'BANGLADESH'),
('BE', 'BELGIUM'),
('BF', 'BURKINA FASO'),
('BG', 'BULGARIA'),
('BH', 'BAHRAIN'),
('BI', 'BURUNDI'),
('BJ', 'BENIN'),
('BM', 'BERMUDA'),
('BN', 'BRUNEIDARUSSALAM'),
('BO', 'BOLIVIA'),
('BR', 'BRAZIL'),
('BS', 'BAHAMAS'),
('BT', 'BHUTAN'),
('BV', 'BOUVET ISLAND'),
('BW', 'BOTSWANA'),
('BY', 'BELARUS'),
('BZ', 'BELIZE'),
('CA', 'CANADA'),
('CD', 'CONGO'),
('CG', 'CONGO'),
('CH', 'SWITZERLAND'),
('CI', 'COTE D`IVOIRE'),
('CK', 'COOK ISLANDS'),
('CL', 'CHILE'),
('CM', 'CAMEROON'),
('CN', 'CHINA'),
('CO', 'COLOMBIA'),
('CR', 'COSTA RICA'),
('CU', 'CUBA'),
('CV', 'CAPE VERDE'),
('CX', 'CHRISTMAS ISLAND'),
('CY', 'CYPRUS'),
('CZ', 'CZECH REPUBLIC'),
('DE', 'GERMANY'),
('DJ', 'DJIBOUTI'),
('DK', 'DENMARK'),
('DM', 'DOMINICA'),
('DO', 'DOMINICAN REPUBLIC'),
('DZ', 'ALGERIA'),
('EC', 'ECUADOR'),
('EE', 'ESTONIA'),
('EG', 'EGYPT'),
('EH', 'WESTERN SAHARA'),
('ER', 'ERITREA'),
('ES', 'SPAIN'),
('ET', 'ETHIOPIA'),
('FI', 'FINLAND'),
('FJ', 'FIJI'),
('FM', 'MICRONESIA'),
('FO', 'FAEROEISLANDS'),
('FR', 'FRANCE'),
('GA', 'GABON'),
('GB', 'UNITED KINGDOM'),
('GD', 'GRENADA'),
('GE', 'GEORGIA'),
('GF', 'FRENCH GUIANA'),
('GH', 'GHANA'),
('GI', 'GIBRALTAR'),
('GL', 'GREENLAND'),
('GM', 'GAMBIA'),
('GN', 'GUINEA'),
('GP', 'GUADELOUPE'),
('GQ', 'EQUATORIAL GUINEA'),
('GR', 'GREECE'),
('GT', 'GUATEMALA'),
('GU', 'GUAM'),
('GW', 'GUINEA-BISSAU'),
('GY', 'GUYANA'),
('HK', 'HONG KONG'),
('HN', 'HONDURAS'),
('HR', 'CROATIA'),
('HT', 'HAITI'),
('HU', 'HUNGARY'),
('ID', 'INDONESIA'),
('IE', 'IRELAND'),
('IL', 'ISRAEL'),
('IN', 'INDIA'),
('IQ', 'IRAQ'),
('IR', 'IRAN'),
('IS', 'ICELAND'),
('IT', 'ITALY'),
('JM', 'JAMAICA'),
('JO', 'JORDAN'),
('JP', 'JAPAN'),
('KE', 'KENYA'),
('KG', 'KYRGYZSTAN'),
('KH', 'CAMBODIA'),
('KI', 'KIRIBATI'),
('KM', 'COMOROS'),
('KP', 'NORTH KOREA'),
('KR', 'SOUTH KOREA'),
('KW', 'KUWAIT'),
('KY', 'CAYMANISLANDS'),
('KZ', 'KAZAKHSTAN'),
('LB', 'LEBANON'),
('LC', 'SAINT LUCIA'),
('LI', 'LIECHTENSTEIN'),
('LK', 'SRI LANKA'),
('LR', 'LIBERIA'),
('LS', 'LESOTHO'),
('LT', 'LITHUANIA'),
('LU', 'LUXEMBOURG'),
('LV', 'LATVIA'),
('MA', 'MOROCCO'),
('MC', 'MONACO'),
('MD', 'MOLDOVA'),
('MG', 'MADAGASCAR'),
('MH', 'MARSHALL ISLANDS'),
('MK', 'MACEDONIA'),
('ML', 'MALI'),
('MM', 'MYANMAR'),
('MN', 'MONGOLIA'),
('MO', 'MACAO'),
('MQ', 'MARTINIQUE'),
('MR', 'MAURITANIA'),
('MS', 'MONTSERRAT'),
('MT', 'MALTA'),
('MU', 'MAURITIUS'),
('MV', 'MALDIVES'),
('MW', 'MALAWI'),
('MX', 'MEXICO'),
('MY', 'MALAYSIA'),
('MZ', 'MOZAMBIQUE'),
('NA', 'NAMIBIA'),
('NC', 'NEW CALEDONIA'),
('NE', 'NIGER'),
('NF', 'NORFOLKISLAND'),
('NG', 'NIGERIA'),
('NI', 'NICARAGUA'),
('NL', 'NETHERLANDS'),
('NO', 'NORWAY'),
('NP', 'NEPAL'),
('NR', 'NAURU'),
('NU', 'NIUE'),
('NZ', 'NEW ZEALAND'),
('OM', 'OMAN'),
('OS', 'Off shore'),
('PA', 'PANAMA'),
('PE', 'PERU'),
('PF', 'FRENCH POLYNESIA'),
('PG', 'PAPUA NEW GUINEA'),
('PH', 'PHILIPPINES'),
('PK', 'PAKISTAN'),
('PL', 'POLAND'),
('PN', 'PITCAIRN'),
('PR', 'PUERTO RICO'),
('PT', 'PORTUGAL'),
('PW', 'PALAU'),
('PY', 'PARAGUAY'),
('QA', 'QATAR'),
('RE', 'REUNION'),
('RO', 'ROMANIA'),
('RU', 'RUSSIAN FEDERATION'),
('RW', 'RWANDA'),
('SA', 'SAUDI ARABIA'),
('SB', 'SOLOMON ISLANDS'),
('SC', 'SEYCHELLES'),
('SD', 'SUDAN'),
('SE', 'SWEDEN'),
('SG', 'SINGAPORE'),
('SH', 'SAINT HELENA'),
('SI', 'SLOVENIA'),
('SK', 'SLOVAKIA'),
('SL', 'SIERRA LEONE'),
('SN', 'SENEGAL'),
('SO', 'SOMALIA'),
('SR', 'SURINAME'),
('SV', 'EL SALVADOR'),
('SY', 'SYRIAN ARAB REPUBLIC'),
('SZ', 'SWAZILAND'),
('TD', 'CHAD'),
('TG', 'TOGO'),
('TH', 'THAILAND'),
('TJ', 'TAJIKISTAN'),
('TK', 'TOKELAU'),
('TM', 'TURKMENISTAN'),
('TN', 'TUNISIA'),
('TO', 'TONGA'),
('TP', 'EAST TIMOR'),
('TR', 'TURKEY'),
('TT', 'TRINIDAD AND TOBAGO'),
('TV', 'TUVALU'),
('TW', 'TAIWAN'),
('TZ', 'TANZANIA'),
('UA', 'UKRAINE'),
('UG', 'UGANDA'),
('US', 'UNITED STATES'),
('UY', 'URUGUAY'),
('UZ', 'UZBEKISTAN'),
('VE', 'VENEZUELA'),
('VG', 'VIRGIN ISLANDS (GB)'),
('VI', 'VIRGIN ISLANDS (USA)'),
('VN', 'VIET NAM'),
('VU', 'VANUATU'),
('WF', 'WALLIS AND FUTUNA'),
('WS', 'SAMOA'),
('YE', 'YEMEN'),
('YT', 'MAYOTTE'),
('YU', 'YUGOSLAVIA'),
('ZA', 'SOUTH AFRICA'),
('ZM', 'ZAMBIA'),
('ZW', 'ZIMBABWE');

CREATE TABLE "country_log" (
  "rev" int NOT NULL,
  "revType" tinyint NOT NULL,
  "id" varchar(2) NOT NULL,
  "descr" varchar(20) NULL,
CONSTRAINT [pk_country_log] PRIMARY KEY CLUSTERED 
([rev] ASC,[id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

-- =====================================================================================
-- PROVINCE
-- =====================================================================================
CREATE TABLE "province" (
  "id" int NOT NULL,
  "descr" varchar(20) NOT NULL,
CONSTRAINT [pk_province] PRIMARY KEY CLUSTERED 
([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

INSERT INTO "province" ("id", "descr") VALUES
(1, 'OTHER'),
(2, 'EASTERN CAPE'),
(3, 'FREE STATE'),
(4, 'GAUTENG'),
(5, 'KWAZULU-NATAL'),
(6, 'LIMPOPO'),
(7, 'MPUMALANGA'),
(8, 'NORTH WEST'),
(9, 'NORTHERN CAPE'),
(10, 'WESTERN CAPE');

CREATE TABLE "province_log" (
  "rev" int NOT NULL,
  "revType" tinyint NOT NULL,
  "id" int NOT NULL,
  "descr" varchar(20) NULL,
CONSTRAINT [pk_province_log] PRIMARY KEY CLUSTERED 
([rev] ASC,[id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

ALTER TABLE "province_log" ADD CONSTRAINT "fk_province_log_rev" FOREIGN KEY ("rev") REFERENCES "revision" ("id");
ALTER TABLE "province_log" ADD CONSTRAINT "fk_province_log_rev_type" FOREIGN KEY ("revType") REFERENCES "rev_type" ("id");
GO


-- =====================================================================================
-- DOC TYPE
-- =====================================================================================
CREATE TABLE [dbo].doc_type(
	[id] [varchar](3) NOT NULL,
	[descr] [varchar](50) NOT NULL,
CONSTRAINT [pk_doc_type] PRIMARY KEY CLUSTERED 
([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

INSERT INTO "doc_type" ("id", "descr") VALUES
('PCR', 'PROOF OF COMPANY REGISTRATION');

CREATE TABLE "doc_type_log" (
  "rev" int NOT NULL,
  "revType" tinyint NOT NULL,
	[id] [varchar](3) NOT NULL,
	[descr] [varchar](50) NULL,
CONSTRAINT pk_doc_type_log PRIMARY KEY CLUSTERED 
([rev] ASC,[id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

ALTER TABLE "doc_type_log" ADD CONSTRAINT "fk_doc_type_log_rev" FOREIGN KEY ("rev") REFERENCES "revision" ("id");  
ALTER TABLE "doc_type_log" ADD CONSTRAINT "fk_doc_type_log_rev_type" FOREIGN KEY ("revType") REFERENCES "rev_type" ("id");
GO


-- =====================================================================================
-- CHAMBER
-- =====================================================================================
CREATE TABLE "chamber" (
  "id" smallint IDENTITY(1,1) NOT NULL,
  "name" varchar(200) NOT NULL,
  building varchar(50) NOT NULL,
  street varchar(100) NOT NULL,
  city varchar(100) NOT NULL,
  postcode varchar(10) NOT NULL,
  province_id int NOT NULL,
  country_id varchar(2) NOT NULL,
  phone_no varchar(100) NOT NULL,
  email varchar(100) NOT NULL,
CONSTRAINT [pk_chamber] PRIMARY KEY CLUSTERED 
([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

ALTER TABLE "chamber" ADD CONSTRAINT "fk_chamber_province" FOREIGN KEY ("province_id") REFERENCES "province" ("id");  
ALTER TABLE "chamber" ADD CONSTRAINT "fk_chamber_country" FOREIGN KEY ("country_id") REFERENCES "country" ("id");
GO

INSERT INTO "chamber" VALUES ('CHAMBER OF RANDBURG','building','street','city','postcode','4','ZA','0119690000','randburg@hotmail.com');
GO

CREATE TABLE "chamber_log" (
  "rev" int NOT NULL,
  "revType" tinyint NOT NULL,
  "id" smallint NOT NULL,
  "name" varchar(200) NULL,
  building varchar(50) NULL,
  street varchar(100) NULL,
  city varchar(100) NULL,
  postcode varchar(10) NULL,
  province_id int NULL,
  country_id varchar(2) NULL,
  phone_no varchar(100) NULL,
  email varchar(100) NULL,
CONSTRAINT pk_chamber_log PRIMARY KEY CLUSTERED 
([rev] ASC,[id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

ALTER TABLE "chamber_log" ADD CONSTRAINT "fk_chamber_log_rev" FOREIGN KEY ("rev") REFERENCES "revision" ("id");  
ALTER TABLE "chamber_log" ADD CONSTRAINT "fk_chamber_log_type" FOREIGN KEY ("revType") REFERENCES "rev_type" ("id");
GO

-- =====================================================================================
-- COMPANY
-- =====================================================================================
CREATE TABLE [dbo].company_status(
	[id] [varchar](3) NOT NULL,
	[descr] [varchar](50) NOT NULL,
CONSTRAINT pk_company_status PRIMARY KEY CLUSTERED 
([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

INSERT INTO "company_status" ("id", "descr") VALUES
('PA', 'Pending Approval'),
('A', 'Approved'),
('D', 'Declined');
GO

CREATE TABLE [dbo].company_type(
	[id] [varchar](3) NOT NULL,
	[descr] [varchar](50) NOT NULL,
CONSTRAINT pk_company_type PRIMARY KEY CLUSTERED 
([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

INSERT INTO "company_type" ("id", "descr") VALUES
('E', 'EXPORTER'),
('FF', 'FREIGHT FORWARDER');

CREATE TABLE "company_type_log" (
  "rev" int NOT NULL,
  "revType" tinyint NOT NULL,
	[id] [varchar](3) NOT NULL,
	[descr] [varchar](50) NULL,
CONSTRAINT pk_company_type_log PRIMARY KEY CLUSTERED 
([rev] ASC,[id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

ALTER TABLE "company_type_log" ADD CONSTRAINT "fk_company_type_log_rev" FOREIGN KEY ("rev") REFERENCES "revision" ("id");  
ALTER TABLE "company_type_log" ADD CONSTRAINT "fk_company_type_log_type" FOREIGN KEY ("revType") REFERENCES "rev_type" ("id");
GO

CREATE TABLE company (
  id int IDENTITY(1,1) NOT NULL,
  name varchar(200) NOT NULL,
  registration_no varchar(50) NOT NULL,
  vat_no varchar(50) NULL,
  ucr_no varchar(50) NULL,
  type_id varchar(3) NOT NULL,
  building varchar(50) NOT NULL,
  street varchar(100) NOT NULL,
  city varchar(100) NOT NULL,
  postcode varchar(10) NOT NULL,
  province_id int NOT NULL,
  country_id varchar(2) NOT NULL,
  phone_no varchar(100) NOT NULL,
  email varchar(255) NOT NULL,
  status varchar(3) NOT NULL,
CONSTRAINT [pk_company] PRIMARY KEY CLUSTERED 
([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

ALTER TABLE "company" ADD CONSTRAINT "fk_company_province" FOREIGN KEY ("province_id") REFERENCES "province" ("id");  
ALTER TABLE "company" ADD CONSTRAINT "fk_company_country" FOREIGN KEY ("country_id") REFERENCES "country" ("id");    
ALTER TABLE "company" ADD CONSTRAINT "fk_company_company_type" FOREIGN KEY ("type_id") REFERENCES "company_type" ("id");  
ALTER TABLE "company" ADD CONSTRAINT "fk_company_company_status" FOREIGN KEY ("status") REFERENCES "company_status" ("id");  
GO

CREATE TABLE "company_log" (
  "rev" int NOT NULL,
  "revType" tinyint NOT NULL,
  id int NOT NULL,
  name varchar(200) NULL,
  registration_no varchar(50) NULL,
  vat_no varchar(50) NULL,
  ucr_no varchar(50) NULL,
  type_id varchar(3) NULL,
  building varchar(50) NULL,
  street varchar(100) NULL,
  city varchar(100) NULL,
  postcode varchar(10) NULL,
  province_id int NULL,
  country_id varchar(2) NULL,
  phone_no varchar(100) NULL,
  email varchar(255) NULL,
  status varchar(3) NULL,
CONSTRAINT pk_company_log PRIMARY KEY CLUSTERED 
([rev] ASC,[id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

ALTER TABLE "company_log" ADD CONSTRAINT "fk_company_log_rev" FOREIGN KEY ("rev") REFERENCES "revision" ("id");  
ALTER TABLE "company_log" ADD CONSTRAINT "fk_company_log_rev_type" FOREIGN KEY ("revType") REFERENCES "rev_type" ("id");
GO

CREATE TABLE company_doc (
  "id" int IDENTITY(1,1) NOT NULL,
  "company_id" int NOT NULL,
  "path" varchar(512) NOT NULL,
  "file_name" varchar(50) NOT NULL,
  "doc_type" varchar(3) NOT NULL,
  "mime_type" varchar(20) NOT NULL,
  "size_in_kb" smallint NOT NULL,
  "date_created" datetime NOT NULL,
CONSTRAINT [pk_company_doc] PRIMARY KEY CLUSTERED 
([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

ALTER TABLE [dbo].company_doc  WITH CHECK ADD CONSTRAINT [fk_company_doc_doc_type] FOREIGN KEY(doc_type) REFERENCES [dbo].doc_type(id);
ALTER TABLE [dbo].company_doc  WITH CHECK ADD CONSTRAINT [fk_company_doc_company] FOREIGN KEY(company_id) REFERENCES [dbo].company(id);
GO

CREATE TABLE "company_doc_log" (
  "rev" int NOT NULL,
  "revType" tinyint NOT NULL,
  id int NOT NULL,
  "company_id" int NULL,
  "path" varchar(512) NULL,
  "file_name" varchar(50) NULL,
  "doc_type" varchar(3) NULL,
  "mime_type" varchar(20) NULL,
  "size_in_kb" smallint NULL,
  "date_created" datetime NULL,
CONSTRAINT pk_company_doc_log PRIMARY KEY CLUSTERED 
([rev] ASC,[id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

ALTER TABLE "company_doc_log" ADD CONSTRAINT "fk_company_doc_log_rev" FOREIGN KEY ("rev") REFERENCES "revision" ("id");  
ALTER TABLE "company_doc_log" ADD CONSTRAINT "fk_company_doc_log_rev_type" FOREIGN KEY ("revType") REFERENCES "rev_type" ("id");
GO


-- =====================================================================================
-- ENDPOINT
-- =====================================================================================
CREATE TABLE "endpoint" (
  "id" int IDENTITY(1,1) NOT NULL,
  "name" varchar(50) NOT NULL,
  "url" varchar(255) NOT NULL,
  "requested_time" datetime NOT NULL,
  "response" varchar(8000) NOT NULL,
  "status" varchar(4) NOT NULL,
CONSTRAINT [pk_endpoint] PRIMARY KEY CLUSTERED 
([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

SET IDENTITY_INSERT "endpoint" ON ;
INSERT INTO "endpoint" ("id", "name", "url", "requested_time", "response", "status") VALUES
(1, 'Gateway API/1', 'http://localhost:7777/health', '2016-08-29 20:23:54', '{"status":"UP","diskSpace":{"status":"UP","total":240054693888,"free":8417980416,"threshold":10485760},"db":{"status":"UP","database":"MySQL","hello":1}}', 'UP');
--(2, 'Workflow API/1', 'http://localhost:3333/bpm-ws/health', '2016-08-29 20:23:54', '{"status":"UP","diskSpace":{"status":"UP","total":240054693888,"free":8417980416,"threshold":10485760},"db":{"status":"UP","database":"MySQL","hello":1}}', 'UP'),
--(3, 'Claim Upload Processor API/1', 'http://localhost:2223/claim-uploadprocessor-ws/health', '2016-08-29 20:23:54', '{"status":"UP","diskSpace":{"status":"UP","total":240054693888,"free":8417980416,"threshold":10485760},"db":{"status":"UP","database":"MySQL","hello":1}}', 'UP'),
--(4, 'Metric Processor API/1', 'http://localhost:2225/metricprocessor-ws/health', '2016-08-29 20:23:54', '{"status":"UP","diskSpace":{"status":"UP","total":240054693888,"free":8417980416,"threshold":10485760},"db":{"status":"UP","database":"MySQL","hello":1}}', 'UP');
SET IDENTITY_INSERT "endpoint" OFF;

CREATE TABLE "endpoint_stat" (
  "id" int IDENTITY(1,1) NOT NULL,
  "endpoint_id" int NOT NULL,
  "requested_time" date NOT NULL,
  "response" varchar(8000) NOT NULL,
  "status" varchar(4) NOT NULL,
CONSTRAINT [pk_endpoint_stat] PRIMARY KEY CLUSTERED 
([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

SET IDENTITY_INSERT "endpoint_stat" ON ;
INSERT INTO "endpoint_stat" ("id", "endpoint_id", "requested_time", "response", "status") VALUES
(1, 1, '2016-08-29', '{"status":"UP","diskSpace":{"status":"UP","total":240054693888,"free":8420458496,"threshold":10485760},"db":{"status":"UP","database":"MySQL","hello":1}}', 'UP');
SET IDENTITY_INSERT "endpoint_stat" OFF;


-- =====================================================================================
-- FEATURE
-- =====================================================================================
CREATE TABLE "feature" (
  "id" tinyint IDENTITY(1,1) NOT NULL,
  "name" varchar(50) NOT NULL,
  "value" varchar(1024) NOT NULL,
  "descr" varchar(100) NOT NULL,
CONSTRAINT [pk_feature] PRIMARY KEY CLUSTERED 
([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

SET IDENTITY_INSERT "feature" ON;
INSERT INTO "feature" ("id", "name", "value", "descr") VALUES
(0, 'APP_HOME', 'C:\\Users\\Justin\\.ecoo', 'The application home directory'),
(1, 'SMTP_SERVER', '', 'The SMTP server either host name or IP address');
SET IDENTITY_INSERT "feature" OFF;

CREATE TABLE "feature_log" (
  "rev" int NOT NULL,
  "revType" tinyint NOT NULL,
  "id" tinyint NOT NULL,
  "name" varchar(50) NULL,
  "value" varchar(1024) NULL,
  "descr" varchar(100) NULL,
CONSTRAINT [pk_feature_log] PRIMARY KEY CLUSTERED 
([rev] ASC,[id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO



-- =====================================================================================
-- SYSTEM JOB
-- =====================================================================================
CREATE TABLE "system_job" (
  "id" int IDENTITY(1,1) NOT NULL,
  "class_name" varchar(1024) NOT NULL,
  "start_time" datetime NOT NULL,
  "end_time" datetime NULL,
  "processing_time" int NULL,
  "successful_ind" char(1) NOT NULL,
  "comment" varchar(512) NULL,
CONSTRAINT [pk_system_job] PRIMARY KEY CLUSTERED 
([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO


-- =====================================================================================
-- USER
-- =====================================================================================
CREATE TABLE "user_acc" (
  "id" int IDENTITY(1,1) NOT NULL,
  "title" varchar(10) NULL,
  "first_name" varchar(100) NOT NULL,
  "last_name" varchar(100) NOT NULL,
  "display_name" varchar(200) NOT NULL,
  "primary_email_address" varchar(255) NULL,
  "mobile_no" varchar(15) NULL,
  "prefer_comm_type" varchar(2) NULL,
  "username" varchar(100) NOT NULL,
  "password" varchar(50) NULL,
  "personal_ref_type" varchar(3) NULL,
  "personal_ref_value" varchar(50) NULL,
  "account_non_expired" char(1) DEFAULT 1,
  "account_non_locked" char(1) DEFAULT 1,
  "credentials_non_expired" char(1) DEFAULT 1,
  "enabled" char(1) DEFAULT 1,
  "reserved" char(1) DEFAULT 0,
  "password_expired" char(1) DEFAULT 1,
  "activation_serial_no" varchar(40) NULL,
  "last_login_time" datetime NULL,
  "status" varchar(3) NOT NULL,
  "company_id" int NULL,
CONSTRAINT [pk_user_acc] PRIMARY KEY CLUSTERED 
([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

CREATE TABLE "user_acc_log" (
  "rev" int NOT NULL,
  "revType" tinyint NOT NULL,
  "id" int NOT NULL,
  "title" varchar(10) NULL,
  "first_name" varchar(100) NULL,
  "last_name" varchar(100) NULL,
  "display_name" varchar(200) NOT NULL,
  "primary_email_address" varchar(255) NULL,
  "mobile_no" varchar(15) NULL,
  "prefer_comm_type" varchar(2) NULL,
  "username" varchar(100) NULL,
  "password" varchar(50) NULL,
  "personal_ref_type" varchar(3) NULL,
  "personal_ref_value" varchar(50) NULL,
  "account_non_expired" char(1) NULL,
  "account_non_locked" char(1) NULL,
  "credentials_non_expired" char(1) NULL,
  "enabled" char(1) NULL,
  "reserved" char(1) NULL,
  "password_expired" char(1) NULL,
  "activation_serial_no" varchar(40) NULL,
  "last_login_time" datetime NULL,
  "status" varchar(3) NULL,
  "company_id" int NULL,
CONSTRAINT [pk_user_acc_log] PRIMARY KEY CLUSTERED 
([rev] ASC,[id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

CREATE TABLE [dbo].comm_type(
	[id] [varchar](2) NOT NULL,
	[descr] [varchar](30) NULL,
CONSTRAINT [pk_comm_type] PRIMARY KEY CLUSTERED 
([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

SET ANSI_PADDING OFF
GO

CREATE TABLE "comm_type_log" (
  "rev" int NOT NULL,
  "revType" tinyint NOT NULL,
	[id] [varchar](2) NOT NULL,
	[descr] [varchar](30) NULL,
CONSTRAINT pk_comm_type_log PRIMARY KEY CLUSTERED 
([rev] ASC,[id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

ALTER TABLE "comm_type_log" ADD CONSTRAINT "fk_comm_type_log_rev" FOREIGN KEY ("rev") REFERENCES "revision" ("id");  
ALTER TABLE "comm_type_log" ADD CONSTRAINT "fk_comm_type_log_rev_type" FOREIGN KEY ("revType") REFERENCES "rev_type" ("id");
GO

ALTER TABLE [dbo].[user_acc]  WITH NOCHECK ADD  CONSTRAINT [fk_user_comm_type] FOREIGN KEY(prefer_comm_type)
REFERENCES [dbo].comm_type(id)
GO

ALTER TABLE [dbo].[user_acc] NOCHECK CONSTRAINT [fk_user_comm_type]
GO

INSERT INTO "comm_type" ("id", "descr") VALUES
('S', 'SMS'),
('E', 'EMAIL');


CREATE TABLE [dbo].personal_ref_type(
	[id] [varchar](3) NOT NULL,
	[descr] [varchar](30) NOT NULL,
CONSTRAINT [pk_personal_ref_type] PRIMARY KEY CLUSTERED 
([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

CREATE TABLE "personal_ref_type_log" (
  "rev" int NOT NULL,
  "revType" tinyint NOT NULL,
	[id] [varchar](3) NOT NULL,
	[descr] [varchar](30) NULL,
CONSTRAINT pk_personal_ref_type_log PRIMARY KEY CLUSTERED 
([rev] ASC,[id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

ALTER TABLE "personal_ref_type_log" ADD CONSTRAINT "fk_personal_ref_type_log_rev" FOREIGN KEY ("rev") REFERENCES "revision" ("id");  
ALTER TABLE "personal_ref_type_log" ADD CONSTRAINT "fk_personal_ref_type_log_rev_type" FOREIGN KEY ("revType") REFERENCES "rev_type" ("id");
GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[user_acc]  WITH NOCHECK ADD  CONSTRAINT [fk_user_personal_ref_type] FOREIGN KEY(personal_ref_type)
REFERENCES [dbo].personal_ref_type(id)
GO

ALTER TABLE [dbo].[user_acc] NOCHECK CONSTRAINT [fk_user_personal_ref_type]
GO

INSERT INTO "personal_ref_type" ("id", "descr") VALUES
('RSA', 'SOUTH AFRICAN ID'),
('P', 'PASSPORT'),
('OTH', 'OTHER');

CREATE TABLE [dbo].user_status(
	[id] [varchar](3) NOT NULL,
	[descr] [varchar](30) NULL,
CONSTRAINT [pk_user_status] PRIMARY KEY CLUSTERED 
([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[user_acc]  WITH NOCHECK ADD  CONSTRAINT [fk_user_user_status] FOREIGN KEY([status])
REFERENCES [dbo].user_status(id)
GO

ALTER TABLE [dbo].[user_acc] NOCHECK CONSTRAINT [fk_user_user_status]
GO

ALTER TABLE [dbo].[user_acc]  WITH NOCHECK ADD  CONSTRAINT [fk_user_title] FOREIGN KEY([title])
REFERENCES [dbo].title(id)
GO

ALTER TABLE [dbo].[user_acc] NOCHECK CONSTRAINT [fk_user_title]
GO

INSERT INTO "user_status" ("id", "descr") VALUES
('PA', 'Pending Approval'),
('A', 'Approved'),
('D', 'Declined');

SET IDENTITY_INSERT "user_acc" ON ;
INSERT INTO "user_acc" ("id", "title", "first_name", "last_name", "display_name", "primary_email_address", "mobile_no", "prefer_comm_type", "username", "password", "personal_ref_type", "personal_ref_value", "account_non_expired", "account_non_locked", "credentials_non_expired", "enabled", "reserved", "password_expired", "activation_serial_no", "last_login_time", "status", "company_id") VALUES
(-99  ,NULL  ,'ANONYMOUS'  ,'ANONYMOUS'  ,'ANONYMOUS'  ,'no-reply@ecoo.co.za'  ,NULL  ,NULL  ,'anonymous'  ,'21232f297a57a5a743894a0e4a801fc3'  ,NULL  ,NULL ,1  ,1  ,1  ,1  ,0  ,0  ,''  ,NULL  ,'A'  ,0),
(-98  ,NULL  ,'SYSTEM'  ,'ACCOUNT'  ,'SYSTEM ACCOUNT'  ,NULL  ,NULL  ,NULL  ,'sysaccount'  ,'21232f297a57a5a743894a0e4a801fc3'  ,NULL ,NULL  ,1  ,1  ,1  ,1  ,0  ,0  ,''  ,NULL  ,'A'  ,0),
(-97  ,NULL  ,'BATCH PROCESSOR'  ,'ACCOUNT'  ,'BATCH PROCESSOR ACCOUNT'  ,NULL  ,NULL  ,NULL  ,'batch'  ,'21232f297a57a5a743894a0e4a801fc3'  ,NULL ,NULL  ,1  ,1  ,1  ,1  ,0  ,0  ,''  ,NULL  ,'A'  ,0),
(1  ,NULL  ,'SYSTEM'  ,'ADMINISTRATION'  ,'SYSTEM ADMINISTRATION'  ,'system@s-squared.co.za'  ,NULL  ,NULL  ,'admin'  ,'21232f297a57a5a743894a0e4a801fc3'  ,'OTH'  ,'admin' ,1  ,1  ,1  ,1  ,1  ,0  ,'a4e6d213-c745-4152-94e2-99577bfb360d'  ,NULL  ,'A'  ,0);
SET IDENTITY_INSERT "user_acc" OFF;

CREATE TABLE "user_signature" (
  "id" int IDENTITY(1,1) NOT NULL,
  "user_id" int NOT NULL,
  "encoded_image" varchar(max) NOT NULL,
  "eff_from_date" datetime NOT NULL,
  "eff_to_date" datetime NOT NULL,
CONSTRAINT [pk_user_signature] PRIMARY KEY CLUSTERED 
([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO


ALTER TABLE [dbo].user_signature  WITH NOCHECK ADD  CONSTRAINT [fk_user_signature_user] FOREIGN KEY(user_id) REFERENCES [dbo].user_acc(id)
GO

ALTER TABLE [dbo].user_signature NOCHECK CONSTRAINT [fk_user_signature_user]
GO


CREATE TABLE [dbo].[user_signature_log](
	[rev] int NOT NULL,
	[revType] tinyint NOT NULL,
	[id] [int] NOT NULL,
	[user_id] int NULL,
  "encoded_image" varchar(max) NULL,
  "eff_from_date" datetime NULL,
  "eff_to_date" datetime NULL,
 CONSTRAINT [pk_user_signature_log] PRIMARY KEY CLUSTERED 
(
	[rev] ASC,
	[id] ASC
)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[user_signature_log]  WITH NOCHECK ADD  CONSTRAINT [fk_user_signature_log_revision] FOREIGN KEY([rev]) REFERENCES [dbo].[revision] ([id])
GO

ALTER TABLE [dbo].[user_signature_log] CHECK CONSTRAINT [fk_user_signature_log_revision]
GO

CREATE TABLE "user_role" (
  "id" int IDENTITY(1,1) NOT NULL,
  "user_id" int NOT NULL,
  "role" varchar(20) NOT NULL,
CONSTRAINT [pk_user_role] PRIMARY KEY CLUSTERED 
([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

CREATE TABLE "user_role_log" (
  "rev" int NOT NULL,
  "revType" tinyint NOT NULL,
  "id" int NOT NULL,
  "user_id" int NULL,
  "role" varchar(20) NULL,
CONSTRAINT pk_user_role_log PRIMARY KEY CLUSTERED 
([rev] ASC,[id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

ALTER TABLE "user_role_log" ADD CONSTRAINT "fk_user_role_log_rev" FOREIGN KEY ("rev") REFERENCES "revision" ("id");  
ALTER TABLE "user_role_log" ADD CONSTRAINT "fk_user_role_log_rev_type" FOREIGN KEY ("revType") REFERENCES "rev_type" ("id");
GO

SET IDENTITY_INSERT "user_role" ON ;
INSERT INTO "user_role" ("id", "user_id", "role") VALUES
(1, 1, 'ROLE_SYSADMIN'); -- admin
SET IDENTITY_INSERT "user_role" OFF;

ALTER TABLE "user_role" ADD CONSTRAINT "fk_user_role_user" FOREIGN KEY ("user_id") REFERENCES [user_acc]("id") ON DELETE CASCADE;

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING OFF
GO

CREATE TABLE "chamber_user" (
  id int IDENTITY(1,1) NOT NULL,
  chamber_id smallint NOT NULL,
  user_id int NOT NULL,
  start_date datetime NOT NULL,
  end_date datetime NOT NULL,
CONSTRAINT [pk_chamber_user] PRIMARY KEY CLUSTERED 
([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

ALTER TABLE "chamber_user" ADD CONSTRAINT "fk_chamber_user_chamber" FOREIGN KEY (chamber_id) REFERENCES chamber ("id");  
ALTER TABLE "chamber_user" ADD CONSTRAINT "fk_chamber_user_user" FOREIGN KEY (user_id) REFERENCES user_acc ("id");
GO


CREATE TABLE chamber_user_log (
  "rev" int NOT NULL,
  "revType" tinyint NOT NULL,
  id int NOT NULL,
  chamber_id smallint NOT NULL,
  user_id int NULL,
  start_date datetime NULL,
  end_date datetime NULL,
CONSTRAINT pk_chamber_user_log PRIMARY KEY CLUSTERED 
([rev] ASC,[id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

ALTER TABLE chamber_user_log ADD CONSTRAINT "fk_chamber_user_log_rev" FOREIGN KEY ("rev") REFERENCES "revision" ("id");  
ALTER TABLE chamber_user_log ADD CONSTRAINT "fk_chamber_user_log_rev_type" FOREIGN KEY ("revType") REFERENCES "rev_type" ("id");
GO


CREATE TABLE "chamber_admin" (
  id int IDENTITY(1,1) NOT NULL,
  chamber_id smallint NOT NULL,
  user_id int NOT NULL,
  start_date datetime NOT NULL,
  end_date datetime NOT NULL,
CONSTRAINT [pk_chamber_admin] PRIMARY KEY CLUSTERED 
([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

ALTER TABLE chamber_admin ADD CONSTRAINT "fk_chamber_admin_chamber" FOREIGN KEY (chamber_id) REFERENCES chamber ("id");  
ALTER TABLE chamber_admin ADD CONSTRAINT "fk_chamber_admin_user" FOREIGN KEY (user_id) REFERENCES user_acc ("id");
GO

INSERT INTO chamber_admin VALUES(1,1,GETDATE(),'9999/12/31');
GO

CREATE TABLE chamber_admin_log (
  "rev" int NOT NULL,
  "revType" tinyint NOT NULL,
  id int NOT NULL,
  chamber_id smallint NOT NULL,
  user_id int NULL,
  start_date datetime NULL,
  end_date datetime NULL,
CONSTRAINT pk_chamber_admin_log PRIMARY KEY CLUSTERED 
([rev] ASC,[id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

ALTER TABLE chamber_admin_log ADD CONSTRAINT "fk_chamber_admin_log_rev" FOREIGN KEY ("rev") REFERENCES "revision" ("id");  
ALTER TABLE chamber_admin_log ADD CONSTRAINT "fk_chamber_admin_log_rev_type" FOREIGN KEY ("revType") REFERENCES "rev_type" ("id");
GO

-- =====================================================================================
-- METRIC
-- =====================================================================================
CREATE TABLE [dbo].[metric_type](
	[id] [varchar](3) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[descr] [varchar](255) NOT NULL,
 CONSTRAINT [pk_metric_type] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]
) ON [PRIMARY]
GO

INSERT INTO [dbo].[metric_type] ("id", "name", "descr") VALUES
('EXP', 'Test','The description.');

SET ANSI_PADDING OFF
GO


SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING OFF
GO

CREATE TABLE [dbo].[metric](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[user_id] int NULL,
	[type_id] [varchar](3) NOT NULL,
	[value] [varchar](50) NOT NULL,
	[last_updated] [datetime] NULL,
 CONSTRAINT [pk_metric] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]
) ON [PRIMARY]
GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[metric]  WITH NOCHECK ADD  CONSTRAINT [fk_metric_metric_type] FOREIGN KEY([type_id])
REFERENCES [dbo].[metric_type] ([id])
GO

ALTER TABLE [dbo].[metric] CHECK CONSTRAINT [fk_metric_metric_type]
GO

ALTER TABLE [dbo].[metric]  WITH NOCHECK ADD  CONSTRAINT [fk_metric_user_acc] FOREIGN KEY([user_id])
REFERENCES [dbo].[user_acc] ([id])
GO

ALTER TABLE [dbo].[metric] CHECK CONSTRAINT [fk_metric_user_acc]
GO


CREATE TABLE [dbo].[metric_log](
	[rev] int NOT NULL,
	[revType] tinyint NOT NULL,
	[id] [int] NOT NULL,
	[user_id] [smallint] NULL,
	[type_id] [varchar](3) NULL,
	[value] [varchar](50) NULL,
	[last_updated] [datetime] NULL,
 CONSTRAINT [pk_metric_log] PRIMARY KEY CLUSTERED 
(
	[rev] ASC,
	[id] ASC
)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[metric_log]  WITH NOCHECK ADD  CONSTRAINT [fk_metric_log_revision] FOREIGN KEY([rev])
REFERENCES [dbo].[revision] ([id])
GO

ALTER TABLE [dbo].[metric_log] CHECK CONSTRAINT [fk_metric_log_revision]
GO


--
-- Indexes for table "feature"
--  
ALTER TABLE [dbo].feature ADD  CONSTRAINT ux_feature_name UNIQUE NONCLUSTERED (name ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]
GO

--
-- Constraints for table "country_log"
--
ALTER TABLE "country_log"
  ADD CONSTRAINT "fk_country_log_rev" FOREIGN KEY ("rev") REFERENCES "revision" ("id");
  
ALTER TABLE "country_log"
  ADD CONSTRAINT "fk_country_log_rev_type" FOREIGN KEY ("revType") REFERENCES "rev_type" ("id");

--
-- Constraints for table "endpoint_stat"
--
ALTER TABLE "endpoint_stat"
  ADD CONSTRAINT "fk_endpoint_stat_endpoint" FOREIGN KEY ("endpoint_id") REFERENCES "endpoint" ("id");

--
-- Constraints for table "feature_log"
--
ALTER TABLE "feature_log"
  ADD CONSTRAINT "fk_feature_log_rev" FOREIGN KEY ("rev") REFERENCES "revision" ("id");
  
ALTER TABLE "feature_log"
  ADD CONSTRAINT "fk_feature_log_rev_type" FOREIGN KEY ("revType") REFERENCES "rev_type" ("id");



-- =====================================================================================
-- UPLOAD
-- =====================================================================================
CREATE TABLE [dbo].[upload_type](
	[id] [varchar](3) NOT NULL,
	[name] [varchar](50) NOT NULL,
	last_upload_date [datetime] NULL,
 CONSTRAINT [pk_upload_type] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY],
 CONSTRAINT [UC_upload_type] UNIQUE NONCLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]
) ON [PRIMARY]

GO


CREATE TABLE [dbo].[upload_status](
	[id] [tinyint] NOT NULL,
	[descr] [varchar](30) NOT NULL,
 CONSTRAINT [pk_upload_status] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

CREATE TABLE [dbo].[upload_map](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	upload_type_id [varchar](3) NOT NULL,
	name [varchar](100) NOT NULL,
	has_heading [bit] NOT NULL,
 CONSTRAINT [pk_upload_map] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].upload_map ADD CONSTRAINT ux_upload_map_001 UNIQUE NONCLUSTERED ([upload_type_id] ASC,[name] ASC)
WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[upload_map_detail](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	map_id [int] NOT NULL,
	csv_column_index [int] NULL,
	table_column_name [varchar](50) NULL,
 CONSTRAINT [pk_upload_map_detail] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[upload_map_detail]  WITH NOCHECK ADD  CONSTRAINT [fk_upload_map_detail_upload_map] FOREIGN KEY(map_id)
REFERENCES [dbo].[upload_map] ([id])
ON DELETE CASCADE
GO

ALTER TABLE [dbo].[upload_map_detail] CHECK CONSTRAINT [fk_upload_map_detail_upload_map]
GO

CREATE TABLE [dbo].[upload](
	id [bigint] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	file_name [varchar](255) NOT NULL,
	upload_type [varchar](3) NOT NULL,
	map_id [int] NULL,
	start_time [datetime] NULL,
	end_time [datetime] NULL,
	status [tinyint] NOT NULL,
	comment [varchar](1024) NULL,
 CONSTRAINT [pk_upload] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[upload]  WITH NOCHECK ADD  CONSTRAINT [fk_upload_upload_map] FOREIGN KEY(map_id)
REFERENCES [dbo].[upload_map] (id)
GO

ALTER TABLE [dbo].[upload] CHECK CONSTRAINT [fk_upload_upload_map]
GO

ALTER TABLE [dbo].[upload]  WITH NOCHECK ADD  CONSTRAINT [fk_upload_upload_status] FOREIGN KEY([status])
REFERENCES [dbo].[upload_status] (id)
GO

ALTER TABLE [dbo].[upload] CHECK CONSTRAINT [fk_upload_upload_status]
GO

ALTER TABLE [dbo].[upload]  WITH NOCHECK ADD  CONSTRAINT [fk_upload_upload_type] FOREIGN KEY(upload_type)
REFERENCES [dbo].[upload_type] (id)
GO

ALTER TABLE [dbo].[upload] CHECK CONSTRAINT [fk_upload_upload_type]
GO

INSERT INTO [ecoo].[dbo].[upload_type]
           ([id]
           ,[name]
           ,[last_upload_date])
VALUES('E','Example',NULL)
GO

INSERT INTO [ecoo].[dbo].[upload_status]
           ([id]
           ,[descr])
VALUES (1,'Running'),
(2,'Upload Failed'),
(3,'Upload Successful'),
(4,'Upload Partial'),
(5,'Parsing Successful'),
(6,'Parsing Partial'),
(7,'Parsing Failed'),
(8,'Ready'),
(9,'Marked As New'),
(10,'Marked As Ignore'),
(11,'Exported'),
(12,'Importing'),
(13,'Import Failed'),
(14,'Imported'),
(15,'Exporting'),
(16,'Scheduled'),
(17,'Queued')
GO


-- =====================================================================================
-- SHIPMENT
-- =====================================================================================
CREATE TABLE [ecoo].[dbo].[shipment](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[tenant_id] [int] NOT NULL, -- TODO: add fk
	[export_ref] [varchar](50) NOT NULL,
	[export_inv_no] [varchar](50) NULL,
	[export_inv_date] [datetime] NULL,
	[buy_ref] [varchar](50) NULL,
	[buy_order_date] [datetime] NULL,
	[letter_credit_no] [varchar](50) NULL,
	[consigee_name] [varchar](200) NOT NULL,
	[consigee_line1] [varchar](50) NOT NULL,
	[consigee_line2] [varchar](50) NULL,
	[consigee_line3] [varchar](50) NULL,
	[consigee_line4] [varchar](50) NULL,
	[buyer_name] [varchar](200) NULL,
	[buyer_line1] [varchar](50) NULL,
	[buyer_line2] [varchar](50) NULL,
	[buyer_line3] [varchar](50) NULL,
	[buyer_line4] [varchar](50) NULL,
	[place_of_issue] [varchar](100) NOT NULL,
	[date_of_issue] [datetime] NOT NULL,
CONSTRAINT [pk_shipment] PRIMARY KEY CLUSTERED ([id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

CREATE TABLE [dbo].[shipment_log](
	[rev] int NOT NULL,
	[revType] tinyint NOT NULL,
	[id] [int] NOT NULL,
CONSTRAINT [pk_shipment_log] PRIMARY KEY CLUSTERED ([rev] ASC,	[id] ASC)WITH (PAD_INDEX  = ON, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON, FILLFACTOR = 85) ON [PRIMARY]) ON [PRIMARY]
GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[shipment_log]  WITH NOCHECK ADD  CONSTRAINT [fk_shipment_log_revision] FOREIGN KEY([rev]) REFERENCES [dbo].[revision] ([id])
GO

ALTER TABLE [dbo].[shipment_log] CHECK CONSTRAINT [fk_shipment_log_revision]
GO


----------------------------------------------------------------------------------------------------------------
-- CERTIFICATE OF ORIGIN
----------------------------------------------------------------------------------------------------------------


----------------------------------------------------------------------------------------------------------------
-- POPULATE AUDIT TABLES
----------------------------------------------------------------------------------------------------------------
DECLARE @revisionId INT
INSERT INTO revision VALUES(1,'1493368646354',GETDATE())
SET @revisionId = @@IDENTITY

INSERT INTO feature_log(rev,revType,id,name,value,descr)
SELECT 1,0,id,name,value,descr FROM feature;
GO

/**
TEST Data: Please REMOVE
*/
INSERT INTO dbo.user_signature VALUES(1,'iVBORw0KGgoAAAANSUhEUgAAAoAAAARwCAYAAAB6j+UAAAAAAXNSR0IArs4c6QAAAAlwSFlzAAAWJQAAFiUBSVIk8AAAABxpRE9UAAAAAgAAAAAAAAI4AAAAKAAAAjgAAAI4AAA58B+Hp5IAADm8SURBVHgB7N0LlquqtgDQ27N04vanmvaatt+iLknlZ0SDCjJrjJxKRYXFBHEFk33+8x8/BAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAIEDBf79+3eJx8+Hx+XA8FRNgAABAgQIECCwgcC/KHPx47///e//vUsaN4hPkQQIECBAgAABApUFFid/UX/xMW8SxUvl+BVHgAABAgQIECCwUKA4mYtyN9n3eSVxYfx2J0CAAAECBAgQWCiwSVIXMVQrV4K4sEft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ECBAgACBFQISwBV4TiVAgAABAgQI9CggAeyx17SZAAECBAgQILBCoJYAxr7biqKdSoAAAQIECBAgcEaBRgL4dcY2axMBAgQIECBAgMAKgXyXb/JvAebkcEXpTiVAgAABAgQIEDirgATwrD2jXQQIECBAgACBnQQkgDvBKpYAAQIECBAgcFYBCeBZe0a7CBAgQIAAAQI7CUgAd4JVLAECBAgQIEDgrAISwLP2jHYRIECAAAECBHYSmEwA//z5899SfbV9pXNsJ0CAAAECBAgQOI/AZAIYzUvbX77ufzZGAvjC4gcCBAgQIECAQHcCzQQwJX456fs+1t8I7K6PNZgAAQIECBAg8CJQTQBzsvdyjATwxc8PBAgQIECAAIHuBF6Su2j94+fnu37P27uLUIMJECBAgAABAgReBB4JX2yd+/ylAD8QIECAAAECBAj0JTA36fs+zi+A9NW5WkuAAAECBAgQmBJ4KwH0+b8pQtsIECBAgAABAh0JTP2SRzS/mBRKADvqXE0lQIAAAQIECEwJVH7RYzIJjATwNlWObQQIECBAgAABAv0ITCZ60fzS9n4i01ICBAgQIECAAIFJgVKiV9o+WYiNBAgQIECAAAEC/QiUEr1f233+r59O1VICBAgQIECAQE3gV6IXB09ukwDWGO0jQIAAAQIECPQjMJnsRfN/bZcA9tOpWkqAAAECBAgQqAn8SvTi4NK2Wjn2ESBAgAABAgQInF0g39ErJXtT288ekvYRIECAAAECBAjUBN5JAP0LuJqkfQQIECBAgACBTgTeSQDj2FsnYWkmAQIECBAgQIBASSAldXP+E0hOFEvF2E6AAAECBAgQINCrQEr00mMiKew1JO0mQIAAAQIECBB4R+CeEL5zjmMJECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgACBCwr8Dz+j1U6jsJoyAAAAAElFTkSuQmCC',GETDATE(),'9999/12/31')
GO

--update user_signature set encoded_image='iVBORw0KGgoAAAANSUhEUgAAAoAAAARwCAYAAAB6j+UAAAAAAXNSR0IArs4c6QAAAAlwSFlzAAAWJQAAFiUBSVIk8AAAABxpRE9UAAAAAgAAAAAAAAI4AAAAKAAAAjgAAAI4AAA58B+Hp5IAADm8SURBVHgB7N0LlquqtgDQ27N04vanmvaatt+iLknlZ0SDCjJrjJxKRYXFBHEFk33+8x8/BAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAIEDBf79+3eJx8+Hx+XA8FRNgAABAgQIECCwgcC/KHPx47///e//vUsaN4hPkQQIECBAgAABApUFFid/UX/xMW8SxUvl+BVHgAABAgQIECCwUKA4mYtyN9n3eSVxYfx2J0CAAAECBAgQWCiwSVIXMVQrV4K4sEft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ECBAgACBFQISwBV4TiVAgAABAgQI9CggAeyx17SZAAECBAgQILBCoJYAxr7biqKdSoAAAQIECBAgcEaBRgL4dcY2axMBAgQIECBAgMAKgXyXb/JvAebkcEXpTiVAgAABAgQIEDirgATwrD2jXQQIECBAgACBnQQkgDvBKpYAAQIECBAgcFYBCeBZe0a7CBAgQIAAAQI7CUgAd4JVLAECBAgQIEDgrAISwLP2jHYRIECAAAECBHYSmEwA//z5899SfbV9pXNsJ0CAAAECBAgQOI/AZAIYzUvbX77ufzZGAvjC4gcCBAgQIECAQHcCzQQwJX456fs+1t8I7K6PNZgAAQIECBAg8CJQTQBzsvdyjATwxc8PBAgQIECAAIHuBF6Su2j94+fnu37P27uLUIMJECBAgAABAgReBB4JX2yd+/ylAD8QIECAAAECBAj0JTA36fs+zi+A9NW5WkuAAAECBAgQmBJ4KwH0+b8pQtsIECBAgAABAh0JTP2SRzS/mBRKADvqXE0lQIAAAQIECEwJVH7RYzIJjATwNlWObQQIECBAgAABAv0ITCZ60fzS9n4i01ICBAgQIECAAIFJgVKiV9o+WYiNBAgQIECAAAEC/QiUEr1f233+r59O1VICBAgQIECAQE3gV6IXB09ukwDWGO0jQIAAAQIECPQjMJnsRfN/bZcA9tOpWkqAAAECBAgQqAn8SvTi4NK2Wjn2ESBAgAABAgQInF0g39ErJXtT288ekvYRIECAAAECBAjUBN5JAP0LuJqkfQQIECBAgACBTgTeSQDj2FsnYWkmAQIECBAgQIBASSAldXP+E0hOFEvF2E6AAAECBAgQINCrQEr00mMiKew1JO0mQIAAAQIECBB4R+CeEL5zjmMJECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBAgACBCwr8Dz+j1U6jsJoyAAAAAElFTkSuQmCC'

