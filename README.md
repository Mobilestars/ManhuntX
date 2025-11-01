# 🏹 ManhuntX

**ManhuntX** is an advanced Minecraft Manhunt plugin for **Bukkit**, **Spigot**, **Paper**, **Purpur**, and **Folia** (1.18+). It provides a dynamic Manhunt experience with **Hunter & Runner roles**, **glowing effects**, **timers**, and **in-game GUIs**.

---

## ✨ Features

- 🧑‍🤝‍🧑 **Hunter & Runner Roles**
  - Assign players as **Hunter** or **Runner**
  - Optional random role assignment
- 🧭 **Tracking Compass**
  - Hunters receive a compass that tracks Runners automatically
- 🎯 **Game Modes**
  - `KILL_ENDER_DRAGON`: Runner wins by killing the Ender Dragon
  - `SURVIVE_TIME`: Runner wins by surviving a set duration
- ⏱️ **Preparation Phase**
  - Hunters are blocked for a preparation period
  - Countdown timer before the main game starts
- ✨ **Glowing Effects**
  - Runners and/or Hunters glow periodically
  - Duration and pause configurable
- 🛠️ **Configurable GUIs**
  - In-game menu to manage roles, times, and glowing settings
- ✅ **Compatibility**
  - Supports Bukkit, Spigot, Paper, Purpur, and Folia
- 🔗 **PlaceholderAPI Support**
  - Optional integration for dynamic messages

---

## ⚡ Installation

1. Place the **ManhuntX.jar** into your `plugins/` folder.  
2. Start the server to generate default configuration files.  
3. Adjust the `config.yml` (roles, glowing, timers, etc.).  
4. Restart the server or use `/reload` to apply changes.

---

## 📝 Commands

| Command | Description | Permission |
|---------|-------------|------------|
| 🛠️ `/manhuntx set <hunter|runner> <player>` | Set a player as Hunter or Runner | `manhuntx.op` |
| ▶️ `/manhuntx start` | Start the Manhunt game | `manhuntx.op` |
| ⚙️ `/manhuntx config` | Open the configuration GUI | `manhuntx.op` |
| 🏃 `/manhuntx runner` | Set yourself as Runner | `manhuntx.runner` |
| 🏹 `/manhuntx hunter` | Set yourself as Hunter | `manhuntx.hunter` |
| 🧭 `/compass [Runner]` | Give Hunter a tracking compass | `manhuntx.hunter` |

---

## 🔐 Permissions

- `manhuntx.op` – Admin commands: set, start, config  
- `manhuntx.runner` – Runner command access  
- `manhuntx.hunter` – Hunter command & compass access  

---

## ⚙️ Configuration

### General

- 🏷️ `prefix`: Message prefix  
- ⏱️ `preparation-time`: Preparation phase in seconds  
- 🔀 `random-roles`: Enable random role assignment  
- 👥 `random-runners`: Number of randomly selected Runners  

### Challenge

- 🎯 `challenge-type`: `KILL_ENDER_DRAGON` or `SURVIVE_TIME`  
- ⏳ `survival-time`: Time in seconds for SURVIVE_TIME mode  

### Glowing

- ✨ `glow.enabled`: Enable glowing  
- 🟢 `glow.runner`: Runner glowing  
- 🔴 `glow.hunter`: Hunter glowing  
- ⏱️ `glow.time`: Duration of glowing  
- ⏸️ `glow.pause`: Pause between glowing intervals  

---

## 🖥️ GUI Overview

### ManhuntX Config
- 🎯 Toggle challenge type  
- ⏱️ Set preparation time  
- 🔀 Enable/disable random roles  
- 👥 Set number of runners  
- ✨ Open glowing settings  
- 💾 Save configuration  

### Select Preparation Time
- ⏱️ Choose the preparation phase duration  

### Glowing Settings
- 🟢/🔴 Toggle Runner/Hunter glowing  
- ⏱️ Set glowing duration  
- ⏸️ Set pause duration  

---

## ✅ Compatibility

- 🟢 **Bukkit**  
- 🟢 **Spigot**  
- 🟢 **Paper**  
- 🟢 **Purpur**  
- 🟢 **Folia**  

---

## 💬 Support

For issues or feature requests:  

- Open a GitHub Issue  
- Discord: `[Your Discord Link]`  

---

## 📜 License

ManhuntX is licensed under the **Apache License 2.0**.
