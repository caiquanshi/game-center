# URL TO CLONE: https://markus.teach.cs.toronto.edu/git/csc207-2018-09-reg/group_0631
1: Answer "Yes" to whether you want to create a new Android Studio project.
2. Select "Import project from external model" and click Next
3. Click Next as many time as needed until the setup is done.
4. if you see message "Add ... to Git", please choose "NO"
5. if you see message "Unregistered VS root detected", you should choose "Add root"

# ADK Set Up:
1. Download And Install Android Studio
2. On top of interface,Click Tools and AVD Manager
3. Click Create Virtual Device Button at left bottom
4. Choose Pixel2 and click next, and choose Oreo with API Level as 27
5. If Oreo is not download, click download, accept the Agreement and wait for download
6. click finish and the AVD Manager will quit

# To Run the App:
1. Launch Android Studios and navigate to the GameCentre folder from the cloned registry.
2. Open GameCentre folder
3. Choose AVD as Pixel2
4. Run the app

# Note: May need to add implementation to java dependencies
1. Open Project Structures
2. Navigate to app:modules dependencies

# Should contain the following dependencies:
1. Implementation 'com.android.support:appcompat-v7:28.0.0'
2. Implementation 'com.android.support:constraint:constraint-layout:1.1.3'
3. Implementation 'com.android.support:design:28.0.0'
4. Implementation 'com.android.volley:volley:1.1.0'
