package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class TTSHelper(context: Context) : TextToSpeech.OnInitListener {

    private val appContext = context.applicationContext
    private var tts: TextToSpeech? = TextToSpeech(appContext, this)

    private var _isInitialized = false
    val isInitialized: Boolean get() = _isInitialized

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private var pendingSpeech: PendingUtterance? = null

    // Callbacks map by utteranceId
    private val completionCallbacks = mutableMapOf<String, () -> Unit>()
    private val startCallbacks = mutableMapOf<String, () -> Unit>()

    private data class PendingUtterance(
        val text: String,
        val utteranceId: String,
        val onStart: (() -> Unit)?,
        val onDone: (() -> Unit)?
    )

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val ttsInstance = tts
            if (ttsInstance != null) {
                // Set to standard English (US/UK) - No Hindi voice
                val langResult = ttsInstance.setLanguage(Locale.US)
                if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    ttsInstance.language = Locale.ENGLISH
                }

                // Child-friendly soft, polite, and calm acoustic settings:
                // Pitch (1.08f) produces a gentle, warm, soothing voice
                // Speech rate (0.80f) is pleasantly slow and easy for children to listen and repeat
                ttsInstance.setPitch(1.08f)
                ttsInstance.setSpeechRate(0.80f)

                // Configure modern AudioAttributes for clear, soft speech synthesis
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val audioAttributes = AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .build()
                    ttsInstance.setAudioAttributes(audioAttributes)
                }

                // Register listener for speech tracking
                ttsInstance.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                        _isSpeaking.value = true
                        utteranceId?.let { id ->
                            startCallbacks[id]?.invoke()
                        }
                    }

                    override fun onDone(utteranceId: String?) {
                        _isSpeaking.value = false
                        utteranceId?.let { id ->
                            completionCallbacks.remove(id)?.invoke()
                            startCallbacks.remove(id)
                        }
                    }

                    override fun onError(utteranceId: String?) {
                        _isSpeaking.value = false
                        utteranceId?.let { id ->
                            completionCallbacks.remove(id)
                            startCallbacks.remove(id)
                        }
                        Log.e("TTSHelper", "Error playing utterance: $utteranceId")
                    }
                })

                _isInitialized = true
                Log.d("TTSHelper", "Soft & polite TextToSpeech initialized successfully")

                // Execute any queued pending speech requested during startup
                pendingSpeech?.let { pending ->
                    speak(pending.text, pending.utteranceId, pending.onStart, pending.onDone)
                    pendingSpeech = null
                }
            }
        } else {
            Log.e("TTSHelper", "Failed to initialize Android TextToSpeech, status code: $status")
        }
    }

    /**
     * Speaks the given text with soft, polite, clear English articulation.
     */
    fun speak(
        text: String,
        utteranceId: String = "TTS_${System.currentTimeMillis()}",
        onStart: (() -> Unit)? = null,
        onDone: (() -> Unit)? = null
    ) {
        val sanitized = cleanTextForSpeech(text)
        if (sanitized.isBlank()) return

        if (!_isInitialized || tts == null) {
            // Queue pending utterance if still initializing
            pendingSpeech = PendingUtterance(sanitized, utteranceId, onStart, onDone)
            return
        }

        onStart?.let { startCallbacks[utteranceId] = it }
        onDone?.let { completionCallbacks[utteranceId] = it }

        tts?.stop()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val params = Bundle().apply {
                putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, 0.95f)
            }
            tts?.speak(sanitized, TextToSpeech.QUEUE_FLUSH, params, utteranceId)
        } else {
            val params = HashMap<String, String>().apply {
                put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId)
            }
            @Suppress("DEPRECATION")
            tts?.speak(sanitized, TextToSpeech.QUEUE_FLUSH, params)
        }
    }

    /**
     * Pronounces a specific category title and description in clean English.
     */
    fun speakCategory(
        categoryTitle: String,
        description: String,
        onDone: (() -> Unit)? = null
    ) {
        val speechText = "$categoryTitle. $description"
        speak(speechText, "CAT_${System.currentTimeMillis()}", onDone = onDone)
    }

    /**
     * Pronounces a detail card item in soft, polite English.
     */
    fun speakDetailItem(
        name: String,
        description: String,
        funFact: String,
        onDone: (() -> Unit)? = null
    ) {
        val speech = "$name. $description. $funFact"
        speak(speech, "ITEM_${name}_${System.currentTimeMillis()}", onDone = onDone)
    }

    /**
     * Stops current speech output immediately.
     */
    fun stop() {
        tts?.stop()
        _isSpeaking.value = false
        completionCallbacks.clear()
        startCallbacks.clear()
    }

    /**
     * Sanitizes input text so TTS engine articulates clearly without stumbling on symbols.
     */
    private fun cleanTextForSpeech(raw: String): String {
        return raw
            .replace(Regex("[\\(\\)\\[\\]\\{\\}]"), ", ")
            .replace(Regex("[*#_~`|]"), "")
            .replace(Regex("\\s+"), " ")
            .trim()
    }

    /**
     * Shuts down TextToSpeech engine and frees audio resources.
     */
    fun shutdown() {
        stop()
        tts?.shutdown()
        tts = null
        _isInitialized = false
    }
}
