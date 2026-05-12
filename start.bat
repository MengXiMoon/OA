@echo off
chcp 65001 >nul 2>&1
title OA Office System

echo.
echo   ========================================
echo         OA Office Automation System
echo         Ceramic White v2.0
echo   ========================================
echo.
echo   [1/2] Starting backend server...
start "OA-Server" cmd /c "cd /d %~dp0oa-server && mvn spring-boot:run -DskipTests"
echo   [2/2] Starting frontend server...
start "OA-Web" cmd /c "cd /d %~dp0oa-web && npx vite --host 0.0.0.0 --port 5173"

echo.
echo   ----------------------------------------
echo     Backend : http://localhost:8080
echo     Frontend: http://localhost:5173
echo     Account : admin / 123456
echo   ----------------------------------------
echo.
echo   Waiting for services to start...
echo   Open http://localhost:5173 in browser
echo.
pause
