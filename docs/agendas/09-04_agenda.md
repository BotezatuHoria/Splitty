| Key | Value                                                        |
| --- |--------------------------------------------------------------|
| Date: | 09-04-2024                                                   |
| Time: | 13:45 - 14:30                                                |
| Location: | Drebbelweg-PC Hall 1 (Cubicle 14)                            |
| Chair | Tom van Leest                                               |
| Minute Taker | Tautvydas Kuklys                                              |
| Attendees: | Ilia Ilinsky, Horia Botezatu, Artur Iurasov, Tim Guldenmundt |

## Agenda Items:
Opening by chair (1 min)

Check-in: How is everyone doing? Did everybody reach the knockout criteria of last week? (2 min)
- Everyone is doing well, Everyone passed the knockout criteria

Are there any announcements by the TA? (2 min)
- The TA reminded that this week still counts as knockout criteria. This is the final week, the deadline for the code submission is Sunday. Self-Reflection and BuddyCheck deadline is Friday.

Are there any announcements by the team? (2 min)
- Tom has a resit on Thursday for ADS
- Ilia has a resit on Friday
- Meeting on Thursday is not cancelled, we will be working on the presentation

Approval of the agenda - Does anyone have any additions? (2 min)
- Everyone agrees

Sprint retrospective:
1. Approval of last minutes - Did everyone read the minutes from the previous meeting? (2 min)
- Everyone read the minutes and approve

2. Reflection on the work distribution of last week. Did everyone manage to do their part? (3 min)
- Everyone except Arthur is satisfied with the work distribution of last week. Arthur couldn't test the front-end due to a bug with lists and Mockito. TA reminded that Mockito is not necessary but very helpful.
- Kasper explains that testing our code right now won't get us "Excellent", but we should still try to do some tests, because we can still get a better score. 

3. Reflect on the quality and quantity of the work of last week. (3 min)
- Everyone is happy with the quality and quantity of work last week.
- We should now start doing the partial additional requirements.

4. Reflect on the basic requirements part of our application. (8 min)
- Everything should be close to being done. More info on next point 

(maybe start application to check during meeting as well, or to show what is meant)
- Is the admin page fully functional? 
  Admin page is fully functional. Recently added date is now working. 
- Websockets fully working? on every page?
  Websockets are 90% done. Should be finished soon.
- Debt fully working? (mark as settled)
  Arthur says that debt settlement is now working. He inquires Kasper about the fail of the basic requirement because Arthur cannot find the issue. Kasper will report on the exact bug that he found. (discussed)
- Are we able to send emails? 
  Horia finished the functionality, but the email config is still hard-coded. We should move it to the config.
- Is the language switch fully functional/implemented everywhere?
  Yes it is, but there might be some bugs that were missed. Tom will go through and test it.
- downloading json dump with most recent?
  It is done fully
- title of the event, when edited updated in the starter page overview?
  Fully functional
- statistics page done?
  Fixed and fully functional

- Testing, what was made?
  Artur tested the back-end with mockito. Horia also pushed some tests.
- Front end testing? admin testing?
  We need to figure out the front-end testing (if it's even worth to do it because of the rubric (Tom will ask on mattermost))
- Was any additional work done? 
  Yes. Horia fixed the transactions tags so that they are stored in the events. Also, he added a new entity for ExpenseType. Tim changed the invite code functionality so that it works with event importing. He also added a token to an event so that the event can be joined using it.
5. Reflect on the bonus requirements, HCI, and design. (6 min)
  We discussed the CSS addition to the project (We agreed that we need to store it in the project and later add it in scenebuilder). We agree that we should add some colors to the project.
- what additional requirements do we want to focus on?
live lanuage switch/detailed expenses/foreign currency/open debts/statistics/email
- Foreign currency is not started
- Live language is close to done
- Open debts are almost done, but some details need to be discussed (about the settlement of debts) 
- Statistics is not started yet
- We will mainly focus on foreign currency and statistics

- HCI (buttons, icons etc) done?
  We should add more HCI because its blank for now, Horia made a logo. We will discuss it later
- design (CSS), how do we want the application to look like?
  We should try to do more design because its very blank now. 

Summarize action points: Who , what , when? (10 min)
- We need to do HCI CSS, Finish all the basic requirements and also do the additional requirements. Tim suggests making an order for the issues for the rank of difficulty/reward ratio. We will discuss this more at the team meeting at Flux.
Presentation of the current app to TA (3 min)
- Presented but not needed

Feedback round: What went well and what can be improved next time? (2 min)
- Everyone is satisfied

Planned meeting duration != actual duration? Where/why did you mis-estimate? (2 min)
- Pretty good estimation

Question round: Does anyone have anything to add before the meeting closes? (2 min)
- No, Meeting is over.

Closure (1 min)
