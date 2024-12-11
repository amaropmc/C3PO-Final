import { loadPlanets, populatePlanets,allPlanetsVisited } from "../views/planets.js";
import { loadQuiz } from "../views/quiz.js";
import { score } from "../views/score.js";
import { getLeaderBoard } from "../views/leaderBoard.js";
import { player, postNewPlayer } from "./player.js";
import { loadHomepage } from "../views/home.js";
import { redirectPage } from "./redirect.js";

let exploreMode = false;

export const home = async () => {
    await loadHomepage();
}

export const planets = async () => {

    if(allPlanetsVisited() && !exploreMode) {

        await postNewPlayer(player);

        redirectPage("/leaderboard");
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

