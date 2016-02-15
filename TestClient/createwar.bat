@echo on


cd  %PATH_TO_UZIPPED_APP%
echo %cd%

%PATH_TO_ZIP_EXE%\zip.exe -r  %PATH_TO_WAR%  *

:end
