import { renderPage } from "./routes.js";

export const redirectPage = (path) => {
    window.history.pushState({}, "", path);
    renderPage(path);
}