@echo off
echo 正在清理和重新构建项目...
cd /d %~dp0
call gradlew.bat clean
call gradlew.bat build
echo 构建完成!
pause
