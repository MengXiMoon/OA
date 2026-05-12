@echo off
chcp 65001 >nul
title OA 办公系统

echo.
echo   ╔══════════════════════════════════════════╗
echo   ║                                          ║
echo   ║       OA 办公自动化系统                    ║
echo   ║       Ceramic White v2.0                 ║
echo   ║                                          ║
echo   ╚══════════════════════════════════════════╝
echo.
echo   [1/2] 启动后端服务...
start "OA-Server" cmd /c "cd oa-server && mvn spring-boot:run -DskipTests"
echo   [2/2] 启动前端服务...
start "OA-Web" cmd /c "cd oa-web && npx vite --host 0.0.0.0 --port 5173"

echo.
echo   ┌──────────────────────────────────────────┐
echo   │                                          │
echo   │   后端启动中: http://localhost:8080       │
echo   │   前端启动中: http://localhost:5173       │
echo   │                                          │
echo   │   默认账号: admin / 123456                │
echo   │                                          │
echo   └──────────────────────────────────────────┘
echo.
echo   等待服务启动后，打开浏览器访问 http://localhost:5173
echo.
pause
