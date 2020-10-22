# Vicabilling

Vicabilling és un petit programa de facturació que he fet a mida per a l'empresa [Assessors de Manteniment VICA, S.L.](http://vica.cat)
com a agraïment personal per l'excel·lent tracte rebut durant tot el temps que hi he tingut tracte i, a l'hora, com a 
exercici d'aprenentatge en el desenvolupament sota la plataforma de [JavaFX](https://en.wikipedia.org/wiki/JavaFX).

Malgrat això, tinc pocs dubtes que també pot servir per a d'altres *pime*, *autònoms*, *freelancers*, etc. I, en cas que
hi calgués alguna modificació, ací teniu el codi font del programa, a la vostra plena disposició.

## Característiques

### Càlcul automàtic

Els imports dels productes (o serveis) són calculats automàticament, en base als camps que han estat omplerts. Per tant,
la interfície disposa de quatre modes, en funció de quin sigui el camp mancant:

* **Mode normal** (o per defalt)
* **Mode de quantitat mancant**
* **Mode de preu unitari mancant**
* **Mode de descompte mancant**

### Maquetació automàtica

Els comptes resultants (factures o escandalls) són maquetats de manera automàtica, farcits amb la informació que
previament s'hagi especificat a la configuració. Logo de l'empresa, nom del client, etc.

### Salvaguarda dels comptes

Els comptes es poden desar i carregar, sempre que calgui. Són emmagatzemats en el format pròpi de l'aplicació
(**.vbp*). Això permet de poder fer la feina en diferents sessions i, si donat el cas, de poder compartir comptes entre
diferents sistemes.

### Interfície minimalista

La interfície ha estat pensada per a una màxima simplicitat d'ús. S'ha procurat de mantenir els apartats clarament
diferenciats, però sense que es perdi la cohesió global. A més a més, tots els butons, etiquetes i menús són el més 
autoexplicatius possible, encara que sense perdre la concisió.
