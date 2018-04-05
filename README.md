# Тестовое задание РТК Лабс
## Инструкция по запуску
1. Установить postgresql (if not)
2. Создать пользователя webapp с пустым паролем
```bash
sudo psql -U postgres 
create user webapp password ''
\q [ENTER]
```
3. Создать базу данных webapp
4. Склонировать проект и перейти в каталог проекта
5. Выполнить в консоли команду
```bash
mvn install
```
6. Перейти в подкаталог target и выполнить 
```bash
java -jar testapp-1.0-SNAPSHOT.jar
```
7. Перейти в веб-браузере по адресу "http://localhost:8080"
