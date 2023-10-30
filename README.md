<p align="center">
   <img align="center" src="./logo.svg" height="250px">
</p>
<h1 align="center">
   IPA-V-MQ
</h1>
<p align="center">
   Yannic Studer
</p>


## Introduction

IPA-V-MQ is a component of the IPA-V project responsible for generating simulated transactions and sending them to the IPA-V-Backend using RabbitMQ. This README provides instructions on how to set up and use IPA-V-MQ in your development environment.

## Prerequisites

Before you can use IPA-V-MQ, ensure that you have the following prerequisites installed:

1. **Java**: IPA-V-MQ is a Java application, so you need to have Java installed. You can download it from [AdoptOpenJDK](https://adoptopenjdk.net/) or your preferred Java distribution.

2. **Maven**: IPA-V-MQ uses Maven as a build tool and dependency management system. Make sure you have Maven installed. You can download it from the [official Maven website](https://maven.apache.org/download.cgi).

3. **IntelliJ IDEA**: You can use IntelliJ IDEA as your Integrated Development Environment (IDE) to work with the project. If you don't have it installed, download it from the [JetBrains website](https://www.jetbrains.com/idea/download/).

4. **Docker**: To set up RabbitMQ for local development, you need Docker. Install Docker from [Docker's official website](https://docs.docker.com/get-docker/).

## Setting Up RabbitMQ

IPA-V-MQ communicates with the IPA-V-Backend via RabbitMQ. To set up RabbitMQ, follow these steps:

1. **Run RabbitMQ with Docker**: Open your terminal and run the following Docker command to start a RabbitMQ container with the management plugin for monitoring:

   ```bash
   docker run -it --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management
   ```

    - The `-it` flag runs the container interactively.
    - `--rm` removes the container when you stop it.
    - `--name rabbitmq` names the container "rabbitmq".
    - `-p 5672:5672` maps the default RabbitMQ port.
    - `-p 15672:15672` maps the RabbitMQ management plugin port for web monitoring.

2. **Access RabbitMQ Management Console**: Once RabbitMQ is running, open your web browser and go to [http://localhost:15672/](http://localhost:15672/).

    - **Username**: guest
    - **Password**: guest

   You will have access to the RabbitMQ management console for monitoring.

## Using IPA-V-MQ

To use IPA-V-MQ in your development environment, follow these steps:

1. **Clone the Repository**: Open your terminal and run the following command to clone the IPA-V-MQ repository:

   ```bash
   git clone https://github.com/your-username/IPA-V-MQ.git
   cd IPA-V-MQ
   ```

2. **Import the Project in IntelliJ IDEA**:
    - Open IntelliJ IDEA.
    - Click on `File` > `Open` and select the IPA-V-MQ directory.
    - IntelliJ IDEA will detect the project's structure and open it.

3. **Build and Run the Application**:
    - Inside IntelliJ IDEA, open the `src/main/java/com.example.IPAMQ` directory.
    - Find the `IpaVmqApplication.java` file and right-click it.
    - Select "Run 'IpaVmqApplication'."

4. **Customize the Simulated Transactions**: IPA-V-MQ is pre-configured to generate simulated transactions and send them to the IPA-V-Backend. You can customize the code in your development environment to simulate transactions according to your requirements.

5. **Monitor RabbitMQ**: Use the RabbitMQ management console at [http://localhost:15672/](http://localhost:15672/) to monitor the RabbitMQ queues and exchanges. This will help you track the messages sent from IPA-V-MQ to the IPA-V-Backend.

That's it! You now have IPA-V-MQ up and running, generating simulated transactions and sending them to the IPA-V-Backend via RabbitMQ.

## Repository Links

- [IPA-V-Backend](https://github.com/FireNick44/IPA-V-Backend): The backend part of the IPA-V application.
- [IPA-V-Frontend](https://github.com/FireNick44/IPA-V-Frontend): The frontend part of the IPA-V application.
- [IPA-V-MQ](https://github.com/FireNick44/IPA-V-MQ): The message queuing component for IPA-V.

### Contributors

 - [Yannic Studer](https://github.com/FireNick44)


## License
Project is [MIT licensed](./LICENSE)