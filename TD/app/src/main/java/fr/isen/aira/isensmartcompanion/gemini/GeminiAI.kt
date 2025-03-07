package fr.isen.aira.isensmartcompanion.gemini
/*
import com.google.android.libraries.ai.gemini.GeminiClient
import com.google.android.libraries.ai.gemini.GeminiRequest
import com.google.android.libraries.ai.gemini.GeminiResponse
import fr.isen.aira.isensmartcompanion.BuildConfig

object GeminiAI {
    // Initialize the Gemini client with your API key
    private val geminiClient: GeminiClient by lazy {
        GeminiClient.initialize(apiKey = BuildConfig.GEMINI_API_KEY)
    }

    /**
     * Call the Gemini 1.5 flash model with the given question.
     * Returns the AI's response text.
     */
    suspend fun callGeminiFlashModel(question: String): String {
        // Create a request for the Gemini flash model
        val request = GeminiRequest(
            model = "gemini-1.5-flash", // specify the flash model
            query = question
        )
        // Call the model and get a response
        val response: GeminiResponse = geminiClient.callModel(request)
        // Return the response text, or a default message if null
        return response.responseText ?: "No answer from Gemini."
    }
}
*/