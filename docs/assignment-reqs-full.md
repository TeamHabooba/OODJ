# Brief description

## 1.  **COURSEWORK TITLE**  
COURSE RECOVERY SYSTEM (CRS) FOR EDUCATIONAL INSTITUTIONS  

  
## 2.  **THE COURSEWORK OVERVIEW**  
CRS is a software solution that is designed to help educational
institutions to manage how the students recover failed or incomplete
courses. It ensures that the students can resume on track academically
without delaying graduation. The key purpose of the solution are to
allow students to enrol in recovery process to improve academic
profiles and also allow course administrators and instructors to plan,
track and evaluate course recovery plan. To streamline this academic
study workflow, a Java-based application must be developed using
Object-Oriented Programming (OOP) principles. The system should offer
the functional requirements as stated in the section 5.0. In addition,
a program documentation is needed covering solution model design and
the implementation details that utilises the Object-oriented
programming concepts.


## 3.  **OBJECTIVES OF THIS COURSEWORK**  
Develop the practical ability to describe, justify, and implement an
Object-oriented system.


## 4.  **TYPE**  
Group Assignment (4 or 5 students)


## 5.  **COURSEWORK DESCRIPTION**  
The developed system should be GUI-driven with options. You and your
development team as Object-oriented programmers need to identify the
relationship among the object entities followed by designing and
developing the necessary classes with appropriate relationships to
fulfil the requirements of the expected systems.  

Target Users: Academic officer and Course Administrator

---
### Functional requirements:

#### 1. User Management  
This is a process of controlling and maintaining users' accounts and
accessibility and interaction with the system. The following features
are commonly included, but not limited to.
-   User accounts (Add/Update/Deactivate)
-   Authentication and Authorization
    -   Login/Logout, log the timestamp of both in binary form
    -   Role-based permission enforcement or access control
-   Password and Credential Management (Reset/Recover)

#### 2. Course Recovery Plan  
This is a special strategy process that allows students to improve
weak performance, through recovering the failed modules with specified
timeline and deadlines. The following features are commonly included,
but not limited to.
-   List out all failed components of the course i.e., assignment or
    exam for the student concerned
-   Recommendation entry for the course recovery (Add/Update/Remove)
-   Set a clear milestone for course recovery with the action plan
    (Add/Update/Remove)
    Example:

    - Course: Object Oriented Programming  
    Study week: Week 1 -- 2   
    Task: Review all lecture topic  

    - Course: Object Oriented Programming   
    Study week: Week 3  
    Task: Meeting with module lecturer  

    - Course: Object Oriented Programming   
    Study week: Week 4  
    Task: Take recovery exam  

-   Monitor/ Track the Recovery progress inclusive the grading entry

#### 3. Eligibility Check and Enrolment  
This is a process of checking whether the students meet the required
criteria to join the next level of study. The following features are
commonly included, but not limited to.  
- List out all students who are _not_ eligible to progress into next
level of study. The two eligibility criteria of progression as
follows:
   - At least CGPA 2.0, the Credit Hour System as such
   - CGPA = (Total Grade Points of all courses taken) / (Total Credit
   Hours of all courses taken)  
   Computation sample:
       - Course 1: A (4.0) × 3 credits = 12
       - Course 2: B (3.0) × 4 credits = 12
       - Course 3: C (2.0) × 2 credits = 4
       - Total = _28 / 9 = 3.11 CGPA_  
   - Not more than three failed courses to be allowed to progress into
   next level of study  
- To allow registration once the eligibility is confirmed.

#### 4. Academic Performance Reporting  
This is the formal document that provides a summary of a student's
progress, achievements, and the recommendations of improvement over a
specific period. The following features are commonly included, but not
limited to.
- Generate an academic performance report by semester and year of
study (create/exportToPDF)
- Example:  
Student Name: Alex Tan  
Student ID: 2025A1234  
Program: Bachelor of Computer Science  
Semester 1  
_table-columns:_  
Course Code, Course Title, Credit Hours, Grade, Grade Point (4.0 Scale)

