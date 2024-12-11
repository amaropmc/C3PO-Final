import { div } from "/js/views/components/commons/div.js";
import { updateUserScore } from "../controllers/player.js";
import { redirectPage } from "../controllers/redirect.js";
import { visitedPlanets } from "./planets.js";


const GENERAL_QUIZ_URL = "http://localhost:8080/c3po/api/planet";

let userScore = 0;
let currentQuizUserScore = 0;

export const loadQuiz = async (planetName) => {

    if(currentQuizUserScore) {
        currentQuizUserScore = 0;
    }

    const response = await fetch(GENERAL_QUIZ_URL + `/${planetName}/quiz`);

    let planetQuiz;

    if(response.ok) {
        planetQuiz = await response.json();

    } else {
        console.error("Failed to load the quiz for:", planetName);
        return;
    }

    if(!visitedPlanets.includes(planetName)) {
        visitedPlanets.push(planetName);
        renderQuiz(planetQuiz);

    } else {
        console.log("This planet's quiz has already been taken.");
        alert("You already prove your knowledge about this planet! Try to show me your value in another one");
        
        redirectPage("/planet");
    }
}

const renderQuiz = planetQuiz => {
    
    /*Tracks which question in the planetQuiz array is currently being displayed.*/    
    let currentQuestionIndex = 0;

    const showNextQuestion = () => {
        if(currentQuestionIndex < planetQuiz.length) {
            /*
            Calls generateQuizCard with the current question and a reference to itself (showNextQuestion). This ensures the next question is displayed only when showNextQuestion is explicitly invoked 
            */
            generateQuizCard(planetQuiz[currentQuestionIndex], showNextQuestion);
            currentQuestionIndex ++;
        } else {
            redirectPage(`/planet/${currentQuizUserScore}`);
        }
    }

    showNextQuestion(); //Invoked for the first time here!
}

/*
This function takes in an object from the array and has a call back function - onAnswerSelect. This is going to trigger the showNextQuestion in the renderQuiz function.
*/

const generateQuizCard = (questionAndAnswer, onAnswerSelect) => {
    const mainElement = document.getElementById("main");
    mainElement.innerHTML = " ";

        const quizFramework = div(["quiz-framework"]);
        
            const c3poContainer = div(["robot-container"]);

                const robotImage = document.createElement('img');
                robotImage.className = "robot-image";
                robotImage.src = "/assets/c3po/C-3PO.jpg";

                const dialogBaloon = div(["dialog"]);
                dialogBaloon.innerHTML= "Good luck to you!"

                c3poContainer.appendChild(robotImage);    
                c3poContainer.appendChild(dialogBaloon);

            quizFramework.appendChild(c3poContainer);

        const quizContainer = div(["quiz-container"]);

            const questionBox = div(["question-box"]);
    
                const questionHeader = div(["question-header"]);
    
                    const backButton = document.createElement('button');
                    backButton.className = "back-btn";
                    backButton.innerHTML = "back";
                    backButton.addEventListener('click', () => {
                        event.preventDefault;

                        redirectPage(`/planet/${userScore}`);
                    })
                    
                    questionHeader.appendChild(backButton);

                    const questionCount = div(["counter"]);
                    questionCount.innerHTML = questionAndAnswer.id;
                    questionHeader.appendChild(questionCount);

            const questionContent = div(["question-content"]);
                questionContent.innerHTML = questionAndAnswer.description.toLowerCase();

            questionBox.appendChild(questionHeader);
            questionBox.appendChild(questionContent);

        const answersBox = div(["answers-box"]);

            questionAndAnswer.answers.forEach(answer => {
                const option = div(["answer-option"]);
                option.innerHTML = answer.description.toLowerCase();

                answersBox.appendChild(option);

                option.onclick = () => {
                    if (answer.correct) {
                        option.style.backgroundColor = "green";
                        dialogBaloon.innerHTML = "";
                        dialogBaloon.innerHTML = "Very well, master!";
                    } else {
                        option.style.backgroundColor = "red"; 
                        dialogBaloon.innerHTML = "";
                        dialogBaloon.innerHTML = "We're doomed!";
                    }
            
                    // Add a delay of 1 seconds before calling the callback
                    setTimeout(() => {
                        if (answer.correct) {
                            ++ currentQuizUserScore;
                            userScore += questionAndAnswer.score;
                            updateUserScore(userScore);
                        }
            
                        console.log(userScore);

                        onAnswerSelect();
                    }, 1000);
            }
        });
        
            quizContainer.appendChild(questionBox);
            quizContainer.appendChild(answersBox);

        quizFramework.appendChild(quizContainer);
    mainElement.appendChild(quizFramework);    
}

