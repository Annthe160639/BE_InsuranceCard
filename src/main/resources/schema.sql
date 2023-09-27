USE [master]
create database [g3_insurance_card]
USE [g3_insurance_card]
GO
/****** Object:  Table [dbo].[buyer]    Script Date: 3/15/2023 8:30:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[buyer](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ci] [nvarchar](50) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[address] [nvarchar](50) NOT NULL,
	[phone] [nvarchar](50) NOT NULL,
	[note] [nvarchar](500) NULL,
 CONSTRAINT [PK_buyer_1] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[chat]    Script Date: 3/15/2023 8:30:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[chat](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[content] [nvarchar](50) NULL,
	[send_date] [datetime] NULL,
	[staff_id] [int] NULL,
	[customer_id] [int] NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[compensation]    Script Date: 3/15/2023 8:30:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[compensation](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[images] [nvarchar](50) NULL,
	[accident_address] [nvarchar](50) NULL,
	[accident_time] [datetime] NULL,
	[payment] [float] NULL,
	[contract_id] [int] NULL,
	[status] [nvarchar](50) NULL,
	[customer_id] [int] NULL,
	[manager_id] [int] NULL,
	[staff_id] [int] NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[contract]    Script Date: 3/15/2023 8:30:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[contract](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[pattern] [nvarchar](50) NULL,
	[type_id] [int] NULL,
	[start_date] [datetime] NULL,
	[end_date] [datetime] NULL,
	[status] [nvarchar](50) NULL,
	[manager_id] [int] NULL,
	[staff_id] [int] NULL,
	[buyer_id] [int] NULL,
	[customer_id] [int] NULL,
 CONSTRAINT [PK__contract__3213E83FC606FE9A] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[contract_type]    Script Date: 3/15/2023 8:30:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[contract_type](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NULL,
	[vehicle_type] [nvarchar](50) NULL,
	[price] [float] NULL,
	[insurance_level] [float] NULL,
	[description] [nvarchar](500) NULL,
	[manager_id] [int] NULL,
	[status] [bit] NULL,
 CONSTRAINT [PK__contract__3213E83F69CAE24F] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[customer]    Script Date: 3/15/2023 8:30:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[customer](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[username] [nvarchar](50) NULL,
	[password] [nvarchar](255) NULL,
	[name] [nvarchar](50) NULL,
	[gmail] [nvarchar](50) NULL,
	[phone] [nvarchar](50) NULL,
	[address] [nvarchar](50) NULL,
	[ci] [nvarchar](50) NULL,
	[is_active] [bit] NULL,
	[role] [nvarchar](50) NULL,
	[manager_id] [int] NULL,
 CONSTRAINT [PK__customer__3213E83F548E24EA] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[manager]    Script Date: 3/15/2023 8:30:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[manager](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[username] [nvarchar](50) NOT NULL,
	[password] [nvarchar](50) NOT NULL,
	[role] [nvarchar](50) NOT NULL,
	[status] [bit] NULL,
 CONSTRAINT [PK__manager__3213E83F42460039] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[staff]    Script Date: 3/15/2023 8:30:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[staff](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[username] [nvarchar](50) NULL,
	[password] [nvarchar](50) NULL,
	[role] [nvarchar](50) NULL,
	[manager_id] [int] NULL,
	[status] [bit] NULL,
PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[buyer] ON

INSERT [dbo].[buyer] ([id], [ci], [name], [address], [phone], [note]) VALUES (1, N'1', N'anntb', N'Hà Nội', N'01234', N'khum')
INSERT [dbo].[buyer] ([id], [ci], [name], [address], [phone], [note]) VALUES (2, N'2', N'havtb', N'Hưng Yên', N'012642', NULL)
INSERT [dbo].[buyer] ([id], [ci], [name], [address], [phone], [note]) VALUES (3, N'3', N'cuongld', N'Thái Nguyên', N'346345', NULL)
INSERT [dbo].[buyer] ([id], [ci], [name], [address], [phone], [note]) VALUES (4, N'4', N'thanhdq', N'Hải Dương', N'923823', NULL)
INSERT [dbo].[buyer] ([id], [ci], [name], [address], [phone], [note]) VALUES (5, N'5', N'thanhnc', N'Hà Nội', N'2345642', NULL)
INSERT [dbo].[buyer] ([id], [ci], [name], [address], [phone], [note]) VALUES (6, N'6', N'giangpt', N'Hạ Long', N'323412', NULL)
INSERT [dbo].[buyer] ([id], [ci], [name], [address], [phone], [note]) VALUES (7, N'00012', N'Nguyen Thi Ngan', N'Vân Côn', N'0919348932', N'không có')
INSERT [dbo].[buyer] ([id], [ci], [name], [address], [phone], [note]) VALUES (8, N'00012', N'Nguyen Thi Ngan', N'Vân Côn', N'0919348932', N'không có')
SET IDENTITY_INSERT [dbo].[buyer] OFF
GO
SET IDENTITY_INSERT [dbo].[compensation] ON

INSERT [dbo].[compensation] ([id], [images], [accident_address], [accident_time], [payment], [contract_id], [status], [customer_id], [manager_id], [staff_id]) VALUES (1, N'https://i.imgflip.com/5zn5dt.jpg', N'Ða Hội', CAST(N'2022-01-01T00:00:00.000' AS DateTime), 5425000, 1, N'Ðã duyệt', 1, 1, 1)
INSERT [dbo].[compensation] ([id], [images], [accident_address], [accident_time], [payment], [contract_id], [status], [customer_id], [manager_id], [staff_id]) VALUES (2, N'https://i.imgflip.com/56at69.jpg', N'Hoài Ðức', CAST(N'2022-01-01T00:00:00.000' AS DateTime), NULL, 2, N'Ðang chờ xử lý', 2, NULL, NULL)
INSERT [dbo].[compensation] ([id], [images], [accident_address], [accident_time], [payment], [contract_id], [status], [customer_id], [manager_id], [staff_id]) VALUES (3, N'https://i.pinimg.com/originals/42/4f/93/424f935cc0', N'Ða Hội', CAST(N'2022-01-01T00:00:00.000' AS DateTime), 8120000, 3, N'Ðã duyệt', 3, 3, 3)
INSERT [dbo].[compensation] ([id], [images], [accident_address], [accident_time], [payment], [contract_id], [status], [customer_id], [manager_id], [staff_id]) VALUES (4, N'https://i.pinimg.com/736x/f7/42/48/f74248291f310e8', N'Thái Nguyên', CAST(N'2021-01-06T00:00:00.000' AS DateTime), NULL, 4, N'Ðang chờ xử lý', 4, NULL, NULL)
INSERT [dbo].[compensation] ([id], [images], [accident_address], [accident_time], [payment], [contract_id], [status], [customer_id], [manager_id], [staff_id]) VALUES (5, N'https://i.imgflip.com/2ufmch.jpg', N'Ða Hội', CAST(N'2022-01-01T00:00:00.000' AS DateTime), 29529000, 5, N'Ðã duyệt', 5, 1, 5)
INSERT [dbo].[compensation] ([id], [images], [accident_address], [accident_time], [payment], [contract_id], [status], [customer_id], [manager_id], [staff_id]) VALUES (6, N'ags', N'Hải Dương', CAST(N'2020-11-03T00:00:00.000' AS DateTime), 1000000, 1, N'Ðã duyệt', 1, 1, 1)
SET IDENTITY_INSERT [dbo].[compensation] OFF
GO
SET IDENTITY_INSERT [dbo].[contract] ON

INSERT [dbo].[contract] ([id], [pattern], [type_id], [start_date], [end_date], [status], [manager_id], [staff_id], [buyer_id], [customer_id]) VALUES (2, N'30-G5 5212', 2, CAST(N'2021-05-02T00:00:00.000' AS DateTime), CAST(N'2021-03-03T00:00:00.000' AS DateTime), N'Đã từ chối', NULL, 1, 2, 2)
INSERT [dbo].[contract] ([id], [pattern], [type_id], [start_date], [end_date], [status], [manager_id], [staff_id], [buyer_id], [customer_id]) VALUES (3, N'29-P4 6231', 3, CAST(N'2021-02-05T00:00:00.000' AS DateTime), CAST(N'2021-08-06T00:00:00.000' AS DateTime), N'Ðã duyệt', 3, 3, 3, 3)
INSERT [dbo].[contract] ([id], [pattern], [type_id], [start_date], [end_date], [status], [manager_id], [staff_id], [buyer_id], [customer_id]) VALUES (4, N'64-A2 7184', 4, CAST(N'2022-03-01T00:00:00.000' AS DateTime), CAST(N'2022-06-12T00:00:00.000' AS DateTime), N'Ðang xử lý', 4, 4, 4, 4)
INSERT [dbo].[contract] ([id], [pattern], [type_id], [start_date], [end_date], [status], [manager_id], [staff_id], [buyer_id], [customer_id]) VALUES (5, N'40-H2 2818', 5, CAST(N'2022-07-02T00:00:00.000' AS DateTime), CAST(N'2022-01-12T00:00:00.000' AS DateTime), N'Ðã duyệt', 5, 5, 1, 5)
INSERT [dbo].[contract] ([id], [pattern], [type_id], [start_date], [end_date], [status], [manager_id], [staff_id], [buyer_id], [customer_id]) VALUES (8, N'an', 1, CAST(N'2000-11-11T00:00:00.000' AS DateTime), CAST(N'2002-11-11T00:00:00.000' AS DateTime), N'Ðang chờ xử lý', NULL, NULL, 1, 1)
INSERT [dbo].[contract] ([id], [pattern], [type_id], [start_date], [end_date], [status], [manager_id], [staff_id], [buyer_id], [customer_id]) VALUES (9, N'kyyyyyyyyyyy', 1, CAST(N'2000-11-11T00:00:00.000' AS DateTime), CAST(N'2002-11-11T00:00:00.000' AS DateTime), N'Ðang chờ xử lý', NULL, NULL, 1, 1)
INSERT [dbo].[contract] ([id], [pattern], [type_id], [start_date], [end_date], [status], [manager_id], [staff_id], [buyer_id], [customer_id]) VALUES (10, N'new', 3, CAST(N'2023-03-15T00:00:00.000' AS DateTime), CAST(N'2025-03-15T00:00:00.000' AS DateTime), N'Ðang chờ xử lý.', NULL, NULL, 7, 1)
INSERT [dbo].[contract] ([id], [pattern], [type_id], [start_date], [end_date], [status], [manager_id], [staff_id], [buyer_id], [customer_id]) VALUES (11, N'new', 3, CAST(N'2023-03-15T00:00:00.000' AS DateTime), CAST(N'2025-03-15T00:00:00.000' AS DateTime), N'Ðang chờ xử lý.', NULL, NULL, 8, 1)
SET IDENTITY_INSERT [dbo].[contract] OFF
GO
SET IDENTITY_INSERT [dbo].[contract_type] ON

INSERT [dbo].[contract_type] ([id], [name], [vehicle_type], [price], [insurance_level], [description], [manager_id], [status]) VALUES (1, N'Bảo hiểm Xe Mô tô 2 bánh dung tích trên 50cc', N'Xe Mô tô 2 bánh dung tích trên 50cc', 66000, 10000000, N'Đối tượng bảo hiểm
Chủ xe máy tham gia giao thông trên lãnh thổ Nước Cộng hòa Xã hội Chủ nghĩa Việt Nam.

Phạm vi bảo hiểm
BIC thay mặt cho chủ xe bồi thường cho các tổn thất về người và tài sản cho bên thứ ba (bên bị thiệt hai do xe của chủ xe gây ra). ', 1, 1)
INSERT [dbo].[contract_type] ([id], [name], [vehicle_type], [price], [insurance_level], [description], [manager_id], [status]) VALUES (2, N'Bảo hiểm Xe Mô tô 2 bánh dung tích 50cc trở xuống', N'Xe Mô tô 2 bánh dung tích từ 50cc trở xuống', 60500, 8000000, N'Quyền lợi bảo hiểm:
BIC sẽ thay mặt cho chủ xe bồi thường cho bên thứ ba những thiệt hại về người và tài sản, cho hành khách những thiệt hại về người với mức tối đa như sau:

Về người: tối đa 150.000.000 đ / người / vụ tai nạn.', 2, 1)
INSERT [dbo].[contract_type] ([id], [name], [vehicle_type], [price], [insurance_level], [description], [manager_id], [status]) VALUES (3, N'Bảo hiểm Xe Mô tô 3 bánh', N'Mô tô 3 bánh', 319000, 30000000, N'Theo biểu phí bảo hiểm bắt buộc Trách nhiệm dân sự ban hành kèm theo Thông tư số 04/2021/TT-BTC ngày 15 tháng 1 năm 2021 của Bộ Tài chính quy định chi tiết một số điều của Nghị định số 03/2021/NĐ-CP về bảo hiểm bắt buộc trách nhiệm của chủ xe cơ giới
', 3, 1)
INSERT [dbo].[contract_type] ([id], [name], [vehicle_type], [price], [insurance_level], [description], [manager_id], [status]) VALUES (4, N'Bảo hiểm Xe máy điện', N'Xe máy điện', 60500, 8000000, N'Quyền lợi bảo hiểm

Tham gia Gói bảo hiểm toàn diện xe máy, khách hàng sẽ được bồi thường:

- Thiệt hại về thân thể, tính mạng và tài sản đối với bên thứ ba do xe gây ra

- Thiệt hại về thân thể đối với người điều khiển xe và người ngồi trên xe bị tai nạn khi đang ở trên xe, lên xuống xe trong quá trình xe tham gia giao thông', 4, 1)
INSERT [dbo].[contract_type] ([id], [name], [vehicle_type], [price], [insurance_level], [description], [manager_id], [status]) VALUES (5, N'Bảo hiểm khác', N'Các loại xe còn lại', 319000, 30000000, N'Tham gia Gói bảo hiểm xe máy, khách hàng sẽ được bồi thường:

- Thiệt hại về thân thế, tính mạng và tài sản đối với bên thứ ba do xe gây ra

- Thiệt hại về thân thể đối với người điều khiển xe và người ngồi trên xe bị tai nạn khi đang ở trên xe, lên xuống xe trong quá trình xe tham gia giao thông', 5, 1)
SET IDENTITY_INSERT [dbo].[contract_type] OFF
GO
SET IDENTITY_INSERT [dbo].[customer] ON

INSERT [dbo].[customer] ([id], [username], [password], [name], [gmail], [phone], [address], [ci], [is_active], [role], [manager_id]) VALUES (1, N'NguyenAn', N'YENE/LTM8RDMDc08qrSEVQ==', N'Nguyen Thi An', N'ahihihi1234@gmail.com', N'0123456789', N'Hoai Duc', N'01234567891', 1, N'customer', 1)
INSERT [dbo].[customer] ([id], [username], [password], [name], [gmail], [phone], [address], [ci], [is_active], [role], [manager_id]) VALUES (2, N'cuongc', N'YENE/LTM8RDMDc08qrSEVQ==', N'Le Duy Cuong', N'leduycuong@gmail.com', N'0123456789', N'Thai Nguyen', N'01234567891', 1, N'customer', 2)
INSERT [dbo].[customer] ([id], [username], [password], [name], [gmail], [phone], [address], [ci], [is_active], [role], [manager_id]) VALUES (3, N'hac', N'YENE/LTM8RDMDc08qrSEVQ==', N'Vu Thi Ha', N'vuthiha@gmail.com', N'0123456789', N'Hung Yen', N'01234567891', 1, N'a', 3)
INSERT [dbo].[customer] ([id], [username], [password], [name], [gmail], [phone], [address], [ci], [is_active], [role], [manager_id]) VALUES (4, N'thanhc', N'YENE/LTM8RDMDc08qrSEVQ==', N'Dinh Quang Thanh', N'dinhquangthanh@gmail.com', N'0123456789', N'Hai Duong', N'01234567891', 1, N'customer', 4)
INSERT [dbo].[customer] ([id], [username], [password], [name], [gmail], [phone], [address], [ci], [is_active], [role], [manager_id]) VALUES (5, N'giangc', N'YENE/LTM8RDMDc08qrSEVQ==', N'Phan Truong Giang', N'phantruonggiang@gmail.com', N'0123456789', N'Ha Long', N'01234567891', 1, N'customer', 5)
INSERT [dbo].[customer] ([id], [username], [password], [name], [gmail], [phone], [address], [ci], [is_active], [role], [manager_id]) VALUES (6, N'ctanhc', N'YENE/LTM8RDMDc08qrSEVQ==', N'Chi Thanh', N'chithanh@gmail.com', N'0123456789', N'Unknown', N'01234567891', 1, N'customer', 6)
INSERT [dbo].[customer] ([id], [username], [password], [name], [gmail], [phone], [address], [ci], [is_active], [role], [manager_id]) VALUES (7, N'customer', N'YENE/LTM8RDMDc08qrSEVQ==', N'Customer Dep Trai', N'haha060502@gmail.com', N'0123456789', N'Hoai Duc', N'01234567891', 1, N'customer', 7)
INSERT [dbo].[customer] ([id], [username], [password], [name], [gmail], [phone], [address], [ci], [is_active], [role], [manager_id]) VALUES (13, N'ky', N'$2a$10$GZiWRNBZfTJHN.wHqcHzc.TJxuTJesS8YmGvCDdhHwGXjtjA9u6lK', N'ha', N'haha060502@gmail.com', N'0123345', N'Ha Noi', N'239479237342', 1, N'customer', 1)
INSERT [dbo].[customer] ([id], [username], [password], [name], [gmail], [phone], [address], [ci], [is_active], [role], [manager_id]) VALUES (20, N'1', N'7IUAOUKzzp3AYbnOQgi0Bg==', N'ha', N'haha060502@gmail.com', N'0123345', N'Ha Noi', N'239479237342', 1, N'customer', 1)
INSERT [dbo].[customer] ([id], [username], [password], [name], [gmail], [phone], [address], [ci], [is_active], [role], [manager_id]) VALUES (25, N'a', N'x4MMUT97oPzAAbmLjDlj9w==', N'a', N'a@gmai.com', N'a', N'a', N'a', 0, N'customer', NULL)
INSERT [dbo].[customer] ([id], [username], [password], [name], [gmail], [phone], [address], [ci], [is_active], [role], [manager_id]) VALUES (26, N'an', N'toZcoQzMBja00n0vfD8ilg==', N'an', N'an@123.com', N'0123', N'as', N'234', 0, N'customer', NULL)
SET IDENTITY_INSERT [dbo].[customer] OFF
GO
SET IDENTITY_INSERT [dbo].[manager] ON

INSERT [dbo].[manager] ([id], [username], [password], [role], [status]) VALUES (1, N'anntm', N'YENE/LTM8RDMDc08qrSEVQ==', N'manager', 1)
INSERT [dbo].[manager] ([id], [username], [password], [role], [status]) VALUES (2, N'cuongm', N'YENE/LTM8RDMDc08qrSEVQ==', N'manager', 1)
INSERT [dbo].[manager] ([id], [username], [password], [role], [status]) VALUES (3, N'ham', N'YENE/LTM8RDMDc08qrSEVQ==', N'manager', 1)
INSERT [dbo].[manager] ([id], [username], [password], [role], [status]) VALUES (4, N'thanhm', N'YENE/LTM8RDMDc08qrSEVQ==', N'manager', 1)
INSERT [dbo].[manager] ([id], [username], [password], [role], [status]) VALUES (5, N'giangm', N'YENE/LTM8RDMDc08qrSEVQ==', N'manager', 1)
INSERT [dbo].[manager] ([id], [username], [password], [role], [status]) VALUES (6, N'cthanhm', N'YENE/LTM8RDMDc08qrSEVQ==', N'manager', 1)
INSERT [dbo].[manager] ([id], [username], [password], [role], [status]) VALUES (7, N'manager', N'manager', N'manager', 1)
SET IDENTITY_INSERT [dbo].[manager] OFF
GO
SET IDENTITY_INSERT [dbo].[staff] ON

INSERT [dbo].[staff] ([id], [username], [password], [role], [manager_id], [status]) VALUES (1, N'annts', N'YENE/LTM8RDMDc08qrSEVQ==', N'staff', 1, 1)
INSERT [dbo].[staff] ([id], [username], [password], [role], [manager_id], [status]) VALUES (2, N'anntc', N'3TFCsltMMRkrHmFiNElI5Q==', N'staff', 2, 1)
INSERT [dbo].[staff] ([id], [username], [password], [role], [manager_id], [status]) VALUES (3, N'has', N'has', N'staff', 3, 1)
INSERT [dbo].[staff] ([id], [username], [password], [role], [manager_id], [status]) VALUES (4, N'thanhs', N'thanhs', N'staff', 4, 1)
INSERT [dbo].[staff] ([id], [username], [password], [role], [manager_id], [status]) VALUES (5, N'giangs', N'giangs', N'staff', 5, 1)
INSERT [dbo].[staff] ([id], [username], [password], [role], [manager_id], [status]) VALUES (6, N'cthanhs', N'cthanhs', N'staff', 6, 1)
INSERT [dbo].[staff] ([id], [username], [password], [role], [manager_id], [status]) VALUES (7, N'staff', N'staff', N'staff', 7, 1)
SET IDENTITY_INSERT [dbo].[staff] OFF
GO
ALTER TABLE [dbo].[chat]  WITH CHECK ADD  CONSTRAINT [FK_Chat_Customer] FOREIGN KEY([customer_id])
REFERENCES [dbo].[customer] ([id])
GO
ALTER TABLE [dbo].[chat] CHECK CONSTRAINT [FK_Chat_Customer]
GO
ALTER TABLE [dbo].[chat]  WITH CHECK ADD  CONSTRAINT [FK_Chat_Staff] FOREIGN KEY([staff_id])
REFERENCES [dbo].[staff] ([id])
GO
ALTER TABLE [dbo].[chat] CHECK CONSTRAINT [FK_Chat_Staff]
GO
ALTER TABLE [dbo].[compensation]  WITH CHECK ADD  CONSTRAINT [FK_Compensation_Admin] FOREIGN KEY([manager_id])
REFERENCES [dbo].[manager] ([id])
GO
ALTER TABLE [dbo].[compensation] CHECK CONSTRAINT [FK_Compensation_Admin]
GO
ALTER TABLE [dbo].[compensation]  WITH CHECK ADD  CONSTRAINT [FK_Compensation_Customer] FOREIGN KEY([customer_id])
REFERENCES [dbo].[customer] ([id])
GO
ALTER TABLE [dbo].[compensation] CHECK CONSTRAINT [FK_Compensation_Customer]
GO
ALTER TABLE [dbo].[compensation]  WITH CHECK ADD  CONSTRAINT [FK_Compensation_Staff] FOREIGN KEY([staff_id])
REFERENCES [dbo].[staff] ([id])
GO
ALTER TABLE [dbo].[compensation] CHECK CONSTRAINT [FK_Compensation_Staff]
GO
ALTER TABLE [dbo].[contract]  WITH CHECK ADD  CONSTRAINT [FK_Contract_Admin] FOREIGN KEY([manager_id])
REFERENCES [dbo].[manager] ([id])
GO
ALTER TABLE [dbo].[contract] CHECK CONSTRAINT [FK_Contract_Admin]
GO
ALTER TABLE [dbo].[contract]  WITH CHECK ADD  CONSTRAINT [FK_contract_buyer1] FOREIGN KEY([buyer_id])
REFERENCES [dbo].[buyer] ([id])
GO
ALTER TABLE [dbo].[contract] CHECK CONSTRAINT [FK_contract_buyer1]
GO
ALTER TABLE [dbo].[contract]  WITH CHECK ADD  CONSTRAINT [FK_Contract_ContractType] FOREIGN KEY([type_id])
REFERENCES [dbo].[contract_type] ([id])
GO
ALTER TABLE [dbo].[contract] CHECK CONSTRAINT [FK_Contract_ContractType]
GO
ALTER TABLE [dbo].[contract]  WITH CHECK ADD  CONSTRAINT [FK_Contract_Customer] FOREIGN KEY([customer_id])
REFERENCES [dbo].[customer] ([id])
GO
ALTER TABLE [dbo].[contract] CHECK CONSTRAINT [FK_Contract_Customer]
GO
ALTER TABLE [dbo].[contract]  WITH CHECK ADD  CONSTRAINT [FK_Contract_Staff] FOREIGN KEY([staff_id])
REFERENCES [dbo].[staff] ([id])
GO
ALTER TABLE [dbo].[contract] CHECK CONSTRAINT [FK_Contract_Staff]
GO
ALTER TABLE [dbo].[contract_type]  WITH CHECK ADD  CONSTRAINT [FK_ContractType_Manager] FOREIGN KEY([manager_id])
REFERENCES [dbo].[manager] ([id])
GO
ALTER TABLE [dbo].[contract_type] CHECK CONSTRAINT [FK_ContractType_Manager]
GO
ALTER TABLE [dbo].[customer]  WITH CHECK ADD  CONSTRAINT [FK_Customer_Admin] FOREIGN KEY([manager_id])
REFERENCES [dbo].[manager] ([id])
GO
ALTER TABLE [dbo].[customer] CHECK CONSTRAINT [FK_Customer_Admin]
GO
ALTER TABLE [dbo].[staff]  WITH CHECK ADD  CONSTRAINT [FK_Staff_Admin] FOREIGN KEY([manager_id])
REFERENCES [dbo].[manager] ([id])
GO
ALTER TABLE [dbo].[staff] CHECK CONSTRAINT [FK_Staff_Admin]
GO
USE [master]
GO
ALTER DATABASE [g3_insurance_card] SET  READ_WRITE
GO
