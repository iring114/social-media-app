@echo off
echo ===================================
echo 社交媒體應用啟動腳本
echo ===================================

echo 選擇啟動模式：
echo 1. 開發模式（前後端分離運行）
echo 2. 生產模式（構建前端並啟動後端）
echo 3. 僅啟動後端
echo 4. 僅啟動前端開發服務器

set /p mode=請選擇（1-4）：

if "%mode%"=="1" (
    echo ===================================
    echo 啟動開發模式...
    echo ===================================
    start cmd /k "echo 啟動後端... && mvn spring-boot:run"
    timeout /t 10
    start cmd /k "echo 啟動前端... && cd frontend && npm run serve"
    echo 開發模式啟動完成！
    echo 後端：http://localhost:8080
    echo 前端：http://localhost:8081
) else if "%mode%"=="2" (
    echo ===================================
    echo 啟動生產模式...
    echo ===================================
    echo 構建前端...
    cd frontend
    call npm install
    call npm run build
    cd ..
    echo 前端構建完成！
    echo 啟動後端...
    mvn spring-boot:run
) else if "%mode%"=="3" (
    echo ===================================
    echo 僅啟動後端...
    echo ===================================
    mvn spring-boot:run
) else if "%mode%"=="4" (
    echo ===================================
    echo 僅啟動前端開發服務器...
    echo ===================================
    cd frontend
    npm run serve
) else (
    echo 無效的選擇！
)

pause