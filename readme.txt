Verteilte Systeme

Backend:

Für das Backend wurde Spring Boot (Webflux mit Reactor, um die Abfragen in Non-Blocking zu bearbeiten) mit Graphql Dgs Netflix benutzt.
Da Graphql mit einem Schema gearbeitet wird, wird kein Swagger IO benötigt, weil die Schnittstellen sehr klar dokumentiert sind.

Angebundene Services:

	Wavescape: Assets Daten von Bitcoin, Ethereum, Waves und Totals

	Blockchair: es werden die news über BTC Waves und Ethereum geladen

	Alphavantage: History Data von BTC, Ethereum und Waves -> Api Key nötig

	Meaningcloud: Sentimental Analyse von News -> Gut, Schlecht oder Neutral -> Api Key nötig

Architektur:

	Web-> Anfragen an den Services und in Response umwandeln

	Service-> die Response verarbeiten und in Graphql Entities umwandeln

	Api-> Die Kommunikationsschnittstelle nach außen. Es wurden sowohl Queries als auch Subscriptions bereitgestellt, um stets aktuelle Daten per Websocket zu erhalte.

	       Über den URL-Endpunkt http://localhost:8080/playground kann man mit den Daten spielen, um besseres Verständnis zu verschaffen

	Config-> Alle nötige Konfigurationen werden hier erstellt.

		Außerdem gibt es unterschiedlich eingestellte Webclients, die man für die Services benutzen kann.

	Scheduler-> sind in den Services zu finden. Werden in unterschiedlichen Abständen ausgeführt. Die sind über Cron Expression eingestellt und können leicht 				abgeschaltet werden, um Testen zu können.


Frontend:

Für Frontend wurde Nodejs benutzt mit Axios, um Http Abfragen machen.
Da die Zeit knapp wurde konnten Die Subcriptions nicht angebunden werden, um die Daten in Echtzeit anzuzeigen.


Quellen:
https://www.baeldung.com/
https://stackoverflow.com/
https://netflix.github.io/dgs/
https://spring.io/guides/gs/reactive-rest-service/
https://projectreactor.io/
https://kotlinlang.org/
https://nodejs.org/
https://github.com/axios/axios

Deployment-URL: http://194.163.177.221:8080/index.html