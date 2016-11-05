@echo off
:begin
@CHOICE /M "Press Y to continue, N to abort."
@IF ERRORLEVEL ==2 GOTO end
@IF ERRORLEVEL ==1 GOTO run
@goto end
:run
echo ...
echo Removing files...
echo Deleting preferences.json.
del preferences.json
echo Deleting config.json.
del config.json
echo Deleting taskmanager.log.0.
del taskmanager.log.0
echo Deleting taskmanager.log.0.1.
del taskmanager.log.0.1
echo ...
echo Preparing for new .jar buid...
echo Deleting build/jar folder.
rmdir /S /Q build\jar
:: rmdir /S /Q src\test\data\sandbox
call "Generate Jar build.bat"
echo ...
echo Preparing to update collated folder...
echo Deleting collated folder.
rmdir /S /Q collated
call "Collate script.bat"
@goto end
:end
pause
exit
