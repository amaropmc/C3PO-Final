import { div } from "/js/views/components/commons/div.js";
import { element } from "/js/views/components/commons/element.js";
import { renderPage } from "../controllers/routes.js"

const PLANETS_URL = "https://swapi.dev/api/planets/";
let planets = [];

const selectedPlanets = [
  "Tatooine",
  "Alderaan",
  "Hoth",
  "Bespin",
  "Endor",
  "Dagobah",
];

export const loadPlanets = async (func) => {
  const response = await fetch(PLANETS_URL);
  if (response.ok) {
    const data = await response.json();
    planets = data.results;

    console.log(planets);
    func();
  }
};

export const populatePlanets = () => {

  const mainElement = document.getElementById("main");
  mainElement.innerHTML = "";

  const planetContainer = div(["planet-container"]);

  const firstLine = element("h1", ["margin-top-1"], "$");
  const secondLine = element("h1", ["letter-spacing"], "May the knowledge");
  const thirdLine = element("h1", ["letter-spacing"], "be with you");

  mainElement.appendChild(firstLine);
  mainElement.appendChild(secondLine);
  mainElement.appendChild(thirdLine);

  planets = planets.filter((planet) => {
    return selectedPlanets.includes(planet.name);
  });

  planets.forEach((planet, index) => {
    const randomNumber = Math.random() * 6;

    // planet wrapper
    const planetWrapper = div(["planet-wrapper"]);
    planetWrapper.setAttribute("style", `flex-grow: ${randomNumber}`);

    //planet info
    const infoPlanetWrapper = div(["info-planet-wrapper"]);

    // planet button
    const planetButton = element("button", ["planet-button"], "Take quiz");
    planetButton.onclick = (event) => {
      event.preventDefault();

      const path = `/planet/${planet.name}/quiz`;

      window.history.pushState({}, "", path);

      renderPage(path);
    };
    
    // planet info
    const planetInfo = div(["planet-info"]);
    planetInfo.innerHTML = `<div><span class="label">$ Name:</span> ${planet.name}</div> 
    <div>
    <span class="label" >$ Rotation Period:</span>
    ${planet.rotation_period}</div> 
    <div><span class="label">$ orbital Period:</span> ${planet.orbital_period}</div> 
    <div><span class="label">$ Diameter:</span> ${planet.diameter}</div> 
    <div><span class="label">$ Climate:</span> ${planet.climate}</div> 
    <div><span class="label">$ Gravity:</span> ${planet.gravity}</div>
    <div><span class="label">$ Terrain:</span> ${planet.terrain}</div> 
    <div><span class="label">$ Surface Water:</span> ${planet.surface_water}</div> 
    <div><span class="label">$ Population:</span> ${planet.population}</div>`;

    // planet
    const planetItem = div(["div"]);
    planetItem.setAttribute(
      "style",
      `background-image: url(/assets/planets/${planet.name}.jpeg);`
    );

    let planetItemClassName = "star-wars-planets";
    if (index % 2 == 0) {
      planetButton.className = planetButton.className + " planet-button-even";
      planetItemClassName = planetItemClassName + " planet-even";
    }
    planetItem.className = planetItemClassName;

    infoPlanetWrapper.appendChild(planetItem);
    infoPlanetWrapper.appendChild(planetInfo);
    planetInfo.appendChild(planetButton);
    planetWrapper.appendChild(infoPlanetWrapper);
    planetContainer.appendChild(planetWrapper);

    mainElement.appendChild(planetContainer);
  });
};
