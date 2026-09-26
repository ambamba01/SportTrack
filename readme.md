

## Installation
The project run on JDK 21.

To run the tests:
- mvn test

To run the project:
- mvn exec:java

To compile the project:
- mvn clean install

To execute the jar : 
 - ```java --module-path "path_to\javafx-sdk-21.0.2\lib" --add-modules=javafx.controls,javafx.fxml --add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED -jar path_to\infof307.jar```

The app is configured to use the db file in "src/main/resources/data/sportTrack.db". 
1. If not exist : Initialise the Database by executing the sql script "src/main/resources/data/sportTrack.db.sql"
2. Populate the Database by running the sql script "src/main/resources/data/InitDB(DEV).sql" or the "production" one

## Features

## User Profile

- When the application is launched for the first time, it assesses the user's physical abilities (maximum number of push-ups, pull-ups, sit-ups, etc.) in order to build their physical profile.

## Exercise Management

- Users must be able to create an exercise by providing:
  - A name
  - A description
  - An image
  - A difficulty level
  - The muscles targeted by the exercise

- When specifying which muscles are targeted by an exercise, the application provides autocomplete suggestions based on muscles that are already known.

- Once an exercise has been created, users must be able to edit it.

## Workout Program Management

- Users must be able to create a workout program by selecting a list of existing exercises and defining:
  - The number of repetitions
  - Rest periods
  - A name
  - A description
  - A difficulty level ranging from **1 star (very easy)** to **5 stars (extremely demanding)**

- Exercises can belong to one of the following categories:
  - Endurance
  - Strength Training
  - Agility
  - Coordination

- Users can filter workout programs based on the types of exercises they contain.

- Additional exercise types may be introduced in future updates.

- Once a workout program has been created, users must be able to edit it.

## Exercise Search and Filtering

- When adding an exercise to a workout program, users must be able to search through existing exercises using different filters.

- Exercises can be filtered according to:
  - Targeted muscles
  - Difficulty
  - Exercise type

- The filtering system must support both **including and excluding** exercises matching specific filter criteria.

## Workout Sessions

- Users can select a workout program and start a workout session.

- Depending on the type of exercise included in the program, the application displays different information and controls.

### Repetition-Based Exercises

For exercises that do not require a timer, the application displays a short explanation of the exercise and waits for the user to indicate that they have completed it.

### Timed Exercises and Rest Periods

For rest periods and timed exercises, the application automatically starts a timer and notifies the user when the allotted time has elapsed.

### Location-Based Exercises

For exercises that require the user to move from one location to another, such as running or cycling, the application displays the route map and the elapsed time.

### Workout Controls

Every exercise must provide:

- A **Skip** button to skip the current exercise
- A button to return to the **previous exercise**
- The ability to **pause** the workout session

## Calorie Tracking

- The application should be able to accurately estimate the number of calories burned during a workout session.

- The calculation should take into account:
  - The characteristics of the workout
  - The user's physical characteristics

## Rest Periods

- Users can define the duration of rest periods:
  - Between sets of the same exercise
  - Between two different exercises

## Settings

The application must include a settings section with several options.

Users must be able to:

- Switch between **light mode and dark mode**
- Choose the **application language**
- Choose the **measurement units**, including:
  - **kg / lbs**
  - **meters / feet**