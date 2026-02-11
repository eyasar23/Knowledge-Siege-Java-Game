# Knowledge Siege: Java 2D Survival Game 🎓

**Knowledge Siege** is a 2D simulation and survival game developed using **Java** and **Swing**. The game metaphorically simulates the academic struggle of a university student facing various academic challenges represented by Student Leaders (SL), Teaching Assistants (TA), and Professors.

> *Developed as a comprehensive study on Object-Oriented Design patterns, Swing GUI management, and File I/O operations.*

## 🎮 Game Mechanics
The player controls a student character navigating through academic levels. [cite_start]The goal is to survive by dodging "Question Boxes" (damage) while collecting "Information Boxes" (score) thrown by academic staff[cite: 241, 242].
- **Survival:** Health decreases when hit by questions.
- [cite_start]**Progression:** The game consists of 3 distinct levels with increasing difficulty[cite: 243].
- **Victory:** Reaching the target score (150 pts) triggers the victory state.

## 🏗️ Technical Architecture & Design
[cite_start]This project was built with a strong emphasis on **OOP Principles** to ensure modularity and scalability[cite: 343].

### 1. Class Hierarchy & Polymorphism
The core of the enemy logic relies on the abstract `KnowledgeKeeper` class.
- **Base Class:** `KnowledgeKeeper` (Abstract) - Defines movement and shooting behaviors.
- **Subclasses:** `SL`, `TA`, `Professor`.
- [cite_start]**Polymorphism:** Each subclass overrides the `getShotSpeed()` and `shoot()` methods to behave differently[cite: 360, 364]. For instance, Professors shoot faster and present harder questions compared to SLs.

### 2. File I/O & Data Management
[cite_start]The game uses a custom `FileLoader` system to manage resources dynamically[cite: 326]:
- [cite_start]**Dynamic Content:** Questions and Information texts are parsed from external `.txt` files (`questions.txt`, `info.txt`)[cite: 396].
- [cite_start]**Persistence:** High scores and user credentials are saved/loaded locally using `ScoreSaverLoader`[cite: 321].

### 3. Java Swing GUI
- [cite_start]**Custom Rendering:** The game loop uses `paintComponent(Graphics g)` in `GamePanel` for real-time rendering of the player, projectiles, and UI elements[cite: 379].
- **Event Handling:** `KeyListeners` are used for smooth character movement.
- [cite_start]**Swing Timers:** Used for the main game loop (`16ms` tick rate) to manage physics and collision detection[cite: 406].

## 📂 Project Structure
- `src/core`: Main game logic (`GameEngine`, `Collision`, `Physics`).
- `src/gui`: UI components (`GamePanel`, `VictoryPanel`, `MainMenu`).
- [cite_start]`src/user`: User authentication and profile management system[cite: 403].
- `resources`: Assets (Images, Text data).

## 🚀 How to Run
1. Clone the repository.
2. Open the project in any Java IDE (IntelliJ IDEA, Eclipse, VS Code).
3. Run the `Main` class.
4. Login with default credentials or register a new user.

---
*Built by Emirhan Efe Yasar - Koç University Computer Engineering Student.*