#### 5.  Email Notifications
This is an automated messages send by a system to the users i.e.,
student's email address. It helps maintain communication message as
alerts, reminders, confirmations and etc between the system and its
users to improve accountability. The following are the applicable
areas for this service, but not limited to.
-   User account management
-   Password & recovery management
-   Course recovery with a clear action plan and milestone
-   Academic performance report
---
### Technical Consideration:

#### 1. Solution design practice:
This is a collection of Object-oriented principles, modelling, best
practices and documentation standards used to design and develop a
software. The following are the development practices for the solution
design.
-   Package
-   Object & Class
-   Inheritance hierarchy
-   Encapsulation
-   Polymorphism
-   Abstraction
-   Modularity

#### 2. Third party APIs:
- Java Mail API for the email notification services, 
[link](https://www.oracle.com/java/technologies/javamail-api.html)
- iText PDF, [link](https://itextpdf.com/resources/downloads)

The following are the additional descriptions for better understanding
of the academic study system.  
Overview Use Case Diagram:  
_Use Case Diagram here_

Course Retrieval Policy:
-   Each course has only three attempt opportunities
to pass the course.
-   The failed component is required only to pass by
resubmission or resit in 2^nd^ attempt.
-   If the students cannot pass the course in the 1^st^ and 2^nd^
attempts, the 3^rd^ attempt requires the students to refer to all
assessment components.

## 6. GENERAL REQUIREMENTS
- The program submitted should compile and be executed without errors
- Validation should be done for each entry from the users in order to
avoid logical errors.
- The implementation code must highlight the use of Object-oriented
programming concepts as required by the solution.
- Use text or binary files for data access and manipulation only.

## 7. DELIVERABLES:
- Java Program Documentation in softcopy form
- Working GUI-driven software prototype
- Software Demonstration or Presentation
- Submission deadline: **Friday, 5 December 2025, 5:00 PM**

## 8. DOCUMENTS: COURSEWORK REPORT
- As part of the assessment, you must submit the project report in
soft copy form, which should have the following format:
### Cover Page:  
All reports must be prepared with a *front cover*. A protective
transparent plastic sheet can be placed in front of the report to
protect the front cover. The front cover should be presented with the
following details:
-   Module
-   Coursework Title
-   Intake
-   Student name and id
-   Date Assigned (the date the report was handed out).
-   Date Completed (the date the report is due to be handed in).

### Contents:
-   Description and justification of Object-oriented programming 
concepts incorporated into the solution
-   Design solution
-   Screenshots of output of the program with appropriate explanations

### Limitation and Conclusion

### References

## 9. ASSIGNMENT ASSESSMENT CRITERIA  
The assignment assessment consists of three components: Program
Documentation (15 marks), Program implementation (40 marks). Details
of the division for each component are as follows:


| **CRITERIA**                                     | **MARK ALLOCATION**  |
|--------------------------------------------------|----------------------|
| **_Program Documentation_**                      |                      |
| Detail Use Case Diagram with description         | 5 marks              |
| Class Diagram                                    | 5 marks              |
| Report Content and Presentation or Demonstration | 5 marks              |
| _Subtotal_                                       | _15 marks_           |
| **_Program Implementation_**                     |                      |      
| User Management                                  | 5 marks              |
| Eligibility Check and Enrolment                  | 5 marks              |  
| Academic Performance Reporting                   | 5 marks              | 
| Course Recovery Plan                             | 10 marks             | 
| Object Oriented Implementation                   | 15 marks             |  
| _Subtotal_                                       | _40 marks_           |  
| **TOTAL**                                        | **55 marks**         |  


## 9. DEVELOPMENT TOOLS
The program written for this assignment should be written applying
Object Oriented Programming using JAVA language only.

## 10. ACADEMIC INTEGRITY  
-   You are expected to maintain the utmost level of academic integrity
    during the duration of the course.
-   Plagiarism is a serious offence and will be dealt with according to
    APU University regulations on plagiarism.
