INSERT INTO [dbo].[manager] ([username], [password], [role]) values ('anntm', 'anntm', 'manager');
INSERT INTO [dbo].[manager] ([username], [password], [role]) values ('cuongm', 'cuongm', 'manager');
INSERT INTO [dbo].[manager] ([username], [password], [role]) values ('ham', 'ham', 'manager');
INSERT INTO [dbo].[manager] ([username], [password], [role]) values ('thanhm', 'thanhm', 'manager');
INSERT INTO [dbo].[manager] ([username], [password], [role]) values ('giangm', 'giangm', 'manager');
INSERT INTO [dbo].[manager] ([username], [password], [role]) values ('cthanhm', 'cthanhm', 'manager');
INSERT INTO [dbo].[manager] ([username], [password], [role]) values ('manager', 'manager', 'manager');

INSERT INTO [dbo].[staff] ([username], [password], [role], [manager_id]) values ('annts', 'annts', 'staff', 1);
INSERT INTO [dbo].[staff] ([username], [password], [role], [manager_id]) values ('cuongs', 'cuongs', 'staff', 2);
INSERT INTO [dbo].[staff] ([username], [password], [role], [manager_id]) values ('has', 'has', 'staff', 3);
INSERT INTO [dbo].[staff] ([username], [password], [role], [manager_id]) values ('thanhs', 'thanhs', 'staff', 4);
INSERT INTO [dbo].[staff] ([username], [password], [role], [manager_id]) values ('giangs', 'giangs', 'staff', 5);
INSERT INTO [dbo].[staff] ([username], [password], [role], [manager_id]) values ('cthanhs', 'cthanhs', 'staff', 6);
INSERT INTO [dbo].[staff] ([username], [password], [role], [manager_id]) values ('staff', 'staff', 'staff', 7);

INSERT INTO [dbo].[customer] ([username], [password], [name], [gmail], [phone], [address], [ci], [is_active], [role], [manager_id])
    values ('anntc', 'anntc', 'Nguyen Thi An', 'ahihihi1234@gmail.com', '0123456789', 'Hoai Duc', '01234567891', 1, 'customer', 1);
INSERT INTO [dbo].[customer] ([username], [password], [name], [gmail], [phone], [address], [ci], [is_active], [role], [manager_id])
    values ('cuongc', 'cuongc', 'Le Duy Cuong', 'leduycuong@gmail.com', '0123456789', 'Thai Nguyen', '01234567891', 1, 'customer', 2);
INSERT INTO [dbo].[customer] ([username], [password], [name], [gmail], [phone], [address], [ci], [is_active], [role], [manager_id])
    values ('hac', 'hac', 'Vu Thi Ha', 'vuthiha@gmail.com', '0123456789', 'Hung Yen', '01234567891', 1, 'customer', 3);
INSERT INTO [dbo].[customer] ([username], [password], [name], [gmail], [phone], [address], [ci], [is_active], [role], [manager_id])
    values ('thanhc', 'thanhc', 'Dinh Quang Thanh', 'dinhquangthanh@gmail.com', '0123456789', 'Hai Duong', '01234567891', 1, 'customer', 4);
INSERT INTO [dbo].[customer] ([username], [password], [name], [gmail], [phone], [address], [ci], [is_active], [role], [manager_id])
    values ('giangc', 'giangc', 'Phan Truong Giang', 'phantruonggiang@gmail.com', '0123456789', 'Ha Long', '01234567891', 1, 'customer', 5);
INSERT INTO [dbo].[customer] ([username], [password], [name], [gmail], [phone], [address], [ci], [is_active], [role], [manager_id])
    values ('ctanhc', 'cthanhc', 'Chi Thanh', 'chithanh@gmail.com', '0123456789', 'Unknown', '01234567891', 1, 'customer', 6);
INSERT INTO [dbo].[customer] ([username], [password], [name], [gmail], [phone], [address], [ci], [is_active], [role], [manager_id])
    values ('customer', 'customer', 'Customer Dep Trai', 'customer@gmail.com', '0123456789', 'Hoai Duc', '01234567891', 1, 'customer', 7);

INSERT INTO [dbo].[contract_type] ([name], [vehicle_type], [price], [insurance_level], [description], [manager_id])
    values ('A', 'Xe Mô tô 2 bánh dung tích trên 50cc', 66000, 5, 'A', 1);
