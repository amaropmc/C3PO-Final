import { redirectPage } from "./redirect.js";
//import { renderPage } from "./routes.js";

export const C3PO_URL = "http://localhost:8080/c3po/api";

redirectPage(document.location.pathname);

window.addEventListener('popstate', () => {
    redirectPage(document.location.pathname);
});
