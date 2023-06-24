# Locality Review Application

## Overview
The Locality Review Application is a web-based platform that allows users to share and explore reviews and recommendations about various localities. Whether you're new to an area or looking to discover hidden gems in your own neighborhood, this application provides a platform for users to share their experiences and insights about different localities.

## Key Features
- User Registration: Users can create an account and login to the application.
- Locality Search: Users can search for specific localities or browse through different regions.
- Locality Reviews: Users can read and post reviews about localities, including details about amenities, safety, transportation, and more.
- Locality Events : Users can read and post about the events that take place in that locality. Events can be of categories - Healthcare campaign, festival celebration, etc.

## Technologies Used
- Frontend: React
- Backend: Spring boot and microservice
- Database: MySQL
- Testing : JUnit and Mockito

## Tools Required

The following are the tools and dependencies needed to locally run this project:

* [Node.js](https://nodejs.org/en/)
* [Java IDE with JDK](https://www.jetbrains.com/idea/download/)
* [MySQL Database](https://dev.mysql.com/downloads/mysql/)
* [Git](https://git-scm.com/downloads/)

## Installation
To run the Locality Review Application locally, follow these steps:


* First step clone the repo
    ```
    https://github.com/Subham-dury/locality.git
    ```
* Let us start by configuring and getting the backend running.

* Open the backend spring boot projects in Java IDE and enter the database connection details in `application.properties` files of all the backend projects. The files are located in `src/main/resources/application.properties`.

* Run the backend projects. To run them, you need to run the `main()` method in the main classes respectively.

* Open `localhost:8671` to start the eureka server. If everthing runs correctly all the services should be listed down and status should be **UP**.

* At this point it is important to know that the application has no support for admin resgistration. Admin needs to be added directly into the database.

* Go to the frontend project directory and install npm dependencies, then run the project
    ```
    npm install
    npm start
    ```

* The application is now running on your local machine. You can visit it by going to the following link
    ```
    http://localhost:3000
    ```


## License
The Locality Review Application is released under the [MIT License](LICENSE).

## Contact
If you have any questions or suggestions, feel free to reach out to me at [karchowdhurysubham@gmail.com](mailto:karchowdhurysubham@gmail.com).

Happy reviewing and exploring localities!