INSERT INTO [dbo].[contract_type] ([name], [vehicle_type], [price], [insurance_level], [description], [manager_id])
    values ('A', 'Xe Mô tô 2 bánh dung tích từ 50cc trở xuống', 60500, 4, 'A', 2);
INSERT INTO [dbo].[contract_type] ([name], [vehicle_type], [price], [insurance_level], [description], [manager_id])
    values ('A', 'Mô tô 3 bánh', 319000, 3, 'A', 3);
INSERT INTO [dbo].[contract_type] ([name], [vehicle_type], [price], [insurance_level], [description], [manager_id])
    values ('A', 'Xe máy điện', 60500, 2, 'A', 4);
INSERT INTO [dbo].[contract_type] ([name], [vehicle_type], [price], [insurance_level], [description], [manager_id])
    values ('A', 'Các loại xe còn lại', 319000, 1, 'A', 5);

INSERT INTO [dbo].[contract] ([pattern], [type_id], [start_date], [end_date], [status], [manager_id], [staff_id], [customer_id])
    values ('99-B1 420.23', 1, '1/1/2019', '3/3/2020', 'Đang xử lý', 1, 1, 1);
INSERT INTO [dbo].[contract] ([pattern], [type_id], [start_date], [end_date], [status], [manager_id], [staff_id], [customer_id])
    values ('30-G5 5212', 2, '5/2/2021', '3/3/2021', 'Đang chờ xử lí', 2, 2, 2);
INSERT INTO [dbo].[contract] ([pattern], [type_id], [start_date], [end_date], [status], [manager_id], [staff_id], [customer_id])
    values ('29-P4 6231', 3, '2/5/2021', '8/6/2021', 'Đã duyệt', 3, 3, 3);
INSERT INTO [dbo].[contract] ([pattern], [type_id], [start_date], [end_date], [status], [manager_id], [staff_id], [customer_id])
    values ('64-A2 7184', 4, '3/1/2022', '6/12/2022', 'Đang xử lí', 4, 4, 4);
INSERT INTO [dbo].[contract] ([pattern], [type_id], [start_date], [end_date], [status], [manager_id], [staff_id], [customer_id])
    values ('40-H2 2818', 5, '7/2/2022', '1/12/2022', 'Đã duyệt', 5, 5, 5);

INSERT INTO [dbo].[compensation] ([images], [accident_address], [accident_time], [payment], [contract_id], [status], [customer_id], [manager_id], [staff_id])
    values ('https://i.imgflip.com/5zn5dt.jpg', 'Đa Hội', '1/1/2022', 5425000, 1, 'Đang xử lí', 1, 1, 1);
INSERT INTO [dbo].[compensation] ([images], [accident_address], [accident_time], [payment], [contract_id], [status], [customer_id], [manager_id], [staff_id])
    values ('https://i.imgflip.com/56at69.jpg', 'Hoài Đức', '1/1/2022', null, 2, 'Đang chờ xử lí', 2, 2, 2);
INSERT INTO [dbo].[compensation] ([images], [accident_address], [accident_time], [payment], [contract_id], [status], [customer_id], [manager_id], [staff_id])
    values ('https://i.pinimg.com/originals/42/4f/93/424f935cc0c962b183865c9f634f2e83.jpg', 'Đa Hội', '1/1/2022', 8120000, 3, 'Đã duyệt', 3, 3, 3);
INSERT INTO [dbo].[compensation] ([images], [accident_address], [accident_time], [payment], [contract_id], [status], [customer_id], [manager_id], [staff_id])
    values ('https://i.pinimg.com/736x/f7/42/48/f74248291f310e84c1730135a3944885--dirtbikes-stay-cool.jpg', 'Thái Nguyên', '1/6/2021', null, 4, 'Đang chờ xử lí', 4, 4, 4);
INSERT INTO [dbo].[compensation] ([images], [accident_address], [accident_time], [payment], [contract_id], [status], [customer_id], [manager_id], [staff_id])
    values ('https://i.imgflip.com/2ufmch.jpg', 'Đa Hội', '1/1/2022', 29529000, 5, 'Đang xử lí', 5, 5, 5);

