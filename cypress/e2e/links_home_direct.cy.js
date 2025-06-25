import home_css from "../selectors/home.selector.ui";
import home from '../selectors/home_ui'

describe('Validación de navegación mediante clicks reales', () => {
    const baseUrl = Cypress.config().baseUrl;
    let initialUrl = '';
    let brokenLinks = [];

    before(() => {
        cy.visit('/');
        cy.url().then(url => {
            initialUrl = url;
        });
    });

    after(() => {
        if (brokenLinks.length > 0) {
            cy.log('***** LINKS ROTOS ENCONTRADOS *****');
            brokenLinks.forEach(link => {
                cy.log(`Elemento: ${link.selector} | URL: ${link.url} | Error: ${link.error}`);
            });
        } else {
            cy.log('¡No se encontraron links rotos!');
        }
    });

    describe('Pruebas de navegación para links rotos', {tags: ['@critical']},() => {
        it('Prueba todos los selectores', () => {
            cy.fixture('home.css_selector').then((selectores) => {
                selectores.forEach((selector, index) => {

                    cy.log(`Procesando selector ${index + 1}/${selectores.length}: ${selector.selector}`);

                    cy.visit(initialUrl);
                    cy.get(home.seguir_yape_bolivia, {timeout: 10000}).should('exist');
                    cy.get(home.seguir_yape_bolivia).click();
                    cy.waitForPageToLoad()
                    cy.wait(500)

                    cy.get(selector.selector)
                        .first()
                        .should('exist')
                        .scrollIntoView()
                        .click({force: true});

                    cy.get('body').then(($body) => {
                        const is404 = $body.text().includes('404');
                        const isErrorPage = $body.text().includes('Error') ||
                            $body.text().includes('Page not found');

                        if (is404 || isErrorPage) {
                            cy.url().then(currentUrl => {
                                brokenLinks.push({
                                    selector: selector.selector,
                                    url: currentUrl,
                                    error: is404 ? 'Página 404 encontrada' : 'Página de error encontrada',
                                    status: '404'
                                });
                                cy.log(`Link roto: ${currentUrl}`);
                            });
                        }
                    });

                    cy.url().then(currentUrl => {
                        const currentOrigin = new URL(currentUrl).origin;
                        const initialOrigin = new URL(initialUrl).origin;

                        if (currentOrigin !== initialOrigin) {
                            cy.origin(currentOrigin, {args: {initialUrl}}, ({initialUrl}) => {
                                cy.visit(initialUrl);
                            });
                        } else {
                            cy.go('back');
                        }
                    });

                });
            });
        });
    });
});

