package com.hedgehog.gdzietabiedra.data.migration

import dagger.Reusable
import io.realm.DynamicRealm
import io.realm.RealmMigration
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

@Reusable
class Migration @Inject constructor(private val versionMigrations: Map<Int, Provider<VersionMigration>>) : RealmMigration {

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        Timber.d("migrating from $oldVersion to $newVersion")
        for (i in oldVersion.toInt() until newVersion) {
            val provider = versionMigrations[i.toInt()]
            Timber.d("migration $i")
            provider?.get()?.migrate(realm, i)
        }
    }
}
