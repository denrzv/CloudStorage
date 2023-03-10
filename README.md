# Cloud Storage

### Документация по сервису

#### Описание

REST сервис для обработки запросов на аутентификацию, выход, а также работу с файлами пользователя.

#### Эндпоинты:

* /login
* /logout

Для входа и выхода пользователя.

* /file
* /list

Для работы с файлами.

Запускается на порту 8081/TCP.

Создан в соответствии со [спецификацией](https://github.com/netology-code/jd-homeworks/blob/master/diploma/CloudServiceSpecification.yaml).

#### Запуск

Для запуска:

git clone https://github.com/denrzv/CloudStorage.git

Перейти в папку с проектом и выполнить:

* docker build -t cloudstorage .
* docker-compose up
* проверка работы выполняется через http.request тесты в проекте, также можно проверить с помощью готового FRONT [приложения]:(https://github.com/netology-code/jd-homeworks/blob/master/diploma/cloudservice.md) 
* если будете использовать готовый FRONT не забудьте поправить как указано в описании к нему порт BACK на 8081

#### Проверка
* Запустить Google Chrome без проверки CORS [инструкция](https://alfilatov.com/posts/run-chrome-without-cors/).
* Перейти в [веб-интерфейс](http://localhost:8080/login) сервиса перевода средств.
* Указать логин и пароль (предзагружены пользователи: test1@user.ru, test2@user.ru, test3@user.ru, пароль: 12345)
* Проверить работу с файлами