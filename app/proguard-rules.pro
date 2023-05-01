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

-keep class retrofit2.** { *; }
-keep class okhttp3.internal.** { *; }

-dontwarn okhttp3.internal.**
-dontwarn retrofit2.**

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

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
-keep class com.hakmar.employeelivetracking.common.data.remote.dto.* { *; }
-keep class com.hakmar.employeelivetracking.common.data.mapper.* { *; }
-keep class com.hakmar.employeelivetracking.common.domain.model.* { *; }
-keep class com.hakmar.employeelivetracking.features.auth.data.remote.dto.* { *; }
-keep class com.hakmar.employeelivetracking.features.bs_store.data.remote.dto.* { *; }
-keep class com.hakmar.employeelivetracking.features.bs_store.data.mapper.* { *; }
-keep class com.hakmar.employeelivetracking.features.bs_store.domain.model.* { *; }
-keep class com.hakmar.employeelivetracking.features.navigation.data.mapper.* { *; }
-keep class com.hakmar.employeelivetracking.features.navigation.domain.model.* { *; }
-keep class com.hakmar.employeelivetracking.features.notification.domain.model.* { *; }
-keep class com.hakmar.employeelivetracking.features.pm_store.data.mapper.* { *; }
-keep class com.hakmar.employeelivetracking.features.pm_store.domain.model.* { *; }
-keep class com.hakmar.employeelivetracking.features.profile.data.mapper.* { *; }
-keep class com.hakmar.employeelivetracking.features.profile.domain.model.* { *; }
-keep class com.hakmar.employeelivetracking.features.qr_screen.data.* { *; }
-keep class com.hakmar.employeelivetracking.features.store_detail.data.mapper.* { *; }
-keep class com.hakmar.employeelivetracking.features.store_detail.data.remote.dto.* { *; }
-keep class com.hakmar.employeelivetracking.features.store_detail.domain.model.* { *; }
-keep class com.hakmar.employeelivetracking.features.store_detail_tasks.data.remote.dto.* { *; }
-keep class com.hakmar.employeelivetracking.features.store_detail_tasks.data.mapper.* { *; }
-keep class com.hakmar.employeelivetracking.features.store_detail_tasks.domain.model.* { *; }
-keep class com.hakmar.employeelivetracking.features.tasks.domain.model.* { *; }
-keep class com.hakmar.employeelivetracking.features.tasks.data.mapper.* { *; }
-keep class com.hakmar.employeelivetracking.features.tasks.data.remote.dto.* { *; }








