Example project for running JavaFX in an OSGi container. This allows for updating the UI without restarting the application. 

JavaFX unfortunatly doesn't work entirely correctly in OSGi. To start a JavaFX application the _launch_ method must be used. This method may only called once, which make it impossible to restart the bundle that execute this code. This is never correct in OSGi, but by making sure this bundle is not doing anything else the practical problem is limited.

The javafx.launcher bundle starts the JavaFX application and makes the primary stage available as a service. Another bundle can depend on this service and create the actual UI. When that bundle is restarted, it can recreate the UI as well.