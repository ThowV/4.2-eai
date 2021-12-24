# Week 3 - Opdracht 3

> Maak een schema van het uiteindelijke applicatie landschap. Geef een overzicht van de verschillende informatie stromen en bespreek aan de hand hiervan de volgende punten:

INSERT SCHEMA HIER

> Wat voor technieken worden er gebruikt voor de verbindingen.

De verschillende applicaties worden onderling aan elkaar verbonden met SOAP services, een REST API, activemq, database verbindingen en JMS die zijn opgesteld doormiddel van Anypoint studio .
Zijn er eventueel alternatieve technieken die beter geschikt zijn.

> Welke patterns worden er gebruikt.

Er wordt gebruik gemaakt van de volgende patterns: Queue pattern (p2p arrivalogger en de busInfo queue) maar ook publish subscribe (Infoborden).

> Beschrijf welke rol AnyPoint heeft in het proces (mediation of orchestration of beide).

Anypoint maakt gebruik van beide mediation en orchestration. Een voorbeeld van mediation is het ophalen van de accurate tijd van de python server en die door te geven aan de berichten. Maar ook orchestration omdat er berichten worden geconstructueerd voor verschillende services zoals de QBuzz web service of de ARRIVALOGGER.
