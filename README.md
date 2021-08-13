# AndroidPharmacyApp utilising MySQL as backend DB Service

## Brief Description of App
Our app was named “Happy Pills Pharmacy”. It essentially is an online pharmaceutical shop aimed at 3 different users: Admin, Doctor and Customer/Patient. All these users are given the ability to login.
Obviously the app also allow a customer to sign up if they have not signed up before and select their doctor from the available doctors. If their doctor is not in the list then they can select “No Doctor” and then no prescriptions can be made for them. However, its main function for the customer is to allow the customer to add pharmaceuticals and their quantities to a cart so an order can be created. The customer also has the ability to edit their personal information and view any details from their current and past orders.
Its main function for the doctor is to allow the doctor to make prescriptions for their patients. The doctor can only make prescriptions for a patient once they verify that the patient is actually a patient of theirs. These prescriptions will then be auto-loaded onto that patient’s cart the next time they log in. The doctor can also edit their own personal information.
Its main function for the admin is to allow the admin to add new pharmaceuticals to the database for purchase and to add new doctor profiles to the system. Also the admin is available to see the details of all orders ever created.

## Business Rules
*	Each Customer/Patient can be uniquely identified by their Customer ID.
*	Each Doctor can be uniquely identified by their Doctor ID.
*	Each Admin can be uniquely identified by their Admin ID.
*	Each Pharmaceutical can be uniquely identified by their Pharmaceutical ID.
*	Each Order can be uniquely identified by an auto-incrementing Order Number.
*	Each Prescription can be uniquely identified by an auto-incrementing Prescription Number.
*	Each Customer/Patient can have zero or one selected Doctor.
*	Each Doctor can be selected as a doctor by zero or many Customers/Patients.
*	Each Customer/Patient can be either verified or not by the Doctor they selected.
*	A Prescription cannot be prescribed by a Doctor to a Customer/Patient unless that Customer/Patient has been verified. 
*	Each Customer/Patient can create zero or many Orders.
*	Each Order can be made by only one Customer/Patient.
*	Each Prescription can be prescribed to one and only one Customer/Patient.
*	Each Customer/Patient can have zero or many Prescriptions.
*	A Prescription for a Customer/Patient will be auto added to their cart if enough stock of the Pharmaceuticals prescribed exist. Also a Prescription for a Customer/Patient will be auto added to their cart if the number of Repeats Left is greater than 1.
*	The Date Completed for an Order may not be before the Date Ordered of that same Order.
*	A Pharmaceutical may only be available for purchase if its Quantity is not zero.
