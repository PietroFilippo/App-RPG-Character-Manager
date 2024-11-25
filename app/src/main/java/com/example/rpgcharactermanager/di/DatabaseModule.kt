package com.example.rpgcharactermanager.di

import android.content.Context
import androidx.room.Room
import com.example.rpgcharactermanager.AppDatabase
import com.example.rpgcharactermanager.data.dao.EquipamentoDao
import com.example.rpgcharactermanager.data.dao.HabilidadeDao
import com.example.rpgcharactermanager.data.dao.PersonagemDao
import com.example.rpgcharactermanager.data.repository.PersonagemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Define um módulo de dependência para o dagger hilt
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    // Fornece uma instância única (Singleton) do banco de dados AppDatabase
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    // Fornece uma instância do PersonagemDao
    @Provides
    @Singleton
    fun providePersonagemDao(database: AppDatabase): PersonagemDao {
        return database.personagemDao()
    }

    // Fornece uma instância do EquipamentoDao
    @Provides
    @Singleton
    fun provideEquipamentoDao(database: AppDatabase): EquipamentoDao {
        return database.equipamentoDao()
    }

    // Fornece uma instância da HabilidadeDao
    @Provides
    @Singleton
    fun provideHabilidadeDao(database: AppDatabase): HabilidadeDao {
        return database.habilidadeDao()
    }

    // Fornece uma instância do repositório PersonagemRepository que combina os DAOs
    @Provides
    @Singleton
    fun providePersonagemRepository(
        personagemDao: PersonagemDao,
        equipamentoDao: EquipamentoDao,
        habilidadeDao: HabilidadeDao
    ): PersonagemRepository {
        return PersonagemRepository(personagemDao, equipamentoDao, habilidadeDao)
    }
}