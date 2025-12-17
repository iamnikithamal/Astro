package com.astro.storm.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.astro.storm.data.local.chat.ChatDao
import com.astro.storm.data.local.chat.ConversationEntity
import com.astro.storm.data.local.chat.MessageEntity

/**
 * Room database for chart and chat persistence
 */
@Database(
    entities = [
        ChartEntity::class,
        ConversationEntity::class,
        MessageEntity::class
    ],
    version = 3,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class ChartDatabase : RoomDatabase() {
    abstract fun chartDao(): ChartDao
    abstract fun chatDao(): ChatDao

    companion object {
        @Volatile
        private var INSTANCE: ChartDatabase? = null

        /**
         * Migration from version 1 to 2: Add gender column to charts table
         */
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE charts ADD COLUMN gender TEXT NOT NULL DEFAULT 'PREFER_NOT_TO_SAY'")
            }
        }

        /**
         * Migration from version 2 to 3: Add chat tables
         */
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Create conversations table
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS chat_conversations (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        title TEXT NOT NULL,
                        modelId TEXT NOT NULL,
                        providerId TEXT NOT NULL,
                        profileId INTEGER,
                        createdAt INTEGER NOT NULL,
                        updatedAt INTEGER NOT NULL,
                        isPinned INTEGER NOT NULL DEFAULT 0,
                        isArchived INTEGER NOT NULL DEFAULT 0,
                        systemPromptOverride TEXT,
                        messageCount INTEGER NOT NULL DEFAULT 0
                    )
                """.trimIndent())

                // Create messages table
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS chat_messages (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        conversationId INTEGER NOT NULL,
                        role TEXT NOT NULL,
                        content TEXT NOT NULL,
                        reasoningContent TEXT,
                        toolCallsJson TEXT,
                        toolCallId TEXT,
                        toolsUsedJson TEXT,
                        modelId TEXT,
                        createdAt INTEGER NOT NULL,
                        isStreaming INTEGER NOT NULL DEFAULT 0,
                        errorMessage TEXT,
                        promptTokens INTEGER,
                        completionTokens INTEGER,
                        totalTokens INTEGER,
                        FOREIGN KEY (conversationId) REFERENCES chat_conversations(id) ON DELETE CASCADE
                    )
                """.trimIndent())

                // Create indices for messages table
                db.execSQL("CREATE INDEX IF NOT EXISTS index_chat_messages_conversationId ON chat_messages(conversationId)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_chat_messages_createdAt ON chat_messages(createdAt)")
            }
        }

        fun getInstance(context: Context): ChartDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChartDatabase::class.java,
                    "astrostorm_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
