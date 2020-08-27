This is an Android student project working with common UI elements such as -Button -TextView -Relative Layout -Linear Layout -A popup activity made by DisplayMetrics -user-defined buttons -Seek bars
This project is a basic math game where the user will have the chance to test their basic math skills.
In the first Activity, the user has to choose the type of operation in a drop-down menu: Addition, Subtraction, or both.
Also, the game needs the user to initialize the range of the numbers for the game.
Then the user will be taken to the next Activity where they will have 30 seconds (solved with CountDownTimer) to answer some addition and/ or subtraction math prompts based on what they chose in the previous Activity.
There are four buttons showing 3 wrong answers and the correct one. Each time that the user chooses an answer, the math prompt will get renewed and the game will account for the score.
The user has the ability to pause the game and this causes the game to hide all the buttons, the math prompt, and also, pause the timer. 
There's also a restart button.Solving the issues with the remaining time for the CountDownTimer when the game is paused was the most challenging part.
A simple algorithm is implemented to make the wrong answers look more challenging, which works with the range of the numbers in the game. 
Also, I played around with XML and the Studio to make my own buttons and colors for the first time.


NOTES:IMPROVEMENTS: 
  - The wrong answers' algorithm can certainly be improved. 
  - Right now, if you don't choose the range and the math operation in the first activity, the app WILL crash (Needs default values) 
  - Could also go for multiplication and subtraction in the next version 
