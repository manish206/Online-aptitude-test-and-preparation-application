package com.example.project

import android.database.Cursor
import android.provider.BaseColumns

public final class QuestionContract : BaseColumns {

    public final class QuestionTable {
        companion object {

            public final var TABLE_NAME: String = "table_questions"
            public final var ID:String="ID"
            public final var COLUMN_TYPE: String = "Type"
            public final var COLUMN_QUESTION: String = "question"
            public final var COLUMN_OPTION1: String = "option1"
            public final var COLUMN_OPTION2: String = "option2"
            public final var COLUMN_OPTION3: String = "option3"
            public final var COLUMN_OPTION4: String = "option4"
            public final var COLUMN_ANSWER: String = "answer"
            public final var COLUMN_DESCRIPTION: String = "description"

        }
    }
}