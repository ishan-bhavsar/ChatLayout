# Chat Layout
[![](https://jitpack.io/v/stellaquila/ChatLayout.svg)](https://jitpack.io/#stellaquila/ChatLayout)  
Chat Layout is a custom layout like LinearLayout which enables layout shape like WhatsApp, Iphone message.
# Requirements
The library requires Android API Level 14+.
# Demo
Watch a short Demo Video on YouTube, Sample APK can be found in Releases section.
# Screenshots
![alt text](https://github.com/stellaquila/ChatLayout/blob/master/Screenshots/Screenshot_WhatsApp.PNG) 
![alt text](https://github.com/stellaquila/ChatLayout/blob/master/Screenshots/Screenshot_IPhone.PNG)
# Usage
## Add following Gradle dependencies.
**Step.1 : add JitPack to root.Gradle file**  
![alt text](https://github.com/stellaquila/ChatLayout/blob/master/Screenshots/rootGradle.png)  
**Step.2 : add library dependency to app.Gradle file**  
![alt text](https://github.com/stellaquila/ChatLayout/blob/master/Screenshots/appGradle.png)  

## Here is the Example of XML file
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context="com.stellaquila.chatlayout.example.ChatLayoutExample">

    <com.stellaquila.ChatLayout xmlns:chat="http://schemas.android.com/apk/res/com.stellaquila.chatlayout.example"
        android:id="@+id/chat_layout"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        chat:corner_radius="10dp"
        chat:layout_foreground="@android:color/darker_gray"
        chat:margin="5dp"
        chat:padding="5dp"
        chat:sender="false"
        chat:triangle_angle="45"
        chat:triangle_corner_radius="1dp"
        chat:triangle_length="10dp"
        chat:style="WhatsApp">

        <ImageView
            android:layout_width="50dp"
            android:layout_height="50dp"
            android:background="@color/colorPrimaryDark"
            android:src="@mipmap/ic_launcher" />

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Hello" />

    </com.stellaquila.ChatLayout>

    <Button
        android:id="@+id/togglebutton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Toggle: Receiver" />

    <Button
        android:id="@+id/stylebutton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Style: WhatsApp" />

</LinearLayout>
 
  For more usage examples check the sample project.

# License
					    MIT License
			    Copyright (c) 2018 Ishan Bhavsar
	  Permission is hereby granted, free of charge, to any person obtaining a copy
	  of this software and associated documentation files (the "Software"), to deal
	  in the Software without restriction, including without limitation the rights
	  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	  copies of the Software, and to permit persons to whom the Software is
	  furnished to do so, subject to the following conditions:
	  The above copyright notice and this permission notice shall be included in all
	  copies or substantial portions of the Software.
	  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	  SOFTWARE.
