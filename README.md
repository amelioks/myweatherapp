MYWEATHERAPP

myweatherapp is  an example application built to demonstrate core Android Development skills as presented in the Udacity Android Developers Kotlin curriculum.

This app demonstrates the following views and techniques:
1. Retrofit to make api calls to an HTTP web service.
2. Glide to load and cache images by URL.
3. Shared Preference for local storage.
4. Google map and Location Tracking

It leverages the following components from the Jetpack library:
1. ViewModel
2. LiveData
3. Navigation with the SafeArgs plugin for parameter passing between fragments
4. ViewBinding

myweatherapp consists of 3 fragments:
1. Main Fragment shows the weather of default city. In Main Fragment, this fragment ables the user to change location with entering the name of the city by clicking change location.
Also user able to change location by enabling the location tracker permission by clicking use my location.
2. Weather Location Fragment is a fragment to search and change the city
3. Weather Detail Fragment is a fragment that give you detail information of the weather. User able to navigate into this fragment after click weather list information from the Main Fragment.

