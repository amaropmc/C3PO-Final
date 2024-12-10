import { loadPlanets, populatePlanets } from "../views/planets.js";
import { loadQuiz, allPlanetsVisited } from "../views/quiz.js";
import { score } from "../views/score.js";
import { getLeaderBoard } from "../views/leaderBoard.js";
import { player, playerExists, postNewPlayer } from "./player.js";
import { renderPage } from "./routes.js";
import { div } from "/js/views/components/commons/div.js";
import { element } from "/js/views/components/commons/element.js";

let exploreMode = false;

export const home = () => {
    const container = document.getElementById("main");
    container.innerHTML = "";

    // Header and Form Container
    const headerFormDiv = div(["header-form-container"]);

        //Title
        const titleContainer = div(['title-container']);
            const title = document.createElement('h1');
            title.innerText = "c - 3 p o";
            title.classList.add("custom-font");

            const subtitle = document.createElement('h2');
            subtitle.innerText = "a star wars quiz game";
            title.classList.add("custom-font");
        
            titleContainer.appendChild(title);
            titleContainer.appendChild(subtitle);

        headerFormDiv.appendChild(titleContainer);

        //Header
        const headerContainer = div(["header-container"]);
            const dialog = element("h1", [], "Greetings! i'm C-3P0, your protocol droid guide. Welcome to the galaxy far, far away! Together, we will navigate through thrilling adventures. Stay alert, and may the Force be with you!");

            headerContainer.appendChild(dialog);

        headerFormDiv.appendChild(headerContainer);
        
        //Form
        const formContainer = div(["form-container"]);
            const form = document.createElement('form');
            form.classList.add("my-form-class");

            // User Name input
            const userNameInput = document.createElement("input");
            userNameInput.type = "text";
            userNameInput.placeholder = "What is your name, Jedi?";
            userNameInput.name = "user";
            userNameInput.id = "user"; 
            userNameInput.required = true;

            form.appendChild(userNameInput);

            // Submit button
            const submitButton = document.createElement("button");
            submitButton.textContent = "Submit";
            submitButton.type = "submit";

            form.appendChild(submitButton);

            // Form submit handler
            form.addEventListener("submit", async (event) => {
                event.preventDefault();
            
                const sessionPlayerUsername = player.username;
                console.log("Current session username:", sessionPlayerUsername);
            
                if (sessionPlayerUsername) {
                    alert(`Username already picked, proceeding as ${sessionPlayerUsername}`);
            
                    const path = "/planet";
                    window.history.pushState({}, '', path);
                    renderPage(path);
                    return; 
                }
            
                const inputUsername = document.getElementById("user").value.trim();
                console.log("Input username:", inputUsername);
            
                if (!inputUsername) {
                    alert("Tell me your name, Jedi");
                    return;
                }
            
                try {
                    const exists = await playerExists(inputUsername);
                    if (exists) {
                        alert("This name is not part of our planet. Please choose another.");
                        return;
                    }
            
                    player.username = inputUsername;
            
                    const path = "/planet";
                    window.history.pushState({}, '', path);
                    renderPage(path);
                } catch (error) {
                    console.error("Error checking username existence:", error.message);
                    alert("An error occurred while processing your request. Please try again.");
                }
            });

            formContainer.appendChild(form);    
        headerFormDiv.appendChild(formContainer);

    container.appendChild(headerFormDiv);

    //Image container
    const imageContainer = div(["image-robot-container"]);
        const image = document.createElement("img");
        image.className = "c3po-image"
        image.src = "/assets/c3po/C-3PO.jpg";
        image.alt = "C-3PO robot";

        imageContainer.appendChild(image);
    
    container.appendChild(imageContainer);            
}

export const planets = async () => {

    if(allPlanetsVisited() && !exploreMode) {

        await postNewPlayer(player);

        const path = "/leaderboard";
    
        window.history.pushState({}, "", path);
    
        renderPage(path);
        exploreMode = true;
        return;
    }

    await loadPlanets(populatePlanets);
}

export const quiz = async (planetName) => {
    await loadQuiz(planetName);
}

export const scorePage = (userScore) => {
    score(userScore);
}

export const leaderBoard = async () => {
    await getLeaderBoard();
}

