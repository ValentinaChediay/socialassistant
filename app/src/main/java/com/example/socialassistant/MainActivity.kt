package com.example.socialassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.socialassistant.ui.theme.SocialAssistantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocialAssistantTheme {
                val viewModel = viewModel<SocialAssistantViewModel>()
                SampleAppNavGraph(viewModel)
            }
        }
    }
}