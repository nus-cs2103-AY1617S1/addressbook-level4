:begin
@CHOICE /M "Press Y to continue, N to abort."
@IF ERRORLEVEL ==2 GOTO end
@IF ERRORLEVEL ==1 GOTO run
@goto end
:run
REM Removing files
rmdir /S /Q collated
del preferences.json
del config.json
del taskmanager.log.0
del taskmanager.log.0.1
rmdir /S /Q build\jar
:: rmdir /S /Q src\test\data\sandbox
REM Running auto scripts
call "Generate Jar build.bat"
call "Collate script.bat"
@goto end
:end
