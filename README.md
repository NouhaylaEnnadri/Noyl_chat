# NOYL ChatBot

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JSON](https://img.shields.io/badge/JSON-000000?style=for-the-badge&logo=json&logoColor=white)
![FlatLaf](https://img.shields.io/badge/FlatLaf-000000?style=for-the-badge&logo=FlatLaf&logoColor=white)

NOYL ChatBot is a Java Swing-based application designed to provide an engaging and interactive chat experience. Utilizing a combination of static response mechanisms and data-driven approaches, this chatbot leverages JSON for managing intents and integrates with a MySQL database for secure user authentication.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Usage](#usage)
- [Screenshots](#screenshots)
  
## Features

- **User Authentication**: Secure login and registration with hashed passwords using MySQL.
- **Static Response System**: Uses JSON files to store predefined responses for efficient querying and interaction.
- **Data-Driven Intent Management**: Manages and updates chatbot intents dynamically via JSON.
- **Intuitive GUI**: Modern and user-friendly interface built with Java Swing.
- **Adaptive Learning**: Continuously improves responses based on user interactions.
## Technologies Used

- **Java Swing**: Core framework for the graphical user interface.
- **MySQL**: Manages user data with a secure and scalable authentication system.
- **JSON**: Efficient data format for storing and manipulating chatbot intents.
- **FlatLaf**: Provides a modern, customizable look and feel with a dark theme.
- **BCrypt**: Secure password hashing to enhance security.

## Project Structure

The project is structured using the Model-View-Controller (MVC) architecture, ensuring modularity and scalability. Key components include:

```
/Noyl_chatbot
│
├── /Controller
│   └── (Controller classes and logic)
│
├── /DAO
│   └── (Data Access Object classes)
│
├── /Model
│   └── (Model classes and data representations)
│
├── /View
│   └── (User interface components)
│
├── /Util
│   └── (Utility classes and functions)
│
└── /JSON
    └── (Chatbot intents and responses)
```


## Usage

- **Login/Sign-up**: Securely log in or sign up using the intuitive interface.
- **Chat**: Engage with the chatbot, which adapts to your queries and learns over time.

## Screenshots

### Login Screen
![image](https://github.com/user-attachments/assets/76cb4a2d-ebe3-4afa-9a91-7a290cff2723)


### Sign-up Screen
![image](https://github.com/user-attachments/assets/c1836107-dd01-46c1-82ae-a1d6d33f5100)


### Chat Interface
![image](https://github.com/user-attachments/assets/abf316dc-941f-4c3a-a28e-254b8f8f08a8)
## if the chat incounterd something he does not know whow to respond to he will learn from you 
![image](https://github.com/user-attachments/assets/e51fad37-9242-48ed-b303-ad33d7c9854d)


