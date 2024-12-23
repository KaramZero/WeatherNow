# Weather Now & Later - README

## Features

### First Screen: City Input
- **City Input Field**:
  - Users can input the name of a city to fetch weather data.
  - The city name is stored locally to display the weather data the next time the app is reopened.

### Second Screen: Current Weather
- **Current Weather Information**:
  - Displays the current temperature for the selected city.
  - Shows the weather condition (e.g., cloudy, sunny, rainy) with an appropriate weather icon.

### Third Screen: 7-Day Forecast
- **7-Day Forecast List**:
  - Displays a list of weather forecasts for the next 7 days.
  - Each item includes the temperature, condition, and a corresponding weather icon.

---

## Technical Stack

The project is built using the following technologies and principles:
- **MVVM Architecture** for the City Input and Current Weather Screens
- **MVI Architecture** for the 7-Day Forecast List
- **Clean Architecture** with the following layers:
  - **Presentation**: Manages UI and ViewModel interactions
  - **Use Cases**: Contains business logic and interacts with repositories
  - **Repository**: Mediates between data sources and use cases
  - **Data Sources**: Local (stores the last searched city) and Remote (fetches weather data)
- **Dagger-Hilt** for Dependency Injection
- **Compose** for building the UI
- **Unit Testing** with a focus on 80% coverage, using a mocking framework
- **SOLID principles** for maintainable and readable code
- **CI/CD** with GitHub Actions or similar for linting, testing, and APK generation

---

## Error Handling

The application handles errors by:
- Differentiating between API errors and local storage issues.
- Displaying user-friendly error messages when something goes wrong (e.g., invalid city, no internet).

---

## Prerequisites

Before building and running the app, ensure you have the following installed:
1. **Android Studio**: [Download](https://developer.android.com/studio)
2. **Java Development Kit (JDK)**: Install the latest version from [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html) or use OpenJDK.
3. **Android SDK**: Install necessary components using the Android Studio SDK Manager.
4. **Android Device or Emulator**: Use a physical Android device or set up an emulator in Android Studio.

---

## Setup Instructions

### 1. Open Project in Android Studio:
- Open Android Studio.
- Select "Open an existing Android Studio project."
- Navigate to the cloned repository and open the `build.gradle` file of the app module.

### 2. Sync Gradle Dependencies:
- Gradle will sync automatically. If not, manually sync by clicking **File > Sync Project with Gradle Files**.

### 3. Configure Emulator (Optional):
- Set up an emulator via **Tools > AVD Manager** or connect a physical device.

---

## Building and Running the App

1. Click the green **Run** button in the Android Studio toolbar.
2. Select a target device (emulator or connected device).
3. Wait for the build to complete and the app to launch automatically on the device.

---

## Testing the App

### Unit Tests:
- Unit tests should cover core functionality, including fetching weather data and displaying it.

### Manual Testing:
- **City Input**: Verify that users can input a city name and retrieve its weather.
- **Current Weather**: Check the accuracy of the current weather details and weather icons.
- **7-Day Forecast**: Ensure the 7-day forecast is displayed correctly with temperatures and conditions.

---

## Building a Release Version

1. Navigate to **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
2. Locate the generated APK in the `build/outputs/apk` directory.

---

## Publishing the App

1. **Sign the APK**:
   - Go to **Build > Generate Signed Bundle / APK** and follow the steps to sign your APK.
2. **Upload to Google Play**:
   - Upload the signed APK to the [Google Play Console](https://play.google.com/console/about/).

---

## Notes

- This project uses the **OpenWeatherMap API** for fetching weather data, including current weather and 7-day forecasts.
- The last searched city is stored locally, allowing the app to display its weather when reopened.

---

## Contact

For any questions or issues related to the app, feel free to reach out.
