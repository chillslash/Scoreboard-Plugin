# Scoreboard Plugin

A Minecraft Bukkit/Spigot scoreboard plugin adapted from an online source for personal server use. This plugin provides kill tracking and parkour completion features for a custom Minecraft server.

## Features

### Kill Tracking & Leaderboard
- Tracks player kills in the PvP "Sumo" world
- Displays a live scoreboard showing the top 5 players with the most kills
- Shows each player their personal kill count and rank
- Automatically updates when players enter/exit the Sumo world
- Persistently stores kill data in YAML format
- Plays a sound effect when a player gets a kill
- Registers kills when players are eliminated through the "Fly2" portal

### Parkour Completion Tracker
- Detects when players complete the parkour course
- Records first-time completions with timestamp
- Broadcasts a server-wide message when someone completes it for the first time
- Prevents duplicate completions (each player can only complete once)
- Stores completion data persistently in YAML format

### Other Features
- Scoreboard automatically appears when entering the Sumo world
- Scoreboard automatically hides when leaving the Sumo world
- Auto-respawn in the Parkour world on death
- Fire resistance effect applied after parkour deaths
- Instant death when falling into the void in Parkour world

## Requirements

- **Java Development Kit (JDK)** for compilation
- **Bukkit/Spigot/Paper** Minecraft server
- **Multiverse-Portals** plugin
