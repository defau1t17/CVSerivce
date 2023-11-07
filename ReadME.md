# CVService

## Тестовое задание на Java Developer Trainee от Krainet

## Стек

* Java 17
* Spring Boot 3.1.5
* Spring Data JPA
* PostgreSQL
* Docker Compose
* Swagger 3
* Slf4j

## Реаизовано

* ### Направление

1. [X] Добавление, изменение, отображение списка направлений с использованием фильтрации, сортировки и постраничного
   ввода.


* ### Тесты

1. [X] Добавление, изменение, отображение списка тестов с использованием фильтрации, сортировки и постраничного ввода


* ### Кандидаты

1. [X] Добавление, изменение, отображение списка направлений с использованием фильтрации, сортировки и постраничного
   ввода.


* ### Тесты Кандидатов

1. [X] Добавление, изменение, отображение списка теста кандидатов с использованием фильтрации, сортировки и
   постраничного ввода.

Добавление и изменение происходит за счет отправки AJAX запроса на REST API, где оно сначала валидируется, если объект
прошел валидацию, он добавляется в базу данных. Отображение и фильтрации реализованы за счет использования спецификаций,
которые ищют в базе данных похожие данные.Реализованна возможность просмотра CV файлов в браузере, кликнув на ссылку. Все изменения в базе данных обрабатываются миграциями **Spring Data JPA**


## Запуск
 

Перейдите в папку с проектом 

Пропишите команду 

`docker-compose --build`

