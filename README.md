# Wraith’s Return

## Nom du Projet
Wraith’s Return

## Description
Wraith’s Return est un jeu 2D où le joueur incarne un fantôme cherchant à revenir à la vie. Pour réussir, il doit retrouver les souvenirs de ses vies antérieures, dispersés dans différents mondes correspondant à ses réincarnations passées. Ces souvenirs sont gardés par des Faucheurs (Grim Reapers) qui tentent d’empêcher son retour dans le monde des vivants.

Le joueur doit explorer, combattre, collecter des fragments de mémoire et progresser à travers plusieurs mondes thématiques inspirés de ses anciennes existences.

Fonctionnalités principales :
- Exploration de mondes représentant les anciennes vies du protagoniste.
- Combat contre différents types de Faucheurs.
- Collecte de fragments de mémoire pour débloquer la progression.
- Système de power-ups.
- Gestion d’états du jeu (menu, pause, game over…).
- Architecture basée sur plusieurs Design Patterns.

## Membres du Groupe
- Benhenda Imen
- Jlassi Hedil
- Touati Balsem

## Technologies Utilisées
- Langage : Java 17
- Framework GUI : Swing
- Logging : Log4j2
- Build : Maven

## Design Patterns Implémentés

### 1. State Pattern
Gère les différents états du jeu (Menu, Gameplay, Pause, Game Over) ainsi que certains états internes du joueur (normal, blessé, sous power-up…).

### 2. Decorator Pattern
Utilisé pour le système de power-ups (vitesse accrue, attaque renforcée, invincibilité, double saut…).  
Permet d’ajouter des comportements sans modifier la classe Player.

### 3. Composite Pattern
Employé pour représenter la structure hiérarchique des mondes du jeu :  
Chaque monde contient plusieurs zones, qui contiennent des ennemis, objets, obstacles, etc.  
Cela permet une organisation flexible et extensible.

### 4. Factory Pattern
Utilisé pour la création des différents types de Faucheurs :
- Faucheur standard
- Faucheur volant
- Gardien d’élite (mini-boss)
- Gardien des souvenirs (boss de monde)

## Installation

### Prérequis
- JDK 17 ou supérieur
- Maven 3.6+

### Étapes
1. Cloner le dépôt :
   git clone [URL]
2. Compiler :
   mvn clean install
   ou
   ./gradlew build
3. Exécuter :
   java -jar target/wraiths-return.jar

## Utilisation

| Action | Touche |
|--------|--------|
| Déplacement gauche/droite | ← / → |
| Saut | Espace |
| Attaque | Ctrl |
| Pause | Échap |

## Structure du Projet

src/
├── main/java/
│    ├── game/
│    │    ├── core/           # Boucle de jeu, gestion du temps, GameState
│    │    ├── entities/       # Player, GrimReapers, souvenirs
│    │    ├── levels/         # Composite pattern : mondes + zones
│    │    ├── patterns/       # Implémentations des design patterns
│    │    ├── ui/             # JavaFX UI (menus, HUD)
│    │    └── utils/          # Logging, utilitaires
├── main/resources/
│    ├── sprites/
│    ├── sounds/
│    └── config/
└── test/java/

