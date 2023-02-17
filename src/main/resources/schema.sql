DROP TABLE IF EXISTS [dbo].[g3_insurance_card]
CREATE DATABASE g3_insurance_card;

USE g3_insurance_card;
SET ANSI_WARNINGS OFF;

CREATE TABLE [dbo].[chat](
    [id] [int] IDENTITY(1,1) PRIMARY KEY,
    [content] [nvarchar](50) NULL,
    [send_date] [datetime] NULL,
    [staff_id] [int] NULL,
    [customer_id] [int] NULL
    );

CREATE TABLE [dbo].[compensation](
    [id] [int] IDENTITY(1,1) PRIMARY KEY,
    [images] [nvarchar](50) NULL,
    [accident_address] [nvarchar](50) NULL,
    [accident_time] [datetime] NULL,
    [payment] [float] NULL,
    [contract_id] [int] NULL,
    [status] [nvarchar](50) NULL,
    [customer_id] [int] NULL,
    [manager_id] [int] NULL,
    [staff_id] [int] NULL
    );

CREATE TABLE [dbo].[contract](
    [id] [int] IDENTITY(1,1) PRIMARY KEY,
    [pattern] [nvarchar](50) NULL,
    [type_id] [int] NULL,
    [start_date] [datetime] NULL,
    [end_date] [datetime] NULL,
    [status] [nvarchar](50) NULL,
    [manager_id] [int] NULL,
    [staff_id] [int] NULL,
    [customer_id] [int] NULL
    );

CREATE TABLE [dbo].[contract_type](
    [id] [int] IDENTITY(1,1) PRIMARY KEY,
    [name] [nvarchar](50) NULL,
    [vehicle_type] [nvarchar](50) NULL,
    [price] [float] NULL,
    [insurance_level] [float] NULL,
    [description] [nvarchar](50) NULL,
    [manager_id] [int] NULL
    );

CREATE TABLE [dbo].[customer](
    [id] [int] IDENTITY(1,1) PRIMARY KEY,
    [username] [nvarchar](50) NULL,
    [password] [nvarchar](50) NULL,
    [name] [nvarchar](50) NULL,
    [gmail] [nvarchar](50) NULL,
    [phone] [nvarchar](50) NULL,
    [address] [nvarchar](50) NULL,
    [ci] [nvarchar](50) NULL,
    [is_active] [bit] NULL,
    [role] [nvarchar](50) NULL,
    [manager_id] [int] NULL
    );

CREATE TABLE [dbo].[manager](
    [id] [int] IDENTITY(1,1) PRIMARY KEY,
    [username] [nvarchar](50) NULL,
    [password] [nvarchar](50) NULL,
    [role] [nvarchar](50) NULL
    );

CREATE TABLE [dbo].[staff](
    [id] [int] IDENTITY(1,1) PRIMARY KEY,
    [username] [nvarchar](50) NULL,
    [password] [nvarchar](50) NULL,
    [role] [nvarchar](50) NULL,
    [manager_id] [int] NULL
    );

ALTER TABLE [dbo].[chat]  WITH CHECK ADD  CONSTRAINT [FK_Chat_Customer] FOREIGN KEY([customer_id])
    REFERENCES [dbo].[customer] ([id]);
ALTER TABLE [dbo].[chat]  WITH CHECK ADD  CONSTRAINT [FK_Chat_Staff] FOREIGN KEY([staff_id])
    REFERENCES [dbo].[staff] ([id]);
ALTER TABLE [dbo].[compensation]  WITH CHECK ADD  CONSTRAINT [FK_Compensation_Admin] FOREIGN KEY([manager_id])
    REFERENCES [dbo].[manager] ([id]);
ALTER TABLE [dbo].[compensation]  WITH CHECK ADD  CONSTRAINT [FK_Compensation_Customer] FOREIGN KEY([customer_id])
    REFERENCES [dbo].[customer] ([id]);
ALTER TABLE [dbo].[compensation]  WITH CHECK ADD  CONSTRAINT [FK_Compensation_Staff] FOREIGN KEY([staff_id])
    REFERENCES [dbo].[staff] ([id]);
ALTER TABLE [dbo].[contract]  WITH CHECK ADD  CONSTRAINT [FK_Contract_Admin] FOREIGN KEY([manager_id])
    REFERENCES [dbo].[manager] ([id]);
ALTER TABLE [dbo].[contract]  WITH CHECK ADD  CONSTRAINT [FK_Contract_ContractType] FOREIGN KEY([type_id])
    REFERENCES [dbo].[contract_type] ([id]);
ALTER TABLE [dbo].[contract]  WITH CHECK ADD  CONSTRAINT [FK_Contract_Customer] FOREIGN KEY([customer_id])
    REFERENCES [dbo].[customer] ([id]);
ALTER TABLE [dbo].[contract]  WITH CHECK ADD  CONSTRAINT [FK_Contract_Staff] FOREIGN KEY([staff_id])
    REFERENCES [dbo].[staff] ([id]);
ALTER TABLE [dbo].[contract_type]  WITH CHECK ADD  CONSTRAINT [FK_ContractType_Manager] FOREIGN KEY([manager_id])
    REFERENCES [dbo].[manager] ([id]);
ALTER TABLE [dbo].[customer]  WITH CHECK ADD  CONSTRAINT [FK_Customer_Admin] FOREIGN KEY([manager_id])
    REFERENCES [dbo].[manager] ([id]);
ALTER TABLE [dbo].[staff]  WITH CHECK ADD  CONSTRAINT [FK_Staff_Admin] FOREIGN KEY([manager_id])
    REFERENCES [dbo].[manager] ([id]);
