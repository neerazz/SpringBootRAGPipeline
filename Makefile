.PHONY: help build run stop clean logs test

help:
@echo "Available commands:"
@echo "  make build    - Build the application"
@echo "  make run      - Run with Docker Compose"
@echo "  make stop     - Stop all containers"
@echo "  make clean    - Clean up containers and volumes"
@echo "  make logs     - View application logs"
@echo "  make test     - Run tests"

build:
./mvnw clean package

run:
@if [ -z "$(OPENAI_API_KEY)" ]; then \
echo "Error: OPENAI_API_KEY not set"; \
exit 1; \
fi
docker-compose up --build -d

stop:
docker-compose down

clean:
docker-compose down -v
./mvnw clean

logs:
docker-compose logs -f app

test:
./mvnw test

# Development commands

dev:
./mvnw spring-boot:run

dev-db:
docker-compose up -d postgres
