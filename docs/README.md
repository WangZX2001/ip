# Algo User Guide

Algo is a **command-line task manager** that helps users organize and track tasks efficiently.  
It is designed for users who prefer typing commands rather than using graphical interfaces.

Algo supports three types of tasks:

- Todo tasks
- Deadline tasks
- Event tasks

All tasks are automatically saved so that your task list persists between sessions.

---

## Adding deadlines

Adds a task that must be completed before a specific date or time.

Example:

`deadline <description> /by yyyy-MM-dd [HHmm]`

Example usage:

`deadline submit report /by 2026-09-20`

`deadline project meeting /by 2026-09-20 1800`

Expected outcome:

```
Got it. I've added this task:
[D][ ] submit report (by: Sep 20 2026)
Now you have 1 tasks in the list.
```

---

## Adding todo tasks

Adds a simple task without any date or time constraints.

Example:

`todo <description>`

Example usage:

`todo read book`

Expected outcome:

```
Got it. I've added this task:
[T][ ] read book
Now you have 1 tasks in the list.
```

---

## Adding events

Adds a task that occurs within a time period.

Example:

`event <description> /from yyyy-MM-dd [HHmm] /to yyyy-MM-dd [HHmm]`

Example usage:

`event team meeting /from 2026-09-20 1400 /to 2026-09-20 1600`

Expected outcome:

```
Got it. I've added this task:
[E][ ] team meeting (from: Sep 20 2026 14:00 to: Sep 20 2026 16:00)
```

---

## Listing tasks

Displays all tasks currently stored.

Example:

`list`

Expected outcome:

```
Here are the tasks in your list:
1. [T][ ] read book
2. [D][ ] submit report
```

---

## Marking tasks as done

Marks a task as completed.

Example:

`mark <task number>`

Example usage:

`mark 2`

Expected outcome:

```
Nice! I've marked this task as done:
[D][X] submit report
```

---

## Unmarking tasks

Marks a completed task as not done.

Example:

`unmark <task number>`

---

## Deleting tasks

Removes a task from the list.

Example:

`delete <task number>`

Example usage:

`delete 1`

Expected outcome:

```
Noted. I've removed this task:
[T][ ] read book
Now you have 1 tasks in the list.
```

---

## Finding tasks

Searches for tasks that contain a specific keyword.

Example:

`find <keyword>`

Example usage:

`find report`

Expected outcome:

```
Here are the matching tasks in your list:
1. [D][ ] submit report
```

---

## Exiting the program

Closes the application.

Example:

`bye`

Expected outcome:

```
Bye. Hope to see you again soon!
```

---

## FAQ

**Q: What happens if the save file is deleted?**

Algo will start with an empty task list the next time it runs. A new save file will be created automatically.

**Q: Where are my tasks stored?**

Tasks are automatically saved in:

`data/algo.txt`

This file is created automatically when Algo runs.

**Q: Can I edit the save file manually?**

Yes, but it is not recommended. If the formatting is incorrect, Algo may treat the save file as corrupted.

---

## Known Issues

**Issue: Invalid task numbers**

If a task number that does not exist is entered, Algo will display an error message.

Example:

```
Invalid task number.
```

---

**Issue: Incorrect date format**

Dates must follow this format:

`yyyy-MM-dd`

Optional time format:

`HHmm`

Example:

```
deadline submit report /by 2026-09-20 1800
```

If the format is incorrect, Algo will show a usage message.

---

**Issue: Corrupted save file**

If the save file format is modified incorrectly, Algo may display:

```
Save file is corrupted.
```

In this case, the application will start with an empty task list.

---

## Command Summary

| Command        | Format                                                              |
|----------------|---------------------------------------------------------------------|
| List tasks     | `list`                                                              |
| Add todo       | `todo <description>`                                                |
| Add deadline   | `deadline <description> /by yyyy-MM-dd [HHmm]`                      |
| Add event      | `event <description> /from yyyy-MM-dd [HHmm] /to yyyy-MM-dd [HHmm]` |
| Mark task      | `mark <task number>`                                                |
| Unmark task    | `unmark <task number>`                                              |
| Delete task    | `delete <task number>`                                              |
| Find tasks     | `find <keyword>`                                                    |
| Exit program   | `bye`                                                               |

---