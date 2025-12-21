# Wraith’s Return

## Nom du Projet
**Wraith’s Return**

## Description
**Wraith’s Return** est un jeu 2D développé en Java dans lequel le joueur incarne un fantôme évoluant à travers différents mondes.  
Ces mondes ne représentent pas directement les anciennes vies du fantôme, mais servent de **niveaux distincts** proposant chacun leurs propres environnements, obstacles et défis.

Le joueur explore ces mondes, affronte des ennemis, interagit avec des tuiles spéciales (pièges, soins, téléportation) et progresse grâce à un gameplay modulaire reposant sur plusieurs **design patterns**.

L’objectif principal est la survie et la progression à travers les niveaux tout en gérant la santé du personnage et les interactions avec l’environnement.

### Fonctionnalités principales
- Exploration de plusieurs mondes (niveaux).
- Système de tuiles interactives (pièges, soins, téléportation).
- Combat contre des ennemis.
- Système de power-ups.
- Gestion des états du jeu (menu, pause, dialogue).
- Architecture orientée design patterns.

---

## Membres du Groupe
- **Benhenda Imen**
- **Jlassi Hedil**
- **Touati Balsem**
- **Abdelli Ameni**
---

## Technologies Utilisées
- **Langage** : Java 17
- **Interface graphique** : Swing
- **Logging** : `java.util.logging`
- **Type de projet** : Projet Java standard (sans Maven ni Gradle)

---

## Design Patterns Implémentés

### 1. State Pattern
Utilisé pour gérer les différents états du jeu tels que :
- Menu
- Jeu
- Pause
- Dialogue
- Jeu Terminé (GameOver)
- Stats & Inventaire (CharacterState)

Ce pattern permet de séparer clairement la logique associée à chaque état et d’éviter l’utilisation excessive de conditions complexes.

---

### 2. Decorator Pattern
Employé pour le système de **power-ups**.

Il permet d’ajouter dynamiquement des améliorations au joueur (bonus temporaires, capacités supplémentaires) sans modifier la classe de base du joueur.

---

### 3. Factory Pattern
Utilisé pour la création des ennemis du jeu.

Types d’ennemis implémentés :
- **Grim Reaper**
- **Skeleton**

La factory centralise la logique de création et facilite l’ajout de nouveaux types d’ennemis.

---

### 4. Strategy Pattern
Utilisé pour la gestion des **événements liés aux tuiles spéciales** du jeu.

Chaque événement est implémenté comme une stratégie indépendante :
- `DamagePitEvent` : inflige des dégâts au joueur
- `HealingPoolEvent` : restaure la vie du joueur
- `TeleportingEvent` : téléporte le joueur vers une autre zone

Ce pattern permet d’ajouter de nouveaux événements sans modifier la logique existante.

### 5. Composite Pattern

Le **Composite Pattern** est utilisé pour représenter la structure hiérarchique des niveaux du jeu.

Chaque monde est composé de plusieurs zones, et chaque zone peut contenir différents éléments du jeu tels que des ennemis, des obstacles et des événements interactifs.

Ce pattern permet de traiter de manière uniforme les mondes et leurs composants, tout en facilitant l’ajout, la modification ou la suppression de niveaux et de zones sans impacter le reste du code.

---

## Installation

### Prérequis
- JDK 17 ou supérieur

### Étapes
1. Cloner le dépôt :
   ```bash
   git clone [URL]
## Utilisation

| Action                                              | Touche |
|-----------------------------------------------------|--------|
| Déplacement gauche / droite                         | ← / → |
| Déplacement haut / bas                              | ↑ / ↓ |
| Attaque                                             | Espace |
| Ouvrir / fermer la pause                            | P |
| Quitter un dialogue                                 | P |
| Ouvrir les statistiques du personnage et l’inventaire | C |
| Confirmer / Interagir                               | Entrée |
| Utiliser un Healing Pool                            | Entrée |

### Navigation dans l’inventaire
| Action | Touche |
|--------|--------|
| Naviguer dans l’inventaire | Q / Z / S / D |
| Équiper un objet | Entrée |
