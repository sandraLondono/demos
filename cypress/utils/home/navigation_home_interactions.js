import home from '../../selectors/home_ui'
import recharge from '../../selectors/recargas_ui'
import 'cypress-real-events/support';

export const navigationHome = () => {
    cy.visit('/');
    cy.get(home.seguir_yape_bolivia, { timeout: 10000 }).should('exist');
    cy.get(home.seguir_yape_bolivia).click();
    cy.screenshot('yape home');
    cy.waitForPageToLoad()
    cy.wait(100)
}

export const navigationHomeConoceMas = () => {
    navigationHome()
    cy.get(home.productos).realHover();
    cy.get(home.recargas).click();
    cy.waitForPageToLoad()
    cy.get(recharge.conoce_mas_recargas).click();
    cy.get(recharge.qr_yape).should('be.visible');
    cy.screenshot('yape qr');
}