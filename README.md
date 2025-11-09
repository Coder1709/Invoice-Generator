# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.7/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.7/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.7/reference/web/servlet.html)
* [PDF Document Reader](https://docs.spring.io/spring-ai/reference/api/etl-pipeline.html#_pdf_page)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.5.7/reference/using/devtools.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

## Requirement

- Provide a small, maintainable PDF generation feature for the PdfGenerator project.
- Accept input (HTML, Markdown or a simple JSON payload) and produce a PDF file via a Spring Boot service.
- Expose a REST endpoint to request PDF creation and return either a downloadable file or a URL to the generated PDF.
- Keep build and runtime dependencies manageable (Maven-managed), and document configuration and usage.

## What I did

- Added concise documentation describing the project requirement and the intended behavior of the PDF generator.
- Explained what components are expected (REST controller, service layer, PDF renderer, templates, and build dependencies).
- Outlined the implementation approach so a developer can implement or review the feature quickly.

## How I did it

High-level implementation steps you can follow:

1. Dependencies
   - Add a PDF rendering dependency to Maven (examples: openhtmltopdf, flying-saucer, or wkhtmltopdf wrapper). Update pom.xml with the chosen library and any required native components.

2. Service Design
   - Create a PdfService that:
     - Accepts input (HTML string or template name + model).
     - Uses the HTML-to-PDF renderer to produce a byte[] or file.
     - Stores the file temporarily or uploads to a storage location if needed.

3. Controller
   - Add a REST controller with endpoints such as:
     - POST /api/pdf/generate — accepts payload, returns application/pdf or a link.
     - GET /api/pdf/{id} — returns previously generated PDF.

4. Templates
   - Keep reusable HTML templates (Thymeleaf, FreeMarker) under src/main/resources/templates.
   - Sanitize input and ensure templates are safe to render.

5. Build & Runtime
   - Ensure Maven is configured to include any native binaries if required (or document local prerequisites).
   - Add tests covering the service and controller behavior.

6. Security & Validation
   - Validate input size and content to avoid resource exhaustion.
   - Apply authentication/authorization as appropriate for your API.

7. Documentation
   - Update HELP.md (this file) with usage examples and required setup steps.
   - Provide curl examples:
     - curl -X POST -H "Content-Type: application/json" --data '{"html":"<h1>Hello</h1>"}' http://localhost:8080/api/pdf/generate
# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.7/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.7/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.7/reference/web/servlet.html)
* [PDF Document Reader](https://docs.spring.io/spring-ai/reference/api/etl-pipeline.html#_pdf_page)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.5.7/reference/using/devtools.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

## Requirement

- Provide a small, maintainable PDF generation feature for the PdfGenerator project.
- Accept input (HTML, Markdown or a simple JSON payload) and produce a PDF file via a Spring Boot service.
- Expose a REST endpoint to request PDF creation and return either a downloadable file or a URL to the generated PDF.
- Keep build and runtime dependencies manageable (Maven-managed), and document configuration and usage.

## What I did

- Added concise documentation describing the project requirement and the intended behavior of the PDF generator.
- Explained what components are expected (REST controller, service layer, PDF renderer, templates, and build dependencies).
- Outlined the implementation approach so a developer can implement or review the feature quickly.

## How I did it

High-level implementation steps you can follow:

1. Dependencies
   - Add a PDF rendering dependency to Maven (examples: openhtmltopdf, flying-saucer, or wkhtmltopdf wrapper). Update pom.xml with the chosen library and any required native components.

2. Service Design
   - Create a PdfService that:
     - Accepts input (HTML string or template name + model).
     - Uses the HTML-to-PDF renderer to produce a byte[] or file.
     - Stores the file temporarily or uploads to a storage location if needed.

3. Controller
   - Add a REST controller with endpoints such as:
     - POST /api/pdf/generate — accepts payload, returns application/pdf or a link.
     - GET /api/pdf/{id} — returns previously generated PDF.

4. Templates
   - Keep reusable HTML templates (Thymeleaf, FreeMarker) under src/main/resources/templates.
   - Sanitize input and ensure templates are safe to render.

5. Build & Runtime
   - Ensure Maven is configured to include any native binaries if required (or document local prerequisites).
   - Add tests covering the service and controller behavior.

6. Security & Validation
   - Validate input size and content to avoid resource exhaustion.
   - Apply authentication/authorization as appropriate for your API.

7. Documentation
   - Update HELP.md (this file) with usage examples and required setup steps.
   - Provide curl examples:
     - curl -X POST -H "Content-Type: application/json" --data '{"html":"<h1>Hello</h1>"}' http://localhost:8080/api/pdf/generate
<img width="2000" height="1556" alt="image" src="https://github.com/user-attachments/assets/7db42ce5-0086-4eb3-bbd6-ac078e138ee2" />
<img width="752" height="1056" alt="image" src="https://github.com/user-attachments/assets/7d67b46f-1e14-4adb-a1c8-d92661613fad" />

