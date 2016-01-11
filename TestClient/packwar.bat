@echo on


cd  %PATH_TO_UZIPPED_APP%
echo %cd%
jar.exe -cfv  %WAR_FILE%  *

:end
