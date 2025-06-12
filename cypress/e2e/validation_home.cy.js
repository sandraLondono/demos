
import {navigationHome, navigationHomeConoceMas} from '../utils/home/navigation_home_interactions'


describe('Validation Home page',{tags: ['@smoke']}, () => {
    it('Should be able to validate home page', () => {
        navigationHome()
    })

})

describe('Navigation Home page and more information', {tags: ['@regression']}, () => {
    it('Should be able to go recharge page', () => {
        navigationHomeConoceMas()
    })
})