# Job Uber Android App

Source code and technical documentation for the semester projects of the Software Engineering course ([INF138](https://eclass.aueb.gr/courses/INF138/)) of the Department of Computer Science of the Athens University of Economics and Business.

The current version includes the [specification of software requirements](docs/markdown/software-requirements.md) with an adaptation of `IEEE Std 830-1998` to incorporate requirements in the form of use cases. For more details you can refer to the book [M Yakoumakis, N. Diamantidis, Software Engineering, Stamoulis, 2009](https://www.softeng.gr).


![Application Features](https://cdn.discordapp.com/attachments/354913879471423492/1020356896919662702/app_features.png)


Note: This is a mirror of the original gitlab repository where the project was developed. The project was developed in 4 separate releases, with each one changing the requirements, the documentation and the source code.

## Description

An application which allows users to declare a need for various services (such as household chores, going to the supermarket, driving children to / from school) and other users to apply to fulfill them.

Each user of the application has a profile where their personal information, ratings from other users and job requests are stored and displayed. For the request of services the user declares the deadline, the job's location and compensation. 

To search for services, the user searches with specific search terms (for example: type of job, date, time, place, range of compensation) and all the requests for services that satisfy those conditions are shown. Once the user expresses an interest in fulfilling a request, they specify the date and time that they would like to complete the work and confirm the request for the service.

The application will export statistics such as volume of payments per month, monthly service revenue from commissions to users' payments (there will be a commission rate for each payment), etc.


## Documentation
Project documentation can be found in the [markdown](https://github.com/dimits-exe/Job-Uber-Android-App/tree/master/docs/markdown) and [uml/requirements](https://github.com/dimits-exe/Job-Uber-Android-App/tree/master/docs/uml/requirements) subdirectories. 


## Executing the app
It's recommended to open and build the project in Android Studio. 

For a manual build :

```bash 
# Clone the repository
git clone https://github.com/dimits-exe/Job-Uber-Android-App.git

# Go to the project directory
cd <project directory>

# Run Gradle 
gradlew.bat

# Run the app in a custom emulator
```



## Converting Umlet diagrams

The conversion to image of Umlet diagrams placed in the docs/uml folder is done by executing the commands:


```bash
# Linux environment
cd docs
./mvnw umlet:convert
```

```bash
# windows environment
cd docs
mvnw umlet:convert
```

The only prerequisite is that the JAVA_HOME environment variable is initialized with the location of the Java JDK.

