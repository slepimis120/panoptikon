# Panoptikon - Spring Boot + Jenkins + GitHub Actions project

![Jenkins](https://img.shields.io/badge/jenkins-%232C5263.svg?style=for-the-badge&logo=jenkins&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
***
Dummy Spring Boot project with focus on Jenkins pipelines. The Jenkins instance is configured to build the application on every push. If a branch does not pass all tests, it cannot be merged with the `develop` branch.

## Prerequisites

1. **Fill in the following environment variables in the `.env` file based on the .example.env file:**
   - `GITHUB_CLIENT_ID` - Github App Client ID
   - `GITHUB_CLIENT_SECRET` - Github App Client Secret
   - `JENKINS_USERNAME` - GitHub username
   - `JENKINS_PASSWORD` - GitHub personal access token
   - `NGROK_AUTH` - ngrok authentication token

2. **Generate SSH keys:**
    ```sh
    ssh-keygen -t ed25519 -f jenkins_deploy_key -C "jenkins@docker"
    ```

3. **Add the public key to your Github repository.**

4. **Start the Docker instance:**
   ```sh
   docker-compose up --build
   ```

5. **Find the ngrok link on the ngrok dashboard.**

6. **Configure the GitHub application to use the correct ngrok link.**

7. **Configure the GitHub repository to use the correct ngrok link.**

## Spring Boot Application
The Spring Boot application is a simple CRUD application with minimal tests. The focus of the application is to demonstrate the Jenkins to GitHub Actions conversion.

## Jenkins Configuration
The Jenkins instance is configured to:
- Build the Spring Boot application on every push.
- Prevent merging branches that do not pass all tests with the develop branch
- Build and test all pull requests and branches, indicating whether they pass or fail.
