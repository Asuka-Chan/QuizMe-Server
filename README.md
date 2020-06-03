# QuizMe-Server
This repo contains the server Java code for the REST server that communicates with the QuizMe Android Application. 
This server is deployed via Heroku.

Existing URLs: 
1) Open Stats and Log Dashboard - https://lit-reef-60639.herokuapp.com/openDashboard
2) Get Quiz Categories - https://lit-reef-60639.herokuapp.com/getCategories
3) Get questions - https://lit-reef-60639.herokuapp.com/getQuestions?amount=(1 - 50, default is 10)&category=(See 2nd point)&difficulty=(Easy, Medium, Hard)

Examples:-
- Get 2 Hard questions from the Art category: https://lit-reef-60639.herokuapp.com/getQuestions?amount=2&category=Art&difficulty=Hard
- Get 2 questions from  any category with any difficulty level: https://lit-reef-60639.herokuapp.com/getQuestions?amount=2
- Default - Get 10 questions from any category with any difficulty level: https://lit-reef-60639.herokuapp.com/getQuestions
