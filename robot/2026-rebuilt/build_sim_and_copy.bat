@echo off
echo Building sim-core...
cd /d "%~dp0..\sim-core"
call gradlew build -x test

echo Copying jar to 2026-rebuilt...
copy /Y build\libs\sim-core-1.0-SNAPSHOT.jar "..\2026-rebuilt\lib\"

echo Building 2026-rebuilt...
cd /d "%~dp0"
call gradlew compileJava
echo Done!
