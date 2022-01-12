# Add project specific ProGuard rules here.
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:widget
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-flattenpackagehierarchy
-allowaccessmodification
-keepattributes Exceptions,InnerClasses,EnclosingMethod,Signature,SourceFile,LineNumberTable
-dontskipnonpubliclibraryclassmembers
-ignorewarnings

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

#kotlin
-keep class kotlin.** { *; }
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**
-dontwarn kotlin.Unit
-keepclassmembers class **$WhenMappings {
    <fields>;
}

-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    public static void checkExpressionValueIsNotNull(java.lang.Object, java.lang.String);
    public static void checkFieldIsNotNull(java.lang.Object, java.lang.String);
    public static void checkFieldIsNotNull(java.lang.Object, java.lang.String, java.lang.String);
    public static void checkNotNull(java.lang.Object);
    public static void checkNotNull(java.lang.Object, java.lang.String);
    public static void checkNotNullExpressionValue(java.lang.Object, java.lang.String);
    public static void checkNotNullParameter(java.lang.Object, java.lang.String);
    public static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
    public static void checkReturnedValueIsNotNull(java.lang.Object, java.lang.String);
    public static void checkReturnedValueIsNotNull(java.lang.Object, java.lang.String, java.lang.String);
    public static void throwUninitializedPropertyAccessException(java.lang.String);
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-dontwarn com.google.android.material.**
-keep class com.google.android.material.** { *; }

-dontwarn com.google.android.gms.**
-keep class com.google.android.gms.** { *; }

-dontwarn com.google.firebase.**
-keep class com.google.firebase.** { *; }

-dontwarn androidx.**
-keep class androidx.** { *; }
-keep interface androidx.** { *; }
-keep class * extends androidx.fragment.app.Fragment { *; }
-keepnames class androidx.navigation.fragment.NavHostFragment
-keep class androidx.core.app.CoreComponentFactory { *; }

-dontwarn android.**
-keep class android.** { *; }
-keep interface android.** { *; }

-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }

-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

-keepclassmembers class * implements android.os.Parcelable { *; }
-keepclassmembers class * implements java.io.Serializable { *; }

-keep class **.R$* { *; }
-keepclassmembers enum * { *; }

-keep class sun.misc.Unsafe { *; }

-keep class com.franmontiel.persistentcookiejar.**

-dontwarn org.codehaus.mojo.animal_sniffer.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
-dontwarn java.lang.instrument.ClassFileTransformer
-dontwarn sun.misc.SignalHandler
-dontwarn com.franmontiel.persistentcookiejar.**

-keep class com.google.inject.** { *; }
-keep class javax.inject.** { *; }


-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep class androidx.security.crypto.** { *; }
-keep class * extends com.google.protobuf.GeneratedMessageLite { *; }
-keep class * extends com.google.crypto.tink.shaded.protobuf.GeneratedMessageLite { *; }

-keepclassmembers class * extends com.google.crypto.tink.shaded.protobuf.GeneratedMessageLite {
  <fields>;
}

-keep class com.crashlytics.** { *; }

-assumenosideeffects class android.util.Log{
    public static int v (...);
    public static int d (...);
    public static int i (...);
    public static int w (...);
    public static int e (...);
}

#dagger
-dontwarn dagger.internal.codegen.**
-keepclassmembers,allowobfuscation class * {
    @javax.inject.* *;
    @dagger.* *;
    <init>();
}
-keep class dagger.* { *; }
-keep class javax.inject.* { *; }
-keep class * extends dagger.internal.Binding
-keep class * extends dagger.internal.ModuleAdapter
-keep class * extends dagger.internal.StaticInjection

#Gson
-keepclassmembers public class com.google.gson.**
-keepclassmembers public class com.google.gson.** { public private protected *; }
-keepclassmembers public class com.project.mocha_patient.login.SignResponseData { private *; }
-keepclassmembers class sun.misc.Unsafe { *; }
-keep @interface com.google.gson.annotations.SerializedName
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
-keep class com.google.gson.stream.** { *; }
-keep class androidx.appcompat.widget.** { *; }

# project code
#-keep class com.honeybee.liargame.data.entity.** { *; }
