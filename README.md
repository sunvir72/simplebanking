# Simple Banking

## How to build and run the application

Clone the Repository

```bash
git clone https://github.com/sunvir72/simplebanking.git
```
 

Build the project

```bash
mvn clean install
```

Run the SimpleBank.java file. The application will run on http://localhost:8080

**API List**

- /accounts/create
  - Create a new account
  - Input (POST):
    - (Mandatory) accountId
    - (Optional) balance
- /transactions/transfer_funds 
  - Transfer funds from one account to another
  - Input (POST):
    - (Mandatory) senderAccountId, receiverAccountId, amount
- /transactions/history
  - View transactions history
  - Input (POST):
      - (Mandatory) accountId

**Assumptions for APIs**
- If balance amount is not provided while crating an account, one will be created with 0 units of balance
- Transfer funds amount must be at least 1 unit.
- Funds cannot be transferred from an account to itself
- Account balance cannot go negative
