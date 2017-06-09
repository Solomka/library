# Library

Построить веб-систему, поддерживающую заданную функциональность:
1.	На основе сущностей предметной области создать классы их описывающие.
2.	Классы и методы должны иметь отражающую их функциональность названия и должны быть грамотно структурированы по пакетам. 
3.	Оформление кода должно соответствовать Java Code Convention.
4.	Информацию о предметной области хранить в БД, для доступа использовать API JDBC с использованием пула соединений, стандартного или разработанного самостоятельно. В качестве СУБД рекомендуется MySQL или Derby.
5.	Приложение должно поддерживать работу с кириллицей (быть многоязычной), в том числе и при хранении информации в БД.
6.	Архитектура приложения должна соответствовать шаблону Model-View-Controller.
7.	При реализации алгоритмов бизнес-логики использовать шаблоны GoF: Factory Method, Command, Builder, Strategy, State, Observer etc.
8.	Используя сервлеты и JSP, реализовать функциональности, предложенные в постановке конкретной задачи.
9.	В страницах JSP применять библиотеку JSTL и разработать собственные теги.
10.	При разработке бизнес логики использовать сессии и фильтры.
11.	Выполнить журналирование событий, то есть информацию о возникающих исключениях и событиях в системе обрабатывать с помощью Log4j.
12.	Код должен содержать комментарии.



	Система Библиотека. 

Читатель имеет возможность осуществлять поиск и заказ Книг в Каталоге. Библиотекарь выдает Читателю Книгу на абонемент или в читальный зал. Книга может присутствовать в Библиотеке в одном или нескольких экземплярах.
