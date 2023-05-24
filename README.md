# weather

## Features

* Mock login and registration page
  * Wrote `MockAuthApi` to demonstrate login/registration flow
  * To go past the login/registration screen, simply fill all input fields and submit. Then the
    system will take you to the Current Weather screen.
* Current weather tab
  * If permission for location is granted, it displays information about the weather in your
    approximate location
  * Without location permission, the location defaults to "Taguig City, PH"
  * Each weather information fetched is saved in a Room database
* Weather history tab
  * Displays weather information that are saved in the Room database

## Backlog

* More unit tests
* Auth via Firebase
* Logout
* Cleaner code (especially MainActivity.kt)
* ...and many more!

## How to run the app

### 1. Set the API_KEY

* Login or register at openweathermap.org and copy your API Key
  from https://home.openweathermap.org/api_keys
* Download this project's code
* Create a keys.properties file in the root folder of the project (e.g.,
  AndroidStudioProjects\weather\keys.properties)
* Add the API key into the keys.properties in this format: `API_KEY=YOUR_API_KEY_HERE`

### 2. Set the API_BASE_URL and ICONS_BASE_URL

* Copy the following lines:
    ```
      API_BASE_URL="https://api.openweathermap.org/data/2.5/"
      ICONS_BASE_URL="https://openweathermap.org/img/wn/"
    ```
* Paste them as additional entries to the keys.properties file

### 3. Run the app on Android Studio (or your favorite IDE for Android app development!)

* Note that you may need to "sync project with gradle files" before being able to build the app