import {navigationHome} from '../home/navigation_home_interactions';
import home from '../../selectors/home_ui';
import qa from '../../selectors/qa_ui';

export const gotoQA = (question) => {
    navigationHome();
    cy.get(home.menu_ayuda).click();
    cy.waitForPageToLoad()
    cy.get(qa.search_question).type(question)
}

export const validateQuestionResults = (question) => {
    cy.get(qa.results).should('have.text', question);
}