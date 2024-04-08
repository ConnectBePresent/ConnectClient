package com.example.connectcompose
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Upsert
    suspend fun insertContact(contact : Contact)

    @Delete
    suspend fun deleteContact(contact : Contact)

    @Query("SELECT * FROM contact ORDER BY firstName ASC")
    fun sortByFirstName() : Flow<List<Contact>>

    @Query("SELECT * FROM contact ORDER BY phoneNumber ASC")
    fun sortByPhoneNumber() : Flow<List<Contact>>
}