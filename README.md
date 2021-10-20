# Example of unexpected behavior with Talaiot+Conf cache

Repository with simple example of unexpected behavior of Talaiot plugin with enabled Gradle's configuration cache.

If you make the first launch of app through green arrow in IDE, or `/.gradlew :app:assembleDebug`, `Talaiot` plugin 
will send all necessary metrics + configuration cache entry will be saved. 

If you make the second launch of app - your Gradle project will use saved configuration cache entry and `Talaiot` will NOT send metrics. 

This behavior is unexpected =)