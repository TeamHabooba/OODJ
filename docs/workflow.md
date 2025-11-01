# Habooba Workflow Manifest
## Contents
- [Methodology](#methodology)
  - [Board](#board)
  - [Priority](#priority)
  - [Size](#size)
  - [Dates](#dates)
  - [Assignee](#assignee)
- [Warnings](#warnings)
  - [General](#general)
  - [Current Warnings](#current-warnings)

## Methodology
For small but unstable projects like our assignments it's better
to use one of Agile methodologies. Our team is using Kanban
methodology.

Here's what ChatGPT says about Kanban:
> Kanban is an Agile project management method focused on
> visualizing work, limiting work in progress (WIP), 
> and improving flow.  
> 
> Key Principles:  
> - Visualize the workflow — tasks are shown on a Kanban board,
> typically divided into columns like To Do → In Progress → Done.
> - Limit Work in Progress (WIP) — each stage has a maximum 
> number of tasks allowed to prevent overload.
> - Manage flow — the goal is to keep work moving smoothly and
> identify bottlenecks.
> - Make process policies explicit — everyone knows how work
> is prioritized and moved.
> - Continuously improve — teams adjust workflows based on 
> data and feedback.
> 
> Characteristics:  
> - No fixed iterations (unlike Scrum).
> - Continuous delivery — tasks are completed as soon as they’re ready.
> - Flexible priorities — tasks can be added or reordered anytime.
>
> Example:  
> A typical Kanban board might look like:  
> [To Do] → [In Progress] → [Review] → [Done]

For our team Kanban workflow would be implemented with
GitHub Projects.  
You can open a GitHub Project with our Kanban board
[here](https://github.com/orgs/TeamHabooba/projects/2).
You can also access it if you go to 
[TeamHabooba](https://github.com/TeamHabooba) 
account on GitHub, then go to 
[Projects](https://github.com/orgs/TeamHabooba/projects).  
Our methodology is a bit different from the standard
Kanban. All the specifics are explained below.

### Board
There are 5 stages:
- [Backlog](#backlog)
- [Ready](#ready)
- [In progress](#in-progress)
- [Review](#review)
- [Done](#done)

All the tasks on GitHub Project Kanban board are called
`Issues`.
All [assignment parts](../README.md#task-division) are displayed
as the `Issues` in `OODJ Workflow` Project. Let's call them
`Modules` for better understanding.  
`Module` is an `Issue` with the following characteristics:  
*Type*: `Task`, *Priority*: `P2`, *Size*: `XL`,
*Start Date*: `01.11.2025` and *Target Date*: `23.11.2025`.


#### Backlog  
Means "What are we planning to do?".
All the tasks that should be completed are put here first.   
Each member would have one Module allocated
as an initial task.   
Every time when you decide to do part of your Module, please
press on Module name, then `Create a sub-issue`. This would 
be your task.  
`Sub-issues` of Module must be discussed and verified before
moving them to the `Ready` column.

#### Ready
This column contains all the tasks that were discussed/specified
enough to start doing them.  
Each member can pick up a task from here and move it to the
`In progress` column. 
> [!IMPORTANT] 
> Before moving a task from `Ready` to `In progress`,
> please specify its 
> - `Start date` - a day when you picked up a task
> - `Target date` - a date for online or offline review.
> Task must be done at that moment.
> 
> Difference between `Target date` and `Start date` must
> not be more than 3 days. 

#### In progress
Tasks moved here must be completed by the `Target date`.
Failing to do so results in a warning.
After completing a task, move it to the `Review` column.

#### Review
All the tasks must be reviewed after completion.  
- If any
mistakes or major bugs are found, you will need to fix them.
Task would be moved back to the `In progress` column.
Depending on the task current progress, issue level
(minor/major) and time remaining, `Target date` might be
moved.  
- If task was completed properly it would be moved to the
`Done` column.
> [!NOTE]
> If you took a task with a 3-day gap, but completed it
> in one hour and review was successful,
> you don't have to pick the new tasks until
> the `Target date` of completed task comes.  
> Example: you pick `Implement File Handling` on 01.xx.xxxx
> and set a `Target date` to 03.xx.xxxx. You finish it
> 2 hours later and it's reviewed the same day (01.xx.xxxx).
> You don't have to start any other task until 04.xx.xxxx.

> [!NOTE]
> `Target date` must be reasonable. You can't pick a task like
> `Eat a sandwhich` and spend 3 days on it.

#### Done
Pretty understandable. All the tasks put here were previously
completed and reviewed.

### Priority
Task priority. The higher priority is, the faster task must
be completed.
- `MD` - Modules. All the coding-related tasks must be
their `sub-issues`. Deadline is 23.11.2025
- `URG` - Urgent tasks. Must be finished the same day as
added to a `Backlog`.
- `P1` - High-priority tasks. Must be finished in 3 days.
- `P2` - Usual task. Must be finished in a week.
- `P3` - Low-priority task. Must be finished in 2 weeks.
- `ZP` - Zero priority. This task has no deadline. Can be
done in a free time, but not necessary.

> [!NOTE]
> If task `Target date` is in three days,
> but priority is `P3`, it doesn't mean that you have 2 weeks
> to finish it.  
> - **Priority** deadline specifies a maximum time period between
> `Backlog` and `Done` stages.  
> - `Start date` => `Target date` gap specifies a time period
> between `In progress` and `Done` stages.

### Size
Shows an estimated task size.
- `XL` - Modules.
- `L` - large tasks that require long
repetitive coding sessions to finish.
- `M` - medium tasks that could be finished in one long
or many short coding sessions.
- `S` - small tasks. Requires one or several short
coding sessions.
- `XS` - tiny tasks that could be finished in 10 minutes.

Code-unrelated tasks will have `S` or `XS` size. Except for
documentation.

### Dates
`Start date` - when was the task picked up from the `Ready` and
moved to the `In progress` column.  
`Target date` - task deadline.

### Assignee
Assignee is a person who was assigned to the task.
Please check your GitHub every day or set up notifications
so you won't miss any updates.

## Warnings
### General
Each group member might be warned if task wasn't
submitted on time.   
In this case, member's warning count
would be increased by 1 and task deadline would be postponed
by 2 calendar days.   
Once member reaches warning cap, they are reported to
the assignment supervisor and asked to leave the group.

Group warning cap equals **5 (FIVE)**. Might be increased or
decreased based on team workload.

### Current Warnings
2 - ALI HASAN ABBOD ALAMMARI  
2 - DARREN CHUI WENG MUN  
0 - KURAPATKIN ALIAKSANDR  
2 - SARRVESH MATHIVANAN

### Warning Log
_07.10.2025_  
ALI HASAN ABBOD ALAMMARI gets a warning for not completing 
a task in time. Current Warnings: 1.  
DARREN CHUI WENG MUN gets a warning for not completing 
a task in time and not coming to the meeting. 
Current Warnings: 1.  
SARRVESH MATHIVANAN gets a warning for not completing
a task in time and not coming to the meeting. Current Warnings: 1.

_10.10.2025_  
ALI HASAN ABBOD ALAMMARI gets a warning for not completing
a task in time. Current Warnings: 2.  
DARREN CHUI WENG MUN gets a warning for not completing
a task in time. Current Warnings: 2.  
SARRVESH MATHIVANAN gets a warning for not completing
a task in time. Current Warnings: 2.

