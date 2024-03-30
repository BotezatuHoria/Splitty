| Key | Value                                                           |
| --- |-----------------------------------------------------------------|
| Date: | 26-03-2024                                                      |
| Time: | 13:45 - 14:30                                                   |
| Location: | Drebbelweg-PC Hall 1 (Cubicle 14)                               |
| Chair | Tautvydas Kuklys                                                |
| Minute Taker | Ilia Ilinsky                                                                |
| Attendees: | Tim Guldenmundt, Horia Botezatu, Artur Iurasov, Tom van Leest |

## Agenda Items:
Opening by chair (1 min)

Check -in: How is everyone doing? Did everybody reach the knockout criteria of last week? (2 min)

- Everybody is healthy, and everybody passed the knockout cirteria last week.

Are there any announcements by the TA? (2 min)

- Presentation schedule is up. If we can't be there, we should let the TA know so he can maybe find a better timeslot.
Two assignments need to be submited this week, product pitch and functionality. Both are formative and the functionality grades the basic requirements + extra.
- The deadline is Thursday officially but feel free to assume Monday for the functionality. 
- For product pitch a video/powerpoint is needed.

Are there any announcements by the team? (2 min)

- Tom might not be here on Thursday, but he will work remotely because he will be on a holiday.

Approval of the agenda - Does anyone have any additions? (2 min) No aditions.

Sprint restropective: 
1. Approval of last minutes - Did everyone read the minutes from the previous meeting? (2 min)

- Everybody has read and approved the last minutes.

2. Reflection on the work distribution of last week. Did everyone do their part? (5 min)
Didn't finish milestones and issues

- Invite page fully responsive? It's Fully responsive but there's no option to send an email. Horia says the invite code is based on the name, Tom disagrees, we should check about that
- Statistics page? The statistics page has an error for loading the event, but is otherwise fully functional
- Expenses page + API? Horia says it works properly all bugs are fixed. Websockets are also partially working, you can have events in parallel, but the only thing that doesn't work is the transactions that show, but only after reloading the page, he will take a look fix that soon. Both long polling and web sockets have to be used.s
- Transactions API? Arthur says it works, the tests have also 100% line coverage. 
- Admin page? The page does not exist but we can login with a password. He needs to display all the events, he also needs the json dump and reloading json files. We don't need to worry about security.
- Event page responsive? We can display people and transactions, from/all/including a person. The only thing missing is a transaction edit button.
Was any additional work done? Horia did the websockets. Debuging was a lot of additional work too. We didn't use services until now. If we want we can use services but it's one point of a section so we need to decide if it's worth it or not. 
- Tom change the People class so that they have an unique first name and last name. 
- The people api had a problem, in the event api. 
- A new method was also added to personcontroller. 
3. Reflect on the quality and quantity of work last week (5 min) 
- Taudvydas says that we need to pick the pace up, we need to be able to do at least half of the extra functionality. We want to finish the basic requirements this week. 
- Aditionally we need to test the frontend. 
4. Reflect on HCI part of our application (4 min) How much does the design count? 5%

Sprint planning (Work Distribution for This Week + Product pitch) (10 min) 

- Tim wants to do the admin page: we need to be able to download and upload a json dump, order/delete events. We can ask for help if needed. Taudvydas will help.
- Ilia wants to do the transaction edditing page and editing the title of an event. 
- Horia will do the websockets so that multiple users can join on an event.
- Tom will work on removing people and sending emails. He also want sto help Horia with the websockets.
- Artur will work on the debt setllement page.
- Taudvydas is working on the language selection part and he will help Tom with the Json dump. Tom will have to translate labels.

- We will work on the project pitch after the meeting and on Thursday. 
- We know how to form milestones and issues. We will also work on this after the meeting.
- We might meet on Wednesday.


Summarize action points: Who , what , when? (3 min) The previous passage was repeated.

Presentation of the current app to TA (2 min)  
- It looks very good, a lot has improved a lot from last week. We seem to be on time compared to other groups

Feedback round: What went well and what can be improved next time? (3 min) 
- The chair did a very well, the SCRUM of this week was done a lot better than last week.

Planned meeting duration != actual duration? Where/why did you mis -estimate? (2 min) The estimations were pretty good.

Question round: Does anyone have anything to add before the meeting closes? (3 min)
- A milestone was showed from another group was shown to us. We noticed that the milestone is one weeek long and describes all the functionality that will be implamented this week form the user's perspective. Then it's broken down into 20 issues assigned between members.


Closure (1 min)
- Until next week!