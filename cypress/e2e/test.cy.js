import home from "../selectors/home_ui";

describe('Validación de navegación mediante clicks reales', () => {
    let initialUrl = '';

    before(() => {
        Cypress.config('experimentalSessionAndOrigin', true);
        cy.visit('/');
        cy.url().then(url => {
            initialUrl = url;
            console.log('URL inicial:', initialUrl);
        });
    });

    it.only('Hace click en cada elemento navegable y valida que no haya error 404/500', () => {
        cy.get('body a, body [onclick]').then($elements => {
            const elementosProcesados = [];

            $elements.each((i, el) => {
                const tag = el.tagName.toLowerCase();
                const href = el.getAttribute('href');
                const onclick = el.getAttribute('onclick');

                const isValidHref = tag === 'a' && href && !href.startsWith('#') && !href.startsWith('mailto:') && href !== 'javascript:void(0);';
                const isOnclickRedirect = onclick && onclick.includes('lo_mas_buscado_principal');

                if (isValidHref || isOnclickRedirect) {
                    const $el = Cypress.$(el);
                    elementosProcesados.push({
                        selector: generarSelectorUnico($el),
                        href: $el.attr('href'),
                        onclick: $el.attr('onclick')
                    });

                    console.log(elementosProcesados.selector)
                }
            });

            console.log("************************************************************")
            console.table(elementosProcesados);
            console.log("************************************************************")
            console.log(elementosProcesados.length)
            console.log("************************************************************")

            Cypress._.each(elementosProcesados, (info, index) => {
                cy.log(`Procesando elemento ${index + 1}/${elementosProcesados.length}: ${info.selector}`);

                cy.visit(initialUrl);

                cy.get(info.selector, { timeout: 10000 })
                    .should('exist')
                    .scrollIntoView()
                    .invoke('removeAttr', 'target')
                    .click({ force: true });

                cy.url().then(currentUrl => {
                    const currentOrigin = new URL(currentUrl).origin;
                    const initialOrigin = new URL(initialUrl).origin;

                    if (currentOrigin !== initialOrigin) {
                        cy.origin(currentOrigin, {
                            args: { info, initialUrl },
                            dependencies: { generarSelectorUnico }
                        }, ({ info, initialUrl }, { generarSelectorUnico }) => {
                            cy.url().should('include', new URL(info.href).pathname);

                            cy.document().should('have.property', 'contentType', 'text/html');
                            cy.get('body')
                                .should('exist')
                                .should('not.contain', '404')
                                .should('not.contain', 'Página no encontrada');

                            cy.visit(initialUrl);
                        });
                    } else {
                        if (info.href) {
                            const expectedPath = new URL(info.href, initialUrl).pathname;
                            cy.url().should('include', expectedPath);
                        }

                        cy.document().should('have.property', 'contentType', 'text/html');
                        cy.get('body')
                            .should('exist')
                            .should('not.contain', '404')
                            .should('not.contain', 'Página no encontrada');

                        cy.go('back');
                    }
                });
            });
        });
    });
});

function generarSelectorUnico($el) {
    const tag = $el.prop('tagName').toLowerCase();
    const id = $el.attr('id');
    const href = $el.attr('href');
    const onclick = $el.attr('onclick');

    if (id) return `#${id}`;
    if (href && !href.includes('javascript')) return `${tag}[href="${href}"]`;
    if (onclick) return `${tag}[onclick="${onclick}"]`;

    const clases = $el.attr('class')?.split(' ').filter(c => !!c).join('.');
    return clases ? `${tag}.${clases}` : tag;
}