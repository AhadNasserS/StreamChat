package com.example.streamchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.streamchat.ui.theme.StreamChatTheme
import com.google.firebase.firestore.auth.User
import io.getstream.video.android.compose.permission.LaunchCallPermissions
import io.getstream.video.android.compose.theme.VideoTheme
import io.getstream.video.android.compose.ui.components.call.activecall.CallContent
import io.getstream.video.android.core.GEO
import io.getstream.video.android.core.StreamVideoBuilder
import io.getstream.video.android.core.call.state.FlipCamera
import io.getstream.video.android.core.call.state.LeaveCall
import io.getstream.video.android.core.call.state.ToggleCamera
import io.getstream.video.android.core.call.state.ToggleMicrophone

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiKey = "mmhfdzb5evj2"
        val userToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiQmFzdGlsYV9TaGFuIiwiaXNzIjoiaHR0cHM6Ly9wcm9udG8uZ2V0c3RyZWFtLmlvIiwic3ViIjoidXNlci9CYXN0aWxhX1NoYW4iLCJpYXQiOjE3MTc0MzE2NTIsImV4cCI6MTcxODAzNjQ1N30.nOjUIp4_49QvreJ-hvCCc9CmxEUxye7MBWRk-30Akts"
        val userId = "Bastila_Shan"
        val callId = "jBdxg7o8I53s"

        val user = io.getstream.video.android.model.User(
            id = userId, // any string
            name = "Tutorial",
            image = "https://bit.ly/2TIt8NR",
            role = "admin",
        )

        val client = StreamVideoBuilder(
            context = applicationContext,
            apiKey = apiKey, // demo API key
            geo = GEO.GlobalEdgeNetwork,
            user = user,
            token = userToken,
        ).build()

        setContent {
            StreamChatTheme {
                val call = client.call(type = "default", id = callId)
                LaunchCallPermissions(call = call) {
                    call.join(create = true)
                }

                VideoTheme {
                    CallContent(
                        modifier = Modifier.fillMaxSize(),
                        call = call,
                        enableInPictureInPicture = true,
                        onBackPressed = { finish() },
                        onCallAction = { callAction ->
                            when (callAction) {
                                is FlipCamera -> call.camera.flip()
                                is ToggleCamera -> call.camera.setEnabled(callAction.isEnabled)
                                is ToggleMicrophone -> call.microphone.setEnabled(callAction.isEnabled)
                                is LeaveCall -> finish()
                                else -> Unit
                            }
                        },
                    )
                }
            }
        }
    }
}
