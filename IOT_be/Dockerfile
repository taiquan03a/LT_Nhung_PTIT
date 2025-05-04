# 1️⃣ Sử dụng image Maven để build ứng dụng
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app

# 2️⃣ Copy toàn bộ project vào container
COPY . .

# 3️⃣ Dùng Maven để build ứng dụng
RUN mvn clean package -DskipTests

# 4️⃣ Lấy file JAR từ bước build
FROM eclipse-temurin:17-jdk
WORKDIR /app

# 5️⃣ Copy file JAR từ bước build sang container runtime
COPY --from=build /app/target/*.jar app.jar

# 6️⃣ Copy file serviceAccountKey.json vào thư mục trong container
COPY src/main/resources/serviceAccountKey.json /app/src/main/resources/serviceAccountKey.json

## 7️⃣ Đặt biến môi trường trỏ đến file serviceAccountKey.json
#ENV GOOGLE_APPLICATION_CREDENTIALS=/app/config/serviceAccountKey.json

# 8️⃣ Cấu hình port ứng dụng chạy (nếu cần)
EXPOSE 8080

# 9️⃣ Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
