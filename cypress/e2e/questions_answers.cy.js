import {gotoQA, validateQuestionResults} from '../utils/help_center/QA_interactions'

describe('Questions', {tags: ['@critical']}, () => {
    it('Questions answers correctly', () => {
        cy.fixture('questions').then((questions) =>{
            questions.forEach((q) => {
                gotoQA(q.question);
                cy.wait(3000);
                validateQuestionResults(q.expected);
            })
        })

    })
})