import { div } from "/js/views/components/commons/div.js";
import { element } from "/js/views/components/commons/element.js";
import { player, playerExists } from "../controllers/player.js";
import { redirectPage } from "../controllers/redirect.js";


export const loadHomepage = async () => {
const container = document.getElementById("main");
    container.innerHTML = "";

    // Header and Form Container
    const headerFormDiv = div(["header-form-container"]);

        //Title
        const titleContainer = div(['title-container']);
            const title = element("h1", ["custom-font"], "c - 3 p o");
            titleContainer.appendChild(title);

            const subtitle = element("h2", ["custom-font"], "a star wars quiz game");
            titleContainer.appendChild(subtitle);

        headerFormDiv.appendChild(titleContainer);

        //Header
        const headerContainer = div(["header-container"]);
            const dialog = element("h1", [], "Greetings! i'm C-3P0, your protocol droid guide." +
                "Welcome to the galaxy far, far away! Together, we will navigate through thrilling adventures." +
                 "Stay alert, and may the Force be with you!");
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
            
                if (sessionPlayerUsername) {
                    alert(`Username already picked, proceeding as ${sessionPlayerUsername}`);
        
                    redirectPage("/planet");
                    return; 
                }
            
                const inputUsername = document.getElementById("user").value.trim();
            
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
                    redirectPage("/planet");
                    
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