
import { div } from "/js/views/components/commons/div.js";
import { renderPage } from "./home.js";

const GENERAL_QUIZ_URL = "http://localhost:8080/c3po/planet/";

export const score = (userScore) => {
    const mainElement = document.getElementById("main");
    mainElement.innerHTML = "";

    const robotGif = document.createElement('img');
    robotGif.className = "robot-gif";
        
    let message = '';

    if (userScore == 3) {
        message = `Excellent, Jedi! <br> Your score is: <br> ★★★ <br> You mastered the quiz!`;
        robotGif.src = "/assets/c3po/dance2.gif"; 
    } else if (userScore == 2) {
        message = `Good job, Jedi! <br> Your score is: <br> ★★ <br> You did well!`;
        robotGif.src = "/assets/c3po/r2.png"; 
    } else if (userScore == 1) {
        message = `All good, Jedi! <br> Your score is: <br> ★ <br> Better luck next time!`;
        robotGif.src = "/assets/c3po/r2.png";     
    } else { 
        message = `Better luck next time, Jedi.<br> Your score is:<br> 0 <br> Keep practicing!`;
        robotGif.src = "/assets/c3po/r2.png"; 
    }

    const resultContainer = div(["result-container"]);

        const backButton = document.createElement('button');
        backButton.className = "back-btn";
        backButton.innerHTML = "Back";
        backButton.onclick = () => {
            const path = "/planet";

            window.history.pushState({}, "", path);

            renderPage(path);
        }

        resultContainer.appendChild(backButton)

        const resultContent = div(["result-content"]);

            const textContainer = div(["text-container"]);

                const resultMessage = div(["result-message"]);
                resultMessage.innerHTML = message;
                textContainer.appendChild(resultMessage);
            
            resultContent.appendChild(textContainer);

            const imageContainer = div(["image-container"]);
                imageContainer.appendChild(robotGif);

            resultContent.appendChild(imageContainer);
        
        resultContainer.appendChild(resultContent);
    mainElement.appendChild(resultContainer);
};