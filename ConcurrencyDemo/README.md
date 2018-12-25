### **Concurrency demo**

**1.  Build the project 'class-loader-demo' :**

    cd concurrency-demo
    mvn clean install

**2.  Start application with default parameters**

    java -jar target/concurrency-demo-0.0.1-SNAPSHOT.jar

In this case following parameters will be used:

- numAccouns = 10         : accounts to create
- numTransactions = 100   : transactions to perform

**3.  Start application with parameters**

    java -jar target/concurrency-demo-0.0.1-SNAPSHOT.jar <numAccouns> <numTransactions>

- Example:

    java -jar target/concurrency-demo-0.0.1-SNAPSHOT.jar 5 20
    
**4.  Predefined parameters**

- All accounts are created with balance = 100.
- Maximum transaction amount = 100.