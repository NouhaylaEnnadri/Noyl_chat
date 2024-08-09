Here's a GitHub README for your NOYL ChatBot project:

---

# NOYL ChatBot

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JSON](https://img.shields.io/badge/JSON-000000?style=for-the-badge&logo=json&logoColor=white)
![FlatLaf](https://img.shields.io/badge/FlatLaf-000000?style=for-the-badge&logo=FlatLaf&logoColor=white)

NOYL ChatBot is a sophisticated and user-centric Java Swing-based application designed to offer an immersive and interactive chat experience. It integrates with a MySQL database to ensure secure user authentication and utilizes JSON for managing chatbot intents, allowing for a seamless and adaptive conversational experience.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Usage](#usage)
- [Screenshots](#screenshots)
- [Conclusion](#conclusion)
- [License](#license)

## Features

- **User Authentication**: Secure login mechanism using MySQL to safeguard user credentials.
- **Intuitive GUI**: Visually appealing interface built with Java Swing.
- **Dynamic Intent Recognition**: JSON-based intent storage and management for adaptable conversations.
- **Learning Mechanism**: Continuously evolves and enhances responses based on user interactions.

## Technologies Used

- **Java Swing**: Core framework for the graphical user interface.
- **MySQL**: Manages user data with a secure and scalable authentication system.
- **JSON**: Efficient data format for storing and manipulating chatbot intents.
- **FlatLaf**: Provides a modern, customizable look and feel with a dark theme.
- **BCrypt**: Secure password hashing to enhance security.

## Project Structure

The project is structured using the Model-View-Controller (MVC) architecture, ensuring modularity and scalability. Key components include:

- **Controller**: Handles the application's logic.
- **DAO**: Data Access Object for database interactions.
- **Model**: Represents the application's data.
- **View**: The graphical interface for user interaction.
- **Util**: Utility classes.
- **JSON**: Manages chatbot intents and responses.

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/NouhaylaEnnadri/Noyl_chat.git
   ```

2. Import the project into your preferred IDE.

3. Set up the MySQL database using the provided schema.

4. Update the database connection settings in `DatabaseConnection.java`.

5. Build and run the project.

## Usage

- **Login/Sign-up**: Securely log in or sign up using the intuitive interface.
- **Chat**: Engage with the chatbot, which adapts to your queries and learns over time.

## Screenshots

### Login Screen
![Login Screen](path_to_screenshot)

### Sign-up Screen
![Sign-up Screen](path_to_screenshot)

### Chat Interface
![Chat Interface](path_to_screenshot)

## Conclusion

NOYL ChatBot combines advanced technologies to provide a secure, user-friendly, and adaptive chat experience. With its learning capabilities, it continuously evolves, setting a new standard in digital communication.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

Feel free to replace `path_to_screenshot` with the actual paths to your screenshots. Let me know if you need any further customization!
