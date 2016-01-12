# GiphyAppApi 

## About
GiphyAppApi was develop experiment with Giphy's API :) 
    #Current features, on app start I load the latest trending giphys
    #Users also have the ability to search for giphys and loads them dynamically as the user types using the search endpoint.
    #Both calls are wrapped with RXJava(I don't have much experience with RX but I thought it would be worth the try')
    #Also focused on the UI element transitions to make the app somewhat pleasant to use, the colors might not be very attractive but thats is what I went with. 

#NOTE: I am assuming the user has close to perfect connectivity in the device being used. This app is available to android version 5.0 and above. 

#### Included libraries

* [Retrofit 2.0 Beta - type-safe REST client](https://github.com/square/retrofit)
* [RXAndroid - reactive extensions for Android](https://github.com/ReactiveX/RxAndroid)
* [butterknife](http://jakewharton.github.io/butterknife/)
* [Picasso for image loading] (http://square.github.io/picasso/)

## Known Bugs
	* Need to add Error handler for RX
	* Random crash when the user has low internet connectivity
	* Sometimes the list shows duplicates, not sure exactly how to reproduce it but it probably has to do with making a api call before finishing the search. 

### What's Next?
	*Looking forward to adding some espresso testing to test the UI part of it
	*Re-work the way adapter

