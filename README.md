# 🧠 WikiPlatform – Universal Knowledge System for Web & Minecraft

Welcome to **WikiPlatform** – an open-source knowledge base system built with **Spring Boot**, **MongoDB**, and a **React frontend**, including seamless integration with **Minecraft**!

Designed for developers, gamers, and communities who need structured documentation **in-game** and **online**.

---

## 🔥 Features

- 🖥️ Clean, responsive web-based wiki (like WorldGuard's docs!)
- 🔧 Powerful REST API (Spring Boot + MongoDB)
- 📘 Markdown-based or text/wiki content storage
- ⛏️ Minecraft plugin to view wiki entries in-game via commands
- 💾 MongoDB-backed persistent storage
- 🚀 Open source and easy to deploy

---

## 🗂️ Tech Stack

| Layer      |                 Technology |
|:-----------|---------------------------:|
| Backend    | Java, Spring Boot, MongoDB |
| Frontend   |                      React |
| Minecraft  |      Spigot / Paper Plugin |
| API        |                REST (JSON) |

---

## 📁 Project Structure

```
wiki-platform/  
├── backend/            # Spring Boot API + MongoDB (Gradle project)  
├── frontend/           # React Web Interface  
├── minecraft-plugin/   # Spigot plugin (Gradle project)  
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Node.js + npm
- MongoDB
- Minecraft Server (Spigot or Paper)

---

### Backend (Spring Boot – Gradle)

```bash
cd backend  
./gradlew bootRun
```

### Frontend (React)

```bash
cd frontend  
npm install  
npm start
```

### Minecraft Plugin

1. Build the plugin:
   ```bash
      cd minecraft-plugin  
      ./gradlew build
   ```
2. Copy the .jar from `build/libs/` into your server's `plugins/` folder
3. Start your Minecraft server
4. Use `/wiki <topic>` to view entries in-game

---

## 📬 API Overview

| Method |             Endpoint             |       Description        |
|:------:|:--------------------------------:|:------------------------:|
|  GET   |              /wiki               |   Get all wiki entries   |
|  GET   |            /wiki/{id}            |   Get a specific entry   |
|  POST  |              /wiki               |    Create a new entry    |
|  PUT   |            /wiki/{id}            | Update an existing entry |
| DELETE |            /wiki/{id}            | Delete a specific entry  |
|  GET   | /wiki/search?query={searchQuery} | Search entries by title  |
|  GET   |           /wiki/random           | Get a random wiki entry  |

---

## 🛡️ License

This project is licensed under the **MIT License** – use it, modify it, share it!

---

## 🫡 Author

Developed by **Max**, IT specialist and Minecraft plugin enthusiast.  
Contributions welcome – feel free to fork and improve!

---

## 🌐 Live Preview (Coming Soon)

A public demo will be hosted shortly. Stay tuned!
