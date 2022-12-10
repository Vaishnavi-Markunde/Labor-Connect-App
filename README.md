# Labor-Connect-App

- Often, we face situations where we require a service provider like a plumber, carpenter, etc. on an urgent basis, but we are unable to find a quality worker or don't have enough time to contact people and ask them for recommendations. This problem is faced by majority of the people. When someone needs aid with small but major household tasks, the trouble arises when service skilled persons are unavailable or the trusted providers are difficult to find, who deliver consistently flawless service on instance. 
- Although we can find contacts of workers, we do not know if they will be available or not, and if they will answer or not. It is also a tedious task for workers to find a job and very often they must search for work or have to sit jobless for a while. 
- This application for household services provides the most expedient and free way to get your domestic work done just with a single click. It will connect customers and workers and will solve both the problems mentioned above. Customers can now simply click on a button to request for a service, and workers will be flooded with work requests.


<br>
<br>

## Workflow of the Application 

The user would have to first register themselves either as a Customer or Worker 


<img src="/Output/Registration/registration.png"  height="600">  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;  <img src="/Output/Registration/login.png"  height="600">

<br>
Once the user register, home page would be rendered based on the user type:

<br>

## Customer Application Page:

<img src="/Output/User/homepage.png"  height="550">  &nbsp; &nbsp;  <img src="/Output/User/work_description_filled.png"  height="550"> &nbsp; &nbsp;
<img src="/Output/User/address_filled.png"  height="550"> 
<br>
<img src="/Output/User/timespan.png"  height="550"> &nbsp; &nbsp; <img src="/Output/User/confirmation.png"  height="550">  &nbsp; &nbsp; <img src="/Output/User/accepted_notification.png"  height="550">


<br>
<br>

<b> Home page displays all the available services and the customers would select a request as per their need , enter title and description for the work they need and address and finally the time preferences if any . The address will be saved to the database once a request is made, for the next requests the user can either go with the same address or edit it. This requests are stored in google firebase database and a notifications are sent to the respective workers in that domain. Once the worker accepts the requests , the customer will be notified. <b>

<br>

Customer can view their profile, check previous accepted requests and current pending requests. Customer can even delete the requests from Pending requests page.

<img src="/Output/User/user_profile.png"  height="550"> &nbsp; &nbsp;
<img src="/Output/User/completed_requests.png"  height="550"> &nbsp; &nbsp;
<img src="/Output/User/pending_requests.png"  height="550">


<br>
<br>

## Worker Application Page

Workers would see requests as per their profession and location. Worker can even update his location as per his current residency from the profiles page and service reuests in that city would be visible. 
<br>

<img src="/Output/Worker/worker_homepage.png"  height="550"> &nbsp; &nbsp;
<img src="/Output/Worker/viewed_requests.png"  height="550"> &nbsp; &nbsp;
<img src="/Output/Worker/updateLocation.png"  height="550">

Whenever a user requests for a service , a notification is sent to the worker

<img src="/Output/Worker/newRequest_notification.png"  height="550"> &nbsp; &nbsp;


<br>
<br>

# Steps to run this project on your machine
- Clone the repo and open LaborConnect in Android Studio
- Create a project on google firebase and replace the google-services.json file with your firebase project's json file.
- Add the FCM Server Key in notifications/FcmNotificationSender.java file
