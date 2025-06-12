Cypress.Commands.add('waitForPageToLoad', () => {
    cy.document().should((doc) => {
        expect(doc.readyState).to.equal('complete')
    })
})

Cypress.Commands.add('waitForSpinnerToDisappear', (selector) => {
    cy.get(selector, { timeout: 10000 }).should('not.exist')
})