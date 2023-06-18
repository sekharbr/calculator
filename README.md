Calcultor API
=============

Calculator API used for simple arithmetic operations.
<br />
The API support following arithmetic operations.
- ADD
- SUBTRACT
- MULTIPLY
- DIVIDE

API HAS basic validations for null check and check parameters validation based on the operations.
<br />
API is developed using Kotlin and you need Java 17 JDK for running the application.
<br />
<br />
API is deployed in AWS lambda and API is exposed through AWS API gateway.
<br />
## Infrastructure
API is deployed in AWS using AWS CDK.
<br />
Infrastructure code is available in infra-as-code directory in root path.
AWS resources are programmed in Typescript.
<br />
To build infra-as-code you need,
- AWS CLI installed
- AWS CDK installed using NPM
- Typescript installed globally.

## Build and Deploy
Inorder to build and deploy the application please follow below steps.
```
How you have all above mentioned requirements available locally.
Root Dir (Calculator)
mvn clean package
cd infra-as-code
cdk deploy
```

## API Documentation
Please use below swagger documentation to test the API through swagger file.
```yml
---
swagger: "2.0"
info:
  description: "Calculator API used for simple arithmetic orperations."
  version: "2023-06-17T15:25:31Z"
  title: "Calculator API"
  contact:
    name: "Sekhar B R"
    email: "sekharbr.sbr@gmail.com"
host: "z77c46omtg.execute-api.eu-west-1.amazonaws.com"
basePath: "/prod"
schemes:
- "https"
paths:
  /calculator:
    post:
      summary: Creates a user.
      description: "Calculator performs addition, subtraction, multiplication and\
        \ division between two numbers"
      parameters:
        - in: body
          name: Calculator
          description: Perform simple arithmetic operations.
          schema:
            type: object
            properties:
              first_number:
                type: number
              second_number:
                type: number
              operator:
                type: string
                description: Type of arithmetic operations. API Support ADD, SUBTRACT, MULTIPLY, DIVIDE.
      responses:
        '200':
          description: A JSON object containing result
          schema:
            type: object
            properties:
              result:
                type: number
                description: Result of the arithmetic operation. The property will be null if the API gives error message.
              error:
                type: string
                description: Error message from the API is shown here, it will be null if the operation is sucessfull

```