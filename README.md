# Secure Notes Demo App

**Secure Notes Demo App** is a simple **Jetpack Compose** based Android app.  
It demonstrates modern Android development practices with a focus on **secure local storage** and **maintainable design**.



## How to Run

1. Clone the repository:  
   ```bash
   git clone https://github.com/<username>/secure-notes-demo.git
2. Open in Android Studio.

3. Sync Gradle and run the app on an emulator or device.



## Key Features

- **Jetpack Compose**: Modern, declarative UI toolkit for Android  
- **SQLCipher**: Encrypted local database for secure note storage  
- **Separation of concerns**: UI, ViewModel, and data handling kept in distinct layers  

### Why Jetpack Compose (instead of XML)?
- **Declarative UI**: Instead of describing *how* to build the UI step by step in XML, we describe *what the UI should look like* given the current state.
- **Less boilerplate**: No need for separate XML files and `findViewById()`.
- **Powerful state management**: UI automatically updates when state changes.
- **Modern Android standard**: Compose is Google’s recommended way forward.



## Architecture Overview

The app follows a simple layered structure:

- **UI layer**: Jetpack Compose screens (`NotesScreen`, `UpgradeScreen`, etc.)  
- **State layer**: ViewModels manage app state and business logic  
- **Data layer**: Database access with SQLCipher  



## Roadmap

- [x] 1.1 Jetpack Compose migration 
- [x] 1.2 Add navigation and screens
- [x] 1.3 Create Room database schema
- [x] 1.4 Integrate SQLCipher for encryption
- [x] 1.5 Implement feature-based app structure
- [x] 1.6 Add screens for testing and security
- [ ] 1.7 Add unit tests
- [ ] 1.8 Add instrumented tests  
- [ ] 1.9 Setup GitHub Actions for automated build and test   
- [ ] 1.10 Add KDocs  
- [ ] 1.11 Integrate Dokka for API docs
- [ ] 1.12 Add architectural diagrams
