USE [QuanLyKTX]
GO
/****** Object:  Table [dbo].[admins]    Script Date: 5/20/2023 4:42:30 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[admins](
	[MSCB] [varchar](50) NOT NULL,
	[ho_ten] [nvarchar](50) NULL,
	[gioi_tinh] [bit] NULL,
	[ngay_thang_nam_sinh] [date] NULL,
	[CMND] [varchar](50) NULL,
	[SDT] [nchar](10) NULL,
	[mail] [varchar](50) NULL,
	[password] [varchar](max) NULL,
	[phan_quyen] [int] NULL,
 CONSTRAINT [PK_admins] PRIMARY KEY CLUSTERED 
(
	[MSCB] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[comments]    Script Date: 5/20/2023 4:42:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[comments](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[content] [varchar](255) NOT NULL,
	[id_room] [int] NOT NULL,
	[mssv] [varchar](50) NOT NULL,
	[time_post] [datetime2](7) NOT NULL,
 CONSTRAINT [PK__comments__3213E83F8949AA91] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[gia_dien_theo_thang]    Script Date: 5/20/2023 4:42:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[gia_dien_theo_thang](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[thang] [int] NOT NULL,
	[nam] [int] NOT NULL,
	[gia_dien] [float] NULL,
 CONSTRAINT [PK_gia_dien_theo_thang_1] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[gia_nuoc_theo_thang]    Script Date: 5/20/2023 4:42:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[gia_nuoc_theo_thang](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[thang] [int] NULL,
	[nam] [int] NULL,
	[gia_nuoc] [float] NULL,
 CONSTRAINT [PK_gia_nuoc_theo_thang] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hop_dong_KTX]    Script Date: 5/20/2023 4:42:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hop_dong_KTX](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[phong_KTX] [int] NULL,
	[MSSV] [varchar](50) NULL,
	[ngay_lam_don] [date] NULL,
	[trang_thai] [bit] NULL,
	[id_term] [int] NULL,
	[tong_tien] [float] NULL,
 CONSTRAINT [PK_hop_dong_KTX] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[loai_KTX]    Script Date: 5/20/2023 4:42:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[loai_KTX](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[so_giuong] [int] NULL,
	[gia_phong] [float] NULL,
	[hinh_anh] [varchar](max) NULL,
	[ten_loai] [nvarchar](50) NULL,
	[gioi_tinh] [bit] NULL,
	[mo_ta] [nvarchar](max) NULL,
 CONSTRAINT [PK_loai_KTX] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[otps]    Script Date: 5/20/2023 4:42:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[otps](
	[username] [varchar](50) NOT NULL,
	[code] [varchar](6) NULL,
	[time_generate] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[phieu_dien_KTX]    Script Date: 5/20/2023 4:42:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[phieu_dien_KTX](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma_so_KTX] [int] NULL,
	[so_dien_tieu_thu] [int] NULL,
	[trang_thai] [bit] NULL,
	[id_don_gia] [int] NULL,
 CONSTRAINT [PK_dien_nuoc_KTX] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[phieu_nuoc_KTX]    Script Date: 5/20/2023 4:42:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[phieu_nuoc_KTX](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma_so_KTX] [int] NULL,
	[id_don_gia] [int] NULL,
	[luong_nuoc_tieu_thu] [int] NULL,
	[trang_thai] [bit] NULL,
 CONSTRAINT [PK_phieu_nuoc_KTX] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[phong_KTX]    Script Date: 5/20/2023 4:42:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[phong_KTX](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[loai_KTX] [int] NULL,
	[trang_thai] [bit] NULL,
 CONSTRAINT [PK_phong_KTX] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[quyen]    Script Date: 5/20/2023 4:42:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[quyen](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[quyen] [varchar](50) NULL,
 CONSTRAINT [PK_quyen] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[students]    Script Date: 5/20/2023 4:42:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[students](
	[MSSV] [varchar](50) NOT NULL,
	[ho_ten] [nvarchar](50) NULL,
	[gioi_tinh] [bit] NULL,
	[ngay_thang_nam_sinh] [date] NULL,
	[CMND] [varchar](50) NULL,
	[SDT] [nchar](10) NULL,
	[mail] [varchar](50) NULL,
	[password] [varchar](max) NULL,
	[phan_quyen] [int] NULL,
	[trang_thai] [bit] NULL,
 CONSTRAINT [PK_sinh_vien] PRIMARY KEY CLUSTERED 
(
	[MSSV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[term]    Script Date: 5/20/2023 4:42:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[term](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ngay_mo_dang_ky] [date] NULL,
	[ngay_ket_thuc_dang_ky] [date] NULL,
	[ngay_ket_thuc] [date] NULL,
	[han_dong_phi] [smallint] NULL,
 CONSTRAINT [PK_term] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[thong_bao_KTX]    Script Date: 5/20/2023 4:42:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[thong_bao_KTX](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[tieu_de] [nvarchar](max) NULL,
	[noi_dung] [nvarchar](max) NULL,
	[nguoi_tao] [varchar](50) NULL,
	[hinh_anh] [varchar](max) NULL,
	[thoi_gian] [date] NULL,
 CONSTRAINT [PK_thong_bao_KTX] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
INSERT [dbo].[admins] ([MSCB], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen]) VALUES (N'CB1', N'Nguyễn Đăng Bắc', 1, CAST(N'2001-03-31' AS Date), N'12345788', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$kWQ/X9Yof5kpnDHS4usAs.HoWcQemrNBGl4vlKSrr1ziZXWyvQf5S', 2)
INSERT [dbo].[admins] ([MSCB], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen]) VALUES (N'CB2', N'Hồ Đức Trung', 1, CAST(N'2001-03-31' AS Date), N'12345788', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$A40pGE1r4HV9Q5I76Xu8ReAuSof9V5Pu5NBOeIuPc3Bs6x.VDyNSi', 3)
INSERT [dbo].[admins] ([MSCB], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen]) VALUES (N'CB3', N'Lê Thành Trung', 1, CAST(N'2001-03-31' AS Date), N'12345788', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$A40pGE1r4HV9Q5I76Xu8ReAuSof9V5Pu5NBOeIuPc3Bs6x.VDyNSi', 1003)
GO
SET IDENTITY_INSERT [dbo].[comments] ON 

INSERT [dbo].[comments] ([id], [content], [id_room], [mssv], [time_post]) VALUES (5, N'good', 3, N'n19dccn039', CAST(N'2023-05-19T09:37:52.1760000' AS DateTime2))
INSERT [dbo].[comments] ([id], [content], [id_room], [mssv], [time_post]) VALUES (6, N'bad, very bad!', 3, N'n19dccn075', CAST(N'2023-05-19T09:38:32.5130000' AS DateTime2))
SET IDENTITY_INSERT [dbo].[comments] OFF
GO
SET IDENTITY_INSERT [dbo].[gia_dien_theo_thang] ON 

INSERT [dbo].[gia_dien_theo_thang] ([id], [thang], [nam], [gia_dien]) VALUES (1, 2, 2023, 4000)
INSERT [dbo].[gia_dien_theo_thang] ([id], [thang], [nam], [gia_dien]) VALUES (2, 3, 2023, 4000)
INSERT [dbo].[gia_dien_theo_thang] ([id], [thang], [nam], [gia_dien]) VALUES (3, 4, 2023, 4000)
INSERT [dbo].[gia_dien_theo_thang] ([id], [thang], [nam], [gia_dien]) VALUES (4, 5, 2023, 4000)
INSERT [dbo].[gia_dien_theo_thang] ([id], [thang], [nam], [gia_dien]) VALUES (5, 6, 2023, 4000)
SET IDENTITY_INSERT [dbo].[gia_dien_theo_thang] OFF
GO
SET IDENTITY_INSERT [dbo].[gia_nuoc_theo_thang] ON 

INSERT [dbo].[gia_nuoc_theo_thang] ([id], [thang], [nam], [gia_nuoc]) VALUES (1, 2, 2023, 18000)
INSERT [dbo].[gia_nuoc_theo_thang] ([id], [thang], [nam], [gia_nuoc]) VALUES (2, 3, 2023, 18000)
INSERT [dbo].[gia_nuoc_theo_thang] ([id], [thang], [nam], [gia_nuoc]) VALUES (3, 4, 2023, 18000)
INSERT [dbo].[gia_nuoc_theo_thang] ([id], [thang], [nam], [gia_nuoc]) VALUES (4, 5, 2023, 18000)
INSERT [dbo].[gia_nuoc_theo_thang] ([id], [thang], [nam], [gia_nuoc]) VALUES (5, 6, 2023, 18000)
SET IDENTITY_INSERT [dbo].[gia_nuoc_theo_thang] OFF
GO
SET IDENTITY_INSERT [dbo].[hop_dong_KTX] ON 

INSERT [dbo].[hop_dong_KTX] ([id], [phong_KTX], [MSSV], [ngay_lam_don], [trang_thai], [id_term], [tong_tien]) VALUES (25, 1, N'n19dccn018', CAST(N'2023-03-16' AS Date), 1, 1, 6000)
INSERT [dbo].[hop_dong_KTX] ([id], [phong_KTX], [MSSV], [ngay_lam_don], [trang_thai], [id_term], [tong_tien]) VALUES (26, 1, N'n19dccn214', CAST(N'2023-03-16' AS Date), 1, 1, 6000)
INSERT [dbo].[hop_dong_KTX] ([id], [phong_KTX], [MSSV], [ngay_lam_don], [trang_thai], [id_term], [tong_tien]) VALUES (27, 2, N'n19dccn213', CAST(N'2023-03-16' AS Date), 1, 1, 6000)
INSERT [dbo].[hop_dong_KTX] ([id], [phong_KTX], [MSSV], [ngay_lam_don], [trang_thai], [id_term], [tong_tien]) VALUES (30, 3, N'n19dccn075', CAST(N'2023-03-16' AS Date), 1, 1, 6000)
INSERT [dbo].[hop_dong_KTX] ([id], [phong_KTX], [MSSV], [ngay_lam_don], [trang_thai], [id_term], [tong_tien]) VALUES (1044, 3, N'n19dccn039', CAST(N'2023-03-16' AS Date), 1, 1, 6000)
INSERT [dbo].[hop_dong_KTX] ([id], [phong_KTX], [MSSV], [ngay_lam_don], [trang_thai], [id_term], [tong_tien]) VALUES (1045, 27, N'n19dccn039', CAST(N'2023-05-18' AS Date), 0, 6, 12000)
INSERT [dbo].[hop_dong_KTX] ([id], [phong_KTX], [MSSV], [ngay_lam_don], [trang_thai], [id_term], [tong_tien]) VALUES (1047, 27, N'n19dccn075', CAST(N'2023-05-18' AS Date), 0, 6, 12000)
INSERT [dbo].[hop_dong_KTX] ([id], [phong_KTX], [MSSV], [ngay_lam_don], [trang_thai], [id_term], [tong_tien]) VALUES (1048, 27, N'n19dccn213', CAST(N'2023-05-18' AS Date), 0, 6, 12000)
INSERT [dbo].[hop_dong_KTX] ([id], [phong_KTX], [MSSV], [ngay_lam_don], [trang_thai], [id_term], [tong_tien]) VALUES (1050, 5, N'n19dccn157', CAST(N'2023-05-18' AS Date), 0, 6, 12000)
INSERT [dbo].[hop_dong_KTX] ([id], [phong_KTX], [MSSV], [ngay_lam_don], [trang_thai], [id_term], [tong_tien]) VALUES (1051, 6, N'n19dccn211', CAST(N'2023-05-18' AS Date), 0, 6, 6000)
INSERT [dbo].[hop_dong_KTX] ([id], [phong_KTX], [MSSV], [ngay_lam_don], [trang_thai], [id_term], [tong_tien]) VALUES (1052, 11, N'n19dccn301', CAST(N'2023-05-18' AS Date), 0, 6, 7200)
INSERT [dbo].[hop_dong_KTX] ([id], [phong_KTX], [MSSV], [ngay_lam_don], [trang_thai], [id_term], [tong_tien]) VALUES (1053, 17, N'n19dccn307', CAST(N'2023-05-18' AS Date), 0, 6, 9000)
SET IDENTITY_INSERT [dbo].[hop_dong_KTX] OFF
GO
SET IDENTITY_INSERT [dbo].[loai_KTX] ON 

INSERT [dbo].[loai_KTX] ([id], [so_giuong], [gia_phong], [hinh_anh], [ten_loai], [gioi_tinh], [mo_ta]) VALUES (5, 12, 1000, N'https://images.unsplash.com/photo-1618773928121-c32242e63f39?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80', N'basic', 1, N'Basic Room: A basic room in a dormitory is usually a small room with minimal amenities. It may contain a single bed, a small desk, and a chair. Basic rooms may also have shared bathrooms and community spaces.')
INSERT [dbo].[loai_KTX] ([id], [so_giuong], [gia_phong], [hinh_anh], [ten_loai], [gioi_tinh], [mo_ta]) VALUES (6, 10, 1200, N'https://images.unsplash.com/photo-1578683010236-d716f9a3f461?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80', N'medium', 1, N'Medium Room: A medium room in a dormitory is slightly larger than a basic room and may include additional amenities such as a private bathroom, a closet or wardrobe, and a dresser. These rooms may be furnished with a single or double bed, a desk, and a chair.')
INSERT [dbo].[loai_KTX] ([id], [so_giuong], [gia_phong], [hinh_anh], [ten_loai], [gioi_tinh], [mo_ta]) VALUES (7, 8, 1500, N'https://images.unsplash.com/photo-1512918728675-ed5a9ecdebfd?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80', N'high', 1, N'High Room: A high room in a dormitory is typically larger than a medium room and may include more luxurious amenities such as a private bathroom with a bathtub, a mini-fridge, a television, and air conditioning. These rooms may also offer more privacy, with individual entrances and additional soundproofing.')
INSERT [dbo].[loai_KTX] ([id], [so_giuong], [gia_phong], [hinh_anh], [ten_loai], [gioi_tinh], [mo_ta]) VALUES (1005, 4, 2000, N'https://images.unsplash.com/photo-1611892440504-42a792e24d32?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80', N'luxury', 1, N'Luxury Room: A luxury room in a dormitory is the most spacious and well-appointed type of room. These rooms may include multiple rooms, such as a bedroom and a living area, as well as high-end amenities such as a Jacuzzi, a private balcony or terrace, and additional storage space. Luxury rooms are often reserved for senior students or graduate students who require a more upscale living arrangement.')
INSERT [dbo].[loai_KTX] ([id], [so_giuong], [gia_phong], [hinh_anh], [ten_loai], [gioi_tinh], [mo_ta]) VALUES (1006, 4, 2000, N'https://storage.googleapis.com/quanlyktx-4b1db.appspot.com/10-1493797352149.jpg', N'luxury', 0, N'Luxury Room: A luxury room in a dormitory is the most spacious and well-appointed type of room. These rooms may include multiple rooms, such as a bedroom and a living area, as well as high-end amenities such as a Jacuzzi, a private balcony or terrace, and additional storage space. Luxury rooms are often reserved for senior students or graduate students who require a more upscale living arrangement.')
INSERT [dbo].[loai_KTX] ([id], [so_giuong], [gia_phong], [hinh_anh], [ten_loai], [gioi_tinh], [mo_ta]) VALUES (1007, 12, 1000, N'https://storage.googleapis.com/quanlyktx-4b1db.appspot.com/b0a24d067e5cfde2606a9aa5fdec7ce2.jpg', N'basic', 0, N'Basic Room: A basic room in a dormitory is usually a small room with minimal amenities. It may contain a single bed, a small desk, and a chair. Basic rooms may also have shared bathrooms and community spaces.')
INSERT [dbo].[loai_KTX] ([id], [so_giuong], [gia_phong], [hinh_anh], [ten_loai], [gioi_tinh], [mo_ta]) VALUES (1008, 10, 1200, N'https://storage.googleapis.com/quanlyktx-4b1db.appspot.com/b0a24d067e5cfde2606a9aa5fdec7ce2.jpg', N'medium', 0, N'Medium Room: A medium room in a dormitory is slightly larger than a basic room and may include additional amenities such as a private bathroom, a closet or wardrobe, and a dresser. These rooms may be furnished with a single or double bed, a desk, and a chair.')
INSERT [dbo].[loai_KTX] ([id], [so_giuong], [gia_phong], [hinh_anh], [ten_loai], [gioi_tinh], [mo_ta]) VALUES (1009, 8, 1500, N'https://storage.googleapis.com/quanlyktx-4b1db.appspot.com/b0a24d067e5cfde2606a9aa5fdec7ce2.jpg', N'high', 0, N'High Room: A high room in a dormitory is typically larger than a medium room and may include more luxurious amenities such as a private bathroom with a bathtub, a mini-fridge, a television, and air conditioning. These rooms may also offer more privacy, with individual entrances and additional soundproofing.')
SET IDENTITY_INSERT [dbo].[loai_KTX] OFF
GO
INSERT [dbo].[otps] ([username], [code], [time_generate]) VALUES (N'CB1', N'073340', 1684574308300)
INSERT [dbo].[otps] ([username], [code], [time_generate]) VALUES (N'CB12', N'198387', 1677931802444)
INSERT [dbo].[otps] ([username], [code], [time_generate]) VALUES (N'CB2', N'046669', 1684575436624)
INSERT [dbo].[otps] ([username], [code], [time_generate]) VALUES (N'CB3', N'969365', 1684574453248)
INSERT [dbo].[otps] ([username], [code], [time_generate]) VALUES (N'n19dccn018', N'131160', 1678417893829)
INSERT [dbo].[otps] ([username], [code], [time_generate]) VALUES (N'n19dccn039', N'262550', 1680771802063)
INSERT [dbo].[otps] ([username], [code], [time_generate]) VALUES (N'n19dccn213', N'748540', 1678120680801)
INSERT [dbo].[otps] ([username], [code], [time_generate]) VALUES (N'n19dccn214', N'592735', 1680186584127)
GO
SET IDENTITY_INSERT [dbo].[phieu_dien_KTX] ON 

INSERT [dbo].[phieu_dien_KTX] ([id], [ma_so_KTX], [so_dien_tieu_thu], [trang_thai], [id_don_gia]) VALUES (14, 1, 24, 1, 1)
INSERT [dbo].[phieu_dien_KTX] ([id], [ma_so_KTX], [so_dien_tieu_thu], [trang_thai], [id_don_gia]) VALUES (15, 2, 24, 1, 1)
INSERT [dbo].[phieu_dien_KTX] ([id], [ma_so_KTX], [so_dien_tieu_thu], [trang_thai], [id_don_gia]) VALUES (16, 3, 24, 1, 1)
INSERT [dbo].[phieu_dien_KTX] ([id], [ma_so_KTX], [so_dien_tieu_thu], [trang_thai], [id_don_gia]) VALUES (17, 1, 24, 1, 2)
INSERT [dbo].[phieu_dien_KTX] ([id], [ma_so_KTX], [so_dien_tieu_thu], [trang_thai], [id_don_gia]) VALUES (18, 2, 24, 1, 2)
INSERT [dbo].[phieu_dien_KTX] ([id], [ma_so_KTX], [so_dien_tieu_thu], [trang_thai], [id_don_gia]) VALUES (19, 3, 24, 1, 2)
INSERT [dbo].[phieu_dien_KTX] ([id], [ma_so_KTX], [so_dien_tieu_thu], [trang_thai], [id_don_gia]) VALUES (20, 1, 10, 0, 3)
INSERT [dbo].[phieu_dien_KTX] ([id], [ma_so_KTX], [so_dien_tieu_thu], [trang_thai], [id_don_gia]) VALUES (21, 2, 10, 0, 3)
INSERT [dbo].[phieu_dien_KTX] ([id], [ma_so_KTX], [so_dien_tieu_thu], [trang_thai], [id_don_gia]) VALUES (22, 3, 10, 0, 3)
SET IDENTITY_INSERT [dbo].[phieu_dien_KTX] OFF
GO
SET IDENTITY_INSERT [dbo].[phieu_nuoc_KTX] ON 

INSERT [dbo].[phieu_nuoc_KTX] ([id], [ma_so_KTX], [id_don_gia], [luong_nuoc_tieu_thu], [trang_thai]) VALUES (14, 1, 1, 5, 1)
INSERT [dbo].[phieu_nuoc_KTX] ([id], [ma_so_KTX], [id_don_gia], [luong_nuoc_tieu_thu], [trang_thai]) VALUES (15, 2, 1, 5, 1)
INSERT [dbo].[phieu_nuoc_KTX] ([id], [ma_so_KTX], [id_don_gia], [luong_nuoc_tieu_thu], [trang_thai]) VALUES (16, 3, 1, 5, 1)
INSERT [dbo].[phieu_nuoc_KTX] ([id], [ma_so_KTX], [id_don_gia], [luong_nuoc_tieu_thu], [trang_thai]) VALUES (17, 1, 2, 5, 1)
INSERT [dbo].[phieu_nuoc_KTX] ([id], [ma_so_KTX], [id_don_gia], [luong_nuoc_tieu_thu], [trang_thai]) VALUES (18, 2, 2, 5, 1)
INSERT [dbo].[phieu_nuoc_KTX] ([id], [ma_so_KTX], [id_don_gia], [luong_nuoc_tieu_thu], [trang_thai]) VALUES (19, 3, 2, 5, 1)
INSERT [dbo].[phieu_nuoc_KTX] ([id], [ma_so_KTX], [id_don_gia], [luong_nuoc_tieu_thu], [trang_thai]) VALUES (20, 1, 3, 3, 0)
INSERT [dbo].[phieu_nuoc_KTX] ([id], [ma_so_KTX], [id_don_gia], [luong_nuoc_tieu_thu], [trang_thai]) VALUES (21, 2, 3, 3, 0)
INSERT [dbo].[phieu_nuoc_KTX] ([id], [ma_so_KTX], [id_don_gia], [luong_nuoc_tieu_thu], [trang_thai]) VALUES (22, 3, 3, 3, 0)
SET IDENTITY_INSERT [dbo].[phieu_nuoc_KTX] OFF
GO
SET IDENTITY_INSERT [dbo].[phong_KTX] ON 

INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (1, 5, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (2, 5, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (3, 5, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (4, 5, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (5, 1006, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (6, 1007, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (7, 1007, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (8, 1006, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (9, 5, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (10, 5, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (11, 1008, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (12, 6, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (13, 6, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (14, 1008, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (15, 6, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (16, 6, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (17, 1009, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (18, 7, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (19, 1009, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (20, 7, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (21, 7, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (22, 7, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (23, 1008, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (24, 1006, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (25, 7, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (27, 7, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (32, 1006, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (33, 6, 1)
INSERT [dbo].[phong_KTX] ([id], [loai_KTX], [trang_thai]) VALUES (34, 1006, 1)
SET IDENTITY_INSERT [dbo].[phong_KTX] OFF
GO
SET IDENTITY_INSERT [dbo].[quyen] ON 

INSERT [dbo].[quyen] ([id], [quyen]) VALUES (1, N'student')
INSERT [dbo].[quyen] ([id], [quyen]) VALUES (2, N'admin')
INSERT [dbo].[quyen] ([id], [quyen]) VALUES (3, N'censor')
INSERT [dbo].[quyen] ([id], [quyen]) VALUES (1003, N'owner')
SET IDENTITY_INSERT [dbo].[quyen] OFF
GO
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn', NULL, 0, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$o/TxqscJSwTKgtUzL2hOb.Fz09aTSRCP/J7xBuz62EvTRdV4DSX5i', 1, 0)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn018', N'Nguyễn Đăng Bắc', 1, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$7wnBdxzuXi57FQE5YgxSSeUkYYyjpu5mcYnagJfsWZBaHvYL8Fe42', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn039', N'Nguyen Ngoc Duc', 1, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$sUHMHAFlp/FpGcwnowiaU.8gLc1jcSXRbqhRMdE7IKs0QTu4jK3TO', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn075', N'Huỳnh Quang Trần', 1, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$BuwpY5KXgodzBiDuRI2EIutwmLJIJGR9kOjxfUw2VSeJTjPhCTZjm', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn126', N'Thanh Nhật', 1, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$GuGGP/TQBSxcaE1iTEajYO1VmjboaxBMHkqa5VsgBJBtdE6HCoowy', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn157', N'Phạm Minh Quang', 0, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$bIYZ2d98xC5hrbmb.KtpSeLsXUFCf5/4dpm2HVllKHoFv/Q9uyAUC', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn211', N'Le Van A', 0, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$TCs6HTXpBWJ.00KzmsVDuOAy/YQAagERzZv9V98xHJeTvUCVJtOl2', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn213', N'Le Thanh Trung', 1, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$gSc9itMw/53zuPva8EJ6auryXaF61jUsrqCKUihEiU8iVrBwAh0Dq', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn214', N'Ho Duc Trung', 1, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$LT1lqZdSNVHMKSJCZYzb9.Ca3JFLuTBea05afWDktop.x.1ZfW/8i', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn300', N'Nguyen Van Duc', 1, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$Tz4Kap3Nxq/rIhY3vEi6/uYxv0zRq2hhuninZNKCQBeb2s52nC4wS', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn301', N'Tran Quang Ngoc Huynh', 0, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$6V3r/yVMglOop.Fck3wZkuYfLhxXWr6tHDzittgxcSeGA0VKSso8e', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn302', N'Le Van Williams', 1, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$ZRy5pID52gV.pGFUHfDgKOZleEvQh3nGj.TlfLvW9iwdsui4wxpQe', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn303', N'Emma Johnson', 1, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$IZZYZyrtLrlB8NR3VKS2Y.AkOa3x/ML2QAjdLcSdpbkMiuhQE2jSm', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn304', N'Olivia Duc', 0, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$n8bb4meoG0BTcBvzCE6iG.eSwpeiGhw.4OCLn3drEnE3GyrxRCPZq', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn305', N'Le Van Thao', 1, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$mJf0NLMrSjITIv56RRe7t.FCbdDsDfPqs1nib.rV2D8XMiLBs1Q.O', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn306', N'Le Thanh Trung', 0, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$qEfQA5d6WnCJghl8y9N6TefY4naqMyZqQLvrcX.YAvT6tQhVBxNfm', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn307', N'John Johnson', 0, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$dKzBd1fpgTQac4j0bSmeEep68yp07Ryb0LxB1bKnYqSuQKEBA0z0K', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn308', N'Tran Quang Ngoc Lam', 1, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$BJmBnuDwcw8ybm6wi61wV.2y1M/l0Ponk5Janx.zzCPwZ2XnL8ZAa', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn309', N'Olivia Thao', 0, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$YlnstGpSYoWHpqk3OfpU0OdjGJSRzYI1cJZihYraG.P7VFhU4/9S.', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn310', N'Le Van Nam', 1, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$Zu0Icw/4gLpu904mqBYDYeYlpcD3HvLGrqtWgLJ.QOOg5aMqLXRV.', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn311', N'Nguyen Thi Trung', 0, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$rhd.K2wyV3yGaakDLyeFu.7Sam/u7IBpTRToQ6RT2Ku21to430b..', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn312', N'Tran Quang Ngoc Son', 1, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$YinuK.Ch9oNrrC9asPl3GuVmUmak7rjqIkazpE5Xu8G7FwpiCxK0W', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn313', N'Olivia Trung', 0, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$q3lf2Va5D6//1Ws/bp/4TOC1/1oIFZM9RqoqMpkfnhi58.2jiIswK', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn314', N'Tran Quang Ngoc Smith', 0, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$zeelICfOP.ldeFy6JeBJ..dDOEKGXbOV9GricTV8.lnejnFhB.fDO', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn315', N'Le Van Jones', 1, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$y6w0tFDHFDAoM6a4EOgDn.cMUwWX5sYGWuPbcZXZXEBevdH2iEA9e', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn316', N'Nguyen Van Thao', 0, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$Rn8g7ZbuImYV0u/V/pn5weIuzrBTiFkgrzOkqTyH0zSgeOdd0eOXS', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn317', N'Tran Thi Smith', 0, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$EMGwkUCNT5KziZJA4VkJf.c7Vm2bpy.EsD3XIe..5e6wYVyPLIjxe', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn318', N'Ho Duc Doe', 0, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$DCQm..CoROTN3Fuo0bH/S.29Kap5OB4bt/eKOVqybF1.ruRb4SGgi', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn319', N'Le Thi Thao', 1, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$1UxgqdxcrkV3/77bcNpcbeGTLzwlkA2bUcERFA7oJ7f6g6nr/QJD2', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn320', N'Nguyen Ngoc Johnson', 1, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$mPNKtFStugRYRhWP00Ht2.jC9JQjiPm4j6zNGogy8iifVqf8DNeIO', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn321', N'Tran Van Doe', 0, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$Bp5qe3nMNDhTDP592xiFEOT4o/sKrC9uHStp3xEiupKsx2O7d2SBK', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn322', N'Nguyen Van Johnson', 1, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$LurQ3CJpXKLe/ikv.Fb8N.DfxvDkz7vrNbKMz8K04jJIg2xwIkpf.', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn323', N'Nguyen Thi Son', 1, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$NHHedrJCA8rIk3ZGE7SLLeaisdB37Yx8ePHrLgbHYaaDY7IJ2epA.', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn324', N'Nguyen Van Nam', 0, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$qgVDRzRAdLoMpXhSXF1krOlXkfq3RLljQ2gnPgJA/UdkfNK5ufJ8u', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn325', N'Tran Quang Ngoc Lam', 1, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$R6p8KjDCocncqA65VyNLuOAbap39qYumrapKE7EpcaEIeK2Hn5TIa', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn326', N'Ho Duc Thanh', 0, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$yFVmjgxSmPcFh0QoTjY6POafLL4QnT78ih5D/LWZcxzZ1KRPOYcmS', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn327', N'Tran Van Lam', 0, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$GnXqGtzGFGMwkDQm7pDeOOM9ScbNaNhwA0I3FK3qjUC3YCo6dL6Ji', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn328', N'Le Van Son', 1, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$nKpkIA3UBKYDzhlg.cnA4ObMfBxdPShViHrCf5FcAr0eEX3pqCDyG', 1, 1)
INSERT [dbo].[students] ([MSSV], [ho_ten], [gioi_tinh], [ngay_thang_nam_sinh], [CMND], [SDT], [mail], [password], [phan_quyen], [trang_thai]) VALUES (N'n19dccn329', N'Nguyen Thi Doe', 0, CAST(N'2001-02-02' AS Date), N'123456789', N'0123456789', N'n19dccn018@student.ptithcm.edu.vn', N'$2a$10$IeAeErA.v4iTZ63K0r5YPuo90CX7lRKdIomU7fe7irpPNShgSiDLq', 1, 1)
GO
SET IDENTITY_INSERT [dbo].[term] ON 

INSERT [dbo].[term] ([id], [ngay_mo_dang_ky], [ngay_ket_thuc_dang_ky], [ngay_ket_thuc], [han_dong_phi]) VALUES (1, CAST(N'2023-02-08' AS Date), CAST(N'2023-02-28' AS Date), CAST(N'2023-05-30' AS Date), 5)
INSERT [dbo].[term] ([id], [ngay_mo_dang_ky], [ngay_ket_thuc_dang_ky], [ngay_ket_thuc], [han_dong_phi]) VALUES (6, CAST(N'2023-05-10' AS Date), CAST(N'2023-05-30' AS Date), CAST(N'2023-10-30' AS Date), 5)
INSERT [dbo].[term] ([id], [ngay_mo_dang_ky], [ngay_ket_thuc_dang_ky], [ngay_ket_thuc], [han_dong_phi]) VALUES (7, CAST(N'2023-10-10' AS Date), CAST(N'2023-10-30' AS Date), CAST(N'2024-02-28' AS Date), 5)
SET IDENTITY_INSERT [dbo].[term] OFF
GO
ALTER TABLE [dbo].[hop_dong_KTX] ADD  CONSTRAINT [DF_hop_dong_KTX_trang_thai]  DEFAULT ((0)) FOR [trang_thai]
GO
ALTER TABLE [dbo].[phong_KTX] ADD  CONSTRAINT [DF_phong_KTX_trang_thai]  DEFAULT ((1)) FOR [trang_thai]
GO
ALTER TABLE [dbo].[students] ADD  CONSTRAINT [DF_students_phan_quyen]  DEFAULT ((1)) FOR [phan_quyen]
GO
ALTER TABLE [dbo].[students] ADD  CONSTRAINT [DF_students_trang_thai]  DEFAULT ((0)) FOR [trang_thai]
GO
ALTER TABLE [dbo].[admins]  WITH CHECK ADD  CONSTRAINT [FK_admins_quyen] FOREIGN KEY([phan_quyen])
REFERENCES [dbo].[quyen] ([id])
GO
ALTER TABLE [dbo].[admins] CHECK CONSTRAINT [FK_admins_quyen]
GO
ALTER TABLE [dbo].[hop_dong_KTX]  WITH CHECK ADD  CONSTRAINT [FK_hop_dong_KTX_phong_KTX] FOREIGN KEY([phong_KTX])
REFERENCES [dbo].[phong_KTX] ([id])
GO
ALTER TABLE [dbo].[hop_dong_KTX] CHECK CONSTRAINT [FK_hop_dong_KTX_phong_KTX]
GO
ALTER TABLE [dbo].[hop_dong_KTX]  WITH CHECK ADD  CONSTRAINT [FK_hop_dong_KTX_sinh_vien] FOREIGN KEY([MSSV])
REFERENCES [dbo].[students] ([MSSV])
GO
ALTER TABLE [dbo].[hop_dong_KTX] CHECK CONSTRAINT [FK_hop_dong_KTX_sinh_vien]
GO
ALTER TABLE [dbo].[hop_dong_KTX]  WITH CHECK ADD  CONSTRAINT [FK_hop_dong_KTX_term] FOREIGN KEY([id_term])
REFERENCES [dbo].[term] ([id])
GO
ALTER TABLE [dbo].[hop_dong_KTX] CHECK CONSTRAINT [FK_hop_dong_KTX_term]
GO
ALTER TABLE [dbo].[phieu_dien_KTX]  WITH CHECK ADD  CONSTRAINT [FK_dien_nuoc_KTX_phong_KTX] FOREIGN KEY([ma_so_KTX])
REFERENCES [dbo].[phong_KTX] ([id])
GO
ALTER TABLE [dbo].[phieu_dien_KTX] CHECK CONSTRAINT [FK_dien_nuoc_KTX_phong_KTX]
GO
ALTER TABLE [dbo].[phieu_dien_KTX]  WITH CHECK ADD  CONSTRAINT [FK_phieu_dien_KTX_gia_dien_theo_thang] FOREIGN KEY([id_don_gia])
REFERENCES [dbo].[gia_dien_theo_thang] ([id])
GO
ALTER TABLE [dbo].[phieu_dien_KTX] CHECK CONSTRAINT [FK_phieu_dien_KTX_gia_dien_theo_thang]
GO
ALTER TABLE [dbo].[phieu_nuoc_KTX]  WITH CHECK ADD  CONSTRAINT [FK_phieu_nuoc_KTX_gia_nuoc_theo_thang] FOREIGN KEY([id_don_gia])
REFERENCES [dbo].[gia_nuoc_theo_thang] ([id])
GO
ALTER TABLE [dbo].[phieu_nuoc_KTX] CHECK CONSTRAINT [FK_phieu_nuoc_KTX_gia_nuoc_theo_thang]
GO
ALTER TABLE [dbo].[phieu_nuoc_KTX]  WITH CHECK ADD  CONSTRAINT [FK_phieu_nuoc_KTX_phong_KTX] FOREIGN KEY([ma_so_KTX])
REFERENCES [dbo].[phong_KTX] ([id])
GO
ALTER TABLE [dbo].[phieu_nuoc_KTX] CHECK CONSTRAINT [FK_phieu_nuoc_KTX_phong_KTX]
GO
ALTER TABLE [dbo].[phong_KTX]  WITH CHECK ADD  CONSTRAINT [FK_phong_KTX_loai_KTX] FOREIGN KEY([loai_KTX])
REFERENCES [dbo].[loai_KTX] ([id])
GO
ALTER TABLE [dbo].[phong_KTX] CHECK CONSTRAINT [FK_phong_KTX_loai_KTX]
GO
ALTER TABLE [dbo].[students]  WITH CHECK ADD  CONSTRAINT [FK_user_quyen] FOREIGN KEY([phan_quyen])
REFERENCES [dbo].[quyen] ([id])
GO
ALTER TABLE [dbo].[students] CHECK CONSTRAINT [FK_user_quyen]
GO
ALTER TABLE [dbo].[thong_bao_KTX]  WITH CHECK ADD  CONSTRAINT [FK_thong_bao_KTX_admins] FOREIGN KEY([nguoi_tao])
REFERENCES [dbo].[admins] ([MSCB])
GO
ALTER TABLE [dbo].[thong_bao_KTX] CHECK CONSTRAINT [FK_thong_bao_KTX_admins]
GO
