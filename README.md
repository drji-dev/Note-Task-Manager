# NoteApp — приложение для заметок

REST API на Java Spring Boot и PostgreSQL для создания и организации заметок.

**Автор:** cyxapuk

---

## Функции

- Создание, редактирование и удаление заметок
- Теги с выбором цвета (автоматически создаются при добавлении к заметке)
- Подсчёт количества заметок для каждого тега
- Валидация входящих данных
- Docker-контейнеризация
- Полностью открытое API (без авторизации)

---

## Технологии

**Бэкенд**
- Java 21
- Spring Boot 4.0
- Spring Data JPA
- Spring Validation
- PostgreSQL 15
- Hibernate
- Maven
- Lombok

**Инфраструктура**
- Docker
- Docker Compose
- Git

---

## Установка и запуск

### Требования
- Java 21
- Docker и Docker Compose (опционально)

### Быстрый старт (через Docker)

```bash
git clone https://github.com/cyxapuk/note-app
cd note-app
./mvnw clean package -DskipTests
docker-compose up --build
```
Приложение будет доступно по адресу: http://localhost:8080

## Запуск без Docker

    Создайте базу данных PostgreSQL:

```sql
CREATE DATABASE noteapp;
```
    Настройте подключение в src/main/resources/application.yml:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/your_DBNAME
    username: your_username
    password: your_password
```
    Запустите приложение:

```bash
./mvnw spring-boot:run
```
## Переменные окружения (для Docker)

Создайте файл .env в корне проекта:
```env
DB_NAME=your_DBNAME
DB_USERNAME=your_username
DB_PASSWORD=your_password
```

### REST API эндпоинты
## Теги
| Метод | URL | Описание |
|-------|-----|----------|
| GET | `/api/tags/{id}` | Получить тег по ID |
| POST | `/api/tags` | Создать тег |
| PUT | `/api/tags/{id}` | Обновить тег |
| DELETE | `/api/tags/{id}` | Удалить тег |

## Заметки
| Метод | URL | Описание |
|-------|-----|----------|
| GET | `/api/notes/{id}` | Получить заметку по ID |
| POST | `/api/notes` | Создать заметку |
| PUT | `/api/notes/{id}` | Обновить заметку |
| DELETE | `/api/notes/{id}` | Удалить заметку |

## Примеры запросов

# Создать тег
```bash
curl -X POST http://localhost:8080/api/tags -H "Content-Type: application/json" -d '{"name":"your_name","color":"your_color"}'
```
# Создать заметку
```bash
curl -X POST http://localhost:8080/api/notes -H "Content-Type: application/json" -d '{"title":"your_title","content":"your_content","tagName":"your_tagName"}'
```
