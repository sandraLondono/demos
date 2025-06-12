Cypress.Commands.add('validateArrayString', (selector, text) => {
    cy.get(selector).then($els => {
        const found = [...$els].some(el => {
            const normalized = el.innerText.trim().normalize("NFD").replace(/[\u0300-\u036f]/g, "");
            console.log(normalized)
            return normalized.includes(text);
        });

        expect(found, `Se esperaba encontrar "${text}" en al menos un elemento`).to.be.true;
    });

})