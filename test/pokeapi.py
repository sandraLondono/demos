from locust import HttpUser, task, between
import json
import random

with open('data/names.json') as names_poke:
    names = json.load(names_poke)

class WebsiteUser(HttpUser):
    wait_time = between(1, 3)

    @task(3)
    def load_posts(self):
        poke = random.choice(names)
        print(f"{poke['name']}")
        self.client.get(f"/pokemon/{poke['name']}")


#locust -f pokeapi.py --host https://pokeapi.co/api/v2 --headless -t 10s -u 3 -r 2 --html report.html
