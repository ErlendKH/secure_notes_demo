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

- [x] Jetpack Compose migration 
- [x] Add navigation and screens
- [x] Create Room database schema
- [x] Integrate SQLCipher for encryption
- [x] Implement feature-based app structure
- [x] Add screens for testing and security
- [x] Add unit tests
- [ ] Add instrumented tests  
- [ ] Setup GitHub Actions for automated build and test   
- [ ] Add KDocs  
- [ ] Integrate Dokka for API docs
- [ ] Add architectural diagrams
