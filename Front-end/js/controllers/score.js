
const GENERAL_QUIZ_URL = "http://localhost:8080/c3po/planet/";

export const score = (userScore) => {
    const quizFramework = document.getElementById("quiz-framework");
    quizFramework.style.display = "none"; 
    
    const result = document.createElement("div");
    result.className = "result-class"; 

    const resultContent = document.createElement('div');
    resultContent.className = "result-content";

    const textContainer = document.createElement('div');
    textContainer.className = "text-container";

    const resultMessage = document.createElement('div');
    resultMessage.className = "result-message";

    const imageContainer = document.createElement('div');
    imageContainer.className = "image-container";

    const robotGif = document.createElement('img');
    let message = '';
    if (userScore >= 3) {
        message = `Excellent, Jedi!<br> Your score is: <br> ★★★  <br> You mastered the quiz!`;
        robotGif.src = "./assets/c3po/dance2.gif"; 
    } else if (userScore >= 2) {
        message = `Good job, Jedi! <br> Your score is: <br>  ★★  <br> You did well!`;
        robotGif.src = "./assets/c3po/r2.png"; 
    } else if (userScore >= 1) {
        message = `All god, Jedi! <br> Your score is: <br>  ★ <br> It will better next time!`;
        robotGif.src = "./assets/c3po/r2.png";     
    } else { 
        message = `Better luck next time, Jedi.<br> Your score is:<br> 0 <br> Keep practicing!`;
        robotGif.src = "./assets/c3po/r2.png"; 
    }

    resultMessage.innerHTML = message;
    robotGif.className = "robot-gif";

    textContainer.appendChild(resultMessage);
    imageContainer.appendChild(robotGif);

    resultContent.appendChild(textContainer);
    resultContent.appendChild(imageContainer);

    const backButton = document.createElement('button');
    backButton.className = "close-btn";
    backButton.innerHTML = "Back";
    backButton.onclick = () => {
        result.style.display = "none";  
        const planets = document.getElementById("planets"); 
        if (planets) {
            planets.style.display = "flex";  // Show the planets again
        }
    }

    result.appendChild(resultContent);
    result.appendChild(backButton);

    document.body.appendChild(result);
};