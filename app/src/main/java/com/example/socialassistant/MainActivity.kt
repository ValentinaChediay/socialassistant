package com.example.socialassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.socialassistant.ui.theme.SocialAssistantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocialAssistantTheme {
                SampleAppNavGraph()
//                Surface(
//                    modifier = Modifier.fillMaxWidth(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Box(
//                        modifier = Modifier.fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        ModalBottomSheetM3()
//                    }
//                    //StandardBottomSheetM3()
//                }
            }
        }
    }
}