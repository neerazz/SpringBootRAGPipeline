#!/bin/bash

# Build and run the RAG application with Docker Compose

if [ -z "$OPENAI_API_KEY" ]; then
    echo "Error: OPENAI_API_KEY environment variable is not set"
    echo "Please run: export OPENAI_API_KEY=your-api-key-here"
    exit 1
fi

echo "Building and starting RAG application..."
docker-compose up --build -d

echo "Waiting for services to start..."
sleep 10

echo "Checking application health..."
curl -s http://localhost:8080/api/health | jq .

echo ""
echo "RAG application is running!"
echo "- API: http://localhost:8080/api/ask?question=your-question"
echo "- Web UI: http://localhost"
echo ""
echo "To view logs: docker-compose logs -f"
echo "To stop: docker-compose down"
