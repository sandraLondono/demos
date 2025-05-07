from locust import LoadTestShape

class PeakLoadShape(LoadTestShape):
    stages = [
        {"duration": 6, "users": 5, "spawn_rate": 2},
        {"duration": 12, "users": 10, "spawn_rate": 2},
    ]

class StepLoadShape(LoadTestShape):
    stages = [
        {"duration": 30, "users": 50, "spawn_rate": 5},
        {"duration": 60, "users": 200, "spawn_rate": 10},
    ]

LOAD_SHAPES = {
    "peak": PeakLoadShape,
    "step": StepLoadShape,
}