


# UC1: Manage job request

**Primary actor:** Poster

**Secondary actor:** Worker

**Stakeholders:**

-   Poster: Needs to have control over the job request’s progress
    
-   Worker: Needs to be assigned to a job request
    

**Requirements:**

-   Both parties have been authenticated by the system
    

  

## Basic flow:

1.  Creation of a job request
    

1.  The System checks if the poster can submit a new job request
    
2.  The poster fills out all the necessary information
    
3.  The job request is submitted to the System
    

  

## Alternative flows:

*: The poster user is deleted.

1.  The job request is canceled
    
2.  The worker is notified
    

#### 1a. The poster isn’t eligible to submit new job requests

1.  An error message is shown
    
2.  The request isn’t created
    

  

#### B.  Assignment of a worker
    

1.  The poster is notified someone has expressed interest in fulfilling one of his job requests
    
2.  The poster selects one of the workers that expressed interest in the job request
    

#### 2a) The worker user is deleted.

1.  The job request is canceled
    
2.  The poster is notified
    

3.  The selected worker is notified he was selected, by the System
    

  

#### C.  Management of the job request
    

1.  The poster can cancel the job request at any time, as long as it’s not completed
    
2.  The job request is marked as “canceled”

## Activity Diagram
![r2.activity_diagram.png](uml/requirements/r2.activity_diagram.png)
