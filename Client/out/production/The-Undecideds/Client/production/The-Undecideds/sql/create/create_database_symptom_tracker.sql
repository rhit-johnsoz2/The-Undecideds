USE master
GO

CREATE DATABASE [SymptomTracker] ON (
    NAME = N'SymptomTracker',
    FILENAME = 'D:\Database\MSSQL15.MSSQLSERVER\MSSQL\DATA\SymptomTracker',
    SIZE = 6MB,  
    MAXSIZE = 30MB,  
    FILEGROWTH = 12%
)
LOG ON (
    NAME = N'SymptomTracker_log',
    FILENAME = 'D:\Database\MSSQL15.MSSQLSERVER\MSSQL\DATA\SymptomTracker.ldf',
    SIZE = 3MB,
    MAXSIZE = 30MB,
    FILEGROWTH = 10%
)
COLLATE SQL_Latin1_General_Cp1_CI_AS
GO
