


# UC3: Authenticate User

**Primary actor:** User

**Stakeholders:**

-   User: Needs to ensure he is the only one having access to his account.
    

  

**Special requirements:**

-   The password needs to be encrypted before being sent to the System.
    

  

## Basic flow:

1.  Registration
    

1.  The user provides his personal details.
    
2.  The user provides his login credentials (username, password).
    
3.  The user is automatically logged in.
    

  

## Alternative flows:

2.1 The username is already in use by a different user.

1.  The user is asked to submit a different username.
    

2b. The password is too short or too long.

1.  The user is asked to submit a different password.

2. Authentication

3. The user is asked to submit his login credentials

4.  The user hasn’t registered to the application

5. The user is asked to register a new account

6.  The user is granted access to the application if the credentials are correct.
