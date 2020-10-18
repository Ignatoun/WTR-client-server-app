USE wtrtest;
DELETE FROM wtrtest.ReportDetails WHERE reportDetailsId <> 0;
DELETE FROM Location WHERE locationId <> 0;
DELETE FROM Factor WHERE factorId <> 0;
DELETE FROM Report WHERE userId <> 0;

SET foreign_key_checks=1;