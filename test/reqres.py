from locust import HttpUser, task, between
from faker import Faker

faker = Faker('es_CO')

class Reqres(HttpUser):
    host='https://reqres.in'
    between(1, 2)

    @task
    def get_post(self):
        self.client.get('/api/users?page=2')

    @task
    def create_post(self):
        payload = {"name":faker.name_male(), "job": faker.job()}
        print(payload)
        headers = {"x-api-key": "reqres-free-v1"}
        with self.client.post("/api/users", json=payload, headers=headers, catch_response=True) as response:
            if response.status_code != 201:
                response.failure(f"❌ Código de estado incorrecto: {response.status_code}")
                return

            response_json = response.json()

            if response_json.get("name") != payload["name"]:
                response.failure(f"❌ El nombre no coincide: esperado {payload['name']}, recibido {response_json.get('name')}")
            else:
                response.success()
                print(f"✅ Usuario creado: {response_json}")
        



#locust -f reqres.py --host https://reqres.in --headless -t 10s -u 4 -r 4 --html report.html