# UI-тесты, проект №2
В данном проекте покрыт тестами UI веб-приложения [Stellar Burgers](https://stellarburgers.nomoreparties.site)
## В проекте используется:
* Java 11
* Maven
* JUnit 4
* RestAssured
* Selenium
* Allure
## Запуск тестов
Склонировать репозиторий
```
git clone https://github.com/MikeKotal/UITestsProject-2.git
```
Локально запустить тесты, лежат по следующему пути:
```
src/test/java/stellar_burger_ui
```
В проекте реализован запуск тестов в браузере Chrome, либо Yandex.Browser, логика реализована в классе **Initializer** для выбора конкретного браузера достаточно зайти в файл **.properties**:
```
src/test/java/resources/environment.properties
```
После чего достаточно поменять значение на одно из двух:
* Chrome
* Yandex

В случае, если выполнять запуск тестов через браузер Yandex, необходимо в интерфейсе **Constants** указать путь до браузера на локальной машине в константе **LOCAL_PATH_TO_YANDEX_BROWSER**

Для просмотра отчета в Allure, выполнить следующие комманды:
```
mvm clean test
mvn allure:serve
```