
# 🚨 Disaster Alert System

![Java](https://img.shields.io/badge/Backend-Java%20(Spring%20Boot)-red?style=for-the-badge&logo=springboot)
![Frontend](https://img.shields.io/badge/Frontend-HTML%20CSS%20JS-blue?style=for-the-badge&logo=javascript)
![Database](https://img.shields.io/badge/Database-SQLite-green?style=for-the-badge&logo=sqlite)
![Build](https://img.shields.io/badge/Build-Maven-orange?style=for-the-badge&logo=apachemaven)
![Status](https://img.shields.io/badge/Project-Active-success?style=for-the-badge)

---

## 🌟 Overview

**Disaster Alert System** is a full-stack emergency coordination platform that enables:

- 🚨 Reporting disasters in real-time  
- 🤝 Registering volunteers  
- 📦 Managing relief resources  
- 🔍 Searching incidents by place or disaster type  

Built using **Spring Boot (Java backend)** and a **modern premium UI frontend**, this project simulates a real-world disaster response system.

---

## ⚙️ Tech Stack

### 🔧 Backend
- Java (Spring Boot)
- REST APIs
- JDBC + SQLite
- Maven

### 🎨 Frontend
- HTML5
- CSS3 (Glassmorphism UI)
- JavaScript (Vanilla JS)

---

---

## 🚀 Features

### 📊 Dashboard
- Live system statistics
- Total reports, volunteers, resources

### 🚨 Disaster Reporting
- Report floods, fires, droughts, etc.
- Includes severity, location, description

### 🤝 Volunteer Management
- Register volunteers with skills and contact info

### 📦 Resource Tracking
- Add available resources (food, medical, etc.)
- Track quantity and contacts

### 🔍 Smart Search
- Search by **place → shows all data**
- Search by **disaster type → shows reports**
- Dynamic UI rendering

---

## 🔌 API Endpoints

| Method | Endpoint | Description |
|------|--------|------------|
| POST | `/api/report` | Add disaster report |
| POST | `/api/volunteer` | Register volunteer |
| POST | `/api/resource` | Add resource |
| GET  | `/api/search` | Search data |
| GET  | `/api/stats` | Get system stats |

---

## 🛠️ Setup & Run

### 1️⃣ Clone Repository
```bash
git clone https://github.com/rohanramgopal/Disaster-Alert-System.git
cd Disaster-Alert-System
2️⃣ Run Backend (Spring Boot)
cd backendmvn clean installmvn spring-boot:run
Server runs on:
http://localhost:8080

3️⃣ Run Frontend
cd ../frontendpython3 -m http.server 3000
Open:
http://localhost:3000

🧠 How It Works

User submits data (report / volunteer / resource)
Frontend sends request → Spring Boot API
Data stored in SQLite DB
Search queries fetch filtered results
UI dynamically renders cards



✨ UI Highlights
🌌 Gradient + Glassmorphism design
📱 Fully responsive layout
⚡ Fast and lightweight
🎯 Clean user flow



📌 Example Use Cases
Disaster management simulations
Emergency response systems
Government/NGO prototypes
Full-stack portfolio project



🔮 Future Enhancements

🗺️ Map integration (Leaflet / Google Maps)
🔐 Authentication (JWT)
☁️ Cloud deployment (AWS / Render)
📡 Real-time alerts (WebSockets)
📊 Analytics dashboard



👨‍💻 Author
Rohan Ramgopal




