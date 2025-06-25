describe('Validación de links con control de flujo Cypress', {tags: ['@critical']},() => {
    const failedLinks = [];

    it('Valida todos los enlaces', () => {
        cy.visit('/');

        cy.get('*').then($elements => {
            const validLinks = [];

            $elements.each((_, el) => {
                const tag = el.tagName.toLowerCase();
                const href = el.getAttribute('href');
                const onclick = el.getAttribute('onclick');

                const isLink = tag === 'a' && href && !href.startsWith('#') && !href.startsWith('mailto:');
                const isButtonWithRedirect = tag === 'button' && onclick && onclick.includes('location.href');

                if (isLink || isButtonWithRedirect) {
                    validLinks.push({ href, onclick, tag });
                }
            });

            cy.wrap(validLinks).each(link => {
                let finalUrl = '';

                if (link.href && link.href !== 'javascript:void(0);') {
                    finalUrl = link.href.startsWith('http')
                        ? link.href
                        : `${Cypress.config().baseUrl}${link.href.startsWith('/') ? link.href : '/' + link.href}`;
                } else if (link.onclick?.includes('lo_mas_buscado_principal')) {
                    const path = extraerRutaDesdeOnClick(link.onclick);
                    if (path) {
                        finalUrl = `${Cypress.config().baseUrl}/${path}`;
                    }
                }

                /*if (
                    finalUrl &&
                    !finalUrl.includes('api.whatsapp.com') &&
                    !finalUrl.includes('facebook') &&
                    !finalUrl.includes('tiktok') &&
                    !finalUrl.includes('instagram') &&
                    !finalUrl.includes('youtube') &&
                    !finalUrl.includes('apps.apple') &&
                    !finalUrl.includes('play.google')
                ) {*/
                    cy.request({
                        url: finalUrl,
                        failOnStatusCode: false
                    }).then(response => {
                        if (response.status >= 400) {
                            failedLinks.push({
                                url: finalUrl,
                                tag: link.tag,
                                status: response.status
                            });
                        }
                    });
                //}
            });
        });

        cy.then(() => {
            if (failedLinks.length > 0) {
                const errores = failedLinks
                    .map(e => `${e.status} - <${e.tag}> - ${e.url}`)
                    .join('\n');

                cy.allure().attachment('Enlaces rotos', errores, 'text/plain');
                throw new Error(`Se encontraron enlaces rotos:\n${errores}`);
            }
        });
    });
});

function extraerRutaDesdeOnClick(onclick) {
    const match = onclick?.match(/lo_mas_buscado_principal\('([^']+)'\)/);
    return match ? match[1] : null;
}
