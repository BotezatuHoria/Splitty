# Splitty

Welcome to **Splitty**! This application **helps** you with party organization and bill splitting. 🚀

## Features

1. **Event Management**: Create an event and add participants. You can then add expenses, which will be efficiently split among the members! 😎 
2. **Debt Tracking**: Easily see how much other people owe you and how much you owe them.
3. **Debt Settlement**: If you want to settle the debt, it can be done easily if the other person provides their bank account details.

## Installation and Running the App

1. You have two options:
   - **Clone the Repository**: Use either SSH or HTTPS to clone this repository to any folder on your PC using the following command:
     `
     git clone git@gitlab.ewi.tudelft.nl:cse1105/2023-2024/teams/oopp-team-53.git
     `
   - **Download Source Code**: Download the source code for the app from the main page of the project on GitLab ([link](https://gitlab.ewi.tudelft.nl/cse1105/2023-2024/teams/oopp-team-53)) and unzip it to a folder on your PC.
2. Navigate to that folder via Command Prompt or Windows PowerShell using:
   `
   cd <path to the folder>
   `
3. Run the following commands:
   ```
   ./gradlew build
   ./gradlew bootRun
   ./gradlew run
   ```

## Starting using the app
* The user should should set his credentials (email and password) in the config file
* For the config file, the user must have an app password that they can obtain from Google.
First screen
1. **Language Selection**: Choose your preferred language.
2. **Template Download**: Download a template for your language to add your own translations.
3. **High Contrast Mode**: Enable high contrast mode for better distinction of elements, which can be useful for colorblind people.
4. **Admin Panel Login**: Log in to the admin panel.
5. **Server Configuration**: Change the server you are connected to.
6. **OK**: After pressing "OK" button, you'll enter the main page where you can view recently visited events, join events, and create new ones.

## Navigation

1. Keybord shortcats: 
- ctrl + e = go to the add expense page
- ctrl + i = go to the invite sending page
- ctrl + s = go to the statistics page
- ctrl + p = go to the add participant page
- ctrl + d = go to the debt overview page
- ctrl + h = go to the starter page 
- esc = settings page
- And finally if you are in the expense, invite sending, statistics, participant and debt overview pages you can always just press ctrl + b to go back to the event page.
2. You can navigate the app using the Tab key or arrow keys and press enter to select an option or enter a field.
3. When you’re in a textarea, you can press Ctrl + Tab to exit that field. This keyboard shortcut allows you to quickly move focus away from the current input area. 
* There are numerous error messages and confirmations to make sure the user is aware of what they are doing.
---
(Line 334-351 in event page controller, Lines 358-396 for websockets connection and registering for messages in ServerUtils)
---
Feel free to explore our app and enjoy your parties using Splitty! 🎉