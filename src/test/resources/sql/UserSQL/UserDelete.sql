delete from app_user;
ALTER TABLE app_user ALTER COLUMN id RESTART WITH 1;
