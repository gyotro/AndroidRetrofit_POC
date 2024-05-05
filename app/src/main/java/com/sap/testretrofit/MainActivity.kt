package com.sap.testretrofit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.sap.testretrofit.presentation.screen.TokenViewModel
import com.sap.testretrofit.presentation.ui.theme.TestRetrofitTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestRetrofitTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val viewModel = getViewModel<TokenViewModel>()
                    TokenData(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun TokenData(modifier: Modifier = Modifier, viewModel: TokenViewModel) {
    val data = viewModel.cpiMoni.observeAsState().value ?: "nada"
    Text(
        text = "Hello $data!",
        modifier = modifier.padding(18.dp)
    )
}

