# Secure Notes Demo App

This is a simple **Jetpack Compose** based Android app, without XML layouts.  
It demonstrates:

- Using **Compose** for UI instead of XML
- **Navigation** with a bottom bar
- An **Agile info carousel** with swipeable pages and indicators

---

## Why Jetpack Compose (instead of XML)?
- **Declarative UI**: Instead of describing *how* to build the UI step by step in XML, we describe *what the UI should look like* given the current state.
- **Less boilerplate**: No need for separate XML files and `findViewById()`.
- **Powerful state management**: UI automatically updates when state changes.
- **Modern Android standard**: Compose is Google’s recommended way forward.

---

## File Structure Overview

As of the 24th of August, 2025

app/src/main/java/com/example/securenotesdemo/
- `MainActivity.kt`: Main entry point.
- `SecureNotesDemoApp.kt`: Main entry point for the app.
- data
- model
- navigation
  - `MainScreen.kt`: Navigation graph.
  - `Screen.kt`: Navigation destinations.
- theme
- ui
  - components
  - screens
    - `AgileInfoScreen.kt`: Agile info screen.
    - `NotesScreen.kt`: Notes screen.
  - `AgileInfoCarousel.kt`: Carousel with swipeable pages.
- viewmodel

---

## Files Deep-Dives

## MainActivity.kt
```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecureNotesDemoApp()
        }
    }
}
```

- `setContent` { ... }: Entry point for Compose UI.
- Instead of inflating an XML layout, it launches the SecureNotesDemoApp composable.

## SecureNotesDemoApp.kt
```kotlin
@Composable
fun SecureNotesDemoApp() {
    MaterialTheme {
        MainScreen()
    }
}
```

- `@Composable`: Marks a function as a composable UI building block.
- `MaterialTheme`: Applies Material Design theming (colors, typography, shapes) to child composables.
- `MainScreen()`: The root of our navigation system.

## MainScreen.kt

This file sets up navigation and the bottom navigation bar.
- `rememberNavController()`: Creates and remembers a navigation controller that manages app navigation.
- `Scaffold()`: Provides a Material Design layout structure (e.g., bottom bar, top bar, floating action button, etc.).
- `NavigationBar`: Our bottom navigation bar.
- `NavHost()`: Defines destinations and connects them to composable screens.

## Screen.kt

```kotlin
sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Carousel : Screen("carousel", "Agile", Icons.Default.Info)
    object Notes : Screen("notes", "Notes", Icons.Default.List)
}
```

- `sealed class`: A restricted class hierarchy. All subclasses are known at compile time.
- Ensures type safety when using when or navigation routes.
- Each object (Carousel, Notes) represents a screen with a route, title, and icon.

## AgileInfoCarousel.kt

This file shows a pager/carousel of Agile information.
- `listOf()`: Creates a list of pages (each page is AnnotatedString text with formatting).
- `rememberPagerState()`: Keeps track of which page the user is currently on.
- `HorizontalPager()`: Displays horizontally swipable pages.
- `Row` + `Box()`: Draws dot indicators under the pager.
- (Optional) `rememberCoroutineScope()` would allow us to programmatically move pages.

