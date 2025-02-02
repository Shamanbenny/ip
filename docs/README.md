# nightcoder.NightCoder User Guide

## Welcome to nightcoder.NightCoder

nightcoder.NightCoder is your playful and motivational coding companion for late-night sessions. With a vibrant personality and powerful task management features, nightcoder.NightCoder is here to keep you organized, inspired, and focused.

---

## Features Overview

### Getting Help
Access a quick reference guide for all available commands.

**Command Syntax:**
```
help
```

**Example:**
```
help
```

**Expected Outcome:**
```
	[ Night Code Command Guide ]
	Need a hand? No problem! Here's what I can do for you:
	    help
	    - Prints this handy guide. Because even pros need reminders sometimes.
	    ...
```

---

### Adding To-Dos
nightcoder.NightCoder lets you quickly add tasks to your to-do list, ensuring you don't forget important tasks during your coding adventures.

**Command Syntax:**
```
todo <task description>
```

**Example:**
```
todo Finish the project report
```

**Expected Outcome:**
```
	[ nightcoder.task.Task #1 Added: Finish the project report ]
	Got it! I'll keep this safe in your to-do list. Let me know what's next!
```

---

### Adding Deadlines
Keep track of time-sensitive tasks by adding deadlines.

**Command Syntax:**
```
deadline <task description> /by <due date and time>
```

**Example:**
```
deadline Submit assignment /by 2025-01-30 23:59
```

**Expected Outcome:**
```
	[ nightcoder.task.Task #2 Added: Submit assignment ]
	Got it! I'll keep this safe in your to-do list. Let me know what's next!
```

---

### Adding Events
Schedule events with specific start and end times to manage your calendar effectively.

**Command Syntax:**
```
event <event description> /from <start time> /to <end time>
```

**Example:**
```
event Team meeting /from 2025-01-21 3:00 PM /to 2025-01-21 4:00 PM
```

**Expected Outcome:**
```
	[ nightcoder.task.Task #3 Added: Team meeting ]
	Got it! I'll keep this safe in your to-do list. Let me know what's next!
```

---

### Viewing Your To-Do List
Get an overview of your tasks, including their completion status and descriptions.

**Command Syntax:**
```
list
```

**Example:**
```
list
```

**Expected Outcome:**
```
	1. [T][ ] Finish the project report
	2. [D][ ] Submit assignment (by: 2025-01-30 23:59)
	3. [E][ ] Team meeting (from: 2025-01-21 3:00 PM to: 2025-01-21 4:00 PM)
```

---

### Marking Tasks as Complete
Track your progress by marking tasks as completed.

**Command Syntax:**
```
mark <task number>
```

**Example:**
```
mark 1
```

**Expected Outcome:**
```
	[ nightcoder.task.Task Marked as Complete! ]
	Great job! nightcoder.task.Task "Finish the project report" is now marked as done. On to the next one!
```

---

### Marking Tasks as Incomplete
Need to revisit a task? Mark it as incomplete.

**Command Syntax:**
```
unmark <task number>
```

**Example:**
```
unmark 1
```

**Expected Outcome:**
```
	[ nightcoder.task.Task Marked as Incomplete! ]
	Got it! nightcoder.task.Task "Finish the project report" is back on your to-do list. Let's tackle it when you're ready!
```

---

### Deleting Tasks
Clean up your to-do list by deleting tasks you no longer need.

**Command Syntax:**
```
delete <task number>
```

**Example:**
```
delete 2
```

**Expected Outcome:**
```
	[ nightcoder.task.Task Deleted! ]
	nightcoder.task.Task "Submit assignment" has been removed from your list. Poof, it's gone! Let me know if there's anything else to tidy up.
```

---

### Exiting nightcoder.NightCoder
Wrap up your session and exit nightcoder.NightCoder gracefully.

**Command Syntax:**
```
bye
```

**Example:**
```
bye
```

**Expected Outcome:**
```
	Alright, signing off for now. Remember, even the brightest coders need some rest...
	Goodnight, and happy coding!
	Powering Down...
```

---

## Summary
nightcoder.NightCoder simplifies task management while keeping your coding spirits high. Whether you're organizing deadlines or clearing your to-do list, nightcoder.NightCoder is always here to assist. Happy coding!

