# weather

## features

* mock login and registration page
  * wrote a MockAuthApi to demonstrate login/registration flow
* current weather tab
  * if permission for location is granted, it displays information about the weather in your
    approximate location
  * without location permission, the location defaults to "Taguig City, PH"
  * each weather information fetched is saved in a Room database
* weather history tab
  * displays weather information that are saved in the Room database

## possible updates in the future

* more unit tests
* auth via Firebase
* logout
* cleaner code (especially MainActivity.kt)
* and many more!

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