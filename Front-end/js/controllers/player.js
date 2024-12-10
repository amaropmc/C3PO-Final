import { C3PO_URL } from "./home.js";

export let player = {
    username: "",
    score: 0
}

export const playerExists = async (userName) => {
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


export const updateUserScore = (newScore) => {
    player.score = newScore;
    console.log(player.score);
}

export const postNewPlayer = async (player) => {
    try {
        const response = await fetch(`${C3PO_URL}/player/add`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(player)
        })
    
        if (!response.ok) {
            throw new Error(`HTTP error status: ${response.status}`);
        }
    } catch (error) {
    console.error('Error making POST request:', error);
    }
}