# PDF to Excel Converter API

This is a Spring Boot application that provides a RESTful API to convert a PDF file into an Excel file and extract bank transaction data from the Excel file. The application can be easily deployed using Docker.

## Features

- **PDF to Excel Conversion**: Accepts a PDF file via a POST request and converts it into an Excel file.
- **Bank Transaction Extraction**: Parses the Excel file to extract bank transaction data and returns it as a response.

## Prerequisites

- Java 17
- Docker
- Maven

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/yourusername/pdf2excel.git
cd pdf2excel
```
## Build and Run the Application
Using Maven
```
mvn clean package
java -jar target/pdf2excel-0.0.1-SNAPSHOT.jar
```
## Using Docker
1. Build the Docker Image:
```
docker build -t pdf2excel 
```
2. Run the Docker Container:
```
docker run -p 8080:8080 pdf2excel
```
## API Usage
* POST /api/convert
* Description: Convert a PDF file to an Excel file and extract bank transactions.
* Request: Multipart form data with a key file containing the PDF.
* Response: JSON containing extracted bank transactions

## Docker Hub
You can also pull the Docker image from Docker Hub and run it:
```
docker pull yourdockerhubusername/pdf2excel
docker run -p 8080:8080 yourdockerhubusername/pdf2excel
```
