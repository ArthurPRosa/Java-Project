Ensimag 2A POO - TP 2022/23
============================

Pour lancer les tests, faites `make testInvader` / `make testLecteurDonnees`

Toutes les stratégies sont dans le fichier `Strategies.java`, sous forme de plusieurs classes dans le même fichier, et de même pour `Scenarios`.

* Pour les lancer depuis un IDE, il suffit de chosir la bonne méthode main en target.
  * **Sur IntelliJ Idea**
    1. Appuyer sur le bouton Run au dessus de la méthode main choisie.
  * **Sur Eclipse**
    1. Appuyer sur Run >> Run Configurations, Créer un nouveau `Java Application` sur la fenêtre qui s'ouvre.
    2. Choisir le bon Projet et la bonne classe.
  * **Sur VSCode**
    1. S'assurer d'avoir une extension Java (Celle de Microsoft par exemple).
    2. Appuyer sur le bouton Run au dessus de la méthode main choisie.
* Pour les lancer depuis le makefile
  Il suffit de se mettre sur le répertoire racine (probablement poo-project) et faire l'une des commandes suivantes :
    1. `$ make scenario0`
    2. `$ make scenario1`

    3. `$ make strategieElem`
    4. `$ make strategieAvancee`
    5. `$ make strategieAmelioree`

  Dans ce cas, vous pouvez faire `$ make ... carte=fichierCarte` pour changer la carte utilisée. (utile seulement pour les stratégies)
  
* Les 2 scénarios sont ceux proposés par le sujet.
* Les 2 1ères stratégies sont celles proposées par le sujet, la 3è est une version de la stratégie avancée où chaque robot est envoyé vers l'incendie le plus proche de lui. Vous pouvez vous reporter aux différents `FirefighterChief` qui implémentent chacun une stratégie, avec une javadoc plus détaillée.
