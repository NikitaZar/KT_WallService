## Задача №1 - Посты

Наконец мы добрались до ООП и можем уже не только решать вычислительные задачи, но и моделировать целые системы.

На лекции мы разобрали упрощённый пример того, как может выглядеть пост, давайте же посмотрим на то, как он выглядит на самом деле. Возьмите себе за правило анализировать системы, с которыми вы работаете в реальной жизни, и продумывать, как бы сделали вы.

В качестве примера возьмём всё тот же VKontakte: https://vk.com/dev/objects/post Если страница недоступна, воспользуйтесь [копией](assets/post.pdf) из каталога [assets](assets)).

Что мы хотим получить:
1. Data класс `Post`.
1. Объект `WallService`, который внутри себя хранит посты в массиве.

Итог: у вас должен быть репозиторий на GitHub, в котором расположен ваш Gradle-проект. Автотесты также должны храниться в репозитории.


## Задача №2 - Wall

Теперь нужно разобраться с функциональностью сервиса, отвечающего за стены пользователей: https://vk.com/dev/wall ([копия](assets/wall.pdf)).

Нас будут интересовать следующие методы:
1. Создание записи.
1. Обновление записи.

### Создание записи

Как он должен работать:
1. Он должен добавлять запись в массив, но при этом назначать посту уникальный среди всех постов идентификатор.
1. Возвращать пост с уже выставленным идентификатором.


### Обновление записи

Как он должен работать:
1. Он должен находить среди всех постов запись с тем же `id`, что и у `post` и обновлять все свойства, кроме `id` владельца и даты создания.
1. Если пост с таким `id` не найден, то ничего не происходит и возвращается `false`, в противном случае - возвращается `true`.

## Задача №3 - Wall Tests

Куда же без автотестов? Правильно, никуда. Нужно написать автотесты на ваши методы:
* на `add` - всего один, который проверяет, что после добавления поста `id` стал не равным 0.
* на `update` - целых два:
    - изменяем пост с существующим `id`, возвращается `true`;
    - изменяем пост с несуществующим `id`, возвращается `false`.
    
Итог:
1. У вас должен быть репозиторий на GitHub, в котором будет ваш Gradle-проект.
1. К репозиторию должен быть подключён GitHub Actions.
1. Сборка должна быть «зелёной» — ваши тесты должны проходить.
1. Необязательно: попробуйте обеспечить максимальное покрытие тестируемых функций. Проанализируйте, какой код в ваших функциях не используйте и почему, по возможности либо избавьтесь от него, либо допишите автотесты.