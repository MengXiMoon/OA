#!/bin/bash

echo ""
echo "  ╔══════════════════════════════════════════╗"
echo "  ║                                          ║"
echo "  ║       OA 办公自动化系统                    ║"
echo "  ║       Ceramic White v2.0                 ║"
echo "  ║                                          ║"
echo "  ╚══════════════════════════════════════════╝"
echo ""

cd "$(dirname "$0")"

echo "  [1/2] 启动后端服务..."
cd oa-server && mvn spring-boot:run -DskipTests &
SERVER_PID=$!
cd ..

echo "  [2/2] 启动前端服务..."
cd oa-web && npx vite --host 0.0.0.0 --port 5173 &
WEB_PID=$!
cd ..

echo ""
echo "  ┌──────────────────────────────────────────┐"
echo "  │                                          │"
echo "  │   后端: http://localhost:8080             │"
echo "  │   前端: http://localhost:5173             │"
echo "  │                                          │"
echo "  │   默认账号: admin / 123456                │"
echo "  │                                          │"
echo "  └──────────────────────────────────────────┘"
echo ""
echo "  等待服务启动后，打开浏览器访问 http://localhost:5173"
echo "  按 Ctrl+C 停止所有服务"
echo ""

trap "kill $SERVER_PID $WEB_PID 2>/dev/null; exit" SIGINT SIGTERM
wait
