# ListVideoManager
video player in ListView or RecyclerView

![image](https://github.com/McoyJiang/ListVideoManager/raw/master/IMAGEs/listvideo.gif)


This is library to solve Single MediaPlayer in ListView or RecyclerView.
Using this lib, it is easy to play video online. Besides, you can ues McoyVideoView in ListView's item layout.
When one item clicked, the corresponding video will play. 

Advantages:

a) It can be used just like an usual UI widget. 

b) It can also be used in ListView's item layout.

How to use:

1 in Project's build.gradle, add lines 

allprojects {

    repositories {
    
        jcenter()
        
        maven { url "https://jitpack.io" }
        
        maven { url "https://oss.sonatype.org/content/repositories/snapshots" }
        
    }
    
}


2 in app module's build.gradle, add this line

    compile 'com.github.McoyJiang:ListVideoManager:v1.0'
    

3 in xml layout, using McoyVideoView tab declare this UI widget

<com.mcoy_jiang.videomanager.ui.McoyVideoView
        android:id="@+id/videoView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
        

4 in Activity, find this widget and set video url

McoyVideoView videoView = ((McoyVideoView) findViewById(R.id.videoViewTest));

videoView.setVideoUrl("this is the video url");


5 when McoyVideoView is clicked on device, the online video will play automatically whith MediaController shown!!
