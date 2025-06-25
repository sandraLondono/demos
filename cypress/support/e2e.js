
//support file default
Cypress.on('uncaught:exception', (err, runnable) => false)

//implementacion de tags para agrupar home.css_selector.json
import registerCypressGrep from '@cypress/grep/src/support'
registerCypressGrep()

//reporte de allure
import '@shelex/cypress-allure-plugin';

//interacciones comunes
import './commands'
