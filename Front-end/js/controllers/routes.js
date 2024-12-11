import { home, planets, quiz, leaderBoard, scorePage } from "./render.js";

const routes = [
    { path: "/", page: home },
    { path: "/planet", page: planets },
    { path: "/planet/:planetName/quiz", page: quiz },
    { path: "/leaderboard", page: leaderBoard },
    { path: "/planet/:userScore", page: scorePage }
]

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