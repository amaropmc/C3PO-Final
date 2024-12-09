import { div } from "/js/views/components/commons/div.js";
import { element } from "/js/views/components/commons/element.js";
import { loadPlanets, populatePlanets } from "./planets.js";
import { loadQuiz } from "./quiz.js";

const C3PO_URL = "http://localhost:8080/c3po/api";

const home = () => {
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
            const dialog = element("h1", [], "Greetings! I am C-3P0, your protocol droid guide. Welcome to the galaxy far, far away! Together, we will navigate through thrilling adventures. Stay alert, and may the Force be with you!");

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
            form.addEventListener("submit", async(event) => {
                event.preventDefault(); 

                const userName= document.getElementById("user").value.trim();

                if(!userName){
                    alert("Tell me your name, Jedi");
                    return;
                }

                try{
                    const exists = await playerExists(userName);
                    if (exists){
                        alert("This name is not part of our planet. Please choose another.");
                        return;
                    }      

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
        image.src = "/assets/C-3PO.jpg";
        image.alt = "C-3PO robot";

        imageContainer.appendChild(image);
    
    container.appendChild(imageContainer);            
}

const playerExists = async (userName) => {
    try {
        const response = await fetch(`${C3PO_URL}/player/${userName}`);

        if (response.ok) {
            return true;
        } else return false;

    } catch (error) {
        console.error("Network or other error:", error);
        return false;
    }
};

const planets = async () => {
    await loadPlanets(populatePlanets);
}

const quiz = async (planetName) => {
    await loadQuiz(planetName);
}

export const renderPage = (path) => {
    const route = routes.find(r => {
        const match = path.match(new RegExp(`^${r.path.replace(/:\w+/g, "(\\w+)")}$`)); // Capture dynamic parts
        return match;
    });

    if (route) {
        const match = path.match(new RegExp(`^${route.path.replace(/:\w+/g, "(\\w+)")}$`)); // Same dynamic capture
        const params = match ? match.slice(1) : [];
        route.page(...params); // Pass the dynamic parameters to the route's page function
    } else {
        console.log("Route not found!");
    }
}

const routes = [
    { path: "/", page: home },
    { path: "/planet", page: planets },
    { path: "/planet/:planetName/quiz", page: quiz }
]

window.onload = () => {
    renderPage("/");
}

