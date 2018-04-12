Department of Computer Science

The College at Brockport

CSC 429: Object-Oriented Software Development

Quick Reference on "Impresario" – the Observer Pattern Framework

1. Consider a screen with the following fields:
        
        Banner Id (of a borrower)
        First Name
        Last Name
        Phone Number
        E-mail
        Check-box indicating whether the borrower is blocked/unblocked from borrowing

1. Desired behavior: 
    
    Whenever a human user enters the banner id into the relevant text field, and either tabs out of the screen, or clicks on some other part of the screen (i.e., the field LOSES FOCUS), or hits the ENTER key after typing in the banner id, the next thing the user sees is that the screen's fields get automatically populated with the relevant information corresponding to that banner id – namely, the first and last name, phone number and e-mail, and the check-box mentioned above.

1. How is this behavior achieved by maintaining low coupling among the view (i.e., the GUI screen) and the model/controller (i.e., the Java object that executes the business logic corresponding to the processing of the banner id entered)? Answer: Use Impresario – the Observer framework.
Steps to using the framework:

    1. In the relevant method ("processAction(EventObject evt)") of the view class (which extends JPanel, actually extends View, which extends JPanel), we have the following code:
        
        ```
        //-------------------------------------------------------------
        public void** processAction(EventObject evt) {
            …
            if (evt.getSource() == accountHolderID) {
                //
                myModel.stateChangeRequest("BannerID", bannerId.getText());
            }
        }
        ```
        This way, the view does not call an object of a specific model/controller class, but calls an object that is only required to implement the "IModel" interface from the observer framework. You can create the view object with any class that implements the "IModel" interface (see the way in which the constructors of the view classes are written).
        
        As an initiator of a user event, in the "processAction()" method, the view should just invoke the model/controller as shown above and terminate. It should not do anything else. In other words, there should be no code in the "processAction()" method following the invocation of "stateChangeRequest()". Once you have sent the request, you become an observer and just wait to be called back by the model/ controller object.

    2. The model/controller object has the following dependencies among its keys (look at the "setDependencies()" method of classes implementing the "IModel" interface):
        ```
        //---------------------------------------------------------------------
        private void setDependencies() {
            dependencies.setProperty("BannerId", "FirstName,LastName,PhoneNumber,Email,Blocked");
            // NOTE: There should be no blanks between the dependent keys
        }
        ```

        This dependency implies that when the model/controller processes the information it received with the "BannerId" key, after it is done processing, it will call back all the observers interested in (i.e., all the observers who have subscribed to) the keys "FirstName", "LastName", "PhoneNumber", "Email" and "Blocked".

    3. In theory (and in concurrent systems), there could be multiple observers (e.g., multiple views subscribing to each of the above dependent keys. In our systems, however, typically a single view will subscribe to all these keys. Where does it do that? In our view class being discussed above, for example, its constructor will have the following code (typically, towards the end of the constructor's code):
        ```
        …
        
        myModel.subscribe("FirstName", this);
        
        myModel.subscribe("LastName", this);
        
        myModel.subscribe("PhoneNumber", this);
        
        myModel.subscribe("Email", this);
        
        myModel.subscribe("Blocked", this);
        ```
        In other words, our view class is interested in ALL these keys, and expects to be called back with information for each one of these keys.

    1. So, going back to (a), we realize that the model/controller implements the business logic to process the "BannerId" key in the "stateChangeRequest()" method, right? What does the body of this method look like? Written in a naïve way (since we don't know how to use Java Reflection yet), it has a huge 'if'-statement that looks at each key it needs to process:
        ```
        //---------------------------------------------------------------------
        public void stateChangeRequest(String key, Object value){
            …
            if (key.equals(&quot;BannerId&quot;) == true) {
                borrower = new Borrower((String)value);
                // 'borrower' is declared as an instance variable in this
                // model/controller class
            } else {
                …
                // VERY IMPORTANT: DO NOT FORGET THIS STATEMENT AT THE END OF
                // EACH "stateChangeRequest()" METHOD
                myRegistry.updateSubscribers(key, this);
            }
        ```
        Now, in this method, the model/controller updated its internal state (i.e., its instance variable – 'borrower'), as it executed the 'relevant business logic'. At the end of the business logic, the invocation of the 'updateSubscribers()' method basically says that all observers subscribing to the dependent keys of the key that is being processed (i.e., 'BannerId') will be updated via callbacks.

    1. IMPORTANTLY – where is the information that is going to be used to update the observers interested in the dependent keys going to come from? Answer: From the internal state (instance variables) of the model/controller. That is why you have a "getState()" method in the model/controller. For example:
        ```
        //---------------------------------------------------------------------
        public Object getState(String key) {
            if (key.equals("FirstName") == true) {
                return borrower.getFirstName();
            } else if (key.equals("LastName") == true) {
                return borrower.getLastName();
            } else {
                ...
                return null;
            }
        }
        ```
        There could be many instance variables whose values (or values of fields inside these other variables) that are returned by this method. This method will actually be invoked by the 'updateSubscribers()' method. 'updateSubscribers()' actually goes thru all the dependent keys of the key in question ('BannerId') and looks up the data associated with each dependent key by calling 'getState()'. So, 'updateSubscribers()' looks up the first dependent key of 'BannerId' – namely, 'FirstName'. It then calls 'getState()' to get the data associated with 'FirstName'. Then, it looks at all observers (aka subscribers) interested in 'FirstName' and calls their "updateState()" method. This method must be implemented by all classes implementing the frameworks' "IView" interface.
        
        In our example, who is the subscriber interested in 'FirstName', 'LastName' and the other dependent keys? It is the original view class!

    1. The view class implements the 'updateState()' method:
        ```
        //---------------------------------------------------------
        public void updateState(String key, Object value)
        // 'value' is provided by the 'updateSubscribers()' method as discussed
        // in 'g'
        {
            if (key.equals("FirstName") == true) {
                firstName.setText((String)value);
            } else if (key.equals("LastName") == true) {
                lastName.setText((String)value);
            }
        
        …
        }
        ```
        
        
That's it! This achieves the behavior we desired in (2) above!
