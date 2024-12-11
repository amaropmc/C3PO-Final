
import { div } from "/js/views/components/commons/div.js";
import { element } from "/js/views/components/commons/element.js";
import { redirectPage } from "../controllers/redirect.js";


export const score = (userScore) => {
    const mainElement = document.getElementById("main");
    mainElement.innerHTML = "";

    const robotGif = document.createElement('img');
    robotGif.className = "robot-gif";
        
    let message = "";

    switch(userScore){
        case 3:
            message = `Excellent, Jedi! <br> Your score is: <br> ${userScore}  <br> You mastered the quiz!`;
            robotGif.src = "/assets/c3po/dance2.gif"; 
            break;
        case 2:
            message = `Good job, Jedi! <br> Your score is: <br> ${userScore} <br> You did well!`;
            robotGif.src = "/assets/c3po/r2.png"; 
            break;
        case 1:
            message = `All good, Jedi! <br> Your score is: <br> ${userScore} <br> Better luck next time!`;
            robotGif.src = "/assets/c3po/r2.png";     
            break;
        default:
            message = `Better luck next time, Jedi.<br> Your score is:<br> ${userScore} <br> Keep practicing!`;
            robotGif.src = "/assets/c3po/r2.png"; 
    }

    const resultContainer = div(["result-container"]);

        const backButton = element('button', ["back-button"], "back");
        backButton.onclick = () => {
            redirectPage("/planet");
        }

        const resultContent = div(["result-content"]);

            const textContainer = div(["text-container"]);

                const resultMessage = div(["result-message"]);
                resultMessage.innerHTML = message;
                textContainer.appendChild(resultMessage);
            
            resultContent.appendChild(textContainer);

            const imageContainer = div(["image-result-container"]);
                imageContainer.appendChild(robotGif);

            resultContent.appendChild(imageContainer);
        
        resultContainer.appendChild(resultContent);
        resultContainer.appendChild(backButton)
    mainElement.appendChild(resultContainer);
};