# Group S (aka Team Habooba) OODJ Assignment

## Contents
- [Team Members](#team-members)
- [Task Division](#task-division)
- [Assignment Question](#assignment-question)
- [Functional Requirements](#functional-requirements)

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
Canban methodology with adjustments would be used to control task completion.
A thorough workflow description could be found at [this file](./docs/workflow.md).


## Assignment Question
> [!INFO]  
> TO BE SPECIFIED

## Functional Requirements
> [!INFO]  
> TO BE SPECIFIED

## Guidelines for Team Members
If it appears that after reading all the `.md` guidelines you
still have any questions, please follow this order to make
a research on the topic:
1. ChatGPT/DeepSeek/Grok/Claude
(Be aware of coding mistakes if not context-trained)
2. Perplexity + Google 
3. YouTube
4. Reddit
5. StackOverflow
(Actually the best one if u know how to find what you need.
So you can try it even before GPT.
Or set GPT to "search"/"web" and ask it
to give the references from this website.)
6. Group Leader

### Toolchain/Framework
For this assignment Team S is using JDK21 (Microsoft OpenJDK 21.0.8 recommended).  
Target platform: Windows 10/11.  
Any IDE would be okay, yet IntelliJ IDEA is recommended.
You don't have to use Ultimate (or any paid) edition.
Community Edition is enough for this assignment.  
Gradle is used as a building system.  
JavaFX implementation will be specified in the nearest future.  
Git + GitHub basic toolchain (without CI pipeline) would be used
for version control and Canban workflow.   
No unit tests required (may be changed according to the lecturer's requirements).  
No building speed requirements.  
No execution speed or frame rate requirements.  
No RAM usage requirements.
Multithreading implementation is not required.

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
