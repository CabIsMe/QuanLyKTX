ALTER TABLE account
DROP COLUMN ma_so;

ALTER TABLE gia_nuoc_theo_thang
ALTER COLUMN gia_nuoc float;

-- Vô Table "account" --> Design --> Xóa Column "ma_so"
-- Vô Diagram --> Tìm table "account", kéo Column "username" vào Table "sinh_vien", Column "MSSV",
-- Table "account", kéo Column "username" vào Table "quan_tri_vien", Column "MSCB".

ALTER TABLE account
ALTER COLUMN password varchar(MAX)

ALTER TABLE quan_tri_vien
ADD FOREIGN KEY (MSCB) REFERENCES account(username)
