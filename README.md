# Android-CustomKeyboard
Fully customizable Android keyboard written in Kotlin.

<img height="600px" width="400px" src="https://s3.amazonaws.com/don-brody-images/qwerty.gif"/>

## Why I Made It
This whole process started when I needed to give users the ability to enter a latitude and longitude into two separate fields. 
Originally I thought that Google must provide a way to easily override their keyboard layout and touch events, but unfortunatley 
that's not the case. The Android system keyboard API is limited and difficult to work with. My only two options were override their
system keyboard, or to create my own component (and disable theirs). I spent many hours researching different ways to approach my
problem, and ended up piecing together a few different approaches and adding some of my own flavor to it. I hope that I can save
somebody else a lot of time and headache.

## Intended Use
This keyboard is intended to be configured to fit your own needs. I chose an architecture that makes it very easy to separate the 
keyboard layouts and controllers, and provided some demos to get you started. You'll likely want to improve the UI for a production 
app, but the functionality is pretty solid.

## Prerequisites
Just make sure you have a version of Android Studio installed that supports Kotlin (3+ should be fine).

## Running the Demo
Just download the project, open it in Android Studio, connect to a virtual or physical device, and run it! There shouldn't be
any further configuration required (unless you need to download build tools, etc., but Android Studio should prompt you to do that).

## Architecture
I'll be writing a Medium article soon about this project. I'll post the link when I'm done. Until then just play around with the app
and look through the code. It shouldn't be too difficult to see what's going on.

## License
This project is licensed under the MIT License - see the [LICENSE](app/LICENSE) file for details
