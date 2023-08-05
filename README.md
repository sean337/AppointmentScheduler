# Appointment Scheduling Java app

Purpose: The application is designed to manage and schedule appointments for a global organization. It provides features to add, update, delete, and view detailed information about appointments, customers, and reports.

Author: Sean Lee

Application Version: 1.0

Date: 08/03/2023

IDE: IntelliJ IDEA Community Edition 2023.1
JDK: Java SE 17.0.1
JavaFX: JavaFX-SDK-17.0.1

Directions to Run the Program:
1. Open the project in IntelliJ IDEA.
2. Ensure that the JDK and JavaFX versions are correctly set in the project settings.
3. Run the Main class to start the application.

Additional Report: The additional report provides a monthly overview of all appointments. It includes the month, the type of appointment, and the total number of each type of appointment for the selected month.

MySQL Connector Driver Version: mysql-connector-java-8.0.27

Scenario:

You are working for a software company that has been contracted to develop a scheduling desktop user interface application. The contract is with a global consulting organization that conducts business in multiple languages and has main offices in Phoenix, Arizona; New York, New York; and London, England. The consulting organization has provided a MySQL database that your application must pull data from. The database is used for other systems and therefore its structure cannot be modified.

The organization outlined specific business requirements that must be included as part of the application. From these requirements, a system analyst at your company created solution statements for you to implement in developing the application. These statements are listed in the requirements section.

Requirements:

Your submission must be your original work. No more than a combined total of 30% of the submission and no more than a 10% match to any one individual source can be directly quoted or closely paraphrased from sources, even if cited correctly. Use the Turnitin Originality Report available in Taskstream as a guide for this measure of originality.

You must use the rubric to direct the creation of your submission because it provides detailed criteria that will be used to evaluate your work. Each requirement below may be evaluated by more than one rubric aspect. The rubric aspect titles may contain hyperlinks to relevant portions of the course.

A. Create a log-in form that can determine the user’s location and translate log-in and error control messages (e.g., “The username and password did not match.”) into two languages.

B. Provide the ability to enter and maintain customer records in the database, including name, address, and phone number.

C. Write lambda expression(s) to schedule and maintain appointments, capturing the type of appointment and a link to the specific customer record in the database.

D. Provide the ability to view the calendar by month and by week.

E. Provide the ability to automatically adjust appointment times based on user time zones and daylight saving time.

F. Write exception controls to prevent each of the following. You may use the same mechanism of exception control more than once, but you must incorporate at least two different mechanisms of exception control.

• scheduling an appointment outside business hours • scheduling overlapping appointments • entering nonexistent or invalid customer data • entering an incorrect username and password

G. Use lambda expressions to create standard pop-up and alert messages.

H. Write code to provide reminders and alerts 15 minutes in advance of an appointment, based on the user’s log-in.

I. Provide the ability to generate each of the following reports:

• number of appointment types by month • the schedule for each consultant • one additional report of your choice

J. Provide the ability to track user activity by recording timestamps for user log-ins in a .txt file. Each new record should be appended to the log file, if the file already exists.
