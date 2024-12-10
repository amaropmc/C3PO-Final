import { renderPage } from "./routes.js";

export const C3PO_URL = "http://localhost:8080/c3po/api";

renderPage(document.location.pathname);

window.addEventListener('popstate', () => {
    renderPage(document.location.pathname);
});
