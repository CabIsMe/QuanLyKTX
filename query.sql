ALTER TABLE user
DROP COLUMN ma_so;

ALTER TABLE gia_nuoc_theo_thang
ALTER COLUMN gia_nuoc float;

-- Vô Table "user" --> Design --> Xóa Column "ma_so"
-- Vô Diagram --> Tìm table "user", kéo Column "username" vào Table "sinh_vien", Column "MSSV",
-- Table "user", kéo Column "username" vào Table "quan_tri_vien", Column "MSCB".

ALTER TABLE user
ALTER COLUMN password varchar(MAX)