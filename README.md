# Marvin/ jeu

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and an `ApplicationAdapter` extension that draws libGDX logo.

## Présentation du projet

Dans ce jeu, Mario est un étudiant en informatique qui doit acquérir des compétences pour réussir son test final. Les ennemis de Mario sont Marvin, et il doit les éviter ou les vaincre pour continuer son parcours. Le but du jeu est de collecter toutes les compétences nécessaires en informatique pour réussir le test.

Inspiré de Mario Bross, découvrez l'aventure des étudiants de Pré-msc lors de leur piscine ;)

## Plateformes

- `core`: Module principal avec la logique de l'application partagée par toutes les plateformes.
- `lwjgl3`: Plateforme de bureau principale utilisant LWJGL3.

## Gradle

Ce projet utilise [Gradle](https://gradle.org/) pour gérer les dépendances.
Le wrapper Gradle est inclus, vous pouvez donc exécuter les tâches Gradle en utilisant les commandes `gradlew.bat` ou `./gradlew`.

### Tâches Gradle utiles et options

- `--continue`: avec cette option, les erreurs n'arrêtent pas l'exécution des tâches.
- `--daemon`: grâce à cette option, le daemon Gradle sera utilisé pour exécuter les tâches choisies.
- `--offline`: avec cette option, les archives de dépendances mises en cache seront utilisées.
- `--refresh-dependencies`: cette option force la validation de toutes les dépendances. Utile pour les versions snapshot.
- `build`: compile les sources et génère les archives de chaque projet.
- `cleanEclipse`: supprime les données du projet Eclipse.
- `cleanIdea`: supprime les données du projet IntelliJ.
- `clean`: supprime les dossiers `build`, qui contiennent les classes compilées et les archives générées.
- `eclipse`: génère les données du projet Eclipse.
- `idea`: génère les données du projet IntelliJ.
- `lwjgl3:jar`: génère le jar exécutable de l'application, qui se trouve dans `lwjgl3/build/libs`.
- `lwjgl3:run`: démarre l'application.

Notez que la plupart des tâches qui ne sont pas spécifiques à un seul projet peuvent être exécutées avec le préfixe `name:`, où `name` doit être remplacé par l'ID d'un projet spécifique. Par exemple, `core:clean` supprime le dossier `build` uniquement du projet `core`.

## Lancer le jeu

Pour lancer le jeu, suivez les étapes suivantes :

1. Clonez le projet depuis le dépôt Git :
    ```sh
    git clone <MarioGame>
    ```

2. Accédez au répertoire du projet :
    ```sh
    cd <NOM_DU_REPERTOIRE>
    ```

3. Utilisez la commande suivante pour lancer le jeu :
    ```sh
    ./gradlew lwjgl3:run
    ```

Les dépendances du projet sont gérées dans les fichiers build.gradle et gradle.properties.

buildscript {
  repositories {
    mavenCentral()
    maven { url 'https://s01.oss.sonatype.org' }
    gradlePluginPortal()
    mavenLocal()
    google()
    maven { url 'https://oss.sonatype.org/content/repositories/snapshots/' }
    maven { url 'https://s01.oss.sonatype.org/content/repositories/snapshots/' }
  }
  dependencies {
    classpath "io.github.fourlastor:construo:1.4.3"
    if(enableGraalNative == 'true') {
      classpath "org.graalvm.buildtools.native:org.graalvm.buildtools.native.gradle.plugin:0.9.28"
    }
  }
}

allprojects {
  apply plugin: 'eclipse'
  apply plugin: 'idea'

  idea {
    module {
      outputDir file('build/classes/java/main')
      testOutputDir file('build/classes/java/test')
    }
  }
}

configure(subprojects) {
  apply plugin: 'java-library'
  sourceCompatibility = 8

  tasks.register('generateAssetList') {
    // From https://lyze.dev/2021/04/29/libGDX-Internal-Assets-List/
    // The article can be helpful when using assets.txt in your project.
  }
}


Ce README fournit une vue d'ensemble de la structure du projet, des dépendances, et des instructions pour lancer le jeu. Assurez-vous de personnaliser les sections selon les besoins spécifiques de votre projet.


## Créateurs 
- Melia Reriouedj
- Meryem Jarrar