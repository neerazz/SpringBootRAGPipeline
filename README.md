# Spring Boot RAG Pipeline Demo

This project demonstrates building a Retrieval-Augmented Generation (RAG) pipeline using Spring Boot and Spring AI.

## Prerequisites
- Java 17 or higher
- Maven 3.6+
- PostgreSQL (if using PGVector)
- OpenAI API Key

## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/spring-rag-demo.git
cd spring-rag-demo
```

### 2. Set Environment Variables
```bash
export OPENAI_API_KEY=your-api-key-here
```

### 3. Set Up Database (if using PGVector)
```bash
# Create database
createdb ragdemo

# Install PGVector extension
psql -d ragdemo -c "CREATE EXTENSION vector;"
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

## Using the API

### Ask a Question
```bash
curl "http://localhost:8080/api/ask?question=What%20is%20our%20password%20policy?"
```

### Health Check
```bash
curl http://localhost:8080/api/health
```

## Project Structure
- `src/main/resources/docs/` - Place your markdown documents here
- `src/main/resources/prompts/` - Prompt templates
- The vector store is populated on startup with all `.md` files

## Customization

### Adding New Documents
Simply add `.md` files to the `src/main/resources/docs/` directory and restart the application.

### Changing the Vector Store
The project uses SimpleVectorStore by default. To use PGVector:
1. Ensure PostgreSQL is running with the vector extension
2. Update application.properties with your database credentials
3. The configuration will automatically use PGVector

### Modifying the Prompt
Edit `src/main/resources/prompts/qa-prompt.st` to customize how the AI responds.

## Common Issues

### Out of Memory
Increase heap size: `java -Xmx2g -jar target/rag-demo-0.0.1-SNAPSHOT.jar`

### Slow Responses
- Reduce the number of documents returned (`withTopK` parameter)
- Use a smaller context window
- Consider upgrading to GPT-3.5-turbo-16k for larger contexts

### Vector Store Not Persisting
SimpleVectorStore is in-memory. For persistence, use PGVector or another persistent store.

## Next Steps
- Add authentication/authorization
- Implement document upload endpoint
- Add conversation memory
- Create a simple web UI
- Add metrics and monitoring

## License
MIT
