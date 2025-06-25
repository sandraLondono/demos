describe('test', () => {
    const failedLinks = [];

    it('Valida todos los enlaces', () => {
        cy.visit('/');

        cy.get('body a, body [onclick]').then($elements => {
            const elementosProcesados = [];

            $elements.each((i, el) => {
                const $el = Cypress.$(el);

                const info = {
                    index: i,
                    tag: el.tagName.toLowerCase(),
                    id: $el.attr('id'),
                    href: $el.attr('href'),
                    onclick: $el.attr('onclick'),
                    classes: $el.attr('class'),
                    text: $el.text().trim(),
                    selector: generarSelectorUnico($el)
                };
                console.log(info.selector)
                elementosProcesados.push(info);
            });


            console.table(elementosProcesados);
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
    if (href && href.includes('javascript')) return `${tag}[onclick="${onclick}"]`;


    const clases = $el.attr('class')?.split(' ').filter(c => !!c).join('.');
    return clases ? `${tag}.${clases}` : tag;
}