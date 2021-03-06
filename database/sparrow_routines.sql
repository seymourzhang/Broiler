CREATE FUNCTION s_f_getCodeName(in_code_type VARCHAR, in_biz_code VARCHAR) RETURNS VARCHAR;
CREATE FUNCTION s_f_getDayAgeByHouseBreedId(in_house_breed_id INT, in_date DATETIME) RETURNS INT;
CREATE FUNCTION s_f_getDayAgeByHouseId(in_house_id INT, in_date DATETIME) RETURNS INT;
CREATE FUNCTION s_f_getFarmBreedId(in_farm_id INT) RETURNS INT;
CREATE FUNCTION s_f_getFarmName(in_farm_id INT) RETURNS VARCHAR;
CREATE FUNCTION s_f_getHouseBreedId(in_house_id INT) RETURNS INT;
CREATE FUNCTION s_f_getHouseName(in_house_id INT) RETURNS VARCHAR;
CREATE FUNCTION s_f_getRecentAgeDateByHouseId(in_house_id INT, in_date_time VARCHAR, in_farm_breed_id INT) RETURNS VARCHAR;
CREATE PROCEDURE s_p_createFarmTask(in_apply_flag VARCHAR, in_farm_id INT);
CREATE PROCEDURE s_p_createHouseTask(in_farm_id INT, in_apply_flag VARCHAR, in_temp_id INT);
CREATE PROCEDURE s_p_createTargetMonitor(in_buzType VARCHAR, in_houseBreedId INT);
CREATE PROCEDURE s_p_dealMonitorAlarm();