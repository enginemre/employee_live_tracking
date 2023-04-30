# Add project specific ProGuard ru
### Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature
# For using GSON @Expose annotation
-keepattributes *Annotation*
# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { <fields>; }
# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

### Retrofit 2
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

-dontwarn retrofit2.adapter.rxjava.CompletableHelper$** # https://github.com/square/retrofit/issues/2034
#Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod
# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**
# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit
# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*
# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

### OkHttp3
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

### Kotlin Coroutine
# https://github.com/Kotlin/kotlinx.coroutines/blob/master/README.md
# ServiceLoader support
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepnames class kotlinx.coroutines.android.AndroidExceptionPreHandler {}
-keepnames class kotlinx.coroutines.android.AndroidDispatcherFactory {}
# Most of volatile fields are updated with AFU and should not be mangled
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}
# Same story for the standard library's SafeContinuation that also uses AtomicReferenceFieldUpdater
-keepclassmembernames class kotlin.coroutines.SafeContinuation {
    volatile <fields>;
}
# https://github.com/Kotlin/kotlinx.atomicfu/issues/57
-dontwarn kotlinx.atomicfu.**

-dontwarn kotlinx.coroutines.flow.**

# Data models
-keep class com.hakmar.employeelivetracking.common.data.remote.dto.*
-keep class com.hakmar.employeelivetracking.common.data.mapper.*
-keep class com.hakmar.employeelivetracking.common.domain.model.*
-keep class com.hakmar.employeelivetracking.features.auth.data.remote.dto.*
-keep class com.hakmar.employeelivetracking.features.bs_store.data.remote.dto.*
-keep class com.hakmar.employeelivetracking.features.bs_store.data.mapper.*
-keep class com.hakmar.employeelivetracking.features.bs_store.domain.model.*
-keep class com.hakmar.employeelivetracking.features.navigation.data.mapper.*
-keep class com.hakmar.employeelivetracking.features.navigation.domain.model.*
-keep class com.hakmar.employeelivetracking.features.notification.domain.model.*
-keep class com.hakmar.employeelivetracking.features.pm_store.data.mapper.*
-keep class com.hakmar.employeelivetracking.features.pm_store.domain.model.*
-keep class com.hakmar.employeelivetracking.features.profile.data.mapper.*
-keep class com.hakmar.employeelivetracking.features.profile.domain.model.*
-keep class com.hakmar.employeelivetracking.features.qr_screen.data.*
-keep class com.hakmar.employeelivetracking.features.store_detail.data.mapper.*
-keep class com.hakmar.employeelivetracking.features.store_detail.data.remote.dto.*
-keep class com.hakmar.employeelivetracking.features.store_detail.domain.model.*
-keep class com.hakmar.employeelivetracking.features.store_detail_tasks.data.remote.dto.*
-keep class com.hakmar.employeelivetracking.features.store_detail_tasks.data.mapper.*
-keep class com.hakmar.employeelivetracking.features.store_detail_tasks.domain.model.*
-keep class com.hakmar.employeelivetracking.features.tasks.domain.model.*
-keep class com.hakmar.employeelivetracking.features.tasks.data.mapper.*
-keep class com.hakmar.employeelivetracking.features.tasks.domain.model.*








