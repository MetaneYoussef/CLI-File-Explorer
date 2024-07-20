[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/TlW_pCD-)
# Projet PGLP

## Groupe
- BOUKEDJANI Imad (22305124)
- CORNEJO GUILLEN Oscar (22305517)
- METANE Youcef (22307378)
 
## Conception du projet


<h1 align="center">
  <b>
    <img src="./src/assets/images/logo4.png" width="50%"/><br>
  </b>
</h1>

## Développeurs
| ![Oscar](./src/assets/images/Oscar.jpg) | ![Youssef](./src/assets/images/Youcef.jpg) | ![Imad](./src/assets/images/imad.jpg) |
| --- | --- | --- |
| CORNEJO, Oscar, 22305517, 40 | METANE, Youcef, 22307378, 59 | BOUKEDJANI, Imad, 22305124, 05 |


## Repositoire

https://github.com/uvsq-hal2324/pglp-prj-imosyo

## Table des matières

- [Projet PGLP](#projet-pglp)
  - [Groupe](#groupe)
  - [Conception du projet](#conception-du-projet)
  - [Développeurs](#développeurs)
  - [Repositoire](#repositoire)
  - [Table des matières](#table-des-matières)
  - [Description](#description)
  - [Prise en charge du système d'exploitation](#prise-en-charge-du-système-dexploitation)
  - [Compilation et exécution de l'application](#compilation-et-exécution-de-lapplication)
    - [Compiler le projet](#compiler-le-projet)
    - [Exécuter l'application](#exécuter-lapplication)
  - [Manuel utilisateur](#manuel-utilisateur)
    - [Catalogue de commandes](#catalogue-de-commandes)
    - [Les mises à jours du fichier des annotations](#les-mises-à-jours-du-fichier-des-annotations)
    - [Les mises à jours du NER](#les-mises-à-jours-du-ner)
    - [La commande \[\<NER\>\] visu](#la-commande-ner-visu)
    - [Pistes futures du projet d'étude](#pistes-futures-du-projet-détude)
      - [Commandes utiles à ajouter](#commandes-utiles-à-ajouter)
      - [Améliorations proposées du côté de l'utilisateur](#améliorations-proposées-du-côté-de-lutilisateur)
      - [Evolutions futures possibles](#evolutions-futures-possibles)
  - [Manuel technique](#manuel-technique)
    - [Rapport de couverture de code par les tests](#rapport-de-couverture-de-code-par-les-tests)
    - [Bibliothèques utilisées](#bibliothèques-utilisées)
    - [Classes](#classes)
      - [App.java](#appjava)
      - [Annnotation.java](#annnotationjava)
      - [ContentToMemory.java](#contenttomemoryjava)
      - [DirectoryNavigation.java](#directorynavigationjava)
      - [FileDetails.java](#filedetailsjava)
      - [FileManipulation.java](#filemanipulationjava)
      - [FileOperations.java](#fileoperationsjava)
      - [FolderManipulation.java](#foldermanipulationjava)
      - [NavigationFunctions.java](#navigationfunctionsjava)
      - [Parser.java](#parserjava)
    - [Traitements des commandes saisies par l'utilisateur](#traitements-des-commandes-saisies-par-lutilisateur)
    - [Améliorations envisagées](#améliorations-envisagées)
  - [Resources](#resources)
  - [Crédits](#crédits)
  - [Licence](#licence)


## Description

<p align="justify"> Le projet PGLP consistera à reprendre le projet de compléments de programmation du semestre dernier et d'en faire une conception respectant les principes et les patterns vus ce semestre (Singleton, Builder, Composite ... ) </p>

## Prise en charge du système d'exploitation

<img src="./src/assets/images/windows.png" alt="Windows" width="80" height="80"> | <img src="./src/assets/images/linux.png" alt="Linux" width="80" height="80"> |
--- | --- |
✔ | ✔ |

## Compilation et exécution de l'application

### Compiler le projet

Sous Linux:

```bash
$ ./mvnw package
```

Sous Windows:

```bash
> ./mvnw.cmd package
```

### Exécuter l'application

```bash
$ java -jar target/builder-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Manuel utilisateur

### Catalogue de commandes

Le système de gestion des fichiers de commandes possède diverses fonctions et une liste variée de commandes. De manière générale, les actions de l’utilisateur sont saisies au clavier sous la forme [\<NER\>] [<commande>] [<nom>]. Les crochets signifient "optionnel".

La structure générale du système de gestion de fichiers est la suivante:

| Commande  | Description  |
| -------- | ----------  | 
| ls [\<directory\>]  | Répertorie les fichiers du répertoire actuel ou d'un répertoire défini |
| ..  | Utilisé pour faire référence au répertoire parent du répertoire actuel |
| \<ner\>  | Désigne un nouvel élément avec le NER saisi comme élément courant |
| [\<NER\>] .  | Permet de référencer l'élément du répertoire courant dont le NER est égal à celui saisi, si aucun NER n'est renseigné, le dernier élément du répertoire est référencé |
| pwd  | Affiche le chemin du répertoire courant |
| [\<NER\>] copy  | Copie le fichier ou répertoire correspondant au NER saisi, si un NER n'est pas renseigné il correspond correspond à sa dernière valeur |
| [\<NER\>] cut  | Supprime le fichier ou répertoire correspondant au NER saisi, si aucun NER n'est renseigné il correspond à sa dernière valeur |
| paste  | Colle le fichier ou le répertoire stocké dans le répertoire courant. S'il y en a un avec le même nom, "-copy" est concaténé au nom du fichier ou du répertoire |
| mkdir <nom>  | Crée le répertoire nommé <nom> dans le répertoire courant |
| [\<NER\>] visu  | Permet de voir le contenu d’un fichier texte. Si le fichier n’est pas de type texte, sa taille est affichée  |
| find <nom fichier>  | Recherche dans toutes les sous répertoires du répertoire courant, le(s) fichier(s) et les affiche  |
| [\<NER\>] + \"text\"  | Permet d'ajouter le texte à la note correspondant au NER saisi ou concaténer au texte existant sur l’ER |
| [\<NER\>] - [\"text\"]  | Permet de supprimer le texte saisi de la note associée au NER, si aucun texte n'est saisi, la commande supprime tout le texte associé au ER |

### Les mises à jours du fichier des annotations

Actuellement, lors de la création de la première annotation d'un fichier dans un répertoire, via la commande [\<NER\>] + \"text\", le fichier [notes.txt](#les-mises-à-jours-du-fichier-des-annotations) associé au répertoire est créé au format colonne avec NER et l'annotation associée comme attributs, exemple :

```text
15	file_name1  note
14	file_name2  note_2
```

Ensuite en utilisant la commande [\<NER\>] - [\"text\"] on peut éliminer l'annotation associée à chaque fichier, comme suit à titre d'exemple :

```bash
$ 15 -
```

Avec le fichier de l'exemple précédent, l'état actuel du fichier notes.txt serait le suivant :

```text
14	file_name2  note_2
```


Une autre façon de visualiser la valeur des annotations associées au répertoire courant consiste à utiliser la commande ls dont l'affichage contient la colonne d'annotation.

Le fichier notes.txt varie également lors de l'utilisation des commandes "cut" et "paste" d'éléments entre répertoires. Dans les deux cas, un fichier ou un répertoire est ajouté ou supprimé dans le répertoire courant et donc un réajustement est effectué dans le fichier notes.txt.

### Les mises à jours du NER

Il était nécessaire de mettre à jour le numéro (NER) chaque fois qu'une commande d'affectation était utilisée. A son tour, celui-ci est réinitialisé lors de la navigation d'un répertoire à un autre et lors de la suppression d'un fichier ou d'un répertoire.

### La commande [\<NER\>] visu

Comme indiqué ci-dessus, cette commande permet de visualiser le contenu d'un fichier texte. Et si le fichier n'est pas de type texte, sa taille est affichée.

Cependant, si vous souhaitez afficher le contenu d’autres types de formats, il existe des bibliothèques pour le faire. Dans le cas des images par exemple, on peut envisager pour les versions ultérieures du projet des bibliothèques comme : 

- [Java AWT](https://www.javatpoint.com/java-awt) qui à travers des classes comme Image, BufferedImage, Graphics, fournit des fonctionnalités de chargement et d'affichage d'images.
- [Java Swing](https://docs.oracle.com/javase/tutorial/uiswing/) qui est une bibliothèque graphique pour le langage de programmation Java et qui est basé sur AWT.
- Autres options ([JavaFX](https://openjfx.io/), [Apache Commons Imaging](https://commons.apache.org/proper/commons-imaging/), etc.).
 
### Pistes futures du projet d'étude

Comme déjà décrit ci-dessus, on constate que beaucoup de choses restent à faire pour que le système de gestion de fichiers en ligne de commande soit pleinement utile à un utilisateur dans les tâches principales effectuées dans un système de fichiers.

#### Commandes utiles à ajouter

Les commandes suivantes seraient utiles pour pouvoir les ajouter au système déjà créé, entre autres :

- Affichage des métadonnées ou des attributs des fichiers : affichez des informations détaillées sur les fichiers, telles que la taille, la date de modification, les autorisations, etc.
  
- Commande chmod : Pour pouvoir modifier les autorisations d'accès aux fichiers ou répertoires.

- Commande diff : Pour comparer deux fichiers ligne par ligne et affichez les différences.

- Commande zip et unzip : Pour autoriser la compression et la décompression des fichiers et répertoires au format zip.

#### Améliorations proposées du côté de l'utilisateur

Bien que les commandes fonctionnent correctement, il est attendu que l'utilisateur exige que le système soit plus dynamique et interactif, c'est pourquoi les améliorations suivantes sont prévues :

- Complétion automatique des noms de fichiers et de répertoires : Implémenter une fonctionnalité qui permet aux utilisateurs de compléter automatiquement les noms de fichiers ou de répertoires lors de la saisie de commandes.
  
- Recherche de fichiers/répertoires en temps réel : Ajouter une fonctionnalité pour rechercher des fichiers ou des répertoires affichant les correspondances en temps réel.

#### Evolutions futures possibles

Suivant la ligne d'étude du projet, on cherche à avoir pour les versions ultérieures les fonctionnalités suivantes :

- Intégration de systèmes de stockage cloud : Autoriser la gestion des fichiers stockés dans les services cloud.

- Fonctionnalités de sécurité avancées : Implémenter des outils de cryptage de fichiers ou une gestion plus détaillée des autorisations.
 
- Prise en charge des liens symboliques : Implémenter la possibilité de créer et d'utiliser des liens symboliques dans le système de fichiers.

## Manuel technique

### Rapport de couverture de code par les tests

La couverture du code a été réalisée avec JaCoCo, un outil permettant de mesurer la quantité de code qui a été testée par des tests automatisés (dans ce cas via JUnit5 pour les tests unitaires). Il permet d'identifier les domaines non testés, d'améliorer la qualité du code et de prendre des décisions sur où concentrer les efforts de test. C'est un moyen efficace d'évaluer l'avancement de vos tests et d'améliorer la fiabilité du logiciel.

Dans un premier temps, le fichier résultant de la utilisation de l'outil de Code Coverage des tests est obtenu par la [compilation du projet](#compiler-le-projet). Le fichier utile résultant est index.html situé dans target/site/jacoco

<img src="./src/assets/images/code_coverage.gif" width="80%">

### Bibliothèques utilisées


- Comme indiqué pour la couverture du code, [Jacoco](https://www.jacoco.org/jacoco/trunk/doc/) a été utilisé car il offre une large compatibilité
et divers formats de rapport (HTML, XML et CSV), intégration simple avec différents systèmes de compilation, dans notre cas Maven, mais il reste complet avec Gradle et Ant. De plus, son poids est léger et a donc peu d'impact sur les performances de le système.

- [JUnit5] (https://junit.org/junit5/) a été utilisé pour effectuer les tests unitaires grâce à son architecture modulaire qui facilite la gestion des dépendances, elle est compatible avec d'autres bibliothèques et frameworks de tests. De plus, il peut être adapté pour être utilisé sur d'autres plateformes.

- [JLine] (#https://github.com/jline/jline3) a été utilisé pour l'interaction avec la console, JLine est couramment utilisé dans les applications en ligne de commande en raison de sa facilité d'intégration entre l'implémentation de sa console de différents systèmes d'exploitation (dans ce cas Linux et Windows). 

- D'autre part, [Jansi](#https://fusesource.github.io/jansi/) a été utilisé car il offre une gestion des couleurs et des styles qui peut être avancée et est compatible multiplateforme.

### Classes

#### App.java

Il s'agit de la classe principale utilisée pour l'affichage de l'interface utilisateur et de la palette de commandes à utiliser avec les restrictions requises, le stockage en mémoire et la validation de ces commandes.

#### Annnotation.java

Cette classe a trois méthodes :
- plus(int en, String note) qui permet d'ajouter une note à la position NER correspondante du notes.txt de chaque répertoire associé.
- minus(int ner, String note) utilisé pour supprimer la valeur de note liée au NER correspondante du fichier notes.txt dans le répertoire.
- updateAnnotation qui, en fonction des données du répertoire courant, cherche à mettre à jour ou non le fichier notes.txt correspondant.

#### ContentToMemory.java

Grâce à ses méthodes readFileToMemory et readDirectoryToMemory, se charge de mapper un fichier ou un répertoire respectivement pour renvoyer son contenu au format byte[] dans le cas d'un fichier et List[\<String\>] dans le cas d'un répertoire.

#### DirectoryNavigation.java

Cette classe a les méthodes :
- listDirectory qui a en entrée un folderPath au format String qui sert à mapper le répertoire courant et son notes.txt correspondant pour les imprimer sous format tableau dont les attributs seraient : "NER", "Name", "Annotations".
- La méthode getFileFromNer, qui renvoie le fichier correspondant au ner renseigné dans le répertoire courant.
- La méthode getNerFromFile qui, à partir du nom d'un fichier, recherche son NER correspondant dans le répertoire courant.
- La méthode accessible pour vérifier si le nom saisi se trouve dans la plage des éléments d'un répertoire.
- Et enfin, getCurrentDir pour récupérer le chemin du répertoire courant.

#### FileDetails.java

Qui permet de récupérer les données d'un fichier saisi au format File et son Chemin au format String.

#### FileManipulation.java

- Il dispose de méthodes de visulizing pour que, en fonction de la nature d'un fichier ou d'un répertoire, son contenu soit affiché s'il est au format texte ou son poids sinon, et s'il s'agit d'un répertoire celui-ci est reconnu.
- La méthode précédente utilise à son tour, la méthode printSize, basée sur un fichier sélectionné au format d'entrée File, calcule son poids en octets, Ko, Mo ou Go selon ce qui convient le mieux. 
- On a également la méthode find qui recherche les fichiers correspondant à fileName dans le répertoire courant.

#### FileOperations.java

Cette classe a diverses méthodes, notamment :
- copy(ner) qui copie un fichier ou un répertoire dans un chemin et un fichier File.
- paste (selectedFile, status) où l'état de selectedFile est vérifié et s'il se trouve dans le répertoire d'entrée, le collage dépendant de ses fichiers stockés peut être effectué.
- Nous avons deux méthodes delete et deleteDirectory qui, comme leurs noms l'indiquent, delete supprime un fichier spécifique et deleteDirectory supprime un répertoire de manière récursive.

#### FolderManipulation.java

Cette classe a la méthode mkDirectory qui, via la classe java.io.File en tant que standard en Java pour travailler avec les fichiers et les répertoires du système de fichiers, le répertoire introduit dans l'argument FolderName est créé dans le chemin actuel et imprime enfin le notification que cela a été fait.

#### NavigationFunctions.java

Cette classe est chargée d'effectuer les tâches de navigation, à savoir :
- .. pour adresser le répertoire parent par rapport au répertoire courant.
- [\<NER\>] . pour se déplacer entre les répertoires
- Et pwd pour afficher le chemin actuel.

#### Parser.java

Pour chaque méthode que nous avons utilisée dans App.java, nous créons un analyseur pour chacune afin d'utiliser uniquement la ligne comme argument, afin de restructurer les lignes dans une structure de blocs afin qu'elles soient plus faciles à tester.

### Traitements des commandes saisies par l'utilisateur

Le traitement des commandes s'effectue selon la logique suivante :
- Il est prévu que, sur la base de la méthode contains(), la commande spécifique à utiliser pour le cas puisse être trouvée.
- Si la commande correspondante est détectée, une validation est recherchée si ladite commande s'attend à avoir 0, 1 ou 2 arguments selon les cas.
- En cas d'incohérence, le format de commande attendu est indiqué à l'utilisateur.
- S'il n'y a pas d'incohérence, ces données sont saisies en entrée des méthodes définies pour un traitement ultérieur dans son affichage de sortie.
- De nouvelles bibliothèques pourraient être utilisées pour améliorer encore l'interface utilisateur afin qu'elle soit beaucoup plus complète pour l'utilisateur.

### Améliorations envisagées

Des améliorations sont attendues dans :
- Optimisation des tâches cherchant à éviter la redondance dans les méthodes utilisées dans chaque cours.
- De nouvelles commandes pourraient être ajoutées telles que celles définies dans le manuel utilisateur.
- On pourrait chercher à supprimer les tests inutiles dans certains cas où le comportement étudié ne sera jamais obtenu et ajouter d'autres cas de tests nécessaires.

## Resources

* [Techniques et outils de développement logiciel](http://persee.prism.uvsq.fr/pub/cp/TOD/book.pdf)
* [Programmation orientée-objet](http://persee.prism.uvsq.fr/pub/cp/POO/)
* [Core Libraries](https://docs.oracle.com/en/java/javase/21/core/java-core-libraries1.html)

## Crédits

Ce système de gestion de fichiers s'inspire et regroupe les différentes fonctionnalités proposées par java.io, java.nio, java.util et org.jline.

## Licence

[UVSQ](https://www.uvsq.fr/)
