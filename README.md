# Health & Wellness Tracker
**HealthTracker** is a modern Android application designed to help users monitor their physical and mental well-being. Built with the latest Android development standards, it provides a seamless experience for tracking nutrition, sleep, and daily activities.
<img width="384" height="840" alt="food_page" src="https://github.com/user-attachments/assets/bfb96752-b1ce-4515-ba8d-f3f74990ccd9" />
<img width="383" height="843" alt="logs_page" src="https://github.com/user-attachments/assets/55b67d55-2178-4992-8a17-38dcedda2ba1" />
<img width="387" height="844" alt="sleep_page" src="https://github.com/user-attachments/assets/76f589d1-9427-4647-acf7-b0f8d4f05785" />
<img width="387" height="845" alt="settings_page" src="https://github.com/user-attachments/assets/a43b2dfe-4dc0-4a59-abbf-2251724bfccb" />
<img width="386" height="846" alt="recent_page" src="https://github.com/user-attachments/assets/c89a6e74-3ed4-4762-a564-18ff35effce1" />
## 🚀 Features
- **Smart Nutrition Tracking**: Full-featured food and drink logging system with macronutrient (carbs, proteins, fats) and calorie analysis
- **Goal Management**: Personal calculator for daily goals based on user parameters (age, gender, weight, etc.)
- **Interactive UI**: Fluid and modern interface for Sleep & Recovery monitoring (UI-ready)
- **History & Search**: Fast search for products, weight adjustment for entries, and "recent items" list
- **Scalable Architecture**: Modular structure ready for mental health and activity tracking modules

## 🛠 Tech Stack
- **UI**: Jetpack Compose
- **Language**: Kotlin
- **Asynchronous Programming**: Coroutines & Flow
- **Dependency Injection**: Hilt
- **Database**: Room Persistence Library
- **Navigation**: Compose Navigation
- **Architecture**: MVVM (Model-View-ViewModel) + Clean Architecture principles

## 🏗 Architecture
The project follows the **Clean Architecture** approach to ensure maintainability and testability:
- **Data Layer**: Room DAOs and Repositories for local storage
- **Domain Layer**: Use Cases for business logic (e.g., calculating daily calorie needs)
- **Presentation Layer**: State-driven UI with ViewModels using StateFlow for reactive updates

## 🗺 Roadmap
- **Health Connect integration**: Syncing steps, heart rate, and data from other health apps
- **Smartwatch Support**: Wear OS companion app for quick logging
- **Advanced Analytics**: Beautiful data visualization and charts for long-term trends
- **Sleep Logic**: Implementing backend/sensor logic for the Sleep tracking module
- **Mental Health**: Journaling and mood tracking features

## ⚙️ Setup
To run this project locally: 
1. Clone the repository: git clone https://github.com/iso-arkisto/HealthTracker
2. Open the project in **Android Studio**
3. Build and run on an emulator or physical device with Android 8.0 (API 26) or higher
