import { element } from "/js/views/components/commons/element.js";
import { div } from "/js/views/components/commons/div.js";

const C3PO_URL = "http://localhost:8080/c3po/api";

export const getLeaderBoard = async () => {
    const data = await fetch(`${C3PO_URL}/player`);
    const allPlayers = await data.json();

    renderLeaderBoard(allPlayers);
}

const renderLeaderBoard = (players) => {
    const mainElement = document.getElementById("main");
    mainElement.innerHTML = "";

    const boardContainer = div(["leader-board-container"]);
    
        const title = element('h1', ["leader-board-title"], "top 10 jedi");
        boardContainer.appendChild(title);

        players.forEach(player => {
            const playerCard = div(["leader-board-playerCard"]);
            
                const playerName = div(["leader-board-usernames"]);
                playerName.innerHTML = player.username;

                const playerTotalScore = div(["leader-board-scores"]);
                playerTotalScore.innerHTML = player.score;

                playerCard.appendChild(playerName);
                playerCard.appendChild(playerTotalScore);
            
            boardContainer.appendChild(playerCard);
        });
    
        const buttonContainer = div(["btn-container"]);

            const replayBtn = document.createElement('button');
            replayBtn.className = "btn";
            replayBtn.innerHTML = "homepage";

            buttonContainer.appendChild(replayBtn);

            const exploreBtn = document.createElement('button');
            exploreBtn.className = "btn";
            exploreBtn.innerHTML = "explore!";

            buttonContainer.appendChild(exploreBtn);

        boardContainer.appendChild(buttonContainer);
    mainElement.appendChild(boardContainer);
}