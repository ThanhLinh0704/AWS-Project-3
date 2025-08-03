# 📁 AWS-Project-3  
**Upload and Manage Files using S3 and DynamoDB**

## 📌 Project Description

This project is a **web application** deployed on an **Amazon EC2 instance** (in a private subnet) and accessed through an **Application Load Balancer (ALB)**.  
It allows users to:

- Upload files to Amazon S3.
- Store file metadata in Amazon DynamoDB.
- Retrieve and display file names stored in DynamoDB.
- View content of files stored in S3.
- *(Optional)* Publish messages to Amazon SNS when a file is uploaded.

### 🧩 AWS Services Used:
- **Amazon EC2** (in private subnet)
- **Amazon S3** for file storage
- **Amazon DynamoDB** for metadata
- **Amazon SNS** *(optional)* for notifications
- **Application Load Balancer (ALB)** for routing
- **IAM Roles** for secure access between EC2 and AWS services

## 🚀 Technologies Used

- **Backend**: Java, Spring Boot, Spring Web, AWS SDK for Java (S3 & DynamoDB)
- **Frontend**: HTML, CSS (Semantic UI), JavaScript
- **Database**: Amazon DynamoDB
- **Storage**: Amazon S3
- **Build Tool**: Maven
  
# Setting Up AWS Credentials (Local Testing)
- Install AWS CLI
- Run the following command in your terminal: aws configure
- AWS Access Key ID     [None]: <your-access-key-id>
- AWS Secret Access Key [None]: <your-secret-access-key>
- Default region name    [None]: ap-southeast-1
  
# Start the server
./mvnw spring-boot:run
# System Architecture Diagram
<img width="681" height="287" alt="image_720(1)" src="https://github.com/user-attachments/assets/17a16fb5-070e-4343-bdc3-aaabcd5b99f3" />


