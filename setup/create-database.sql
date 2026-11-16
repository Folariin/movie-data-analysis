-- Creates the project database and an application login that owns it.
-- Run with sqlcmd as an administrator of the instance, choosing your own password:
--
--   sqlcmd -S tcp:localhost,1433 -E -C -i setup/create-database.sql -v AppPassword="<strong password>"
--
-- (-E uses your Windows login; on Docker/Linux use -U sa -P "<sa password>" instead.)
-- Then put the same password in auth.cfg. Safe to re-run.

IF DB_ID('moviedb') IS NULL
    CREATE DATABASE moviedb;
GO

IF SUSER_ID('movieapp') IS NULL
    CREATE LOGIN movieapp WITH PASSWORD = '$(AppPassword)', DEFAULT_DATABASE = moviedb, CHECK_POLICY = ON;
GO

USE moviedb;
IF USER_ID('movieapp') IS NULL
    CREATE USER movieapp FOR LOGIN movieapp;
ALTER ROLE db_owner ADD MEMBER movieapp;
GO

PRINT 'moviedb and login movieapp are ready.';
