import { element } from "/js/views/components/commons/element.js";
import { div } from "/js/views/components/commons/div.js";
import { C3PO_URL } from "../controllers/index.js";
import { redirectPage } from "../controllers/redirect.js";

export const getLeaderBoard = async () => {
    const data = await fetch(`${C3PO_URL}/player`);
    const allPlayers = await data.json();

    renderLeaderBoard(allPlayers);
}

const renderLeaderBoard = (players) => {

    const mainElement = document.getElementById("main");
    mainElement.innerHTML = "";

    const boardContainer = div(["leader-board-container"]);
    
        const title = element('h1', ["leader-board-title"], "top 5 jedi");
        boardContainer.appendChild(title);

        for (let i = 0; (i < players.length && i < 5); i++) {
            const playerCard = div(["leader-board-playerCard"]);
            
                const playerName = div(["leader-board-usernames"]);
                playerName.innerHTML = players[i].username;

                const playerTotalScore = div(["leader-board-scores"]);
                playerTotalScore.innerHTML = players[i].score;

                playerCard.appendChild(playerName);
                playerCard.appendChild(playerTotalScore);
            
            boardContainer.appendChild(playerCard);
        };
    
        const buttonContainer = div(["btn-container"]);

            const replayBtn = document.createElement('button');
            replayBtn.className = "replay-btn";
            replayBtn.innerHTML = "homepage";
            replayBtn.addEventListener('click', () => {
                redirectPage("/");
            })
            buttonContainer.appendChild(replayBtn);

            const exploreBtn = document.createElement('button');
            exploreBtn.className = "explore-btn";
            exploreBtn.innerHTML = "explore!";
            exploreBtn.addEventListener('click', () => {
                redirectPage("/planet");
            })
            buttonContainer.appendChild(exploreBtn);

        boardContainer.appendChild(buttonContainer);
    mainElement.appendChild(boardContainer);
}