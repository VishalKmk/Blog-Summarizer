# Blog Service with AI Integration

This is a Spring Boot application that provides a RESTful API for managing blogs with AI-powered summarization.

## Features
- Create, read, update, and delete blogs
- Pagination support for fetching blogs
- AI-powered blog summarization using OpenAI API
- PostgreSQL database for persistence
- Dockerized application

## Technology Stack
- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- PostgreSQL
- OpenAI API
- Docker

## API Endpoints

### Blog Operations
- `POST /api/blogs` - Create a new blog
- `GET /api/blogs` - Get all blogs with pagination
- `GET /api/blogs/{id}` - Get a blog by ID
- `PUT /api/blogs/{id}` - Update a blog
- `DELETE /api/blogs/{id}` - Delete a blog

### AI Operations
- `POST /api/blogs/summary` - Generate a summary for a blog

## Setup Instructions

### Prerequisites
- Java 17
- Maven
- PostgreSQL
- Docker (for containerization)
- OpenAI API key

### Local Development
1. Clone the repository
2. Create a PostgreSQL database named `blogdb`
3. Update `application.properties` with your database credentials and OpenAI API key
4. Run `mvn spring-boot:run` to start the application

### Docker Deployment
1. Build the Docker image:
   ```
   docker build -t blog-service .
   ```
2. Run the container:
   ```
   docker run -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:postgresql://<db-host>:5432/blogdb -e SPRING_DATASOURCE_USERNAME=<username> -e SPRING_DATASOURCE_PASSWORD=<password> -e OPENAI_API_KEY=<your-api-key> blog-service
   ```

### AWS Deployment
1. Push the Docker image to Amazon ECR
2. Launch an EC2 instance with Docker installed
3. Pull the image and run it with the appropriate environment variables

## API Examples

### Create a Blog
```
POST /api/blogs
{
  "title": "Spring Boot Introduction",
  "content": "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications...",
  "author": "John Doe"
}
```

### Generate a Summary
```
POST /api/blogs/summary
{
  "blogId": 1
}
```
