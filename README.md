# Group S (aka Team Habooba) OODJ Assignment

## Contents
- [Team Members](#team-members)
- [Task Division](#task-division)
- [Assignment Question](#assignment-question)
- [Requirements](#requirements)
- [Guidelines for Team Members](#guidelines-for-team-members)


## Team Members
- **TP084936** - ALI HASAN ABBOD ALAMMARI
- **TP085917** - DARREN CHUI WENG MUN
- **TP081705** - KURAPATKIN ALIAKSANDR
- **TP085736** - SARRVESH MATHIVANAN


## Task Division
Initial project modules where distributed as following:
- **User Management** - Kurapatkin Aliaksandr
- **Course Recovery Plan** - Darren Chui Weng Mun
- **Eligibility Check and Enrollment** - Sarrvesh Mathivanan
- **Academic Performance Reporting** - Ali Hasan Abbod Alammari
- **Email Notifications** - Kurapatkin Aliaksandr  

Project modules might be redistributed in future.
Tasks within one module would be created and placed in the project backlog.
Kanban methodology with adjustments would be used to control task completion.
A thorough workflow description could be found at [this file](./docs/workflow.md).


## Assignment Question
Assignment question is described in the
`2509-OODJ-AssignmentReq.docx` file in Moodle.  
You can 
[download](https://lms2.apiit.edu.my/mod/folder/view.php?id=980058)
it and read yourself.  
You can also refer to [this file](docs/assignment-reqs-full.md)
to read it in `.md` format.  
Submission would be opened on:
**Monday, 1 December 2025 at 12:00 AM**  
Submission is due on:
**Friday, 5 December 2025 at 5:00 PM**
> [!NOTE]
> Some questions to clarify:
> - In assignment question it's stated that a soft copy
> of documentation is required as a part of submission.
> Do we have to print a documentation? Each?
> - What is `design solution` point in assignment question at 
> `DOCUMENTS: COURSEWORK REPORT`?
> Is it a Java source code? Or is it some GUI design?
> Or application executable `.exe`?
> - `Screenshots of output of the program with
> appropriate explanations`. How do we define `appropriate`
> explanations?
> - `Limitation and Conclusion`. What does the word `Limitation`
> mean here?
> - Is it obligatory to follow the given `.csv` format?
> Can we add more fields and rename the existent ones?


## Requirements
> [!NOTE] To be described. Create a new file for it?
### Functional
#### Data Structures
All data structures are described in the class diagram.
You can find it [here](docs/diagrams/uml_class.drawio.xml).  
(`docs/diagrams/uml_class.drawio.xml`)  
A guide on downloading/uploading diagrams is [here](docs/guides.md#diagrams)


#### User management
- User CRUD (Deactivate instead of Delete).
- Auth:
  - Login/Logout.
  - Access timestamp logging in binary form.
  - RBAC with 2 roles:
    - Course Admin.
    - Academic Officer.
- Password and Credential Management:
  - Password Reset/Recovery.
  - Credential Editing.

#### Course Recovery Plan
- Display a list of all failed components.
- Manage a course recovery plan with multiple entries.
Each entry must be an object with several fields.
(see [class diagram](docs/diagrams/uml_class.drawio.xml) 
for thorough description)
- Show recovery plan progress.

#### Eligibility Check and Enrolment


#### Academic Performance Reporting

#### Email Notifications


### Non-Functional
Weave the following practices into implementation
source code:
- Packages
- Modularity
- Objects & Classes
- Abstraction
- Hierarchical Inheritance
- Encapsulation
- Polymorphism

## Guidelines for Team Members
> [!TIP]
> If it appears that after reading all the `.md` guidelines you
still have any questions, please follow this order to make
a research on the topic:
> 1. ChatGPT/DeepSeek/Grok/Claude
(Be aware of coding mistakes if not context-pretrained)
> 2. Perplexity + Google 
> 3. YouTube
> 4. Reddit
> 5. StackOverflow
(Actually the best one if u know how to find what you need.
So you can try it even before any GPT.
Or set GPT to "search"/"web" and ask it
to give the references from this website.)
> 6. Group Leader

### Toolchain/Framework
- For this assignment Team S is using JDK21 (Microsoft OpenJDK 21.0.8 recommended).  
- Target platform: Windows 10/11.  
- Any IDE would be okay, yet IntelliJ IDEA is recommended.
You don't have to use Ultimate (or any paid) edition.
Community Edition is enough for this assignment.  
- Gradle is used as a building system.  
- JavaFX implementation will be specified in the nearest future.  
- Git + GitHub basic toolchain (without CI pipeline) would be used
for version control and Kanban workflow.   
- No unit tests required (may be changed according to the lecturer's requirements).  
- No building speed requirements.  
- No execution speed or frame rate requirements.  
- No RAM usage requirements.
- Multithreading implementation is not required.

### Style and Naming Conventions
We are using the most popular Java coding conventions.
Not Android ones.  
The exact list of requirements is [here](./docs/code-style.md).

### Documentation
Documentation might be implemented in 2 ways:
1. Create your own `.md` file in `docs` directory. No formatting required.
2. Write Javadoc comments on each entity: classes, methods, fields, constants.

You can pick one or do both.  
Check [this file](./docs/code-doc.md) to understand what exactly do you need to mention in documentation.

### Diagrams
UML Class Diagram is required to be done by each member.
All classes from both UI (`JavaFX`) and back-end (functionality) must be described.  
Only custom classes must be mentioned.
No need to describe the imported classes
(`Stage`, `Scene`, `HBox`, etc. for JavaFX
and `Random`, `Scanner`, etc. for logic)

Other guidelines might be found [here](./docs/guides.md)