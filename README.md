
# SENG300-Project Journal logging system
**Version 1.0v** \
Web based University Journal logging system<br/>
<br/>
This a system that allows user of many types user to interact with journals logging system. 
This was for our software engineering course which focus on how to devolop software with proper methodology/practices and agile development, which we used SCRUM. 

#### Users

- Admins: Able to manage users add/remove/edit users.
- Researcher: Able sumbits papers into a specfic jouranls, assign to a reviewer and editor,see comments/status of papers. 
- Editors: Able to download papers and update status of journals.
- Reviewers: Able to comment on the paper. 
##### See [user case details](#user-case) for more details

#### Sample Login Demo
![Demo](/screenshots/demo.gif)

---
### Table of Contents

- [Stack](#stack)
- [Prerequisites](#prerequisites) 
- [Development set up / How to Run](#development-set-up--how-to-run)  
- [Our Design Diagrams](#our-design)
- [Contributors](#contributors)
- [We did we learn?](#we-did-we-learn)
---

## Stack

- We are going to use Java (make sure java 8 jdk is installed) with Vaadin a web framework.
- node.js (required) 
- Database: JSON files stored locally 

### Prerequisites

#### Maven is required for testing/deployment.

intellaj: https://www.youtube.com/watch?v=pt3uB0sd5kY \
eclipse: https://www.youtube.com/watch?v=Dq5mm0tkSsw, \
https://vaadin.com/docs/v7/designer/designer-installing-eclipse.html

### Development set up / How to Run

##### Clean starter website version
1) https://vaadin.com/start/latest 
2) select plain severlet instructions 
3) Open the maven window on the IDE intellaj (crtl+E) then click the play button 
4) it should automatically install the dependencies it will take awhile.
5) open your browser are type localhost:8080 to run

or

##### Our website 
1) git clone this repo
2) Open the maven window on the IDE intellaj (crtl+E) then click the play button 
3) it should automatically install the dependencies it will take awhile.
4) open your browser are type localhost:8080 to run

### Editing

We will be doing most of our work in the src file in this project.<br/>
Html Styling in Frontend folder.

## Our Design
We used these Diagrams below:
#### UI Design
![UI](/screenshots/UI%20Idea.png)

#### RM 
Adapted for JSON
![RM](/screenshots/SENG%20300%20RM.png)
[Click here to see how we readed JSON using Binder in vaadin](/screenshots/Binder.png) 

#### User Case 
![Usercase](/screenshots/Use%20Case%20Diagram.png)

#### Class Diagram 
###### Outdated 

![Class](/screenshots/Class%20Diagram.png)


## Contributors
- **Tony Wong**      | Github:[@hitony7](https://github.com/hitony7) 
  - Scrum Leader
  - Code Reviwer
  - Developer 
- **Alexander Chao**     | Github:[@AlexanderChao14](https://github.com/AlexanderChao14) 
   - Team Lead
   - Developer
- **Sean Park** | Github:[@WelcomeBakC](https://github.com/WelcomeBakC) 
  - UI Designer
  - Developer 
- **Josha Ngo** | Github: [@Splaysational](https://github.com/Splaysational) 
  - Tester 
  - Developer
 - **Sohaib Bajwa** | Email: sohaib.bajwa1@ucalgary.ca
    - Product Owner 
    - Our Professor

#### We did we learn? 

- Agile Development
  - SCRUM
  - Trello to organize Product Backlog, Sprint Backlogs, and complete sprints 
- Java Web Framework ( Vaadin )
- JSON 
- Git and Githubs Workflows ( Githubs Actions, General Git commands)
- UML Class Diagrams, Sequence diagram, Data Flow diagram, Use Cases diagrams.



