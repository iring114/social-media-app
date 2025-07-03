@echo off
echo ===================================
echo 社交媒體應用前端構建腳本
echo ===================================

echo 進入前端目錄...
cd frontend

echo 安裝依賴...
call npm install

echo 構建前端項目...
call npm run build

echo 前端構建完成！
echo 前端資源已自動部署到Spring Boot的靜態資源目錄。
echo 現在您可以運行後端應用來訪問完整的應用。

cd ..
echo ===================================
echo 如需運行後端應用，請執行：
echo mvn spring-boot:run
echo ===================================

pause